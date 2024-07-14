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
        private String nombre;

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
        if (padre.nombre==null){padre.nombre = new String(carrera);}    //O(1)
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

    public void insertarMateria(String carrera, String materia){ //Complejidad de la función: O(|c| + |m|)
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

    public void insertarAlumno(String carrera, String materia, String alumno){
        if(perteneceCarrera(carrera)){
            Nodo actual = raiz;
            for(int i=0;i<carrera.length();i++){
                while(actual.valor != carrera.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(carrera.length()-1)){
                    actual = actual.hijo;
                }
            }
            actual.materias.insertarAlumno(materia, alumno);
        }
    }

    public int inscriptosEnMateria(String carrera, String materia){
        if(perteneceCarrera(carrera)){
            Nodo actual = raiz;
            for(int i=0;i<carrera.length();i++){
                while(actual.valor != carrera.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(carrera.length()-1)){
                    actual = actual.hijo;
                }
            }
            return actual.materias.inscriptos(materia);
        }else{
            return 0;
        }
    }

    public void adjuntarInfoMateriasIguales(String carrera, String materia, InfoMateria info){
        if(perteneceCarrera(carrera)){
            Nodo actual = raiz;
            for(int i=0;i<carrera.length();i++){
                while(actual.valor != carrera.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<carrera.length()-1){
                    actual = actual.hijo;
                }
            }
            actual.materias.adjuntarInfoMateriasIguales(materia, info);
        }
    }

    public void insertarInfo(InfoMateria infoMateria){
        for (ParCarreraMateria parCarreraMateria : infoMateria.getParesCarreraMateria()){
            this.insertarCarrera(parCarreraMateria.getCarrera());
            this.insertarMateria(parCarreraMateria.getCarrera(), parCarreraMateria.getNombreMateria());
            this.adjuntarInfoMateriasIguales(parCarreraMateria.getCarrera(),
                                             parCarreraMateria.getNombreMateria(),
                                             infoMateria);
        }
    }

    public InfoMateria materiasIguales(String carrera, String materia){
        if(this.perteneceCarrera(carrera)){
            Nodo actual = raiz;
            for(int i=0;i<carrera.length();i++){
                while(actual.valor != carrera.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(carrera.length()-1)){
                    actual = actual.hijo;
                }
            }
            return actual.materias.materiasIguales(materia);
        }else{
            return null;
        }
    }

    public void agregarDocente(CargoDocente docente, String carrera, String materia){
        if(perteneceCarrera(carrera)){
            Nodo actual = raiz;
            for(int i=0;i<carrera.length();i++){
                while(actual.valor != carrera.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(carrera.length()-1)){
                    actual = actual.hijo;
                }
            }
            actual.materias.agregarDocente(docente, materia);
        }
    }

    public int[] plantelDocente(String materia, String carrera){
        if(this.perteneceCarrera(carrera)){
            Nodo actual = raiz;
            for(int i=0;i<carrera.length();i++){
                while(actual.valor != carrera.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(carrera.length()-1)){
                    actual = actual.hijo;
                }
            }
            return actual.materias.plantelDocente(materia);
        }else{
            return null;
        }
    }

    public String[] carreras(){
        String[] res = new String[cantCarreras];
        String pref = "";
        Nodo actual = raiz;
        carreras(actual, pref, res);
        while(actual.hermano!=null){
            carreras(actual.hermano,pref,res);
            actual = actual.hermano;
        }
        return res;
    }

    public void carreras(Nodo n, String prefijo, String[] res){
        if(n == null){
            return;
        }else{
            if(n.def == true){
                String carrera = prefijo + n.valor;
                res[ultimoElem(res)] = carrera;
            }else{
                Nodo hijo = n.hijo;
                carreras(hijo,prefijo + n.valor,res);
                while(hijo.hermano!=null){
                    carreras(hijo.hermano,prefijo + n.valor,res);
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

    public String[] materias(String carrera){
        if(this.perteneceCarrera(carrera)){
            Nodo actual = raiz;
            for(int i=0;i<carrera.length();i++){
                while(actual.valor != carrera.charAt(i)){
                    actual = actual.hermano;
                }
                if(i<(carrera.length()-1)){
                    actual = actual.hijo;
                }
            }
            return actual.materias.materias();
        }else{
            return null;
        }
    }

    public void eliminarMateria(String carrera, String materia, trieAlumnos alumnos){    //Complejidad de la función: 
        if(perteneceCarrera(carrera)){                                                       //O(|c|)
            Nodo actual = raiz;                                                              //O(1)
            for(int i=0;i<carrera.length();i++){                                             //Ciclo: //Guarda: 3*O(1) = O(1)
                while(actual.valor != carrera.charAt(i)){                                        //Ciclo:
                    actual = actual.hermano;                                                         //O(1)
                }
                if(i<(carrera.length()-1)){                                                      //If completo: 2*O(1) = O(1)
                    actual = actual.hijo;                                                            //O(1)
                }
            }
            actual.materias.eliminarMateria(this, alumnos, materia);                         //????????????????????????????????????????????
        }
    }

}
