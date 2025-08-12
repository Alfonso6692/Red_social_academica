/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import modelo.Comentario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
//import java.util.UUID;
//import java.time.LocalDateTime;

public class ComentarioDAO {
    
    public void guardar(Comentario comentario) throws SQLException {
      System.out.println("DEBUG: === GUARDANDO COMENTARIO ===");
        
        // Verificaciones de seguridad
        if (comentario.getId() == null) {
            comentario.setId(java.util.UUID.randomUUID().toString());
            System.out.println("DEBUG: ID generado: " + comentario.getId());
        }
        
        if (comentario.getFechaCreacion() == null) {
            comentario.setFechaCreacion(java.time.LocalDateTime.now());
            System.out.println("DEBUG: Fecha generada: " + comentario.getFechaCreacion());
        }
        
        // ✅ SQL CORREGIDO: usar "contenido" en lugar de "texto"
        String sql = "INSERT INTO comentarios (id, contenido, fecha_creacion, id_publicacion, id_autor) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, comentario.getId());
            pstmt.setString(2, comentario.getContenido()); // Obtener el texto/contenido
            pstmt.setTimestamp(3, Timestamp.valueOf(comentario.getFechaCreacion()));
            pstmt.setString(4, comentario.getIdPublicacion());
            pstmt.setString(5, comentario.getAutor().getId());
            
            System.out.println("DEBUG: Ejecutando INSERT SQL:");
            System.out.println("  " + sql);
            System.out.println("DEBUG: Con valores:");
            System.out.println("  ID: " + comentario.getId());
            System.out.println("  Contenido: " + comentario.getTexto());
            System.out.println("  Fecha: " + comentario.getFechaCreacion());
            System.out.println("  ID Publicación: " + comentario.getIdPublicacion());
            System.out.println("  ID Autor: " + comentario.getAutor().getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("DEBUG: Filas afectadas: " + filasAfectadas);
            
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo insertar el comentario");
            }
            
            System.out.println("DEBUG: ✅ Comentario guardado exitosamente");
            
        } catch (SQLException e) {
            System.err.println("DEBUG: ❌ Error SQL: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    
    public List<Comentario> obtenerPorPublicacion(String publicacionId) throws SQLException {
        //List<Comentario> comentarios = new ArrayList<>();
        List<Comentario> comentarios = new ArrayList<>();
        // La consulta SQL debe coincidir con los nombres de columnas en tu BD
        String sql = "SELECT c.id, c.contenido, c.fecha_creacion, c.id_publicacion, c.id_autor, " +
                    "u.id as usuario_id, u.nombre as usuario_nombre, u.correo as usuario_correo " +
                    "FROM comentarios c " +
                    "JOIN usuarios u ON c.id_autor = u.id " +
                    "WHERE c.id_publicacion = ? " +
                    "ORDER BY c.fecha_creacion ASC";  // Orden ascendente para ver primero los más antiguos
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, publicacionId);
            
            System.out.println("DEBUG: Buscando comentarios para publicación ID: " + publicacionId);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Comentario comentario = new Comentario();
                comentario.setId(rs.getString("id"));
                comentario.setContenido(rs.getString("contenido"));
                comentario.setIdPublicacion(rs.getString("id_publicacion"));
                
                // Crear el usuario autor
                Usuario autor = new Usuario();
                autor.setId(rs.getString("usuario_id"));
                autor.setNombre(rs.getString("usuario_nombre"));
                autor.setCorreo(rs.getString("usuario_correo"));
                comentario.setAutor(autor);
                
                // Establecer fecha
                java.sql.Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                if (timestamp != null) {
                    comentario.setFechaComentario(timestamp.toLocalDateTime());
                }
                
                comentarios.add(comentario);
                System.out.println("DEBUG: Comentario encontrado - " + 
                                 autor.getNombre() + ": " + comentario.getContenido());
            }
            
            System.out.println("DEBUG: Total comentarios encontrados: " + comentarios.size());
            
        } catch (SQLException e) {
            System.err.println("ERROR SQL en obtenerPorPublicacion: " + e.getMessage());
            throw e;
        }
        
        return comentarios;
    }
    
    
    public estructuras.ArrayListPersonalizado<Comentario> obtenerPorPublicacionPersonalizado(String publicacionId) 
        throws SQLException {
    
    // Crear ArrayListPersonalizado en lugar de ArrayList
    estructuras.ArrayListPersonalizado<Comentario> comentarios = new estructuras.ArrayListPersonalizado<>();
    
    String sql = "SELECT c.id, c.contenido, c.fecha_creacion, c.id_publicacion, c.id_autor, " +
                "u.id as usuario_id, u.nombre as usuario_nombre, u.correo as usuario_correo " +
                "FROM comentarios c " +
                "JOIN usuarios u ON c.id_autor = u.id " +
                "WHERE c.id_publicacion = ? " +
                "ORDER BY c.fecha_creacion ASC";
    
    try (Connection conn = ConexionBD.getConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, publicacionId);
        
        System.out.println("DEBUG: Buscando comentarios para publicación ID: " + publicacionId);
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Comentario comentario = new Comentario();
            comentario.setId(rs.getString("id"));
            comentario.setContenido(rs.getString("contenido"));
            comentario.setIdPublicacion(rs.getString("id_publicacion"));
            
            // Crear el usuario autor
            Usuario autor = new Usuario();
            autor.setId(rs.getString("usuario_id"));
            autor.setNombre(rs.getString("usuario_nombre"));
            autor.setCorreo(rs.getString("usuario_correo"));
            comentario.setAutor(autor);
            
            // Establecer fecha
            java.sql.Timestamp timestamp = rs.getTimestamp("fecha_creacion");
            if (timestamp != null) {
                comentario.setFechaCreacion(timestamp.toLocalDateTime());
            }
            
            // Usar el método agregar() de ArrayListPersonalizado
            comentarios.agregar(comentario);
            
            System.out.println("DEBUG: Comentario encontrado - " + 
                             autor.getNombre() + ": " + comentario.getContenido());
        }
        
        System.out.println("DEBUG: Total comentarios encontrados: " + comentarios.tamaño());
        
    } catch (SQLException e) {
        System.err.println("ERROR SQL en obtenerPorPublicacionPersonalizado: " + e.getMessage());
        throw e;
    }
    
    return comentarios;
}
    
    
    
    
    
}