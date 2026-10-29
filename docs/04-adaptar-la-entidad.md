# 04. Adaptar el kit a otra entidad en 10 minutos

Ejemplo: el enunciado habla de un **catálogo de libros** con clave única `isbn` y una jerarquía de categorías (Biblioteca, Área, Materia). El proceso es el mismo para cualquier caso.

## Paso 1: crea la entidad en `modelo`

Parte de `plantilla-base/src/modelo/Elemento.java` (clave de texto). Para ver una clave numérica o más reglas, mira `ejemplo-sistema-academico/src/modelo/Estudiante.java` y `Producto.java`. Copia, renombra y ajusta los campos. La clave va como `final`.

```java
package modelo;

public class Libro {

    private final String isbn;
    private String titulo;
    private String autor;
    private int ejemplares;

    public Libro(String isbn, String titulo, String autor, int ejemplares) {
        if (isbn == null || !isbn.matches("\\d{10}")) {
            throw new IllegalArgumentException("El ISBN debe tener 10 dígitos");
        }
        this.isbn = isbn;
        setTitulo(titulo);
        setAutor(autor);
        setEjemplares(ejemplares);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        this.titulo = titulo.trim();
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        this.autor = autor.trim();
    }

    public int getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(int ejemplares) {
        if (ejemplares < 0) {
            throw new IllegalArgumentException("Los ejemplares no pueden ser negativos");
        }
        this.ejemplares = ejemplares;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %d ejemplares", isbn, titulo, autor, ejemplares);
    }
}
```

Cada regla del enunciado ("no negativo", "10 dígitos", "no vacío") se vuelve una validación en el constructor o en un setter.

## Paso 2: crea el gestor en `negocio`

Parte de `plantilla-base/src/negocio/GestorElementos.java` (o de `ejemplo-sistema-academico/src/negocio/GestorEstudiantes.java` si necesitas ver más operaciones). Cambia el tipo de la clave, la entidad y las operaciones que pida el enunciado.

```java
package negocio;

import enums.TipoRecorrido;
import java.util.List;
import modelo.Libro;

public class GestorCatalogo {

    private final ArbolBinarioBusqueda<String, Libro> arbol =
            new ArbolBinarioBusqueda<>(Libro::getIsbn);

    public boolean registrar(Libro libro) {
        return arbol.insertar(libro);
    }

    public Libro buscar(String isbn) {
        return arbol.buscar(isbn);
    }

    public boolean prestar(String isbn) {
        Libro libro = arbol.buscar(isbn);
        if (libro == null || libro.getEjemplares() == 0) {
            return false;
        }
        libro.setEjemplares(libro.getEjemplares() - 1);
        return true;
    }

    public boolean eliminar(String isbn) {
        return arbol.eliminar(isbn);
    }

    public List<Libro> listar(TipoRecorrido tipo) {
        return arbol.recorrer(tipo);
    }

    public int totalEjemplares() {
        return (int) arbol.sumar(Libro::getEjemplares);
    }
}
```

## Paso 3: la jerarquía, si el enunciado la pide

Parte de `GestorOrganizacion.java`. Cambia `UnidadAcademica` por tu entidad de jerarquía y ajusta la regla de qué puede colgar de qué. Si cada hoja guarda su propio ABB, usa como modelo `NodoAcademico` y `GestorSistemaAcademico`.

## Paso 4: datos de prueba

En `DatosPrueba.java` agrega un método `cargarCatalogo(GestorCatalogo gestor)` con al menos 8 o 10 registros. Elige claves en un orden que no cree una cadena, para que el árbol tenga forma, y agrega algunos casos para el informe: un libro sin ejemplares, uno con clave muy baja y otro con clave muy alta.

## Paso 5: menú

Copia `app/Main.java`, cambia los gestores y las opciones del menú, y reutiliza `leerEntero`, `leerTexto` y `leerDecimal`. El `Main` solo lee y muestra: nunca contiene reglas de negocio.

## Paso 6: pruebas

Copia `plantilla-base/src/pruebas/PruebasBase.java` como plantilla (el ejemplo grande está en `ejemplo-sistema-academico/src/pruebas/PruebasKit.java`) y escribe una verificación por cada regla del enunciado. Al terminar, ejecuta las pruebas, el menú y toma capturas.

## Lista de control del paso a paso

- [ ] La clave es `final` y única.
- [ ] Todas las reglas del enunciado tienen una validación.
- [ ] El gestor devuelve `boolean` o `null` para "no encontrado" y no imprime.
- [ ] `Main` solo lee y muestra.
- [ ] Los datos de prueba cubren casos normales y casos límite.
- [ ] Borraste de tu repo lo que no uses (por ejemplo `Elemento` y `GestorJerarquia` si el enunciado no pide un árbol general).
