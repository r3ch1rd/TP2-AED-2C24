package aed;

// Invariante de representación:
//  pred InvRepTrieAlumnos (e: trieAlumnos)
//      {esTrie(e) == true}
//  
//  esTrie(e) = esArbol(e) && todosNodosUtiles(e) && arbolEnOrdenLexicografico(e) 
//
//  esArbol(e) = todos los nodos, salvo la raiz, tienen un solo padre
//  todosNodosUtiles(e) = todo nodo no definido tiene hijos 
//  arbolEnOrdenLexicografico(e) = toda lista de hijos de un nodo está ordenada en orden lexicográfico

public class trieAlumnos {
    
    private Nodo raiz;
    private int cantAlumnos;

    private class Nodo{
        private char valor;
        private Nodo hermano;
        private Nodo hijo;
        private boolean def;
        
        // solo se usa para el trie alumnosNroMaterias y cerrarCarrera
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
        if(raiz == null){ //no hay alumnos             //Guarda: O(1) //Rama: 5 * O(1) = O(1)
            raiz = new Nodo(alumno.charAt(0));         //O(1)
            padre = raiz;                              //O(1) 
            actual = raiz.hijo;                        //O(1)
            i = 1;                                     //O(1)
        } else { //hay alumnos
            if (raiz.valor > alumno.charAt(0)) {                   //Guarda: O(1) //Rama: 7 * O(1) = O(1)
                Nodo nuevaRaiz = new Nodo(alumno.charAt(0));       //O(1)
                nuevaRaiz.hermano = raiz;                          //O(1)
                raiz = nuevaRaiz;                                  //O(1)
                padre = raiz;                                      //O(1)
                actual = raiz.hijo;                                //O(1)
                i = 1;                                             //O(1)
            } else {
                while (actual != null && i < alumno.length()) {
                    while (actual != null && actual.valor < alumno.charAt(i)){    //Guarda: O(1) + O(1) = O(1) // Ciclo: Sumatoria desde k = 0 hasta (en el peor de los casos) 255 (todo el abecedario ASCII) de 3*O(1) = 256*3*O(1) = O(1)
                        hermanoMenor = actual;                                    //O(1)
                        actual = actual.hermano;                                  //O(1)
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
    
    // solo se usa para cerrarMateria
    public void eliminarMateriaAAlumno(String alumno){
        if(perteneceAlumnos(alumno)){
            Nodo actual = raiz;
            for(char c : alumno.toCharArray()){
                while(actual.valor != c){
                    actual = actual.hermano;
                }
                if(actual.def==false){
                    actual = actual.hijo;
                }else{
                    actual.nroMaterias--;
                }
            }
        }
    }


    public String[] alumnos(){
        String[] res = new String[cantAlumnos];
        String pref = "";
        Nodo actual = raiz;
        alumnos(actual, pref, res);
        while(actual.hermano!=null){
            alumnos(actual.hermano,pref,res);
            actual = actual.hermano;
        }
        return res;
    }

    public void alumnos(Nodo n, String prefijo, String[] res){
        if(n == null){
            return;
        }else{
            if(n.def == true){
                String alumno = prefijo + n.valor;
                res[ultimoElem(res)] = alumno;
            }else{
                Nodo hijo = n.hijo;
                alumnos(hijo,prefijo + n.valor,res);
                while(hijo.hermano!=null){
                    alumnos(hijo.hermano,prefijo + n.valor,res);
                    hijo = hijo.hermano;
                }
            }
        }
    }

    public int ultimoElem(String[] lista){
        int i = 0;
        while(lista[i]!=null){i++;}
        return i;
    }
}
