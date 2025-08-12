/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.ControladorPrincipal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;
import persistencia.ComentarioDAO;
import persistencia.PublicacionDAO;
import servicios.ServicioNotificaciones;
import servicios.ServicioPublicaciones;
import persistencia.PublicacionDAO;

/**
 *
 * @author ASUS-VASQUEZ
 */
public class VentanaDashboard extends javax.swing.JFrame {

    private modelo.Usuario usuarioActual;
    private modelo.Usuario usuarioLogueado;
    private PublicacionDAO publicacionDAO;
    private ComentarioDAO comentarioDAO;
    private JScrollPane scrollPanePublicaciones;

    private ControladorPrincipal controlador;

    //private final Usuario usuarioLogueado; 
    public VentanaDashboard(Usuario usuario) {
        //this.usuarioActual = usuario;
        this.usuarioLogueado = usuario;
        //this.usuarioActual=usuario;
        this.controlador = new ControladorPrincipal();
        this.controlador.setUsuarioActual(usuario);
        this.publicacionDAO = new PublicacionDAO();
        this.comentarioDAO = new ComentarioDAO();

        initComponents();

        personalizarComponentes();
        cargarNotificaciones();
//        cargarSugerencias();
        cargarPublicaciones();

        pack(); // 

    }

    public void setUsuarioActual(modelo.Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("DEBUG: Usuario actual establecido: " + usuario);
    }

    private void cargarNotificaciones() {
        // Asumiendo que tienes una clase ServicioNotificaciones
        servicios.ServicioNotificaciones servicio = new servicios.ServicioNotificaciones();
        try {
            java.util.List<modelo.Notificacion> notificaciones = servicio.obtenerNotificacionesNoLeidas(this.usuarioLogueado);

            if (!notificaciones.isEmpty()) {
                // Si hay notificaciones, muestra el número
                menuNotificaciones.setText("Notificaciones (" + notificaciones.size() + ")");
            } else {
                // Si no hay, muestra el texto normal
                menuNotificaciones.setText("Notificaciones");
            }
        } catch (Exception e) {
            // En caso de error, puedes indicarlo en el menú
            menuNotificaciones.setText("Notificaciones (Error)");
            e.printStackTrace();

        }
    }

    public void cargarPublicaciones() {
        // 1. Limpiamos el panel
        panelPublicaciones.removeAll();

        try {
            PublicacionDAO pubDAO = new PublicacionDAO();
//            ServicioPublicaciones servicio = new ServicioPublicaciones();
            java.util.List<modelo.Publicacion> publicaciones = pubDAO.obtenerTodasLasPublicaciones();

            //java.util.List<modelo.Publicacion> publicaciones = servicio.obtenerTodas();
            // --- PUNTO DE VERIFICACIÓN CLAVE ---
            // Imprimimos en la consola para saber cuántos elementos llegaron.
            System.out.println("Número de publicaciones recuperadas: " + publicaciones.size());

            if (publicaciones.isEmpty()) {
                JPanel panelVacio = new JPanel();
                panelVacio.setBackground(Color.WHITE);
                panelVacio.setLayout(new GridBagLayout());
                // Si no hay publicaciones, mostramos un mensaje amigable en el panel.
                panelPublicaciones.add(new javax.swing.JLabel("No hay publicaciones para mostrar."));
                lblNoPublicaciones.setFont(new Font("Arial", Font.PLAIN, 14));
                lblNoPublicaciones.setForeground(Color.GRAY);

                panelVacio.add(lblNoPublicaciones);
                panelVacio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                panelPublicaciones.add(panelVacio);

            } else {
                for (modelo.Publicacion pub : publicaciones) {
                    // Por cada publicación, creamos un pequeño panel para mostrarla
                    javax.swing.JPanel panelPost = new javax.swing.JPanel();
                    // MEJOR OPCIÓN: Usar GridBagLayout para control total
                    panelPost.setLayout(new GridBagLayout());
                    panelPost.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(230, 230, 230), 1), // Borde gris claro de 1px
                            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding de 10px
                    ));

                    // --- CÓDIGO A PRUEBA DE NULLPOINTEREXCEPTION ---
                    String nombreAutor = "Autor desconocido"; // Valor por defecto
                    if (pub.getUsuario() != null && pub.getUsuario().getNombre() != null) {
                        nombreAutor = pub.getUsuario().getNombre();
                    }

                    javax.swing.JLabel lblAutor = new javax.swing.JLabel("Publicado por: " + nombreAutor);

                    javax.swing.JTextArea txtContenido = new javax.swing.JTextArea(pub.getContenido());
                    txtContenido.setEditable(false);
                    txtContenido.setLineWrap(true);
                    txtContenido.setWrapStyleWord(true);
                    txtContenido.setOpaque(false); // Para que tome el color de fondo del panel

                    // Añadimos los componentes al panel del post
                    panelPost.add(lblAutor);
                    panelPost.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5))); // Pequeño espacio
                    panelPost.add(new javax.swing.JScrollPane(txtContenido));

                    // Añadimos el panel del post al panel principal de publicaciones
                    panelPublicaciones.add(panelPost);
                    panelPublicaciones.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10))); // Espacio entre posts
                }
            }
        } catch (Exception e) {
            // Si ocurre cualquier otro error, lo imprimimos claramente.
            System.err.println("Ocurrió un error inesperado al cargar las publicaciones:");
            e.printStackTrace();
        } finally {
            // --- ACTUALIZACIÓN FINAL DE LA UI ---
            // Esto se ejecuta siempre, haya o no haya errores/publicaciones.
            // Es crucial para que los cambios (añadir o quitar todo) se reflejen.
            panelPublicaciones.revalidate();
            panelPublicaciones.repaint();
        }
    }

    /**
     * Método público para refrescar las publicaciones desde otra ventana
     */
    public void refrescarDespuesDeEliminacion() {
        System.out.println("DEBUG: Refrescando dashboard después de eliminación");
        cargarPublicaciones();

        // Forzar actualización visual
        this.revalidate();
        this.repaint();
    }

    // Método auxiliar para mostrar la primera publicación disponible
    private void mostrarPrimeraPublicacion() {
        try {
            java.util.List<Publicacion> publicaciones = controlador.obtenerTodasLasPublicaciones();
            if (!publicaciones.isEmpty()) {
                Publicacion primera = publicaciones.get(0);
                controlador.mostrarVentanaPublicacion(
                        primera.getId(),
                        this.usuarioLogueado,
                        this
                );
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No hay publicaciones disponibles",
                        "Información",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error al cargar publicación: " + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
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

        jMenu6 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        popupMenu1 = new java.awt.PopupMenu();
        popupMenu2 = new java.awt.PopupMenu();
        popupMenu3 = new java.awt.PopupMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jColorChooser1 = new javax.swing.JColorChooser();
        jFileChooser1 = new javax.swing.JFileChooser();
        lblNombreCompleto = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnVerGrupos = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnVerPublicacion = new javax.swing.JButton();
        lblBienvenido = new javax.swing.JLabel();
        botonCrearPublicacion = new javax.swing.JButton();
        lblNoPublicaciones = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelPublicaciones = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        lblCarreraCiclo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        itemExplorarGrupos = new javax.swing.JMenuItem();
        itemCrearNuevoGrupo = new javax.swing.JMenuItem();
        menuRecursos = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        itemBuscarUsuarios = new javax.swing.JMenuItem();
        itemVerSolicitudes = new javax.swing.JMenuItem();
        itemMisConexiones = new javax.swing.JMenuItem();
        menuUsuario = new javax.swing.JMenu();
        itemCerrarSesion = new javax.swing.JMenuItem();
        itemVerPerfil = new javax.swing.JMenuItem();
        menuNotificaciones = new javax.swing.JMenu();

        jMenu6.setText("jMenu6");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        jMenu4.setText("jMenu4");

        jMenuItem4.setText("jMenuItem4");

        jMenuItem5.setText("jMenuItem5");

        jMenuItem6.setText("jMenuItem6");

        jMenu2.setText("jMenu2");

        popupMenu1.setLabel("popupMenu1");

        popupMenu2.setLabel("popupMenu2");

        popupMenu3.setLabel("popupMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNombreCompleto.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Tus Grupos");

        jLabel2.setText("Descripcion");

        btnVerGrupos.setText("Ver mis grupos");
        btnVerGrupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerGruposActionPerformed(evt);
            }
        });

        jLabel3.setText("Recursos educativos");

        jLabel4.setText("Descripcion");

        jButton2.setText("Explorar recursos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnVerPublicacion.setText("Ver Publicación ");
        btnVerPublicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerPublicacionActionPerformed(evt);
            }
        });

        lblBienvenido.setText("Bienvenido");

        botonCrearPublicacion.setText("Crear Publicacion");
        botonCrearPublicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrearPublicacionActionPerformed(evt);
            }
        });

        lblNoPublicaciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane2.setBackground(new java.awt.Color(204, 255, 204));
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setHorizontalScrollBar(null);

        panelPublicaciones.setLayout(new javax.swing.BoxLayout(panelPublicaciones, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelPublicaciones);

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBienvenido, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(lblNoPublicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnVerPublicacion)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(169, 169, 169)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVerGrupos, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCrearPublicacion))
                        .addContainerGap(123, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblBienvenido)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 38, Short.MAX_VALUE)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnVerGrupos))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerPublicacion)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(botonCrearPublicacion)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(lblNoPublicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );

        jMenu1.setText("Inicio");
        jMenuBar1.add(jMenu1);

        jMenu5.setText("Grupo");

        itemExplorarGrupos.setText("Explorar Todos los Grupo");
        itemExplorarGrupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExplorarGruposActionPerformed(evt);
            }
        });
        jMenu5.add(itemExplorarGrupos);

        itemCrearNuevoGrupo.setText("Crear Nuevo Grupo");
        itemCrearNuevoGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCrearNuevoGrupoActionPerformed(evt);
            }
        });
        jMenu5.add(itemCrearNuevoGrupo);

        jMenuBar1.add(jMenu5);

        menuRecursos.setText("Recursos");
        menuRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRecursosActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuRecursos);

        jMenu8.setText("Conexiones");

        itemBuscarUsuarios.setText("Buscar Usuarios");
        itemBuscarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarUsuariosActionPerformed(evt);
            }
        });
        jMenu8.add(itemBuscarUsuarios);

        itemVerSolicitudes.setText("Ver Solicitudes Pendientes");
        itemVerSolicitudes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerSolicitudesActionPerformed(evt);
            }
        });
        jMenu8.add(itemVerSolicitudes);

        itemMisConexiones.setText("Mis Conexiones");
        itemMisConexiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMisConexionesActionPerformed(evt);
            }
        });
        jMenu8.add(itemMisConexiones);

        jMenuBar1.add(jMenu8);

        menuUsuario.setText("Usuario");
        menuUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUsuarioActionPerformed(evt);
            }
        });

        itemCerrarSesion.setText("Cerrar Sesion");
        itemCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCerrarSesionActionPerformed(evt);
            }
        });
        menuUsuario.add(itemCerrarSesion);

        itemVerPerfil.setText("Ver mi Perfil");
        itemVerPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerPerfilActionPerformed(evt);
            }
        });
        menuUsuario.add(itemVerPerfil);

        jMenuBar1.add(menuUsuario);

        menuNotificaciones.setText("Notificaciones");
        menuNotificaciones.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                menuNotificacionesMenuSelected(evt);
            }
        });
        jMenuBar1.add(menuNotificaciones);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(64, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombreCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCarreraCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(210, 210, 210))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblCarreraCiclo, javax.swing.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNombreCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCerrarSesionActionPerformed
        // 1. Cierra la ventana actual del Dashboard
        this.dispose();

        // 2. Crea y muestra una nueva ventana de Login
        VentanaLogin login = new VentanaLogin();
        login.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_itemCerrarSesionActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        VentanaRecursos ventanaDeRecursos = new VentanaRecursos(this.usuarioLogueado);
        ventanaDeRecursos.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnVerGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerGruposActionPerformed
        // Este código abre la ventana que muestra los grupos del usuario.
        VentanaMisGrupos misGrupos = new VentanaMisGrupos(this.usuarioLogueado);
        misGrupos.setVisible(true);
    }//GEN-LAST:event_btnVerGruposActionPerformed

    private void itemBuscarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarUsuariosActionPerformed

        new VentanaBuscarUsuarios(this.usuarioLogueado).setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_itemBuscarUsuariosActionPerformed

    private void itemVerSolicitudesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemVerSolicitudesActionPerformed

        new VentanaSolicitudes(this.usuarioLogueado).setVisible(true);

    }//GEN-LAST:event_itemVerSolicitudesActionPerformed

    private void itemMisConexionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMisConexionesActionPerformed
        // TODO add your handling code here:
        new VentanaConexiones(this.usuarioLogueado).setVisible(true);

    }//GEN-LAST:event_itemMisConexionesActionPerformed

    private void menuRecursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRecursosActionPerformed
        new VentanaRecursos(this.usuarioLogueado).setVisible(true);
    }//GEN-LAST:event_menuRecursosActionPerformed

    private void menuNotificacionesMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_menuNotificacionesMenuSelected
        ServicioNotificaciones servicio = new ServicioNotificaciones();
        try {
            List<modelo.Notificacion> notificaciones = servicio.obtenerNotificacionesNoLeidas(this.usuarioLogueado);

            if (notificaciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tienes notificaciones nuevas.");
            } else {
                StringBuilder mensajeCompleto = new StringBuilder("Tus notificaciones:\n\n");
                for (modelo.Notificacion n : notificaciones) {
                    mensajeCompleto.append("• ").append(n.getMensaje()).append("\n");
                }
                JOptionPane.showMessageDialog(this, mensajeCompleto.toString());

                servicio.marcarComoLeidas(notificaciones);
                // 2. Vuelve a cargar las notificaciones para actualizar el contador del menú
                cargarNotificaciones();
                // --- FIN DE LA LÓGICA AÑADIDA ---
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener notificaciones.");
        }
    }//GEN-LAST:event_menuNotificacionesMenuSelected

    private void itemExplorarGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExplorarGruposActionPerformed
        new VentanaGrupos(this.usuarioLogueado).setVisible(true);
    }//GEN-LAST:event_itemExplorarGruposActionPerformed

    private void itemCrearNuevoGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCrearNuevoGrupoActionPerformed
        String nombreGrupo = javax.swing.JOptionPane.showInputDialog(this, "Introduce el nombre del nuevo grupo:");
        if (nombreGrupo != null && !nombreGrupo.trim().isEmpty()) {
            try {
                servicios.ServicioGrupos servicio = new servicios.ServicioGrupos();
                servicio.crearGrupo(nombreGrupo, "Nueva descripción", this.usuarioLogueado);
                javax.swing.JOptionPane.showMessageDialog(this, "Grupo '" + nombreGrupo + "' creado con éxito.");
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al crear el grupo: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_itemCrearNuevoGrupoActionPerformed

    private void itemVerPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemVerPerfilActionPerformed
        // Ya no necesitas verificar usuarioActual, usa usuarioLogueado
        if (this.usuarioLogueado != null) {
            // Si necesitas mostrar una publicación de ejemplo, hazlo así:
            mostrarPrimeraPublicacion();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: No hay usuario logueado.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemVerPerfilActionPerformed

    private void menuUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUsuarioActionPerformed
        // Ya no necesitas verificar usuarioActual, usa usuarioLogueado
        if (this.usuarioLogueado != null) {
            // Si necesitas mostrar una publicación de ejemplo, hazlo así:
            mostrarPrimeraPublicacion();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: No hay usuario logueado.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_menuUsuarioActionPerformed


    private void btnVerPublicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerPublicacionActionPerformed
        // Si este botón es para ver una publicación de ejemplo
        if (this.usuarioLogueado != null) {
            mostrarPrimeraPublicacion();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: No hay usuario logueado.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnVerPublicacionActionPerformed

    private void botonCrearPublicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrearPublicacionActionPerformed
        String contenido = JOptionPane.showInputDialog(this, "Escribe tu nueva publicación:");
        if (contenido != null && !contenido.trim().isEmpty()) {
            try {
                servicios.ServicioPublicaciones servicio = new servicios.ServicioPublicaciones();
                servicio.crearPublicacion(this.usuarioLogueado, contenido);

                // Actualiza el panel de publicaciones para ver el cambio
                cargarPublicaciones();
                javax.swing.JOptionPane.showMessageDialog(this, "Publicación creada exitosamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear la publicación.");
            }
        }
    }//GEN-LAST:event_botonCrearPublicacionActionPerformed

    /**
     * @param args the command line arguments
     */
    private void personalizarComponentes() {
        this.setTitle("Dashboard - " + this.usuarioLogueado.getNombre() + " " + this.usuarioLogueado.getsegundoApellido());
        this.setLocationRelativeTo(null);

        if (this.usuarioLogueado != null) {
            String nombre = this.usuarioLogueado.getNombre();
            String apellido1 = this.usuarioLogueado.getApellido();
            String apellido2 = this.usuarioLogueado.getsegundoApellido();

            String nombreCompleto = nombre + " " + apellido1;

            // Añadir el segundo apellido solo si existe (no es nulo o vacío)
            if (apellido2 != null && !apellido2.isEmpty()) {
                nombreCompleto += " " + apellido2;
            }

            lblNombreCompleto.setText("BIENVENIDO, " + nombreCompleto.toUpperCase());

            String infoCarrera = "Carrera: " + this.usuarioLogueado.getCarrera() + "  |  Ciclo: " + this.usuarioLogueado.getCiclo();
            lblCarreraCiclo.setText(infoCarrera);

            menuUsuario.setText(this.usuarioLogueado.getNombre());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCrearPublicacion;
    private javax.swing.JButton btnVerGrupos;
    private javax.swing.JButton btnVerPublicacion;
    private javax.swing.JMenuItem itemBuscarUsuarios;
    private javax.swing.JMenuItem itemCerrarSesion;
    private javax.swing.JMenuItem itemCrearNuevoGrupo;
    private javax.swing.JMenuItem itemExplorarGrupos;
    private javax.swing.JMenuItem itemMisConexiones;
    private javax.swing.JMenuItem itemVerPerfil;
    private javax.swing.JMenuItem itemVerSolicitudes;
    private javax.swing.JButton jButton2;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBienvenido;
    private javax.swing.JLabel lblCarreraCiclo;
    private javax.swing.JLabel lblNoPublicaciones;
    private javax.swing.JLabel lblNombreCompleto;
    private javax.swing.JMenu menuNotificaciones;
    private javax.swing.JMenu menuRecursos;
    private javax.swing.JMenu menuUsuario;
    private javax.swing.JPanel panelPublicaciones;
    private java.awt.PopupMenu popupMenu1;
    private java.awt.PopupMenu popupMenu2;
    private java.awt.PopupMenu popupMenu3;
    // End of variables declaration//GEN-END:variables
}
