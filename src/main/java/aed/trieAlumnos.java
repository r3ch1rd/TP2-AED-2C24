package aed;

public class trieAlumnos {
    
    private Nodo raiz;
    private int cantAlumnos;

    private class Nodo{
        private char valor;
        private Nodo hermano;
        private Nodo hijo;
        private boolean def;
        
        // solo se usa para el trie alumnosNroMaterias
        private int nroMaterias;

        Nodo(char v){
            valor = v;
            def = false;
        }
    }

    public trieAlumnos(){
        cantAlumnos = 0;
        raiz = null;
    }

    public int cantAlumnos(){
        return this.cantAlumnos;
    }

    public void insertarAlumno(String alumno){
        int i = 0;
        Nodo actual = raiz;
        Nodo hermanoMenor = null;
        Nodo padre = null;
        if(raiz == null){ //no hay alumnos
            raiz = new Nodo(alumno.charAt(0));
            padre = raiz;
            actual = raiz.hijo;
            i = 1;
        } else { //hay alumnos
            if (raiz.valor > alumno.charAt(0)) {
                Nodo nuevaRaiz = new Nodo(alumno.charAt(0));
                nuevaRaiz.hermano = raiz;
                raiz = nuevaRaiz;
                padre = raiz;
                actual = raiz.hijo;
                i = 1;
            } else {
                while (actual != null && i < alumno.length()) {
                    while (actual != null && actual.valor < alumno.charAt(i)){
                        hermanoMenor = actual;
                        actual = actual.hermano;
                    } // actual == null || actual.valor >= carrera.charAt(i)
                    if (actual != null && actual.valor > alumno.charAt(i)) {
                        Nodo nuevo = new Nodo(alumno.charAt(i));
                        if (hermanoMenor == null) {
                        padre.hijo = nuevo;
                        } else {
                        hermanoMenor.hermano = nuevo;
                        }
                        nuevo.hermano = actual;
                        actual = nuevo;
                    } else if (actual == null) { 
                        Nodo nuevo = new Nodo(alumno.charAt(i));
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
        while(i < alumno.length()){
            actual = new Nodo(alumno.charAt(i));
            padre.hijo = actual;
            padre = actual;
            actual = actual.hijo;
            i++;
        } 
        padre.def = true;
        this.cantAlumnos++;

        // solo se usa para el trie alumnosNroMaterias
        padre.nroMaterias = 0;
    }

    public boolean perteneceAlumnos(String alumno){
        if(raiz == null){ //no hay alumnos
            return false;
        } else { //hay alumnos
            int i = 0;
            Nodo actual = raiz;
            Nodo padre = null;
            while (actual != null && i < alumno.length()) {
                while (actual != null && actual.valor != alumno.charAt(i)){
                    actual = actual.hermano;
                } // actual == null || actual.valor == carrera.charAt(i)
                if (actual != null && actual.valor == alumno.charAt(i)) {
                    i++;
                    padre = actual;
                    actual = actual.hijo;
                }
            }
            return i == alumno.length() && padre.valor == alumno.charAt(alumno.length()-1) && padre.def == true; 
        }
    }

    // solo se usa para el trie alumnosNroMaterias
    public void agregarMateriaAAlumno(String alumno){
        if(perteneceAlumnos(alumno)){
            Nodo actual = raiz;
            for(char c : alumno.toCharArray()){
                while(actual.valor != c){
                    actual = actual.hermano;
                }
                if(actual.def==false){
                    actual = actual.hijo;
                }else{
                    actual.nroMaterias++;
                }
            }
        }
    }

    // solo se usa para el trie alumnosNroMaterias
    public int materiasInscriptas(String alumno){
        if(perteneceAlumnos(alumno)){
            Nodo actual = raiz;
            for(char c : alumno.toCharArray()){
                while(actual.valor != c){
                    actual = actual.hermano;
                }
                if(actual.def==false){
                    actual = actual.hijo;
                }else{
                    return actual.nroMaterias;
                }
            }
        }
        return 0;
    }
}
