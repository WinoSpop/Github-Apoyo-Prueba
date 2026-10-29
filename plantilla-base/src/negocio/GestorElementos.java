package negocio;

import enums.TipoRecorrido;
import java.util.List;
import modelo.Elemento;

public class GestorElementos {

    private final ArbolBinarioBusqueda<String, Elemento> arbol =
            new ArbolBinarioBusqueda<>(Elemento::getCodigo);

    private static String normalizar(String codigo) {
        return codigo == null ? "" : codigo.trim().toUpperCase();
    }

    public boolean registrar(Elemento elemento) {
        return arbol.insertar(elemento);
    }

    public Elemento buscar(String codigo) {
        return arbol.buscar(normalizar(codigo));
    }

    public boolean modificar(String codigo, String nombre, double valor) {
        String clave = normalizar(codigo);
        if (!arbol.existe(clave)) {
            return false;
        }
        new Elemento(clave, nombre, valor);
        return arbol.modificar(clave, e -> {
            e.setNombre(nombre);
            e.setValor(valor);
        });
    }

    public boolean eliminar(String codigo) {
        return arbol.eliminar(normalizar(codigo));
    }

    public List<Elemento> listar(TipoRecorrido tipo) {
        return arbol.recorrer(tipo);
    }

    public int total() {
        return arbol.contarNodos();
    }

    public int altura() {
        return arbol.altura();
    }

    public int hojas() {
        return arbol.contarHojas();
    }

    public Elemento menor() {
        return arbol.menor();
    }

    public Elemento mayor() {
        return arbol.mayor();
    }

    public double promedioValor() {
        return arbol.promedio(Elemento::getValor);
    }

    public String estructura() {
        return arbol.mostrarEstructura(Elemento::getCodigo);
    }

    public String traza(TipoRecorrido tipo) {
        return arbol.trazaRecorrido(tipo, Elemento::getCodigo);
    }

    public ArbolBinarioBusqueda<String, Elemento> getArbol() {
        return arbol;
    }
}
