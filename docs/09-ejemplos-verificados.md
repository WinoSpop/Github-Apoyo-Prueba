# 09. Ejemplos verificados

Salidas reales del kit. Úsalos para comprobar tus trazas y como ejemplos en el informe. Cada resultado se obtuvo ejecutando el código de este repositorio.

## ABB del reto del Grupo 2: 45, 25, 70, 10, 30, 60, 90, 27, 65

```
45
|-- I: 25
|   |-- I: 10
|   \-- D: 30
|       \-- I: 27
\-- D: 70
    |-- I: 60
    |   \-- D: 65
    \-- D: 90
```

| Recorrido | Resultado |
|---|---|
| Preorden | 45 25 10 30 27 70 60 65 90 |
| Inorden | 10 25 27 30 45 60 65 70 90 |
| Posorden | 10 27 30 25 65 60 90 70 45 |
| Por niveles | 45 25 70 10 30 60 90 27 65 |

Altura 3, 9 nodos, 4 hojas.

## ABB de las estaciones: 38, 18, 55, 8, 28, 48, 75, 23, 33, 68

| Recorrido | Resultado |
|---|---|
| Preorden | 38 18 8 28 23 33 55 48 75 68 |
| Inorden | 8 18 23 28 33 38 48 55 68 75 |
| Posorden | 8 23 33 28 18 48 68 75 55 38 |
| Por niveles | 38 18 55 8 28 48 75 23 33 68 |

Altura 3, 10 nodos, 5 hojas.

## ABB del sistema académico: 50, 30, 70, 20, 40, 60, 80, 35, 65

```
50
|-- I: 30
|   |-- I: 20
|   \-- D: 40
|       \-- I: 35
\-- D: 70
    |-- I: 60
    |   \-- D: 65
    \-- D: 80
```

| Recorrido | Resultado |
|---|---|
| Preorden | 50 30 20 40 35 70 60 65 80 |
| Inorden | 20 30 35 40 50 60 65 70 80 |
| Posorden | 20 35 40 30 65 60 80 70 50 |
| Por niveles | 50 30 70 20 40 60 80 35 65 |

Altura 3, 9 nodos, 4 hojas (20, 35, 65, 80). Para buscar el 35 se hacen 4 comparaciones (altura + 1).

## Eliminar la raíz con dos hijos

ABB: 55, 30, 80, 20, 40, 70, 90, 45.

Antes de eliminar el 55:

```
55
|-- I: 30
|   |-- I: 20
|   \-- D: 40
|       \-- D: 45
\-- D: 80
    |-- I: 70
    \-- D: 90
```

El sucesor inorden del 55 es el menor de su subárbol derecho: 70. Se copia el 70 en la raíz y se elimina el 70 original (era hoja).

Después:

```
70
|-- I: 30
|   |-- I: 20
|   \-- D: 40
|       \-- D: 45
\-- D: 80
    \-- D: 90
```

Inorden: 20 30 40 45 70 80 90. El 80 se queda solo con el 90 a la derecha.

## ABB en cadena: 10, 20, 30, 40, 50

```
10
\-- D: 20
    \-- D: 30
        \-- D: 40
            \-- D: 50
```

Altura 4 (nodos menos uno). Buscar el 50 cuesta 5 comparaciones: O(n).

## Transformación 1: A(B(E,F), C, D(G)) a binario

General:

```
A
  B
    E
    F
  C
  D
    G
```

Binario:

```
A
\-- I: B
    |-- I: E
    |   \-- D: F
    \-- D: C
        \-- D: D
            \-- I: G
```

| Nodo | Izquierdo (primer hijo) | Derecho (siguiente hermano) |
|---|---|---|
| A | B | null |
| B | E | C |
| C | null | D |
| D | G | null |
| E | null | F |
| F | null | null |
| G | null | null |

## Transformación 2: R(A(D,E,F), B, C(G(H))) a binario

| Nodo | Izquierdo | Derecho |
|---|---|---|
| R | A | null |
| A | D | B |
| B | null | C |
| C | G | null |
| D | null | E |
| E | null | F |
| F | null | null |
| G | H | null |
| H | null | null |

Son 9 nodos, igual que en el general. Si un nodo cuenta con un solo hijo, va a la izquierda (G bajo C, H bajo G).

## Lectura inversa: de binario a general

Binario con R.izq = M, M.izq = P, M.der = N, N.der = Q:

```
R
\-- I: M
    |-- I: P
    \-- D: N
        \-- D: Q
```

General: los hijos de R son M, N y Q (la cadena que sale del primer hijo hacia la derecha) y P es hijo de M. El grado de R es 3 y el de M es 1.

## Jerarquía del sistema académico (datos de prueba del kit)

```
UTA - Universidad Técnica de Ambato (UNIVERSIDAD)
  FISEI - Ingeniería en Sistemas (FACULTAD)
    SW - Software (CARRERA)
      SW-N3 - Tercer nivel (NIVEL)
        SW-N3-A - Paralelo A (PARALELO)
        SW-N3-B - Paralelo B (PARALELO)
      SW-N4 - Cuarto nivel (NIVEL)
    TI - Tecnologías de la Información (CARRERA)
  FCA - Ciencias Administrativas (FACULTAD)
  FCHE - Ciencias Humanas y de la Educación (FACULTAD)
```

| Consulta | Resultado |
|---|---|
| Unidades | 10 |
| Altura | 4 |
| Hojas | 6 (SW-N3-A, SW-N3-B, SW-N4, TI, FCA, FCHE) |
| Grado de UTA | 3 |
| Nivel de SW-N3-B | 4 |
| Grado de SW-N3-B | 0 |
| Nivel y grado de un código que no existe | -1 |
| Eliminar FISEI (tiene carreras) | `false` |
| Eliminar UTA (raíz) | `false` |
| Eliminar FCHE (hoja) | `true` |

## Casos base

| Llamada | Resultado |
|---|---|
| `contar(null)` | 0 |
| `contarHojas(null)` | 0 |
| `altura(null)` | -1 |
| `altura(hoja)` | 0 |
| `promedio` de un árbol vacío | 0.0 |
| `eliminar` de una clave inexistente | `false`, árbol intacto |
