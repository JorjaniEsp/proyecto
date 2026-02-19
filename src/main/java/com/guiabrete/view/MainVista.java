package com.guiabrete.view;

import com.guiabrete.controller.ControladorApp;
import com.guiabrete.model.Servicio;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainVista extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private ControladorApp controlador; // Referencia al controlador

    public MainVista() {
        setTitle("Sistema de Servicios - Guía Brete");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // --- AGREGAR PANELES ---
        // Asegúrate de que estas clases existen en tu paquete view
        contenedor.add(new InicioSesionPanel(this), "inicio");
        contenedor.add(new InicioSesionProveedorPanel(this), "inicioProveedor");
        contenedor.add(new RegistroVisitantePanel(this), "registroVisitante");
        contenedor.add(new RegistroProveedorPanel(this), "registroProveedor");

        // Paneles principales
        contenedor.add(new PanelPrincipalVisitante(this), "panelVisitante");
        contenedor.add(new PanelProveedor(this), "panelProveedor");

        // Paneles de gestión de servicios
        contenedor.add(new PanelAnadirServicio(this), "anadirServicio");
        contenedor.add(new PanelModificarServicio(this), "modificarServicio");
        contenedor.add(new PanelDetalleServicio(this), "detalleServicio");

        // Panel perfil (Si ya creaste la clase PanelPerfilProveedor, descomenta esta línea)
        // contenedor.add(new PanelPerfilProveedor(this), "perfilProveedor");

        add(contenedor);

        // Iniciar en la pantalla de bienvenida
        cardLayout.show(contenedor, "inicio");
    }

    // --- MÉTODOS DE NAVEGACIÓN ---

    public void cambiarVista(String nombreVista) {
        cardLayout.show(contenedor, nombreVista);
    }

    // --- CONEXIÓN CON CONTROLADOR ---

    public void setControlador(ControladorApp controlador) {
        this.controlador = controlador;
    }

    public ControladorApp getControlador() {
        return controlador;
    }

    // --- MÉTODOS DE INTERACCIÓN (Para quitar los errores del Controlador) ---

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Este método es llamado por el controlador para actualizar la lista del visitante
    public void mostrarPanelVisitante(List<Servicio> listaServicios) {
        // Obtenemos el panel (esto asume que ya fue agregado al contenedor)
        // NOTA: Para que esto funcione al 100%, PanelPrincipalVisitante necesitará un método "cargarDatos"
        // Por ahora, solo cambiamos la vista para que no de error de compilación.
        cambiarVista("panelVisitante");
    }

    // Método para mostrar servicios del proveedor (Fase 2)
    public void mostrarPanelProveedor(List<Servicio> listaServicios) {
        // Pendiente de implementar la tabla en PanelProveedor
        cambiarVista("panelProveedor");
    }

    // --- MAIN PARA PRUEBAS VISUALES ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainVista v = new MainVista();
            v.setVisible(true);
        });
    }
}