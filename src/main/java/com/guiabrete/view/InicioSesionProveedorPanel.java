package com.guiabrete.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InicioSesionProveedorPanel extends JPanel {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLoginProv, btnLoginVis, btnVolver;
    private MainVista ventana;

    public InicioSesionProveedorPanel(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());
        setBackground(EstiloUI.MANZANA_50);

        initLoginCard();
    }

    private void initLoginCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_100, 2),
                new EmptyBorder(30, 40, 30, 40)
        ));

        JLabel lblTitulo = new JLabel("¡Bienvenido de vuelta!");
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(crearEtiquetaInterna("Correo Electrónico:"));
        txtEmail = EstiloUI.crearInput();
        txtEmail.setMaximumSize(new Dimension(300, 35));
        card.add(txtEmail);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

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

        // --- BOTONES DIVIDIDOS ---
        btnLoginProv = EstiloUI.crearBoton("ENTRAR COMO PROVEEDOR");
        btnLoginProv.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLoginProv.setMaximumSize(new Dimension(300, 40));

        btnLoginVis = EstiloUI.crearBoton("ENTRAR COMO VISITANTE");
        btnLoginVis.setBackground(EstiloUI.MANZANA_600); // Diferenciamos el color
        btnLoginVis.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLoginVis.setMaximumSize(new Dimension(300, 40));

        btnVolver = EstiloUI.crearBoton("VOLVER AL MENÚ");
        btnVolver.setBackground(EstiloUI.MANZANA_900);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.setMaximumSize(new Dimension(300, 40));

        // --- CABLEADO ---
        btnVolver.addActionListener(e -> {
            limpiarCampos();
            ventana.cambiarVista("inicio");
        });

        // Evento para el Proveedor
        btnLoginProv.addActionListener(e -> {
            if (validarCampos()) {
                ventana.getControlador().iniciarSesionProveedor(txtEmail.getText(), new String(txtPassword.getPassword()));
                limpiarCampos();
            }
        });

        // Evento para el Visitante
        btnLoginVis.addActionListener(e -> {
            if (validarCampos()) {
                ventana.getControlador().iniciarSesionVisitante(txtEmail.getText(), new String(txtPassword.getPassword()));
                limpiarCampos();
            }
        });

        card.add(btnLoginProv);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btnLoginVis);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btnVolver);

        add(card);
    }

    private JLabel crearEtiquetaInterna(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(EstiloUI.MANZANA_950);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private boolean validarCampos() {
        if (txtEmail.getText().isEmpty() || new String(txtPassword.getPassword()).isEmpty()) {
            ventana.mostrarMensaje("Por favor, ingresa tu correo y contraseña.");
            return false;
        }
        return true;
    }

    public void limpiarCampos() {
        txtEmail.setText("");
        txtPassword.setText("");
    }
}
