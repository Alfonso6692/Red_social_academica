/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;
import persistencia.PublicacionDAO;
import persistencia.UsuarioDAO;
import persistencia.ComentarioDAO;

/**
 *
 * @author ASUS-VASQUEZ
 */
public final class PublicacionVista extends javax.swing.JFrame {

    private modelo.Publicacion publicacion;
    private modelo.Publicacion publicacionMostrada;
    private modelo.Usuario usuarioLogueado;
    private vista.VentanaDashboard dashboardPadre;

    ;
    private Object vista;

    /**
     * Creates new form PublicacionVista
     *
     * @param publicacion
     * @param usuario
     * @param dashboard
     * 
     */
    public PublicacionVista(modelo.Publicacion publicacion, modelo.Usuario usuario, vista.VentanaDashboard dashboard) {
        System.out.println("DEBUG: Constructor PublicacionVista con dashboard llamado");
        System.out.println("DEBUG: Dashboard recibido = " + dashboard);
        this.usuarioLogueado = usuario;
        this.publicacion = publicacion;
        this.publicacionMostrada = publicacion;
        this.dashboardPadre = null;
        this.dashboardPadre = dashboard;

        System.out.println("DEBUG: dashboardPadre asignado = " + this.dashboardPadre);

        initComponents();
        actualizarVista();
        configurarPermisos();
        boolean esMiPublicacion = publicacion.getUsuario().getId().equals(usuarioLogueado.getId());
        //boolean esMiPublicacion = publicacion.getUsuario().getId().equals(usuarioLogueado.getId());
        botonModificar.setVisible(esMiPublicacion);

        botonEliminar.setVisible(esMiPublicacion);
        // Aquí puedes añadir el código para mostrar los datos en la ventana
        this.setTitle("Publicación de " + publicacion.getUsuario().getNombre());
        // ej: textAreaContenido.setText(publicacion.getContenido());
    }

    private PublicacionVista(Publicacion publicacionAMostrar, Usuario usuarioLogueado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    public PublicacionVista (modelo.Publicacion publicacion, modelo.Usuario usuario, vista.VentanaDashboard dashboard) {
//        this.usuarioLogueado = usuario;
//        this.publicacion = publicacion;
//        this.publicacionMostrada = publicacion;
//        this.dashboardPadre = dashboard;
//
//        initComponents();   
//        actualizarVista();
//        configurarPermisos();
//    }
    private void configurarPermisos() {
        if (publicacion != null && usuarioLogueado != null) {
            boolean esMiPublicacion = publicacion.getUsuario().getId().equals(usuarioLogueado.getId());

            // Solo mostrar botones si existen
            if (botonModificar != null) {
                botonModificar.setVisible(esMiPublicacion);
            }
            if (botonEliminar != null) {
                botonEliminar.setVisible(esMiPublicacion);
            }

            // Configurar título
            this.setTitle("Publicación de " + publicacion.getUsuario().getNombre());
        }
    }

    private void actualizarVista() {
        // Esta parte está bien
        lblNombreAutor.setText("Publicación de: " + publicacion.getUsuario().getNombre());
        txtAreaContenido.setText(publicacion.getContenido());

        StringBuilder textoComentarios = new StringBuilder();
        if (publicacion.getComentarios() != null) {

            // --- CORRECCIÓN AQUÍ ---
            // Se recorre cada comentario individual usando la variable 'c'
            for (Comentario c : publicacion.getComentarios()) {
                // Asumiendo que la clase Comentario tiene getAutor() y getTexto()
                textoComentarios.append(c.getAutor().getNombre()) // Usa 'c' minúscula
                        .append(": ")
                        .append(c.getTexto()) // Usa 'c' minúscula
                        .append("\n");
            }
        }
        txtAreaComentarios.setText(textoComentarios.toString());

    }

    public void mostrarVentanaPublicacion(String emailUsuario, String idPublicacion, vista.VentanaDashboard dashboard) {
        System.out.println("DEBUG: mostrarVentanaPublicacion CON dashboard llamado");
        System.out.println("DEBUG: Dashboard recibido = " + dashboard);
        
        
        // 1. Crear instancias de los DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PublicacionDAO publicacionDAO = new PublicacionDAO();

        try {
            // 2. Usar el email que se pasa como parámetro
            Usuario usuarioLogueado = usuarioDAO.buscarPorCorreo(emailUsuario);

            // CORRECCIÓN: Se usa obtenerPublicacionSimple
            Publicacion publicacionAMostrar = publicacionDAO.obtenerPorId(idPublicacion);

            // 3. Verificación para evitar errores si algo no se encuentra
            if (usuarioLogueado == null) {
                 System.err.println("Error: Usuario no encontrado con email: " + emailUsuario);
                return;
            }
            
            if (publicacionAMostrar == null) {
                System.out.println("Error: Publicacion no encontrada con ID: " +idPublicacion);
                return;
            }

            // 4. Crear y mostrar la Vista
            
            vista.PublicacionVista vistaPublicacion = new vista.PublicacionVista(publicacionAMostrar, usuarioLogueado, dashboard);
            vistaPublicacion.setVisible(true);
            
            System.out.println("DEBUG: PublicacionVista creada CON dashboard");

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.err.println("Error de base de datos: " + e.getMessage());
        }
    }
    
    public void mostrarVentanaPublicacionEjemplo(String emailUsuario) {
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    PublicacionDAO publicacionDAO = new PublicacionDAO();
    
    try {
        Usuario usuarioLogueado = usuarioDAO.buscarPorCorreo(emailUsuario);
        Publicacion publicacionAMostrar = publicacionDAO.obtenerPublicacionSimple();
        
        if (usuarioLogueado == null || publicacionAMostrar == null) {
            System.err.println("Error: No se pudieron cargar los datos de ejemplo desde el DAO.");
            return;
        }
        
        // Usar constructor sin dashboard para el ejemplo
        vista.PublicacionVista vistaPublicacion = new vista.PublicacionVista(publicacionAMostrar, usuarioLogueado);
        vistaPublicacion.setVisible(true);
        
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
}
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombreAutor = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaContenido = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaComentarios = new javax.swing.JTextArea();
        txtNuevoComentario = new javax.swing.JTextField();
        btnComentar = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtAreaContenido.setEditable(false);
        txtAreaContenido.setColumns(20);
        txtAreaContenido.setRows(5);
        jScrollPane1.setViewportView(txtAreaContenido);

        txtAreaComentarios.setEditable(false);
        txtAreaComentarios.setColumns(20);
        txtAreaComentarios.setRows(5);
        jScrollPane2.setViewportView(txtAreaComentarios);

        btnComentar.setText("Comentar");
        btnComentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComentarActionPerformed(evt);
            }
        });

        botonModificar.setText("Modificar");
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        botonEliminar.setText("Eliminar");
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblNombreAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNuevoComentario, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnComentar)
                        .addGap(68, 68, 68))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonModificar)
                            .addComponent(botonEliminar))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(42, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(lblNombreAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(botonModificar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonEliminar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnComentar)
                    .addComponent(txtNuevoComentario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComentarActionPerformed
        String texto = txtNuevoComentario.getText().trim();
        if (!texto.isEmpty()) {
            // A:
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor escribe un comentario.");
            return;
        }

        // Llama al método sobre el objeto, no sobre la clase
        // A:
        try {

            Comentario nuevoComentario = new Comentario();
            nuevoComentario.setAutor(this.usuarioLogueado);
            nuevoComentario.setTexto(texto);
            nuevoComentario.setIdPublicacion(this.publicacionMostrada.getId());
            persistencia.ComentarioDAO comentarioDAO = new persistencia.ComentarioDAO();
            comentarioDAO.guardar(nuevoComentario);

            // Actualizar la vista para mostrar el nuevo comentario
            actualizarVista();

            // Limpiar el campo de texto
            txtNuevoComentario.setText("");

            javax.swing.JOptionPane.showMessageDialog(this, "Comentario agregado exitosamente.");

        } catch (Exception ex) {
            System.err.println("Error general: " + ex.getMessage());
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
        }

        txtNuevoComentario.setText("");
        actualizarVista();

    }//GEN-LAST:event_btnComentarActionPerformed

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        // Obtiene el contenido actual para mostrarlo en el cuadro de diálogo
        String contenidoActual = this.publicacionMostrada.getContenido();

        // Pide al usuario el nuevo texto para la publicación
        String nuevoContenido = javax.swing.JOptionPane.showInputDialog(this, "Edita tu publicación:", contenidoActual);

        // Verifica que el usuario no haya cancelado o dejado el campo vacío
        if (nuevoContenido != null && !nuevoContenido.trim().isEmpty()) {
            try {
                this.publicacionMostrada.setContenido(nuevoContenido);
                // Llama al servicio para guardar los cambios en la base de datos
                servicios.ServicioPublicaciones servicio = new servicios.ServicioPublicaciones();
                servicio.actualizarPublicacion(this.publicacionMostrada);

                // Actualiza la vista para mostrar el cambio inmediatamente
                actualizarVista();

                javax.swing.JOptionPane.showMessageDialog(this, "Publicación actualizada con éxito.");

            } catch (java.sql.SQLException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al modificar la publicación: " + e.getMessage());
            }

// En el método botonEliminarActionPerformed (línea 266):
            try {
                // Llama al servicio para eliminar la publicación de la base de datos
                servicios.ServicioPublicaciones servicio = new servicios.ServicioPublicaciones();
                servicio.eliminarPublicacion(this.publicacionMostrada);

                javax.swing.JOptionPane.showMessageDialog(this, "Publicación eliminada con éxito.");

                // Cierra la ventana de la publicación después de eliminarla
                this.dispose();

            } catch (java.sql.SQLException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al eliminar la publicación: " + e.getMessage());
            }
        }


    }//GEN-LAST:event_botonModificarActionPerformed

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar esta publicación?",
                "Confirmar Eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            try {
                System.out.println("DEBUG: Eliminando publicación con ID: " + this.publicacionMostrada.getId());

                servicios.ServicioPublicaciones servicio = new servicios.ServicioPublicaciones();
                servicio.eliminarPublicacion(this.publicacionMostrada);

                javax.swing.JOptionPane.showMessageDialog(this,
                        "Publicación eliminada con éxito.",
                        "Eliminación Exitosa",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);

                // Refrescar dashboard si existe
                if (dashboardPadre != null) {
                    System.out.println("DEBUG: Llamando cargarPublicaciones() en dashboard");
                    dashboardPadre.cargarPublicaciones();
                    System.out.println("DEBUG: cargarPublicaciones() ejecutado");

                    //FORZAR LA ACTUALIZACION ADICIONAL
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        System.out.println("DEBUG: Forzando revalite() y repaint()");
                        dashboardPadre.revalidate();
                        dashboardPadre.repaint();
                    });

                } else {

                    System.out.println("DEBUG: ❌ ERROR - dashboardPadre es NULL!");
                    System.out.println("DEBUG: La publicación se eliminó de BD pero no se refrescará el dashboard");

//                    dashboardPadre.revalidate();
//                    dashboardPadre.repaint();
                }

                // Cerrar esta ventana
                this.dispose();
                System.out.println("DEBUG: === FIN ELIMINACIÓN ===");

                // Cerrar ventana
                this.dispose();

            } catch (java.sql.SQLException e) {
                System.err.println("DEBUG: Error SQL al eliminar: " + e.getMessage());
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Error al eliminar la publicación: " + e.getMessage(),
                        "Error de Eliminación",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                System.err.println("DEBUG: Error general: " + e.getMessage());
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Error inesperado: " + e.getMessage(),
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_botonEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton btnComentar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNombreAutor;
    private javax.swing.JTextArea txtAreaComentarios;
    private javax.swing.JTextArea txtAreaContenido;
    private javax.swing.JTextField txtNuevoComentario;
    // End of variables declaration//GEN-END:variables
}
