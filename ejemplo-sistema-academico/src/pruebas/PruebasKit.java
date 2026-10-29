package pruebas;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import estructuras.Cola;
import estructuras.Pila;
import java.util.List;
import java.util.Objects;
import modelo.Estudiante;
import modelo.Nodo;
import modelo.NodoGeneral;
import modelo.UnidadAcademica;
import negocio.ArbolBinario;
import negocio.ArbolBinarioBusqueda;
import negocio.ArbolGeneral;
import negocio.DatosPrueba;
import negocio.GestorEstudiantes;
import negocio.GestorOrganizacion;
import negocio.GestorSistemaAcademico;
import negocio.Transformador;

public class PruebasKit {

    private static int fallos = 0;
    private static int total = 0;

    public static void main(String[] args) {
        pruebasArbolBinario();
        pruebasABB();
        pruebasEliminacionABB();
        pruebasPresentacion();
        pruebasCadena();
        pruebasArbolVacio();
        pruebasArbolGeneral();
        pruebasTransformacion();
        pruebasGestores();
        pruebasSistemaAcademico();
        pruebasEstructuras();
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

    private static ArbolBinarioBusqueda<Integer, Integer> abbDe(int... valores) {
        ArbolBinarioBusqueda<Integer, Integer> abb = new ArbolBinarioBusqueda<>(x -> x);
        for (int valor : valores) {
            abb.insertar(valor);
        }
        return abb;
    }

    private static String izquierdoDe(ArbolBinario<String> arbol, String dato) {
        Nodo<String> nodo = arbol.buscarSi(d -> d.equals(dato));
        return nodo.getIzquierdo() == null ? "null" : nodo.getIzquierdo().getDato();
    }

    private static String derechoDe(ArbolBinario<String> arbol, String dato) {
        Nodo<String> nodo = arbol.buscarSi(d -> d.equals(dato));
        return nodo.getDerecho() == null ? "null" : nodo.getDerecho().getDato();
    }

    private static void pruebasArbolBinario() {
        System.out.println("--- Árbol binario manual: 40 / 20 60 / 10 30 _ 80");
        ArbolBinario<Integer> arbol = new ArbolBinario<>();
        Nodo<Integer> n40 = arbol.crearRaiz(40);
        Nodo<Integer> n20 = arbol.agregarIzquierdo(n40, 20);
        Nodo<Integer> n60 = arbol.agregarDerecho(n40, 60);
        arbol.agregarIzquierdo(n20, 10);
        arbol.agregarDerecho(n20, 30);
        arbol.agregarDerecho(n60, 80);
        verificar("preorden", List.of(40, 20, 10, 30, 60, 80), arbol.recorrer(TipoRecorrido.PREORDEN));
        verificar("inorden", List.of(10, 20, 30, 40, 60, 80), arbol.recorrer(TipoRecorrido.INORDEN));
        verificar("posorden", List.of(10, 30, 20, 80, 60, 40), arbol.recorrer(TipoRecorrido.POSORDEN));
        verificar("niveles", List.of(40, 20, 60, 10, 30, 80), arbol.recorrer(TipoRecorrido.NIVELES));
        verificar("altura", 2, arbol.altura());
        verificar("nodos", 6, arbol.contarNodos());
        verificar("hojas", 3, arbol.contarHojas());
        verificar("nivel de 30", 2, arbol.nivelDe(x -> x == 30));
        verificar("nivel de inexistente", -1, arbol.nivelDe(x -> x == 99));
        verificar("grado de 60", 1, arbol.gradoDe(x -> x == 60));
        verificar("grado de inexistente", -1, arbol.gradoDe(x -> x == 99));
        verificar("camino a 30", List.of(40, 20, 30), arbol.camino(x -> x == 30));
        verificar("hojas listadas", List.of(10, 30, 80), arbol.hojas());
        verificar("contarSi mayores que 25", 4, arbol.contarSi(x -> x > 25));
        verificar("no es cadena", false, arbol.esCadena());
        verificar("raíz duplicada lanza error", true, lanza(() -> arbol.crearRaiz(1)));
        verificar("lugar ocupado lanza error", true, lanza(() -> arbol.agregarIzquierdo(n40, 5)));
        verificar("padre null lanza error", true, lanza(() -> arbol.agregarIzquierdo(null, 5)));
    }

    private static boolean lanza(Runnable accion) {
        try {
            accion.run();
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }

    private static void pruebasABB() {
        System.out.println("--- ABB: 50 30 70 20 40 60 80 35 65");
        ArbolBinarioBusqueda<Integer, Integer> abb = abbDe(50, 30, 70, 20, 40, 60, 80, 35, 65);
        verificar("preorden", List.of(50, 30, 20, 40, 35, 70, 60, 65, 80), abb.recorrer(TipoRecorrido.PREORDEN));
        verificar("inorden ordenado", List.of(20, 30, 35, 40, 50, 60, 65, 70, 80), abb.recorrer(TipoRecorrido.INORDEN));
        verificar("posorden", List.of(20, 35, 40, 30, 65, 60, 80, 70, 50), abb.recorrer(TipoRecorrido.POSORDEN));
        verificar("niveles", List.of(50, 30, 70, 20, 40, 60, 80, 35, 65), abb.recorrer(TipoRecorrido.NIVELES));
        verificar("altura", 3, abb.altura());
        verificar("nodos", 9, abb.contarNodos());
        verificar("hojas", List.of(20, 35, 65, 80), abb.hojas());
        verificar("menor", 20, abb.menor());
        verificar("mayor", 80, abb.mayor());
        verificar("buscar existente", 65, abb.buscar(65));
        verificar("buscar inexistente", null, abb.buscar(99));
        verificar("buscar iterativo", 35, abb.buscarIterativo(35));
        verificar("comparaciones para 35", 4, abb.comparaciones(35));
        verificar("duplicado rechazado", false, abb.insertar(40));
        verificar("duplicado rechazado iterativo", false, abb.insertarIterativo(40));
        verificar("rango 35 a 65", List.of(35, 40, 50, 60, 65), abb.rango(35, 65));
        verificar("tercer menor", 35, abb.kEsimo(3));
        verificar("sucesor de 50", 60, abb.sucesorDe(50));
        verificar("predecesor de 50", 40, abb.predecesorDe(50));
        verificar("sucesor de 80", null, abb.sucesorDe(80));
        verificar("camino a 35", List.of(50, 30, 40, 35), abb.camino(x -> x == 35));
        verificar("es ABB válido", true, abb.esValido());
        verificar("está balanceado", true, abb.esBalanceado());
        verificar("promedio de claves", 450.0 / 9, abb.promedio(x -> x));
        verificar("cambiar clave 35 por 36", true, abb.cambiarClave(35, 36));
        verificar("ya no existe 35", false, abb.existe(35));
        verificar("existe 36", true, abb.existe(36));
        verificar("sigue válido", true, abb.esValido());
        verificar("cambiar a clave repetida falla", false, abb.cambiarClave(36, 40));
        verificar("insertar iterativo nuevo", true, abb.insertarIterativo(85));
        verificar("crearRaiz bloqueado", true, lanza(() -> abb.crearRaiz(1)));
    }

    private static void pruebasEliminacionABB() {
        System.out.println("--- Eliminación en ABB");
        ArbolBinarioBusqueda<Integer, Integer> abb = abbDe(55, 30, 80, 20, 40, 70, 90, 45);
        verificar("eliminar raíz con dos hijos", true, abb.eliminar(55));
        verificar("nueva raíz es el sucesor 70", 70, abb.getRaiz().getDato());
        verificar("inorden tras eliminar", List.of(20, 30, 40, 45, 70, 80, 90), abb.recorrer(TipoRecorrido.INORDEN));
        verificar("80 sin izquierdo", null, abb.buscarNodo(80).getIzquierdo());
        verificar("80 conserva el 90", 90, abb.buscarNodo(80).getDerecho().getDato());

        ArbolBinarioBusqueda<Integer, Integer> caso2 = abbDe(40, 20, 60, 80);
        caso2.eliminar(60);
        verificar("caso un hijo: 80 sube", 80, caso2.getRaiz().getDerecho().getDato());

        ArbolBinarioBusqueda<Integer, Integer> caso1 = abbDe(40, 20, 60);
        caso1.eliminar(20);
        verificar("caso hoja: queda null", null, caso1.getRaiz().getIzquierdo());

        verificar("eliminar inexistente devuelve false", false, caso1.eliminar(999));
        verificar("árbol intacto tras fallo", 2, caso1.contarNodos());

        ArbolBinarioBusqueda<Integer, Integer> solo = abbDe(10);
        solo.eliminar(10);
        verificar("eliminar la única raíz deja vacío", true, solo.esVacio());
    }

    private static void pruebasPresentacion() {
        System.out.println("--- Estructura y trazas");
        ArbolBinarioBusqueda<Integer, Integer> pequeno = abbDe(40, 20, 60);
        verificar("estructura dibujada", "40\n|-- I: 20\n\\-- D: 60\n", pequeno.mostrarEstructura());
        verificar("estructura vacía", "(árbol vacío)", new ArbolBinario<Integer>().mostrarEstructura());
        verificar("secuencia de la traza inorden", true,
                pequeno.trazaRecorrido(TipoRecorrido.INORDEN).endsWith("Secuencia INORDEN: 20 40 60"));
        verificar("secuencia de la traza posorden", true,
                pequeno.trazaRecorrido(TipoRecorrido.POSORDEN).endsWith("Secuencia POSORDEN: 20 60 40"));
        verificar("secuencia de la traza preorden", true,
                pequeno.trazaRecorrido(TipoRecorrido.PREORDEN).endsWith("Secuencia PREORDEN: 40 20 60"));
        verificar("vista lateral", "    60\n40\n    20\n", pequeno.mostrarLateral());
    }

    private static void pruebasCadena() {
        System.out.println("--- ABB en cadena: 10 20 30 40 50");
        ArbolBinarioBusqueda<Integer, Integer> cadena = abbDe(10, 20, 30, 40, 50);
        verificar("es cadena", true, cadena.esCadena());
        verificar("altura es nodos menos uno", 4, cadena.altura());
        verificar("comparaciones para 50", 5, cadena.comparaciones(50));
        verificar("no está balanceado", false, cadena.esBalanceado());
    }

    private static void pruebasArbolVacio() {
        System.out.println("--- Árbol vacío y casos límite");
        ArbolBinarioBusqueda<Integer, Integer> vacio = new ArbolBinarioBusqueda<>(x -> x);
        verificar("altura vacío", -1, vacio.altura());
        verificar("contar vacío", 0, vacio.contarNodos());
        verificar("hojas vacío", 0, vacio.contarHojas());
        verificar("promedio vacío", 0.0, vacio.promedio(x -> x));
        verificar("menor vacío", null, vacio.menor());
        verificar("mayor vacío", null, vacio.mayor());
        verificar("recorrido vacío", List.of(), vacio.recorrer(TipoRecorrido.INORDEN));
        verificar("buscar en vacío", null, vacio.buscar(1));
        verificar("eliminar en vacío", false, vacio.eliminar(1));
        verificar("no es cadena si está vacío", false, vacio.esCadena());
    }

    private static ArbolGeneral<String> generalDeEjemplo() {
        ArbolGeneral<String> arbol = new ArbolGeneral<>();
        NodoGeneral<String> a = arbol.crearRaiz("A");
        NodoGeneral<String> b = arbol.agregarHijo(a, "B");
        arbol.agregarHijo(a, "C");
        NodoGeneral<String> d = arbol.agregarHijo(a, "D");
        arbol.agregarHijo(b, "E");
        arbol.agregarHijo(b, "F");
        arbol.agregarHijo(d, "G");
        return arbol;
    }

    private static void pruebasArbolGeneral() {
        System.out.println("--- Árbol general: A(B(E,F),C,D(G))");
        ArbolGeneral<String> arbol = generalDeEjemplo();
        verificar("nodos", 7, arbol.contarNodos());
        verificar("hojas", 4, arbol.contarHojas());
        verificar("altura", 2, arbol.altura());
        verificar("grado del árbol", 3, arbol.gradoArbol());
        verificar("nivel de G", 2, arbol.nivelDe(x -> x.equals("G")));
        verificar("grado de B", 2, arbol.gradoDe(x -> x.equals("B")));
        verificar("nivel inexistente", -1, arbol.nivelDe(x -> x.equals("Z")));
        verificar("grado inexistente", -1, arbol.gradoDe(x -> x.equals("Z")));
        verificar("padre de G", "D", arbol.padreDe(x -> x.equals("G")));
        verificar("hermanos de B", List.of("C", "D"), arbol.hermanosDe(x -> x.equals("B")));
        verificar("camino a F", List.of("A", "B", "F"), arbol.camino(x -> x.equals("F")));
        verificar("descendientes de B", 2, arbol.contarDescendientes(x -> x.equals("B")));
        verificar("preorden", List.of("A", "B", "E", "F", "C", "D", "G"), arbol.recorrer(TipoRecorrido.PREORDEN));
        verificar("posorden", List.of("E", "F", "B", "C", "G", "D", "A"), arbol.recorrer(TipoRecorrido.POSORDEN));
        verificar("niveles", List.of("A", "B", "C", "D", "E", "F", "G"), arbol.recorrer(TipoRecorrido.NIVELES));
        verificar("inorden no aplica", true, lanza(() -> arbol.recorrer(TipoRecorrido.INORDEN)));
        verificar("no se elimina nodo con subunidades", false, arbol.eliminar(x -> x.equals("B")));
        verificar("no se elimina la raíz", false, arbol.eliminar(x -> x.equals("A")));
        verificar("no se elimina inexistente", false, arbol.eliminar(x -> x.equals("Z")));
        verificar("se elimina una hoja", true, arbol.eliminar(x -> x.equals("G")));
        verificar("tras eliminar hoja quedan 6", 6, arbol.contarNodos());
        verificar("D ahora es hoja", true, arbol.buscar(x -> x.equals("D")).esHoja());
        verificar("eliminar rama completa", true, arbol.eliminarRama(x -> x.equals("B")));
        verificar("tras eliminar rama quedan 3", 3, arbol.contarNodos());
        ArbolGeneral<String> vacio = new ArbolGeneral<>();
        verificar("altura general vacío", -1, vacio.altura());
        verificar("hojas general vacío", 0, vacio.contarHojas());
    }

    private static void pruebasTransformacion() {
        System.out.println("--- Transformación general a binario");
        ArbolGeneral<String> general = generalDeEjemplo();
        ArbolBinario<String> binario = Transformador.aBinario(general);
        verificar("mismos nodos", 7, binario.contarNodos());
        verificar("A izquierdo", "B", izquierdoDe(binario, "A"));
        verificar("A derecho", "null", derechoDe(binario, "A"));
        verificar("B izquierdo", "E", izquierdoDe(binario, "B"));
        verificar("B derecho", "C", derechoDe(binario, "B"));
        verificar("C izquierdo", "null", izquierdoDe(binario, "C"));
        verificar("C derecho", "D", derechoDe(binario, "C"));
        verificar("D izquierdo", "G", izquierdoDe(binario, "D"));
        verificar("D derecho", "null", derechoDe(binario, "D"));
        verificar("E derecho", "F", derechoDe(binario, "E"));
        verificar("G sin enlaces", "null", izquierdoDe(binario, "G") + derechoDe(binario, "G").replace("null", ""));
        verificar("ida y vuelta conserva jerarquía", general.mostrarJerarquia(),
                Transformador.aGeneral(binario).mostrarJerarquia());

        ArbolGeneral<String> segundo = new ArbolGeneral<>();
        NodoGeneral<String> r = segundo.crearRaiz("R");
        NodoGeneral<String> a = segundo.agregarHijo(r, "A");
        segundo.agregarHijo(r, "B");
        NodoGeneral<String> c = segundo.agregarHijo(r, "C");
        segundo.agregarHijo(a, "D");
        segundo.agregarHijo(a, "E");
        segundo.agregarHijo(a, "F");
        NodoGeneral<String> g = segundo.agregarHijo(c, "G");
        segundo.agregarHijo(g, "H");
        ArbolBinario<String> segundoBinario = segundo.aBinario();
        verificar("R izquierdo", "A", izquierdoDe(segundoBinario, "R"));
        verificar("A izquierdo", "D", izquierdoDe(segundoBinario, "A"));
        verificar("A derecho", "B", derechoDe(segundoBinario, "A"));
        verificar("B derecho", "C", derechoDe(segundoBinario, "B"));
        verificar("D derecho", "E", derechoDe(segundoBinario, "D"));
        verificar("E derecho", "F", derechoDe(segundoBinario, "E"));
        verificar("C izquierdo", "G", izquierdoDe(segundoBinario, "C"));
        verificar("G izquierdo", "H", izquierdoDe(segundoBinario, "G"));
        verificar("G derecho", "null", derechoDe(segundoBinario, "G"));
        verificar("9 nodos", 9, segundoBinario.contarNodos());
        verificar("grado de R en el general", 3, segundo.gradoDe(x -> x.equals("R")));

        ArbolBinario<String> conHermanoEnRaiz = new ArbolBinario<>();
        Nodo<String> raiz = conHermanoEnRaiz.crearRaiz("X");
        conHermanoEnRaiz.agregarDerecho(raiz, "Y");
        verificar("raíz con derecho no es válida", true, lanza(() -> Transformador.aGeneral(conHermanoEnRaiz)));
    }

    private static void pruebasGestores() {
        System.out.println("--- Gestores y datos de prueba");
        GestorEstudiantes gestor = new GestorEstudiantes();
        DatosPrueba.cargarEstudiantes(gestor);
        verificar("9 estudiantes", 9, gestor.total());
        verificar("altura 3", 3, gestor.altura());
        verificar("matrícula repetida", false, gestor.registrar(new Estudiante(50, "1800000050", "Repetido", 5)));
        verificar("modificar datos", true, gestor.modificar(30, "1800000031", "Nuevo Nombre", 9.9));
        verificar("modificado", "Nuevo Nombre", gestor.buscar(30).getNombres());
        verificar("modificar inexistente", false, gestor.modificar(999, "1800000031", "X", 5));
        verificar("modificar con datos inválidos lanza error", true, lanza(() -> gestor.modificar(30, "123", "X", 5)));
        verificar("datos intactos tras error", "Nuevo Nombre", gestor.buscar(30).getNombres());
        verificar("menor", 20, gestor.menor().getMatricula());
        verificar("mayor", 80, gestor.mayor().getMatricula());
        verificar("eliminar", true, gestor.eliminar(20));
        verificar("eliminar inexistente 9999", false, gestor.eliminar(9999));
        verificar("árbol intacto tras eliminar inexistente", 8, gestor.total());
        verificar("ABB sigue válido", true, gestor.getArbol().esValido());

        GestorEstudiantes vacio = new GestorEstudiantes();
        verificar("promedio general vacío", 0.0, vacio.promedioGeneral());

        GestorOrganizacion org = new GestorOrganizacion();
        DatosPrueba.cargarOrganizacion(org);
        verificar("10 unidades", 10, org.contarNodos());
        verificar("altura 4", 4, org.altura());
        verificar("nivel de SW-N3-B", 4, org.nivel("SW-N3-B"));
        verificar("nivel con minúsculas", 4, org.nivel("sw-n3-b"));
        verificar("grado de UTA", 3, org.grado("UTA"));
        verificar("grado de SW-N3-B", 0, org.grado("SW-N3-B"));
        verificar("nivel inexistente", -1, org.nivel("NOEXISTE"));
        verificar("grado inexistente", -1, org.grado("NOEXISTE"));
        verificar("no se elimina FISEI con carreras", false, org.eliminar("FISEI"));
        verificar("no se elimina la raíz", false, org.eliminar("UTA"));
        verificar("se elimina una hoja", true, org.eliminar("FCHE"));
        verificar("jerarquía inválida rechazada", false,
                org.agregar("UTA", new UnidadAcademica("X1", "Carrera suelta", TipoUnidad.CARRERA)));
        verificar("código repetido rechazado", false,
                org.agregar("UTA", new UnidadAcademica("FCA", "Repetida", TipoUnidad.FACULTAD)));
        verificar("padre inexistente rechazado", false,
                org.agregar("NOEXISTE", new UnidadAcademica("X2", "Huérfana", TipoUnidad.FACULTAD)));
        verificar("binario conserva nodos", org.contarNodos(), org.aBinario().contarNodos());
    }

    private static void pruebasSistemaAcademico() {
        System.out.println("--- Sistema académico: árbol general con un ABB por paralelo");
        GestorSistemaAcademico sistema = new GestorSistemaAcademico();
        DatosPrueba.cargarSistema(sistema);
        verificar("6 unidades", 6, sistema.contarUnidades());
        verificar("altura 4", 4, sistema.altura());
        verificar("estudiantes en B", 9, sistema.estudiantesDe("SW-N3-B").total());
        verificar("estudiantes en A", 2, sistema.estudiantesDe("sw-n3-a").total());
        verificar("total de estudiantes", 11, sistema.totalEstudiantes());
        verificar("promedio general", (74.0 + 9.1 + 7.9) / 11, sistema.promedioGeneral(), 1e-9);
        verificar("unidad que no es paralelo lanza error", true, lanza(() -> sistema.estudiantesDe("SW")));
        verificar("unidad inexistente lanza error", true, lanza(() -> sistema.estudiantesDe("NOEXISTE")));
        verificar("matrícula repetida en el mismo paralelo", false,
                sistema.estudiantesDe("SW-N3-A").registrar(new Estudiante(100, "1800000100", "Repetida", 5)));
        verificar("la misma matrícula en otro paralelo es válida", true,
                sistema.estudiantesDe("SW-N3-A").registrar(new Estudiante(50, "1800000050", "Otro", 5)));
        verificar("no se elimina el nivel con paralelos", false, sistema.eliminarUnidad("SW-N3"));
        verificar("nivel de SW-N3-B", 4, sistema.nivel("SW-N3-B"));
        verificar("binario conserva unidades", sistema.contarUnidades(), sistema.aBinario().contarNodos());
        verificar("promedio vacío", 0.0, new GestorSistemaAcademico().promedioGeneral());
    }

    private static void verificar(String nombre, double esperado, double real, double tolerancia) {
        verificar(nombre, true, Math.abs(esperado - real) <= tolerancia);
    }

    private static void pruebasEstructuras() {
        System.out.println("--- Cola y pila propias");
        Cola<Integer> cola = new Cola<>();
        cola.encolar(1);
        cola.encolar(2);
        cola.encolar(3);
        verificar("cola FIFO", 1, cola.desencolar());
        verificar("frente", 2, cola.verFrente());
        verificar("tamaño cola", 2, cola.tamano());
        cola.desencolar();
        cola.desencolar();
        verificar("cola vacía", true, cola.estaVacia());
        verificar("desencolar vacía lanza error", true, lanza(cola::desencolar));
        Pila<Integer> pila = new Pila<>();
        pila.apilar(1);
        pila.apilar(2);
        verificar("pila LIFO", 2, pila.desapilar());
        verificar("cima", 1, pila.verCima());
        pila.desapilar();
        verificar("pila vacía", true, pila.estaVacia());
        verificar("desapilar vacía lanza error", true, lanza(pila::desapilar));
    }
}
