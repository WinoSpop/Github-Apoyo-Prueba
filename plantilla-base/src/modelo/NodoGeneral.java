package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodoGeneral<T> {

    private T dato;
    private final List<NodoGeneral<T>> hijos;

    public NodoGeneral(T dato) {
        this.dato = dato;
        this.hijos = new ArrayList<>();
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public List<NodoGeneral<T>> getHijos() {
        return Collections.unmodifiableList(hijos);
    }

    public void agregarHijo(NodoGeneral<T> hijo) {
        if (hijo == null) {
            throw new IllegalArgumentException("el hijo no puede ser null");
        }
        hijos.add(hijo);
    }

    public boolean quitarHijo(NodoGeneral<T> hijo) {
        return hijos.remove(hijo);
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    public int grado() {
        return hijos.size();
    }
}
