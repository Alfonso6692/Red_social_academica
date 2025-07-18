// Paquete actualizado para coincidir con tu proyecto
package persistencia;

// Import actualizado para que encuentre la clase Usuario en tu paquete modelo
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO para las operaciones CRUD de la entidad Usuario. Esta clase se encarga de
 * toda la comunicación con la base de datos para la tabla de usuarios.
 */
public class UsuarioDAO {

    /**
     * Guarda un nuevo usuario en la base de datos (Crear).
     *
     * @param idUsuarioActual
     * @return
     * @throws SQLException Si ocurre un error de base de datos.
     */
    // Dentro de la clase UsuarioDAO.java
// Dentro de la clase UsuarioDAO.java
    public java.util.List<Usuario> obtenerTodosExcepto(String idUsuarioActual) throws java.sql.SQLException {
        java.util.List<Usuario> usuarios = new java.util.ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE id != ?";

        try (java.sql.Connection conn = ConexionBD.getConexion(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idUsuarioActual);

            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getString("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setSegundo_apellido(rs.getString("segundo_apellido"));
                    usuario.setCarrera(rs.getString("carrera"));
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }

    public void guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id, nombre, apellido, segundo_apellido, correo, contrasena, carrera, ciclo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Asumiendo que tienes una clase ConexionBD en el mismo paquete 'persistencia'
        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getId());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
             pstmt.setString(4, usuario.getSegundo_apellido());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getContrasena());
            pstmt.setString(7, usuario.getCarrera());
            pstmt.setInt(8, usuario.getCiclo());
           

            pstmt.executeUpdate();
        }
    }

    /**
     * Busca un usuario por su correo electrónico (Leer).
     *
     * @param correo El correo del usuario a buscar.
     * @return Un objeto Usuario si se encuentra, de lo contrario null.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Usuario buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        Usuario usuario = null;

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getString("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setSegundo_apellido("segundo_apellido");
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setCarrera(rs.getString("carrera"));
                    usuario.setCiclo(rs.getInt("ciclo"));
                    
                }
            }
        }
        return usuario;
    }

    /**
     * Busca un usuario por su ID único (Leer).
     *
     * @param id El ID del usuario a buscar.
     * @return Un objeto Usuario si se encuentra, de lo contrario null.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Usuario buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        Usuario usuario = null;

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getString("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("correo"),
                            rs.getString("contrasena"),
                            rs.getString("carrera"),
                            rs.getInt("ciclo"),
                            rs.getString("segundo_apellido")
                    );
                }
            }
        }
        return usuario;
    }

    /**
     * Actualiza los datos de un usuario existente en la base de datos
     * (Actualizar).
     *
     * @param usuario El objeto Usuario con los datos actualizados.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, segundo_apellido = ?, carrera = ?, ciclo = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(4, usuario.getSegundo_apellido());
            pstmt.setString(5, usuario.getCarrera());
            pstmt.setInt(6, usuario.getCiclo());
            pstmt.setString(7, usuario.getId());
            

            pstmt.executeUpdate();
        }
    }

    /**
     * Elimina un usuario de la base de datos usando su ID (Eliminar).
     *
     * @param id El ID del usuario a eliminar.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void eliminar(String id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }
}
