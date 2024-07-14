package aed;

// Invariante de representación:
//  pred InvRepTrieMaterias (e: trieMaterias)
//      {esTrie(e) == true}
//  
//  esTrie(e) = esArbol(e) && todosNodosUtiles(e) && arbolEnOrdenLexicografico(e) 
//
//  esArbol(e) = todos los nodos, salvo la raiz, tienen un solo padre
//  todosNodosUtiles(e) = todo nodo no definido tiene hijos 
//  arbolEnOrdenLexicografico(e) = toda lista de hijos de un nodo está ordenada en orden lexicográfico


import aed.SistemaSIU.CargoDocente;

public class trieMaterias {
    
    private Nodo raiz;
    private int cantMaterias;

    private class Nodo{
        private char valor;
        private Nodo hermano;
        private Nodo hijo;
        private boolean def;
        private materia materia;

        Nodo(char v){
            valor = v;
            def = false;
        }
    }

    public trieMaterias(){  //Complejidad de la función: O(1)
        cantMaterias = 0;    //O(1)
        raiz = null;    //O(1)
    }

    public void insertarMateria(materia materiaClase){   //Complejidad de la función: max{O(1), O(|m|)} = O(|m|)
        String materia = materiaClase.getNombre();
        int i = 0;                //O(1)
        Nodo actual = raiz;       //O(1) 
        Nodo hermanoMenor = null; //O(1)
        Nodo padre = null;        //O(1)
        if(raiz == null){ //no hay materias  //O(1)
            raiz = new Nodo(materia.charAt(0));  //O(1)
            padre = raiz;                              //O(1)
            actual = raiz.hijo;                        //O(1)  
            i = 1;                                     //O(1) 
        } else { //hay materias
            if (raiz.valor > materia.charAt(0)) {    //O(1)
                Nodo nuevaRaiz = new Nodo(materia.charAt(0));    //O(1)
                nuevaRaiz.hermano = raiz;        //O(1)
                raiz = nuevaRaiz;                //O(1)
                padre = raiz;                    //O(1)
                actual = raiz.hijo;              //O(1)
                i = 1;                           //O(1) 
            } else {
                while (actual != null && i < materia.length()) {  //Complejidad del ciclo: O(|m|) * O(1)  = O(|m|) 
                    while (actual != null && actual.valor < materia.charAt(i)){ //Comp del ciclo: O(1) * O(1) = O(1) por caracteres finitos 
                        hermanoMenor = actual;        //O(1)
                        actual = actual.hermano;      //O(1)
                    } // actual == null || actual.valor >= carrera.charAt(i)
                    if (actual != null && actual.valor > materia.charAt(i)) {   //O(1)
                        Nodo nuevo = new Nodo(materia.charAt(i));               //O(1) 
                        if (hermanoMenor == null) {                             //O(1)
                        padre.hijo = nuevo;                          //O(1)      
                        } else {                    
                        hermanoMenor.hermano = nuevo;                //O(1)
                        }
                        nuevo.hermano = actual;                      //O(1)
                        actual = nuevo;                              //O(1)
                    } else if (actual == null) {     //O(1)
                        Nodo nuevo = new Nodo(materia.charAt(i)); //O(1)
                        hermanoMenor.hermano = nuevo;             //O(1)
                        actual = nuevo;                           //O(1) 
                    } //aca 100% -> (actual != null && actual.valor == carrera.charAt(i))
                    padre = actual;        //O(1)
                    actual = actual.hijo;    //O(1)
                    hermanoMenor = null;    //O(1)
                    i++;                    //O(1)
                }

            }

        }
        while(i < materia.length()){   //Complejidad del ciclo: O(|m|) pues recorre el largo de la materia y cada ejecución del ciclo es O(1)
            actual = new Nodo(materia.charAt(i)); //O(1)
            padre.hijo = actual;    //O(1)
            padre = actual;         //O(1)
            actual = actual.hijo;   //O(1)
            i++;                    //O(1)
        }
        if (padre.def==false){this.cantMaterias++;}   //O(1)
        padre.def = true;                            //O(1)
        if(padre.materia==null){padre.materia = materiaClase;}     //O(1)
    }

    public boolean perteneceMaterias(String materia){    //Complejidad de la función: O(|m|) 
        if(raiz == null){ //no hay materias, mejor caso   //O(1)
            return false;                      //O(1)  
        } else { //hay carreras, peor caso 
            int i = 0;            //O(1)
            Nodo actual = raiz;    //O(1)
            Nodo padre = null;    //O(1)
            while (actual != null && i < materia.length()) {    //Complejidad del ciclo: O(|m|)
                while (actual != null && actual.valor != materia.charAt(i)){ //O(1) por cantidad de caracteres acotada
                    actual = actual.hermano;    //O(1)
                } // actual == null || actual.valor == carrera.charAt(i)
                if (actual != null && actual.valor == materia.charAt(i)) { //O(1)
                    i++;    //O(1)
                    padre = actual;    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            return i == materia.length() && padre.valor == materia.charAt(materia.length()-1) && padre.def == true;   //O(1)
        }
    }
    
    public void insertarAlumno(String materia, String alumno){    //Complejidad de la función: 2*O(|m|) = O(|m|)    
        if(perteneceMaterias(materia)){        //O(|m|) por la función perteneceMaterias
            Nodo actual = raiz;        //O(1)
            for(int i=0;i<materia.length();i++){         //O(|m|)
                while(actual.valor != materia.charAt(i)){     //O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(i<materia.length()-1){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            actual.materia.insertarAlumno(alumno);    //O(1)
        }
    }

    public int inscriptos(String materia){    //Complejidad de la función: O(|m|)
        if(perteneceMaterias(materia)){    //O(|m|)    peor caso
            Nodo actual = raiz;    //O(1)
            for(int i=0;i<materia.length();i++){            //O(|m|)
                while(actual.valor != materia.charAt(i)){    //O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(i<materia.length()-1){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            return actual.materia.cantAlumnos();        //O(1)
        }else{        //O(1)    mejor caso
            return 0;    //O(1)
        }
    }

    public void agregarDocente(CargoDocente docente, String materia){    //Complejidad de la función: O(|m|)
        if(perteneceMaterias(materia)){        //O(|m|)
            Nodo actual = raiz;    //O(1)
            for(int i=0;i<materia.length();i++){     //O(|m|)
                while(actual.valor != materia.charAt(i)){    //O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(i<materia.length()-1){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            actual.materia.agregarDocente(docente);    //O(1)
        }
    }

    public int[] plantelDocente(String materia){    //Complejidad de la materia: O(|m|)
        if(perteneceMaterias(materia)){ //Peor caso   //O(|m|)
            Nodo actual = raiz;    //O(1)
            for(int i=0;i<materia.length();i++){    //O(|m|)
                while(actual.valor != materia.charAt(i)){    //O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(i<(materia.length()-1)){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            return actual.materia.plantelDocente();    //O(1)
        }else{    //Mejor caso
            return null;    //O(1)
        }
    }

    public String[] materias(){    //O(sum(|M_c|))
        if (cantMaterias !=0 ) {    //Peor caso    //O(1)
            String[] res = new String[cantMaterias];    //O(1)
            String pref = "";    //O(1)
            Nodo actual = raiz;    //O(1)
            materias(actual, pref, res); //O(sum(|M_c|)) recorre todos las materias (que empiezan con la letra de la raiz), entonces es O de la materia de nombre más largo, ie, O(sum()|M_c|)
            while(actual.hermano!=null){    //O(1)
                materias(actual.hermano,pref,res);     //recorre todos las materias (que empiezan con las siguientes letras), entonces es O de la materia de nombre más largo, ie, O(sum()|M_c|) 
                actual = actual.hermano;    //O(1)
            }
            return res;     //O(1)
        } else {    //Mejor caso
            return new String[0];    //O(1)
        }
    }

    public void materias(Nodo n, String prefijo, String[] res){
        if(n == null){
            return;
        }else{
            if(n.def == true){
                String materia = prefijo + n.valor;
                res[nroElementos(res)] = materia;
                if(n.hijo!=null){materias(n.hijo,materia,res);}
            }else{
                Nodo hijo = n.hijo;
                materias(hijo,prefijo + n.valor,res);
                while(hijo.hermano!=null){
                    materias(hijo.hermano,prefijo + n.valor,res);
                    hijo = hijo.hermano;
                }
            }
        }
    }

    public int nroElementos(String[] lista){
        int i = 0;
        while(lista[i]!=null){i++;}
        return i;
    }

    public void eliminarMateria(String materia){
        if(raiz != null){ //si hay materia...
            int i = 0;
            Nodo actual = raiz;
            Nodo padre = null;
            Nodo ultimoNodoUtil = null;
            int caso = 0;
            while (actual != null && i < materia.length()) {
                while (actual != null && actual.valor != materia.charAt(i)){
                    if (actual.hijo != null || actual.def == true) {
                        ultimoNodoUtil = actual;
                        caso = 1;
                    }
                    actual = actual.hermano;
                } // actual == null || actual.valor == materia.charAt(i)
                if (actual != null && actual.valor == materia.charAt(i)) {
                    i++;
                    padre = actual;
                    actual = actual.hijo;
                    if (actual !=null && actual.hermano != null) { //caso = 2;
                        ultimoNodoUtil = padre;
                    }
                }
            }
            if (i == materia.length() && padre.valor == materia.charAt(materia.length()-1) && padre.def == true) { // si materia pertenece a trieMaterias...
                if (padre.hijo != null) { // caso -1 agregado despues
                    padre.def = false;
                } else if (ultimoNodoUtil == null) { // tambien podria poner caso == 0
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
                cantMaterias--;
            } // si no pertenece, no hago nada
        } // si no hay materias, no hago nada
    }

    public void eliminarMateria(String materia, trieAlumnos trieAlumnos){
        if(perteneceMaterias(materia)){
            Nodo actual = raiz;
            for(int i=0;i<materia.length();i++){
                while(actual.valor != materia.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<materia.length()-1){
                    actual = actual.hijo;
                }
            }
            actual.materia.eliminarMateria();
            actual.materia.eliminarMateriaAAlumno(trieAlumnos);
        }
    }

}
