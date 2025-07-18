package servicios;

import modelo.Mensajes;
import modelo.Usuario;
import persistencia.MensajeDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ServicioMensajeria {
    
    private final MensajeDAO mensajeDAO = new MensajeDAO();

    public void enviarMensaje(Usuario emisor, Usuario receptor, String contenido) throws SQLException {
        Mensajes msg = new Mensajes();
        msg.setId(UUID.randomUUID().toString());
        msg.setIdEmisor(emisor.getId());
        msg.setIdReceptor(receptor.getId());
        msg.setContenido(contenido);
        msg.setFechaEnvio(LocalDateTime.now());
        mensajeDAO.guardar(msg);
    }

    public List<Mensajes> obtenerConversacion(Usuario u1, Usuario u2) throws SQLException {
        return mensajeDAO.obtenerConversacion(u1.getId(), u2.getId());
    }
}