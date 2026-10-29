package negocio;

import enums.TipoRecorrido;
import estructuras.Cola;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import modelo.Nodo;

public class ArbolBinario<T> {

    protected Nodo<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    void fijarRaiz(Nodo<T> nueva) {
        this.raiz = nueva;
    }

    public Nodo<T> crearRaiz(T dato) {
        if (raiz != null) {
            throw new IllegalStateException("el árbol ya tiene raíz");
        }
        raiz = new Nodo<>(dato);
        return raiz;
    }

    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        if (padre == null) {
            throw new IllegalArgumentException("el padre no puede ser null");
        }
        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException("el lugar izquierdo de " + padre.getDato() + " ya está ocupado");
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setIzquierdo(nuevo);
        return nuevo;
    }

    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        if (padre == null) {
            throw new IllegalArgumentException("el padre no puede ser null");
        }
        if (padre.getDerecho() != null) {
            throw new IllegalStateException("el lugar derecho de " + padre.getDato() + " ya está ocupado");
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setDerecho(nuevo);
        return nuevo;
    }

    public Nodo<T> buscarSi(Predicate<T> condicion) {
        return buscarSi(raiz, condicion);
    }

    private Nodo<T> buscarSi(Nodo<T> nodo, Predicate<T> condicion) {
        if (nodo == null) {
            return null;
        }
        if (condicion.test(nodo.getDato())) {
            return nodo;
        }
        Nodo<T> enIzquierda = buscarSi(nodo.getIzquierdo(), condicion);
        if (enIzquierda != null) {
            return enIzquierda;
        }
        return buscarSi(nodo.getDerecho(), condicion);
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
    }

    public int altura() {
        return altura(raiz);
    }

    protected int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    public int contarSi(Predicate<T> condicion) {
        return contarSi(raiz, condicion);
    }

    private int contarSi(Nodo<T> nodo, Predicate<T> condicion) {
        if (nodo == null) {
            return 0;
        }
        int propio = condicion.test(nodo.getDato()) ? 1 : 0;
        return propio + contarSi(nodo.getIzquierdo(), condicion) + contarSi(nodo.getDerecho(), condicion);
    }

    public List<T> filtrar(Predicate<T> condicion) {
        List<T> resultado = new ArrayList<>();
        for (T dato : recorrer(TipoRecorrido.INORDEN)) {
            if (condicion.test(dato)) {
                resultado.add(dato);
            }
        }
        return resultado;
    }

    public double sumar(ToDoubleFunction<T> valor) {
        return sumar(raiz, valor);
    }

    private double sumar(Nodo<T> nodo, ToDoubleFunction<T> valor) {
        if (nodo == null) {
            return 0.0;
        }
        return valor.applyAsDouble(nodo.getDato())
                + sumar(nodo.getIzquierdo(), valor)
                + sumar(nodo.getDerecho(), valor);
    }

    public double promedio(ToDoubleFunction<T> valor) {
        int total = contarNodos();
        if (total == 0) {
            return 0.0;
        }
        return sumar(valor) / total;
    }

    public int nivelDe(Predicate<T> condicion) {
        return nivelDe(raiz, condicion, 0);
    }

    private int nivelDe(Nodo<T> nodo, Predicate<T> condicion, int nivel) {
        if (nodo == null) {
            return -1;
        }
        if (condicion.test(nodo.getDato())) {
            return nivel;
        }
        int enIzquierda = nivelDe(nodo.getIzquierdo(), condicion, nivel + 1);
        if (enIzquierda != -1) {
            return enIzquierda;
        }
        return nivelDe(nodo.getDerecho(), condicion, nivel + 1);
    }

    public int gradoDe(Predicate<T> condicion) {
        Nodo<T> nodo = buscarSi(condicion);
        return nodo == null ? -1 : nodo.grado();
    }

    public List<T> camino(Predicate<T> condicion) {
        List<T> ruta = new ArrayList<>();
        if (!camino(raiz, condicion, ruta)) {
            return new ArrayList<>();
        }
        return ruta;
    }

    private boolean camino(Nodo<T> nodo, Predicate<T> condicion, List<T> ruta) {
        if (nodo == null) {
            return false;
        }
        ruta.add(nodo.getDato());
        if (condicion.test(nodo.getDato())) {
            return true;
        }
        if (camino(nodo.getIzquierdo(), condicion, ruta) || camino(nodo.getDerecho(), condicion, ruta)) {
            return true;
        }
        ruta.remove(ruta.size() - 1);
        return false;
    }

    public List<T> hojas() {
        List<T> lista = new ArrayList<>();
        hojas(raiz, lista);
        return lista;
    }

    private void hojas(Nodo<T> nodo, List<T> lista) {
        if (nodo == null) {
            return;
        }
        if (nodo.esHoja()) {
            lista.add(nodo.getDato());
            return;
        }
        hojas(nodo.getIzquierdo(), lista);
        hojas(nodo.getDerecho(), lista);
    }

    public boolean esCadena() {
        int nodos = contarNodos();
        return nodos > 0 && altura() == nodos - 1;
    }

    public boolean esBalanceado() {
        return alturaBalanceada(raiz) != -2;
    }

    private int alturaBalanceada(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        int izquierda = alturaBalanceada(nodo.getIzquierdo());
        if (izquierda == -2) {
            return -2;
        }
        int derecha = alturaBalanceada(nodo.getDerecho());
        if (derecha == -2) {
            return -2;
        }
        if (Math.abs(izquierda - derecha) > 1) {
            return -2;
        }
        return 1 + Math.max(izquierda, derecha);
    }

    public List<T> recorrer(TipoRecorrido tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("el tipo de recorrido es obligatorio");
        }
        List<T> lista = new ArrayList<>();
        switch (tipo) {
            case PREORDEN -> preorden(raiz, lista);
            case INORDEN -> inorden(raiz, lista);
            case POSORDEN -> posorden(raiz, lista);
            case NIVELES -> niveles(lista);
        }
        return lista;
    }

    private void preorden(Nodo<T> nodo, List<T> lista) {
        if (nodo == null) {
            return;
        }
        lista.add(nodo.getDato());
        preorden(nodo.getIzquierdo(), lista);
        preorden(nodo.getDerecho(), lista);
    }

    private void inorden(Nodo<T> nodo, List<T> lista) {
        if (nodo == null) {
            return;
        }
        inorden(nodo.getIzquierdo(), lista);
        lista.add(nodo.getDato());
        inorden(nodo.getDerecho(), lista);
    }

    private void posorden(Nodo<T> nodo, List<T> lista) {
        if (nodo == null) {
            return;
        }
        posorden(nodo.getIzquierdo(), lista);
        posorden(nodo.getDerecho(), lista);
        lista.add(nodo.getDato());
    }

    private void niveles(List<T> lista) {
        if (raiz == null) {
            return;
        }
        Cola<Nodo<T>> cola = new Cola<>();
        cola.encolar(raiz);
        while (!cola.estaVacia()) {
            Nodo<T> actual = cola.desencolar();
            lista.add(actual.getDato());
            if (actual.getIzquierdo() != null) {
                cola.encolar(actual.getIzquierdo());
            }
            if (actual.getDerecho() != null) {
                cola.encolar(actual.getDerecho());
            }
        }
    }

    public String trazaRecorrido(TipoRecorrido tipo) {
        return trazaRecorrido(tipo, String::valueOf);
    }

    public String trazaRecorrido(TipoRecorrido tipo, Function<T, String> etiqueta) {
        if (tipo == null) {
            throw new IllegalArgumentException("el tipo de recorrido es obligatorio");
        }
        StringBuilder traza = new StringBuilder();
        StringBuilder secuencia = new StringBuilder();
        if (tipo == TipoRecorrido.NIVELES) {
            for (T dato : recorrer(TipoRecorrido.NIVELES)) {
                secuencia.append(etiqueta.apply(dato)).append(" ");
            }
        } else {
            trazar(raiz, tipo, 0, traza, secuencia, etiqueta);
        }
        traza.append("Secuencia ").append(tipo).append(": ").append(secuencia.toString().trim());
        return traza.toString();
    }

    private void trazar(Nodo<T> nodo, TipoRecorrido tipo, int nivel, StringBuilder traza, StringBuilder secuencia,
            Function<T, String> etiqueta) {
        String sangria = "  ".repeat(nivel);
        if (nodo == null) {
            traza.append(sangria).append("null -> regresa\n");
            return;
        }
        traza.append(sangria).append("entra ").append(etiqueta.apply(nodo.getDato())).append("\n");
        if (tipo == TipoRecorrido.PREORDEN) {
            visitar(nodo.getDato(), sangria, traza, secuencia, etiqueta);
        }
        trazar(nodo.getIzquierdo(), tipo, nivel + 1, traza, secuencia, etiqueta);
        if (tipo == TipoRecorrido.INORDEN) {
            visitar(nodo.getDato(), sangria, traza, secuencia, etiqueta);
        }
        trazar(nodo.getDerecho(), tipo, nivel + 1, traza, secuencia, etiqueta);
        if (tipo == TipoRecorrido.POSORDEN) {
            visitar(nodo.getDato(), sangria, traza, secuencia, etiqueta);
        }
    }

    private void visitar(T dato, String sangria, StringBuilder traza, StringBuilder secuencia,
            Function<T, String> etiqueta) {
        traza.append(sangria).append("VISITA ").append(etiqueta.apply(dato)).append("\n");
        secuencia.append(etiqueta.apply(dato)).append(" ");
    }

    public String mostrarEstructura() {
        return mostrarEstructura(String::valueOf);
    }

    public String mostrarEstructura(Function<T, String> etiqueta) {
        if (raiz == null) {
            return "(árbol vacío)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(etiqueta.apply(raiz.getDato())).append("\n");
        ramas(raiz, "", sb, etiqueta);
        return sb.toString();
    }

    private void ramas(Nodo<T> nodo, String prefijo, StringBuilder sb, Function<T, String> etiqueta) {
        Nodo<T> izquierdo = nodo.getIzquierdo();
        Nodo<T> derecho = nodo.getDerecho();
        if (izquierdo != null) {
            boolean ultimo = derecho == null;
            sb.append(prefijo).append(ultimo ? "\\-- " : "|-- ").append("I: ")
                    .append(etiqueta.apply(izquierdo.getDato())).append("\n");
            ramas(izquierdo, prefijo + (ultimo ? "    " : "|   "), sb, etiqueta);
        }
        if (derecho != null) {
            sb.append(prefijo).append("\\-- ").append("D: ")
                    .append(etiqueta.apply(derecho.getDato())).append("\n");
            ramas(derecho, prefijo + "    ", sb, etiqueta);
        }
    }

    public String mostrarLateral() {
        return mostrarLateral(String::valueOf);
    }

    public String mostrarLateral(Function<T, String> etiqueta) {
        if (raiz == null) {
            return "(árbol vacío)";
        }
        StringBuilder sb = new StringBuilder();
        lateral(raiz, 0, sb, etiqueta);
        return sb.toString();
    }

    private void lateral(Nodo<T> nodo, int nivel, StringBuilder sb, Function<T, String> etiqueta) {
        if (nodo == null) {
            return;
        }
        lateral(nodo.getDerecho(), nivel + 1, sb, etiqueta);
        sb.append("    ".repeat(nivel)).append(etiqueta.apply(nodo.getDato())).append("\n");
        lateral(nodo.getIzquierdo(), nivel + 1, sb, etiqueta);
    }
}
