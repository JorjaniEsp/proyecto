package com.guiabrete.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel de interfaz gráfica dedicado al registro de nuevos proveedores en el sistema.
 * <p>Este componente presenta un formulario estructurado en dos columnas utilizando
 * {@link GridBagLayout}, permitiendo una recolección organizada de datos personales,
 * de contacto y de disponibilidad laboral.</p>
 * <p>Incluye validaciones de integridad para asegurar que no se envíen perfiles
 * incompletos al controlador de la aplicación.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class RegistroProveedorPanel extends JPanel {
    private JTextField txtNombre, txtTelefono, txtEmail, txtZona, txtHorario;
    private JPasswordField txtPassword;
    private JButton btnRegistrar, btnVolver;
    private MainVista ventana;

    /**
     * Constructor del panel de registro.
     * <p>Configura la disposición visual general, establece el fondo temático
     * definido en {@link EstiloUI} y añade márgenes de respiración para el formulario.</p>
     * * @param ventana Referencia a la {@link MainVista} para gestionar la navegación de vistas.
     */
    public RegistroProveedorPanel(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(EstiloUI.MANZANA_50);
        setBorder(new EmptyBorder(30, 50, 30, 50));

        initHeader();
        initFormulario();
    }

    /**
     * Inicializa la sección superior del panel.
     * <p>Renderiza la identidad de la marca ("Guía-Brete") y el título de la acción
     * actual para orientar al usuario en el flujo de registro.</p>
     */
    private void initHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);

        JLabel lblLogo = new JLabel("Guía-Brete");
        lblLogo.setFont(EstiloUI.FONT_TITULO);
        lblLogo.setForeground(EstiloUI.MANZANA_900);

        JLabel lblAccion = new JLabel("ÚNETE COMO PROVEEDOR", SwingConstants.RIGHT);
        lblAccion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAccion.setForeground(EstiloUI.MANZANA_600);

        pnlHeader.add(lblLogo, BorderLayout.WEST);
        pnlHeader.add(lblAccion, BorderLayout.EAST);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, EstiloUI.MANZANA_100));

        add(pnlHeader, BorderLayout.NORTH);
    }

    /**
     * Construye el cuerpo del formulario de registro.
     * <p>Organiza los campos en dos columnas principales:</p>
     * <ul>
     * <li><b>Columna Izquierda:</b> Identidad (Nombre, Email y Contraseña).</li>
     * <li><b>Columna Derecha:</b> Operación (Teléfono, Zona y Horario).</li>
     * </ul>
     * <p>Gestiona los eventos de creación de cuenta invocando al método
     * {@code registrarProveedor} del controlador tras validar los campos.</p>
     */
    private void initFormulario() {
        JPanel pnlCampos = new JPanel(new GridBagLayout());
        pnlCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- COLUMNA 1: DATOS DE ACCESO ---
        txtNombre = agregarCampo(pnlCampos, "Nombre Completo:", 0, 0, gbc);
        txtEmail = agregarCampo(pnlCampos, "Correo Electrónico:", 1, 0, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlCampos.add(new JLabel("Contraseña:"), gbc);

        txtPassword = new JPasswordField();
        txtPassword.setBackground(EstiloUI.MANZANA_100);
        txtPassword.setFont(EstiloUI.FONT_TEXTO);
        txtPassword.setPreferredSize(new Dimension(250, 35));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1;
        pnlCampos.add(txtPassword, gbc);

        // --- COLUMNA 2: DATOS DE SERVICIO ---
        txtTelefono = agregarCampo(pnlCampos, "Teléfono Contacto:", 0, 2, gbc);
        txtZona = agregarCampo(pnlCampos, "Zona de Trabajo:", 1, 2, gbc);
        txtHorario = agregarCampo(pnlCampos, "Horario Laboral:", 2, 2, gbc);

        // --- SECCIÓN DE ACCIONES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        pnlBotones.setOpaque(false);

        btnVolver = EstiloUI.crearBoton("RETROCEDER");
        btnVolver.setBackground(EstiloUI.MANZANA_900);

        btnRegistrar = EstiloUI.crearBoton("CREAR CUENTA");

        btnVolver.addActionListener(e -> ventana.cambiarVista("inicio"));

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String pass = new String(txtPassword.getPassword());
            String tel = txtTelefono.getText();
            String zona = txtZona.getText();
            String horario = txtHorario.getText();

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty() ||
                    tel.isEmpty() || zona.isEmpty() || horario.isEmpty()) {
                ventana.mostrarMensaje("Todos los campos son obligatorios para unirte.");
                return;
            }

            if (ventana.getControlador() != null) {
                ventana.getControlador().registrarProveedor(nombre, tel, zona, horario, email, pass);
            }
        });

        pnlBotones.add(btnVolver);
        pnlBotones.add(btnRegistrar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(40, 0, 0, 0);
        pnlCampos.add(pnlBotones, gbc);

        add(pnlCampos, BorderLayout.CENTER);
    }

    /**
     * Método utilitario para añadir pares Etiqueta-Campo de forma estandarizada.
     * <p>Aplica dimensiones uniformes para evitar el colapso visual de los componentes
     * y hereda el estilo de entrada de {@link EstiloUI}.</p>
     * * @param panel El panel contenedor.
     * @param etiqueta El texto descriptivo.
     * @param fila Posición en el eje Y.
     * @param columna Posición en el eje X.
     * @param gbc Configuración de restricciones.
     * @return El {@link JTextField} creado para su posterior acceso.
     */
    private JTextField agregarCampo(JPanel panel, String etiqueta, int fila, int columna, GridBagConstraints gbc) {
        gbc.gridx = columna; gbc.gridy = fila;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl, gbc);

        gbc.gridx = columna + 1;
        JTextField txt = EstiloUI.crearInput();
        txt.setPreferredSize(new Dimension(250, 35));
        panel.add(txt, gbc);
        return txt;
    }

    /**
     * Restablece todos los campos del formulario a su estado vacío.
     * <p>Debe invocarse tras un registro exitoso o al navegar fuera del panel
     * para limpiar datos sensibles.</p>
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtZona.setText("");
        txtHorario.setText("");
        txtPassword.setText("");
    }
}