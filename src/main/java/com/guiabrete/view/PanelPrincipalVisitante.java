package com.guiabrete.view;

import com.guiabrete.model.Categoria;
import com.guiabrete.model.Servicio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel principal para el perfil de Visitante o Invitado.
 * <p>Esta vista funciona como el catálogo central de servicios de la aplicación.
 * Se divide en dos secciones principales:</p>
 * <ul>
 * <li><b>Barra Lateral (Filtros):</b> Permite al usuario segmentar servicios por zona geográfica,
 * categoría técnica o limpiar las búsquedas actuales.</li>
 * <li><b>Área de Contenido:</b> Un espacio dinámico con un buscador textual y una cuadrícula
 * de resultados que renderiza tarjetas detalladas de servicios.</li>
 * </ul>
 * * @author Grupo 04
 * @version 1.0
 */
public class PanelPrincipalVisitante extends JPanel {

    private MainVista ventana;
    /** Contenedor dinámico donde se inyectan las tarjetas de servicios. */
    private JPanel panelResultados;
    /** Campo de entrada para búsquedas textuales por palabra clave. */
    private JTextField txtBuscador;

    /**
     * Constructor que inicializa la interfaz del catálogo.
     * <p>Configura el {@link BorderLayout} principal y ensambla los subpaneles de
     * filtros (Oeste) y resultados (Centro). Implementa diálogos modales para la
     * selección de filtros de zona y categoría.</p>
     * * @param ventana Referencia a la {@link MainVista} para navegación y acceso al controlador.
     */
    public PanelPrincipalVisitante(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // =================================================================================
        // 1. PANEL LATERAL IZQUIERDO (Filtros y Navegación)
        // =================================================================================
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setPreferredSize(new Dimension(250, 0));
        panelIzquierdo.setBackground(EstiloUI.MANZANA_50);
        panelIzquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, EstiloUI.MANZANA_500));
        panelIzquierdo.setLayout(new GridBagLayout());

        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.insets = new Insets(10, 15, 10, 15);
        gbcIzq.fill = GridBagConstraints.HORIZONTAL;
        gbcIzq.gridx = 0;

        JLabel logoLabel = new JLabel("<html><h2 style='color:#1c4b23'>Guía-Brete</h2></html>", SwingConstants.CENTER);

        // Botones de acción de filtrado
        JButton btnZona = EstiloUI.crearBoton("BUSCAR POR ZONA");
        JButton btnCategoria = EstiloUI.crearBoton("BUSCAR POR CATEGORÍA");
        JButton btnLimpiar = EstiloUI.crearBoton("LIMPIAR FILTROS");
        btnLimpiar.setBackground(EstiloUI.MANZANA_900);

        // --- LÓGICA DE FILTRADO ---
        btnZona.addActionListener(e -> {
            String zona = JOptionPane.showInputDialog(this, "Ingrese la zona a buscar:");
            if (zona != null && !zona.isEmpty() && ventana.getControlador() != null) {
                ventana.getControlador().buscarPorZona(zona.trim());
            }
        });

        btnCategoria.addActionListener(e -> {
            Categoria[] categorias = Categoria.values();
            Categoria seleccion = (Categoria) JOptionPane.showInputDialog(
                    this, "Seleccione la categoría:", "Filtrar por Categoría",
                    JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);

            if (seleccion != null && ventana.getControlador() != null) {
                ventana.getControlador().buscarPorCategoria(seleccion);
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtBuscador.setText("");
            if (ventana.getControlador() != null) {
                ventana.getControlador().mostrarCatalogoCompleto();
            }
        });

        JButton btnVolver = new JButton("VOLVER AL INICIO");
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> ventana.cambiarVista("inicio"));

        gbcIzq.gridy = 0; panelIzquierdo.add(logoLabel, gbcIzq);
        gbcIzq.gridy = 1; panelIzquierdo.add(btnZona, gbcIzq);
        gbcIzq.gridy = 2; panelIzquierdo.add(btnCategoria, gbcIzq);
        gbcIzq.gridy = 3; panelIzquierdo.add(btnLimpiar, gbcIzq);
        gbcIzq.weighty = 1.0;
        gbcIzq.anchor = GridBagConstraints.SOUTH;
        gbcIzq.gridy = 4; panelIzquierdo.add(btnVolver, gbcIzq);

        // =================================================================================
        // 2. PANEL DERECHO (Barra de búsqueda y Scroll de resultados)
        // =================================================================================
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);

        // Barra Superior de búsqueda textual
        JPanel barraSuperior = new JPanel(new BorderLayout(10, 10));
        barraSuperior.setBackground(Color.WHITE);
        barraSuperior.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtBuscador = EstiloUI.crearInput();
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBackground(EstiloUI.MANZANA_500);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> {
            if (ventana.getControlador() != null) {
                ventana.getControlador().buscarPorTexto(txtBuscador.getText().trim());
            }
        });

        JPanel panelBuscador = new JPanel(new BorderLayout());
        panelBuscador.add(txtBuscador, BorderLayout.CENTER);
        panelBuscador.add(btnBuscar, BorderLayout.EAST);
        barraSuperior.add(panelBuscador, BorderLayout.CENTER);

        // Área de resultados con Grid dinámico (2 columnas)
        panelResultados = new JPanel(new GridLayout(0, 2, 20, 20));
        panelResultados.setBackground(Color.WHITE);
        panelResultados.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panelResultados);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll más fluido

        panelDerecho.add(barraSuperior, BorderLayout.NORTH);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        cargarServiciosDePrueba();
    }

    /**
     * Actualiza el área de resultados con una nueva lista de servicios.
     * <p>Este método es invocado por el controlador tras realizar un filtrado
     * o búsqueda. Limpia el contenedor actual y reconstruye las tarjetas.</p>
     * * @param servicios Lista de objetos {@link Servicio} a renderizar.
     */
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

    /**
     * Crea un componente visual (Tarjeta) para representar un servicio individual.
     * <p>La tarjeta incluye el nombre, una etiqueta de zona, descripción resumida
     * y un botón de acceso a detalles.</p>
     * * @param s El objeto {@link Servicio} a encapsular en la tarjeta.
     * @return Un {@link JPanel} estilizado con la información del servicio.
     */
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

        JButton btnDetalles = new JButton("VER DETALLES");
        btnDetalles.setBackground(EstiloUI.MANZANA_900);
        btnDetalles.setForeground(Color.WHITE);
        btnDetalles.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    /**
     * Muestra un mensaje de bienvenida o estado inicial en el área de resultados.
     */
    private void cargarServiciosDePrueba() {
        panelResultados.removeAll();
        JLabel lblInicio = new JLabel("<html><center>Bienvenido al Catálogo.<br>Usa los filtros o busca un servicio.</center></html>", SwingConstants.CENTER);
        lblInicio.setFont(EstiloUI.FONT_SUBTITULO);
        lblInicio.setForeground(Color.GRAY);
        panelResultados.add(lblInicio);
    }
}