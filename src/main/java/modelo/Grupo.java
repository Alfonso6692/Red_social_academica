package modelo;

import java.util.Objects;

public class Grupo {

    //Atributos
    private String id;
    private String nombre;
    private String descripcion;
    private Usuario creador; //El usuario que creo el grupo

    public Grupo() {

    }

    public Grupo(String id, String nombre, String descripcion, Usuario creador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    @Override
    public String toString() {
        return nombre; //Para que se vea bien en lista y otros componentes
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;    
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return Objects.equals(id, grupo.id);
    }
    
    @Override
    public int hashCode() {
       return Objects.hash(id);
    }

}
