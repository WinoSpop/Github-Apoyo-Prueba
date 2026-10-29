package negocio;

import java.util.List;
import modelo.Nodo;
import modelo.NodoGeneral;

public final class Transformador {

    private Transformador() {
    }

    public static <T> ArbolBinario<T> aBinario(ArbolGeneral<T> general) {
        if (general == null) {
            throw new IllegalArgumentException("el árbol general no puede ser null");
        }
        ArbolBinario<T> binario = new ArbolBinario<>();
        NodoGeneral<T> raizGeneral = general.getRaiz();
        if (raizGeneral == null) {
            return binario;
        }
        Nodo<T> raizBinaria = new Nodo<>(raizGeneral.getDato());
        raizBinaria.setIzquierdo(convertirHermanos(raizGeneral.getHijos(), 0));
        binario.fijarRaiz(raizBinaria);
        return binario;
    }

    private static <T> Nodo<T> convertirHermanos(List<NodoGeneral<T>> hermanos, int posicion) {
        if (posicion >= hermanos.size()) {
            return null;
        }
        NodoGeneral<T> actual = hermanos.get(posicion);
        Nodo<T> nodo = new Nodo<>(actual.getDato());
        nodo.setIzquierdo(convertirHermanos(actual.getHijos(), 0));
        nodo.setDerecho(convertirHermanos(hermanos, posicion + 1));
        return nodo;
    }

    public static <T> ArbolGeneral<T> aGeneral(ArbolBinario<T> binario) {
        if (binario == null) {
            throw new IllegalArgumentException("el árbol binario no puede ser null");
        }
        ArbolGeneral<T> general = new ArbolGeneral<>();
        Nodo<T> raizBinaria = binario.getRaiz();
        if (raizBinaria == null) {
            return general;
        }
        if (raizBinaria.getDerecho() != null) {
            throw new IllegalArgumentException("la raíz no puede tener hijo derecho: la raíz no tiene hermanos");
        }
        general.fijarRaiz(reconstruir(raizBinaria));
        return general;
    }

    private static <T> NodoGeneral<T> reconstruir(Nodo<T> binario) {
        NodoGeneral<T> general = new NodoGeneral<>(binario.getDato());
        for (Nodo<T> hijo = binario.getIzquierdo(); hijo != null; hijo = hijo.getDerecho()) {
            general.agregarHijo(reconstruir(hijo));
        }
        return general;
    }
}
