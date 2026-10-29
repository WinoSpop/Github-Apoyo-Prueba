# Plantilla base (neutral)

Proyecto Java 21 listo para compilar. Las estructuras de árbol son genéricas y la entidad de ejemplo (`Elemento`: código, nombre, valor) es deliberadamente neutra. Se copia esta carpeta y se adapta al dominio del ejercicio.

## Qué trae

| Capa | Archivo | Para qué sirve |
|---|---|---|
| modelo | `Nodo`, `NodoGeneral` | Nodos genéricos. No se tocan. |
| modelo | `Elemento` | Entidad de ejemplo. Se reemplaza por la del ejercicio. |
| negocio | `ArbolBinario<T>` | Recorridos, altura, hojas, nodos, promedio, contar con condición. |
| negocio | `ArbolBinarioBusqueda<K,T>` | Insertar, buscar, eliminar (3 casos), modificar, menor, mayor, rango, k-ésimo. |
| negocio | `ArbolGeneral<T>` | Hijos ilimitados, eliminar solo hojas, nivel, grado, camino, hermanos, a binario. |
| negocio | `Transformador` | General a binario y de vuelta (primer hijo a la izquierda, hermano a la derecha). |
| negocio | `GestorElementos` | Reglas de negocio sobre el ABB. Se adapta. |
| negocio | `GestorJerarquia` | Reglas de negocio sobre el árbol general. Se adapta. |
| negocio | `DatosPrueba` | Datos de arranque. Se reemplaza. |
| app | `Main` | Menú de consola. Se adapta. |
| pruebas | `PruebasBase` | Verificaciones sin librerías. |
| estructuras | `Cola`, `Pila` | Propias, por si el enunciado prohíbe `java.util`. |

## Cómo empezar un ejercicio nuevo

1. Copiar `plantilla-base/` a la carpeta del repositorio.
2. Renombrar `Elemento` por la entidad del enunciado y cambiar sus atributos. El atributo que sirve de clave no tiene setter.
3. En `GestorElementos`, cambiar el tipo de la clave y la referencia `Elemento::getCodigo`. Si la clave es numérica, usar `Integer` y quitar `normalizar`.
4. En `GestorJerarquia`, cambiar `porCodigo` por la condición que identifica un nodo. Si el enunciado impone una regla padre e hijo, usar `new ArbolGeneral<>((padre, hijo) -> ...)`.
5. Agregar los métodos que pida el enunciado en el gestor, usando los del árbol (`contarSi`, `promedio`, `rango`, `camino`).
6. Editar `Main`: menú y lectura de datos. La lógica no va en el `Main`.
7. Reemplazar `DatosPrueba` con datos del enunciado y ajustar `PruebasBase`.
8. Ejecutar `probar` y luego `compilar`.

## Reglas de diseño

- `null` o `false` cuando algo no se encuentra. Excepción solo para dato inválido o estado inválido.
- Validar con un objeto temporal antes de modificar el original.
- La clave nunca se modifica. Para cambiarla se elimina y se inserta de nuevo.
- Árbol general: eliminar solo hojas. Con hijos o raíz devuelve `false`.

## Ejecutar

Windows: `compilar.bat` y `probar.bat`. Linux o macOS: `sh compilar.sh` y `sh probar.sh`.

Si la consola muestra `?` en lugar de tildes: `chcp 65001` en Windows, o agregar `-Dstdout.encoding=UTF-8` a `java`.

## Ver también

- `../docs/` guía de uso, plantillas de adaptación, errores comunes y fórmulas.
- `../ejemplo-sistema-academico/` el mismo código aplicado a un dominio completo (estudiantes y unidades académicas).
