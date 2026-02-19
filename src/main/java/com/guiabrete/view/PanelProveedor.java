package com.guiabrete.view;

import com.guiabrete.model.Servicio; // Importante para manejar los datos [cite: 234]
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PanelProveedor extends JPanel {
    // Componentes de la UI
    private JButton btnAdd, btnEdit, btnDelete, btnProfile;
    private JPanel contenedorServicios;

    // Atributo de control (Vital para el flujo de selección) [cite: 18]
    private Servicio servicioSeleccionado;
    private MainVista ventana;

    public PanelProveedor(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(EstiloUI.MANZANA_50);

        // 1. SIDEBAR IZQUIERDA [cite: 12]
        add(crearSidebar(), BorderLayout.WEST);

        // 2. CONTENIDO PRINCIPAL [cite: 14]
        add(crearAreaPrincipal(), BorderLayout.CENTER);
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(EstiloUI.MANZANA_950);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel lblLogo = new JLabel("Guía-Brete", SwingConstants.CENTER);
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(EstiloUI.FONT_TITULO);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel lblAcciones = new JLabel("ACCIONES");
        lblAcciones.setForeground(EstiloUI.MANZANA_500);
        lblAcciones.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sidebar.add(lblAcciones);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botones de acción [cite: 98, 99, 100]
        btnAdd = EstiloUI.crearBoton("Publicar Servicio");
        btnEdit = EstiloUI.crearBoton("Modificar Seleccionado");
        btnDelete = EstiloUI.crearBoton("Eliminar Servicio");

        // Deshabilitar edición/borrado hasta que haya selección [cite: 18]
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

        Dimension btnSize = new Dimension(190, 40);
        JButton[] botones = {btnAdd, btnEdit, btnDelete};
        for (JButton b : botones) {
            b.setMaximumSize(btnSize);
            sidebar.add(b);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return sidebar;
    }

    private JPanel crearAreaPrincipal() {
        JPanel area = new JPanel(new BorderLayout(20, 20));
        area.setOpaque(false);
        area.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitulo = new JLabel("Tus Servicios Activos");
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);

        btnProfile = EstiloUI.crearBoton("Mi Perfil");
        btnProfile.setBackground(EstiloUI.MANZANA_900);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(btnProfile, BorderLayout.EAST);

        area.add(header, BorderLayout.NORTH);

        contenedorServicios = new JPanel();
        contenedorServicios.setLayout(new BoxLayout(contenedorServicios, BoxLayout.Y_AXIS));
        contenedorServicios.setBackground(EstiloUI.MANZANA_50);

        JScrollPane scroll = new JScrollPane(contenedorServicios);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        area.add(scroll, BorderLayout.CENTER);

        JLabel lblHint = new JLabel("Seleccione un servicio para gestionar sus opciones.");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblHint.setForeground(Color.GRAY);
        area.add(lblHint, BorderLayout.SOUTH);

        return area;
    }

    // --- MÉTODOS DE INTEGRACIÓN (OBLIGATORIOS PARA MVC) --- [cite: 79, 238]

    public void actualizarListaServicios(List<Servicio> servicios) {
        contenedorServicios.removeAll();
        if (servicios.isEmpty()) {
            contenedorServicios.add(new JLabel("No hay servicios publicados en Limón aún."));
        } else {
            for (Servicio s : servicios) {
                // Aquí se insertará la tarjeta que diseñaremos a continuación
                // TarjetaServicio tarjeta = new TarjetaServicio(s, this);
                // contenedorServicios.add(tarjeta);
                contenedorServicios.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        revalidate();
        repaint();
    }

    // Getters para que el Controlador (Angely) asigne los eventos [cite: 236]
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnProfile() { return btnProfile; }

    public Servicio getServicioSeleccionado() { return servicioSeleccionado; }

    public void setServicioSeleccionado(Servicio s) {
        this.servicioSeleccionado = s;
        // Habilitar botones si hay algo seleccionado [cite: 18]
        boolean tieneSeleccion = (s != null);
        btnEdit.setEnabled(tieneSeleccion);
        btnDelete.setEnabled(tieneSeleccion);
    }
}

