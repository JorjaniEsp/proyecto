package com.guiabrete.view;

import com.guiabrete.model.Servicio;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;

/**
 * Panel de visualización detallada de un servicio específico.
 * <p>Esta vista presenta toda la información de un oficio (nombre, proveedor, zona, horario y descripción)
 * de forma organizada y estética. Además, proporciona funcionalidad de integración externa
 * para facilitar el contacto directo entre el visitante y el proveedor.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class PanelDetalleServicio extends JPanel {

    /** Referencia a la ventana principal para la navegación entre vistas. */
    private MainVista ventana;

    // Componentes de visualización de datos
    private JLabel lblNombreServicio;
    private JLabel lblProveedor;
    private JLabel lblZona;
    private JLabel lblHorario;
    private JTextArea txtDescripcion;
    private JButton btnContactar;

    /** Objeto de dominio que se está visualizando actualmente. */
    private Servicio servicioActual;

    /**
     * Constructor que inicializa la estructura visual del panel.
     * <p>Configura tres secciones principales:</p>
     * <ul>
     * <li><b>Cabecera:</b> Título e identidad visual del detalle.</li>
     * <li><b>Centro:</b> Formulario de información técnica utilizando {@link GridBagLayout}.</li>
     * <li><b>Inferior:</b> Botonera de acciones (Contacto y Navegación).</li>
     * </ul>
     * * @param ventana Referencia a la {@link MainVista}.
     */
    public PanelDetalleServicio(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // 1. CABECERA
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("DETALLE DEL SERVICIO", SwingConstants.CENTER);
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);

        JLabel icono = new JLabel("🛠️", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI", Font.PLAIN, 60));

        panelCabecera.add(lblTitulo, BorderLayout.NORTH);
        panelCabecera.add(icono, BorderLayout.CENTER);

        // 2. FORMULARIO DE INFORMACIÓN
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblNombreServicio = crearEtiquetaValor("");
        lblProveedor = crearEtiquetaValor("");
        lblZona = crearEtiquetaValor("");
        lblHorario = crearEtiquetaValor("");

        txtDescripcion = new JTextArea(5, 20);
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(EstiloUI.FONT_TEXTO);
        txtDescripcion.setBackground(EstiloUI.MANZANA_50);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(EstiloUI.MANZANA_100));

        agregarFila(panelInfo, gbc, 0, "Servicio:", lblNombreServicio);
        agregarFila(panelInfo, gbc, 1, "Proveedor:", lblProveedor);
        agregarFila(panelInfo, gbc, 2, "Zona:", lblZona);
        agregarFila(panelInfo, gbc, 3, "Horario:", lblHorario);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelInfo.add(new JLabel("Descripción:"), gbc);
        gbc.gridy = 5;
        panelInfo.add(new JScrollPane(txtDescripcion), gbc);

        // 3. BOTONERA
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(Color.WHITE);

        btnContactar = EstiloUI.crearBoton("📲 CONTACTAR POR WHATSAPP");
        btnContactar.setBackground(new Color(37, 211, 102));

        JButton btnVolver = new JButton("VOLVER AL CATÁLOGO");
        btnVolver.setFont(EstiloUI.FONT_BOTON);
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Listeners
        btnVolver.addActionListener(e -> ventana.cambiarVista("panelVisitante"));
        btnContactar.addActionListener(e -> abrirWhatsApp());

        panelBotones.add(btnVolver);
        panelBotones.add(btnContactar);

        add(panelCabecera, BorderLayout.NORTH);
        add(panelInfo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Método auxiliar para estructurar filas dentro del GridBagLayout.
     * Separa la etiqueta del título (izquierda) del valor del dato (derecha).
     * * @param p Panel contenedor.
     * @param gbc Configuración de restricciones del GridBag.
     * @param fila Índice de la fila en la cuadrícula.
     * @param titulo Texto de la etiqueta descriptiva.
     * @param valor Componente JLabel que contiene el dato.
     */
    private void agregarFila(JPanel p, GridBagConstraints gbc, int fila, String titulo, JLabel valor) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1; gbc.weightx = 0.3;
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(EstiloUI.FONT_BOTON);
        p.add(lblTitulo, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        p.add(valor, gbc);
    }

    /**
     * Crea un JLabel con el estilo predeterminado para mostrar valores de datos.
     * @param texto Texto inicial.
     * @return JLabel configurado.
     */
    private JLabel crearEtiquetaValor(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(EstiloUI.FONT_TEXTO);
        l.setForeground(Color.DARK_GRAY);
        return l;
    }

    /**
     * Actualiza el contenido del panel con la información de un servicio específico.
     * @param s El objeto {@link Servicio} a mostrar.
     */
    public void mostrarDetalle(Servicio s) {
        this.servicioActual = s;
        lblNombreServicio.setText(s.getNombreServ());
        lblProveedor.setText(s.getProveedor().getNombre());
        lblZona.setText(s.getZona());
        lblHorario.setText(s.getHorario());
        txtDescripcion.setText(s.getDescripcionServ());
    }

    /**
     * Ejecuta la apertura del navegador predeterminado para iniciar una conversación de WhatsApp.
     * <p>Utiliza la API de {@link Desktop} y el protocolo {@code https://wa.me/} con el número
     * de contacto normalizado del servicio.</p>
     */
    private void abrirWhatsApp() {
        if (servicioActual != null && servicioActual.getContacto() != null) {
            try {
                String url = "https://wa.me/" + servicioActual.getContacto();
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo abrir WhatsApp: " + ex.getMessage());
            }
        }
    }
}