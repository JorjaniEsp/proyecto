package com.guiabrete.view;

import com.guiabrete.controller.ControladorApp;
import com.guiabrete.model.Servicio;
import com.guiabrete.model.Proveedor; // Importante para perfil

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainVista extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private ControladorApp controlador;

    // --- REFERENCIAS A PANELES DINÁMICOS ---
    // Necesitamos esto para poder decirles "¡Muestra estos datos!"
    private PanelPrincipalVisitante panelVisitante;
    private PanelDetalleServicio panelDetalle;
    private PanelProveedor panelProveedor;
    private PanelPerfilProveedor panelPerfil;

    public MainVista() {
        setTitle("Sistema de Servicios - Guía Brete");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // 1. Paneles Estáticos (No cambian mucho)
        contenedor.add(new InicioSesionPanel(this), "inicio");
        contenedor.add(new InicioSesionProveedorPanel(this), "inicioProveedor");
        contenedor.add(new RegistroVisitantePanel(this), "registroVisitante");
        contenedor.add(new RegistroProveedorPanel(this), "registroProveedor");
        contenedor.add(new PanelAnadirServicio(this), "anadirServicio");
        contenedor.add(new PanelModificarServicio(this), "modificarServicio");

        // 2. Paneles Dinámicos (Los creamos y guardamos en variable)
        panelVisitante = new PanelPrincipalVisitante(this);
        contenedor.add(panelVisitante, "panelVisitante");

        panelDetalle = new PanelDetalleServicio(this);
        contenedor.add(panelDetalle, "detalleServicio");

        panelProveedor = new PanelProveedor(this);
        contenedor.add(panelProveedor, "panelProveedor");

        // Si ya tienes el perfil:
        panelPerfil = new PanelPerfilProveedor(this);
        contenedor.add(panelPerfil, "perfilProveedor");

        add(contenedor);
        cardLayout.show(contenedor, "inicio");
    }

    // --- MÉTODOS DE NAVEGACIÓN ---
    public void cambiarVista(String nombreVista) {
        cardLayout.show(contenedor, nombreVista);
    }

    public void setControlador(ControladorApp controlador) {
        this.controlador = controlador;
    }

    public ControladorApp getControlador() {
        return controlador;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // --- MÉTODOS DE DATOS (Aquí está la magia) ---

    // 1. Mostrar Catálogo con datos reales
    public void mostrarPanelVisitante(List<Servicio> listaServicios) {
        if (panelVisitante != null) {
            panelVisitante.cargarServicios(listaServicios);
        }
        cambiarVista("panelVisitante");
    }

    // 2. Mostrar Detalle (Soluciona el error de tu imagen)
    public void mostrarDetalleServicio(Servicio servicio) {
        if (panelDetalle != null) {
            panelDetalle.mostrarDetalle(servicio);
        }
        cambiarVista("detalleServicio");
    }

    // 3. Mostrar Dashboard Proveedor
    public void mostrarPanelProveedor(List<Servicio> listaServicios) {
        if (panelProveedor != null) panelProveedor.cargarMisServicios(listaServicios);
        cambiarVista("panelProveedor");
    }


    public void mostrarPerfilProveedor(Proveedor p) {
        if (panelPerfil != null) panelPerfil.cargarDatosPerfil(p);
        cambiarVista("perfilProveedor");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainVista().setVisible(true));
    }
}