package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class RegistroProveedorPanel extends JPanel {

    public RegistroProveedorPanel(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(6,1,10,10));

        JLabel titulo = new JLabel("PANEL DE REGISTRO PROVEEDOR",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JTextField nombre = new JTextField();
        nombre.setBorder(BorderFactory.createTitledBorder("Nombre"));

        JTextField contacto = new JTextField();
        contacto.setBorder(BorderFactory.createTitledBorder("Contacto"));

        JTextField zona = new JTextField();
        zona.setBorder(BorderFactory.createTitledBorder("Zona de trabajo"));

        JTextField horario = new JTextField();
        horario.setBorder(BorderFactory.createTitledBorder("Horario de trabajo"));

        JButton registrar = new JButton("REGISTRARSE");
        JButton retroceder = new JButton("RETROCEDER");

        registrar.setBackground(EstiloUI.VERDE);
        retroceder.setBackground(EstiloUI.VERDE);

        retroceder.addActionListener(e->ventana.cambiarVista("inicioProveedor"));

        add(titulo);
        add(nombre);
        add(contacto);
        add(zona);
        add(horario);
        add(registrar);
        add(retroceder);
    }
}

