package negocio;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import java.util.List;
import java.util.function.Predicate;
import modelo.UnidadAcademica;

public class GestorOrganizacion {

    private final ArbolGeneral<UnidadAcademica> arbol =
            new ArbolGeneral<>((padre, hijo) -> padre.getTipo().puedeTenerHijo(hijo.getTipo()));

    private static Predicate<UnidadAcademica> porCodigo(String codigo) {
        String buscado = codigo == null ? "" : codigo.trim().toUpperCase();
        return unidad -> unidad.getCodigo().equals(buscado);
    }

    public boolean registrarRaiz(UnidadAcademica unidad) {
        if (!arbol.esVacio() || unidad.getTipo() != TipoUnidad.UNIVERSIDAD) {
            return false;
        }
        arbol.crearRaiz(unidad);
        return true;
    }

    public boolean agregar(String codigoPadre, UnidadAcademica nueva) {
        if (existe(nueva.getCodigo())) {
            return false;
        }
        return arbol.agregarHijo(porCodigo(codigoPadre), nueva);
    }

    public boolean existe(String codigo) {
        return arbol.buscar(porCodigo(codigo)) != null;
    }

    public UnidadAcademica buscar(String codigo) {
        var nodo = arbol.buscar(porCodigo(codigo));
        return nodo == null ? null : nodo.getDato();
    }

    public boolean eliminar(String codigo) {
        return arbol.eliminar(porCodigo(codigo));
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

    public int contarNodos() {
        return arbol.contarNodos();
    }

    public int contarHojas() {
        return arbol.contarHojas();
    }

    public List<UnidadAcademica> camino(String codigo) {
        return arbol.camino(porCodigo(codigo));
    }

    public List<UnidadAcademica> listar(TipoRecorrido tipo) {
        return arbol.recorrer(tipo);
    }

    public String jerarquia() {
        return arbol.mostrarJerarquia();
    }

    public ArbolBinario<UnidadAcademica> aBinario() {
        return arbol.aBinario();
    }

    public ArbolGeneral<UnidadAcademica> getArbol() {
        return arbol;
    }
}
