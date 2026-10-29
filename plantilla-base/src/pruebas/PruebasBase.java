package pruebas;

import enums.TipoRecorrido;
import java.util.List;
import java.util.Objects;
import modelo.Elemento;
import negocio.ArbolBinario;
import negocio.DatosPrueba;
import negocio.GestorElementos;
import negocio.GestorJerarquia;

public class PruebasBase {

    private static int fallos = 0;
    private static int total = 0;

    public static void main(String[] args) {
        pruebasElementos();
        pruebasJerarquia();
        System.out.println();
        System.out.println(total + " verificaciones, " + fallos + " fallos");
        if (fallos > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String nombre, Object esperado, Object real) {
        total++;
        boolean ok = Objects.equals(esperado, real);
        System.out.println((ok ? "OK     " : "FALLA  ") + nombre + (ok ? "" : " | esperado=" + esperado + " real=" + real));
        if (!ok) {
            fallos++;
        }
    }

    private static List<String> codigos(List<Elemento> lista) {
        return lista.stream().map(Elemento::getCodigo).toList();
    }

    private static void pruebasElementos() {
        GestorElementos g = new GestorElementos();
        DatosPrueba.cargarElementos(g);
        verificar("total", 7, g.total());
        verificar("altura", 2, g.altura());
        verificar("hojas", 4, g.hojas());
        verificar("preorden", List.of("E50", "E30", "E20", "E40", "E70", "E60", "E80"), codigos(g.listar(TipoRecorrido.PREORDEN)));
        verificar("inorden", List.of("E20", "E30", "E40", "E50", "E60", "E70", "E80"), codigos(g.listar(TipoRecorrido.INORDEN)));
        verificar("posorden", List.of("E20", "E40", "E30", "E60", "E80", "E70", "E50"), codigos(g.listar(TipoRecorrido.POSORDEN)));
        verificar("niveles", List.of("E50", "E30", "E70", "E20", "E40", "E60", "E80"), codigos(g.listar(TipoRecorrido.NIVELES)));
        verificar("menor", "E20", g.menor().getCodigo());
        verificar("mayor", "E80", g.mayor().getCodigo());
        verificar("promedio", 40.0, g.promedioValor());
        verificar("duplicado rechazado", false, g.registrar(new Elemento("e50", "Repetido", 1)));
        verificar("buscar sin importar mayúsculas", "E40", g.buscar(" e40 ").getCodigo());
        verificar("buscar inexistente", null, g.buscar("X"));
        verificar("modificar", true, g.modificar("E40", "Nuevo", 99));
        verificar("modificar cambia el valor", 99.0, g.buscar("E40").getValor());
        verificar("modificar inexistente", false, g.modificar("X", "Nuevo", 1));
        verificar("modificar inválido lanza error y no altera", true, intentarModificarInvalido(g));
        verificar("eliminar hoja", true, g.eliminar("E20"));
        verificar("eliminar con dos hijos", true, g.eliminar("E50"));
        verificar("raíz tras eliminar con dos hijos", "E60", codigos(g.listar(TipoRecorrido.PREORDEN)).get(0));
        verificar("total tras eliminar", 5, g.total());
        verificar("eliminar inexistente", false, g.eliminar("E20"));
        verificar("árbol válido", true, g.getArbol().esValido());
        GestorElementos vacio = new GestorElementos();
        verificar("vacío total", 0, vacio.total());
        verificar("vacío altura", -1, vacio.altura());
        verificar("vacío promedio", 0.0, vacio.promedioValor());
        verificar("vacío menor", null, vacio.menor());
    }

    private static boolean intentarModificarInvalido(GestorElementos g) {
        try {
            g.modificar("E30", "Valido", -5);
            return false;
        } catch (IllegalArgumentException e) {
            return g.buscar("E30").getValor() == 20.0;
        }
    }

    private static void pruebasJerarquia() {
        GestorJerarquia g = new GestorJerarquia();
        DatosPrueba.cargarJerarquia(g);
        verificar("nodos", 7, g.contarNodos());
        verificar("altura", 2, g.altura());
        verificar("hojas", 4, g.contarHojas());
        verificar("nivel de A1", 2, g.nivel("A1"));
        verificar("nivel de la raíz", 0, g.nivel("RAIZ"));
        verificar("grado de RAIZ", 3, g.grado("RAIZ"));
        verificar("grado de una hoja", 0, g.grado("B"));
        verificar("camino a C1", List.of("RAIZ", "C", "C1"), codigos(g.camino("C1")));
        verificar("padre inexistente", false, g.agregar("X", new Elemento("Z", "Z", 1)));
        verificar("código repetido", false, g.agregar("RAIZ", new Elemento("A", "Otro", 1)));
        verificar("segunda raíz", false, g.registrarRaiz(new Elemento("R2", "R2", 1)));
        verificar("eliminar con hijos", false, g.eliminar("A"));
        verificar("eliminar raíz", false, g.eliminar("RAIZ"));
        verificar("eliminar hoja", true, g.eliminar("A1"));
        verificar("nodos tras eliminar", 6, g.contarNodos());
        ArbolBinario<Elemento> binario = g.aBinario();
        verificar("binario conserva nodos", g.contarNodos(), binario.contarNodos());
        verificar("raíz del binario sin derecho", null, binario.getRaiz().getDerecho());
        verificar("primer hijo a la izquierda", "A", binario.getRaiz().getIzquierdo().getDato().getCodigo());
        verificar("hermano a la derecha", "B", binario.getRaiz().getIzquierdo().getDerecho().getDato().getCodigo());
        verificar("preorden jerarquía", List.of("RAIZ", "A", "A2", "B", "C", "C1"), codigos(g.listar(TipoRecorrido.PREORDEN)));
        GestorJerarquia vacia = new GestorJerarquia();
        verificar("vacía nodos", 0, vacia.contarNodos());
        verificar("vacía altura", -1, vacia.altura());
    }
}
