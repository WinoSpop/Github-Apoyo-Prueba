# Kit de práctica: Árboles en Java

Repositorio de apoyo para la prueba práctica de Estructura de Datos (Unidad 3, Árboles): [Github-Apoyo-Prueba](https://github.com/juanchicoyunda1234/Github-Apoyo-Prueba). Reúne plantillas de código que compilan y están probadas, más la documentación para decidir rápido qué hacer ante cualquier enunciado.

Todo el código está en español, en capas `modelo`, `negocio` y `app`, sin comentarios, con el mismo estilo de los repos del curso (`Nodo<T>`, `ArbolBinario`, validaciones con `IllegalArgumentException` e `IllegalStateException`).

## Qué contiene

```
README.md
compilar.bat / compilar.sh        compila y corre el menú
probar.bat / probar.sh            corre las verificaciones automáticas del kit
.gitignore
  src/
    enums/        TipoRecorrido, TipoUnidad
    estructuras/  Cola, Pila (propias, sin java.util, para recorrido por niveles)
    modelo/       Nodo, NodoGeneral, Estudiante, Producto, UnidadAcademica
    negocio/      ArbolBinario, ArbolBinarioBusqueda, ArbolGeneral, Transformador,
                  GestorEstudiantes, GestorOrganizacion,
                  NodoAcademico, GestorSistemaAcademico (árbol general con un ABB por paralelo),
                  DatosPrueba
    app/          Main (menú de consola)
    pruebas/      PruebasKit (verificaciones automáticas)
  docs/
    01-chuleta-teoria.md
    02-como-atacar-un-enunciado.md
    03-recetas-de-codigo.md
    04-adaptar-la-entidad.md
    05-plantilla-informe.md
    06-git-y-commits.md
    07-diagramas-mermaid.md
    08-errores-y-trampas.md
    09-ejemplos-verificados.md
    10-sustentacion.md
```

## Compilar y ejecutar

Windows (PowerShell o cmd), desde la carpeta del repo:

```
compilar.bat
probar.bat
```

Linux o macOS:

```
sh compilar.sh
sh probar.sh
```

Si en la consola de Windows las tildes salen como signos raros, ejecuta antes `chcp 65001`. En VS Code, abre la carpeta `src` como proyecto Java y ejecuta `app.Main`.

## Mapa rápido: me piden X, uso Y

| Me piden | Uso | Dónde |
|---|---|---|
| Árbol binario con hijo izquierdo y derecho a mano | `ArbolBinario<T>` con `crearRaiz`, `agregarIzquierdo`, `agregarDerecho` | `negocio/ArbolBinario.java` |
| Árbol de búsqueda por una clave (matrícula, código, cédula) | `ArbolBinarioBusqueda<K, T>` con el extractor de clave | `negocio/ArbolBinarioBusqueda.java` |
| Jerarquía con cualquier número de hijos (organigrama, categorías, carpetas) | `ArbolGeneral<T>` | `negocio/ArbolGeneral.java` |
| Una regla de qué puede colgar de qué | Constructor `new ArbolGeneral<>((padre, hijo) -> ...)` | `negocio/GestorOrganizacion.java` |
| Insertar sin repetidos, buscar, modificar, eliminar | `insertar`, `buscar`, `modificar`, `eliminar` del ABB | `ArbolBinarioBusqueda` |
| Preorden, inorden, posorden, por niveles | `recorrer(TipoRecorrido.X)` | `ArbolBinario`, `ArbolGeneral` |
| Traza o prueba de escritorio del recorrido | `trazaRecorrido(tipo)` | `ArbolBinario` |
| Dibujar el árbol en consola para la captura | `mostrarEstructura()`, `mostrarLateral()`, `mostrarJerarquia()` | los tres árboles |
| Altura, contar nodos, contar hojas, nivel, grado | `altura`, `contarNodos`, `contarHojas`, `nivelDe`, `gradoDe` | los tres árboles |
| Menor, mayor, promedio, rango, k-ésimo, sucesor | `menor`, `mayor`, `promedio`, `rango`, `kEsimo`, `sucesorDe` | `ArbolBinarioBusqueda` |
| Eliminar solo si no tiene subunidades (regla A5) | `eliminar(condicion)` del árbol general | `ArbolGeneral` |
| Pasar de árbol general a binario (primer hijo izquierdo, hermano derecho) | `aBinario()` o `Transformador.aBinario` | `Transformador` |
| Volver de binario a general | `Transformador.aGeneral` | `Transformador` |
| Filtrar o contar por una condición | `filtrar(x -> ...)`, `contarSi(x -> ...)` | `ArbolBinario`, `ArbolGeneral` |
| Árbol general donde cada hoja guarda su propio ABB (jerarquía más estudiantes) | `GestorSistemaAcademico` y `NodoAcademico` | `negocio/` |
| Menú de consola | Copiar `app/Main.java` y cambiar los gestores | `app/Main.java` |
| Preguntas de sustentación oral | Respuestas modelo | `docs/10-sustentacion.md` |

## Flujo recomendado durante la prueba

1. Lee el enunciado completo y subraya entidades, reglas y operaciones (`docs/02-como-atacar-un-enunciado.md`).
2. Crea un repositorio nuevo para la prueba y copia de este kit solo lo que necesites.
3. Primer commit con la estructura de carpetas, el README y el `.gitignore`.
4. Adapta la entidad (`docs/04-adaptar-la-entidad.md`), que tarda unos 10 minutos.
5. Copia `estructuras`, `modelo/Nodo*.java` y los árboles de `negocio` sin tocarlos.
6. Escribe el gestor de negocio de tu caso y los datos de prueba.
7. Adapta `Main` y prueba cada opción del menú.
8. Toma las capturas y completa el informe (`docs/05-plantilla-informe.md`).
9. Commits pequeños y con mensaje claro durante todo el proceso (`docs/06-git-y-commits.md`).

## Repositorio

Puedes acceder al repositorio directamente a través del siguiente enlace:
- [https://github.com/juanchicoyunda1234/Github-Apoyo-Prueba](https://github.com/juanchicoyunda1234/Github-Apoyo-Prueba)

## Aviso

Las plantillas están pensadas para que entiendas cada método y puedas sustentarlo. Antes de la prueba, lee `docs/01-chuleta-teoria.md` y ejecuta el menú para ver cómo se comporta cada operación. El código que entregues debe ser el que entiendes y puedes explicar.
