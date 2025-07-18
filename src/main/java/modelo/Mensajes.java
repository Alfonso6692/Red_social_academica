package modelo;

import java.time.LocalDateTime;

/**
 * Representa un mensaje individual enviado entre dos usuarios.
 */
public class Mensajes {

    private String id;
    private String idEmisor;
    private String idReceptor;
    private String contenido;
    private LocalDateTime fechaEnvio;

    /**
     * Constructor por defecto.
     */
    public Mensajes() {
    }

    /**
     * Constructor con todos los campos.
     * @param id
     * @param fechaEnvio
     * @param idEmisor
     * @param contenido
     * @param idReceptor
     */
    public Mensajes(String id, String idEmisor, String idReceptor, String contenido, LocalDateTime fechaEnvio) {
        this.id = id;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
    }

    // --- GETTERS Y SETTERS ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}