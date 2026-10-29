package negocio;

import enums.TipoUnidad;
import modelo.UnidadAcademica;

public class NodoAcademico {

    private final UnidadAcademica unidad;
    private final GestorEstudiantes estudiantes;

    public NodoAcademico(UnidadAcademica unidad) {
        if (unidad == null) {
            throw new IllegalArgumentException("el nodo necesita una unidad académica");
        }
        this.unidad = unidad;
        this.estudiantes = unidad.getTipo() == TipoUnidad.PARALELO ? new GestorEstudiantes() : null;
    }

    public UnidadAcademica getUnidad() {
        return unidad;
    }

    public GestorEstudiantes getEstudiantes() {
        return estudiantes;
    }

    public boolean tieneEstudiantes() {
        return estudiantes != null;
    }

    @Override
    public String toString() {
        if (estudiantes == null) {
            return unidad.toString();
        }
        return unidad + " [" + estudiantes.total() + " estudiantes]";
    }
}
