package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class PanelDetalleServicio extends JPanel {

    public PanelDetalleServicio(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(6,1,10,10));

        JLabel titulo = new JLabel("DETALLE SERVICIO",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JLabel proveedor = new JLabel("Nombre proveedor");
        proveedor.setForeground(EstiloUI.VERDE);

        JLabel zona = new JLabel("Zona de trabajo");
        zona.setForeground(EstiloUI.VERDE);

        JLabel descripcion = new JLabel("Descripción del servicio");
        descripcion.setForeground(EstiloUI.VERDE);

        JButton contactar = new JButton("CONTACTAR");
        JButton retro = new JButton("RETROCEDER");

        contactar.setBackground(EstiloUI.VERDE);
        retro.setBackground(EstiloUI.VERDE);

        retro.addActionListener(e->ventana.cambiarVista("panelVisitante"));

        add(titulo);
        add(proveedor);
        add(zona);
        add(descripcion);
        add(contactar);
        add(retro);
    }
}

