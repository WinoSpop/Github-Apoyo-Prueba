# 🌳 Kit de Práctica: Árboles en Java (V2)

<p align="left">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Pruebas-231%20Pasadas%20(100%25)-success?style=for-the-badge" alt="Tests passing" />
  <img src="https://img.shields.io/badge/Documentación-10%20Guías-blue?style=for-the-badge" alt="Docs" />
  <img src="https://img.shields.io/badge/Arquitectura-Capas%20(Modelo%20%7C%20Negocio%20%7C%20App)-informational?style=for-the-badge" alt="Arquitectura" />
</p>

Repositorio de apoyo para la prueba práctica de **Estructura de Datos (Unidad 3: Árboles)**. Reúne plantillas de código listas para compilar, probadas al 100%, más documentación exhaustiva para atacar cualquier enunciado con rapidez.

> **Características clave**: Todo el código está en español, estructurado en capas (`modelo`, `negocio`, `app`), sin dependencias externas (`Cola` y `Pila` propias), con nomenclatura del curso (`Nodo<T>`, `ArbolBinario`) y validaciones robustas mediante `IllegalArgumentException` e `IllegalStateException`.

---

## 📦 Dos proyectos, un solo kit

| Carpeta | Propósito | ¿Cuándo usarla? |
|---|---|---|
| 🚀 [`plantilla-base/`](plantilla-base/) | **Plantilla inicial limpia.** Árboles genéricos con una entidad neutra (`Elemento`). | **Empieza aquí tu examen.** Cópiala y adáptala a la entidad del enunciado. |
| 🎓 [`ejemplo-sistema-academico/`](ejemplo-sistema-academico/) | **Proyecto completo resuelto.** Dominio académico real (`Estudiante`, `UnidadAcademica`, jerarquía general + ABB por paralelo). | **Consulta y referencia.** Ver cómo se resuelven relaciones complejas y validaciones. |
| 📚 [`docs/`](docs/) | **10 Guías teóricas y prácticas.** | Durante todo el desarrollo, informe y sustentación. |

> ℹ️ *Nota: Las implementaciones de estructuras (`Cola`, `Pila`), nodos (`Nodo`, `NodoGeneral`) y árboles (`ArbolBinario`, `ArbolBinarioBusqueda`, `ArbolGeneral`, `Transformador`) son idénticas y 100% compatibles entre ambos proyectos.*

---

## 📁 Estructura del repositorio

```
README.md
.gitignore
plantilla-base/
  compilar.bat / compilar.sh      compila y corre el menú
  probar.bat / probar.sh          corre las verificaciones
  src/
    enums/        TipoRecorrido
    estructuras/  Cola, Pila (propias, sin java.util)
    modelo/       Nodo, NodoGeneral, Elemento
    negocio/      ArbolBinario, ArbolBinarioBusqueda, ArbolGeneral, Transformador,
                  GestorElementos, GestorJerarquia, DatosPrueba
    app/          Main
    pruebas/      PruebasBase
ejemplo-sistema-academico/
  compilar.bat / compilar.sh      compila y corre el menú
  probar.bat / probar.sh          corre las verificaciones automáticas del kit
  src/
    enums/        TipoRecorrido, TipoUnidad
    estructuras/  Cola, Pila
    modelo/       Nodo, NodoGeneral, Estudiante, Producto, UnidadAcademica
    negocio/      árboles, GestorEstudiantes, GestorOrganizacion,
                  NodoAcademico, GestorSistemaAcademico, DatosPrueba
    app/          Main
    pruebas/      PruebasKit
docs/             10 guías compartidas de apoyo (ver enlaces abajo)
```

---

## 📚 Documentación y guías del kit

Las guías están organizadas para consultar rápidamente según la fase del examen:

### 📖 Fase 1: Análisis y Modelado
- 📄 [docs/01-chuleta-teoria.md](docs/01-chuleta-teoria.md) — Chuleta rápida de teoría de árboles (definiciones, alturas, propiedades).
- 📄 [docs/02-como-atacar-un-enunciado.md](docs/02-como-atacar-un-enunciado.md) — Metodología paso a paso para desglosar el enunciado.
- 📄 [docs/04-adaptar-la-entidad.md](docs/04-adaptar-la-entidad.md) — Cómo transformar `Elemento` en la entidad requerida en ~10 minutos.

### 🛠️ Fase 2: Implementación y Código
- 📄 [docs/03-recetas-de-codigo.md](docs/03-recetas-de-codigo.md) — Recetas listas para copiar y pegar (búsquedas, eliminaciones, recorridos).
- 📄 [docs/07-diagramas-mermaid.md](docs/07-diagramas-mermaid.md) — Plantillas para generar diagramas Mermaid del árbol para el informe.
- 📄 [docs/08-errores-y-trampas.md](docs/08-errores-y-trampas.md) — Errores típicos y trampas comunes en la prueba práctica.
- 📄 [docs/09-ejemplos-verificados.md](docs/09-ejemplos-verificados.md) — Ejemplos completos verificados de extremo a extremo.

### 🎯 Fase 3: Entrega y Sustentación
- 📄 [docs/05-plantilla-informe.md](docs/05-plantilla-informe.md) — Plantilla preformateada para el informe final de entrega.
- 📄 [docs/06-git-y-commits.md](docs/06-git-y-commits.md) — Guía de commits pequeños, mensajes claros y buenas prácticas en Git.
- 📄 [docs/10-sustentacion.md](docs/10-sustentacion.md) — Banco de preguntas modelo y respuestas para la defensa oral.

---

## ⚡ Compilar y ejecutar

Accede a la carpeta del proyecto en el que vayas a trabajar ([`plantilla-base/`](plantilla-base/) o [`ejemplo-sistema-academico/`](ejemplo-sistema-academico/)).

### En Windows (PowerShell o CMD):
```cmd
cd plantilla-base
compilar.bat
probar.bat
```

### En Linux / macOS:
```bash
cd plantilla-base
sh compilar.sh
sh probar.sh
```

> 💡 **Tip para Windows**: Si en la consola los acentos o caracteres especiales aparecen distorsionados, ejecuta previamente `chcp 65001`. En VS Code, abre la carpeta `src` del proyecto deseado como proyecto Java y ejecuta `app.Main`.

---

## 🗺️ Mapa rápido: "Me piden X ➔ Uso Y"

| Requerimiento del enunciado | Estructura / Método | Ubicación recomendada |
|---|---|---|
| **Árbol binario manual** (hijo izq/der específico) | `ArbolBinario<T>` con `crearRaiz`, `agregarIzquierdo`, `agregarDerecho` | [`plantilla-base/src/negocio/ArbolBinario.java`](plantilla-base/src/negocio/ArbolBinario.java) |
| **Árbol de búsqueda por clave única** (código, ID, etc.) | `ArbolBinarioBusqueda<K, T>` con extractor de clave | [`plantilla-base/src/negocio/ArbolBinarioBusqueda.java`](plantilla-base/src/negocio/ArbolBinarioBusqueda.java) |
| **Jerarquía n-aria** (organigramas, categorías, carpetas) | `ArbolGeneral<T>` | [`plantilla-base/src/negocio/ArbolGeneral.java`](plantilla-base/src/negocio/ArbolGeneral.java) |
| **Validar qué hijo puede colgar de qué padre** | Constructor con validador `new ArbolGeneral<>((padre, hijo) -> ...)` | [`ejemplo-sistema-academico/src/negocio/GestorOrganizacion.java`](ejemplo-sistema-academico/src/negocio/GestorOrganizacion.java) |
| **CRUD en ABB** (insertar sin duplicados, buscar, modificar, eliminar) | `insertar`, `buscar`, `modificar`, `eliminar` | [`plantilla-base/src/negocio/ArbolBinarioBusqueda.java`](plantilla-base/src/negocio/ArbolBinarioBusqueda.java) |
| **Recorridos estándar** (preorden, inorden, posorden, niveles) | `recorrer(TipoRecorrido.X)` | [`ArbolBinario`](plantilla-base/src/negocio/ArbolBinario.java) / [`ArbolGeneral`](plantilla-base/src/negocio/ArbolGeneral.java) |
| **Traza / prueba de escritorio de recorrido** | `trazaRecorrido(tipo)` | [`plantilla-base/src/negocio/ArbolBinario.java`](plantilla-base/src/negocio/ArbolBinario.java) |
| **Dibujar el árbol en consola para capturas** | `mostrarEstructura()`, `mostrarLateral()`, `mostrarJerarquia()` | Disponible en las tres clases de árbol |
| **Métricas** (altura, total nodos, total hojas, nivel, grado) | `altura`, `contarNodos`, `contarHojas`, `nivelDe`, `gradoDe` | Disponible en las tres clases de árbol |
| **Estadísticas de ABB** (mínimo, máximo, promedio, rango, k-ésimo) | `menor`, `mayor`, `promedio`, `rango`, `kEsimo`, `sucesorDe` | [`plantilla-base/src/negocio/ArbolBinarioBusqueda.java`](plantilla-base/src/negocio/ArbolBinarioBusqueda.java) |
| **Eliminar solo si es hoja** (regla estándar árbol general) | `eliminar(condicion)` | [`plantilla-base/src/negocio/ArbolGeneral.java`](plantilla-base/src/negocio/ArbolGeneral.java) |
| **Transformación a binario** (hijo izq, hermano der) | `aBinario()` o `Transformador.aBinario` | [`plantilla-base/src/negocio/Transformador.java`](plantilla-base/src/negocio/Transformador.java) |
| **Transformación inversa** (binario a general) | `Transformador.aGeneral` | [`plantilla-base/src/negocio/Transformador.java`](plantilla-base/src/negocio/Transformador.java) |
| **Filtrar o contar con condición personalizada** | `filtrar(x -> ...)`, `contarSi(x -> ...)` | [`ArbolBinario`](plantilla-base/src/negocio/ArbolBinario.java) / [`ArbolGeneral`](plantilla-base/src/negocio/ArbolGeneral.java) |
| **Árbol general donde cada hoja contiene un ABB propio** | Jerarquía general combinada con ABB | Ver implementación en [`ejemplo-sistema-academico/src/negocio/`](ejemplo-sistema-academico/src/negocio/) |
| **Menú interactivo de consola** | Menú con switch y lector de datos | [`plantilla-base/src/app/Main.java`](plantilla-base/src/app/Main.java) |
| **Preguntas teóricas de sustentación oral** | Respuestas modelo argumentadas | [`docs/10-sustentacion.md`](docs/10-sustentacion.md) |

---

## 🚀 Flujo recomendado durante la prueba

1. **Lectura y desglose**: Lee el enunciado y subraya entidades, claves únicas, jerarquías y operaciones requeridas ([docs/02-como-atacar-un-enunciado.md](docs/02-como-atacar-un-enunciado.md)).
2. **Preparar el proyecto**: Copia [`plantilla-base/`](plantilla-base/) a tu nuevo repositorio de examen.
3. **Commit inicial**: Sube la estructura limpia con README y `.gitignore` ([docs/06-git-y-commits.md](docs/06-git-y-commits.md)).
4. **Adaptar entidad**: Transforma `Elemento` en tu entidad (`Libro`, `Vehículo`, `Paciente`, etc.) con validaciones ([docs/04-adaptar-la-entidad.md](docs/04-adaptar-la-entidad.md)).
5. **Configurar el gestor**: Ajusta `GestorElementos` o `GestorJerarquia` con las reglas de negocio del caso.
6. **Datos de prueba**: En `DatosPrueba.java`, carga al menos 8-10 registros balanceados para probar casos normales y límites.
7. **Menú de usuario**: Adapta `Main.java` conectando cada opción del menú a los métodos del gestor.
8. **Pruebas y capturas**: Ejecuta las verificaciones automáticas, abre el menú y toma capturas para el informe ([docs/05-plantilla-informe.md](docs/05-plantilla-informe.md)).
9. **Commits constantes**: Realiza commits con mensajes descriptivos tras cada hito completado ([docs/06-git-y-commits.md](docs/06-git-y-commits.md)).

---

## ⚠️ Aviso y recomendaciones

Las plantillas están diseñadas para que comprendas en profundidad cada método algorítmico y puedas defender tu código en la sustentación oral. Antes de la prueba:
- Revisa los fundamentos teóricos en [docs/01-chuleta-teoria.md](docs/01-chuleta-teoria.md).
- Ejecuta el menú de ambos proyectos para familiarizarte con las salidas visuales (`mostrarEstructura()`, recorridos, etc.).
- Recuerda que todo código presentado debe ser comprensible y explicable por ti mismo.
