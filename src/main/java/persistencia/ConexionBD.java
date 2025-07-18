package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase se encarga únicamente de establecer y devolver
 * una conexión con la base de datos PostgreSQL.
 */
public class ConexionBD {

    // URL de conexión para tu base de datos PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/red_social_academica";
    
    // Tu usuario de PostgreSQL
    private static final String USUARIO = "postgres";
    
    // La contraseña que definiste para tu usuario de PostgreSQL
    private static final String CONTRASENA = "123";

    /**
     * Método estático que devuelve una conexión a la base de datos.
     * @return Un objeto Connection.
     * @throws SQLException Si la conexión falla.
     */
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
    
    
    public static void main(String[] args) {
    try {
        Connection conn = ConexionBD.getConexion();
        System.out.println("***********************************");
        System.out.println("¡CONEXIÓN A POSTGRESQL EXITOSA!");
        System.out.println("***********************************");
        conn.close(); // Cerramos la conexión de prueba
    } catch (SQLException e) {
        System.out.println("*********************************");
        System.out.println("ERROR: LA CONEXIÓN FALLÓ.");
        System.out.println("*********************************");
        e.printStackTrace();
    }
}
}