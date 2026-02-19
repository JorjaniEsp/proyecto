package com.guiabrete.view;

import com.guiabrete.model.Servicio;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;

public class PanelDetalleServicio extends JPanel {

    private MainVista ventana;

    private JLabel lblNombreServicio;
    private JLabel lblProveedor;
    private JLabel lblZona;
    private JLabel lblHorario;
    private JTextArea txtDescripcion;
    private JButton btnContactar;
    private Servicio servicioActual;

    public PanelDetalleServicio(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 40, 20, 40)); // Márgenes amplios

        // 1. CABECERA
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("DETALLE DEL SERVICIO", SwingConstants.CENTER);
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);

        // Icono grande de servicio
        JLabel icono = new JLabel("🛠️", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI", Font.PLAIN, 60));

        panelCabecera.add(lblTitulo, BorderLayout.NORTH);
        panelCabecera.add(icono, BorderLayout.CENTER);

        // 2. FORMULARIO DE INFORMACIÓN (GridBag para alineación bonita)
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inicializar componentes
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

        // Agregar filas (Etiqueta + Valor)
        agregarFila(panelInfo, gbc, 0, "Servicio:", lblNombreServicio);
        agregarFila(panelInfo, gbc, 1, "Proveedor:", lblProveedor);
        agregarFila(panelInfo, gbc, 2, "Zona:", lblZona);
        agregarFila(panelInfo, gbc, 3, "Horario:", lblHorario);

        // Descripción ocupa más espacio
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelInfo.add(new JLabel("Descripción:"), gbc);
        gbc.gridy = 5;
        panelInfo.add(new JScrollPane(txtDescripcion), gbc);

        // 3. BOTONERA (Contactar y Volver)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(Color.WHITE);

        btnContactar = EstiloUI.crearBoton("📲 CONTACTAR POR WHATSAPP");
        btnContactar.setBackground(new Color(37, 211, 102)); // Verde WhatsApp oficial

        JButton btnVolver = new JButton("VOLVER AL CATÁLOGO");
        btnVolver.setFont(EstiloUI.FONT_BOTON);
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ACCIONES
        btnVolver.addActionListener(e -> ventana.cambiarVista("panelVisitante"));

        btnContactar.addActionListener(e -> abrirWhatsApp());

        panelBotones.add(btnVolver);
        panelBotones.add(btnContactar);

        // ENSAMBLAR
        add(panelCabecera, BorderLayout.NORTH);
        add(panelInfo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Método auxiliar para construir filas del formulario
    private void agregarFila(JPanel p, GridBagConstraints gbc, int fila, String titulo, JLabel valor) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1; gbc.weightx = 0.3;
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(EstiloUI.FONT_BOTON); // Negrita
        p.add(lblTitulo, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        p.add(valor, gbc);
    }

    private JLabel crearEtiquetaValor(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(EstiloUI.FONT_TEXTO);
        l.setForeground(Color.DARK_GRAY);
        return l;
    }

    public void mostrarDetalle(Servicio s) {
        this.servicioActual = s;
        lblNombreServicio.setText(s.getNombreServ());
        lblProveedor.setText(s.getProveedor().getNombre());
        lblZona.setText(s.getZona());
        lblHorario.setText(s.getHorario());
        txtDescripcion.setText(s.getDescripcionServ());
    }

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
