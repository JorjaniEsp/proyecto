package com.guiabrete.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistroProveedorPanel extends JPanel {
    private JTextField txtNombre, txtTelefono, txtEmail, txtZona, txtHorario;
    private JPasswordField txtPassword;
    private JButton btnRegistrar, btnVolver;
    private MainVista ventana; // [cite: 242]

    public RegistroProveedorPanel(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(EstiloUI.MANZANA_50); //
        setBorder(new EmptyBorder(30, 50, 30, 50));

        initHeader();
        initFormulario();
    }

    private void initHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);

        // Logo y Título [cite: 3, 30]
        JLabel lblLogo = new JLabel("Guía-Brete");
        lblLogo.setFont(EstiloUI.FONT_TITULO);
        lblLogo.setForeground(EstiloUI.MANZANA_900);

        JLabel lblAccion = new JLabel("REGISTRO DE NUEVO PROVEEDOR", SwingConstants.RIGHT);
        lblAccion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAccion.setForeground(EstiloUI.MANZANA_600);

        pnlHeader.add(lblLogo, BorderLayout.WEST);
        pnlHeader.add(lblAccion, BorderLayout.EAST);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, EstiloUI.MANZANA_100));

        add(pnlHeader, BorderLayout.NORTH);
    }

    private void initFormulario() {
        JPanel pnlCampos = new JPanel(new GridBagLayout());
        pnlCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- COLUMNA 1 ---
        txtNombre = agregarCampo(pnlCampos, "Nombre Completo:", 0, 0, gbc);
        txtEmail = agregarCampo(pnlCampos, "Correo Electrónico:", 1, 0, gbc);

        // Password con estilo unificado
        gbc.gridx = 0; gbc.gridy = 2;
        pnlCampos.add(new JLabel("Contraseña:"), gbc);
        txtPassword = new JPasswordField();
        txtPassword.setBackground(EstiloUI.MANZANA_100);
        txtPassword.setFont(EstiloUI.FONT_TEXTO);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.MANZANA_500, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1;
        pnlCampos.add(txtPassword, gbc);

        // --- COLUMNA 2 ---
        txtTelefono = agregarCampo(pnlCampos, "Teléfono Contacto:", 0, 2, gbc);
        txtZona = agregarCampo(pnlCampos, "Zona de Trabajo:", 1, 2, gbc);
        txtHorario = agregarCampo(pnlCampos, "Horario Laboral:", 2, 2, gbc);

        // --- BOTONES AL PIE ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        pnlBotones.setOpaque(false);

        btnVolver = EstiloUI.crearBoton("RETROCEDER");
        btnVolver.setBackground(EstiloUI.MANZANA_900);

        btnRegistrar = EstiloUI.crearBoton("CREAR CUENTA");

        pnlBotones.add(btnVolver);
        pnlBotones.add(btnRegistrar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(40, 0, 0, 0);
        pnlCampos.add(pnlBotones, gbc);

        add(pnlCampos, BorderLayout.CENTER);
    }

    private JTextField agregarCampo(JPanel panel, String etiqueta, int fila, int columna, GridBagConstraints gbc) {
        gbc.gridx = columna; gbc.gridy = fila;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl, gbc);

        gbc.gridx = columna + 1;
        JTextField txt = EstiloUI.crearInput();
        panel.add(txt, gbc);
        return txt;
    }

    // --- MÉTODOS INDISPENSABLES ---

    public void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtZona.setText("");
        txtHorario.setText("");
        txtPassword.setText("");
    }

    // Getters para el controlador
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnVolver() { return btnVolver; }

    public String getNombre() { return txtNombre.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getZona() { return txtZona.getText(); }
    public String getHorario() { return txtHorario.getText(); }
    public String getTelefono() { return txtTelefono.getText(); }
}