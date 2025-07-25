package controlador;

import modelo.Publicacion;
import modelo.Usuario;
import persistencia.PublicacionDAO;
import persistencia.UsuarioDAO;
import vista.PublicacionVista;

public class ControladorPrincipal {

    /**
     * Muestra una publicación específica por ID
     */
    public void mostrarVentanaPublicacion(String emailUsuario, String idPublicacion) {
        // 1. Crear instancias de los DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PublicacionDAO pubDAO = new PublicacionDAO();

        try {
            // 2. Obtener usuario logueado por email
            Usuario usuarioLogueado = usuarioDAO.buscarPorCorreo(emailUsuario);

            // 3. Obtener publicación específica por ID
            Publicacion publicacionAMostrar = pubDAO.obtenerPorId(idPublicacion);

            // 4. Verificación para evitar errores
            if (usuarioLogueado == null) {
                System.err.println("Error: Usuario no encontrado con email: " + emailUsuario);
                return;
            }

            if (publicacionAMostrar == null) {
                System.err.println("Error: Publicación no encontrada con ID: " + idPublicacion);
                return;
            }

            // 5. Crear y mostrar la Vista con los datos correctos
            PublicacionVista vistaPublicacion = new PublicacionVista(publicacionAMostrar, usuarioLogueado);
            vistaPublicacion.setVisible(true);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.err.println("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Método de ejemplo - muestra la primera publicación disponible
     */
    public void mostrarVentanaPublicacionEjemplo(String emailUsuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PublicacionDAO pubDAO = new PublicacionDAO();

        try {
            Usuario usuarioLogueado = usuarioDAO.buscarPorCorreo(emailUsuario);
            Publicacion publicacionAMostrar = pubDAO.obtenerPublicacionSimple();

            if (usuarioLogueado == null || publicacionAMostrar == null) {
                System.err.println("Error: No se pudieron cargar los datos de ejemplo desde el DAO.");
                return;
            }

            PublicacionVista vistaPublicacion = new PublicacionVista(publicacionAMostrar, usuarioLogueado);
            vistaPublicacion.setVisible(true);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
