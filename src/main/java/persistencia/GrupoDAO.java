package persistencia;

import modelo.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoDAO {

    // Dentro de la clase GrupoDAO.java
    public void unirUsuarioAGrupo(String idGrupo, String idUsuario) throws SQLException {
        // La consulta SQL para insertar en la tabla de unión
        String sql = "INSERT INTO grupo_miembros (id_grupo, id_usuario) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idGrupo);
            pstmt.setString(2, idUsuario);

            pstmt.executeUpdate();
        }
    }

    public void guardar(Grupo grupo) throws SQLException {
        // Asegúrate de tener una tabla 'grupos' en tu BD
        String sql = "INSERT INTO grupos (id, nombre, descripcion, id_creador) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, grupo.getId());
            pstmt.setString(2, grupo.getNombre());
            pstmt.setString(3, grupo.getDescripcion());
            pstmt.setString(4, grupo.getCreador().getId()); // Guardamos el ID del creador

            pstmt.executeUpdate();
        }
    }

    // Dentro de la clase GrupoDAO.java
    public java.util.List<Grupo> obtenerTodos() throws SQLException {
        java.util.List<Grupo> grupos = new java.util.ArrayList<>();
        String sql = "SELECT * FROM grupos"; // Asume que tu tabla se llama 'grupos'

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // NOTA: Este constructor para Grupo es un ejemplo.
                // Necesitarás una forma de obtener el objeto Usuario del creador.
                // Por ahora, lo dejaremos simplificado.
                Grupo grupo = new Grupo();
                grupo.setId(rs.getString("id"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setDescripcion(rs.getString("descripcion"));
                grupos.add(grupo);
            }
        }
        return grupos;
    }

    // Dentro de la clase GrupoDAO.java
    public java.util.List<Grupo> obtenerGruposPorUsuario(String idUsuario) throws SQLException {
        java.util.List<Grupo> grupos = new java.util.ArrayList<>();
        // Esta consulta une las tablas para encontrar los grupos de un usuario
        String sql = "SELECT g.* FROM grupos g "
                + "JOIN grupo_miembros gm ON g.id = gm.id_grupo "
                + "WHERE gm.id_usuario = ?";

        try (Connection conn = ConexionBD.  getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Grupo grupo = new Grupo();
                    grupo.setId(rs.getString("id"));
                    grupo.setNombre(rs.getString("nombre"));
                    grupo.setDescripcion(rs.getString("descripcion"));
                    // Nota: El creador no se carga aquí para simplificar
                    grupos.add(grupo);
                }
            }
        }
        return grupos;
    }

}
