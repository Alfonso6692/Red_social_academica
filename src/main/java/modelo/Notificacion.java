package modelo;

import java.time.LocalDateTime;

/**
 * Representa una notificación para un usuario.
 */
public class Notificacion {

    private String id;
    private String idUsuario; // ID del usuario que recibe la notificación
    private String mensaje;
    private boolean leida;
    private LocalDateTime fechaCreacion;

    /**
     * Constructor por defecto.
     */
    public Notificacion() {
    }

    /**
     * Constructor con todos los campos.
     * @param id
     * @param idUsuario
     * @param mensaje
     * @param leida
     * @param fechaCreacion
     */
    public Notificacion(String id, String idUsuario, String mensaje, boolean leida, LocalDateTime fechaCreacion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
        this.leida = leida;
        this.fechaCreacion = fechaCreacion;
    }
    
    // --- GETTERS Y SETTERS ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}