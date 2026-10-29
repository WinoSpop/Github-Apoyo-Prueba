# 08. Errores frecuentes y trampas

## Errores de código

| Error | Síntoma | Corrección |
|---|---|---|
| Recursión sin caso base: `return 1 + contar(izq) + contar(der)` sin revisar `null` | `NullPointerException` | `if (nodo == null) return 0;` al inicio |
| Caso base de la altura en 0 | La hoja da 1 en lugar de 0 | `altura(null) = -1`, así `1 + max(-1, -1) = 0` |
| Eliminar un nodo con un solo hijo devolviendo `null` | Se pierde todo el subárbol | Devolver el hijo que existe |
| Eliminar un nodo con dos hijos sin reconectar | Árbol partido | Copiar el sucesor inorden y eliminarlo del subárbol derecho |
| Insertar sin comparar contra cada nodo del camino | El árbol deja de cumplir la regla de orden | Comparar en cada nodo desde la raíz |
| Insertar eligiendo un lado "por defecto" | Valores mal ubicados | El lado lo decide siempre la comparación |
| Sobreescribir un hijo ocupado | Se pierde un subárbol | Validar que el lugar esté libre y lanzar `IllegalStateException` |
| Crear una raíz cuando ya existe | Se pierde todo el árbol | Lanzar `IllegalStateException` |
| Modificar la clave en el mismo nodo | Búsquedas que no encuentran datos que sí están | Eliminar el nodo e insertar uno nuevo |
| Modificar campo por campo sin validar antes | Objeto a medias si el tercer dato es inválido | Crear un objeto temporal que valide todo y luego aplicar los cambios |
| Eliminar un nodo del árbol general con subunidades | Se pierde toda una rama | Revisar `esHoja()` y devolver `false` |
| `substring` o `equals` sin tener en cuenta mayúsculas | Código "uta" no encuentra "UTA" | Normalizar con `trim().toUpperCase()` al guardar y al buscar |
| Menú que se cierra con una excepción | El programa termina | `try`/`catch` de `IllegalArgumentException` e `IllegalStateException` en el ciclo del menú |
| `Scanner.nextInt()` seguido de `nextLine()` | La siguiente lectura sale vacía | Leer todo con `nextLine()` y convertir con `Integer.parseInt` |
| Cambiar la lista que se está recorriendo | `ConcurrentModificationException` | Quitar fuera del recorrido, o buscar primero y quitar después |
| Comparar `Integer` con `==` | Falla con valores mayores de 127 | Usar `.equals` o `compareTo` |

## Trampas de lectura (de los quizzes y simulacros)

| Trampa | Cómo evitarla |
|---|---|
| Confundir nodos con arcos | Altura y nivel se cuentan en arcos. Raíz sola = 0 |
| Confundir grado, nivel y altura | Grado = hijos directos. Nivel = distancia a la raíz. Altura = camino más largo hacia una hoja |
| Altura de un árbol vacío | -1 |
| "Hermanos = mismo nivel" | Hermanos = mismo padre. Los primos están en el mismo nivel y no son hermanos |
| "Derecho = hijo" en el binario transformado | Izquierdo = primer hijo. Derecho = siguiente hermano |
| Un hijo único en la transformación | Va a la izquierda |
| "NIVEL 3" como nombre de un nodo | Es el nombre, no el nivel del árbol. Cuenta desde la raíz en 0 |
| "ABB siempre es O(log n)" | Es O(h). En cadena es O(n). Mira el orden de inserción |
| "Nunca se puede cambiar la matrícula" | La clave nunca se edita; para cambiarla se elimina el nodo y se inserta uno nuevo |
| Opciones con "siempre" y "nunca" | Desconfía: casi siempre son el distractor |
| Inorden de un ABB | Sale ascendente. Si tu resultado no sale ordenado, hay un error |
| Posorden | La raíz queda última y cada nodo va después de sus descendientes |
| Contar los valores de un recorrido | Debe haber tantos valores como nodos |
| Qué capa usa a cuál | `app` usa `negocio` y `negocio` usa `modelo`, nunca al revés |

## Qué hacer cuando te trabas

1. Dibuja el árbol con cajas: cada "izquierdo" y "derecho" es un subárbol completo.
2. Haz una traza con un árbol de 3 nodos.
3. Escribe el caso base antes que el caso recursivo.
4. Corre `esValido()` en tu ABB y compara el inorden con la lista ordenada.
5. Si dos respuestas se parecen, lee otra vez cada palabra de las dos.
