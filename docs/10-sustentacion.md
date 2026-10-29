# 10. Preguntas de sustentación

Si te piden explicar tu código, estas son las preguntas más probables. Prepara una respuesta de dos o tres frases para cada una, con tus propias palabras.

## Sobre la estructura

**¿Por qué usaste un ABB y no una lista?**
En una lista ordenada buscar cuesta O(n) porque hay que recorrer nodo por nodo. En un ABB cuesta O(h), que en un árbol balanceado es O(log n).

**¿Por qué usaste un árbol general para las unidades?**
Porque cada unidad puede tener cualquier número de hijos y no hay regla de orden entre ellas. Se guardan en una lista de hijos.

**¿Qué pasa si insertas los datos ya ordenados?**
El ABB queda como una cadena, la altura es n - 1 y buscar pasa a O(n).

**¿Qué diferencia hay entre árbol general, binario y ABB?**
El general admite cualquier cantidad de hijos. El binario tiene máximo dos. El ABB es un binario con la regla de que lo de la izquierda es menor y lo de la derecha es mayor.

**¿Cómo se relaciona el árbol general con el ABB en tu sistema?**
Cada nodo de la jerarquía que es un paralelo guarda su propio ABB de estudiantes. La relación es de cero o uno: los demás nodos no tienen ABB.

## Sobre las operaciones

**¿Cómo funciona insertar?**
Se compara la clave con cada nodo desde la raíz: si es menor se baja a la izquierda y si es mayor a la derecha, hasta llegar a un lugar vacío. El nodo nuevo queda siempre como hoja. Si la clave ya existe, se rechaza.

**¿Cómo funciona eliminar?**
Hay tres casos. Hoja: el padre queda con ese lado vacío. Un hijo: el hijo ocupa el lugar del nodo. Dos hijos: se copia el sucesor inorden (el menor del subárbol derecho) y se elimina ese sucesor.

**¿Por qué el sucesor inorden?**
Porque es el siguiente valor en orden: es mayor que todo lo de la izquierda y menor que todo lo demás de la derecha, así que la regla del ABB se mantiene.

**¿Por qué no se puede modificar la clave?**
Porque la posición del nodo depende de la clave. Si la cambias en el mismo nodo, queda en un lugar donde ya no le corresponde y las búsquedas fallan. Si hay que cambiarla, se elimina el nodo y se inserta uno nuevo.

**¿Por qué solo se eliminan hojas en el árbol general?**
Es la regla A5: así no se pierden subunidades por accidente. Si tiene subunidades, el método devuelve `false` y no cambia nada.

**¿Qué hace el recorrido inorden en un ABB?**
Devuelve los datos ordenados de menor a mayor.

**¿Para qué sirve el posorden?**
Para operaciones que necesitan procesar primero a los hijos, como eliminar un árbol completo o contar de abajo hacia arriba.

**¿Cómo se transforma un árbol general en binario?**
El primer hijo de cada nodo pasa a ser su hijo izquierdo y cada hermano siguiente pasa a ser el hijo derecho de su hermano anterior.

## Sobre el diseño

**¿Qué hace cada capa?**
`modelo` guarda y valida los datos. `negocio` aplica las reglas y maneja los árboles. `app` solo lee datos y muestra resultados.

**¿Dónde validas los datos?**
En el constructor y en los setters del modelo, y en el gestor antes de modificar, usando un objeto temporal para no dejar datos a medias.

**¿Por qué devuelves `boolean` y no lanzas excepción cuando no se encuentra?**
Porque "no encontrado" es un resultado normal, no un error. Las excepciones se usan para datos inválidos o estados imposibles.

**¿Qué complejidad tiene tu búsqueda?**
O(h). En un ABB balanceado es O(log n) y en una cadena es O(n).

**¿Qué pasa con un árbol vacío?**
Altura -1, conteo 0, promedio 0.0, buscar devuelve `null` y eliminar devuelve `false`.

**¿Cómo evitas que el programa se caiga?**
El menú captura `IllegalArgumentException` e `IllegalStateException` y muestra el mensaje.

## Sobre recursividad

**¿Qué es el caso base y por qué es necesario?**
Es la condición que corta la recursión, por ejemplo `nodo == null`. Sin él la llamada intenta bajar más allá de una hoja y se produce una `NullPointerException` o un desbordamiento de pila.

**¿Cómo se calcula la altura recursivamente?**
`altura(null) = -1` y `altura(nodo) = 1 + max(altura(izquierdo), altura(derecho))`. Una hoja da 0.

**¿Qué es mejor, la versión recursiva o la iterativa?**
Las dos son O(h). La recursiva es más corta y usa O(h) de pila. La iterativa evita el riesgo de desbordar la pila en árboles muy profundos.

## Cómo responder bien

- Responde primero con la idea y luego con un ejemplo corto.
- Si no sabes algo, di qué parte entiendes y razona en voz alta.
- Dibuja un árbol pequeño de 3 a 5 nodos para explicar cualquier operación.
