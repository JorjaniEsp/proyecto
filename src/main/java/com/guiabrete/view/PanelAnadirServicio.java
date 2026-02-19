package com.guiabrete.view;

import com.guiabrete.model.Categoria;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelAnadirServicio extends JPanel {
    private JTextField txtNombre, txtZona, txtHorario, txtContacto;
    private JComboBox<Categoria> cbCategoria;
    private JTextArea txtDescripcion;
    private JButton btnGuardar, btnCancelar;
    private MainVista ventana;

    public PanelAnadirServicio(MainVista ventana) {
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

        JLabel lblTitulo = new JLabel("PUBLICAR NUEVO SERVICIO");
        lblTitulo.setFont(EstiloUI.FONT_TITULO);
        lblTitulo.setForeground(EstiloUI.MANZANA_900);

        JLabel lblPaso = new JLabel("Complete los datos de su oficio", SwingConstants.RIGHT);
        lblPaso.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblPaso.setForeground(EstiloUI.MANZANA_600);

        pnlHeader.add(lblTitulo, BorderLayout.WEST);
        pnlHeader.add(lblPaso, BorderLayout.EAST);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, EstiloUI.MANZANA_100));

        add(pnlHeader, BorderLayout.NORTH);
    }

    private void initFormulario() {
        JPanel pnlCampos = new JPanel(new GridBagLayout());
        pnlCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Ayuda a que los campos se expandan bien

        // --- FILA 1: NOMBRE Y CATEGORÍA ---
        gbc.gridx = 0; gbc.gridy = 0;
        pnlCampos.add(new JLabel("NOMBRE DEL SERVICIO:"), gbc);

        gbc.gridx = 1;
        txtNombre = EstiloUI.crearInput();
        txtNombre.setPreferredSize(new Dimension(200, 35)); // <-- ¡Soluciona el campo aplastado!
        pnlCampos.add(txtNombre, gbc);

        gbc.gridx = 2;
        pnlCampos.add(new JLabel("CATEGORÍA:"), gbc);

        gbc.gridx = 3;
        cbCategoria = new JComboBox<>(Categoria.values());
        cbCategoria.setBackground(EstiloUI.MANZANA_100);
        cbCategoria.setFont(EstiloUI.FONT_TEXTO);
        cbCategoria.setPreferredSize(new Dimension(200, 35));
        pnlCampos.add(cbCategoria, gbc);

        // --- FILA 2: ZONA Y CONTACTO ---
        gbc.gridx = 0; gbc.gridy = 1;
        pnlCampos.add(new JLabel("ZONA DE TRABAJO:"), gbc);

        gbc.gridx = 1;
        txtZona = EstiloUI.crearInput();
        txtZona.setPreferredSize(new Dimension(200, 35));
        txtZona.setToolTipText("Tu zona se asignará automáticamente de tu perfil.");
        pnlCampos.add(txtZona, gbc);

        gbc.gridx = 2;
        pnlCampos.add(new JLabel("TELÉFONO CONTACTO:"), gbc);

        gbc.gridx = 3;
        txtContacto = EstiloUI.crearInput();
        txtContacto.setPreferredSize(new Dimension(200, 35));
        txtContacto.setToolTipText("Tu teléfono se asignará automáticamente de tu perfil.");
        pnlCampos.add(txtContacto, gbc);

        // --- FILA 3: HORARIO (Ocupa dos columnas) ---
        gbc.gridx = 0; gbc.gridy = 2;
        pnlCampos.add(new JLabel("HORARIO DE TRABAJO:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        txtHorario = EstiloUI.crearInput();
        txtHorario.setPreferredSize(new Dimension(200, 35));
        txtHorario.setToolTipText("Tu horario se asignará automáticamente de tu perfil.");
        pnlCampos.add(txtHorario, gbc);

        // --- FILA 4: DESCRIPCIÓN (Área de texto grande) ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        pnlCampos.add(new JLabel("DESCRIPCIÓN:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(EstiloUI.MANZANA_500));
        txtDescripcion.setBackground(EstiloUI.MANZANA_100);
        txtDescripcion.setFont(EstiloUI.FONT_TEXTO);
        pnlCampos.add(new JScrollPane(txtDescripcion), gbc);

        // --- BOTONES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        pnlBotones.setOpaque(false);

        btnCancelar = EstiloUI.crearBoton("CANCELAR");
        btnCancelar.setBackground(EstiloUI.MANZANA_900);

        btnGuardar = EstiloUI.crearBoton("PUBLICAR SERVICIO");

        // ==========================================
        // CABLEADO: Los botones cobran vida
        // ==========================================

        // 1. Botón Cancelar
        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            ventana.cambiarVista("panelProveedor"); // Regresa al dashboard de Angely
        });

        // 2. Botón Guardar / Publicar
        // 2. Botón Guardar / Publicar
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String desc = txtDescripcion.getText().trim();
            String zona = txtZona.getText().trim();
            String horario = txtHorario.getText().trim();
            String contacto = txtContacto.getText().trim();
            Categoria cat = (Categoria) cbCategoria.getSelectedItem();

            // Validamos que no deje campos críticos en blanco
            if (nombre.isEmpty() || desc.isEmpty() || zona.isEmpty() || horario.isEmpty() || contacto.isEmpty()) {
                ventana.mostrarMensaje("Por favor, complete todos los campos para publicar el servicio.");
                return;
            }

            if (ventana.getControlador() != null) {
                // Enviamos TODOS los datos al cerebro
                ventana.getControlador().anadirServicio(nombre, desc, cat, zona, horario, contacto);
                limpiarCampos();
            }
        });

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnGuardar);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(30, 0, 0, 0);
        pnlCampos.add(pnlBotones, gbc);

        add(pnlCampos, BorderLayout.CENTER);
    }

    // --- MÉTODOS DE LIMPIEZA ---
    public void limpiarCampos() {
        txtNombre.setText("");
        txtZona.setText("");
        txtHorario.setText("");
        txtContacto.setText("");
        txtDescripcion.setText("");
        if(cbCategoria.getItemCount() > 0) {
            cbCategoria.setSelectedIndex(0);
        }
    }
}
