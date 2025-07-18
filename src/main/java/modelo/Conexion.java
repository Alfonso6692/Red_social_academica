package modelo;

/**
 * Clase que representa una conexión (o solicitud) entre dos usuarios.
 */
public class Conexion {

    // --- ATRIBUTOS ---
    private String idSolicitante;
    private String idDestinatario;
    private String estado;
    
    // Este es un campo temporal para guardar el nombre de quien envía la solicitud
    // y mostrarlo fácilmente en la interfaz. No se guarda en la base de datos.
    private String nombreSolicitante;

    // --- CONSTRUCTOR ---
    public Conexion() {
    }

    // --- GETTERS Y SETTERS ---

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }
}