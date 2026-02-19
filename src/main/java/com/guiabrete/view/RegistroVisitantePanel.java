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
        setBackground(EstiloUI.MANZANA_50);
        setLayout(new GridBagLayout());

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 2, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        JLabel lblTitulo = new JLabel("REGISTRO DE VISITANTE", SwingConstants.CENTER);
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel camposPanel = new JPanel(new GridBagLayout());
        camposPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;

        txtNombre = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 0, "Nombre Completo:", txtNombre);

        txtContacto = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 1, "Número de Teléfono (8 dígitos):", txtContacto);

        txtEmail = EstiloUI.crearInput();
        agregarCampo(camposPanel, gbc, 2, "Correo Electrónico:", txtEmail);

        txtPassword = new JPasswordField();
        txtPassword.setFont(EstiloUI.FONT_INPUT);
        txtPassword.setBackground(EstiloUI.MANZANA_100);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        agregarCampo(camposPanel, gbc, 3, "Contraseña:", txtPassword);

        JButton btnRegistrar = EstiloUI.crearBoton("CREAR CUENTA");
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnVolver = new JButton("Volver al Inicio");
        btnVolver.setFont(EstiloUI.FONT_BOTON);
        btnVolver.setForeground(EstiloUI.MANZANA_900);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnVolver.addActionListener(e -> {
            limpiarCampos(); // Limpiamos si el usuario se arrepiente y vuelve
            ventana.cambiarVista("inicio");
        });

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String contacto = txtContacto.getText();
            String email = txtEmail.getText();
            String pass = new String(txtPassword.getPassword());

            if (nombre.isEmpty() || contacto.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                ventana.mostrarMensaje("Por favor complete todos los campos.");
                return;
            }

            if (ventana.getControlador() != null) {
                ventana.getControlador().registrarVisitante(nombre, contacto, email, pass);
                limpiarCampos(); // <--- ¡AQUÍ ESTÁ LA SOLUCIÓN! Limpia tras registrar
            } else {
                ventana.mostrarMensaje("Error: Controlador no conectado.");
            }
        });

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(camposPanel);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(btnRegistrar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnVolver);

        add(tarjeta);
    }

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

    // Nuevo método para vaciar todo
    private void limpiarCampos() {
        txtNombre.setText("");
        txtContacto.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
}
