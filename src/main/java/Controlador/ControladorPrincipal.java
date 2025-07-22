package controlador;

import modelo.Publicacion;
import modelo.Usuario;
import persistencia.PublicacionDAO;
import persistencia.UsuarioDAO;
import vista.PublicacionVista;

public class ControladorPrincipal {

    public void mostrarVentanaPublicacion() {
        // 1. Crear instancias de los DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PublicacionDAO publicacionDAO = new PublicacionDAO();

        try {
            // 2. Usar los DAO para obtener los datos
            // CORRECCIÓN: Se usa buscarPorCorreo con un correo de ejemplo
            Usuario usuarioLogueado = usuarioDAO.buscarPorCorreo("carlos.garcia@gmail.com"); 
            
            // CORRECCIÓN: Se usa obtenerPublicacionSimple
            Publicacion publicacionAMostrar = publicacionDAO.obtenerPublicacionSimple();

            // 3. Verificación para evitar errores si algo no se encuentra
            if (usuarioLogueado == null || publicacionAMostrar == null) {
                System.err.println("Error: No se pudieron cargar los datos de ejemplo desde el DAO.");
                // En una app real, mostrarías un JOptionPane con un error.
                return;
            }

            // 4. Crear y mostrar la Vista, pasándole los datos "reales" obtenidos del DAO
            PublicacionVista vistaPublicacion = new PublicacionVista(publicacionAMostrar, usuarioLogueado);
            vistaPublicacion.setVisible(true);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            // Manejar el error de la base de datos
        }
    }
}