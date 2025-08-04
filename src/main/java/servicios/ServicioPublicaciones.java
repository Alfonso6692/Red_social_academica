package servicios;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import modelo.Publicacion;
import modelo.Usuario;
import persistencia.PublicacionDAO;

/**
 * Contiene la lógica de negocio para la gestión de publicaciones.
 */
public class ServicioPublicaciones {

    private final PublicacionDAO publicacionDAO;

    public ServicioPublicaciones() {
        this.publicacionDAO = new PublicacionDAO();
    }

    /**
     * Obtiene todas las publicaciones de la base de datos.
     * @return Una lista de todas las publicaciones.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<Publicacion> obtenerTodas() throws SQLException {
        return publicacionDAO.obtenerTodasLasPublicaciones();
    }

    /**
     * Procesa la creación de una nueva publicación.
     * @param autor El usuario que crea la publicación.
     * @param contenido El texto de la publicación.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void crearPublicacion(Usuario autor, String contenido) throws SQLException {
        // Se crea el objeto Publicacion con sus datos
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setId(java.util.UUID.randomUUID().toString());
        nuevaPublicacion.setUsuario(autor);
        nuevaPublicacion.setContenido(contenido);
        nuevaPublicacion.setFechaPublicacion(LocalDateTime.now());
        
        // Se llama al DAO para guardarla en la base de datos
        publicacionDAO.guardar(nuevaPublicacion);
    }
    
        /**
     * NUEVO: Actualiza una publicación existente
     * @param publicacion La publicación a actualizar
     * @throws java.sql.SQLException Si ocurre un error en la base de datos
     */
    public void actualizarPublicacion(Publicacion publicacion) throws java.sql.SQLException {
        publicacionDAO.actualizar(publicacion);
    }
    
    /**
     * NUEVO: Elimina una publicación
     * @param publicacion La publicación a eliminar
     * @throws java.sql.SQLException Si ocurre un error en la base de datos
     */
   public void eliminarPublicacion(Publicacion publicacion) throws java.sql.SQLException {
    System.out.println("DEBUG: Servicio eliminando publicación ID: " + publicacion.getId());
    publicacionDAO.eliminar(publicacion.getId());
}
    
    
}