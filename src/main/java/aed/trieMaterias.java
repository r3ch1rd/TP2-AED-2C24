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
        private trieAlumnos alumnos;
        private InfoMateria info;
        private int[] docentes;

        Nodo(char v){
            valor = v;
            def = false;
        }
    }

    public trieMaterias(){  //Complejidad de la función: O(1)
        cantMaterias = 0;    //O(1)
        raiz = null;    //O(1)
    }

    public void insertarMateria(String materia){   //Complejidad de la función: max{O(1), O(|m|)} = O(|m|)
        int i = 0;                //O(1)
        Nodo actual = raiz;       //O(1) 
        Nodo hermanoMenor = null; //O(1)
        Nodo padre = null;        //O(1)
        if(raiz == null){ //no hay materias  //O(1)
            raiz = new Nodo(materia.charAt(0));  //O(1)
            padre = raiz;                        //O(1)
            actual = raiz.hijo;                  //O(1)  
            i = 1;                               //O(1) 
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
        if(padre.alumnos==null){padre.alumnos = new trieAlumnos();}    //O(1)
        if(padre.docentes==null){padre.docentes = new int[] {0,0,0,0};}    //O(1)
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
    
    public void insertarAlumno(String materia, String alumno){
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
            actual.alumnos.insertarAlumno(alumno);
        }
    }

    public int inscriptos(String materia){
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
            return actual.alumnos.cantAlumnos();
        }else{
            return 0;
        }
    }

    public void adjuntarInfoMateriasIguales(String materia, InfoMateria info){
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
            actual.info = info;
        }
    }

    public InfoMateria materiasIguales(String materia){
        if(perteneceMaterias(materia)){
            Nodo actual = raiz;
            for(int i=0;i<materia.length();i++){
                while(actual.valor != materia.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(materia.length()-1)){
                    actual = actual.hijo;
                }
            }
            return actual.info;
        }else{
            return null;
        }
    }

    public void agregarDocente(CargoDocente docente, String materia){
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
            switch (docente) {
                case AY2:
                    actual.docentes[3] = actual.docentes[3] + 1;
                    break;
                case AY1:
                    actual.docentes[2] = actual.docentes[2] + 1;
                    break;
                case JTP:
                    actual.docentes[1] = actual.docentes[1] + 1;
                    break;
                default:
                    actual.docentes[0] = actual.docentes[0] + 1;
                    break;
            }
        }
    }

    public int[] plantelDocente(String materia){
        if(perteneceMaterias(materia)){
            Nodo actual = raiz;
            for(int i=0;i<materia.length();i++){
                while(actual.valor != materia.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(materia.length()-1)){
                    actual = actual.hijo;
                }
            }
            return actual.docentes;
        }else{
            return null;
        }
    }

    public String[] materias(){
        if (cantMaterias !=0 ) {
            String[] res = new String[cantMaterias];
            String pref = "";
            Nodo actual = raiz;
            materias(actual, pref, res);
            while(actual.hermano!=null){
                materias(actual.hermano,pref,res);
                actual = actual.hermano;
            }
            return res; 
        } else {
            return new String[0];
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

    public void eliminarMateria(trieCarreras Carreras, trieAlumnos trieAlumnos, String materia){
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
            if (i == materia.length() && padre.valor == materia.charAt(materia.length()-1) && padre.def == true) { // si materia pertenece a trieCarreras...
                InfoMateria infomateria = padre.info;
                String[] alumnos = padre.alumnos.alumnos();
                if (padre.hijo != null) { // caso -1 agregado despues
                    padre.def = false;
                    padre.alumnos = null;
                    padre.docentes = null;
                    padre.info = null;
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
                for (ParCarreraMateria par : infomateria.getParesCarreraMateria()) {
                    if (Carreras.perteneceMaterias(par.getCarrera(), par.getNombreMateria())) {
                        Carreras.eliminarMateria(par.getCarrera(),par.getNombreMateria(), trieAlumnos);
                    }
                }
                for (String alumno : alumnos) {
                    trieAlumnos.eliminarMateriaAAlumno(alumno);
                }
            } // si no pertenece, no hago nada
        } // si no hay materias, no hago nada
    }



}
