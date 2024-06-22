package aed;

public class SistemaSIU {

    private trieCarreras Carreras;
    private trieAlumnos Alumnos;
    private materiasDeCarrera materiasDeCarrera;

    private class trieCarreras{

        private Nodo raiz;
        private int cantCarreras;

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
