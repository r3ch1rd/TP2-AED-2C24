package aed;

public class SistemaSIU {

    private trieCarreras Carreras;
    private trieAlumnos AlumnosNroMaterias;
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

        System.out.println("tests inscribir alumno");
        carreras.insertarAlumno("ciencias de datos", "intro", "001/01");
        carreras.insertarAlumno("ciencias de datos", "intro", "002/01");
        carreras.insertarAlumno("ciencias de datos", "intro", "003/01");
        System.out.println(3 == carreras.inscriptosEnMateria("ciencias de datos", "intro"));
        System.out.println(0 == carreras.inscriptosEnMateria("ciencias de la computacion", "programacion"));
        

        System.out.println("test sobre sistema");
        
        InfoMateria[] infoMaterias = new InfoMateria[] {
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias de la Computación", "Intro a la Programación"), new ParCarreraMateria("Ciencias de Datos", "Algoritmos1")}),
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias de la Computación", "Algoritmos"), new ParCarreraMateria("Ciencias de Datos", "Algoritmos2")}),
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias de la Computación", "Técnicas de Diseño de Algoritmos"), new ParCarreraMateria("Ciencias de Datos", "Algoritmos3")}),
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias de la Computación", "Análisis I"), new ParCarreraMateria("Ciencias de Datos", "Análisis I"), new ParCarreraMateria("Ciencias Físicas", "Matemática 1"), new ParCarreraMateria("Ciencias Químicas", "Análisis Matemático I"), new ParCarreraMateria("Ciencias Matemáticas", "Análisis I") }),
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias Biológicas", "Química General e Inorgánica 1"), new ParCarreraMateria("Ciencias Químicas", "Química General")}),
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias Matemáticas", "Análisis II"), new ParCarreraMateria("Ciencias de Datos", "Análisis II"), new ParCarreraMateria("Ciencias Físicas", "Matemática 3"), new ParCarreraMateria("Ciencias Químicas", "Análisis Matemático II")})
        };
        String[] estudiantes = new String[] {"123/23", "321/24", "122/99", "314/81", "391/18", "478/19", "942/20", "291/18", "382/19", "892/22", "658/13", "217/12", "371/11", "294/20"};

        SistemaSIU sistema = new SistemaSIU(infoMaterias, estudiantes);
        
        System.out.println("test docentes sobre sistema");

        sistema.agregarDocente(CargoDocente.PROF, "Ciencias de la Computación", "Intro a la Programación");
        sistema.agregarDocente(CargoDocente.PROF, "Ciencias de la Computación", "Intro a la Programación");
        sistema.agregarDocente(CargoDocente.AY2, "Ciencias de la Computación", "Intro a la Programación");
        sistema.agregarDocente(CargoDocente.AY2, "Ciencias de la Computación", "Intro a la Programación");
        sistema.agregarDocente(CargoDocente.AY1, "Ciencias de la Computación", "Intro a la Programación");


        int[] plant2 = sistema.plantelDocente("Algoritmos1", "Ciencias de Datos");
        System.out.println("Algoritmos1 Ciencias de Datos");
        System.out.println(plant2[0]);
        System.out.println(plant2[1]);
        System.out.println(plant2[2]);
        System.out.println(plant2[3]);

        int[] plant = sistema.plantelDocente("Intro a la Programación", "Ciencias de la Computación");
        System.out.println("Intro a la Programación Ciencias de la Computación");
        System.out.println(plant[0]);
        System.out.println(plant[1]);
        System.out.println(plant[2]);
        System.out.println(plant[3]);


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
        
        this.AlumnosNroMaterias = new trieAlumnos();
        for (String alumno : libretasUniversitarias){
            this.AlumnosNroMaterias.insertarAlumno(alumno);
        }

        this.Carreras = new trieCarreras();
        for (InfoMateria infoMateria : infoMaterias){
            this.Carreras.insertarInfo(infoMateria);
        }
    }

    public void inscribir(String estudiante, String carrera, String materia){
        this.Carreras.insertarAlumno(carrera, materia, estudiante);
        this.AlumnosNroMaterias.agregarMateriaAAlumno(estudiante);
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){

        InfoMateria infomateria = this.Carreras.materiasIguales(carrera,materia);
        for (ParCarreraMateria parCarreraMateria : infomateria.getParesCarreraMateria()){
            this.Carreras.agregarDocente(cargo, 
                                         parCarreraMateria.getCarrera(),
                                         parCarreraMateria.getNombreMateria());
        }
    }

    public int[] plantelDocente(String materia, String carrera){
        return this.Carreras.plantelDocente(materia,carrera);
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        int res = 0;
        InfoMateria infomateria = this.Carreras.materiasIguales(carrera,materia);
        for (ParCarreraMateria parCarreraMateria : infomateria.getParesCarreraMateria()){
            res += this.Carreras.inscriptosEnMateria(parCarreraMateria.getCarrera(),
                                                     parCarreraMateria.getNombreMateria());
        }
        return res; 
    }

    public boolean excedeCupo(int inscriptos, int[] docentes, String materia, String carrera){
        boolean res = false;
        if (docentes[0]*250<inscriptos){res = true;}
        if (docentes[1]*100<inscriptos){res = true;}
        if (docentes[2]*20<inscriptos){res = true;}
        if (docentes[3]*30<inscriptos){res = true;}
        return res;
    }

    public boolean excedeCupo(String materia, String carrera){
        return excedeCupo(this.inscriptos(materia, carrera),
                          this.plantelDocente(materia, carrera),
                          materia, carrera);
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        return this.AlumnosNroMaterias.materiasInscriptas(estudiante);
    }
}
