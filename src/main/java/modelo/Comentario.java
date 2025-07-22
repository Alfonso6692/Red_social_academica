package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa un comentario realizado por un usuario en una publicación.
 */
public class Comentario {

    private final Usuario autor;
    private final String texto;
    private final LocalDateTime fechaHora;

    /**
     * Crea una nueva instancia de un Comentario.
     * La fecha y hora se registran automáticamente al momento de la creación.
     *
     * @param autor El usuario que realiza el comentario.
     * @param texto El contenido del comentario.
     */
    public Comentario(Usuario autor, String texto) {
        this.autor = autor;
        this.texto = texto;
        this.fechaHora = LocalDateTime.now();
    }

    // --- Getters ---

    public Usuario getAutor() {
        return autor;
    }

    public String getTexto() {
        return texto;
    }

    /**
     * Devuelve la fecha y hora en un formato legible para la interfaz.
     * @return Un String con la fecha y hora formateada (ej: 21-07-2025 23:19:42).
     */
    public String getFechaHoraFormateada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return fechaHora.format(formato);
    }

    /**
     * Proporciona una representación en texto del comentario, ideal para mostrar en listas.
     * @return Una cadena de texto formateada.
     */
    @Override
    public String toString() {
        // Ejemplo: "carlos_dev: ¡Gran aporte!"
        return autor.getNombreUsuario() + ": " + texto;
    }
}