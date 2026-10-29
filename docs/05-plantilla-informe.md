# 05. Plantilla de informe

Hay dos versiones. Elige según lo que pida el enunciado. Si no dice nada, usa la corta: es la que sigue el formato de deber.

## Versión corta (formato de deber)

Solo estas secciones: Actividades por desarrollar, Resultados obtenidos y Anexos. No lleva objetivos, modalidad, tiempo, equipos, habilidades blandas, recomendaciones ni referencias.

```
PORTADA
Universidad Técnica de Ambato
Facultad de Ingeniería en Sistemas, Electrónica e Industrial
Carrera de Software
Asignatura: Estructura de Datos
Tema: [nombre de la prueba o del caso]
Estudiante: [apellidos y nombres]
Nivel y paralelo: [nivel y paralelo]
Docente: [nombre del docente]
Fecha: [fecha]

1. ACTIVIDADES POR DESARROLLAR
1.1 Análisis del problema
    [2 a 3 párrafos: qué pide el enunciado, entidades, clave única, reglas]
1.2 Estructura elegida
    [árbol general, ABB o ambos, y por qué]
1.3 Diseño de clases
    [diagrama de clases + descripción breve de cada clase]
1.4 Implementación
    [por operación: qué hace, cómo se resuelve, complejidad]
1.5 Casos límite considerados
    [tabla]

2. RESULTADOS OBTENIDOS
    [capturas de cada operación y de los casos límite, con una frase de explicación]

3. ANEXOS
    Enlace del repositorio: https://github.com/[usuario]/[repositorio]
    [capturas de los commits]
```

## Versión completa (guía práctica)

```
PORTADA
[igual que la versión corta]

1. TEMA
2. OBJETIVO GENERAL
3. OBJETIVOS ESPECÍFICOS
4. ACTIVIDADES POR DESARROLLAR
   4.1 Análisis
   4.2 Diseño
   4.3 Implementación
   4.4 Pruebas
5. RESULTADOS OBTENIDOS
6. CONCLUSIONES
7. RECOMENDACIONES
8. BIBLIOGRAFÍA
9. ANEXOS
```

## Tabla de casos límite (copiar y completar)

| Operación | Caso | Resultado esperado | Resultado obtenido | Captura |
|---|---|---|---|---|
| Insertar | clave repetida | `false`, árbol intacto | | |
| Buscar | clave inexistente | `null`, mensaje "no existe" | | |
| Modificar | datos inválidos | error y datos sin cambios | | |
| Modificar | cambio de la clave | se hace con eliminar e insertar | | |
| Eliminar | hoja | el padre queda con ese lado `null` | | |
| Eliminar | un hijo | el hijo ocupa su lugar | | |
| Eliminar | dos hijos | lo reemplaza el sucesor inorden | | |
| Eliminar | clave inexistente | `false`, árbol intacto | | |
| Estadísticas | árbol vacío | altura -1, conteo 0, promedio 0.0 | | |
| Jerarquía | eliminar con subunidades | `false` (regla A5) | | |
| Jerarquía | eliminar la raíz | `false` | | |
| Jerarquía | hijo de un tipo inválido | rechazado | | |

## Tabla de complejidad (copiar)

| Operación | Árbol general | ABB balanceado | ABB en cadena |
|---|---|---|---|
| Buscar | O(n) | O(log n) | O(n) |
| Insertar | O(n) | O(log n) | O(n) |
| Recorrer | O(n) | O(n) | O(n) |
| Modificar | O(n) | O(log n) | O(n) |
| Eliminar | O(n) | O(log n) | O(n) |

## Descripción de cada operación (plantilla de una línea)

> **Operación**: qué recibe, qué devuelve, qué valida, y su complejidad. Ejemplo: **eliminar(matrícula)** recibe la matrícula, devuelve `true` si existía y la eliminó, `false` si no existe; resuelve los tres casos (hoja, un hijo, dos hijos con sucesor inorden); O(h).

## Consejos para las capturas

- Usa una ventana de consola con fuente grande y sin nada más.
- Muestra primero la acción (el menú con la opción elegida) y después el resultado.
- Para recorridos y estructura, usa `mostrarEstructura()` y la traza de `trazaRecorrido`.
- Una captura por operación y una por cada caso límite importante.
- Si el informe es en Word, pega las capturas con un pie de figura numerado.
