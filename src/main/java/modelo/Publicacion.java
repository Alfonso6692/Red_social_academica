package modelo;

import estructuras.ArrayListPersonalizado;
import java.time.LocalDateTime;

public class Publicacion {

    private String id;
    private String contenido;
    private LocalDateTime fechaPublicacion;
    private Usuario usuario; // Representa al autor
    private ArrayListPersonalizado<Comentario> comentarios;

    public Publicacion() {
        this.comentarios = new ArrayListPersonalizado<>();
    }

    // --- GETTERS Y SETTERS (ESTO SOLUCIONA TUS ERRORES) ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayListPersonalizado<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayListPersonalizado<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    // Dentro de la clase modelo/Publicacion.java
    public void agregarComentario(Comentario nuevoComentario) {
        // Se asegura de que la lista de comentarios no sea nula antes de agregar
        if (this.comentarios == null) {
            this.comentarios = new estructuras.ArrayListPersonalizado<>();
        }
        this.comentarios.agregar(nuevoComentario);
    }
}
