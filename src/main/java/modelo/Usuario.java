// Paquete actualizado para coincidir con tu proyecto
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa a un usuario en la red social académica.
 * Contiene toda la información personal y académica del estudiante.
 */
public class Usuario {

    // --- ATRIBUTOS ---
    private String id;
    private String nombre;
    private String apellido;
    private String segundo_apellido;
    private String correo;
    private String contrasena;
    private String carrera;
    private int ciclo;
    private List<String> intereses;

    // --- CONSTRUCTORES ---

    /**
     * Constructor por defecto.
     */
    public Usuario() {
        this.intereses = new ArrayList<>();
    }

    /**
     * Constructor completo para crear un nuevo usuario.
     * @param id
     * @param ciclo
     * @param nombre
     * @param segundo_apellido
     * @param carrera
     * @param apellido
     * @param contrasena
     * @param correo
     */
    public Usuario(String id, String nombre, String apellido, String correo, String contrasena, String carrera, int ciclo, String segundo_apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.carrera = carrera;
        this.ciclo = ciclo;
        this.segundo_apellido = segundo_apellido;
        this.intereses = new ArrayList<>();
    }

    // --- GETTERS Y SETTERS ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundoApellido) {
        this.segundo_apellido = segundoApellido;
    }
    
   

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public List<String> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<String> intereses) {
        this.intereses = intereses;
    }

    // --- MÉTODOS ADICIONALES ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}