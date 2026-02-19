package com.guiabrete.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistroProveedorPanel extends JPanel {
    private JTextField txtNombre, txtTelefono, txtEmail, txtZona, txtHorario;
    private JPasswordField txtPassword;
    private JButton btnRegistrar, btnVolver;
    private MainVista ventana;

    public RegistroProveedorPanel(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(EstiloUI.MANZANA_50);
        setBorder(new EmptyBorder(30, 50, 30, 50));

        initHeader();
        initFormulario();
    }

    private void initHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);

        // Logo
        JLabel lblLogo = new JLabel("Guía-Brete");
        lblLogo.setFont(EstiloUI.FONT_TITULO);
        lblLogo.setForeground(EstiloUI.MANZANA_900);

        // Título más amigable
        JLabel lblAccion = new JLabel("ÚNETE COMO PROVEEDOR", SwingConstants.RIGHT);
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
        gbc.weightx = 1.0; // Ayuda a distribuir el espacio equitativamente

        // --- COLUMNA 1 ---
        txtNombre = agregarCampo(pnlCampos, "Nombre Completo:", 0, 0, gbc);
        txtEmail = agregarCampo(pnlCampos, "Correo Electrónico:", 1, 0, gbc);

        // Password con estilo unificado y TAMAÑO ARREGLADO
        gbc.gridx = 0; gbc.gridy = 2;
        pnlCampos.add(new JLabel("Contraseña:"), gbc);

        txtPassword = new JPasswordField();
        txtPassword.setBackground(EstiloUI.MANZANA_100);
        txtPassword.setFont(EstiloUI.FONT_TEXTO);
        txtPassword.setPreferredSize(new Dimension(250, 35)); // <--- ¡Esto evita que se vea enano!
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

        // ==========================================
        // CABLEADO: Los botones cobran vida
        // ==========================================
        btnVolver.addActionListener(e -> ventana.cambiarVista("inicio"));

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String pass = new String(txtPassword.getPassword());
            String tel = txtTelefono.getText();
            String zona = txtZona.getText();
            String horario = txtHorario.getText();

            // Validación visual rápida
            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty() || tel.isEmpty() || zona.isEmpty() || horario.isEmpty()) {
                ventana.mostrarMensaje("Todos los campos son obligatorios para unirte.");
                return;
            }

            // Enviar datos al cerebro
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

    private JTextField agregarCampo(JPanel panel, String etiqueta, int fila, int columna, GridBagConstraints gbc) {
        gbc.gridx = columna; gbc.gridy = fila;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl, gbc);

        gbc.gridx = columna + 1;
        JTextField txt = EstiloUI.crearInput();
        txt.setPreferredSize(new Dimension(250, 35)); // <--- ¡Arreglo clave para que deje escribir bien!
        panel.add(txt, gbc);
        return txt;
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtZona.setText("");
        txtHorario.setText("");
        txtPassword.setText("");
    }
}