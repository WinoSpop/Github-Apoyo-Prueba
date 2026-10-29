# 03. Recetas de código

Todas las recetas usan clases de este kit. El código no lleva comentarios; la explicación está en el texto.

Las clases `Estudiante`, `Producto` y los gestores académicos de los ejemplos están en `ejemplo-sistema-academico/`. Los árboles genéricos son idénticos en `plantilla-base/` y en el ejemplo.

## Referencia rápida de la API

### `ArbolBinario<T>` (base, sin orden)

| Método | Devuelve | Nota |
|---|---|---|
| `crearRaiz(T)` | `Nodo<T>` | `IllegalStateException` si ya hay raíz |
| `agregarIzquierdo(padre, T)` y `agregarDerecho(padre, T)` | `Nodo<T>` | error si el padre es `null` o el lugar está ocupado |
| `buscarSi(Predicate<T>)` | `Nodo<T>` o `null` | recorre todo el árbol, O(n) |
| `contarNodos()`, `contarHojas()`, `altura()` | `int` | vacío: 0, 0 y -1 |
| `contarSi(Predicate<T>)` | `int` | cuenta los que cumplen la condición |
| `filtrar(Predicate<T>)` | `List<T>` | en inorden |
| `sumar(ToDoubleFunction<T>)`, `promedio(ToDoubleFunction<T>)` | `double` | promedio vacío: 0.0 |
| `nivelDe(Predicate<T>)`, `gradoDe(Predicate<T>)` | `int` | -1 si no existe |
| `camino(Predicate<T>)` | `List<T>` | desde la raíz hasta el nodo, vacío si no existe |
| `hojas()` | `List<T>` | de izquierda a derecha |
| `esCadena()`, `esBalanceado()` | `boolean` | |
| `recorrer(TipoRecorrido)` | `List<T>` | PREORDEN, INORDEN, POSORDEN, NIVELES |
| `trazaRecorrido(tipo)` y `trazaRecorrido(tipo, etiqueta)` | `String` | una línea por paso, con sangría |
| `mostrarEstructura()`, `mostrarLateral()` | `String` | dibujo para la captura; acepta una función de etiqueta |

### `ArbolBinarioBusqueda<K extends Comparable<K>, T>` (hereda todo lo anterior)

| Método | Devuelve | Nota |
|---|---|---|
| `insertar(T)` | `boolean` | `false` si la clave ya existe |
| `insertarIterativo(T)` | `boolean` | mismo resultado, con bucle |
| `buscar(K)`, `buscarIterativo(K)` | `T` o `null` | O(h) |
| `buscarNodo(K)`, `existe(K)` | `Nodo<T>`, `boolean` | |
| `comparaciones(K)` | `int` | cuántos nodos se visitan al buscar |
| `eliminar(K)` | `boolean` | 3 casos, `false` si no existe |
| `modificar(K, Consumer<T>)` | `boolean` | cambia datos que no son la clave |
| `cambiarClave(K, T)` | `boolean` | elimina e inserta, `false` si la nueva ya existe |
| `menor()`, `mayor()` | `T` o `null` | |
| `rango(K min, K max)` | `List<T>` | en orden ascendente |
| `kEsimo(int k)` | `T` o `null` | k = 1 es el menor |
| `sucesorDe(K)`, `predecesorDe(K)` | `T` o `null` | siguiente o anterior en inorden |
| `esValido()` | `boolean` | verifica la regla de orden de todo el árbol |

### `ArbolGeneral<T>`

| Método | Devuelve | Nota |
|---|---|---|
| `crearRaiz(T)` | `NodoGeneral<T>` | |
| `agregarHijo(NodoGeneral, T)` | `NodoGeneral<T>` | lanza error si la regla de hijo lo prohíbe |
| `agregarHijo(Predicate<T> padre, T)` | `boolean` | `false` si el padre no existe o la regla lo prohíbe |
| `buscar(Predicate<T>)` | `NodoGeneral<T>` o `null` | |
| `eliminar(Predicate<T>)` | `boolean` | solo hojas, no elimina la raíz (regla A5) |
| `eliminarRama(Predicate<T>)` | `boolean` | elimina el nodo con todos sus descendientes |
| `padreDe`, `hermanosDe`, `camino`, `hojas` | | |
| `contarNodos`, `contarHojas`, `altura`, `gradoArbol` | `int` | |
| `nivelDe`, `gradoDe`, `contarDescendientes` | `int` | -1 si no existe |
| `recorrer(tipo)` | `List<T>` | PREORDEN, POSORDEN y NIVELES. El INORDEN lanza error |
| `mostrarJerarquia()` | `String` | sangría de dos espacios por nivel |
| `aBinario()` | `ArbolBinario<T>` | regla A6 |

## Receta 1: ABB con una entidad

```java
ArbolBinarioBusqueda<Integer, Estudiante> arbol =
        new ArbolBinarioBusqueda<>(Estudiante::getMatricula);

arbol.insertar(new Estudiante(50, "1800000050", "Ana", 8.5));
Estudiante e = arbol.buscar(50);
arbol.eliminar(50);
```

El segundo parámetro de tipo es la entidad y el primero es el tipo de su clave. La clave sale de la referencia a método.

## Receta 2: clave de texto

```java
ArbolBinarioBusqueda<String, Producto> inventario =
        new ArbolBinarioBusqueda<>(Producto::getCodigo);
```

`String` ya es `Comparable`. Si los códigos pueden venir en minúsculas, normalízalos en el constructor de la entidad (`toUpperCase()`), como hace `Producto`.

## Receta 3: clave compuesta

```java
ArbolBinarioBusqueda<String, Matricula> arbol =
        new ArbolBinarioBusqueda<>(m -> m.getPeriodo() + "-" + String.format("%05d", m.getNumero()));
```

Si los números van con ceros a la izquierda, el orden por texto coincide con el orden numérico.

## Receta 4: listar ordenado, ascendente y descendente

```java
List<Estudiante> ascendente = arbol.recorrer(TipoRecorrido.INORDEN);

List<Estudiante> descendente = new ArrayList<>(ascendente);
Collections.reverse(descendente);
```

## Receta 5: buscar por un atributo que no es la clave

```java
Nodo<Estudiante> nodo = arbol.buscarSi(e -> e.getCedula().equals("1800000050"));
Estudiante encontrado = nodo == null ? null : nodo.getDato();
```

Es O(n), porque el orden del ABB no sirve para otro atributo.

## Receta 6: contar y filtrar por condición

```java
int aprobados = arbol.contarSi(e -> e.getPromedio() >= 7.0);
List<Estudiante> destacados = arbol.filtrar(e -> e.getPromedio() >= 9.0);
List<Estudiante> mayores = arbol.filtrar(e -> e.getMatricula() > 50);
```

## Receta 7: estadísticas

```java
int total = arbol.contarNodos();
int altura = arbol.altura();
int hojas = arbol.contarHojas();
double promedio = arbol.promedio(Estudiante::getPromedio);
double suma = arbol.sumar(Estudiante::getPromedio);
Estudiante menor = arbol.menor();
Estudiante mayor = arbol.mayor();
```

El mejor o peor por otro atributo, con un `Comparator`:

```java
Estudiante mejor = arbol.recorrer(TipoRecorrido.INORDEN).stream()
        .max(Comparator.comparingDouble(Estudiante::getPromedio))
        .orElse(null);
```

## Receta 8: ordenar por otra clave

```java
List<Estudiante> porNombre = new ArrayList<>(arbol.recorrer(TipoRecorrido.INORDEN));
porNombre.sort(Comparator.comparing(Estudiante::getNombres));
```

## Receta 9: modificar sin tocar la clave

```java
boolean ok = arbol.modificar(50, e -> {
    e.setNombres("Ana María");
    e.setPromedio(9.0);
});
```

Si el cambio puede lanzar una excepción de validación, valida primero con un objeto temporal para no dejar el estudiante a medias (`GestorEstudiantes.modificar` lo hace así):

```java
new Estudiante(matricula, cedula, nombres, promedio);
```

## Receta 10: cambiar la clave

```java
boolean ok = arbol.cambiarClave(50, new Estudiante(55, "1800000050", "Ana", 8.5));
```

Elimina el nodo y vuelve a insertar con la clave nueva. Devuelve `false` si la nueva ya existe o si la actual no existe.

## Receta 11: permitir claves repetidas

El kit rechaza repetidos. Si el enunciado los permite, copia `ArbolBinarioBusqueda` y borra este bloque de `insertar` e `insertarIterativo`:

```java
if (existe(clave(dato))) {
    return false;
}
```

y en el iterativo el `if (comparacion == 0) return false;`. Los repetidos irán a la derecha. Con repetidos la eliminación y la búsqueda solo encuentran uno: aclara en el informe qué hace tu diseño.

## Receta 12: ABB por rango y vecinos

```java
List<Estudiante> entre = arbol.rango(30, 60);
Estudiante siguiente = arbol.sucesorDe(50);
Estudiante anterior = arbol.predecesorDe(50);
Estudiante tercero = arbol.kEsimo(3);
```

## Receta 13: árbol general con regla de qué puede colgar de qué

```java
ArbolGeneral<UnidadAcademica> arbol = new ArbolGeneral<>(
        (padre, hijo) -> padre.getTipo().puedeTenerHijo(hijo.getTipo()));

arbol.crearRaiz(new UnidadAcademica("UTA", "Universidad", TipoUnidad.UNIVERSIDAD));
boolean ok = arbol.agregarHijo(u -> u.getCodigo().equals("UTA"),
        new UnidadAcademica("FISEI", "Sistemas", TipoUnidad.FACULTAD));
```

Con otra regla, por ejemplo categorías con máximo de tres niveles:

```java
ArbolGeneral<Categoria> arbol = new ArbolGeneral<>((padre, hijo) -> padre.getNivel() < 3);
```

## Receta 14: eliminar con la regla A5

```java
boolean eliminada = arbol.eliminar(u -> u.getCodigo().equals("FCHE"));
```

Devuelve `false` si no existe, si es la raíz o si tiene subunidades. Para borrar una rama entera, que no es la regla A5, usa `eliminarRama`.

## Receta 15: árbol general donde las hojas guardan un ABB

Es el patrón del sistema académico. Cada nodo guarda la unidad y, si es un paralelo, su propio ABB de estudiantes. Mira `NodoAcademico` y `GestorSistemaAcademico`:

```java
GestorSistemaAcademico sistema = new GestorSistemaAcademico();
DatosPrueba.cargarSistema(sistema);

sistema.estudiantesDe("SW-N3-B").registrar(new Estudiante(75, "1800000075", "Sara", 9.0));
int total = sistema.totalEstudiantes();
double promedio = sistema.promedioGeneral();
```

Para tu caso, cambia `NodoAcademico` por una clase que guarde tu entidad del árbol general más tu estructura de datos interna.

## Receta 16: pasar de general a binario y volver

```java
ArbolBinario<UnidadAcademica> binario = arbol.aBinario();
System.out.println(binario.mostrarEstructura());

ArbolGeneral<UnidadAcademica> original = Transformador.aGeneral(binario);
```

## Receta 17: mostrar el camino de la raíz a un nodo

```java
List<UnidadAcademica> ruta = arbol.camino(u -> u.getCodigo().equals("SW-N3-B"));
```

Para mostrarlo: `UTA > FISEI > SW > SW-N3 > SW-N3-B`.

```java
String texto = ruta.stream().map(UnidadAcademica::getCodigo).collect(Collectors.joining(" > "));
```

## Receta 18: contar los nodos de un subárbol

```java
int carreras = arbol.contarDescendientes(u -> u.getCodigo().equals("FISEI"));
```

Cuenta todos los descendientes sin incluir al nodo. Devuelve -1 si el código no existe.

## Receta 19: recorrido a mano si te lo piden "sin usar las listas del kit"

```java
private void inorden(Nodo<T> nodo) {
    if (nodo == null) {
        return;
    }
    inorden(nodo.getIzquierdo());
    System.out.println(nodo.getDato());
    inorden(nodo.getDerecho());
}
```

El preorden mueve la línea de impresión antes de las dos llamadas y el posorden después.

## Receta 20: validaciones y errores

Regla general del curso:

- Datos inválidos o estado imposible: se lanza `IllegalArgumentException` o `IllegalStateException` con un mensaje claro.
- "No encontrado" o "no se pudo": el método devuelve `false` o `null` y el árbol queda intacto. El menú muestra el mensaje.
- El menú captura las excepciones con `try`/`catch` para no cerrarse.

```java
try {
    ejecutar(opcion);
} catch (IllegalArgumentException | IllegalStateException e) {
    System.out.println("Error: " + e.getMessage());
}
```

## Receta 21: altura medida en nodos

Si el enunciado define altura como cantidad de niveles o de nodos del camino más largo, suma uno:

```java
int alturaEnNodos = arbol.altura() + 1;
```

Aclara siempre en el informe qué definición usas.
