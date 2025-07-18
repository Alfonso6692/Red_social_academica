/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import java.sql.SQLException;
import java.util.List;
import modelo.Usuario;
import persistencia.ConexionDAO;
import modelo.Conexion;

/**
 *
 * @author ASUS-VASQUEZ
 */
public class ServiciosConexiones {
    
    private final ConexionDAO conexionDAO;
    
    public ServiciosConexiones() {
        this.conexionDAO = new ConexionDAO();
    }
    
    // Dentro de la clase ServicioConexiones.java
public void enviarSolicitud(Usuario solicitante, Usuario destinatario) throws SQLException {
        // ... (tu código actual para crear la solicitud)
        conexionDAO.crearSolicitud(solicitante.getId(), destinatario.getId());

       
        try {
            ServicioNotificaciones servicioNotif = new ServicioNotificaciones();
            String mensaje = solicitante.getNombre() + " " + solicitante.getApellido() + " te ha enviado una solicitud de conexión.";
            servicioNotif.crearNotificacion(destinatario.getId(), mensaje);
        } catch (SQLException e) {
            // Opcional: manejar el error si la notificación no se puede crear
            e.printStackTrace();
        }
    }
    
    public List<Conexion> obtenerSolicitudesPendientes(Usuario destinatario) throws SQLException {
        return conexionDAO.obtenerSolicitudesPendientes(destinatario.getId());
    }
    
    public void aceptarSolicitud(String idSolicitante, String idDestinatario) throws SQLException {
        conexionDAO.aceptarSolicitud(idSolicitante, idDestinatario);
    }
    // Dentro de la clase ServicioConexiones.java
public java.util.List<Usuario> obtenerConexiones(Usuario usuario) throws SQLException {
        return conexionDAO.obtenerConexionesAceptadas(usuario.getId());
}
}
