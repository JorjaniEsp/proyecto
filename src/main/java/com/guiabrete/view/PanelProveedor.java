package com.guiabrete.view;

import com.guiabrete.model.Servicio;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel de administración para el perfil de Proveedor.
 * <p>Esta vista funciona como el Dashboard central del profesional, permitiéndole
 * gestionar sus servicios (publicar, editar, eliminar) y visualizar su perfil.</p>
 * <p>Utiliza una estructura de Sidebar para navegación y un área de scroll dinámica
 * para listar los servicios activos mediante un sistema de selección visual.</p>
 * * @author Grupo 04
 * @version 1.2
 */
public class PanelProveedor extends JPanel {

    // Componentes de la interfaz
    private JButton btnAdd, btnEdit, btnDelete, btnProfile, btnVolver;
    private JPanel contenedorServicios;

    /** Referencia al servicio seleccionado por el usuario para acciones CRUD. */
    private Servicio servicioSeleccionado;

    /** Referencia a la ventana principal para gestión de navegación. */
    private MainVista ventana;

    /**
     * Constructor que inicializa el Dashboard del Proveedor.
     * @param ventana Instancia de {@link MainVista} necesaria para la comunicación entre paneles.
     */
    public PanelProveedor(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(crearSidebar(), BorderLayout.WEST);
        add(crearAreaPrincipal(), BorderLayout.CENTER);
    }

    /**
     * Crea la barra lateral de acciones.
     * <p><b>Nota técnica:</b> La inicialización de los botones sigue un orden estricto
     * para evitar NullPointerException antes de la asignación de Listeners.</p>
     * @return El panel de la barra lateral configurado.
     */
    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(EstiloUI.MANZANA_50);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, EstiloUI.MANZANA_500));

        // --- SECCIÓN DE LOGO ---
        JLabel lblLogo = new JLabel("<html><h2 style='color:#1c4b23'>Guía-Brete</h2></html>", SwingConstants.CENTER);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel pnlLogo = new JPanel();
        pnlLogo.setOpaque(false);
        pnlLogo.setBorder(new EmptyBorder(20, 0, 40, 0));
        pnlLogo.add(lblLogo);
        sidebar.add(pnlLogo);

        // --- SECCIÓN DE ACCIONES ---
        JLabel lblAcciones = new JLabel("ACCIONES");
        lblAcciones.setForeground(Color.GRAY);
        lblAcciones.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAcciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblAcciones);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        // Inicialización de botones de gestión
        btnAdd = EstiloUI.crearBoton("PUBLICAR SERVICIO");
        btnEdit = EstiloUI.crearBoton("MODIFICAR SERVICIO");
        btnDelete = EstiloUI.crearBoton("ELIMINAR SERVICIO");

        btnDelete.setBackground(new Color(220, 53, 69));
        btnEdit.setEnabled(false); // Deshabilitado hasta selección
        btnDelete.setEnabled(false);

        // Configuración estética de botones
        Dimension btnSize = new Dimension(200, 40);
        JButton[] botones = {btnAdd, btnEdit, btnDelete};
        for (JButton b : botones) {
            b.setMaximumSize(btnSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(b);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // --- SECCIÓN DE CIERRE DE SESIÓN ---
        sidebar.add(Box.createVerticalGlue());

        // IMPORTANTE: Inicialización antes del addActionListener
        btnVolver = new JButton("CERRAR SESIÓN");
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cableado de eventos
        btnVolver.addActionListener(e -> {
            if (ventana.getControlador() != null) ventana.getControlador().cerrarSesion();
            else ventana.cambiarVista("inicio");
        });

        btnAdd.addActionListener(e -> ventana.cambiarVista("anadirServicio"));

        btnEdit.addActionListener(e -> {
            if (servicioSeleccionado != null) {
                // Inyectar datos en el panel de modificación antes del cambio de vista
                for (Component comp : getParent().getComponents()) {
                    if (comp instanceof PanelModificarServicio) {
                        ((PanelModificarServicio) comp).cargarDatosServicio(servicioSeleccionado);
                        ventana.cambiarVista("modificarServicio");
                        break;
                    }
                }
            }
        });

        btnDelete.addActionListener(e -> {
            if (servicioSeleccionado != null && ventana.getControlador() != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que desea eliminar?\n" + servicioSeleccionado.getNombreServ(),
                        "Confirmar Borrado", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION){
                    ventana.getControlador().eliminarServicio(servicioSeleccionado);
                    setServicioSeleccionado(null);
                }
            }
        });

        JPanel pnlVolver = new JPanel();
        pnlVolver.setOpaque(false);
        pnlVolver.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlVolver.add(btnVolver);
        sidebar.add(pnlVolver);

        return sidebar;
    }

    /**
     * Crea el área central de visualización de servicios.
     * @return El panel principal configurado con JScrollPane.
     */
    private JPanel crearAreaPrincipal() {
        JPanel area = new JPanel(new BorderLayout(20, 20));
        area.setBackground(Color.WHITE);
        area.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Cabecera del área de contenido
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitulo = new JLabel("Tus Servicios Activos");
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);

        btnProfile = EstiloUI.crearBoton("MI PERFIL");
        btnProfile.addActionListener(e -> {
            if (ventana.getControlador() != null) ventana.getControlador().verPerfil();
        });

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(btnProfile, BorderLayout.EAST);
        area.add(header, BorderLayout.NORTH);

        // Contenedor dinámico de servicios
        contenedorServicios = new JPanel();
        contenedorServicios.setLayout(new BoxLayout(contenedorServicios, BoxLayout.Y_AXIS));
        contenedorServicios.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(contenedorServicios);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        area.add(scroll, BorderLayout.CENTER);

        // Ayuda visual al pie
        JLabel lblHint = new JLabel("Seleccione un servicio para habilitar 'Modificar' o 'Eliminar'.");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblHint.setForeground(Color.GRAY);
        area.add(lblHint, BorderLayout.SOUTH);

        return area;
    }

    /**
     * Refresca la lista de servicios del proveedor en pantalla.
     * @param servicios Lista de objetos {@link Servicio} a renderizar.
     */
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

    /**
     * Genera un panel visual (Tarjeta) para un servicio específico.
     * <p>Implementa selección mediante MouseListener para actualizar el estado del Dashboard.</p>
     * @param s El servicio a encapsular.
     * @return Un JPanel estilizado con la información del servicio.
     */
    private JPanel crearTarjetaServicio(Servicio s) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBackground(EstiloUI.MANZANA_50);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblNombre = new JLabel(s.getNombreServ());
        lblNombre.setFont(EstiloUI.FONT_SUBTITULO);
        lblNombre.setForeground(EstiloUI.MANZANA_900);

        JTextArea txtDesc = new JTextArea(s.getDescripcionServ());
        txtDesc.setEditable(false);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setBackground(EstiloUI.MANZANA_50);
        txtDesc.setFont(EstiloUI.FONT_TEXTO);

        tarjeta.add(lblNombre, BorderLayout.NORTH);
        tarjeta.add(txtDesc, BorderLayout.CENTER);

        // Lógica de selección visual
        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setServicioSeleccionado(s);
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

    /**
     * Limpia los bordes de selección de todas las tarjetas visibles.
     */
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

    /**
     * Actualiza el servicio activo y habilita/deshabilita los botones de acción.
     * @param s El servicio seleccionado o null para limpiar la selección.
     */
    public void setServicioSeleccionado(Servicio s) {
        this.servicioSeleccionado = s;
        boolean tieneSeleccion = (s != null);
        btnEdit.setEnabled(tieneSeleccion);
        btnDelete.setEnabled(tieneSeleccion);
    }
}