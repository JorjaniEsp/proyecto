package com.guiabrete.view;

import com.guiabrete.model.Proveedor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel de interfaz gráfica dedicado a la gestión y actualización del perfil de un proveedor.
 * <p>Este componente permite al proveedor modificar su información de contacto (Teléfono),
 * área de cobertura (Zona) y disponibilidad (Horario). Los datos de identidad base
 * como el nombre y correo electrónico se mantienen en modo de solo lectura por seguridad.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class PanelPerfilProveedor extends JPanel {
    private JTextField txtNombre, txtTelefono, txtEmail, txtZona, txtHorario;
    private JButton btnGuardar, btnVolver;
    private MainVista ventana;

    /** Almacena la referencia del proveedor que ha iniciado sesión actualmente. */
    private Proveedor proveedorActual;

    /**
     * Constructor del panel de perfil.
     * <p>Configura el layout general y los márgenes internos para una visualización
     * aireada y profesional, siguiendo la paleta de colores de {@link EstiloUI}.</p>
     * * @param ventana Referencia a la {@link MainVista} para gestionar la navegación de vistas.
     */
    public PanelPerfilProveedor(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(EstiloUI.MANZANA_50);
        setBorder(new EmptyBorder(40, 60, 40, 60));

        initHeader();
        initFormulario();
    }

    /**
     * Inicializa la cabecera informativa del panel.
     */
    private void initHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);

        JLabel lblTitulo = new JLabel("MI PERFIL DE PROVEEDOR");
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);

        JLabel lblInfo = new JLabel("Gestione su información de contacto y disponibilidad", SwingConstants.RIGHT);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblInfo.setForeground(EstiloUI.MANZANA_600);

        pnlHeader.add(lblTitulo, BorderLayout.WEST);
        pnlHeader.add(lblInfo, BorderLayout.EAST);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, EstiloUI.MANZANA_100));

        add(pnlHeader, BorderLayout.NORTH);
    }

    /**
     * Construye los campos de entrada y los controles de acción.
     * <p>Utiliza un {@link GridBagLayout} para organizar los campos en dos columnas
     * temáticas (Datos Personales y Datos de Servicio) y gestiona los eventos de guardado.</p>
     */
    private void initFormulario() {
        JPanel pnlCampos = new JPanel(new GridBagLayout());
        pnlCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- SECCIÓN: DATOS DE IDENTIDAD (Inhabilitados para edición directa) ---
        txtNombre = agregarCampo(pnlCampos, "NOMBRE COMPLETO:", 0, 0, gbc);
        txtNombre.setEnabled(false);

        txtEmail = agregarCampo(pnlCampos, "CORREO ELECTRÓNICO:", 1, 0, gbc);
        txtEmail.setEnabled(false);

        // --- SECCIÓN: DATOS OPERATIVOS ---
        txtTelefono = agregarCampo(pnlCampos, "TELÉFONO / WHATSAPP:", 2, 0, gbc);
        txtZona = agregarCampo(pnlCampos, "ZONA DE TRABAJO:", 0, 2, gbc);
        txtHorario = agregarCampo(pnlCampos, "HORARIO DE ATENCIÓN:", 1, 2, gbc);

        // --- CONFIGURACIÓN DE ACCIONES ---
        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        pnlAcciones.setOpaque(false);

        btnVolver = EstiloUI.crearBoton("VOLVER");
        btnVolver.setBackground(EstiloUI.MANZANA_900);

        btnGuardar = EstiloUI.crearBoton("GUARDAR PERFIL");

        // Regresar al dashboard del proveedor
        btnVolver.addActionListener(e -> ventana.cambiarVista("panelProveedor"));

        // Validar y enviar cambios al controlador
        btnGuardar.addActionListener(e -> {
            String tel = txtTelefono.getText().trim();
            String zona = txtZona.getText().trim();
            String horario = txtHorario.getText().trim();

            if (tel.isEmpty() || zona.isEmpty() || horario.isEmpty()) {
                ventana.mostrarMensaje("Teléfono, Zona y Horario no pueden quedar vacíos.");
                return;
            }

            if (ventana.getControlador() != null) {
                ventana.getControlador().actualizarPerfilProveedor(tel, zona, horario);
            }
        });

        pnlAcciones.add(btnVolver);
        pnlAcciones.add(btnGuardar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(40, 0, 0, 0);
        pnlCampos.add(pnlAcciones, gbc);

        add(pnlCampos, BorderLayout.CENTER);
    }

    /**
     * Método auxiliar para inyectar un conjunto de etiqueta y campo de texto en el formulario.
     * * @param panel El panel contenedor.
     * @param etiqueta Texto descriptivo del campo.
     * @param fila Índice de fila en el GridBag.
     * @param columna Índice de columna inicial.
     * @param gbc Objeto de restricciones.
     * @return El {@link JTextField} creado para su posterior manipulación.
     */
    private JTextField agregarCampo(JPanel panel, String etiqueta, int fila, int columna, GridBagConstraints gbc) {
        gbc.gridx = columna; gbc.gridy = fila;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(EstiloUI.MANZANA_950);
        panel.add(lbl, gbc);

        gbc.gridx = columna + 1;
        JTextField txt = EstiloUI.crearInput();
        txt.setPreferredSize(new Dimension(250, 35));
        panel.add(txt, gbc);
        return txt;
    }

    /**
     * Carga y renderiza los datos de un objeto Proveedor en el formulario.
     * <p>Realiza un formateo visual del teléfono eliminando el prefijo internacional
     * "506" para facilitar la lectura del usuario costarricense.</p>
     * * @param p La instancia de {@link Proveedor} con los datos actuales.
     */
    public void cargarDatosPerfil(Proveedor p) {
        this.proveedorActual = p;
        txtNombre.setText(p.getNombre());
        txtEmail.setText(p.getEmail());

        String tel = p.getTelefono();
        if(tel != null && tel.startsWith("506")) tel = tel.substring(3);
        txtTelefono.setText(tel);

        txtZona.setText(p.getZona());
        txtHorario.setText(p.getHorario());
    }
}