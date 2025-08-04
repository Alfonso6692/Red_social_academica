package modelo;

import java.time.LocalDateTime;

/**
 * Representa un comentario realizado por un usuario en una publicación.
 */
public class Comentario {

    // --- ATRIBUTOS ---
    // Se necesita un ID para la base de datos y saber a qué publicación pertenece
    private String id;
    private String texto;
    private LocalDateTime fechaCreacion;
    private Usuario autor;
    private String idPublicacion; // Para la clave foránea en la BD

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
     * @param texto El contenido del comentario.
     * @param idPublicacion
     */
    public Comentario(Usuario autor, String texto, String idPublicacion ) {
        this.autor = autor;
        this.texto = texto;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    @Override
    public String toString() {
        // CORREGIDO: Se usa getNombre() que sí existe en la clase Usuario
        return autor.getNombre() + ": " + texto;
    }
}