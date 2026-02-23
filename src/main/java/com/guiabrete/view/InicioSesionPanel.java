package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de bienvenida y pre-autenticación de la aplicación GuiaBrete.
 * <p>Esta vista implementa una distribución de dos columnas mediante {@link GridBagLayout}:</p>
 * <ul>
 * <li><b>Panel Lateral (Izquierdo):</b> Orientado a proveedores que ya poseen una cuenta,
 * permitiendo el acceso rápido al login.</li>
 * <li><b>Panel Principal (Derecho):</b> Contiene la identidad visual (Logo) y las opciones
 * de registro para nuevos proveedores, visitantes o acceso rápido como invitado.</li>
 * </ul>
 * * @author Grupo 04
 * @version 1.0
 */
public class InicioSesionPanel extends JPanel {

    /**
     * Constructor que inicializa y estructura los componentes del panel de inicio.
     * <p>Utiliza la clase {@link EstiloUI} para garantizar la coherencia visual con la
     * paleta de colores y tipografía del proyecto.</p>
     * * @param ventana Referencia a la {@link MainVista} para gestionar la navegación entre pantallas.
     */
    public InicioSesionPanel(MainVista ventana) {
        // Estructura principal con GridBagLayout para proporciones 30/70
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // =================================================================================
        // 1. PANEL IZQUIERDO (Lateral: Acceso Proveedores existentes)
        // =================================================================================
        /* * Este panel utiliza un borde mate y el color MANZANA_50 para crear un
         * contraste sutil pero efectivo que guía al usuario recurrente.
         */
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(EstiloUI.MANZANA_50);
        panelIzquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, EstiloUI.MANZANA_500));
        panelIzquierdo.setLayout(new GridBagLayout());

        JLabel lblPregunta = new JLabel("<html><div style='text-align: center;'>¿ERES PROVEEDOR<br>Y YA TIENES<br>CUENTA?</div></html>");
        lblPregunta.setFont(EstiloUI.FONT_BOTON);
        lblPregunta.setForeground(EstiloUI.MANZANA_900);
        lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnLogin = EstiloUI.crearBoton("YA TENGO CUENTA");
        btnLogin.setBackground(EstiloUI.MANZANA_900); // Resaltado oscuro para diferenciación

        // Navegación al panel de inicio de sesión de proveedor
        btnLogin.addActionListener(e -> ventana.cambiarVista("inicioProveedor"));

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0; gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(0, 20, 20, 20);
        panelIzquierdo.add(lblPregunta, gbcLeft);

        gbcLeft.gridy = 1;
        panelIzquierdo.add(btnLogin, gbcLeft);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setLayout(new GridBagLayout());

        // Gestión del Logo con escalado suave
        JLabel logoLabel = new JLabel();
        String rutaImagen = "src/logo.jpeg";
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        if (iconoOriginal.getIconWidth() > 0) {
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(imagenEscalada));
        } else {
            logoLabel.setText("<html><h1 style='color:#36b144'>[LOGO AQUÍ]</h1></html>");
        }

        // Definición de botones de acción principal
        JButton btnRegistroProv = EstiloUI.crearBoton("QUIERO SER PROVEEDOR");
        JButton btnVisitante = EstiloUI.crearBoton("REGISTRARSE COMO VISITANTE");
        JButton btnInvitado = EstiloUI.crearBoton("ENTRAR COMO INVITADO");

        // Configuración de navegadores de vista
        btnRegistroProv.addActionListener(e -> ventana.cambiarVista("registroProveedor"));
        btnVisitante.addActionListener(e -> ventana.cambiarVista("registroVisitante"));
        btnInvitado.addActionListener(e -> {
            // Lógica condicional para invitados: Carga directa de datos si el controlador está listo
            if(ventana.getControlador() != null) ventana.getControlador().mostrarCatalogoCompleto();
            else ventana.cambiarVista("panelVisitante");
        });

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.insets = new Insets(10, 10, 10, 10);
        gbcRight.fill = GridBagConstraints.HORIZONTAL;

        gbcRight.gridy = 0;
        gbcRight.insets = new Insets(10, 10, 40, 10);
        panelDerecho.add(logoLabel, gbcRight);

        gbcRight.insets = new Insets(10, 50, 10, 50);
        gbcRight.gridy = 1; panelDerecho.add(btnRegistroProv, gbcRight);
        gbcRight.gridy = 2; panelDerecho.add(btnVisitante, gbcRight);
        gbcRight.gridy = 3; panelDerecho.add(btnInvitado, gbcRight);

        // Adición del panel lateral (proporción 30%)
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        add(panelIzquierdo, gbc);

        // Adición del panel principal (proporción 70%)
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        add(panelDerecho, gbc);
    }
}