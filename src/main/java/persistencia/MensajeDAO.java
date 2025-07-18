package persistencia;

import modelo.Mensajes;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MensajeDAO {

    public void guardar(Mensajes mensaje) throws SQLException {
        String sql = "INSERT INTO mensajes (id, id_emisor, id_receptor, contenido, fecha_envio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mensaje.getId());
            pstmt.setString(2, mensaje.getIdEmisor());
            pstmt.setString(3, mensaje.getIdReceptor());
            pstmt.setString(4, mensaje.getContenido());
            pstmt.setTimestamp(5, Timestamp.valueOf(mensaje.getFechaEnvio()));
            pstmt.executeUpdate();
        }
    }

    public List<Mensajes> obtenerConversacion(String idUsuario1, String idUsuario2) throws SQLException {
        List<Mensajes> conversacion = new ArrayList<>();
        String sql = "SELECT * FROM mensajes WHERE (id_emisor = ? AND id_receptor = ?) OR (id_emisor = ? AND id_receptor = ?) ORDER BY fecha_envio ASC";
        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idUsuario1);
            pstmt.setString(2, idUsuario2);
            pstmt.setString(3, idUsuario2);
            pstmt.setString(4, idUsuario1);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mensajes msg = new Mensajes();
                msg.setIdEmisor(rs.getString("id_emisor"));
                msg.setContenido(rs.getString("contenido"));
                conversacion.add(msg);
            }
        }
        return conversacion;
    }
}