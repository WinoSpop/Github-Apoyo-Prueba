package negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import modelo.Nodo;

public class ArbolBinarioBusqueda<K extends Comparable<K>, T> extends ArbolBinario<T> {

    private final Function<T, K> extractorClave;

    public ArbolBinarioBusqueda(Function<T, K> extractorClave) {
        if (extractorClave == null) {
            throw new IllegalArgumentException("el extractor de clave es obligatorio");
        }
        this.extractorClave = extractorClave;
    }

    @Override
    public Nodo<T> crearRaiz(T dato) {
        throw new UnsupportedOperationException("en un ABB se usa insertar");
    }

    @Override
    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        throw new UnsupportedOperationException("en un ABB se usa insertar");
    }

    @Override
    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        throw new UnsupportedOperationException("en un ABB se usa insertar");
    }

    private K clave(T dato) {
        return extractorClave.apply(dato);
    }

    public boolean insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("el dato no puede ser null");
        }
        if (existe(clave(dato))) {
            return false;
        }
        raiz = insertar(raiz, dato);
        return true;
    }

    private Nodo<T> insertar(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return new Nodo<>(dato);
        }
        if (clave(dato).compareTo(clave(nodo.getDato())) < 0) {
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), dato));
        } else {
            nodo.setDerecho(insertar(nodo.getDerecho(), dato));
        }
        return nodo;
    }

    public boolean insertarIterativo(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("el dato no puede ser null");
        }
        if (raiz == null) {
            raiz = new Nodo<>(dato);
            return true;
        }
        Nodo<T> actual = raiz;
        while (true) {
            int comparacion = clave(dato).compareTo(clave(actual.getDato()));
            if (comparacion == 0) {
                return false;
            }
            if (comparacion < 0) {
                if (actual.getIzquierdo() == null) {
                    actual.setIzquierdo(new Nodo<>(dato));
                    return true;
                }
                actual = actual.getIzquierdo();
            } else {
                if (actual.getDerecho() == null) {
                    actual.setDerecho(new Nodo<>(dato));
                    return true;
                }
                actual = actual.getDerecho();
            }
        }
    }

    public T buscar(K clave) {
        Nodo<T> nodo = buscarNodo(raiz, clave);
        return nodo == null ? null : nodo.getDato();
    }

    public Nodo<T> buscarNodo(K clave) {
        return buscarNodo(raiz, clave);
    }

    private Nodo<T> buscarNodo(Nodo<T> nodo, K clave) {
        if (nodo == null) {
            return null;
        }
        int comparacion = clave.compareTo(clave(nodo.getDato()));
        if (comparacion == 0) {
            return nodo;
        }
        return comparacion < 0
                ? buscarNodo(nodo.getIzquierdo(), clave)
                : buscarNodo(nodo.getDerecho(), clave);
    }

    public T buscarIterativo(K clave) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int comparacion = clave.compareTo(clave(actual.getDato()));
            if (comparacion == 0) {
                return actual.getDato();
            }
            actual = comparacion < 0 ? actual.getIzquierdo() : actual.getDerecho();
        }
        return null;
    }

    public boolean existe(K clave) {
        return buscarNodo(raiz, clave) != null;
    }

    public int comparaciones(K clave) {
        int total = 0;
        Nodo<T> actual = raiz;
        while (actual != null) {
            total++;
            int comparacion = clave.compareTo(clave(actual.getDato()));
            if (comparacion == 0) {
                return total;
            }
            actual = comparacion < 0 ? actual.getIzquierdo() : actual.getDerecho();
        }
        return total;
    }

    public boolean eliminar(K clave) {
        if (!existe(clave)) {
            return false;
        }
        raiz = eliminar(raiz, clave);
        return true;
    }

    private Nodo<T> eliminar(Nodo<T> nodo, K clave) {
        if (nodo == null) {
            return null;
        }
        int comparacion = clave.compareTo(clave(nodo.getDato()));
        if (comparacion < 0) {
            nodo.setIzquierdo(eliminar(nodo.getIzquierdo(), clave));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminar(nodo.getDerecho(), clave));
        } else {
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                return null;
            }
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            T sucesor = minimo(nodo.getDerecho());
            nodo.setDato(sucesor);
            nodo.setDerecho(eliminar(nodo.getDerecho(), clave(sucesor)));
        }
        return nodo;
    }

    private T minimo(Nodo<T> nodo) {
        if (nodo.getIzquierdo() == null) {
            return nodo.getDato();
        }
        return minimo(nodo.getIzquierdo());
    }

    private T maximo(Nodo<T> nodo) {
        if (nodo.getDerecho() == null) {
            return nodo.getDato();
        }
        return maximo(nodo.getDerecho());
    }

    public T menor() {
        return raiz == null ? null : minimo(raiz);
    }

    public T mayor() {
        return raiz == null ? null : maximo(raiz);
    }

    public boolean modificar(K clave, Consumer<T> cambios) {
        Nodo<T> nodo = buscarNodo(raiz, clave);
        if (nodo == null) {
            return false;
        }
        cambios.accept(nodo.getDato());
        return true;
    }

    public boolean cambiarClave(K claveActual, T nuevoDato) {
        if (nuevoDato == null) {
            throw new IllegalArgumentException("el dato no puede ser null");
        }
        Nodo<T> nodo = buscarNodo(raiz, claveActual);
        if (nodo == null) {
            return false;
        }
        K claveNueva = clave(nuevoDato);
        if (claveNueva.compareTo(claveActual) == 0) {
            nodo.setDato(nuevoDato);
            return true;
        }
        if (existe(claveNueva)) {
            return false;
        }
        eliminar(claveActual);
        insertar(nuevoDato);
        return true;
    }

    public List<T> rango(K minimo, K maximo) {
        List<T> resultado = new ArrayList<>();
        rango(raiz, minimo, maximo, resultado);
        return resultado;
    }

    private void rango(Nodo<T> nodo, K minimo, K maximo, List<T> resultado) {
        if (nodo == null) {
            return;
        }
        K actual = clave(nodo.getDato());
        if (actual.compareTo(minimo) > 0) {
            rango(nodo.getIzquierdo(), minimo, maximo, resultado);
        }
        if (actual.compareTo(minimo) >= 0 && actual.compareTo(maximo) <= 0) {
            resultado.add(nodo.getDato());
        }
        if (actual.compareTo(maximo) < 0) {
            rango(nodo.getDerecho(), minimo, maximo, resultado);
        }
    }

    public T kEsimo(int k) {
        List<T> ordenados = recorrer(enums.TipoRecorrido.INORDEN);
        if (k < 1 || k > ordenados.size()) {
            return null;
        }
        return ordenados.get(k - 1);
    }

    public T sucesorDe(K clave) {
        Nodo<T> actual = raiz;
        T candidato = null;
        while (actual != null) {
            if (clave.compareTo(clave(actual.getDato())) < 0) {
                candidato = actual.getDato();
                actual = actual.getIzquierdo();
            } else {
                actual = actual.getDerecho();
            }
        }
        return candidato;
    }

    public T predecesorDe(K clave) {
        Nodo<T> actual = raiz;
        T candidato = null;
        while (actual != null) {
            if (clave.compareTo(clave(actual.getDato())) > 0) {
                candidato = actual.getDato();
                actual = actual.getDerecho();
            } else {
                actual = actual.getIzquierdo();
            }
        }
        return candidato;
    }

    public boolean esValido() {
        return esValido(raiz, null, null);
    }

    private boolean esValido(Nodo<T> nodo, K minimo, K maximo) {
        if (nodo == null) {
            return true;
        }
        K actual = clave(nodo.getDato());
        if (minimo != null && actual.compareTo(minimo) <= 0) {
            return false;
        }
        if (maximo != null && actual.compareTo(maximo) >= 0) {
            return false;
        }
        return esValido(nodo.getIzquierdo(), minimo, actual) && esValido(nodo.getDerecho(), actual, maximo);
    }
}
