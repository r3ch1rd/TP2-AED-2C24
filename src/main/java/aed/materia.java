package aed;
import java.util.*;

import aed.SistemaSIU.CargoDocente;

public class materia {
    private String nombre;
    private ArrayList<String> equivalentes;
    private ArrayList<trieMaterias> triesEquivalentes; 
    private trieAlumnos trieAlumnos;
    private int[] docentes;

    public materia(String nombre, ParCarreraMateria[] pares, trieCarreras trieCarreras, trieAlumnos trieAlumnos,int[] docentes){
        this.equivalentes = new ArrayList<>();
        this.triesEquivalentes = new ArrayList<trieMaterias>();
        for (ParCarreraMateria par : pares){
            this.equivalentes.add(par.getNombreMateria());
            this.triesEquivalentes.add(trieCarreras.trieMaterias(par.getCarrera()));
        }
        this.nombre = nombre;
        this.trieAlumnos = trieAlumnos;
        this.docentes = docentes;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void insertarTrie(trieMaterias trie){
        this.triesEquivalentes.add(trie);
    }

    public void insertarNombreEq(String nombre){
        this.equivalentes.add(nombre);
    }

    public void eliminarMateria(){
        for(int i=0;i<equivalentes.size();i++){
            String materia = this.equivalentes.get(i);
            this.triesEquivalentes.get(i).eliminarMateria(materia);
        }
    }

    public void eliminarMateriaAAlumno(trieAlumnos trieAlumnos){
        String[] alumnos = this.trieAlumnos.alumnos();
        for (String alumno : alumnos) {
            trieAlumnos.eliminarMateriaAAlumno(alumno);
        }
    }

    public void insertarAlumno(String alumno){
        this.trieAlumnos.insertarAlumno(alumno);
    }

    public int cantAlumnos(){
        return this.trieAlumnos.cantAlumnos();
    }

    public void agregarDocente(CargoDocente docente){
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

    public int[] plantelDocente(){
        return this.docentes;
    }
}
