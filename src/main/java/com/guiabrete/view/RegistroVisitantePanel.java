package com.guiabrete.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistroVisitantePanel extends JPanel {

    private JTextField txtNombre;
    private JTextField txtContacto;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    public RegistroVisitantePanel(MainVista ventana) {
        // 1. Configuración Base (Fondo Manzana Suave)
        setBackground(EstiloUI.MANZANA_50);
        setLayout(new GridBagLayout()); // Centramos todo el formulario en la pantalla

        // 2. TARJETA CENTRAL (Contenedor blanco con el formulario)
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        // Borde verde suave y relleno interno
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 2, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // 3. TÍTULO
        JLabel lblTitulo = new JLabel("REGISTRO DE VISITANTE", SwingConstants.CENTER);
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. CAMPOS DEL FORMULARIO
        // Usamos un panel auxiliar con GridBagLayout para alinear etiquetas y cajas
        JPanel camposPanel = new JPanel(new GridBagLayout());
        camposPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0); // Espacio vertical entre campos
        gbc.gridx = 0;

        // --- Nombre ---
        txtNombre = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 0, "Nombre Completo:", txtNombre);

        // --- Contacto (Teléfono) ---
        txtContacto = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 1, "Número de Teléfono (8 dígitos):", txtContacto);

        // --- Correo ---
        txtEmail = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 2, "Correo Electrónico:", txtEmail);

        // --- Contraseña ---
        txtPassword = new JPasswordField();
        txtPassword.setFont(EstiloUI.FONT_INPUT);
        txtPassword.setBackground(EstiloUI.MANZANA_100);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        agregarCampo(camposPanel, gbc, 3, "Contraseña:", txtPassword);

        // 5. BOTONES
        JButton btnRegistrar = EstiloUI.crearBoton("CREAR CUENTA");
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnVolver = new JButton("Volver al Inicio");
        btnVolver.setFont(EstiloUI.FONT_BOTON);
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- ACCIONES ---

        // Acción Volver
        btnVolver.addActionListener(e -> ventana.cambiarVista("inicio"));

        // Acción Registrar
        btnRegistrar.addActionListener(e -> {
            // Capturar datos
            String nombre = txtNombre.getText();
            String contacto = txtContacto.getText();
            String email = txtEmail.getText();
            String pass = new String(txtPassword.getPassword());

            // Validar campos vacíos antes de molestar al controlador
            if (nombre.isEmpty() || contacto.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                ventana.mostrarMensaje("Por favor complete todos los campos.");
                return;
            }

            // Llamar al Controlador
            if (ventana.getControlador() != null) {
                ventana.getControlador().registrarVisitante(nombre, contacto, email, pass);
            } else {
                ventana.mostrarMensaje("Error: Controlador no conectado.");
            }
        });

        // 6. ENSAMBLAJE FINAL
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(20)); // Espacio
        tarjeta.add(camposPanel);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(btnRegistrar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnVolver);

        add(tarjeta);
    }

    // Método auxiliar para no repetir código al agregar campos
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridy = fila * 2; // Fila par: Etiqueta
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(EstiloUI.MANZANA_900);
        panel.add(lbl, gbc);

        gbc.gridy = (fila * 2) + 1; // Fila impar: Input
        // Definir tamaño preferido para que no se achiquen
        campo.setPreferredSize(new Dimension(300, 35));
        panel.add(campo, gbc);
    }
}
