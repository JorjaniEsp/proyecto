package com.guiabrete.view;

import com.guiabrete.model.Servicio;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class PanelPrincipalVisitante extends JPanel {

    private MainVista ventana;
    private JPanel panelResultados; // Aquí pintaremos las tarjetas
    private JTextField txtBuscador;

    public PanelPrincipalVisitante(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // =================================================================================
        // 1. PANEL LATERAL IZQUIERDO (Filtros)
        // =================================================================================
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setPreferredSize(new Dimension(250, 0)); // Ancho fijo
        panelIzquierdo.setBackground(EstiloUI.MANZANA_50);
        panelIzquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, EstiloUI.MANZANA_500));
        panelIzquierdo.setLayout(new GridBagLayout());

        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.insets = new Insets(10, 15, 10, 15);
        gbcIzq.fill = GridBagConstraints.HORIZONTAL;
        gbcIzq.gridx = 0;

        // A. Logo (Pequeño)
        JLabel logoLabel = new JLabel("<html><h2 style='color:#1c4b23'>Guía-Brete</h2></html>", SwingConstants.CENTER);
        // (Opcional: Cargar imagen pequeña aquí si la tienes)

        // B. Botones de Filtros
        JButton btnZona = EstiloUI.crearBoton("BUSCAR POR ZONA");
        JButton btnCategoria = EstiloUI.crearBoton("BUSCAR POR CATEGORÍA");
        JButton btnLimpiar = EstiloUI.crearBoton("LIMPIAR FILTROS");

        // Estilo diferente para "Limpiar" (más suave)
        btnLimpiar.setBackground(EstiloUI.MANZANA_900);

        // Acciones de Filtros (Simuladas por ahora)
        btnZona.addActionListener(e -> {
            String zona = JOptionPane.showInputDialog(this, "Ingrese la zona a buscar:");
            if(zona != null && !zona.isEmpty() && ventana.getControlador() != null) {
                ventana.getControlador().buscarPorZona(zona);
            }
        });

        btnCategoria.addActionListener(e -> {
            // Idealmente mostrar un ComboBox con las categorías del Enum
            JOptionPane.showMessageDialog(this, "Funcionalidad de filtro por categoría pendiente.");
        });

        btnLimpiar.addActionListener(e -> {
            if(ventana.getControlador() != null) ventana.getControlador().mostrarCatalogoCompleto();
        });

        // Botón Salir / Volver
        JButton btnVolver = new JButton("VOLVER AL INICIO");
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> ventana.cambiarVista("inicio"));

        // Agregar al panel izquierdo
        gbcIzq.gridy = 0; panelIzquierdo.add(logoLabel, gbcIzq);
        gbcIzq.insets = new Insets(40, 15, 10, 15); // Espacio tras el logo
        gbcIzq.gridy = 1; panelIzquierdo.add(btnZona, gbcIzq);
        gbcIzq.insets = new Insets(10, 15, 10, 15);
        gbcIzq.gridy = 2; panelIzquierdo.add(btnCategoria, gbcIzq);
        gbcIzq.gridy = 3; panelIzquierdo.add(btnLimpiar, gbcIzq);

        gbcIzq.weighty = 1.0; // Empujar botón volver al fondo
        gbcIzq.anchor = GridBagConstraints.SOUTH;
        gbcIzq.gridy = 4; panelIzquierdo.add(btnVolver, gbcIzq);

        // =================================================================================
        // 2. PANEL DERECHO (Contenido Principal)
        // =================================================================================
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);

        // A. Barra Superior (Buscador + Perfil)
        JPanel barraSuperior = new JPanel(new BorderLayout(10, 10));
        barraSuperior.setBackground(Color.WHITE);
        barraSuperior.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtBuscador = EstiloUI.crearInput();
        txtBuscador.setText("Buscar servicio..."); // Placeholder simple

        // Botón Lupa (Búsqueda)
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBackground(EstiloUI.MANZANA_500);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> {
            // Lógica de búsqueda por texto pendiente en controlador
            JOptionPane.showMessageDialog(this, "Buscando: " + txtBuscador.getText());
        });

        // Icono Perfil (Simulado con botón redondo o texto)
        JButton btnPerfil = new JButton("👤");
        btnPerfil.setBackground(Color.WHITE);
        btnPerfil.setBorder(new LineBorder(EstiloUI.MANZANA_500, 1, true));
        btnPerfil.setPreferredSize(new Dimension(40, 40));
        btnPerfil.setToolTipText("Iniciar Sesión / Mi Perfil");
        btnPerfil.addActionListener(e -> ventana.cambiarVista("inicio")); // O ir a perfil si está logueado

        JPanel panelBuscador = new JPanel(new BorderLayout());
        panelBuscador.add(txtBuscador, BorderLayout.CENTER);
        panelBuscador.add(btnBuscar, BorderLayout.EAST);

        barraSuperior.add(panelBuscador, BorderLayout.CENTER);
        barraSuperior.add(btnPerfil, BorderLayout.EAST);

        // B. Área de Resultados (Scroll con Grid de Tarjetas)
        panelResultados = new JPanel(new GridLayout(0, 2, 20, 20)); // 2 Columnas, filas dinámicas
        panelResultados.setBackground(Color.WHITE);
        panelResultados.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Scroll
        JScrollPane scrollPane = new JScrollPane(panelResultados);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll suave

        panelDerecho.add(barraSuperior, BorderLayout.NORTH);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        // Agregar todo al Panel Principal
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        cargarServiciosDePrueba();
    }

    public void cargarServicios(List<Servicio> servicios) {
        panelResultados.removeAll(); // Limpiar anteriores

        if (servicios == null || servicios.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay servicios disponibles con esos filtros.", SwingConstants.CENTER);
            lblVacio.setFont(EstiloUI.FONT_SUBTITULO);
            panelResultados.add(lblVacio);
        } else {
            for (Servicio s : servicios) {
                panelResultados.add(crearTarjetaServicio(s));
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }

    private JPanel crearTarjetaServicio(Servicio s) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(EstiloUI.MANZANA_50);

        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstiloUI.MANZANA_500, 2, true), // Borde externo
                new EmptyBorder(15, 15, 15, 15) // Padding interno
        ));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(EstiloUI.MANZANA_50);

        JLabel lblNombre = new JLabel(s.getNombreServ());
        lblNombre.setFont(EstiloUI.FONT_SUBTITULO);
        lblNombre.setForeground(EstiloUI.MANZANA_900);

        JLabel lblZona = new JLabel(s.getZona());
        lblZona.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblZona.setForeground(Color.GRAY);
        lblZona.setBorder(new LineBorder(Color.GRAY, 1, true)); // Estilo "chip"

        cabecera.add(lblNombre, BorderLayout.CENTER);
        cabecera.add(lblZona, BorderLayout.EAST);

        JTextArea txtDesc = new JTextArea(s.getDescripcionServ());
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setBackground(EstiloUI.MANZANA_50); // Mismo fondo
        txtDesc.setFont(EstiloUI.FONT_TEXTO);
        txtDesc.setBorder(new EmptyBorder(10, 0, 10, 0));

        JButton btnDetalles = new JButton("VER DETALLES");
        btnDetalles.setBackground(EstiloUI.MANZANA_900);
        btnDetalles.setForeground(Color.WHITE);
        btnDetalles.setFocusPainted(false);
        btnDetalles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetalles.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDetalles.addActionListener(e -> {
            if(ventana.getControlador() != null) {
                ventana.getControlador().verDetalleServicio(s);
            } else {
                ventana.mostrarDetalleServicio(s);
            }
        });

        tarjeta.add(cabecera);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(txtDesc);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnDetalles);

        tarjeta.setPreferredSize(new Dimension(300, 180));

        return tarjeta;
    }

    private void cargarServiciosDePrueba() {
        panelResultados.removeAll();
        // Simulamos datos vacíos por ahora o un mensaje de bienvenida
        JLabel lblInicio = new JLabel("<html><center>Bienvenido al Catálogo.<br>Usa los filtros o busca un servicio.</center></html>", SwingConstants.CENTER);
        lblInicio.setFont(EstiloUI.FONT_SUBTITULO);
        lblInicio.setForeground(Color.GRAY);
        panelResultados.add(lblInicio);
    }
}
