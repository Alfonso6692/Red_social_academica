package modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Recurso {

    private String id;
    private String titulo;
    private String descripcion;
    private String nombreArchivo;
    private String tipoArchivo;
    private LocalDate fechaPublicacion;
    private Usuario usuario; // El usuario que subió el recurso

    // Constructor vacío
    public Recurso() {
    }

    // Constructor completo
    public Recurso(String id, String titulo, String descripcion, String nombreArchivo, String tipoArchivo, LocalDate fechaPublicacion, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.fechaPublicacion = fechaPublicacion;
        this.usuario = usuario;
    }

    // --- GETTERS Y SETTERS (ESTO ES LO QUE SOLUCIONA TU ERROR) ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recurso recurso = (Recurso) o;
        return Objects.equals(id, recurso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}