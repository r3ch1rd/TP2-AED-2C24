package aed;

// Invariante de representación:
//  pred InvRepSistemaSIU (s: SistemaSIU)
//      {esTrie(s.Carreras) == true && esTrie(s.AlumnosNroMaterias) == true}
//  
//  esTrie(e) = esArbol(e) && todosNodosUtiles(e) && arbolEnOrdenLexicografico(e)
// 
//  esArbol(e) = todos los nodos, salvo la raiz, tienen un solo padre
//  todosNodosUtiles(e) = todo nodo no definido tiene hijos 
//  arbolEnOrdenLexicografico(e) = toda lista de hijos de un nodo está ordenada en orden lexicográfico


public class SistemaSIU {

    private trieCarreras Carreras;
    private trieAlumnos AlumnosNroMaterias;

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    // Complejidad
// asiganar AlumnosNroMaterias = new trieAlumnos es O(1) pues vrear un trieAlumnos es O(1)    
// insetarAlumno tiene una complejidad O(1) pues el String alumno es acotado
// por lo que insetar todos los alumnos al trie AlumnosNroMaterias es de O(E).
// crear un trieCarreras tambien es O(1)
//  
    

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

    // Complejidad
// insetarAlumno(estudiante,carrera,materia) tiene una complejidad O(|carrera|+|materia|)
// pues se recorre el trieCarreras O(|carrera|), luego el trieMaterias O(|materia|) y luego el
// trieAlumnos O(1) pues el String estudiante es acotado.
// agregarMateriAAlumno es recorrer el trie AlumnosNroMaterias, lo es O(1).
// La suma da la complejidad de inscribir() que es O(|carrera|+|materia|)

    public void inscribir(String estudiante, String carrera, String materia){
        this.Carreras.insertarAlumno(carrera, materia, estudiante);
        this.AlumnosNroMaterias.agregarMateriaAAlumno(estudiante);
    }

    // Complejidad
// materiasIguales(carrera,materia) es O(|carrera|+|materia|) pues es recorrer el trieCarreras y 
// el trieMaterias.
// agregarDocente(cargo,carrera,materia) es O(|carrera|+|materia|) pues también es recorrer el
// trieCarreras y el trieMaterias, más agregar un docente O(1).
// Luego agregarDocente es O(|carrera|+|materia|).

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        this.Carreras.agregarDocente(cargo,carrera,materia);
    }

    // Complejidad
// plantelDocente(materia,carrera) es O(|carrera|+|materia|) pues es recorrer el trieCarreras y 
// el trieMaterias, más devolver la lista de int docentes O(1).

    public int[] plantelDocente(String materia, String carrera){
        return this.Carreras.plantelDocente(materia,carrera);
    }

    //Complejidad
//O(|c| + Sumatoria de |n|, para todo n perteneciente a N_m + |m| + E_m)

    public void cerrarMateria(String materia, String carrera){        //O(|c| + Sumatoria de |n|, para todo n perteneciente a N_m + |m| + E_m)
        this.Carreras.eliminarMateria(carrera, materia, AlumnosNroMaterias);
    }

    //Complejidad
//O(|c|+|m|)

    public int inscriptos(String materia, String carrera){            // O(|c|+|m|)
        return this.Carreras.inscriptosEnMateria(carrera, materia); 
    }

    // Complejidad
// excedeCupo es O(1) pues define una variable y chequea 4 
// condiciones (en caso de cimplirse, cambia valor) y todo es es O(1).

    public boolean excedeCupo(int inscriptos, int[] docentes){
        boolean res = false;
        if (docentes[0]*250<inscriptos){res = true;}
        if (docentes[1]*100<inscriptos){res = true;}
        if (docentes[2]*20<inscriptos){res = true;}
        if (docentes[3]*30<inscriptos){res = true;}
        return res;
    }

    //Complejidad
// inscriptos es O(|C| + |m|)
// plantelDocente es O(|c| + |m|)
// Entonces excedeCupo ES O(2 * |c| + |m|) =  O(|c| + |m|)

    public boolean excedeCupo(String materia, String carrera){
        return excedeCupo(this.inscriptos(materia, carrera),
                          this.plantelDocente(materia, carrera));
    }

    //Complejidad
//    O(Sumatoria de |c|, |C| cantidad de veces)

    public String[] carreras(){
        return this.Carreras.carreras();
    }

    //Complejidad
//    O(|c| + Sumatoria de |m_c|, para todo m_c perteneciente a M_c) 
//    pues solo usa la función materias de trieCarreras

    public String[] materias(String carrera){
        return this.Carreras.materias(carrera);
    }

    //Complejidad
// materiasInscriptas(estudiante) es O(1) pues es recorrer el trie AlumnosNroMaterias con el String
// estudiante que está acotado.

    public int materiasInscriptas(String estudiante){
        return this.AlumnosNroMaterias.materiasInscriptas(estudiante);
    }
}
