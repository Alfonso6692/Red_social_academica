package servicios;

import modelo.Grupo;
import modelo.Usuario;
import persistencia.GrupoDAO; // Asegúrate de que este import esté presente
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
public class ServicioGrupos {

    private final GrupoDAO grupoDAO;

    public ServicioGrupos() {
        this.grupoDAO = new GrupoDAO();
    }

    /**
     * Lógica para crear un nuevo grupo de estudio.
     *
     * @param nombre El nombre del grupo.
     * @param descripcion La descripción del grupo.
     * @param creador El usuario que crea el grupo.
     * @return El grupo recién creado.
     * @throws SQLException Si hay un error en la base de datos.
     */
   public Grupo crearGrupo(String nombre, String descripcion, Usuario creador) throws SQLException {
        String id = UUID.randomUUID().toString();
        Grupo nuevoGrupo = new Grupo(id, nombre, descripcion, creador);
        grupoDAO.guardar(nuevoGrupo); // Se usa la variable grupoDAO
        return nuevoGrupo;
    }

    // Dentro de la clase ServicioGrupos.java
  public void unirUsuarioAGrupo(Grupo grupo, Usuario usuario) throws SQLException {
        // 3. Llama al método usando la variable (con 'g' minúscula)
        grupoDAO.unirUsuarioAGrupo(grupo.getId(), usuario.getId());
    }
  


    // Dentro de la clase ServicioGrupos.java
    public java.util.List<Grupo> obtenerTodosLosGrupos() throws SQLException {
        return grupoDAO.obtenerTodos();
    }
    
    // Dentro de la clase ServicioGrupos.java

public java.util.List<Grupo> obtenerGruposDelUsuario(Usuario usuario) throws SQLException {
    return grupoDAO.obtenerGruposPorUsuario(usuario.getId());
}
}
