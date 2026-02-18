package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class RegistroVisitantePanel extends JPanel {

    public RegistroVisitantePanel(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(5,1,10,10));

        JLabel titulo = new JLabel("PANEL DE REGISTRO VISITANTE",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JTextField nombre = new JTextField();
        nombre.setBorder(BorderFactory.createTitledBorder("Nombre"));

        JTextField contacto = new JTextField();
        contacto.setBorder(BorderFactory.createTitledBorder("Contacto"));

        JButton registrar = new JButton("REGISTRARSE");
        JButton retroceder = new JButton("RETROCEDER");

        registrar.setBackground(EstiloUI.VERDE);
        retroceder.setBackground(EstiloUI.VERDE);

        retroceder.addActionListener(e->ventana.cambiarVista("inicio"));

        add(titulo);
        add(nombre);
        add(contacto);
        add(registrar);
        add(retroceder);
    }
}
