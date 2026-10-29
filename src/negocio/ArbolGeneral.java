package negocio;

import enums.TipoRecorrido;
import estructuras.Cola;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import modelo.NodoGeneral;

public class ArbolGeneral<T> {

    private NodoGeneral<T> raiz;
    private final BiPredicate<T, T> reglaHijo;

    public ArbolGeneral() {
        this((padre, hijo) -> true);
    }

    public ArbolGeneral(BiPredicate<T, T> reglaHijo) {
        if (reglaHijo == null) {
            throw new IllegalArgumentException("la regla de hijo es obligatoria");
        }
        this.raiz = null;
        this.reglaHijo = reglaHijo;
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public NodoGeneral<T> getRaiz() {
        return raiz;
    }

    void fijarRaiz(NodoGeneral<T> nueva) {
        this.raiz = nueva;
    }

    public NodoGeneral<T> crearRaiz(T dato) {
        if (raiz != null) {
            throw new IllegalStateException("el árbol ya tiene raíz");
        }
        raiz = new NodoGeneral<>(dato);
        return raiz;
    }

    public NodoGeneral<T> agregarHijo(NodoGeneral<T> padre, T dato) {
        if (padre == null) {
            throw new IllegalArgumentException("el padre no puede ser null");
        }
        if (!reglaHijo.test(padre.getDato(), dato)) {
            throw new IllegalArgumentException("la regla del árbol no permite ese hijo bajo " + padre.getDato());
        }
        NodoGeneral<T> nuevo = new NodoGeneral<>(dato);
        padre.agregarHijo(nuevo);
        return nuevo;
    }

    public boolean agregarHijo(Predicate<T> condicionPadre, T dato) {
        NodoGeneral<T> padre = buscar(condicionPadre);
        if (padre == null || !reglaHijo.test(padre.getDato(), dato)) {
            return false;
        }
        padre.agregarHijo(new NodoGeneral<>(dato));
        return true;
    }

    public NodoGeneral<T> buscar(Predicate<T> condicion) {
        return buscar(raiz, condicion);
    }

    private NodoGeneral<T> buscar(NodoGeneral<T> nodo, Predicate<T> condicion) {
        if (nodo == null) {
            return null;
        }
        if (condicion.test(nodo.getDato())) {
            return nodo;
        }
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            NodoGeneral<T> encontrado = buscar(hijo, condicion);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    private NodoGeneral<T> buscarPadre(NodoGeneral<T> nodo, Predicate<T> condicion) {
        if (nodo == null) {
            return null;
        }
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            if (condicion.test(hijo.getDato())) {
                return nodo;
            }
            NodoGeneral<T> padre = buscarPadre(hijo, condicion);
            if (padre != null) {
                return padre;
            }
        }
        return null;
    }

    public T padreDe(Predicate<T> condicion) {
        NodoGeneral<T> padre = buscarPadre(raiz, condicion);
        return padre == null ? null : padre.getDato();
    }

    public List<T> hermanosDe(Predicate<T> condicion) {
        List<T> hermanos = new ArrayList<>();
        NodoGeneral<T> padre = buscarPadre(raiz, condicion);
        if (padre == null) {
            return hermanos;
        }
        for (NodoGeneral<T> hijo : padre.getHijos()) {
            if (!condicion.test(hijo.getDato())) {
                hermanos.add(hijo.getDato());
            }
        }
        return hermanos;
    }

    public boolean eliminar(Predicate<T> condicion) {
        if (raiz == null || condicion.test(raiz.getDato())) {
            return false;
        }
        NodoGeneral<T> padre = buscarPadre(raiz, condicion);
        if (padre == null) {
            return false;
        }
        for (NodoGeneral<T> hijo : padre.getHijos()) {
            if (condicion.test(hijo.getDato())) {
                if (!hijo.esHoja()) {
                    return false;
                }
                return padre.quitarHijo(hijo);
            }
        }
        return false;
    }

    public boolean eliminarRama(Predicate<T> condicion) {
        if (raiz == null || condicion.test(raiz.getDato())) {
            return false;
        }
        NodoGeneral<T> padre = buscarPadre(raiz, condicion);
        if (padre == null) {
            return false;
        }
        for (NodoGeneral<T> hijo : padre.getHijos()) {
            if (condicion.test(hijo.getDato())) {
                return padre.quitarHijo(hijo);
            }
        }
        return false;
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoGeneral<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int total = 1;
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            total += contarNodos(hijo);
        }
        return total;
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(NodoGeneral<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        int total = 0;
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            total += contarHojas(hijo);
        }
        return total;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoGeneral<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        int mayor = -1;
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            mayor = Math.max(mayor, altura(hijo));
        }
        return 1 + mayor;
    }

    public int nivelDe(Predicate<T> condicion) {
        return nivelDe(raiz, condicion, 0);
    }

    private int nivelDe(NodoGeneral<T> nodo, Predicate<T> condicion, int nivel) {
        if (nodo == null) {
            return -1;
        }
        if (condicion.test(nodo.getDato())) {
            return nivel;
        }
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            int encontrado = nivelDe(hijo, condicion, nivel + 1);
            if (encontrado != -1) {
                return encontrado;
            }
        }
        return -1;
    }

    public int gradoDe(Predicate<T> condicion) {
        NodoGeneral<T> nodo = buscar(condicion);
        return nodo == null ? -1 : nodo.grado();
    }

    public int gradoArbol() {
        return gradoArbol(raiz);
    }

    private int gradoArbol(NodoGeneral<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int mayor = nodo.grado();
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            mayor = Math.max(mayor, gradoArbol(hijo));
        }
        return mayor;
    }

    public int contarDescendientes(Predicate<T> condicion) {
        NodoGeneral<T> nodo = buscar(condicion);
        return nodo == null ? -1 : contarNodos(nodo) - 1;
    }

    public int contarSi(Predicate<T> condicion) {
        return contarSi(raiz, condicion);
    }

    private int contarSi(NodoGeneral<T> nodo, Predicate<T> condicion) {
        if (nodo == null) {
            return 0;
        }
        int total = condicion.test(nodo.getDato()) ? 1 : 0;
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            total += contarSi(hijo, condicion);
        }
        return total;
    }

    public List<T> camino(Predicate<T> condicion) {
        List<T> ruta = new ArrayList<>();
        if (!camino(raiz, condicion, ruta)) {
            return new ArrayList<>();
        }
        return ruta;
    }

    private boolean camino(NodoGeneral<T> nodo, Predicate<T> condicion, List<T> ruta) {
        if (nodo == null) {
            return false;
        }
        ruta.add(nodo.getDato());
        if (condicion.test(nodo.getDato())) {
            return true;
        }
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            if (camino(hijo, condicion, ruta)) {
                return true;
            }
        }
        ruta.remove(ruta.size() - 1);
        return false;
    }

    public List<T> hojas() {
        List<T> lista = new ArrayList<>();
        hojas(raiz, lista);
        return lista;
    }

    private void hojas(NodoGeneral<T> nodo, List<T> lista) {
        if (nodo == null) {
            return;
        }
        if (nodo.esHoja()) {
            lista.add(nodo.getDato());
            return;
        }
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            hojas(hijo, lista);
        }
    }

    public List<T> recorrer(TipoRecorrido tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("el tipo de recorrido es obligatorio");
        }
        List<T> lista = new ArrayList<>();
        switch (tipo) {
            case PREORDEN -> preorden(raiz, lista);
            case POSORDEN -> posorden(raiz, lista);
            case NIVELES -> niveles(lista);
            case INORDEN -> throw new IllegalArgumentException("el inorden no aplica a un árbol general");
        }
        return lista;
    }

    private void preorden(NodoGeneral<T> nodo, List<T> lista) {
        if (nodo == null) {
            return;
        }
        lista.add(nodo.getDato());
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            preorden(hijo, lista);
        }
    }

    private void posorden(NodoGeneral<T> nodo, List<T> lista) {
        if (nodo == null) {
            return;
        }
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            posorden(hijo, lista);
        }
        lista.add(nodo.getDato());
    }

    private void niveles(List<T> lista) {
        if (raiz == null) {
            return;
        }
        Cola<NodoGeneral<T>> cola = new Cola<>();
        cola.encolar(raiz);
        while (!cola.estaVacia()) {
            NodoGeneral<T> actual = cola.desencolar();
            lista.add(actual.getDato());
            for (NodoGeneral<T> hijo : actual.getHijos()) {
                cola.encolar(hijo);
            }
        }
    }

    public String mostrarJerarquia() {
        if (raiz == null) {
            return "(árbol vacío)";
        }
        StringBuilder sb = new StringBuilder();
        jerarquia(raiz, 0, sb);
        return sb.toString();
    }

    private void jerarquia(NodoGeneral<T> nodo, int nivel, StringBuilder sb) {
        sb.append("  ".repeat(nivel)).append(nodo.getDato()).append("\n");
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            jerarquia(hijo, nivel + 1, sb);
        }
    }

    public ArbolBinario<T> aBinario() {
        return Transformador.aBinario(this);
    }
}
