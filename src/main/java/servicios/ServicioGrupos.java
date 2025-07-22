package servicios;

import modelo.Grupo;
import modelo.Usuario;
import persistencia.GrupoDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ServicioGrupos {

    private final GrupoDAO grupoDAO;

    public ServicioGrupos() {
        this.grupoDAO = new GrupoDAO();
    }

    public Grupo crearGrupo(String nombre, String descripcion, Usuario creador) throws SQLException {
        String id = UUID.randomUUID().toString();
        Grupo nuevoGrupo = new Grupo(id, nombre, descripcion, creador);
        grupoDAO.guardar(nuevoGrupo);
        return nuevoGrupo;
    }

    // --- MÉTODO MOVIDO A SU LUGAR CORRECTO ---
    public void unirUsuarioAGrupo(Grupo grupo, Usuario usuario) throws SQLException {
        grupoDAO.unirUsuarioAGrupo(grupo.getId(), usuario.getId());
    }
    
    public List<Grupo> obtenerTodosLosGrupos() throws SQLException {
        return grupoDAO.obtenerTodos();
    }
    
    public List<Grupo> obtenerGruposDelUsuario(Usuario usuario) throws SQLException {
        return grupoDAO.obtenerGruposPorUsuario(usuario.getId());
    }
}