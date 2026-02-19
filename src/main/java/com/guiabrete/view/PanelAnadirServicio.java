package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class PanelAnadirServicio extends JPanel {

    public PanelAnadirServicio(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(6,1,10,10));

        JLabel titulo = new JLabel("PANEL AÑADIR SERVICIO",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JTextField nombre = new JTextField();
        nombre.setBorder(BorderFactory.createTitledBorder("Nombre servicio"));

        JTextArea descripcion = new JTextArea();
        descripcion.setBorder(BorderFactory.createTitledBorder("Descripción"));

        JButton registrar = new JButton("REGISTRAR SERVICIO");
        JButton retro = new JButton("RETROCEDER");

        registrar.setBackground(EstiloUI.VERDE);
        retro.setBackground(EstiloUI.VERDE);

        retro.addActionListener(e->ventana.cambiarVista("panelProveedor"));

        add(titulo);
        add(nombre);
        add(new JScrollPane(descripcion));
        add(registrar);
        add(retro);
    }
}

