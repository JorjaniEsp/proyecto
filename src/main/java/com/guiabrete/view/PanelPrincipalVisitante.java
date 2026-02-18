package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipalVisitante extends JPanel {

    public PanelPrincipalVisitante(MainVista ventana) {

        setBackground(EstiloUI.NEGRO);
        setLayout(new BorderLayout(10,10));


        JLabel titulo = new JLabel("PANEL PRINCIPAL VISITANTE", SwingConstants.CENTER);
        titulo.setForeground(EstiloUI.VERDE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);


        JPanel centro = new JPanel();
        centro.setBackground(EstiloUI.NEGRO);
        centro.setLayout(new BorderLayout(10,10));


        JPanel filtros = new JPanel(new GridLayout(1,3,10,10));
        filtros.setBackground(EstiloUI.NEGRO);

        JButton buscarZona = new JButton("BUSCAR POR ZONA");
        JButton buscarCategoria = new JButton("BUSCAR POR CATEGORIA");
        JButton limpiar = new JButton("LIMPIAR FILTROS");

        buscarZona.setBackground(EstiloUI.VERDE);
        buscarCategoria.setBackground(EstiloUI.VERDE);
        limpiar.setBackground(EstiloUI.VERDE);

        filtros.add(buscarZona);
        filtros.add(buscarCategoria);
        filtros.add(limpiar);

        centro.add(filtros, BorderLayout.NORTH);


        JPanel servicios = new JPanel(new GridLayout(1,2,20,20));
        servicios.setBackground(EstiloUI.NEGRO);
        servicios.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        servicios.add(crearTarjetaServicio("SERVICIO A", ventana));
        servicios.add(crearTarjetaServicio("SERVICIO B", ventana));

        centro.add(servicios, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);


        JButton retroceder = new JButton("RETROCEDER");
        retroceder.setBackground(EstiloUI.VERDE);
        retroceder.addActionListener(e -> ventana.cambiarVista("inicio"));

        JPanel sur = new JPanel();
        sur.setBackground(EstiloUI.NEGRO);
        sur.add(retroceder);

        add(sur, BorderLayout.SOUTH);
    }


    private JPanel crearTarjetaServicio(String nombreServicio, MainVista ventana) {

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(Color.BLACK);
        tarjeta.setBorder(BorderFactory.createLineBorder(EstiloUI.VERDE,2));

        JLabel nombre = new JLabel(nombreServicio, SwingConstants.CENTER);
        nombre.setForeground(EstiloUI.VERDE);
        nombre.setFont(new Font("Arial", Font.BOLD, 16));

        JButton ver = new JButton("VER DETALLE");
        ver.setBackground(EstiloUI.VERDE);

        ver.addActionListener(e -> ventana.cambiarVista("detalleServicio"));

        tarjeta.add(nombre, BorderLayout.CENTER);
        tarjeta.add(ver, BorderLayout.SOUTH);

        return tarjeta;
    }
}
