/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import modelo.Mensajes;
import modelo.Usuario;
import servicios.ServicioMensajeria;
import javax.swing.JOptionPane;

public class VentanaChat extends javax.swing.JFrame {

    private final Usuario usuarioLogueado;
    private final Usuario amigo;
    private final ServicioMensajeria servicioMensajeria;

    public VentanaChat(Usuario usuarioLogueado, Usuario amigo) {

        this.usuarioLogueado = usuarioLogueado;
        this.amigo = amigo;
        this.servicioMensajeria = new ServicioMensajeria();
        initComponents();

        this.setTitle("Chat con " + amigo.getNombre());
        this.setLocationRelativeTo(null);
        // Hacemos que el área de chat no se pueda editar
        areaChat.setEditable(false);

        // Cargamos el historial de mensajes al abrir la ventana
        cargarHistorial();

    }

     private void cargarHistorial() {
        try {
            // Limpiamos el área de chat
            areaChat.setText("");
            
            java.util.List<Mensajes> conversacion = servicioMensajeria.obtenerConversacion(usuarioLogueado, amigo);
            
            for (Mensajes msg : conversacion) {
                // Determinamos quién envió el mensaje para mostrarlo correctamente
                if (msg.getIdEmisor().equals(usuarioLogueado.getId())) {
                    areaChat.append("Tú: " + msg.getContenido() + "\n");
                } else {
                    areaChat.append(amigo.getNombre() + ": " + msg.getContenido() + "\n");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el historial: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaChat = new javax.swing.JTextArea();
        campoMensaje = new javax.swing.JTextField();
        botonEnviar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        areaChat.setColumns(20);
        areaChat.setRows(5);
        jScrollPane1.setViewportView(areaChat);

        botonEnviar.setText("Enviar");
        botonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEnviarActionPerformed(evt);
            }
        });

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(botonEnviar)
                        .addGap(28, 28, 28)
                        .addComponent(campoMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEnviar))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEnviarActionPerformed
         String contenido = campoMensaje.getText().trim();
        
        if (!contenido.isEmpty()) {
            try {
                // Llamamos al servicio para enviar el mensaje
                servicioMensajeria.enviarMensaje(this.usuarioLogueado, this.amigo, contenido);
                
                // Limpiamos el campo de texto
                campoMensaje.setText("");
                
                // Recargamos el historial para ver el nuevo mensaje al instante
                cargarHistorial();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al enviar el mensaje: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_botonEnviarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaChat;
    private javax.swing.JButton botonEnviar;
    private javax.swing.JTextField campoMensaje;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
