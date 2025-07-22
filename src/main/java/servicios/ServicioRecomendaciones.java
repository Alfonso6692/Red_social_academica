package servicios;

import estructuras.Grafo;
import java.sql.SQLException;
import java.util.List;
import modelo.Usuario;
import persistencia.ConexionDAO;
import persistencia.UsuarioDAO;

public class ServicioRecomendaciones {

    // 1. La única instancia de la clase (Patrón Singleton)
    private static ServicioRecomendaciones instancia;
    
    private final Grafo<Usuario> grafoUsuarios;

    // 2. El constructor es PRIVADO para que nadie más pueda crear instancias
    private ServicioRecomendaciones() {
        this.grafoUsuarios = new Grafo<>();
        // Carga los datos al iniciar el servicio por primera vez
        try {
            cargarGrafoDesdeBD();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar error, quizás el grafo queda vacío
        }
    }

    // 3. El método PÚBLICO para obtener la única instancia
    public static ServicioRecomendaciones getInstancia() {
        if (instancia == null) {
            instancia = new ServicioRecomendaciones();
        }
        return instancia;
    }
    
    /**
     * Carga todos los usuarios y conexiones de la BD para construir el grafo.
     */
    private void cargarGrafoDesdeBD() throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ConexionDAO conexionDAO = new ConexionDAO();

        // Añade todos los usuarios como vértices
        List<Usuario> todosLosUsuarios = usuarioDAO.obtenerTodos(); 
        for (Usuario u : todosLosUsuarios) {
            grafoUsuarios.agregarVertice(u);
        }

        // Añade todas las conexiones como aristas
        List<modelo.Conexion> todasLasConexiones = conexionDAO.obtenerTodasLasConexionesAceptadas(); // Y este también
        for (modelo.Conexion c : todasLasConexiones) {
            Usuario u1 = usuarioDAO.buscarPorId(c.getIdSolicitante());
            Usuario u2 = usuarioDAO.buscarPorId(c.getIdDestinatario());
            if (u1 != null && u2 != null) {
                grafoUsuarios.agregarArista(u1, u2); // Asumiendo que tienes un método así
            }
        }
    }

    /**
     * Llama al método del grafo para obtener las sugerencias de amigos.
     * @param usuario
     * @return 
     */
    public List<Usuario> sugerirAmigos(Usuario usuario) {
         return this.grafoUsuarios.sugerirConexiones(usuario);
    }
}