package negocio;

import enums.TipoUnidad;
import modelo.Estudiante;
import modelo.UnidadAcademica;

public final class DatosPrueba {

    private DatosPrueba() {
    }

    public static void cargarEstudiantes(GestorEstudiantes gestor) {
        int[] matriculas = {50, 30, 70, 20, 40, 60, 80, 35, 65};
        double[] promedios = {8.5, 9.0, 7.5, 8.0, 9.5, 6.5, 7.0, 8.8, 9.2};
        for (int i = 0; i < matriculas.length; i++) {
            gestor.registrar(new Estudiante(
                    matriculas[i],
                    String.format("18000000%02d", matriculas[i]),
                    "Estudiante " + matriculas[i],
                    promedios[i]));
        }
    }

    public static void cargarSistema(GestorSistemaAcademico sistema) {
        sistema.registrarRaiz(new UnidadAcademica("UTA", "Universidad Técnica de Ambato", TipoUnidad.UNIVERSIDAD));
        sistema.agregarUnidad("UTA", new UnidadAcademica("FISEI", "Ingeniería en Sistemas", TipoUnidad.FACULTAD));
        sistema.agregarUnidad("FISEI", new UnidadAcademica("SW", "Software", TipoUnidad.CARRERA));
        sistema.agregarUnidad("SW", new UnidadAcademica("SW-N3", "Tercer nivel", TipoUnidad.NIVEL));
        sistema.agregarUnidad("SW-N3", new UnidadAcademica("SW-N3-A", "Paralelo A", TipoUnidad.PARALELO));
        sistema.agregarUnidad("SW-N3", new UnidadAcademica("SW-N3-B", "Paralelo B", TipoUnidad.PARALELO));
        cargarEstudiantes(sistema.estudiantesDe("SW-N3-B"));
        sistema.estudiantesDe("SW-N3-A").registrar(new Estudiante(100, "1800000100", "Ana Torres", 9.1));
        sistema.estudiantesDe("SW-N3-A").registrar(new Estudiante(90, "1800000090", "Luis Mora", 7.9));
    }

    public static void cargarOrganizacion(GestorOrganizacion gestor) {
        gestor.registrarRaiz(new UnidadAcademica("UTA", "Universidad Técnica de Ambato", TipoUnidad.UNIVERSIDAD));
        gestor.agregar("UTA", new UnidadAcademica("FISEI", "Ingeniería en Sistemas", TipoUnidad.FACULTAD));
        gestor.agregar("UTA", new UnidadAcademica("FCA", "Ciencias Administrativas", TipoUnidad.FACULTAD));
        gestor.agregar("UTA", new UnidadAcademica("FCHE", "Ciencias Humanas y de la Educación", TipoUnidad.FACULTAD));
        gestor.agregar("FISEI", new UnidadAcademica("SW", "Software", TipoUnidad.CARRERA));
        gestor.agregar("FISEI", new UnidadAcademica("TI", "Tecnologías de la Información", TipoUnidad.CARRERA));
        gestor.agregar("SW", new UnidadAcademica("SW-N3", "Tercer nivel", TipoUnidad.NIVEL));
        gestor.agregar("SW", new UnidadAcademica("SW-N4", "Cuarto nivel", TipoUnidad.NIVEL));
        gestor.agregar("SW-N3", new UnidadAcademica("SW-N3-A", "Paralelo A", TipoUnidad.PARALELO));
        gestor.agregar("SW-N3", new UnidadAcademica("SW-N3-B", "Paralelo B", TipoUnidad.PARALELO));
    }
}
