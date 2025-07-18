package persistencia;

import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    /**
     * Guarda un nuevo usuario en la base de datos, incluyendo el segundo apellido.
     */
    public void guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id, nombre, apellido, segundo_apellido, correo, contrasena, carrera, ciclo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getId());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getsegundoApellido());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getContrasena());
            pstmt.setString(7, usuario.getCarrera());
            pstmt.setInt(8, usuario.getCiclo());
            
            pstmt.executeUpdate();
        }
    }

    /**
     * Busca un usuario por su correo, leyendo todos sus datos incluyendo el segundo apellido.
     */
    public Usuario buscarPorCorreo(String correo) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getString("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setsegundoApellido(rs.getString("segundo_apellido"));
                    
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
     * Actualiza los datos de un usuario existente en la base de datos.
     */
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, segundo_apellido = ?, carrera = ?, ciclo = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getsegundoApellido());
            pstmt.setString(4, usuario.getCarrera());
            pstmt.setInt(5, usuario.getCiclo());
            pstmt.setString(6, usuario.getId());
            
            pstmt.executeUpdate();
        }
    }
    
    /**
     * Obtiene todos los usuarios registrados excepto el usuario actual.
     */
    public List<Usuario> obtenerTodosExcepto(String idUsuarioActual) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE id != ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idUsuarioActual);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getString("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setsegundoApellido(rs.getString("segundo_apellido"));
                    usuario.setCarrera(rs.getString("carrera"));
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }
}