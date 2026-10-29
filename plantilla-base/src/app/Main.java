package app;

import enums.TipoRecorrido;
import java.util.List;
import java.util.Scanner;
import modelo.Elemento;
import negocio.ArbolBinario;
import negocio.DatosPrueba;
import negocio.GestorElementos;
import negocio.GestorJerarquia;

public class Main {

    private static final Scanner entrada = new Scanner(System.in);
    private static final GestorElementos elementos = new GestorElementos();
    private static final GestorJerarquia jerarquia = new GestorJerarquia();

    public static void main(String[] args) {
        DatosPrueba.cargarElementos(elementos);
        DatosPrueba.cargarJerarquia(jerarquia);
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            try {
                ejecutar(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
        System.out.println("Hasta luego.");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== MENÚ =====");
        System.out.println("Árbol de búsqueda");
        System.out.println(" 1. Registrar");
        System.out.println(" 2. Buscar");
        System.out.println(" 3. Modificar");
        System.out.println(" 4. Eliminar");
        System.out.println(" 5. Recorridos");
        System.out.println(" 6. Estadísticas");
        System.out.println(" 7. Ver estructura");
        System.out.println("Jerarquía");
        System.out.println(" 8. Agregar");
        System.out.println(" 9. Eliminar");
        System.out.println("10. Ver jerarquía");
        System.out.println("11. Nivel, grado y altura");
        System.out.println("12. Transformar a binario");
        System.out.println(" 0. Salir");
    }

    private static void ejecutar(int opcion) {
        switch (opcion) {
            case 1 -> registrar();
            case 2 -> buscar();
            case 3 -> modificar();
            case 4 -> eliminar();
            case 5 -> recorridos();
            case 6 -> estadisticas();
            case 7 -> System.out.println(elementos.estructura());
            case 8 -> agregarEnJerarquia();
            case 9 -> eliminarEnJerarquia();
            case 10 -> System.out.println(jerarquia.jerarquia());
            case 11 -> consultasJerarquia();
            case 12 -> transformar();
            case 0 -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    private static void registrar() {
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        double valor = leerDecimal("Valor: ");
        boolean registrado = elementos.registrar(new Elemento(codigo, nombre, valor));
        System.out.println(registrado ? "Registrado" : "Ya existe ese código");
    }

    private static void buscar() {
        Elemento e = elementos.buscar(leerTexto("Código: "));
        System.out.println(e == null ? "No existe ese código" : e.toString());
    }

    private static void modificar() {
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nuevo nombre: ");
        double valor = leerDecimal("Nuevo valor: ");
        boolean modificado = elementos.modificar(codigo, nombre, valor);
        System.out.println(modificado ? "Datos actualizados" : "No existe ese código");
    }

    private static void eliminar() {
        boolean eliminado = elementos.eliminar(leerTexto("Código: "));
        System.out.println(eliminado ? "Eliminado" : "No existe ese código");
    }

    private static void recorridos() {
        System.out.println("1. Preorden  2. Inorden  3. Posorden  4. Por niveles");
        int tipo = leerEntero("Tipo: ");
        TipoRecorrido recorrido = switch (tipo) {
            case 1 -> TipoRecorrido.PREORDEN;
            case 2 -> TipoRecorrido.INORDEN;
            case 3 -> TipoRecorrido.POSORDEN;
            case 4 -> TipoRecorrido.NIVELES;
            default -> null;
        };
        if (recorrido == null) {
            System.out.println("Tipo no válido");
            return;
        }
        List<Elemento> lista = elementos.listar(recorrido);
        if (lista.isEmpty()) {
            System.out.println("El árbol está vacío");
            return;
        }
        lista.forEach(System.out::println);
        System.out.println(elementos.traza(recorrido));
    }

    private static void estadisticas() {
        System.out.println("Total: " + elementos.total());
        System.out.println("Altura: " + elementos.altura());
        System.out.println("Hojas: " + elementos.hojas());
        System.out.println("Menor código: " + elementos.menor());
        System.out.println("Mayor código: " + elementos.mayor());
        System.out.printf("Promedio del valor: %.2f%n", elementos.promedioValor());
    }

    private static void agregarEnJerarquia() {
        String padre = leerTexto("Código del padre: ");
        String codigo = leerTexto("Código nuevo: ");
        String nombre = leerTexto("Nombre: ");
        double valor = leerDecimal("Valor: ");
        boolean agregado = jerarquia.agregar(padre, new Elemento(codigo, nombre, valor));
        System.out.println(agregado ? "Agregado" : "No se pudo agregar: padre inexistente o código repetido");
    }

    private static void eliminarEnJerarquia() {
        boolean eliminado = jerarquia.eliminar(leerTexto("Código a eliminar: "));
        System.out.println(eliminado ? "Eliminado" : "No se eliminó: no existe, es la raíz o tiene hijos");
    }

    private static void consultasJerarquia() {
        String codigo = leerTexto("Código: ");
        System.out.println("Nivel: " + jerarquia.nivel(codigo));
        System.out.println("Grado: " + jerarquia.grado(codigo));
        System.out.println("Altura del árbol: " + jerarquia.altura());
        System.out.println("Total de nodos: " + jerarquia.contarNodos());
        System.out.println("Hojas: " + jerarquia.contarHojas());
    }

    private static void transformar() {
        ArbolBinario<Elemento> binario = jerarquia.aBinario();
        System.out.println(binario.mostrarEstructura(Elemento::getCodigo));
        System.out.println("Nodos del binario: " + binario.contarNodos());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número");
            }
        }
    }
}
