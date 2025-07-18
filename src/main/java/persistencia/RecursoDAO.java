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
     * @param recurso El objeto Recurso a guardar.
     * @throws SQLException Si ocurre un error.
     */
    public void guardar(Recurso recurso) throws SQLException {
        String sql = "INSERT INTO recursos (id, titulo, descripcion, nombre_archivo, tipo_archivo, fecha_publicacion, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, recurso.getId());
            pstmt.setString(2, recurso.getTitulo());
            pstmt.setString(3, recurso.getDescripcion());
            pstmt.setString(4, recurso.getNombreArchivo());
            pstmt.setString(5, recurso.getTipoArchivo());
            pstmt.setDate(6, Date.valueOf(recurso.getFechaPublicacion()));
            pstmt.setString(7, recurso.getUsuario().getId());
            
            pstmt.executeUpdate();
        }
    }

    /**
     * Obtiene todos los recursos de la base de datos.
     * @return Una lista de todos los recursos.
     * @throws SQLException Si ocurre un error.
     */
    // Dentro de la clase RecursoDAO.java

    public java.util.List<Recurso> obtenerTodos() throws java.sql.SQLException {
        java.util.List<Recurso> recursos = new java.util.ArrayList<>();
        String sql = "SELECT r.*, u.nombre, u.apellido FROM recursos r "
                + "JOIN usuarios u ON r.id_usuario = u.id "
                + "ORDER BY r.fecha_publicacion DESC";

        try (java.sql.Connection conn = ConexionBD.getConexion(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql); java.sql.ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Recurso recurso = new Recurso();
                recurso.setId(rs.getString("id"));
                recurso.setTitulo(rs.getString("titulo"));
                recurso.setTipoArchivo(rs.getString("tipo_archivo"));
                recurso.setFechaPublicacion(rs.getDate("fecha_publicacion").toLocalDate());

                Usuario uploader = new Usuario();
                uploader.setNombre(rs.getString("nombre"));
                uploader.setApellido(rs.getString("apellido"));
                recurso.setUsuario(uploader);

                recursos.add(recurso);
            }
        }
        return recursos;
    }

}
