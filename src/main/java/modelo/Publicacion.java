package modelo;

import estructuras.ArrayListPersonalizado;
import java.time.LocalDateTime;


public class Publicacion {
    private Usuario autor;
    private String contenido;
    private String textoPublicacion;
    private LocalDateTime fechaPublicacion;
    private ArrayListPersonalizado<Comentario> comentarios;

    public Publicacion(Usuario autor, String textoPublicacion) {
        this.autor = autor;
        this.textoPublicacion = textoPublicacion;
        this.comentarios = new ArrayListPersonalizado<>();
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

    public void agregarComentario(Comentario nuevoComentario) {
        this.comentarios.agregar(nuevoComentario);
    }

    // --- Getters para que la Vista acceda a los datos ---
    public Usuario getAutor() {
        return autor;
    }

    public String getTextoPublicacion() {
        return textoPublicacion;
    }

    public ArrayListPersonalizado<Comentario> getComentarios() {
        return comentarios;
    }

    public void setId(String string) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}