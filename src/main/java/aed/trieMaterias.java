package aed;
import java.util.*;

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
        if (padre.def==false){this.cantMaterias++;}
        padre.def = true;
        if(padre.alumnos==null){padre.alumnos = new trieAlumnos();}
        if(padre.docentes==null){padre.docentes = new int[] {0,0,0,0};}
    }

    public boolean perteneceMaterias(String materia){
        if(raiz == null){ //no hay materias
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
        String[] res = new String[cantMaterias];
        String pref = "";
        Nodo actual = raiz;
        materias(actual, pref, res);
        while(actual.hermano!=null){
            materias(actual.hermano,pref,res);
            actual = actual.hermano;
        }
        return res;
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
            if (i == materia.length() && padre.valor == materia.charAt(materia.length()-1) && padre.def == true) { // si materia pertenece a trieCarreras...
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
                cantMaterias--;
            } // si no pertenece, no hago nada
        } // si no hay materias, no hago nada
    }



}
