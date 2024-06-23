package aed;

public class SistemaSIU {

    private trieCarreras Carreras;
    private trieAlumnos AlumnosNroMaterias;
    // private materiasDeCarrera materiasDeCarrera;


    public static void main(String[] args){
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

        System.out.println("test carreras");
        String[] car = sistema.carreras();
        System.out.println(car[0]);
        System.out.println(car[1]);
        System.out.println(car[2]);
        System.out.println(car[3]);
        System.out.println(car[4]);
        System.out.println(car[5]);

        System.out.println("test materias");
        String[] mat = sistema.materias("Ciencias de Datos");
        System.out.println(mat[0]);
        System.out.println(mat[1]);
        System.out.println(mat[2]);
        System.out.println(mat[3]);
        System.out.println(mat[4]);
        System.out.println(mat[5]);
        System.out.println(mat[6]);

        System.out.println("test intro compu");
        InfoMateria[] infoMaterias2 = new InfoMateria[] {
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias de la Computación", "Intro a la Programación")}),
            new InfoMateria(new ParCarreraMateria[] {new ParCarreraMateria("Ciencias de la Computación", "Algoritmos")})
        };
        String[] estudiantes2 = new String[] {"123/23", "321/24", "122/99", "314/81", "391/18", "478/19", "942/20", "291/18", "382/19", "892/22", "658/13", "217/12", "371/11", "294/20"};

        SistemaSIU sistema2 = new SistemaSIU(infoMaterias2, estudiantes2);

        String[] com = sistema2.materias("Ciencias de la Computación");
        System.out.println(com[0]);
        System.out.println(com[1]);

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
        return this.Carreras.carreras();
    }

    public String[] materias(String carrera){
        return this.Carreras.materias(carrera);
    }

    public int materiasInscriptas(String estudiante){
        return this.AlumnosNroMaterias.materiasInscriptas(estudiante);
    }
}
