package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class InicioSesionProveedorPanel extends JPanel {

    public InicioSesionProveedorPanel(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(5,1,10,10));

        JLabel titulo = new JLabel("INICIO SESIÓN - PROVEEDOR",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JTextField correo = new JTextField();
        correo.setBorder(BorderFactory.createTitledBorder("Correo"));

        JPasswordField pass = new JPasswordField();
        pass.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        JButton iniciar = new JButton("INICIAR SESIÓN");
        JButton retroceder = new JButton("RETROCEDER");

        iniciar.setBackground(EstiloUI.VERDE);
        retroceder.setBackground(EstiloUI.VERDE);

        iniciar.addActionListener(e->ventana.cambiarVista("panelProveedor"));
        retroceder.addActionListener(e->ventana.cambiarVista("inicio"));

        add(titulo);
        add(correo);
        add(pass);
        add(iniciar);
        add(retroceder);
    }
}

