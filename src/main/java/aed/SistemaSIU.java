package aed;

public class SistemaSIU {

    private trieCarreras Carreras;
    // private trieAlumnos Alumnos;
    // private materiasDeCarrera materiasDeCarrera;

    public static void main(String[] args){

        trieCarreras carreras = new trieCarreras();

        System.out.println("tests insertar/pertenece en trie carreras");
        carreras.insertarCarrera("ciencias de datos");
        carreras.insertarCarrera("ciencias de la computacion");
        carreras.insertarCarrera("diseño de indumentaria");

        System.out.println(carreras.perteneceCarrera("ciencias de datos"));
        System.out.println(carreras.perteneceCarrera("ciencias de la computacion"));
        System.out.println(carreras.perteneceCarrera("diseño de indumentaria"));

        System.out.println("tests insertar/pertenece materia en trie carreras");
        carreras.insertarMateria("ciencias de datos", "intro");
        carreras.insertarMateria("ciencias de datos", "algo2/exalgo1");
        carreras.insertarMateria("ciencias de la computacion", "programacion");
        carreras.insertarMateria("ciencias de la computacion", "analisis 2");
        carreras.insertarMateria("diseño de indumentaria","materiales 1");
        carreras.insertarMateria("diseño de indumentaria","materiales 2");

        System.out.println(carreras.perteneceMaterias("ciencias de datos","intro"));
        System.out.println(carreras.perteneceMaterias("ciencias de datos","algo2/exalgo1"));
        System.out.println(carreras.perteneceMaterias("ciencias de la computacion", "programacion"));
        System.out.println(carreras.perteneceMaterias("ciencias de la computacion", "analisis 2"));
        System.out.println(carreras.perteneceMaterias("diseño de indumentaria","materiales 1"));
        System.out.println(carreras.perteneceMaterias("diseño de indumentaria","materiales 2"));

        System.out.println("tests eliminar carreras");
        carreras.eliminarCarrera("diseño de indumentaria");
        System.out.println(carreras.perteneceCarrera("diseño de indumentaria"));

    }
    

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void inscribir(String estudiante, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int[] plantelDocente(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public boolean excedeCupo(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }
}
