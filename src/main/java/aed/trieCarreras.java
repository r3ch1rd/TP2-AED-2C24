package aed;

import aed.SistemaSIU.CargoDocente;
//  Invariante de representación:
//  pred InvRepTrieCarreras (e: trieCarreras)
//      {esTrie(e) == true}
//  
//  esTrie(e) = esArbol(e) && todosNodosUtiles(e) && arbolEnOrdenLexicografico(e) 
//
//  esArbol(e) = Todos los nodos, salvo la raiz, tienen un solo padre. La raiz no tiene padre.
//  todosNodosUtiles(e) = Todo nodo no definido tiene hijos.
//  arbolEnOrdenLexicografico(e) = Toda lista de hijos de un nodo está ordenada en orden lexicográfico.


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

    public trieCarreras(){  //Función completa: O(1)
        cantCarreras = 0;       //O(1)
        raiz = null;            //O(1)
    }

    public void insertarCarrera(String carrera){  //Función completa: O(|c|)
        int i = 0;                //O(1)
        Nodo actual = raiz;       //O(1) 
        Nodo hermanoMenor = null; //O(1)
        Nodo padre = null;        //O(1)

        if(raiz == null){ //no hay carreras   O(1)
            raiz = new Nodo(carrera.charAt(0));  //O(1)
            padre = raiz;                        //O(1)
            actual = raiz.hijo;                  //O(1)
            i = 1;                               //O(1) 
        } else { //hay carreras
            if (raiz.valor > carrera.charAt(0)) {                //O(1)
                Nodo nuevaRaiz = new Nodo(carrera.charAt(0));    //O(1)
                nuevaRaiz.hermano = raiz;                        //O(1)
                raiz = nuevaRaiz;                                //O(1)
                padre = raiz;                                    //O(1)
                actual = raiz.hijo;                              //O(1)  
                i = 1;                                           //O(1) 
            } else {
                while (actual != null && i < carrera.length()) { //Complejidad del while completo: O(|c|) * O(1) = O(|c|) pues recorre el largo de la carrera y por cada letra es O(1)
                    while (actual != null && actual.valor < carrera.charAt(i)){ //En el peor caso, 
                                                                                //carrera.charAt es el ultimo caracter posible en orden lexicográfico, 
                                                                                //pero los caracteres están acotados entonces O(1)
                        hermanoMenor = actual;    //O(1)
                        actual = actual.hermano;  //O(1)
                    } // actual == null || actual.valor >= carrera.charAt(i)
                    if (actual != null && actual.valor > carrera.charAt(i)) {  //O(1)
                        Nodo nuevo = new Nodo(carrera.charAt(i));              //O(1)  
                        if (hermanoMenor == null) {                            //O(1)
                        padre.hijo = nuevo;                                    //O(1)
                        } else {                                                
                        hermanoMenor.hermano = nuevo;                          //O(1)  
                        }
                        nuevo.hermano = actual;                                //O(1)
                        actual = nuevo;                                        //O(1)
                    } else if (actual == null) {                               //O(1)  
                        Nodo nuevo = new Nodo(carrera.charAt(i));              //O(1)  
                        hermanoMenor.hermano = nuevo;                          //O(1)  
                        actual = nuevo;                                        //O(1)
                    } //aca 100% -> (actual != null && actual.valor == carrera.charAt(i))
                    padre = actual;                                            //O(1)    
                    actual = actual.hijo;                                      //O(1)  
                    hermanoMenor = null;                                       //O(1) 
                    i++;                                                       //O(1) 
                }

            }

        }
        while(i < carrera.length()){     // Complejidad del while: O(|c|) * O(1) = O(|c|)
            actual = new Nodo(carrera.charAt(i)); //O(1)
            padre.hijo = actual;                  //O(1)
            padre = actual;                       //O(1) 
            actual = actual.hijo;                 //O(1)
            i++;                                  //O(1)  
        }
        if (padre.def==false){this.cantCarreras++;} //O(1)
        padre.def = true;                            //O(1)
        if (padre.materias==null){padre.materias = new trieMaterias();} //O(1) pues trieMaterias() es O(1)
    }

    public boolean perteneceCarrera(String carrera){  //Complejidad de la función: max{O(|c|), O(1)} = O(|c|)
        if(raiz == null){ //no hay carreras
            return false;                //O(1)
        } else { //hay carreras
            int i = 0;                     //O(1) 
            Nodo actual = raiz;            //O(1)
            Nodo padre = null;             //O(1)
            while (actual != null && i < carrera.length()) {  //Comp de l while: O(|c|) * O(1) = O(|c|)
                 while (actual != null && actual.valor != carrera.charAt(i)){  //Comp del while : O(1) por cant de caracteres acotada
                    actual = actual.hermano;  //O(1)
                } // actual == null || actual.valor == carrera.charAt(i)
                if (actual != null && actual.valor == carrera.charAt(i)) {  //O(1)
                    i++;                    //O(1)
                    padre = actual;         //O(1)
                    actual = actual.hijo;   //O(1)
                }
            }
            return i == carrera.length() && padre.valor == carrera.charAt(carrera.length()-1) && padre.def == true; //O(1)
        }
    }

    public void eliminarCarrera(String carrera){  //Complejidad de la función: max{O(|c|), O(1)} = O(|c|)
        if(raiz != null){ //si hay carreras...
            int i = 0;                    //O(1)
            Nodo actual = raiz;           //O(1) 
            Nodo padre = null;            //O(1)
            Nodo ultimoNodoUtil = null;   //O(1)
            int caso = 0;                 //O(1)
            while (actual != null && i < carrera.length()) { //Complejidad del ciclo: O(|c|) * O(1) = O(|c|)
                while (actual != null && actual.valor != carrera.charAt(i)){  //O(1) misma justificación que en la linea 60
                    if (actual.hijo != null || actual.def == true) { //O(1)
                        ultimoNodoUtil = actual;                     //O(1)
                        caso = 1;                                    //O(1)
                    }
                    actual = actual.hermano; //O(1)
                } // actual == null || actual.valor == carrera.charAt(i)
                if (actual != null && actual.valor == carrera.charAt(i)) {   //O(1)
                    i++;                    //O(1)
                    padre = actual;         //O(1)
                    actual = actual.hijo;   //O(1)
                    if (actual.hermano != null) { //caso = 2; //O(1)
                        ultimoNodoUtil = padre;    //O(1)
                    }
                }
            }
            if (i == carrera.length() && padre.valor == carrera.charAt(carrera.length()-1) && padre.def == true) { // si carrera pertenece a trieCarreras... //O(1) chquea condiciones finitas
                if (ultimoNodoUtil == null) { // tambien podria poner caso == 0  //O(1)
                    if (raiz.hermano == null) {                                  //O(1)
                        raiz = null;                                             //O(1)
                    } else {            
                        raiz = raiz.hermano;                                     //O(1)
                    }
                } else if (caso == 1) {                                          //O(1)
                    if (ultimoNodoUtil.hermano.hermano == null) {                //O(1)
                        ultimoNodoUtil.hermano = null;                           //O(1)    
                    } else {
                        ultimoNodoUtil.hermano = ultimoNodoUtil.hermano.hermano; //O(1)
                    }
                } else { //caso == 2
                    ultimoNodoUtil.hijo = ultimoNodoUtil.hijo.hermano;           //O(1) 
                }
                cantCarreras--;                                //O(1)
            } // si no pertenece, no hago nada
        } // si no hay carreras, no hago nada
    }

    public void insertarMateria(String carrera, materia materia){ //Complejidad de la función: O(|c| + |m|)
        if(perteneceCarrera(carrera)){                //O(1)
            Nodo actual = raiz;                       //O(1)
            for(char c : carrera.toCharArray()){      //O(|c|)  
                while(actual.valor != c){        //O(1) misma justificación de antes (finitos caracteres)
                    actual = actual.hermano; //O(1)   
                }
                if(actual.materias == null){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            actual.materias.insertarMateria(materia); //O(|m|) especificado en trieMaterias.java
            
        }
    }

    public boolean perteneceMaterias(String carrera, String materia){  //Complejidad de la función: O(|c|+|m|)
        if(perteneceCarrera(carrera)){   //O(1)  es el peor caso si entra en el if
            Nodo actual = raiz;            //O(1)
            for(char c : carrera.toCharArray()){    //O(|c|)    
                while(actual.valor != c){  //O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(actual.materias == null){        //O(1)
                    actual = actual.hijo;        //O(1)
                }
            }
            return actual.materias.perteneceMaterias(materia); //O(|m|) especificado en trieMaterias.java
        }else{
            return false; //O(1)
        }
    }

    public void insertarAlumno(String carrera, String materia, String alumno){    //Complejidad de la función: O(|c|+|m|)
        if(perteneceCarrera(carrera)){                                                //O(|c|)
            Nodo actual = raiz;                                                       //O(1)
            for(int i=0;i<carrera.length();i++){                                      //Ciclo: O(|c|) //Guarda: 3*O(1) = O(1)
                while(actual.valor != carrera.charAt(i)){                                 //Ciclo: O(1) //Guarda: O(1)
                    actual = actual.hermano;                                                  //O(1)
                }
                if(i<(carrera.length()-1)){                                               //If completo: 2*O(1) = O(1) //Guarda: O(1)
                    actual = actual.hijo;                                                     //O(1)
                }
            }
            actual.materias.insertarAlumno(materia, alumno);                         //o(|m|)
        }
    }

    public int inscriptosEnMateria(String carrera, String materia){       //Complejidad de la función: O(|c|+|m|)
        if(perteneceCarrera(carrera)){    //Peor caso        //O(|c|) 
            Nodo actual = raiz;    //O(1)
            for(int i=0;i<carrera.length();i++){        //Ciclo: O(|c|)  //Guarda: O(1)
                while(actual.valor != carrera.charAt(i)){    //Ciclo: O(1)  //Guarda: O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(i<(carrera.length()-1)){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            return actual.materias.inscriptos(materia);    //O(|m|)
        }else{    //Mejor caso
            return 0;    //O(1)
        }
    }

    public trieMaterias trieMaterias(String carrera){   //Funcion completa: O(1) + O(|c|) = O(|c|)
        if(perteneceCarrera(carrera)){                      //If completo: O(|c|)*2 = O(|c|) //Guarda: O(|c|)
            Nodo actual = raiz;                                 //O(1)
            for(int i=0;i<carrera.length();i++){                //Ciclo: Sumatoria desde k = 0 hasta (en el peor caso) |c| de 2*O(1) = |c|*2*O(1) = O(|c|) //Guarda: O(1)
                while(actual.valor != carrera.charAt(i)){           //Ciclo: Sumatoria desde k = 0 hasta (en el peor de los casos) 255 (todo el abecedario ASCII) de O(1) = 256*O(1) = O(1) //Guarda: O(1)
                    actual = actual.hermano;                        //O(1)
                }
                if(i<carrera.length()-1){                           //If completo: O(1)
                    actual = actual.hijo;                           
                }
            }
            return actual.materias;                                 //O(1)
        }else{                                                      //en el peor caso por aca no pasa
            return null;
        }
    }

    public void insertarInfo(InfoMateria infoMateria){                                                  //Funcion completa: Sumatoria de O(|c| + |m|), |C| cantidad de veces

        ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria();                     //O(1)
        trieAlumnos trieAlumnos = new trieAlumnos();                                                        //O(1)
        int[] docentes = new int[] {0,0,0,0};                                                               //O(1)

        for (ParCarreraMateria par : paresCarreraMateria){                                                  //Ciclo: Sumatoria de O(|c|), |C| cantidad de veces
            String carrera = par.getCarrera();                                                                  //O(1)
            this.insertarCarrera(carrera);                                                                      //O(|c|)
        }
        for (ParCarreraMateria par : paresCarreraMateria){                                                  //Ciclo: Sumatoria de O(|c| + |m|), |C| cantidad de veces
            String carrera = par.getCarrera();                                                                  //O(1)
            String nombreMat = par.getNombreMateria();                                                          //O(1)
            materia materia = new materia(nombreMat, paresCarreraMateria, this, trieAlumnos, docentes);         //O(1)
            this.insertarMateria(carrera, materia);                                                             //O(|c| + |m|)
        }
    }

    public void agregarDocente(CargoDocente docente, String carrera, String materia){    //Comp de la función: O(|c|+|m|)
        if(perteneceCarrera(carrera)){    //Peor caso    //O(|c|)    
            Nodo actual = raiz;    //O(1)
            for(int i=0;i<carrera.length();i++){    //Ciclo: O(|c|)
                while(actual.valor != carrera.charAt(i)){    //Ciclo: O(1)    //Guarda: O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(i<(carrera.length()-1)){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            actual.materias.agregarDocente(docente, materia);    //O(|m|)
        }
    }

    public int[] plantelDocente(String materia, String carrera){    //Comp de la función: O(|c|+|m|)
        if(this.perteneceCarrera(carrera)){    //Peor caso    //O(|c|)
            Nodo actual = raiz;        //O(1)
            for(int i=0;i<carrera.length();i++){    //Ciclo:O(|c|)    //Guarda:O(1)
                while(actual.valor != carrera.charAt(i)){    //Ciclo: O(1)    //Guarda: O(1)
                    actual = actual.hermano;    //O(1)
                }
                if(i<(carrera.length()-1)){    //O(1)
                    actual = actual.hijo;    //O(1)
                }
            }
            return actual.materias.plantelDocente(materia);    //O(|m|)
        }else{      //Mejor caso  
            return null;    //O(1)
        }
    }

    public String[] carreras(){                     //Funcion completa: O(Sumatoria de |c|, |C| cantidad de veces) 
        String[] res = new String[cantCarreras];        //O(1)
        String pref = "";                               //O(1)
        Nodo actual = raiz;                             //O(1)
        iteradorInt iterador = new iteradorInt();       //O(1)
        carreras(actual, pref, res, iterador);                    //Esta funcion + el ciclo de abajo: O(Sumatoria de |c|, |C| cantidad de veces) 
        while(actual.hermano!=null){
            carreras(actual.hermano,pref,res,iterador);
            actual = actual.hermano;
        }
        return res;                                     //O(1)
    }

    //Complejidad de void carreras(n,prefijo,res):
    //esta funcion es recursiva e itera una vez por cada nodo perteneciente al trie. por lo que su complejidad estaria acotada por la cantidad total de nodos.
    //en el peor caso imaginable un trie tiene tantos nodos como caracteres totales tengan los string almacenados en él.
    //Que, en este caso, seria igual a la sumatoria del largo de los nombres de todas las carreras.
    //Concluyendo que la funcion tiene complejidad O(Sumatoria de |c|, |C| cantidad de veces) 
    
    public void carreras(Nodo n, String prefijo, String[] res, iteradorInt iterador){
        if(n == null){
            return;
        }else{
            if(n.def == true){
                String carrera = prefijo + n.valor;
                res[iterador.posicion] = carrera;
                iterador.posicion++;
            }else{
                Nodo hijo = n.hijo;
                carreras(hijo,prefijo + n.valor,res,iterador);
                while(hijo.hermano!=null){
                    carreras(hijo.hermano,prefijo + n.valor,res,iterador);
                    hijo = hijo.hermano;
                }
            }
        }
    }

    private class iteradorInt {
        private int posicion;

        iteradorInt(){       //O(1)
            posicion = 0;
        }
        
    }

    public String[] materias(String carrera){               //funcion completa: O(|c| + Sumatoria de |m_c|, para todo m_c perteneciente a M_c)
        if(this.perteneceCarrera(carrera)){                     //O(|c|)
            Nodo actual = raiz;                                 //O(1)
            for(int i=0;i<carrera.length();i++){                //Ciclo: Sumatoria desde k = 0 hasta (en el peor caso) |c| de 2*O(1) = |c|*2*O(1) = O(|c|) //Guarda: O(1)
                while(actual.valor != carrera.charAt(i)){           //Ciclo: Sumatoria desde k = 0 hasta (en el peor de los casos) 255 (todo el abecedario ASCII) de O(1) = 256*O(1) = O(1) //Guarda: O(1)
                    actual = actual.hermano;                            //O(1)
                }
                if(i<carrera.length()-1){                           //If completo: O(1)
                    actual = actual.hijo;                           
                }
            }
            return actual.materias.materias();                  //O(Sumatoria de |m_c|, para todo m_c perteneciente a M_c)
        }else{
            return null;
        }
    }

    public void eliminarMateria(String carrera, String materia, trieAlumnos alumnos){   //O(|c| + Sumatoria de |n|, para todo n perteneciente a N_m + |m| + E_m)
        if(perteneceCarrera(carrera)){                                                  //O(|c|)
            Nodo actual = raiz;                                                         //O(1)
            for(int i=0;i<carrera.length();i++){                                        //Ciclo: Sumatoria desde k = 0 hasta (en el peor caso) |c| de 2*O(1) = |c|*2*O(1) = O(|c|) //Guarda: O(1)
                while(actual.valor != carrera.charAt(i)){                                   //Ciclo: Sumatoria desde k = 0 hasta (en el peor de los casos) 255 (todo el abecedario ASCII) de O(1) = 256*O(1) = O(1) //Guarda: O(1)
                    actual = actual.hermano;                                                    //O(1)
                }
                if(i<carrera.length()-1){                                               //If completo: O(1)
                    actual = actual.hijo;  
                }
            }
            actual.materias.eliminarMateria(materia, alumnos);                          //O(Sumatoria de |n|, para todo n perteneciente a N_m + |m| + E_m)
        }
    }

}
