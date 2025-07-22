package servicios;

import modelo.Usuario;
import persistencia.UsuarioDAO;
import java.util.UUID;

public class ServiciosUsuarios {

    private final UsuarioDAO usuarioDAO;

    public ServiciosUsuarios() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Dentro de la clase ServicioUsuarios.java
    // Dentro de la clase ServicioUsuarios.java
    /**
     * Actualiza los datos de un usuario en la base de datos.
     *
     * @param usuario El objeto Usuario con los datos ya modificados.
     * @throws java.sql.SQLException Si ocurre un error de base de datos.
     */
    public void actualizarUsuario(Usuario usuario) throws java.sql.SQLException {
        // Aquí podría ir lógica de validación antes de guardar.
        // Por ahora, solo llamamos al DAO.
        usuarioDAO.actualizar(usuario);
    }

    /**
     * Obtiene una lista de todos los usuarios registrados excepto el usuario
     * actual.
     *
     * @param usuarioActual El usuario que está realizando la búsqueda.
     * @return Una lista de objetos Usuario.
     * @throws java.sql.SQLException Si hay un error en la base de datos.
     */
    public java.util.List<Usuario> obtenerTodosLosUsuarios(Usuario usuarioActual) throws java.sql.SQLException {
        // Llama al método del DAO que acabamos de verificar
        return usuarioDAO.obtenerTodosExcepto(usuarioActual.getId());
    }

    public Usuario registrarUsuario(String nombre, String apellido, String correo, String contrasena, String carrera, int ciclo, String segundo_apellido) throws Exception {

        if (!correo.contains("@") || !correo.contains(".")) {
            throw new Exception("El formato del correo electrónico no es válido.");
        }
        // --- FIN DE LA VALIDACIÓN ---

        // 1. Validar que el correo no exista (esto ya lo tienes)
        if (usuarioDAO.buscarPorCorreo(correo) != null) {
            throw new Exception("El correo electrónico ya está registrado.");
        }

        // 2. Crear el nuevo usuario
        String id = UUID.randomUUID().toString(); // Generar un ID único
        Usuario nuevoUsuario = new Usuario(id, nombre, apellido, correo, contrasena, carrera, ciclo, segundo_apellido);

        // 3. Guardar en la base de datos
        usuarioDAO.guardar(nuevoUsuario);

        return nuevoUsuario;
    }

    // Dentro de la clase servicios/ServicioUsuarios.java
    /**
     * Valida las credenciales de un usuario para iniciar sesión.
     *
     * @param correo El correo del usuario.
     * @param contrasena La contraseña del usuario.
     * @return El objeto Usuario si las credenciales son correctas.
     * @throws Exception Si el usuario no se encuentra o la contraseña es
     * incorrecta.
     */
    public Usuario loginUsuario(String correo, String contrasena) throws Exception {
        // 1. Buscar al usuario por su correo
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);

        // 2. Verificar si el usuario existe y si la contraseña coincide
        if (usuario == null) {
            throw new Exception("Usuario no encontrado o credenciales incorrectas.");
        }

        if (!usuario.getContrasena().equals(contrasena)) {
            throw new Exception("Contraseña incorrecta.");
        }

        // 3. Si todo es correcto, devolver el objeto usuario
        return usuario;
    }// Dentro de la clase ServicioUsuarios.java

    public java.util.List<String> buscarCorreos(String textoParcial) throws java.sql.SQLException {
        return usuarioDAO.buscarCorreosQueEmpiezanCon(textoParcial);
    }

    public void actualizarFotoPerfil(Usuario usuario, String rutaFoto) throws java.sql.SQLException {
        usuario.setRutaFotoPerfil(rutaFoto); // Actualiza el objeto en memoria
        usuarioDAO.actualizarRutaFoto(usuario.getId(), rutaFoto); // Actualiza la BD
    }

}
