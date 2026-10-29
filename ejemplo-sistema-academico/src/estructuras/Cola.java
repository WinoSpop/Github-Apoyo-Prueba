package estructuras;

public class Cola<T> {

    private static class NodoCola<E> {
        private final E dato;
        private NodoCola<E> siguiente;

        private NodoCola(E dato) {
            this.dato = dato;
        }
    }

    private NodoCola<T> frente;
    private NodoCola<T> fin;
    private int tamano;

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamano() {
        return tamano;
    }

    public void encolar(T dato) {
        NodoCola<T> nuevo = new NodoCola<>(dato);
        if (fin == null) {
            frente = nuevo;
        } else {
            fin.siguiente = nuevo;
        }
        fin = nuevo;
        tamano++;
    }

    public T desencolar() {
        if (frente == null) {
            throw new IllegalStateException("la cola está vacía");
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamano--;
        return dato;
    }

    public T verFrente() {
        if (frente == null) {
            throw new IllegalStateException("la cola está vacía");
        }
        return frente.dato;
    }
}
