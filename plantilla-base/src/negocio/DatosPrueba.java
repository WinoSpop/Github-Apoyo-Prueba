package negocio;

import modelo.Elemento;

public final class DatosPrueba {

    private DatosPrueba() {
    }

    public static void cargarElementos(GestorElementos gestor) {
        String[] codigos = {"E50", "E30", "E70", "E20", "E40", "E60", "E80"};
        double[] valores = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0};
        for (int i = 0; i < codigos.length; i++) {
            gestor.registrar(new Elemento(codigos[i], "Elemento " + codigos[i], valores[i]));
        }
    }

    public static void cargarJerarquia(GestorJerarquia gestor) {
        gestor.registrarRaiz(new Elemento("RAIZ", "Raíz", 0));
        gestor.agregar("RAIZ", new Elemento("A", "Rama A", 1));
        gestor.agregar("RAIZ", new Elemento("B", "Rama B", 2));
        gestor.agregar("RAIZ", new Elemento("C", "Rama C", 3));
        gestor.agregar("A", new Elemento("A1", "Hijo A1", 4));
        gestor.agregar("A", new Elemento("A2", "Hijo A2", 5));
        gestor.agregar("C", new Elemento("C1", "Hijo C1", 6));
    }
}
