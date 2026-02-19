package com.guiabrete.view;

import com.guiabrete.model.Proveedor; // [cite: 234]
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelPerfilProveedor extends JPanel {
    private JTextField txtNombre, txtTelefono, txtEmail, txtZona, txtHorario;
    private JButton btnGuardar, btnVolver;
    private MainVista ventana; //
    private Proveedor proveedorActual;

    public PanelPerfilProveedor(MainVista ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(EstiloUI.MANZANA_50);
        setBorder(new EmptyBorder(40, 60, 40, 60));

        initHeader();
        initFormulario();
    }

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

    private void initFormulario() {
        JPanel pnlCampos = new JPanel(new GridBagLayout());
        pnlCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- DATOS PERSONALES ---
        txtNombre = agregarCampo(pnlCampos, "NOMBRE COMPLETO:", 0, 0, gbc);
        txtEmail = agregarCampo(pnlCampos, "CORREO ELECTRÓNICO:", 1, 0, gbc);
        txtTelefono = agregarCampo(pnlCampos, "TELÉFONO / WHATSAPP:", 2, 0, gbc);

        // --- DATOS DE SERVICIO --- [cite: 75, 147]
        txtZona = agregarCampo(pnlCampos, "ZONA DE TRABAJO:", 0, 2, gbc);
        txtHorario = agregarCampo(pnlCampos, "HORARIO DE ATENCIÓN:", 1, 2, gbc);

        // --- ÁREA DE BOTONES ---
        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        pnlAcciones.setOpaque(false);

        btnVolver = EstiloUI.crearBoton("VOLVER");
        btnVolver.setBackground(EstiloUI.MANZANA_900);

        btnGuardar = EstiloUI.crearBoton("GUARDAR PERFIL");

        pnlAcciones.add(btnVolver);
        pnlAcciones.add(btnGuardar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(40, 0, 0, 0);
        pnlCampos.add(pnlAcciones, gbc);

        add(pnlCampos, BorderLayout.CENTER);
    }

    private JTextField agregarCampo(JPanel panel, String etiqueta, int fila, int columna, GridBagConstraints gbc) {
        gbc.gridx = columna; gbc.gridy = fila;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(EstiloUI.MANZANA_950);
        panel.add(lbl, gbc);

        gbc.gridx = columna + 1;
        JTextField txt = EstiloUI.crearInput();
        panel.add(txt, gbc);
        return txt;
    }

    public void cargarDatosPerfil(Proveedor p) {
        this.proveedorActual = p;
        txtNombre.setText(p.getNombre());
        txtEmail.setText(p.getEmail());
        txtTelefono.setText(p.getTelefono());
        txtZona.setText(p.getZona());
        txtHorario.setText(p.getHorario());
        txtEmail.setEnabled(false); // No se permite cambiar el ID [cite: 154]
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtZona.setText("");
        txtHorario.setText("");
    }

    // Getters para Angely (Controlador) [cite: 236]
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnVolver() { return btnVolver; }

    // Getters para extraer la información actualizada [cite: 75, 204]
    public String getNombre() { return txtNombre.getText(); }
    public String getTelefono() { return txtTelefono.getText(); }
    public String getZona() { return txtZona.getText(); }
    public String getHorario() { return txtHorario.getText(); }
}