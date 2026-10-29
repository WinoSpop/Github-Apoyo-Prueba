# 01. Chuleta de teoría de árboles

## Terminología

| Término | Definición | Ejemplo con A(B(E,F),C,D(G)) |
|---|---|---|
| Raíz | Nodo sin padre | A |
| Hoja | Nodo sin hijos | E, F, C, G |
| Arco o arista | Enlace padre a hijo. n nodos tienen n - 1 arcos | 6 arcos |
| Grado de un nodo | Número de hijos directos | A = 3, B = 2, C = 0 |
| Grado del árbol | El mayor grado de sus nodos | 3 |
| Nivel de un nodo | Arcos desde la raíz hasta el nodo. La raíz es nivel 0 | A 0, B 1, E 2 |
| Altura del árbol | Arcos del camino más largo de la raíz a una hoja. Es el mayor nivel | 2 |
| Hermanos | Nodos con el mismo padre | B, C y D |
| Subárbol | Un nodo con todos sus descendientes | subárbol de B: B, E, F |

Convenciones que se usan en el curso:

- Altura de un árbol vacío: `-1`. Altura de una hoja o de una raíz sola: `0`.
- `altura = 1 + max(altura(izq), altura(der))`. Para un árbol general: `1 + max` sobre todos los hijos.
- Número de comparaciones al buscar un nodo en el peor caso: `altura + 1`.
- Otros libros miden la altura en nodos. En este curso es en arcos.
- Un árbol en cadena de n nodos tiene altura `n - 1`.

## Tipos

- Árbol general: cada nodo puede tener cualquier número de hijos. Se guarda con una lista de hijos.
- Árbol binario: cada nodo tiene como máximo dos hijos, izquierdo y derecho.
- ABB (árbol binario de búsqueda): binario con regla de orden. Todo lo de la izquierda es menor que el nodo y todo lo de la derecha es mayor. La clave no se repite.

## Los tres recorridos (con `null` se ignora ese lado)

| Recorrido | Orden | Uso típico |
|---|---|---|
| Preorden | raíz, izquierdo, derecho | copiar un árbol, mostrar jerarquía con sangría |
| Inorden | izquierdo, raíz, derecho | en un ABB devuelve los datos ordenados ascendente |
| Posorden | izquierdo, derecho, raíz | eliminar un árbol, contar o sumar de abajo hacia arriba |
| Por niveles | nivel 0, nivel 1, ... | usa una cola |

Comprobaciones: la cantidad de valores es igual a la cantidad de nodos. El inorden de un ABB sale ascendente. En el posorden la raíz queda última y cada nodo va después de todos sus descendientes. En el preorden la raíz va primero.

## ABB: reglas de operación

- Insertar: la raíz es el primer valor. En cada nodo, si la clave es menor se baja a la izquierda y si es mayor a la derecha, hasta encontrar un lugar `null`. El nodo nuevo siempre queda como hoja. La forma del árbol depende del orden de inserción.
- Clave repetida: se rechaza (devuelve `false`).
- Buscar: misma comparación, termina al encontrar la clave o al llegar a `null`.
- Menor: bajar siempre por la izquierda. Mayor: bajar siempre por la derecha.
- Sucesor inorden de un nodo con dos hijos: el menor de su subárbol derecho (una vez a la derecha y luego todo lo posible a la izquierda).
- Modificar: se pueden cambiar los datos que no son la clave. La clave no se edita. Si hay que cambiarla, se elimina el nodo y se inserta uno nuevo con la clave nueva.

## Eliminar en un ABB, tres casos

| Caso | Qué pasa |
|---|---|
| Hoja | El método devuelve `null` y el padre queda con ese lado vacío |
| Un hijo | El método devuelve ese hijo y toma el lugar del nodo eliminado |
| Dos hijos | Se copia el sucesor inorden en el nodo y luego se elimina el sucesor del subárbol derecho |

Plantilla del caso de dos hijos:

```java
T sucesor = minimo(nodo.getDerecho());
nodo.setDato(sucesor);
nodo.setDerecho(eliminar(nodo.getDerecho(), clave(sucesor)));
```

Si el nodo a eliminar solo tiene hijo derecho y devuelves `null`, pierdes todo ese subárbol. Debes devolver `nodo.getDerecho()`.

## Árbol general: operaciones y regla A5

- Buscar: se visita cada hijo, O(n).
- Insertar bajo un padre: se busca el padre y se agrega a su lista de hijos, O(n).
- Eliminar con la regla A5: solo se elimina un nodo hoja. Si tiene subunidades, no se elimina y el método devuelve `false`. La raíz tampoco se elimina.
- Mostrar jerarquía: preorden con dos espacios por nivel.
- Nivel de un código que no existe: `-1`. Grado de un código que no existe: `-1`. Se usa `-1` porque los valores reales siempre son 0 o más.

## Transformación de árbol general a binario (regla A6)

- El primer hijo de un nodo pasa a ser su hijo izquierdo.
- Cada hermano siguiente pasa a ser el hijo derecho de su hermano anterior, formando una cadena hacia la derecha.
- La raíz no tiene hermanos, así que su derecho es siempre `null`.
- Izquierda baja al primer hijo. Derecha se mueve al siguiente hermano.
- Un hijo único va a la izquierda de su padre.
- El resultado conserva todos los nodos y no es un ABB.

Para volver: izquierda es bajar un nivel y derecha es quedarse en el mismo nivel, con el mismo padre.

## Complejidad

| Operación | Árbol general | ABB |
|---|---|---|
| Buscar | O(n) | O(h) |
| Insertar | O(n) | O(h) |
| Recorrer | O(n) | O(n) |
| Modificar | O(n) | O(h) |
| Eliminar | O(n) | O(h) |

h es la altura. Balanceado: h cerca de log n, así que O(log n). En cadena (por ejemplo insertar 10, 20, 30, 40, 50): h = n - 1, así que O(n). La recursividad usa O(h) de pila. Sin caso base se produce un desbordamiento de pila o una `NullPointerException`.

## Casos base y casos límite

| Situación | Resultado |
|---|---|
| `contar(null)` | 0 |
| `contarHojas(null)` | 0 |
| `altura(null)` | -1 |
| Promedio de un árbol vacío | 0.0 (evita dividir entre cero) |
| Eliminar una clave que no existe | `false` y el árbol queda intacto |
| Buscar en un árbol vacío | `null` |
| Crear raíz cuando ya existe | `IllegalStateException` |
| Agregar hijo a un padre `null` o a un lugar ocupado | error, nunca se sobreescribe |
| Eliminar la raíz de la jerarquía | `false` (jerarquía protegida) |

## Capas

`app` interactúa con el usuario y usa a `negocio`. `negocio` aplica las reglas y usa a `modelo`. `modelo` guarda los datos y no depende de nadie. La capa de negocio no usa a la de aplicación.

## Recursividad en una línea

Todo método recursivo tiene un caso base que corta (`if (nodo == null) return ...`) y un caso recursivo que reduce el problema (`izquierdo` y `derecho`). Los valores se arman de abajo hacia arriba: las llamadas esperan a las de adentro.
