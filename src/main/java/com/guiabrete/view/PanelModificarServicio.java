package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class PanelModificarServicio extends JPanel {

    public PanelModificarServicio(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(5,1,10,10));

        JLabel titulo = new JLabel("PANEL MODIFICAR SERVICIO",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JTextField nombre = new JTextField();
        nombre.setBorder(BorderFactory.createTitledBorder("Nombre"));

        JTextArea descripcion = new JTextArea();
        descripcion.setBorder(BorderFactory.createTitledBorder("Nueva descripción"));

        JButton actualizar = new JButton("ACTUALIZAR");
        JButton cancelar = new JButton("CANCELAR");

        actualizar.setBackground(EstiloUI.VERDE);
        cancelar.setBackground(EstiloUI.VERDE);

        cancelar.addActionListener(e->ventana.cambiarVista("panelProveedor"));

        add(titulo);
        add(nombre);
        add(new JScrollPane(descripcion));
        add(actualizar);
        add(cancelar);
    }
}

