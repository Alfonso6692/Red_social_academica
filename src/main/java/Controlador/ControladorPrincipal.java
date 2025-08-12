package controlador;

import modelo.Publicacion;
import modelo.Usuario;
import modelo.Comentario;
import persistencia.PublicacionDAO;
import persistencia.UsuarioDAO;
import persistencia.ComentarioDAO;
import vista.PublicacionVista;
import vista.VentanaDashboard;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.swing.JOptionPane;

public class ControladorPrincipal {

    private PublicacionDAO publicacionDAO;
    private UsuarioDAO usuarioDAO;
    private ComentarioDAO comentarioDAO;
    private Usuario usuarioActual;

    public ControladorPrincipal() {
        this.publicacionDAO = new PublicacionDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.comentarioDAO = new ComentarioDAO();
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    
        /**
     * Crea una nueva publicación
     * @param contenido
     * @param autor
     * @param dashboard
     * @return 
     */
    public boolean crearPublicacion(String contenido, Usuario autor, VentanaDashboard dashboard) {
        try {
            // Crear nueva publicación con datos reales
            Publicacion nuevaPublicacion = new Publicacion();
            nuevaPublicacion.setId(UUID.randomUUID().toString());
            nuevaPublicacion.setContenido(contenido);
            nuevaPublicacion.setUsuario(autor); // Usuario real logueado
            nuevaPublicacion.setFechaPublicacion(LocalDateTime.now());
            
            // Guardar en base de datos
            publicacionDAO.guardar(nuevaPublicacion);
            
            System.out.println("DEBUG: Publicación creada por: " + autor.getNombre());
            
            // Actualizar el dashboard automáticamente
            if (dashboard != null) {
                dashboard.cargarPublicaciones();
            }
            
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al crear publicación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
      /**
     * Muestra una publicación específica por ID
     */
    public void mostrarVentanaPublicacion(String idPublicacion, Usuario usuarioLogueado, VentanaDashboard dashboard) {
        System.out.println("DEBUG: Abriendo publicación ID: " + idPublicacion);
        System.out.println("DEBUG: Usuario logueado: " + usuarioLogueado.getNombre());
        
        try {
            // Obtener publicación de la BD
            Publicacion publicacion = publicacionDAO.obtenerPorId(idPublicacion);
            
            if (publicacion == null) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontró la publicación", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cargar comentarios de la publicación
            estructuras.ArrayListPersonalizado<Comentario> comentarios = 
                comentarioDAO.obtenerPorPublicacionPersonalizado(idPublicacion);
            publicacion.setComentarios(comentarios);
            
            // Crear y mostrar la vista
            PublicacionVista vistaPublicacion = new PublicacionVista(
                publicacion, 
                usuarioLogueado, 
                dashboard
            );
            vistaPublicacion.setVisible(true);
            
            System.out.println("DEBUG: Ventana de publicación abierta");
            
        } catch (SQLException e) {
            System.err.println("Error al cargar publicación: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error al cargar la publicación: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * Obtiene todas las publicaciones para el dashboard
     * @return 
     */
    public java.util.List<Publicacion> obtenerTodasLasPublicaciones() {
        try {
            return publicacionDAO.obtenerTodasLasPublicaciones();
        } catch (SQLException e) {
            System.err.println("Error al obtener publicaciones: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Elimina una publicación
     * @param idPublicacion
     * @param dashboard
     * @return 
     */
    public boolean eliminarPublicacion(String idPublicacion, VentanaDashboard dashboard) {
        try {
            publicacionDAO.eliminar(idPublicacion);
            
            // Actualizar dashboard
            if (dashboard != null) {
                dashboard.cargarPublicaciones();
            }
            
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar publicación: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza una publicación existente
     * @param publicacion
     * @param dashboard
     * @return 
     */
    public boolean actualizarPublicacion(Publicacion publicacion, VentanaDashboard dashboard) {
        try {
            publicacionDAO.actualizar(publicacion);
            
            // Actualizar dashboard
            if (dashboard != null) {
                dashboard.cargarPublicaciones();
            }
            
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar publicación: " + e.getMessage());
            return false;
        }
    }

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
            PublicacionVista vistaPublicacion = new vista.PublicacionVista(publicacionAMostrar, usuarioLogueado, dashboard);
            vistaPublicacion.setVisible(true);
            System.out.println("DEBUG: PublicacionVista creada CON dashboard");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Método simplificado para mostrar una publicación de ejemplo
     * @param usuario - el usuario logueado
     * @param dashboard - referencia al dashboard
     */
    public void mostrarVentanaPublicacionEjemplo(Usuario usuario, VentanaDashboard dashboard) {
        try {
            // Obtener la primera publicación disponible
            java.util.List<Publicacion> publicaciones = obtenerTodasLasPublicaciones();
            
            if (publicaciones.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No hay publicaciones disponibles para mostrar", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Tomar la primera publicación como ejemplo
            Publicacion publicacionEjemplo = publicaciones.get(0);
            
            // Mostrar la ventana de publicación
            mostrarVentanaPublicacion(
                publicacionEjemplo.getId(), 
                usuario, 
                dashboard
            );
            
        } catch (Exception e) {
            System.err.println("Error al mostrar publicación de ejemplo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error al cargar la publicación: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}


