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

        Nodo(char v){      //Funcion completa: O(1) + O(1) = O(1)
            valor = v;     //O(1)
            def = false;   //O(1)
        }
    }

    public trieAlumnos(){
        cantAlumnos = 0;
        raiz = null;
    }

    public int cantAlumnos(){
        return this.cantAlumnos;
    }

    public void insertarAlumno(String alumno){                    //Funcion completa: 9*O(1) = O(1)
        int i = 0;                                                    //O(1)
        Nodo actual = raiz;                                           //O(1)
        Nodo hermanoMenor = null;                                     //O(1)
        Nodo padre = null;                                            //O(1)
        if(raiz == null){ //no hay alumnos                            //If completo: 2*O(1) = O(1) en el peor caso solo hay que tener en cuenta la guarda y la rama del else //Guarda: O(1) 
            raiz = new Nodo(alumno.charAt(0));                            
            padre = raiz;                                                 
            actual = raiz.hijo;                                           
            i = 1;                                                        
        } else { //hay alumnos                                        //Rama: O(1)
            if (raiz.valor > alumno.charAt(0)) {                          //If completo: 2*O(1) = O(1) en el peor caso solo hay que tener en cuenta la guarda y la rama del else //Guarda: O(1) 
                Nodo nuevaRaiz = new Nodo(alumno.charAt(0));                  
                nuevaRaiz.hermano = raiz;                                     
                raiz = nuevaRaiz;                                             
                padre = raiz;                                                 
                actual = raiz.hijo;                                           
                i = 1;                                                        
            } else {                                                      //Rama: O(1)
                while (actual != null && i < alumno.length()) {               //Ciclo: Sumatoria desde k = 0 hasta (en el peor caso) alumno.length() de 7*O(1) = alumno.length()*7*O(1) = O(alumno.length()) = O(1) pues alumno.length() esta acotada //Guarda: O(1) + O(1) = O(1)
                    while (actual != null && actual.valor < alumno.charAt(i)){    //Ciclo: Sumatoria desde k = 0 hasta (en el peor de los casos) 255 (todo el abecedario ASCII) de 3*O(1) = 256*3*O(1) = O(1) //Guarda: O(1) + O(1) = O(1) 
                        hermanoMenor = actual;                                        //O(1)
                        actual = actual.hermano;                                      //O(1)
                    } // actual == null || actual.valor >= carrera.charAt(i)
                    if (actual != null && actual.valor > alumno.charAt(i)) {      //If completo: max{O(1),O(1)} = O(1) //Rama: 5*O(1)  = O(1) //Guarda: O(1) + O(1) = O(1) 
                        Nodo nuevo = new Nodo(alumno.charAt(i));                      //O(1)
                        if (hermanoMenor == null) {                                   //If completo: max{O(1),O(1)} = O(1) //Rama: 2*O(1) = O(1) //Guarda: O(1) 
                        padre.hijo = nuevo;                                               //O(1)
                        } else {                                                      //Rama: O(1)
                        hermanoMenor.hermano = nuevo;                                     //O(1)
                        }
                        nuevo.hermano = actual;                                       //O(1)
                        actual = nuevo;                                               //O(1)
                    } else if (actual == null) {                                  //Rama: 3*O(1) = O(1) //Guarda: O(1) 
                        Nodo nuevo = new Nodo(alumno.charAt(i));                      //O(1)
                        hermanoMenor.hermano = nuevo;                                 //O(1)
                        actual = nuevo;                                               //O(1)
                    } //aca 100% -> (actual != null && actual.valor == carrera.charAt(i))
                    padre = actual;                                               //O(1)
                    actual = actual.hijo;                                         //O(1)
                    hermanoMenor = null;                                          //O(1)
                    i++;                                                          //O(1)
                }

            }

        }
        while(i < alumno.length()){                                   //Ciclo: en el peor caso solo hay que tener en cuenta la guarda pues no entra al ciclo //Guarda: O(1)
            actual = new Nodo(alumno.charAt(i));
            padre.hijo = actual;
            padre = actual;
            actual = actual.hijo;
            i++;
        } 
        padre.def = true;                                             //O(1)
        this.cantAlumnos++;                                           //O(1)

        // solo se usa para el trie alumnosNroMaterias
        padre.nroMaterias = 0;                                        //O(1)
    }

    public boolean perteneceAlumnos(String alumno){                                                          //Funcion completa: 2*O(1) = O(1) 
        if(raiz == null){ //no hay alumnos                                                                       //If completo: 2*O(1) = O(1) en el peor caso solo hay que tener en cuenta la guarda y la rama del else // Guarda: O(1)
            return false;                                                                                            
        } else { //hay alumnos                                                                                   //Rama: 6*O(1) = O(1)
            int i = 0;                                                                                               //O(1)
            Nodo actual = raiz;                                                                                      //O(1)
            Nodo padre = null;                                                                                       //O(1)
            while (actual != null && i < alumno.length()) {                                                          //Ciclo: Sumatoria desde k = 0 hasta (en el peor caso) alumno.length() de 2*O(1) = alumno.length()*2*O(1) = O(alumno.length()) = O(1) pues alumno.length() esta acotada //Guarda: O(1) + O(1) = O(1)
                while (actual != null && actual.valor != alumno.charAt(i)){                                              //Ciclo: Sumatoria desde k = 0 hasta (en el peor de los casos) 255 (todo el abecedario ASCII) de 3*O(1) = 256*3*O(1) = O(1) //Guarda: O(1) + O(1) = O(1) 
                    actual = actual.hermano;                                                                                 //O(1)
                } // actual == null || actual.valor == carrera.charAt(i)
                if (actual != null && actual.valor == alumno.charAt(i)) {                                            //If completo: 4*O(1) = O(1) //Guarda: 2*O(1) = O(1)
                    i++;                                                                                                 //O(1)
                    padre = actual;                                                                                      //O(1)
                    actual = actual.hijo;                                                                                //O(1)
                }
            }
            return i == alumno.length() && padre.valor == alumno.charAt(alumno.length()-1) && padre.def == true;     //O(1)
        }
    }

    // solo se usa para el trie alumnosNroMaterias
    public void agregarMateriaAAlumno(String alumno){    //Funcion completa: 
        if(perteneceAlumnos(alumno)){                        //If completo: O(1) //Guarda: O(1)
            Nodo actual = raiz;                                  //O(1)
            for(char c : alumno.toCharArray()){                  //Ciclo: Sumatoria desde k = 0 hasta (en el peor caso) alumno.length() de 2*O(1) = alumno.length()*2*O(1) = O(alumno.length()) = O(1) pues alumno.length() esta acotada //Guarda: O(1)
                while(actual.valor != c){                            //Ciclo: Sumatoria desde k = 0 hasta (en el peor de los casos) 255 (todo el abecedario ASCII) de O(1) = 256*O(1) = O(1) //Guarda: O(1)
                    actual = actual.hermano;                             //O(1)
                }
                if(actual.def==false){                               //If completo: O(1) //Guarda: O(1)
                    actual = actual.hijo;                                //O(1)
                }else{
                    actual.nroMaterias++;                                //O(1)
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
