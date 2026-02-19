package com.guiabrete.view;

import com.guiabrete.model.Servicio;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PanelProveedor extends JPanel {
    // Componentes de la UI
    private JButton btnAdd, btnEdit, btnDelete, btnProfile, btnVolver;
    private JPanel contenedorServicios;

    // Atributo de control
    private Servicio servicioSeleccionado;
    private MainVista ventana;

    public PanelProveedor(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Fondo principal blanco limpio

        add(crearSidebar(), BorderLayout.WEST);
        add(crearAreaPrincipal(), BorderLayout.CENTER);
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        // Mismo estilo suave del panel visitante
        sidebar.setBackground(EstiloUI.MANZANA_50);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, EstiloUI.MANZANA_500));

        // Logo
        JLabel lblLogo = new JLabel("<html><h2 style='color:#1c4b23'>Guía-Brete</h2></html>", SwingConstants.CENTER);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pnlLogo = new JPanel();
        pnlLogo.setOpaque(false);
        pnlLogo.setBorder(new EmptyBorder(20, 0, 40, 0));
        pnlLogo.add(lblLogo);
        sidebar.add(pnlLogo);

        JLabel lblAcciones = new JLabel("ACCIONES");
        lblAcciones.setForeground(Color.GRAY);
        lblAcciones.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAcciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblAcciones);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botones de acción
        btnAdd = EstiloUI.crearBoton("PUBLICAR SERVICIO");
        btnEdit = EstiloUI.crearBoton("MODIFICAR SERVICIO");
        btnDelete = EstiloUI.crearBoton("ELIMINAR SERVICIO");

        // Color distintivo para eliminar
        btnDelete.setBackground(new Color(220, 53, 69));

        // Deshabilitar edición/borrado hasta que seleccione algo de la lista
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

        Dimension btnSize = new Dimension(200, 40);
        JButton[] botones = {btnAdd, btnEdit, btnDelete};
        for (JButton b : botones) {
            b.setMaximumSize(btnSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(b);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // --- BOTÓN CERRAR SESIÓN ---
        sidebar.add(Box.createVerticalGlue()); // Empuja el botón hacia el fondo

        btnVolver = new JButton("CERRAR SESIÓN");
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pnlVolver = new JPanel();
        pnlVolver.setOpaque(false);
        pnlVolver.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlVolver.add(btnVolver);

        sidebar.add(pnlVolver);

        // ==========================================
        // CABLEADO: Conectando botones a la app
        // ==========================================

        // 1. Añadir
        btnAdd.addActionListener(e -> ventana.cambiarVista("anadirServicio"));

        // 2. Modificar
        btnEdit.addActionListener(e -> {
            if (servicioSeleccionado != null) {
                // Buscamos el panel de modificar para enviarle los datos antes de cambiar de pantalla
                for (Component comp : getParent().getComponents()) {
                    if (comp instanceof PanelModificarServicio) {
                        ((PanelModificarServicio) comp).cargarDatosServicio(servicioSeleccionado);
                        ventana.cambiarVista("modificarServicio");
                        break;
                    }
                }
            }
        });

        // 3. Eliminar
        btnDelete.addActionListener(e -> {
            if (servicioSeleccionado != null && ventana.getControlador() != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar el servicio?\n" + servicioSeleccionado.getNombreServ(), "Confirmar", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION){
                    ventana.getControlador().eliminarServicio(servicioSeleccionado);
                    servicioSeleccionado = null; // Reiniciamos selección
                    btnEdit.setEnabled(false);
                    btnDelete.setEnabled(false);
                }
            }
        });

        // 4. Salir
        btnVolver.addActionListener(e -> {
            if (ventana.getControlador() != null) ventana.getControlador().cerrarSesion();
            else ventana.cambiarVista("inicio");
        });

        return sidebar;
    }

    private JPanel crearAreaPrincipal() {
        JPanel area = new JPanel(new BorderLayout(20, 20));
        area.setBackground(Color.WHITE);
        area.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitulo = new JLabel("Tus Servicios Activos");
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);

        btnProfile = EstiloUI.crearBoton("MI PERFIL");
        btnProfile.setBackground(EstiloUI.MANZANA_900);

        // Evento de Perfil
        btnProfile.addActionListener(e -> {
            if (ventana.getControlador() != null) {
                ventana.getControlador().verPerfil();
            }
        });

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(btnProfile, BorderLayout.EAST);

        area.add(header, BorderLayout.NORTH);

        contenedorServicios = new JPanel();
        contenedorServicios.setLayout(new BoxLayout(contenedorServicios, BoxLayout.Y_AXIS));
        contenedorServicios.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(contenedorServicios);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        area.add(scroll, BorderLayout.CENTER);

        JLabel lblHint = new JLabel("Seleccione un servicio en la lista para habilitar 'Modificar' o 'Eliminar'.");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblHint.setForeground(Color.GRAY);
        area.add(lblHint, BorderLayout.SOUTH);

        return area;
    }

    // --- CARGAR LOS DATOS EN PANTALLA ---
    public void actualizarListaServicios(List<Servicio> servicios) {
        // En tu código original se llamaba "actualizarListaServicios",
        // pero recuerda que en MainVista lo llamamos usando "mostrarPanelProveedor".
        // Para asegurar que funciona, creamos el alias aquí.
        cargarMisServicios(servicios);
    }

    public void cargarMisServicios(List<Servicio> servicios) {
        contenedorServicios.removeAll();
        if (servicios == null || servicios.isEmpty()) {
            JLabel lblVacio = new JLabel("No tienes servicios publicados aún.");
            lblVacio.setFont(EstiloUI.FONT_SUBTITULO);
            contenedorServicios.add(lblVacio);
        } else {
            for (Servicio s : servicios) {
                contenedorServicios.add(crearTarjetaServicio(s));
                contenedorServicios.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        revalidate();
        repaint();
    }

    // Generador visual de la tarjeta de servicio
    private JPanel crearTarjetaServicio(Servicio s) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBackground(EstiloUI.MANZANA_50);

        // Estilo inicial
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblNombre = new JLabel(s.getNombreServ());
        lblNombre.setFont(EstiloUI.FONT_SUBTITULO);
        lblNombre.setForeground(EstiloUI.MANZANA_900);

        JLabel lblCat = new JLabel("Categoría: " + s.getCategoria().toString());
        lblCat.setForeground(Color.GRAY);

        JTextArea txtDesc = new JTextArea(s.getDescripcionServ());
        txtDesc.setEditable(false);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setBackground(EstiloUI.MANZANA_50);
        txtDesc.setFont(EstiloUI.FONT_TEXTO);

        tarjeta.add(lblNombre, BorderLayout.NORTH);
        tarjeta.add(txtDesc, BorderLayout.CENTER);
        tarjeta.add(lblCat, BorderLayout.SOUTH);

        // EVENTO PARA SELECCIONAR LA TARJETA
        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setServicioSeleccionado(s);

                // Feedback visual: Quitar color a todas, poner borde grueso a la seleccionada
                resetearColorTarjetas();
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(EstiloUI.MANZANA_900, 3, true),
                        new EmptyBorder(13, 13, 13, 13)
                ));
            }
        });

        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        return tarjeta;
    }

    // Auxiliar visual
    private void resetearColorTarjetas() {
        for (Component c : contenedorServicios.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1, true),
                        new EmptyBorder(15, 15, 15, 15)
                ));
            }
        }
    }

    public void setServicioSeleccionado(Servicio s) {
        this.servicioSeleccionado = s;
        boolean tieneSeleccion = (s != null);
        btnEdit.setEnabled(tieneSeleccion);
        btnDelete.setEnabled(tieneSeleccion);
    }
}