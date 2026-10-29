package app;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import java.util.List;
import java.util.Scanner;
import modelo.Estudiante;
import modelo.UnidadAcademica;
import negocio.ArbolBinario;
import negocio.DatosPrueba;
import negocio.GestorEstudiantes;
import negocio.GestorOrganizacion;

public class Main {

    private static final Scanner entrada = new Scanner(System.in);
    private static final GestorEstudiantes estudiantes = new GestorEstudiantes();
    private static final GestorOrganizacion organizacion = new GestorOrganizacion();

    public static void main(String[] args) {
        DatosPrueba.cargarEstudiantes(estudiantes);
        DatosPrueba.cargarOrganizacion(organizacion);
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
        System.out.println("ABB de estudiantes");
        System.out.println(" 1. Registrar estudiante");
        System.out.println(" 2. Buscar estudiante");
        System.out.println(" 3. Modificar estudiante");
        System.out.println(" 4. Eliminar estudiante");
        System.out.println(" 5. Recorridos");
        System.out.println(" 6. Estadísticas");
        System.out.println(" 7. Ver estructura del ABB");
        System.out.println("Árbol general de unidades");
        System.out.println(" 8. Agregar unidad");
        System.out.println(" 9. Eliminar unidad");
        System.out.println("10. Ver jerarquía");
        System.out.println("11. Nivel, grado y altura");
        System.out.println("12. Transformar a binario");
        System.out.println(" 0. Salir");
    }

    private static void ejecutar(int opcion) {
        switch (opcion) {
            case 1 -> registrarEstudiante();
            case 2 -> buscarEstudiante();
            case 3 -> modificarEstudiante();
            case 4 -> eliminarEstudiante();
            case 5 -> recorridos();
            case 6 -> estadisticas();
            case 7 -> System.out.println(estudiantes.estructura());
            case 8 -> agregarUnidad();
            case 9 -> eliminarUnidad();
            case 10 -> System.out.println(organizacion.jerarquia());
            case 11 -> consultasUnidad();
            case 12 -> transformar();
            case 0 -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    private static void registrarEstudiante() {
        int matricula = leerEntero("Matrícula: ");
        String cedula = leerTexto("Cédula: ");
        String nombres = leerTexto("Nombres: ");
        double promedio = leerDecimal("Promedio: ");
        boolean registrado = estudiantes.registrar(new Estudiante(matricula, cedula, nombres, promedio));
        System.out.println(registrado ? "Estudiante registrado" : "Ya existe esa matrícula");
    }

    private static void buscarEstudiante() {
        Estudiante e = estudiantes.buscar(leerEntero("Matrícula: "));
        System.out.println(e == null ? "No existe esa matrícula" : e.toString());
    }

    private static void modificarEstudiante() {
        int matricula = leerEntero("Matrícula del estudiante: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombres = leerTexto("Nuevos nombres: ");
        double promedio = leerDecimal("Nuevo promedio: ");
        boolean modificado = estudiantes.modificar(matricula, cedula, nombres, promedio);
        System.out.println(modificado ? "Datos actualizados" : "No existe esa matrícula");
    }

    private static void eliminarEstudiante() {
        boolean eliminado = estudiantes.eliminar(leerEntero("Matrícula: "));
        System.out.println(eliminado ? "Estudiante eliminado" : "No existe esa matrícula");
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
        List<Estudiante> lista = estudiantes.listar(recorrido);
        if (lista.isEmpty()) {
            System.out.println("El árbol está vacío");
            return;
        }
        lista.forEach(System.out::println);
        System.out.println(estudiantes.traza(recorrido));
    }

    private static void estadisticas() {
        System.out.println("Total de estudiantes: " + estudiantes.total());
        System.out.println("Altura del árbol: " + estudiantes.altura());
        System.out.println("Hojas: " + estudiantes.hojas());
        System.out.println("Menor matrícula: " + estudiantes.menor());
        System.out.println("Mayor matrícula: " + estudiantes.mayor());
        System.out.printf("Promedio general: %.2f%n", estudiantes.promedioGeneral());
    }

    private static void agregarUnidad() {
        String padre = leerTexto("Código del padre: ");
        String codigo = leerTexto("Código nuevo: ");
        String nombre = leerTexto("Nombre: ");
        System.out.println("Tipos: " + java.util.Arrays.toString(TipoUnidad.values()));
        TipoUnidad tipo = TipoUnidad.valueOf(leerTexto("Tipo: ").toUpperCase());
        boolean agregado = organizacion.agregar(padre, new UnidadAcademica(codigo, nombre, tipo));
        System.out.println(agregado ? "Unidad agregada" : "No se pudo agregar: padre inexistente, código repetido o jerarquía inválida");
    }

    private static void eliminarUnidad() {
        boolean eliminada = organizacion.eliminar(leerTexto("Código a eliminar: "));
        System.out.println(eliminada ? "Unidad eliminada" : "No se eliminó: no existe, es la raíz o tiene subunidades");
    }

    private static void consultasUnidad() {
        String codigo = leerTexto("Código: ");
        System.out.println("Nivel: " + organizacion.nivel(codigo));
        System.out.println("Grado: " + organizacion.grado(codigo));
        System.out.println("Altura del árbol: " + organizacion.altura());
        System.out.println("Total de unidades: " + organizacion.contarNodos());
        System.out.println("Hojas: " + organizacion.contarHojas());
    }

    private static void transformar() {
        ArbolBinario<UnidadAcademica> binario = organizacion.aBinario();
        System.out.println(binario.mostrarEstructura());
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
