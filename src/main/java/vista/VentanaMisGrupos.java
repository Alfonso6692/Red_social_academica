/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import modelo.Grupo;
import modelo.Usuario;
import servicios.ServicioGrupos;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.util.List;

public class VentanaMisGrupos extends javax.swing.JFrame {

    private final Usuario usuarioLogueado;
    private final ServicioGrupos servicioGrupos;
    private DefaultListModel<String> listModel;
    private List<Grupo> listaDeGruposCompleta;

    public VentanaMisGrupos(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.servicioGrupos = new ServicioGrupos();
        this.listModel = new DefaultListModel<>();

        initComponents();

        this.setTitle("Mis Grupos");
        this.setLocationRelativeTo(null); // Centra la ventana
        listaMisGrupos.setModel(listModel); // Conecta el modelo de datos con la JList

        cargarMisGrupos();

    }

    private void cargarMisGrupos() {
        try {
            // Obtenemos solo los grupos del usuario logueado
            this.listaDeGruposCompleta = servicioGrupos.obtenerGruposDelUsuario(this.usuarioLogueado);
            listModel.clear();

            if (listaDeGruposCompleta.isEmpty()) {
                listModel.addElement("Aún no te has unido a ningún grupo.");
            } else {
                for (Grupo grupo : listaDeGruposCompleta) {
                    listModel.addElement(grupo.getNombre());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tus grupos: " + e.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lista = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaMisGrupos = new javax.swing.JList<>();
        JButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lista.setText("MisGrupos");

        jScrollPane2.setViewportView(listaMisGrupos);

        JButton.setText("Cerrar");
        JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lista, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(JButton)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lista)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(JButton)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonActionPerformed
        this.dispose(); // Cierra únicamente esta ventana
    }//GEN-LAST:event_JButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lista;
    private javax.swing.JList<String> listaMisGrupos;
    // End of variables declaration//GEN-END:variables
}
