package com.guiabrete.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InicioSesionProveedorPanel extends JPanel {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnVolver;
    private MainVista ventana;

    public InicioSesionProveedorPanel(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout()); // Para centrar el formulario de login
        setBackground(EstiloUI.MANZANA_50);

        initLoginCard();
    }

    private void initLoginCard() {
        // Contenedor tipo "Tarjeta" blanca
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_100, 2),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // Título e Icono [cite: 3, 214]
        JLabel lblTitulo = new JLabel("Ingreso de Proveedor");
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        // Campo: Correo
        card.add(crearEtiquetaInterna("Correo Electrónico:"));
        txtEmail = EstiloUI.crearInput();
        txtEmail.setMaximumSize(new Dimension(300, 35));
        card.add(txtEmail);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campo: Contraseña
        card.add(crearEtiquetaInterna("Contraseña:"));
        txtPassword = new JPasswordField();
        txtPassword.setBackground(EstiloUI.MANZANA_100);
        txtPassword.setFont(EstiloUI.FONT_TEXTO);
        txtPassword.setMaximumSize(new Dimension(300, 35));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        card.add(txtPassword);

        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botones
        btnLogin = EstiloUI.crearBoton("INICIAR SESIÓN");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(300, 40));

        btnVolver = EstiloUI.crearBoton("VOLVER AL MENÚ");
        btnVolver.setBackground(EstiloUI.MANZANA_900);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.setMaximumSize(new Dimension(300, 40));

        card.add(btnLogin);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btnVolver);

        add(card); // Añade la tarjeta al GridBagLayout central
    }

    private JLabel crearEtiquetaInterna(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(EstiloUI.MANZANA_950);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    // Métodos para el controlador
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnVolver() { return btnVolver; }

    public String getEmail() { return txtEmail.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }

    public void limpiarCampos() {
        txtEmail.setText("");
        txtPassword.setText("");
    }
}

