package servicios;

import modelo.Recurso;
import modelo.Usuario;
import persistencia.RecursoDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contiene la lógica de negocio para la gestión de recursos educativos.
 */
public class ServicioRecursos {

    // Instancia del DAO para interactuar con la base de datos.
    private final RecursoDAO recursoDAO;

    public ServicioRecursos() {
        this.recursoDAO = new RecursoDAO();
    }

    /**
     * Procesa la subida de un nuevo recurso.
     *
     * @param titulo El título del recurso.
     * @param descripcion La descripción del recurso.
     * @param esPrivado
     * @param nombreArchivo El nombre del archivo guardado.
     * @param tipoArchivo La extensión del archivo (ej: "pdf", "docx").
     * @param usuario El usuario que sube el recurso.
     * @throws SQLException Si hay un error de base de datos.
     */
    public void subirRecurso(String titulo, String descripcion, String nombreArchivo, String tipoArchivo, Usuario usuario, boolean esPrivado) throws SQLException {
        String id = UUID.randomUUID().toString();

        Recurso nuevoRecurso = new Recurso();
        nuevoRecurso.setId(id);
        nuevoRecurso.setTitulo(titulo);
        nuevoRecurso.setDescripcion(descripcion);
        nuevoRecurso.setNombreArchivo(nombreArchivo);
        nuevoRecurso.setTipoArchivo(tipoArchivo);
        nuevoRecurso.setFechaPublicacion(LocalDate.now());
        nuevoRecurso.setUsuario(usuario);
        nuevoRecurso.setEsPrivado(esPrivado); // Asigna el valor de privacidad
        recursoDAO.guardar(nuevoRecurso);
    }

//    /**
//     * Obtiene todos los recursos de la base de datos.
//     *
//     * @param usuario
//     * @return Una lista de todos los recursos.
//     * @throws SQLException Si hay un error de base de datos.
//     */
//    public List<Recurso> obtenerRecursosVisibles(Usuario usuario) throws SQLException {
//        return recursoDAO.obtenerRecursosPublicos(usuario.getId());
//    }
    public void cambiarVisibilidad(String idRecurso, boolean esPrivado) throws java.sql.SQLException {
        recursoDAO.actualizarVisibilidad(idRecurso, esPrivado);
    }

    // Dentro de la clase ServicioRecursos.java
    public java.util.List<modelo.Recurso> obtenerRecursosPrivados(modelo.Usuario usuario) throws java.sql.SQLException {
        return recursoDAO.obtenerRecursosPrivadosDe(usuario.getId());
    }
// Dentro de la clase ServicioRecursos.java

    public java.util.List<modelo.Recurso> obtenerRecursosPublicos() throws java.sql.SQLException {
        return recursoDAO.obtenerRecursosPublicos();
    }

    public void eliminarRecurso(Recurso recurso) throws java.sql.SQLException, java.io.IOException {
        // 1. Eliminar el registro de la base de datos
        recursoDAO.eliminar(recurso.getId());

        // 2. Eliminar el archivo físico del disco
        Path rutaArchivo = Paths.get("recursos_compartidos/" + recurso.getNombreArchivo());
        Files.deleteIfExists(rutaArchivo);
    }

}
