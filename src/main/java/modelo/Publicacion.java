package modelo;

import estructuras.ArrayListPersonalizado;


public class Publicacion {
    private Usuario autor;
    private String textoPublicacion;
    private ArrayListPersonalizado<Comentario> comentarios;

    public Publicacion(Usuario autor, String textoPublicacion) {
        this.autor = autor;
        this.textoPublicacion = textoPublicacion;
        this.comentarios = new ArrayListPersonalizado<>();
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
}