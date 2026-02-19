package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class PanelProveedor extends JPanel {

    public PanelProveedor(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(5,1,10,10));

        JLabel titulo = new JLabel("PANEL DEL PROVEEDOR",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JButton add = new JButton("AÑADIR SERVICIO");
        JButton mod = new JButton("MODIFICAR SERVICIO");
        JButton eliminar = new JButton("ELIMINAR SERVICIO");
        JButton retro = new JButton("RETROCEDER");

        add.setBackground(EstiloUI.VERDE);
        mod.setBackground(EstiloUI.VERDE);
        eliminar.setBackground(EstiloUI.VERDE);
        retro.setBackground(EstiloUI.VERDE);

        add.addActionListener(e->ventana.cambiarVista("anadirServicio"));
        mod.addActionListener(e->ventana.cambiarVista("modificarServicio"));
        retro.addActionListener(e->ventana.cambiarVista("inicioProveedor"));

        add(titulo);
        add(add);
        add(mod);
        add(eliminar);
        add(retro);
    }
}

