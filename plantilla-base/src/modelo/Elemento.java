package modelo;

public class Elemento {

    private final String codigo;
    private String nombre;
    private double valor;

    public Elemento(String codigo, String nombre, double valor) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede estar vacío");
        }
        this.codigo = codigo.trim().toUpperCase();
        setNombre(nombre);
        setValor(valor);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %.2f", codigo, nombre, valor);
    }
}
