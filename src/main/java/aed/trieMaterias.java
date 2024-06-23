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
        padre.def = true;
        this.cantMaterias++;
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

}
