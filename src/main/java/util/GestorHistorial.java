package util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GestorHistorial {
    
    private static final String NOMBRE_ARCHIVO = "historial_correos.txt";

    public static void guardarCorreo(String correo) {
        try {
            List<String> historial = cargarHistorial();
            Set<String> historialUnico = new HashSet<>(historial);

            if (!historialUnico.contains(correo)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
                    writer.write(correo);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
    
}
    
    public static List<String> cargarHistorial() {
        List<String> historial = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            return historial;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                historial.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historial;
    }
    
    
    
}
