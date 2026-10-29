# 07. Diagramas en Mermaid

GitHub dibuja estos bloques directamente en el README o en cualquier `.md`. Copia, cambia los nombres y listo. Para el informe, toma una captura del diagrama ya dibujado, o pega el código en https://mermaid.live y exporta la imagen.

## Diagrama de clases del kit

```mermaid
classDiagram
    class Nodo~T~ {
        -T dato
        -Nodo~T~ izquierdo
        -Nodo~T~ derecho
        +esHoja() boolean
        +grado() int
    }
    class NodoGeneral~T~ {
        -T dato
        -List~NodoGeneral~T~~ hijos
        +agregarHijo(NodoGeneral~T~)
        +esHoja() boolean
        +grado() int
    }
    class ArbolBinario~T~ {
        #Nodo~T~ raiz
        +crearRaiz(T) Nodo~T~
        +agregarIzquierdo(Nodo~T~, T) Nodo~T~
        +agregarDerecho(Nodo~T~, T) Nodo~T~
        +recorrer(TipoRecorrido) List~T~
        +altura() int
        +contarNodos() int
        +contarHojas() int
    }
    class ArbolBinarioBusqueda~K, T~ {
        -Function~T, K~ extractorClave
        +insertar(T) boolean
        +buscar(K) T
        +modificar(K, Consumer~T~) boolean
        +eliminar(K) boolean
        +menor() T
        +mayor() T
    }
    class ArbolGeneral~T~ {
        -NodoGeneral~T~ raiz
        -BiPredicate~T, T~ reglaHijo
        +agregarHijo(Predicate~T~, T) boolean
        +eliminar(Predicate~T~) boolean
        +aBinario() ArbolBinario~T~
    }
    class Transformador {
        +aBinario(ArbolGeneral~T~) ArbolBinario~T~
        +aGeneral(ArbolBinario~T~) ArbolGeneral~T~
    }
    ArbolBinarioBusqueda --|> ArbolBinario
    ArbolBinario o-- Nodo
    ArbolGeneral o-- NodoGeneral
    Transformador ..> ArbolGeneral
    Transformador ..> ArbolBinario
```

## Diagrama de clases de un caso (capas)

```mermaid
classDiagram
    class Estudiante {
        -int matricula
        -String cedula
        -String nombres
        -double promedio
    }
    class UnidadAcademica {
        -String codigo
        -String nombre
        -TipoUnidad tipo
    }
    class GestorEstudiantes {
        +registrar(Estudiante) boolean
        +buscar(int) Estudiante
        +modificar(int, String, String, double) boolean
        +eliminar(int) boolean
    }
    class GestorSistemaAcademico {
        +agregarUnidad(String, UnidadAcademica) boolean
        +eliminarUnidad(String) boolean
        +estudiantesDe(String) GestorEstudiantes
    }
    class Main
    Main ..> GestorSistemaAcademico
    GestorSistemaAcademico ..> GestorEstudiantes
    GestorEstudiantes ..> Estudiante
    GestorSistemaAcademico ..> UnidadAcademica
```

## Diagrama de flujo: insertar en un ABB

```mermaid
flowchart TD
    A([Inicio: insertar dato]) --> B{¿La clave ya existe?}
    B -- Sí --> C([Devuelve false])
    B -- No --> D{¿El nodo actual es null?}
    D -- Sí --> E[Crear nodo nuevo como hoja]
    E --> F([Devuelve true])
    D -- No --> G{¿Clave menor que la del nodo?}
    G -- Sí --> H[Bajar al hijo izquierdo]
    G -- No --> I[Bajar al hijo derecho]
    H --> D
    I --> D
```

## Diagrama de flujo: eliminar en un ABB

```mermaid
flowchart TD
    A([Inicio: eliminar clave]) --> B{¿Existe la clave?}
    B -- No --> C([Devuelve false])
    B -- Sí --> D[Buscar el nodo]
    D --> E{¿Cuántos hijos tiene?}
    E -- Ninguno --> F[Devuelve null: el padre queda vacío]
    E -- Uno --> G[Devuelve ese hijo: ocupa el lugar del nodo]
    E -- Dos --> H[Buscar el sucesor inorden: menor del subárbol derecho]
    H --> I[Copiar el sucesor en el nodo]
    I --> J[Eliminar el sucesor del subárbol derecho]
    F --> K([Devuelve true])
    G --> K
    J --> K
```

## Diagrama de flujo: eliminar en el árbol general (regla A5)

```mermaid
flowchart TD
    A([Inicio: eliminar unidad]) --> B{¿Existe?}
    B -- No --> C([Devuelve false])
    B -- Sí --> D{¿Es la raíz?}
    D -- Sí --> C
    D -- No --> E{¿Tiene subunidades?}
    E -- Sí --> C
    E -- No --> F[Quitar la unidad de la lista de hijos del padre]
    F --> G([Devuelve true])
```

## Diagrama de secuencia: registrar un estudiante

```mermaid
sequenceDiagram
    actor Usuario
    participant Main
    participant Gestor as GestorEstudiantes
    participant ABB as ArbolBinarioBusqueda
    Usuario->>Main: Opción 1, datos del estudiante
    Main->>Main: Crear Estudiante (valida datos)
    Main->>Gestor: registrar(estudiante)
    Gestor->>ABB: insertar(estudiante)
    ABB->>ABB: existe(clave)
    alt clave repetida
        ABB-->>Gestor: false
        Gestor-->>Main: false
        Main-->>Usuario: Ya existe esa matrícula
    else clave nueva
        ABB->>ABB: insertar(raiz, estudiante)
        ABB-->>Gestor: true
        Gestor-->>Main: true
        Main-->>Usuario: Estudiante registrado
    end
```

## Diagrama de secuencia: transformar a binario

```mermaid
sequenceDiagram
    actor Usuario
    participant Main
    participant Gestor as GestorOrganizacion
    participant AG as ArbolGeneral
    participant T as Transformador
    Usuario->>Main: Opción 12
    Main->>Gestor: aBinario()
    Gestor->>AG: aBinario()
    AG->>T: aBinario(this)
    T->>T: primer hijo a la izquierda, hermano a la derecha
    T-->>AG: ArbolBinario
    AG-->>Gestor: ArbolBinario
    Gestor-->>Main: ArbolBinario
    Main-->>Usuario: Estructura del binario
```

## Dibujo de un árbol de ejemplo

```mermaid
graph TD
    A((40)) --> B((20))
    A --> C((60))
    B --> D((10))
    B --> E((30))
    C --> F[ ]
    C --> G((80))
    style F fill:none,stroke:none
```

## Diagrama de casos de uso (menú)

```mermaid
flowchart LR
    U([Usuario]) --- R[Registrar estudiante]
    U --- B[Buscar estudiante]
    U --- M[Modificar estudiante]
    U --- E[Eliminar estudiante]
    U --- RC[Ver recorridos]
    U --- ES[Ver estadísticas]
    U --- AU[Agregar unidad]
    U --- EU[Eliminar unidad]
    U --- TB[Transformar a binario]
```

Si la prueba exige casos de uso en UML, esta es la forma simple. Cambia los nombres por las operaciones del enunciado.
