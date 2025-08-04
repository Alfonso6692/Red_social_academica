package servicios;

import modelo.Comentario;
import modelo.Publicacion;
import persistencia.ComentarioDAO;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Contiene la lógica de negocio para la gestión de comentarios.
 */
public class ServicioComentarios {
    
    private final ComentarioDAO comentarioDAO;

    public ServicioComentarios() {
        this.comentarioDAO = new ComentarioDAO();
    }

    /**
     * Procesa y guarda un nuevo comentario en la base de datos.
     * @param comentario El objeto Comentario a guardar (creado en la vista).
     * @param publicacion La publicación a la que pertenece el comentario.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void guardarComentario(Comentario comentario, Publicacion publicacion) throws SQLException {
        // Asigna un ID único universal al comentario antes de guardarlo.
        comentario.setId(UUID.randomUUID().toString());
        
        // Asigna el ID de la publicación para crear la relación en la base de datos.
        comentario.setIdPublicacion(publicacion.getId());
        
        // Llama al DAO para que realice la inserción en la base de datos.
        comentarioDAO.guardar(comentario);
    }
}