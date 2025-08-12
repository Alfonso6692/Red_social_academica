package modelo;

import java.time.LocalDateTime;

/**
 * Representa un comentario realizado por un usuario en una publicación.
 */
public class Comentario {

    // --- ATRIBUTOS ---
    // Se necesita un ID para la base de datos y saber a qué publicación pertenece
    private String id;
    private String contenido;
    private LocalDateTime fechaCreacion;
    private Usuario autor;
    private String idPublicacion; // Para la clave foránea en la BD
    //private java.time.LocalDateTime fechaComentario;

    /**
     * Constructor por defecto (vacío).
     * Necesario para que el DAO pueda crear un objeto y luego llenarlo.
     */
    public Comentario() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    /**
     * Crea una nueva instancia de un Comentario.
     * @param autor El usuario que realiza el comentario.
     * @param contenido El contenido del comentario.
     * @param idPublicacion
     */
    public Comentario(Usuario autor, String contenido, String idPublicacion ) {
        this.autor = autor;
        this.contenido= contenido;
         this.idPublicacion = idPublicacion;
        this.fechaCreacion = LocalDateTime.now();
    }

    // --- GETTERS Y SETTERS ---
    // Se necesitan setters para que el DAO pueda asignar los valores desde la BD

    
    
    
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

   // ✅ ALIAS para compatibilidad con el código existente
    public LocalDateTime getFechaComentario() {
        return fechaCreacion; // Devolver fechaCreacion
    }

     public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaCreacion = fechaComentario; // Asignar a fechaCreacion
    }
    
     public String getTexto() {
        return contenido;
    }
    
    public void setTexto(String texto) {
        this.contenido = texto;
    }
    
    @Override
    public String toString() {
        return autor.getNombre() + ": " + getContenido();
    }
    

    
}