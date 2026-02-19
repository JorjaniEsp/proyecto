package com.guiabrete.view;

import com.guiabrete.model.Categoria;
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

        // A. Logo
        JLabel logoLabel = new JLabel("<html><h2 style='color:#1c4b23'>Guía-Brete</h2></html>", SwingConstants.CENTER);

        // B. Botones de Filtros
        JButton btnZona = EstiloUI.crearBoton("BUSCAR POR ZONA");
        JButton btnCategoria = EstiloUI.crearBoton("BUSCAR POR CATEGORÍA");
        JButton btnLimpiar = EstiloUI.crearBoton("LIMPIAR FILTROS");

        btnLimpiar.setBackground(EstiloUI.MANZANA_900);

        // --- CABLEADO DE FILTROS ---
        btnZona.addActionListener(e -> {
            String zona = JOptionPane.showInputDialog(this, "Ingrese la zona a buscar:");
            if (zona != null && !zona.isEmpty() && ventana.getControlador() != null) {
                ventana.getControlador().buscarPorZona(zona.trim());
            }
        });

        btnCategoria.addActionListener(e -> {
            // Genera una lista desplegable con tu Enum "Categoria"
            Categoria[] categorias = Categoria.values();
            Categoria seleccion = (Categoria) JOptionPane.showInputDialog(
                    this,
                    "Seleccione la categoría que busca:",
                    "Filtrar por Categoría",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    categorias,
                    categorias[0]
            );

            // Si el usuario seleccionó una categoría y le dio a OK
            if (seleccion != null && ventana.getControlador() != null) {
                ventana.getControlador().buscarPorCategoria(seleccion);
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtBuscador.setText(""); // Limpiamos la barra
            if (ventana.getControlador() != null) {
                ventana.getControlador().mostrarCatalogoCompleto();
            }
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
        gbcIzq.insets = new Insets(40, 15, 10, 15);
        gbcIzq.gridy = 1; panelIzquierdo.add(btnZona, gbcIzq);
        gbcIzq.insets = new Insets(10, 15, 10, 15);
        gbcIzq.gridy = 2; panelIzquierdo.add(btnCategoria, gbcIzq);
        gbcIzq.gridy = 3; panelIzquierdo.add(btnLimpiar, gbcIzq);

        gbcIzq.weighty = 1.0;
        gbcIzq.anchor = GridBagConstraints.SOUTH;
        gbcIzq.gridy = 4; panelIzquierdo.add(btnVolver, gbcIzq);

        // =================================================================================
        // 2. PANEL DERECHO (Contenido Principal)
        // =================================================================================
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);

        // A. Barra Superior (Buscador)
        JPanel barraSuperior = new JPanel(new BorderLayout(10, 10));
        barraSuperior.setBackground(Color.WHITE);
        barraSuperior.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtBuscador = EstiloUI.crearInput();
        txtBuscador.setText(""); // Nace vacío, tal como pediste

        // Botón Lupa (Búsqueda por texto)
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBackground(EstiloUI.MANZANA_500);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> {
            // Conexión real con el controlador
            if (ventana.getControlador() != null) {
                ventana.getControlador().buscarPorTexto(txtBuscador.getText().trim());
            }
        });

        JPanel panelBuscador = new JPanel(new BorderLayout());
        panelBuscador.add(txtBuscador, BorderLayout.CENTER);
        panelBuscador.add(btnBuscar, BorderLayout.EAST);

        // El botón de perfil fue eliminado
        barraSuperior.add(panelBuscador, BorderLayout.CENTER);

        // B. Área de Resultados (Scroll con Grid de Tarjetas)
        panelResultados = new JPanel(new GridLayout(0, 2, 20, 20));
        panelResultados.setBackground(Color.WHITE);
        panelResultados.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panelResultados);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelDerecho.add(barraSuperior, BorderLayout.NORTH);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        cargarServiciosDePrueba();
    }

    public void cargarServicios(List<Servicio> servicios) {
        panelResultados.removeAll();

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
                new LineBorder(EstiloUI.MANZANA_500, 2, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(EstiloUI.MANZANA_50);

        JLabel lblNombre = new JLabel(s.getNombreServ());
        lblNombre.setFont(EstiloUI.FONT_SUBTITULO);
        lblNombre.setForeground(EstiloUI.MANZANA_900);

        JLabel lblZona = new JLabel(s.getZona());
        lblZona.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblZona.setForeground(Color.GRAY);
        lblZona.setBorder(new LineBorder(Color.GRAY, 1, true));

        cabecera.add(lblNombre, BorderLayout.CENTER);
        cabecera.add(lblZona, BorderLayout.EAST);

        JTextArea txtDesc = new JTextArea(s.getDescripcionServ());
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setBackground(EstiloUI.MANZANA_50);
        txtDesc.setFont(EstiloUI.FONT_TEXTO);
        txtDesc.setBorder(new EmptyBorder(10, 0, 10, 0));

        JButton btnDetalles = new JButton("VER DETALLES");
        btnDetalles.setBackground(EstiloUI.MANZANA_900);
        btnDetalles.setForeground(Color.WHITE);
        btnDetalles.setFocusPainted(false);
        btnDetalles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetalles.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDetalles.addActionListener(e -> {
            if (ventana.getControlador() != null) {
                ventana.getControlador().verDetalleServicio(s);
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
        JLabel lblInicio = new JLabel("<html><center>Bienvenido al Catálogo.<br>Usa los filtros o busca un servicio.</center></html>", SwingConstants.CENTER);
        lblInicio.setFont(EstiloUI.FONT_SUBTITULO);
        lblInicio.setForeground(Color.GRAY);
        panelResultados.add(lblInicio);
    }
}
