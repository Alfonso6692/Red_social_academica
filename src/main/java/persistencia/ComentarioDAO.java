/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import modelo.Comentario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ComentarioDAO {
    
    public void guardar(Comentario comentario) throws SQLException {
        String sql = "INSERT INTO comentarios (id, contenido, fecha_creacion, id_publicacion, id_autor) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, comentario.getId());
            pstmt.setString(2, comentario.getTexto());
            pstmt.setTimestamp(3, Timestamp.valueOf(comentario.getFechaCreacion()));
            pstmt.setString(4, comentario.getIdPublicacion());
            pstmt.setString(5, comentario.getAutor().getId());
            
            pstmt.executeUpdate();
        }
    }
}