package negocio;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import java.util.List;
import java.util.function.Predicate;
import modelo.UnidadAcademica;

public class GestorSistemaAcademico {

    private final ArbolGeneral<NodoAcademico> arbol = new ArbolGeneral<>(
            (padre, hijo) -> padre.getUnidad().getTipo().puedeTenerHijo(hijo.getUnidad().getTipo()));

    private static Predicate<NodoAcademico> porCodigo(String codigo) {
        String buscado = codigo == null ? "" : codigo.trim().toUpperCase();
        return nodo -> nodo.getUnidad().getCodigo().equals(buscado);
    }

    public boolean registrarRaiz(UnidadAcademica unidad) {
        if (!arbol.esVacio() || unidad.getTipo() != TipoUnidad.UNIVERSIDAD) {
            return false;
        }
        arbol.crearRaiz(new NodoAcademico(unidad));
        return true;
    }

    public boolean agregarUnidad(String codigoPadre, UnidadAcademica nueva) {
        if (arbol.buscar(porCodigo(nueva.getCodigo())) != null) {
            return false;
        }
        return arbol.agregarHijo(porCodigo(codigoPadre), new NodoAcademico(nueva));
    }

    public boolean eliminarUnidad(String codigo) {
        return arbol.eliminar(porCodigo(codigo));
    }

    public UnidadAcademica buscarUnidad(String codigo) {
        var nodo = arbol.buscar(porCodigo(codigo));
        return nodo == null ? null : nodo.getDato().getUnidad();
    }

    public GestorEstudiantes estudiantesDe(String codigoParalelo) {
        var nodo = arbol.buscar(porCodigo(codigoParalelo));
        if (nodo == null) {
            throw new IllegalArgumentException("la unidad " + codigoParalelo + " no existe");
        }
        if (!nodo.getDato().tieneEstudiantes()) {
            throw new IllegalArgumentException("la unidad " + codigoParalelo + " no es un paralelo");
        }
        return nodo.getDato().getEstudiantes();
    }

    public int nivel(String codigo) {
        return arbol.nivelDe(porCodigo(codigo));
    }

    public int grado(String codigo) {
        return arbol.gradoDe(porCodigo(codigo));
    }

    public int altura() {
        return arbol.altura();
    }

    public int contarUnidades() {
        return arbol.contarNodos();
    }

    public int contarHojas() {
        return arbol.contarHojas();
    }

    public int totalEstudiantes() {
        int total = 0;
        for (NodoAcademico nodo : arbol.recorrer(TipoRecorrido.PREORDEN)) {
            if (nodo.tieneEstudiantes()) {
                total += nodo.getEstudiantes().total();
            }
        }
        return total;
    }

    public double promedioGeneral() {
        double suma = 0.0;
        int cantidad = 0;
        for (NodoAcademico nodo : arbol.recorrer(TipoRecorrido.PREORDEN)) {
            if (nodo.tieneEstudiantes()) {
                suma += nodo.getEstudiantes().getArbol().sumar(e -> e.getPromedio());
                cantidad += nodo.getEstudiantes().total();
            }
        }
        return cantidad == 0 ? 0.0 : suma / cantidad;
    }

    public List<NodoAcademico> listar(TipoRecorrido tipo) {
        return arbol.recorrer(tipo);
    }

    public String jerarquia() {
        return arbol.mostrarJerarquia();
    }

    public ArbolBinario<NodoAcademico> aBinario() {
        return arbol.aBinario();
    }

    public ArbolGeneral<NodoAcademico> getArbol() {
        return arbol;
    }
}
