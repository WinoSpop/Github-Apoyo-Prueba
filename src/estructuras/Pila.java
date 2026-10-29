package estructuras;

public class Pila<T> {

    private static class NodoPila<E> {
        private final E dato;
        private final NodoPila<E> abajo;

        private NodoPila(E dato, NodoPila<E> abajo) {
            this.dato = dato;
            this.abajo = abajo;
        }
    }

    private NodoPila<T> cima;
    private int tamano;

    public boolean estaVacia() {
        return cima == null;
    }

    public int tamano() {
        return tamano;
    }

    public void apilar(T dato) {
        cima = new NodoPila<>(dato, cima);
        tamano++;
    }

    public T desapilar() {
        if (cima == null) {
            throw new IllegalStateException("la pila está vacía");
        }
        T dato = cima.dato;
        cima = cima.abajo;
        tamano--;
        return dato;
    }

    public T verCima() {
        if (cima == null) {
            throw new IllegalStateException("la pila está vacía");
        }
        return cima.dato;
    }
}
