/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Recurso;
import modelo.Usuario;

/**
 *
 * @author ASUS-VASQUEZ
 */
public class RecursoDAO {

    /**
     * Guarda un nuevo recurso en la base de datos.
     *
     * @param recurso El objeto Recurso a guardar.
     * @throws SQLException Si ocurre un error.
     */
    public void guardar(Recurso recurso) throws SQLException {
        String sql = "INSERT INTO recursos (id, titulo, descripcion, nombre_archivo, tipo_archivo, fecha_publicacion,es_privado, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, recurso.getId());
            pstmt.setString(2, recurso.getTitulo());
            pstmt.setString(3, recurso.getDescripcion());
            pstmt.setString(4, recurso.getNombreArchivo());
            pstmt.setString(5, recurso.getTipoArchivo());
            pstmt.setDate(6, Date.valueOf(recurso.getFechaPublicacion()));
            pstmt.setBoolean(7, recurso.isEsPrivado());
            pstmt.setString(8, recurso.getUsuario().getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Obtiene una lista de recursos visibles para un usuario específico.
     * Incluye todos los recursos públicos y los recursos privados del propio
     * usuario.
     *
     * @param idUsuario El ID del usuario que está viendo los recursos.
     * @return Una lista de objetos Recurso.
     * @throws java.sql.SQLException Si hay un error de base de datos.
     */
    public java.util.List<modelo.Recurso> obtenerRecursosPublicos() throws java.sql.SQLException {
        java.util.List<modelo.Recurso> recursos = new java.util.ArrayList<>();
        String sql = "SELECT r.*, u.id as id_uploader, u.nombre, u.apellido FROM recursos r "
                + "JOIN usuarios u ON r.id_usuario = u.id "
                + "WHERE r.es_privado = FALSE "
                + "ORDER BY r.fecha_publicacion DESC";

        try (java.sql.Connection conn = ConexionBD.getConexion(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql); java.sql.ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Asumiendo que tienes un método 'mapearRecurso' o llenas el objeto aquí
                modelo.Recurso recurso = new modelo.Recurso();
                recurso.setId(rs.getString("id"));
                recurso.setTitulo(rs.getString("titulo"));
                recurso.setTipoArchivo(rs.getString("tipo_archivo"));
                recurso.setEsPrivado(rs.getBoolean("es_privado"));
                recurso.setFechaPublicacion(rs.getDate("fecha_publicacion").toLocalDate());

                modelo.Usuario uploader = new modelo.Usuario();
                uploader.setId(rs.getString("id_uploader"));
                uploader.setNombre(rs.getString("nombre"));
                uploader.setApellido(rs.getString("apellido"));
                recurso.setUsuario(uploader);

                recursos.add(recurso);
            }
        }
        return recursos;
    }

    // Dentro de la clase RecursoDAO.java
    /**
     * Obtiene una lista de recursos privados de un usuario específico.
     *
     * @param idUsuario El ID del usuario.
     * @return Una lista de objetos Recurso.
     * @throws java.sql.SQLException
     */
    public java.util.List<modelo.Recurso> obtenerRecursosPrivadosDe(String idUsuario) throws java.sql.SQLException {
        java.util.List<modelo.Recurso> recursos = new java.util.ArrayList<>();
        String sql = "SELECT r.*, u.id as id_uploader, u.nombre, u.apellido FROM recursos r "
                + "JOIN usuarios u ON r.id_usuario = u.id "
                + "WHERE r.id_usuario = ? AND r.es_privado = TRUE "
                + "ORDER BY r.fecha_publicacion DESC";

        try (java.sql.Connection conn = ConexionBD.getConexion(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idUsuario);

            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    modelo.Recurso recurso = new modelo.Recurso();
                    recurso.setId(rs.getString("id"));
                    recurso.setTitulo(rs.getString("titulo"));
                    recurso.setTipoArchivo(rs.getString("tipo_archivo"));
                    recurso.setEsPrivado(rs.getBoolean("es_privado"));

                    // --- AÑADE ESTA LÍNEA QUE FALTA ---
                    recurso.setFechaPublicacion(rs.getDate("fecha_publicacion").toLocalDate());

                    modelo.Usuario uploader = new modelo.Usuario();
                    uploader.setId(rs.getString("id_uploader"));
                    uploader.setNombre(rs.getString("nombre"));
                    uploader.setApellido(rs.getString("apellido"));
                    recurso.setUsuario(uploader);

                    recursos.add(recurso);
                }
            }
        }
        return recursos;
    }

    public void eliminar(String idRecurso) throws java.sql.SQLException {
        String sql = "DELETE FROM recursos WHERE id = ?";
        try (java.sql.Connection conn = ConexionBD.getConexion(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idRecurso);
            pstmt.executeUpdate();
        }
    }

    public void actualizarVisibilidad(String idRecurso, boolean esPrivado) throws java.sql.SQLException {
        String sql = "UPDATE recursos SET es_privado = ? WHERE id = ?";
        try (java.sql.Connection conn = ConexionBD.getConexion(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, esPrivado);
            pstmt.setString(2, idRecurso);

            pstmt.executeUpdate();
        }
    }

}
