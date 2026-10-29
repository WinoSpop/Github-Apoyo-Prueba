package negocio;

import enums.TipoRecorrido;
import java.util.List;
import java.util.function.Predicate;
import modelo.Elemento;

public class GestorJerarquia {

    private final ArbolGeneral<Elemento> arbol = new ArbolGeneral<>();

    private static Predicate<Elemento> porCodigo(String codigo) {
        String buscado = codigo == null ? "" : codigo.trim().toUpperCase();
        return elemento -> elemento.getCodigo().equals(buscado);
    }

    public boolean registrarRaiz(Elemento elemento) {
        if (!arbol.esVacio()) {
            return false;
        }
        arbol.crearRaiz(elemento);
        return true;
    }

    public boolean agregar(String codigoPadre, Elemento nuevo) {
        if (existe(nuevo.getCodigo())) {
            return false;
        }
        return arbol.agregarHijo(porCodigo(codigoPadre), nuevo);
    }

    public boolean existe(String codigo) {
        return arbol.buscar(porCodigo(codigo)) != null;
    }

    public Elemento buscar(String codigo) {
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

    public List<Elemento> camino(String codigo) {
        return arbol.camino(porCodigo(codigo));
    }

    public List<Elemento> listar(TipoRecorrido tipo) {
        return arbol.recorrer(tipo);
    }

    public String jerarquia() {
        return arbol.mostrarJerarquia();
    }

    public ArbolBinario<Elemento> aBinario() {
        return arbol.aBinario();
    }

    public ArbolGeneral<Elemento> getArbol() {
        return arbol;
    }
}
