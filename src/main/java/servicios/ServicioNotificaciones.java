package servicios;

import modelo.Notificacion;
import modelo.Usuario;
import persistencia.NotificacionDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ServicioNotificaciones {

    private final NotificacionDAO notificacionDAO = new NotificacionDAO();

    /**
     * Crea y guarda una nueva notificación para un usuario.
     *
     * @param idUsuarioDestino
     * @param mensaje
     * @throws java.sql.SQLException
     */
    public void crearNotificacion(String idUsuarioDestino, String mensaje) throws SQLException {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(UUID.randomUUID().toString());
        notificacion.setIdUsuario(idUsuarioDestino);
        notificacion.setMensaje(mensaje);
        notificacion.setLeida(false);
        notificacion.setFechaCreacion(LocalDateTime.now());

        notificacionDAO.guardar(notificacion);
    }

    /**
     * Marca una lista de notificaciones como leídas.
     * @param notificaciones
     * @throws java.sql.SQLException
     */
    public void marcarComoLeidas(List<Notificacion> notificaciones) throws SQLException {
        // Llama al método del DAO para cada notificación en la lista
        for (Notificacion notificacion : notificaciones) {
            notificacionDAO.marcarComoLeida(notificacion.getId());
        }
    }

    /**
     * Obtiene las notificaciones no leídas para un usuario.
     *
     * @param usuario
     * @return
     * @throws java.sql.SQLException
     */
    public List<Notificacion> obtenerNotificacionesNoLeidas(Usuario usuario) throws SQLException {
        return notificacionDAO.obtenerNoLeidasPorUsuario(usuario.getId());
    }
}
