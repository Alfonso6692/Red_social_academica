package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

}
