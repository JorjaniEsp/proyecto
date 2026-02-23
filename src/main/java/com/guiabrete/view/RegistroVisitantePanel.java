package com.guiabrete.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel de interfaz gráfica diseñado para el registro de nuevos Visitantes.
 * <p>A diferencia de los paneles de gestión, este componente utiliza un diseño de
 * tarjeta (Card) centrado en pantalla, proporcionando una experiencia de usuario
 * más limpia y enfocada al proceso de "Onboarding".</p>
 * <p>Implementa validaciones de campos obligatorios y una gestión automática de
 * limpieza de datos para mantener la privacidad y seguridad del usuario.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class RegistroVisitantePanel extends JPanel {

    private JTextField txtNombre;
    private JTextField txtContacto;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    /**
     * Constructor que inicializa el entorno visual del registro de visitantes.
     * <p>Utiliza un {@link GridBagLayout} en el panel raíz para centrar la tarjeta
     * de registro independientemente del tamaño de la ventana.</p>
     * * @param ventana Referencia a la {@link MainVista} para orquestar los cambios de pantalla.
     */
    public RegistroVisitantePanel(MainVista ventana) {
        setBackground(EstiloUI.MANZANA_50);
        setLayout(new GridBagLayout());

        // --- TARJETA CONTENEDORA ---
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 2, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // Título de la tarjeta
        JLabel lblTitulo = new JLabel("REGISTRO DE VISITANTE", SwingConstants.CENTER);
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- PANEL DE ENTRADA DE DATOS ---
        JPanel camposPanel = new JPanel(new GridBagLayout());
        camposPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;

        // Inicialización de inputs con EstiloUI
        txtNombre = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 0, "Nombre Completo:", txtNombre);

        txtContacto = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 1, "Número de Teléfono (8 dígitos):", txtContacto);

        txtEmail = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 2, "Correo Electrónico:", txtEmail);

        // Campo especial para contraseñas
        txtPassword = new JPasswordField();
        txtPassword.setFont(EstiloUI.FONT_INPUT);
        txtPassword.setBackground(EstiloUI.MANZANA_100);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        agregarCampo(camposPanel, gbc, 3, "Contraseña:", txtPassword);

        // --- BOTONERA ---
        JButton btnRegistrar = EstiloUI.crearBoton("CREAR CUENTA");
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnVolver = new JButton("Volver al Inicio");
        btnVolver.setFont(EstiloUI.FONT_BOTON);
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- EVENTOS ---
        btnVolver.addActionListener(e -> {
            limpiarCampos();
            ventana.cambiarVista("inicio");
        });

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String contacto = txtContacto.getText();
            String email = txtEmail.getText();
            String pass = new String(txtPassword.getPassword());

            // Validación de integridad
            if (nombre.isEmpty() || contacto.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                ventana.mostrarMensaje("Por favor complete todos los campos.");
                return;
            }

            if (ventana.getControlador() != null) {
                ventana.getControlador().registrarVisitante(nombre, contacto, email, pass);
                limpiarCampos();
            } else {
                ventana.mostrarMensaje("Error: Controlador no conectado.");
            }
        });

        // Ensamblaje de la tarjeta
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(camposPanel);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(btnRegistrar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnVolver);

        add(tarjeta);
    }

    /**
     * Método auxiliar para estructurar las filas del formulario.
     * <p>Ubica la etiqueta sobre el campo de texto de forma vertical, aplicando
     * dimensiones estándar de 300x35 píxeles.</p>
     * * @param panel El contenedor del formulario.
     * @param gbc Objeto de restricciones para el layout.
     * @param fila Índice de la fila.
     * @param etiqueta Texto del label.
     * @param campo Componente de entrada de datos.
     */
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridy = fila * 2;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(EstiloUI.MANZANA_900);
        panel.add(lbl, gbc);

        gbc.gridy = (fila * 2) + 1;
        campo.setPreferredSize(new Dimension(300, 35));
        panel.add(campo, gbc);
    }

    /**
     * Vacía el contenido de todos los campos de texto del formulario.
     * <p>Se utiliza para resetear la interfaz después de un registro exitoso
     * o cuando el usuario cancela la operación.</p>
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtContacto.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
}