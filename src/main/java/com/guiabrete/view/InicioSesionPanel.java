package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class InicioSesionPanel extends JPanel {

    public InicioSesionPanel(MainVista ventana){

        setBackground(EstiloUI.NEGRO);
        setLayout(new GridLayout(6,1,10,10));

        JLabel titulo = new JLabel("INICIO SESIÓN - VISITANTE",SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);

        JTextField correo = new JTextField();
        correo.setBorder(BorderFactory.createTitledBorder("Ingrese su correo"));

        JPasswordField pass = new JPasswordField();
        pass.setBorder(BorderFactory.createTitledBorder("Ingrese su contraseña"));

        JButton iniciar = new JButton("INICIAR SESIÓN");
        JButton registrarse = new JButton("REGISTRARSE");
        JButton proveedor = new JButton("ERES PROVEEDOR?");

        iniciar.setBackground(EstiloUI.VERDE);
        registrarse.setBackground(EstiloUI.VERDE);
        proveedor.setBackground(EstiloUI.VERDE);

        iniciar.addActionListener(e->ventana.cambiarVista("panelVisitante"));
        registrarse.addActionListener(e->ventana.cambiarVista("registroVisitante"));
        proveedor.addActionListener(e->ventana.cambiarVista("inicioProveedor"));

        add(titulo);
        add(correo);
        add(pass);
        add(iniciar);
        add(registrarse);
        add(proveedor);
    }
}

