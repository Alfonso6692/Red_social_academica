package controlador;

import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;
import vista.PublicacionVista;

/**
 * Esta clase se encarga de la lógica principal de la aplicación,
 * como la creación de ventanas y la preparación de datos.
 */
public class ControladorPrincipal {

    /**
     * Prepara los datos de una publicación de ejemplo y luego
     * crea y muestra la ventana para visualizarla.
     */
    public void mostrarVentanaPublicacion() {
        // 1. Prepara el Modelo (los datos)
        Usuario autor = new Usuario("carlos_dev", "Carlos Santana");
        Publicacion publicacionDeEjemplo = new Publicacion(autor, "Este es el post que se abre desde el Dashboard. ¡Funciona!");
        publicacionDeEjemplo.agregarComentario(new Comentario(new Usuario("ana_code", "Ana"), "¡Excelente! Click para ver."));

        // 2. Crea y muestra la Vista, pasándole los datos
        PublicacionVista vistaPublicacion = new PublicacionVista(publicacionDeEjemplo);
        vistaPublicacion.setVisible(true);
    }
}