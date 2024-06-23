
public void cerrarMateria(SistemaSIU sistema, String carrera, String nombreMateria){
        cerrarMateria(raiz, sistema, carrera, nombreMateria);
    }

    private void cerrarMateria(Nodo nodo, SistemaSIU sistema, String carrera, String nombreMateria){
        if (nodo == null) return;

       
        if (nodo.valor == nombreMateria.charAt(0)) {
            if (nombreMateria.length() == 1 && nodo.def) {
               
                nodo.def = false;  
                sistema.cerrarMateriaParaCarrera(carrera, nombreMateria); 
            } else if (nodo.hijo != null) {
                cerrarMateria(nodo.hijo, sistema, carrera, nombreMateria.substring(1));
            }
        }

        cerrarMateria(nodo.hermano, sistema, carrera, nombreMateria);
    }



