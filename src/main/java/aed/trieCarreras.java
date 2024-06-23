package aed;

public class trieCarreras {

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