package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

public class ConexionDAO {

    // Dentro de la clase ConexionDAO.java
    public java.util.List<Usuario> obtenerConexionesAceptadas(String idUsuario) throws SQLException {
        java.util.List<Usuario> amigos = new java.util.ArrayList<>();
        // Consulta que busca en ambas columnas y se une con usuarios para obtener los datos del amigo
        String sql = "SELECT u.* FROM usuarios u JOIN conexiones c ON "
                + "(u.id = c.id_destinatario AND c.id_solicitante = ?) OR "
                + "(u.id = c.id_solicitante AND c.id_destinatario = ?) "
                + "WHERE c.estado = 'aceptada'";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idUsuario);
            pstmt.setString(2, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario amigo = new Usuario();
                    amigo.setId(rs.getString("id"));
                    amigo.setNombre(rs.getString("nombre"));
                    amigo.setApellido(rs.getString("apellido"));
                    amigos.add(amigo);
                }
            }
        }
        return amigos;
    }

    /**
     * Actualiza el estado de una conexión a 'aceptada'.
     *
     * @param idSolicitante
     * @param idDestinatario
     * @throws java.sql.SQLException
     */
    public void aceptarSolicitud(String idSolicitante, String idDestinatario) throws SQLException {
        String sql = "UPDATE conexiones SET estado = 'aceptada' WHERE id_solicitante = ? AND id_destinatario = ?";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idSolicitante);
            pstmt.setString(2, idDestinatario);

            pstmt.executeUpdate();
        }
    }

    /**
     * Inserta una nueva fila en la tabla 'conexiones' con estado 'pendiente'.
     *
     * @param idSolicitante El ID del usuario que envía la solicitud.
     * @param idDestinatario El ID del usuario que recibe la solicitud.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void crearSolicitud(String idSolicitante, String idDestinatario) throws SQLException {
        String sql = "INSERT INTO conexiones (id_solicitante, id_destinatario, estado) VALUES (?, ?, 'pendiente')";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idSolicitante);
            pstmt.setString(2, idDestinatario);

            pstmt.executeUpdate();
        }
    }

    // Dentro de la clase ConexionDAO.java
    /**
     * Obtiene todas las solicitudes de conexión pendientes para un usuario.
     *
     * @param idDestinatario El ID del usuario que recibió las solicitudes.
     * @return Una lista de objetos Conexion.
     */
    public java.util.List<modelo.Conexion> obtenerSolicitudesPendientes(String idDestinatario) throws SQLException {
        java.util.List<modelo.Conexion> solicitudes = new java.util.ArrayList<>();
        // Esta consulta obtiene la información de la conexión y el nombre del solicitante
        String sql = "SELECT c.*, u.nombre, u.apellido FROM conexiones c "
                + "JOIN usuarios u ON c.id_solicitante = u.id "
                + "WHERE c.id_destinatario = ? AND c.estado = 'pendiente'";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idDestinatario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Asumiendo que tienes una clase modelo.Conexion
                    modelo.Conexion sol = new modelo.Conexion();
                    sol.setIdSolicitante(rs.getString("id_solicitante"));
                    sol.setIdDestinatario(rs.getString("id_destinatario"));
                    sol.setEstado(rs.getString("estado"));

                    // Guardamos el nombre del solicitante para mostrarlo en la UI
                    String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                    sol.setNombreSolicitante(nombreCompleto); // Necesitarás añadir este campo a tu clase Conexion

                    solicitudes.add(sol);
                }
            }
        }
        return solicitudes;
    }

}
