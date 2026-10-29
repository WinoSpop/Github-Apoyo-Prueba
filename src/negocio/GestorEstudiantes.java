package negocio;

import enums.TipoRecorrido;
import java.util.List;
import modelo.Estudiante;

public class GestorEstudiantes {

    private final ArbolBinarioBusqueda<Integer, Estudiante> arbol =
            new ArbolBinarioBusqueda<>(Estudiante::getMatricula);

    public boolean registrar(Estudiante estudiante) {
        return arbol.insertar(estudiante);
    }

    public Estudiante buscar(int matricula) {
        return arbol.buscar(matricula);
    }

    public boolean modificar(int matricula, String cedula, String nombres, double promedio) {
        if (!arbol.existe(matricula)) {
            return false;
        }
        new Estudiante(matricula, cedula, nombres, promedio);
        return arbol.modificar(matricula, e -> {
            e.setCedula(cedula);
            e.setNombres(nombres);
            e.setPromedio(promedio);
        });
    }

    public boolean eliminar(int matricula) {
        return arbol.eliminar(matricula);
    }

    public List<Estudiante> listar(TipoRecorrido tipo) {
        return arbol.recorrer(tipo);
    }

    public int total() {
        return arbol.contarNodos();
    }

    public int altura() {
        return arbol.altura();
    }

    public int hojas() {
        return arbol.contarHojas();
    }

    public Estudiante menor() {
        return arbol.menor();
    }

    public Estudiante mayor() {
        return arbol.mayor();
    }

    public double promedioGeneral() {
        return arbol.promedio(Estudiante::getPromedio);
    }

    public String estructura() {
        return arbol.mostrarEstructura(e -> String.valueOf(e.getMatricula()));
    }

    public String traza(TipoRecorrido tipo) {
        return arbol.trazaRecorrido(tipo, e -> String.valueOf(e.getMatricula()));
    }

    public ArbolBinarioBusqueda<Integer, Estudiante> getArbol() {
        return arbol;
    }
}
