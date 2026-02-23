package com.guiabrete.view;

import com.guiabrete.controller.ControladorApp;
import com.guiabrete.model.Servicio;
import com.guiabrete.model.Proveedor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana principal de la aplicación Guía Brete.
 * <p>Esta clase funciona como el contenedor principal (Frame) que gestiona la navegación
 * entre las diferentes pantallas del sistema utilizando {@link CardLayout}.</p>
 * <p>Actúa como el punto de entrada para la visualización de datos reales provenientes
 * del controlador, centralizando el acceso a los paneles de visitantes, proveedores,
 * registros y detalles de servicio.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class MainVista extends JFrame {

    /** Gestor de diseño para intercambiar entre diferentes paneles. */
    private CardLayout cardLayout;

    /** Contenedor principal donde se apilan todos los paneles de la aplicación. */
    private JPanel contenedor;

    /** Referencia al controlador para procesar la lógica de negocio desde la vista. */
    private ControladorApp controlador;

    // Paneles dinámicos que requieren actualización constante de datos
    private PanelPrincipalVisitante panelVisitante;
    private PanelDetalleServicio panelDetalle;
    private PanelProveedor panelProveedor;
    private PanelPerfilProveedor panelPerfil;

    /**
     * Constructor que inicializa el JFrame principal y configura el sistema de navegación.
     * <p>Carga tanto los paneles estáticos (formularios de registro/login) como los
     * paneles dinámicos (dashboard de usuario/catálogo) en el {@code CardLayout}.</p>
     */
    public MainVista() {
        setTitle("Sistema de Servicios - Guía Brete");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // 1. Registro de Paneles Estáticos
        contenedor.add(new InicioSesionPanel(this), "inicio");
        contenedor.add(new InicioSesionProveedorPanel(this), "inicioProveedor");
        contenedor.add(new RegistroVisitantePanel(this), "registroVisitante");
        contenedor.add(new RegistroProveedorPanel(this), "registroProveedor");
        contenedor.add(new PanelAnadirServicio(this), "anadirServicio");
        contenedor.add(new PanelModificarServicio(this), "modificarServicio");

        // 2. Inicialización de Paneles Dinámicos
        panelVisitante = new PanelPrincipalVisitante(this);
        contenedor.add(panelVisitante, "panelVisitante");

        panelDetalle = new PanelDetalleServicio(this);
        contenedor.add(panelDetalle, "detalleServicio");

        panelProveedor = new PanelProveedor(this);
        contenedor.add(panelProveedor, "panelProveedor");

        panelPerfil = new PanelPerfilProveedor(this);
        contenedor.add(panelPerfil, "perfilProveedor");

        add(contenedor);
        cardLayout.show(contenedor, "inicio");
    }

    /**
     * Alterna la visualización del contenedor hacia el panel especificado.
     * @param nombreVista El nombre identificador de la vista a mostrar.
     */
    public void cambiarVista(String nombreVista) {
        cardLayout.show(contenedor, nombreVista);
    }

    /**
     * Establece el controlador de la aplicación.
     * @param controlador Instancia del {@link ControladorApp}.
     */
    public void setControlador(ControladorApp controlador) {
        this.controlador = controlador;
    }

    /**
     * Obtiene el controlador asociado a la vista.
     * @return El controlador principal.
     */
    public ControladorApp getControlador() {
        return controlador;
    }

    /**
     * Muestra un cuadro de diálogo emergente con un mensaje para el usuario.
     * @param mensaje Texto a mostrar en la alerta.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Actualiza el catálogo de servicios en el panel de visitantes y cambia la vista.
     * @param listaServicios Lista de objetos {@link Servicio} a renderizar.
     */
    public void mostrarPanelVisitante(List<Servicio> listaServicios) {
        if (panelVisitante != null) {
            panelVisitante.cargarServicios(listaServicios);
        }
        cambiarVista("panelVisitante");
    }

    /**
     * Carga la información de un servicio específico en el panel de detalles y lo muestra.
     * @param servicio El objeto {@link Servicio} que se desea inspeccionar.
     */
    public void mostrarDetalleServicio(Servicio servicio) {
        if (panelDetalle != null) {
            panelDetalle.mostrarDetalle(servicio);
        }
        cambiarVista("detalleServicio");
    }

    /**
     * Actualiza el panel de gestión del proveedor con sus servicios propios.
     * @param listaServicios Lista de servicios pertenecientes al proveedor logueado.
     */
    public void mostrarPanelProveedor(List<Servicio> listaServicios) {
        if (panelProveedor != null) panelProveedor.cargarMisServicios(listaServicios);
        cambiarVista("panelProveedor");
    }

    /**
     * Carga la información del perfil de un proveedor en el panel correspondiente.
     * @param p El objeto {@link Proveedor} con los datos de perfil.
     */
    public void mostrarPerfilProveedor(Proveedor p) {
        if (panelPerfil != null) panelPerfil.cargarDatosPerfil(p);
        cambiarVista("perfilProveedor");
    }

    /**
     * Método de entrada principal para ejecutar la interfaz gráfica.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainVista().setVisible(true));
    }
}