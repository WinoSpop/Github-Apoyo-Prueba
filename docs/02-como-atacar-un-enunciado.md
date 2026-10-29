# 02. Cómo atacar un enunciado de práctica

## 1. Lee y subraya (5 a 10 minutos)

Copia el enunciado en un archivo `ENUNCIADO.md` del repo y marca:

| Pregunta | Dónde se responde |
|---|---|
| ¿Qué entidades hay? (estudiante, producto, unidad, departamento) | `modelo` |
| ¿Cuál es la clave única de cada entidad? | clave del ABB |
| ¿Hay una jerarquía con cualquier número de hijos? | `ArbolGeneral` |
| ¿Hay que buscar rápido por una clave ordenada? | `ArbolBinarioBusqueda` |
| ¿Qué reglas se imponen? (no repetir clave, solo borrar hojas, no cambiar la clave, qué puede colgar de qué) | validaciones en `negocio` |
| ¿Qué operaciones piden? | métodos del gestor y opciones del menú |
| ¿Qué entregables piden? (código, diagrama, informe, capturas, commits, sustentación) | lista de control final |

Si el enunciado pide las dos estructuras (como el sistema académico: jerarquía general y un ABB por cada nodo hoja), usa las dos: el árbol general guarda las unidades y cada nodo que lo necesite guarda su propio ABB.

## 2. Decide la estructura

| Si el enunciado dice | Usa |
|---|---|
| organigrama, categorías, carpetas, facultades y carreras | árbol general |
| registros ordenados por matrícula, código o cédula, sin repetidos | ABB |
| "árbol binario" sin regla de orden, o ubicación manual izquierda y derecha | árbol binario común |
| "convertir", "transformar" una jerarquía a binario | `Transformador` |

## 3. Construye en este orden

1. `modelo`: entidad con validaciones en el constructor y en los setters. La clave va como `final`.
2. `estructuras` y `negocio` de árboles: se copian de `plantilla-base/` sin tocar.
3. Gestor de negocio: una clase por caso (por ejemplo `GestorBiblioteca`) que arma el árbol con el extractor de clave o la regla de hijo.
4. `DatosPrueba`: al menos 8 o 10 registros que ejerciten todos los casos.
5. `Main`: menú que solo lee datos, llama al gestor y muestra resultados.
6. Pruebas: ejecuta cada opción con casos normales y casos límite.

## 4. Reparte el tiempo

| Fase | Tiempo aproximado |
|---|---|
| Leer y decidir | 10% |
| Modelo y gestor | 40% |
| Menú y datos de prueba | 20% |
| Pruebas y capturas | 15% |
| Informe y commits finales | 15% |

Lo que más puntos suele dar: que compile, que el menú funcione, que las reglas del enunciado se cumplan y que los casos límite no rompan el programa.

## 5. Casos límite que siempre se revisan

- Árbol vacío (buscar, eliminar, recorrer, promedio, altura).
- Clave repetida al insertar.
- Clave inexistente al buscar, modificar o eliminar.
- Eliminar una hoja, un nodo con un hijo, un nodo con dos hijos y la raíz.
- Intentar cambiar la clave.
- Datos inválidos (texto vacío, número negativo, cédula con menos de 10 dígitos). Valida antes de modificar para no dejar el objeto a medias.
- Árbol en cadena.
- Jerarquía: eliminar con subunidades, eliminar la raíz, agregar un hijo del tipo que no corresponde.

## 6. Lista de control antes de entregar

- [ ] Compila desde cero con `compilar.bat` o `javac` sin errores.
- [ ] El menú funciona de principio a fin sin cerrarse por una excepción.
- [ ] Cada regla del enunciado tiene una validación y una captura.
- [ ] No hay comentarios de relleno ni código que no uses.
- [ ] El README explica cómo compilar, ejecutar y qué hace cada opción.
- [ ] El informe tiene capturas de cada operación y de los casos límite.
- [ ] Hay commits pequeños con mensajes claros y de tu propia cuenta.
- [ ] Puedes explicar con tus palabras cada clase y cada método.

## 7. Si algo no sale

- Un error de compilación: lee la primera línea del mensaje, suele ser un `import` o un tipo mal escrito.
- Una `NullPointerException`: falta un caso base o un `if (x == null)`.
- Resultados raros en el ABB: revisa que no se haya modificado la clave y llama a `esValido()`.
- Poco tiempo: entrega primero el flujo completo con operaciones básicas (insertar, buscar, recorrer, eliminar) y deja para el final lo extra.
