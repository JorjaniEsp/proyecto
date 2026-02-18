package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class MainVista extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;

    public MainVista() {

        setTitle("Sistema de Servicios");
        setSize(1000,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        contenedor.add(new InicioSesionPanel(this), "inicio");
        contenedor.add(new InicioSesionProveedorPanel(this), "inicioProveedor");
        contenedor.add(new RegistroVisitantePanel(this), "registroVisitante");
        contenedor.add(new RegistroProveedorPanel(this), "registroProveedor");
        contenedor.add(new PanelPrincipalVisitante(this), "panelVisitante");
        contenedor.add(new PanelProveedor(this), "panelProveedor");
        contenedor.add(new PanelAnadirServicio(this), "anadirServicio");
        contenedor.add(new PanelModificarServicio(this), "modificarServicio");
        contenedor.add(new PanelDetalleServicio(this), "detalleServicio");

        add(contenedor);
        cardLayout.show(contenedor,"inicio");

        setVisible(true);
    }

    public void cambiarVista(String nombre){
        cardLayout.show(contenedor,nombre);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new MainVista());
    }
}
