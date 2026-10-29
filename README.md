# Kit de práctica: Árboles en Java

Repositorio de apoyo para la prueba práctica de Estructura de Datos (Unidad 3, Árboles). Reúne plantillas de código que compilan y están probadas, más la documentación para decidir rápido qué hacer ante cualquier enunciado.

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
  docs/           Documentación de apoyo (ver enlaces abajo)
```

### Documentación y guías del kit

- [docs/01-chuleta-teoria.md](docs/01-chuleta-teoria.md): Chuleta de teoría de árboles
- [docs/02-como-atacar-un-enunciado.md](docs/02-como-atacar-un-enunciado.md): Cómo atacar un enunciado de examen
- [docs/03-recetas-de-codigo.md](docs/03-recetas-de-codigo.md): Recetas de código listas para usar
- [docs/04-adaptar-la-entidad.md](docs/04-adaptar-la-entidad.md): Adaptar la entidad al enunciado
- [docs/05-plantilla-informe.md](docs/05-plantilla-informe.md): Plantilla de informe para la entrega
- [docs/06-git-y-commits.md](docs/06-git-y-commits.md): Guía de Git y frecuencia de commits
- [docs/07-diagramas-mermaid.md](docs/07-diagramas-mermaid.md): Generación de diagramas Mermaid
- [docs/08-errores-y-trampas.md](docs/08-errores-y-trampas.md): Errores y trampas frecuentes
- [docs/09-ejemplos-verificados.md](docs/09-ejemplos-verificados.md): Ejemplos completos verificados
- [docs/10-sustentacion.md](docs/10-sustentacion.md): Preguntas frecuentes de sustentación oral

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
| Árbol binario con hijo izquierdo y derecho a mano | `ArbolBinario<T>` con `crearRaiz`, `agregarIzquierdo`, `agregarDerecho` | [`src/negocio/ArbolBinario.java`](src/negocio/ArbolBinario.java) |
| Árbol de búsqueda por una clave (matrícula, código, cédula) | `ArbolBinarioBusqueda<K, T>` con el extractor de clave | [`src/negocio/ArbolBinarioBusqueda.java`](src/negocio/ArbolBinarioBusqueda.java) |
| Jerarquía con cualquier número de hijos (organigrama, categorías, carpetas) | `ArbolGeneral<T>` | [`src/negocio/ArbolGeneral.java`](src/negocio/ArbolGeneral.java) |
| Una regla de qué puede colgar de qué | Constructor `new ArbolGeneral<>((padre, hijo) -> ...)` | [`src/negocio/GestorOrganizacion.java`](src/negocio/GestorOrganizacion.java) |
| Insertar sin repetidos, buscar, modificar, eliminar | `insertar`, `buscar`, `modificar`, `eliminar` del ABB | [`src/negocio/ArbolBinarioBusqueda.java`](src/negocio/ArbolBinarioBusqueda.java) |
| Preorden, inorden, posorden, por niveles | `recorrer(TipoRecorrido.X)` | [`ArbolBinario`](src/negocio/ArbolBinario.java), [`ArbolGeneral`](src/negocio/ArbolGeneral.java) |
| Traza o prueba de escritorio del recorrido | `trazaRecorrido(tipo)` | [`src/negocio/ArbolBinario.java`](src/negocio/ArbolBinario.java) |
| Dibujar el árbol en consola para la captura | `mostrarEstructura()`, `mostrarLateral()`, `mostrarJerarquia()` | los tres árboles |
| Altura, contar nodos, contar hojas, nivel, grado | `altura`, `contarNodos`, `contarHojas`, `nivelDe`, `gradoDe` | los tres árboles |
| Menor, mayor, promedio, rango, k-ésimo, sucesor | `menor`, `mayor`, `promedio`, `rango`, `kEsimo`, `sucesorDe` | [`src/negocio/ArbolBinarioBusqueda.java`](src/negocio/ArbolBinarioBusqueda.java) |
| Eliminar solo si no tiene subunidades (regla A5) | `eliminar(condicion)` del árbol general | [`src/negocio/ArbolGeneral.java`](src/negocio/ArbolGeneral.java) |
| Pasar de árbol general a binario (primer hijo izquierdo, hermano derecho) | `aBinario()` o `Transformador.aBinario` | [`src/negocio/Transformador.java`](src/negocio/Transformador.java) |
| Volver de binario a general | `Transformador.aGeneral` | [`src/negocio/Transformador.java`](src/negocio/Transformador.java) |
| Filtrar o contar por una condición | `filtrar(x -> ...)`, `contarSi(x -> ...)` | [`ArbolBinario`](src/negocio/ArbolBinario.java), [`ArbolGeneral`](src/negocio/ArbolGeneral.java) |
| Árbol general donde cada hoja guarda su propio ABB (jerarquía más estudiantes) | `GestorSistemaAcademico` y `NodoAcademico` | [`src/negocio/`](src/negocio/) |
| Menú de consola | Copiar `app/Main.java` y cambiar los gestores | [`src/app/Main.java`](src/app/Main.java) |
| Preguntas de sustentación oral | Respuestas modelo | [`docs/10-sustentacion.md`](docs/10-sustentacion.md) |

## Flujo recomendado durante la prueba

1. Lee el enunciado completo y subraya entidades, reglas y operaciones ([docs/02-como-atacar-un-enunciado.md](docs/02-como-atacar-un-enunciado.md)).
2. Crea un repositorio nuevo para la prueba y copia de este kit solo lo que necesites.
3. Primer commit con la estructura de carpetas, el README y el `.gitignore`.
4. Adapta la entidad ([docs/04-adaptar-la-entidad.md](docs/04-adaptar-la-entidad.md)), que tarda unos 10 minutos.
5. Copia `estructuras`, `modelo/Nodo*.java` y los árboles de `negocio` sin tocarlos.
6. Escribe el gestor de negocio de tu caso y los datos de prueba.
7. Adapta `Main` y prueba cada opción del menú.
8. Toma las capturas y completa el informe ([docs/05-plantilla-informe.md](docs/05-plantilla-informe.md)).
9. Commits pequeños y con mensaje claro durante todo el proceso ([docs/06-git-y-commits.md](docs/06-git-y-commits.md)).

## Aviso

Las plantillas están pensadas para que entiendas cada método y puedas sustentarlo. Antes de la prueba, lee [docs/01-chuleta-teoria.md](docs/01-chuleta-teoria.md) y ejecuta el menú para ver cómo se comporta cada operación. El código que entregues debe ser el que entiendes y puedes explicar.

