package controlador;

import modelo.Publicacion;
import modelo.Usuario;
import persistencia.PublicacionDAO;
import persistencia.UsuarioDAO;
import vista.PublicacionVista;
import vista.VentanaDashboard;

public class ControladorPrincipal {

    /**
     * Muestra una publicación específica por ID
     *
     * @param emailUsuario
     * @param idPublicacion
     * @param dashboard
     */
    public void mostrarVentanaPublicacion(String emailUsuario, String idPublicacion, vista.VentanaDashboard dashboard) {
        
        System.out.println("DEBUG: mostrarVentanaPublicacion CON dashboard llamado");
        System.out.println("DEBUG: Dashboard recibido = " + dashboard);
        // 1. Crear instancias de los DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PublicacionDAO publicacionDAO = new PublicacionDAO();

        try {
            // 2. Obtener usuario logueado por email
            Usuario usuarioLogueado = usuarioDAO.buscarPorCorreo(emailUsuario);

            // 3. Obtener publicación específica por ID
            Publicacion publicacionAMostrar = publicacionDAO.obtenerPorId(idPublicacion);

            // 4. Verificación para evitar errores
            if (usuarioLogueado == null || publicacionAMostrar == null) {
                System.err.println("Error: No se pudieron cargar los datos de ejemplo desde el DAO.");
                return;
            }    
            

            // 5. Crear y mostrar la Vista con los datos correctos
            PublicacionVista vistaPublicacion = new vista.PublicacionVista(publicacionAMostrar, usuarioLogueado,dashboard);
            vistaPublicacion.setVisible(true);
            System.out.println("DEBUG: PublicacionVista creada CON dashboard");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            
        }
    }

    /**
     * Método de ejemplo - muestra la primera publicación disponible
     * @param emailUsuario
     * @param idPublicacion
     * @param dashboard
     */
    public void mostrarVentanaPublicacionEjemplo(String emailUsuario, String idPublicacion, vista.VentanaDashboard dashboard) {
        System.out.println("DEBUG: mostrarVentanaPublicacion SIN dashboard llamado");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PublicacionDAO publicacionDAO = new PublicacionDAO();

        try {
        Usuario usuarioLogueado = usuarioDAO.buscarPorCorreo(emailUsuario);
        Publicacion publicacionAMostrar = publicacionDAO.obtenerPorId(idPublicacion);   
        
        if (usuarioLogueado == null) {
            System.err.println("Error: Publicacion no encontrada con ID: "+idPublicacion);
            return;
        }
        
        // CORREGIDO: Usar nombre completo de la clase
        vista.PublicacionVista vistaPublicacion = new vista.PublicacionVista(publicacionAMostrar, usuarioLogueado,dashboard);
        vistaPublicacion.setVisible(true);
        
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
            System.out.println("Error en la base de datos"+e.getMessage());
    }
    }
}
