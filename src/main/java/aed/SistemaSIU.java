package aed;

public class SistemaSIU {

    private trieCarreras Carreras;
    // private trieAlumnos Alumnos;
    // private materiasDeCarrera materiasDeCarrera;

    static class trieCarreras{

        private Nodo raiz;
        private int cantCarreras;

        private class Nodo{
            private char valor;
            private Nodo hermano;
            private Nodo hijo;
            private boolean def;
            private trieMaterias materias;

            Nodo(char v){
                valor = v;
                def = false;
            }
        }

        public trieCarreras(){
            cantCarreras = 0;
            raiz = null;
        }

        public void insertarCarrera(String carrera){
            int i = 0;
            Nodo actual = raiz;
            Nodo hermanoMenor = null;
            Nodo padre = null;

            if(raiz == null){ //no hay carreras
                raiz = new Nodo(carrera.charAt(0));
                padre = raiz;
                actual = raiz.hijo;
                i = 1;
            } else { //hay carreras
                if (raiz.valor > carrera.charAt(0)) {
                    Nodo nuevaRaiz = new Nodo(carrera.charAt(0));
                    nuevaRaiz.hermano = raiz;
                    raiz = nuevaRaiz;
                    padre = raiz;
                    actual = raiz.hijo;
                    i = 1;
                } else {
                    while (actual != null && i < carrera.length()) {
                        while (actual != null && actual.valor < carrera.charAt(i)){
                            hermanoMenor = actual;
                            actual = actual.hermano;
                        } // actual == null || actual.valor >= carrera.charAt(i)
                        if (actual != null && actual.valor > carrera.charAt(i)) {
                            Nodo nuevo = new Nodo(carrera.charAt(i));
                            if (hermanoMenor == null) {
                            padre.hijo = nuevo;
                            } else {
                            hermanoMenor.hermano = nuevo;
                            }
                            nuevo.hermano = actual;
                            actual = nuevo;
                        } else if (actual == null) { 
                            Nodo nuevo = new Nodo(carrera.charAt(i));
                            hermanoMenor.hermano = nuevo;
                            actual = nuevo;
                        } //aca 100% -> (actual != null && actual.valor == carrera.charAt(i))
                        padre = actual;
                        actual = actual.hijo;
                        hermanoMenor = null;
                        i++;
                    }

                }

            }
            while(i < carrera.length()){
                actual = new Nodo(carrera.charAt(i));
                padre.hijo = actual;
                padre = actual;
                actual = actual.hijo;
                i++;
            }
            padre.def = true;
            padre.materias = new trieMaterias();
            this.cantCarreras++;
        }


        public boolean perteneceCarrera(String carrera){
            if(raiz == null){ //no hay carreras
                return false;
            } else { //hay carreras
                int i = 0;
                Nodo actual = raiz;
                Nodo padre = null;
                while (actual != null && i < carrera.length()) {
                    while (actual != null && actual.valor != carrera.charAt(i)){
                        actual = actual.hermano;
                    } // actual == null || actual.valor == carrera.charAt(i)
                    if (actual != null && actual.valor == carrera.charAt(i)) {
                        i++;
                        padre = actual;
                        actual = actual.hijo;
                    }
                }
                return i == carrera.length() && padre.valor == carrera.charAt(carrera.length()-1) && padre.def == true; 
            }
        }

        public void eliminarCarrera(String carrera){
            if(raiz != null){ //si hay carreras...
                int i = 0;
                Nodo actual = raiz;
                Nodo padre = null;
                Nodo ultimoNodoUtil = null;
                int caso = 0;
                while (actual != null && i < carrera.length()) {
                    while (actual != null && actual.valor != carrera.charAt(i)){
                        if (actual.hijo != null || actual.def == true) {
                            ultimoNodoUtil = actual;
                            caso = 1;
                        }
                        actual = actual.hermano;
                    } // actual == null || actual.valor == carrera.charAt(i)
                    if (actual != null && actual.valor == carrera.charAt(i)) {
                        i++;
                        padre = actual;
                        actual = actual.hijo;
                        if (actual.hermano != null) { //caso = 2;
                            ultimoNodoUtil = padre;
                        }
                    }
                }
                if (i == carrera.length() && padre.valor == carrera.charAt(carrera.length()-1) && padre.def == true) { // si carrera pertenece a trieCarreras...
                    if (ultimoNodoUtil == null) { // tambien podria poner caso == 0
                        if (raiz.hermano == null) {
                            raiz = null;
                        } else {
                            raiz = raiz.hermano;
                        }
                    } else if (caso == 1) {
                        if (ultimoNodoUtil.hermano.hermano == null) {
                            ultimoNodoUtil.hermano = null;   
                        } else {
                            ultimoNodoUtil.hermano = ultimoNodoUtil.hermano.hermano;
                        }
                    } else { //caso == 2
                        ultimoNodoUtil.hijo = ultimoNodoUtil.hijo.hermano;
                    }
                    cantCarreras--;
                } // si no pertenece, no hago nada
            } // si no hay carreras, no hago nada
        }

        public void insertarMateria(String carrera, String materia){
            if(perteneceCarrera(carrera)){
                Nodo actual = raiz;
                for(char c : carrera.toCharArray()){
                    while(actual.valor != c){
                        actual = actual.hermano;
                    }
                    if(actual.materias == null){
                        actual = actual.hijo;
                    }else{
                        actual.materias.insertarMateria(materia);
                    }
                }
            }
        }

        public boolean perteneceMaterias(String carrera, String materia){
            if(perteneceCarrera(carrera)){
                Nodo actual = raiz;
                for(char c : carrera.toCharArray()){
                    while(actual.valor != c){
                        actual = actual.hermano;
                    }
                    if(actual.materias == null){
                        actual = actual.hijo;
                    }
                }
                return actual.materias.perteneceMaterias(materia);
            }else{
                return false;
            }
        }

    }

    static class trieMaterias{

        private Nodo raiz;
        private int cantMaterias;

        private class Nodo{
            private char valor;
            private Nodo hermano;
            private Nodo hijo;
            private boolean def;

            Nodo(char v){
                valor = v;
                def = false;
            }
        }

        public trieMaterias(){
            cantMaterias = 0;
            raiz = null;
        }

        public void insertarMateria(String materia){
            int i = 0;
            Nodo actual = raiz;
            Nodo hermanoMenor = null;
            Nodo padre = null;
            if(raiz == null){ //no hay materias
                raiz = new Nodo(materia.charAt(0));
                padre = raiz;
                actual = raiz.hijo;
                i = 1;
            } else { //hay materias
                if (raiz.valor > materia.charAt(0)) {
                    Nodo nuevaRaiz = new Nodo(materia.charAt(0));
                    nuevaRaiz.hermano = raiz;
                    raiz = nuevaRaiz;
                    padre = raiz;
                    actual = raiz.hijo;
                    i = 1;
                } else {
                    while (actual != null && i < materia.length()) {
                        while (actual != null && actual.valor < materia.charAt(i)){
                            hermanoMenor = actual;
                            actual = actual.hermano;
                        } // actual == null || actual.valor >= carrera.charAt(i)
                        if (actual != null && actual.valor > materia.charAt(i)) {
                            Nodo nuevo = new Nodo(materia.charAt(i));
                            if (hermanoMenor == null) {
                            padre.hijo = nuevo;
                            } else {
                            hermanoMenor.hermano = nuevo;
                            }
                            nuevo.hermano = actual;
                            actual = nuevo;
                        } else if (actual == null) { 
                            Nodo nuevo = new Nodo(materia.charAt(i));
                            hermanoMenor.hermano = nuevo;
                            actual = nuevo;
                        } //aca 100% -> (actual != null && actual.valor == carrera.charAt(i))
                        padre = actual;
                        actual = actual.hijo;
                        hermanoMenor = null;
                        i++;
                    }

                }

            }
            while(i < materia.length()){
                actual = new Nodo(materia.charAt(i));
                padre.hijo = actual;
                padre = actual;
                actual = actual.hijo;
                i++;
            }
            padre.def = true;
            this.cantMaterias++;
        }


        public boolean perteneceMaterias(String materia){
            if(raiz == null){ //no hay carreras
                return false;
            } else { //hay carreras
                int i = 0;
                Nodo actual = raiz;
                Nodo padre = null;
                while (actual != null && i < materia.length()) {
                    while (actual != null && actual.valor != materia.charAt(i)){
                        actual = actual.hermano;
                    } // actual == null || actual.valor == carrera.charAt(i)
                    if (actual != null && actual.valor == materia.charAt(i)) {
                        i++;
                        padre = actual;
                        actual = actual.hijo;
                    }
                }
                return i == materia.length() && padre.valor == materia.charAt(materia.length()-1) && padre.def == true; 
            }
        }
    }

    public static void main(String[] args){
        trieCarreras carreras = new trieCarreras();
        carreras.insertarCarrera("ciencias de datos");
        carreras.insertarCarrera("ciencias de la computacion");
        carreras.insertarCarrera("diseño de indumentaria");

        System.out.println(carreras.perteneceCarrera("ciencias de datos"));
        System.out.println(carreras.perteneceCarrera("ciencias de la computacion"));
        System.out.println(carreras.perteneceCarrera("diseño de indumentaria"));

        carreras.insertarMateria("ciencias de datos", "intro");
        carreras.insertarMateria("ciencias de datos", "algo2/exalgo1");
        carreras.insertarMateria("ciencias de la computacion", "programacion");
        carreras.insertarMateria("ciencias de la computacion", "analisis 2");
        carreras.insertarMateria("diseño de indumentaria","materiales 1");
        carreras.insertarMateria("diseño de indumentaria","materiales 2");

        System.out.println(carreras.perteneceMaterias("ciencias de datos","intro"));
        System.out.println(carreras.perteneceMaterias("ciencias de datos","algo2/exalgo1"));
        System.out.println(carreras.perteneceMaterias("ciencias de la computacion", "programacion"));
        System.out.println(carreras.perteneceMaterias("ciencias de la computacion", "analisis 2"));
        System.out.println(carreras.perteneceMaterias("diseño de indumentaria","materiales 1"));
        System.out.println(carreras.perteneceMaterias("diseño de indumentaria","materiales 2"));
    }
    

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void inscribir(String estudiante, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int[] plantelDocente(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public boolean excedeCupo(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }
}
