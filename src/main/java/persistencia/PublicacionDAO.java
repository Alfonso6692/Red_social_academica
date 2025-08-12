package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;

public class PublicacionDAO {

    /**
     * Obtiene la publicación más reciente de la base de datos. Es un método
     * simple para propósitos de demostración.
     *
     * @return Un objeto Publicacion con la última publicación.
     * @throws SQLException
     */
    public Publicacion obtenerPublicacionSimple() throws SQLException {
        Publicacion publicacion = null;
        // Esta consulta trae la publicación más reciente
        String sql = "SELECT * FROM publicaciones ORDER BY fecha_publicacion DESC LIMIT 1";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                publicacion = new Publicacion();

                publicacion.setId(rs.getString("id"));
                publicacion.setContenido(rs.getString("contenido"));
                publicacion.setFechaPublicacion(rs.getTimestamp("fecha_publicacion").toLocalDateTime());

                // Para obtener los datos del usuario, necesitamos buscarlo
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario autor = usuarioDAO.buscarPorId(rs.getString("id_usuario"));
                publicacion.setUsuario(autor);
            }
        }
        return publicacion;
    }

    // Dentro de la clase PublicacionDAO.java
    public java.util.List<modelo.Publicacion> obtenerTodasLasPublicaciones() throws java.sql.SQLException {
        java.util.List<modelo.Publicacion> publicaciones = new java.util.ArrayList<>();
        // Hacemos un JOIN con usuarios para obtener el nombre del autor
        String sql = "SELECT p.*, u.nombre, u.apellido FROM publicaciones p "
                + "JOIN usuarios u ON p.id_usuario = u.id "
                + "ORDER BY p.fecha_publicacion DESC"; // Las más nuevas primero

        try (java.sql.Connection conn = ConexionBD.getConexion(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql); java.sql.ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Publicacion pub = new Publicacion();
                pub.setId(rs.getString("id"));
                pub.setContenido(rs.getString("contenido"));
                pub.setFechaPublicacion(rs.getTimestamp("fecha_publicacion").toLocalDateTime());

                Usuario autor = new Usuario();
                autor.setId(rs.getString("id_usuario"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido(rs.getString("apellido"));
                pub.setUsuario(autor);

                publicaciones.add(pub);
            }
        }
        return publicaciones;
    }

    // Método para guardar una nueva publicación (lo necesitarás más adelante)
    public void guardar(Publicacion publicacion) throws SQLException {
        System.out.println("DEBUG: PublicacionDAO.guardar llamado");
        String sql = "INSERT INTO publicaciones (id, contenido, fecha_publicacion, id_usuario) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, publicacion.getId());
            pstmt.setString(2, publicacion.getContenido());
            pstmt.setTimestamp(3, Timestamp.valueOf(publicacion.getFechaPublicacion()));
            pstmt.setString(4, publicacion.getUsuario().getId());

            pstmt.executeUpdate();
        }
    }

    /**
     * Obtiene una publicación específica por su ID
     *
     * @param idPublicacion ID de la publicación a buscar
     * @return Objeto Publicacion o null si no se encuentra
     * @throws SQLException
     */
    public Publicacion obtenerPorId(String idPublicacion) throws SQLException {
        Publicacion publicacion = null;
        String sql = "SELECT p.*, u.nombre, u.apellido FROM publicaciones p "
                + "JOIN usuarios u ON p.id_usuario = u.id "
                + "WHERE p.id = ?";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idPublicacion);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                publicacion = new Publicacion();
                publicacion.setId(rs.getString("id"));
                publicacion.setContenido(rs.getString("contenido"));
                publicacion.setFechaPublicacion(rs.getTimestamp("fecha_publicacion").toLocalDateTime());

                // Crear objeto Usuario con los datos del JOIN
                Usuario autor = new Usuario();
                autor.setId(rs.getString("id_usuario"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido(rs.getString("apellido"));

                publicacion.setUsuario(autor);
            }
        }
        return publicacion;
    }

    /**
     * Actualiza una publicación existente en la base de datos
     *
     * @param publicacion La publicación con los datos actualizados
     * @throws SQLException Si ocurre un error en la base de datos
     */
    public void actualizar(Publicacion publicacion) throws SQLException {
        String sql = "UPDATE publicaciones SET contenido = ?, fecha_publicacion = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, publicacion.getContenido());
            pstmt.setTimestamp(2, Timestamp.valueOf(publicacion.getFechaPublicacion()));
            pstmt.setString(3, publicacion.getId());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo actualizar la publicación con ID: " + publicacion.getId());
            }
        }
    }

    /**
     * Elimina una publicación de la base de datos
     */
    public void eliminar(String idPublicacion) throws SQLException {
        System.out.println("DEBUG: Intentando eliminar publicación con ID: " + idPublicacion);

        // Primero eliminar comentarios relacionados
        String sqlComentarios = "DELETE FROM comentarios WHERE id_publicacion = ?";

        // Luego eliminar la publicación
        String sqlPublicacion = "DELETE FROM publicaciones WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion()) {
            // Iniciar transacción
            conn.setAutoCommit(false);

            try {
                // Eliminar comentarios primero
                try (PreparedStatement pstmtComentarios = conn.prepareStatement(sqlComentarios)) {
                    pstmtComentarios.setString(1, idPublicacion);
                    int comentariosEliminados = pstmtComentarios.executeUpdate();
                    System.out.println("DEBUG: Comentarios eliminados: " + comentariosEliminados);
                }

                // Eliminar publicación
                try (PreparedStatement pstmtPublicacion = conn.prepareStatement(sqlPublicacion)) {
                    pstmtPublicacion.setString(1, idPublicacion);
                    int filasAfectadas = pstmtPublicacion.executeUpdate();
                    System.out.println("DEBUG: Publicaciones eliminadas: " + filasAfectadas);

                    if (filasAfectadas == 0) {
                        throw new SQLException("No se encontró la publicación con ID: " + idPublicacion);
                    }
                }

                // Confirmar transacción
                conn.commit();
                System.out.println("DEBUG: Eliminación exitosa");

            } catch (SQLException e) {
                // Rollback en caso de error
                conn.rollback();
                System.err.println("DEBUG: Error en eliminación, rollback realizado");
                throw e;
            } finally {
                // Restaurar autocommit
                conn.setAutoCommit(true);
            }
        }
    }

    /**
     * Busca una publicación por su ID
     *
     * @param idPublicacion ID de la publicación
     * @return La publicación encontrada o null si no existe
     * @throws SQLException Si ocurre un error en la base de datos
     */
    public Publicacion buscarPorId(String idPublicacion) throws SQLException {
        String sql = "SELECT p.*, u.nombre, u.apellido FROM publicaciones p "
                + "JOIN usuarios u ON p.id_usuario = u.id "
                + "WHERE p.id = ?";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idPublicacion);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Publicacion publicacion = new Publicacion();
                publicacion.setId(rs.getString("id"));
                publicacion.setContenido(rs.getString("contenido"));
                publicacion.setFechaPublicacion(rs.getTimestamp("fecha_publicacion").toLocalDateTime());

                Usuario autor = new Usuario();
                autor.setId(rs.getString("id_usuario"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido(rs.getString("apellido"));

                publicacion.setUsuario(autor);

                return publicacion;
            }
        }
        return null;
    }
    
    public boolean agregarComentario(String textoComentario, String idPublicacion, String idUsuario) {
    // La consulta SQL que vas a ejecutar. Asegúrate que incluya la columna id.
    String sql = "INSERT INTO comentarios (id, contenido, fecha_creacion, id_publicacion, id_usuario) VALUES (?, ?, ?, ?, ?)";

    try {
        // Obten tu conexión a la BD
        Connection conn = ConexionBD.getConexion(); 
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // --- LÍNEA CLAVE: Generar un nuevo ID único para el comentario ---
        String nuevoIdComentario = UUID.randomUUID().toString();

        // Asignar los valores a la consulta
        pstmt.setString(1, nuevoIdComentario); // El ID que acabas de generar
        pstmt.setString(2, textoComentario);    // El texto del comentario ("Excelente")
        pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // La fecha y hora actual
        pstmt.setString(4, idPublicacion);  // El ID de la publicación a la que pertenece el comentario
        pstmt.setString(5, idUsuario);      // El ID del usuario que está comentando (ej. "ALFONSO VASQUEZ LUDEÑA")

        // Ejecutar la inserción
        int filasAfectadas = pstmt.executeUpdate();
        
        // Cerrar recursos
        pstmt.close();
        conn.close();
        
        return filasAfectadas > 0;

    } catch (SQLException e) {
        System.err.println("Error al agregar comentario: " + e.getMessage());
        // Aquí puedes mostrar el JOptionPane de error que ya tienes
        return false;
    }
}
    
    
    public void guardar(Comentario comentario) throws SQLException {
    System.out.println("DEBUG: Guardando comentario con ID: " + comentario.getId());
    
    String sql = "INSERT INTO comentarios (id, texto, fecha_comentario, id_publicacion, id_autor) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = ConexionBD.getConexion(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // Verificar que el ID no sea null
        if (comentario.getId() == null) {
            comentario.setId(java.util.UUID.randomUUID().toString());
            System.out.println("DEBUG: ID era null, generado nuevo: " + comentario.getId());
        }
        
        pstmt.setString(1, comentario.getId());
        pstmt.setString(2, comentario.getContenido());
        pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(comentario.getFechaComentario()));
        pstmt.setString(4, comentario.getIdPublicacion());
        pstmt.setString(5, comentario.getAutor().getId());
        
        System.out.println("DEBUG: Ejecutando INSERT con:");
        System.out.println("  ID: " + comentario.getId());
        System.out.println("  Texto: " + comentario.getContenido());
        System.out.println("  ID Publicación: " + comentario.getIdPublicacion());
        System.out.println("  ID Autor: " + comentario.getAutor().getId());
        
        int filasAfectadas = pstmt.executeUpdate();
        System.out.println("DEBUG: Comentario guardado, filas afectadas: " + filasAfectadas);
        
        if (filasAfectadas == 0) {
            throw new SQLException("No se pudo insertar el comentario");
        }
    }
}


}
