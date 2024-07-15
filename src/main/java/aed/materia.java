package aed;
import java.util.*;

import aed.SistemaSIU.CargoDocente;

public class materia {
    private String nombre;
    private ArrayList<String> equivalentes;
    private ArrayList<trieMaterias> triesEquivalentes; 
    private trieAlumnos trieAlumnos;
    private int[] docentes;

    public materia(String nombre, ParCarreraMateria[] pares, trieCarreras trieCarreras, trieAlumnos trieAlumnos,int[] docentes){    //Funcion completa: max{O(1),O(|N_m|)} = O(|N_m|)
        this.equivalentes = new ArrayList<>();                                                                                          //O(1)
        this.triesEquivalentes = new ArrayList<trieMaterias>();                                                                         //O(1)
        for (ParCarreraMateria par : pares){                                                                                            //Ciclo: O(|N_m|) pues se repite funciones con complejidad O(1) |N_m| cantidad de veces
            this.equivalentes.add(par.getNombreMateria());                                                                                  //O(1)
            this.triesEquivalentes.add(trieCarreras.trieMaterias(par.getCarrera()));                                                        //O(1)
        }                                                                                                                               
        this.nombre = nombre;                                                                                                           //O(1)
        this.trieAlumnos = trieAlumnos;                                                                                                 //O(1)
        this.docentes = docentes;                                                                                                       //O(1)
    }

    public String getNombre(){    //Funcion completa: O(1)
        return this.nombre;
    }

    public void insertarTrie(trieMaterias trie){    //Funcion completa: O(1)
        this.triesEquivalentes.add(trie);
    }

    public void insertarNombreEq(String nombre){    //Funcion completa: O(1)
        this.equivalentes.add(nombre);
    }

    public void eliminarMateria(){                                  //Funcion completa: O(Sumatoria de |n|, para todo n perteneciente a N_m + |m|)
        for(int i=0;i<equivalentes.size();i++){                         //Ciclo: O(Sumatoria de |n|, para todo n perteneciente a N_m + |m|)
            String materia = this.equivalentes.get(i);                      //O(1)
            this.triesEquivalentes.get(i).eliminarMateria(materia);         //O(|m|) o O(|n|) depende el caso
        }
    }

    public void eliminarMateriaAAlumno(trieAlumnos trieAlumnos){    //Funcion completa: O(E)
        String[] alumnos = this.trieAlumnos.alumnos();                  //O(1)
        for (String alumno : alumnos) {                                 //Ciclo: O(Sumatoria de 1, E cantidad de veces)
            trieAlumnos.eliminarMateriaAAlumno(alumno);                     //O(1)
        }
    }

    public void insertarAlumno(String alumno){      //Funcion completa: O(1)
        this.trieAlumnos.insertarAlumno(alumno);        //O(1)
    }

    public int cantAlumnos(){                       //Funcion completa: O(1)
        return this.trieAlumnos.cantAlumnos();          //O(1)
    }

    public void agregarDocente(CargoDocente docente){       //Funcion completa: O(1)
        switch (docente) {
            case AY2:
                this.docentes[3] = this.docentes[3] + 1;
                break;
            case AY1:
                this.docentes[2] = this.docentes[2] + 1;
                break;
            case JTP:
                this.docentes[1] = this.docentes[1] + 1;
                break;
            default:
                this.docentes[0] = this.docentes[0] + 1;
                break;
        }
    }

    public int[] plantelDocente(){      //Funcion completa: O(1)
        return this.docentes;
    }
}
