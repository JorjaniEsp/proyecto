package com.guiabrete.view;

import javax.swing.*;
import java.awt.*;

public class InicioSesionPanel extends JPanel {

    public InicioSesionPanel(MainVista ventana) {
        // Usamos GridBagLayout en el panel principal para dividir en 2 columnas (Izquierda 30%, Derecha 70%)
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE); // Fondo base

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Estirar para llenar todo el alto
        gbc.weighty = 1.0; // Ocupar toda la altura vertical

        // =================================================================================
        // 1. PANEL IZQUIERDO (Lateral: "¿Ya tienes cuenta?")
        // =================================================================================
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(EstiloUI.MANZANA_50); // Un verde muy suave para diferenciar
        panelIzquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, EstiloUI.MANZANA_500)); // Línea divisoria a la derecha
        panelIzquierdo.setLayout(new GridBagLayout()); // Centrar contenido verticalmente

        // Contenido Izquierdo
        JLabel lblPregunta = new JLabel("<html><div style='text-align: center;'>¿ERES PROVEEDOR<br>Y YA TIENES<br>CUENTA?</div></html>");
        lblPregunta.setFont(EstiloUI.FONT_BOTON); // Fuente negrita mediana
        lblPregunta.setForeground(EstiloUI.MANZANA_900);
        lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnLogin = EstiloUI.crearBoton("YA TENGO CUENTA");
        // Hacemos este botón un poco diferente (más oscuro) para resaltar
        btnLogin.setBackground(EstiloUI.MANZANA_900);

        // Acción: Ir al Login de Proveedor
        btnLogin.addActionListener(e -> ventana.cambiarVista("inicioProveedor"));

        // Agregamos elementos al panel izquierdo
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0; gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(0, 20, 20, 20); // Margen inferior
        panelIzquierdo.add(lblPregunta, gbcLeft);

        gbcLeft.gridy = 1;
        panelIzquierdo.add(btnLogin, gbcLeft);

        // =================================================================================
        // 2. PANEL DERECHO (Principal: Logo y Opciones)
        // =================================================================================
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setLayout(new GridBagLayout()); // Centrar contenido

        // A. LOGO
        JLabel logoLabel = new JLabel();
        // Carga de imagen segura
        String rutaImagen = "src/logo.jpeg";
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        if (iconoOriginal.getIconWidth() > 0) {
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(imagenEscalada));
        } else {
            logoLabel.setText("<html><h1 style='color:#36b144'>[LOGO AQUÍ]</h1></html>");
        }

        // B. BOTONES PRINCIPALES (Según tu diagrama image_105e45.png)
        // Nota: En el diagrama "Iniciar proveedor" está en el centro. Como a la izquierda ya pusimos el Login ("Ya tengo cuenta"),
        // asumimos que este botón central es para REGISTRARSE (Nuevo proveedor).
        JButton btnRegistroProv = EstiloUI.crearBoton("QUIERO SER PROVEEDOR");
        JButton btnVisitante = EstiloUI.crearBoton("REGISTRARSE COMO VISITANTE");
        JButton btnInvitado = EstiloUI.crearBoton("ENTRAR COMO INVITADO");

        // Acciones
        btnRegistroProv.addActionListener(e -> ventana.cambiarVista("registroProveedor"));
        btnVisitante.addActionListener(e -> ventana.cambiarVista("registroVisitante")); // O panelVisitante directo si prefieres
        btnInvitado.addActionListener(e -> {
            // Cargar catálogo si hay controlador
            if(ventana.getControlador() != null) ventana.getControlador().mostrarCatalogoCompleto();
            else ventana.cambiarVista("panelVisitante");
        });

        // Layout del Panel Derecho (Uno debajo del otro con separación)
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.insets = new Insets(10, 10, 10, 10); // Espacio entre botones
        gbcRight.fill = GridBagConstraints.HORIZONTAL; // Que los botones tengan el mismo ancho

        // Agregamos en orden
        gbcRight.gridy = 0;
        gbcRight.insets = new Insets(10, 10, 40, 10); // Más espacio bajo el logo
        panelDerecho.add(logoLabel, gbcRight);

        gbcRight.insets = new Insets(10, 50, 10, 50); // Margen lateral para los botones
        gbcRight.gridy = 1; panelDerecho.add(btnRegistroProv, gbcRight);
        gbcRight.gridy = 2; panelDerecho.add(btnVisitante, gbcRight);
        gbcRight.gridy = 3; panelDerecho.add(btnInvitado, gbcRight);

        // =================================================================================
        // 3. UNIÓN DE PANELES AL CONTENEDOR PRINCIPAL
        // =================================================================================

        // Columna 0: Panel Izquierdo (Ocupa 30% del ancho)
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        add(panelIzquierdo, gbc);

        // Columna 1: Panel Derecho (Ocupa 70% del ancho)
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        add(panelDerecho, gbc);
    }
}
