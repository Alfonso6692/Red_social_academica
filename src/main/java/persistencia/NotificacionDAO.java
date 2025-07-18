package persistencia;

import modelo.Notificacion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO {

    /**
     * Guarda una nueva notificación en la base de datos.
     * @param notificacion
     * @throws java.sql.SQLException
     */
    public void guardar(Notificacion notificacion) throws SQLException {
        String sql = "INSERT INTO notificaciones (id, id_usuario, mensaje, leida, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, notificacion.getId());
            pstmt.setString(2, notificacion.getIdUsuario());
            pstmt.setString(3, notificacion.getMensaje());
            pstmt.setBoolean(4, notificacion.isLeida());
            pstmt.setTimestamp(5, Timestamp.valueOf(notificacion.getFechaCreacion()));
            pstmt.executeUpdate();
        }
    }

    /**
     * Obtiene las notificaciones no leídas de un usuario.
     * @param idUsuario
     * @return 
     * @throws java.sql.SQLException
     */
    public List<Notificacion> obtenerNoLeidasPorUsuario(String idUsuario) throws SQLException {
        List<Notificacion> notificaciones = new ArrayList<>();
        String sql = "SELECT * FROM notificaciones WHERE id_usuario = ? AND leida = FALSE ORDER BY fecha_creacion DESC";
        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Notificacion n = new Notificacion();
                n.setId(rs.getString("id"));
                n.setIdUsuario(rs.getString("id_usuario"));
                n.setMensaje(rs.getString("mensaje"));
                n.setLeida(rs.getBoolean("leida"));
                n.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                notificaciones.add(n);
            }
        }
        return notificaciones;
    }

    /**
     * Marca una notificación como leída en la base de datos.
     * @param idNotificacion
     * @throws java.sql.SQLException
     */
    public void marcarComoLeida(String idNotificacion) throws SQLException {
        String sql = "UPDATE notificaciones SET leida = TRUE WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idNotificacion);
            pstmt.executeUpdate();
        }
    }
}