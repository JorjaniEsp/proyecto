package com.guiabrete.view;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EstiloUI {
    // --- PALETA LIMÓN (Tus colores) ---
    public static final Color MANZANA_50  = new Color(0xf2fbf3); // Fondo General
    public static final Color MANZANA_100 = new Color(0xe0f8e2); // Entradas de texto
    public static final Color MANZANA_500 = new Color(0x36b144); // Botones Principal
    public static final Color MANZANA_600 = new Color(0x2ba139); // Hover Botones
    public static final Color MANZANA_900 = new Color(0x1c4b23); // Textos / Títulos
    public static final Color MANZANA_950 = new Color(0x0a290f); // Bordes oscuros

    // --- FUENTES (Para que se vea moderno) ---
    public static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_TEXTO = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    // --- UTILIDADES ---
    // Método para crear botones estandarizados
    public static JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(MANZANA_500);
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BOTON);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding interno
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Método para inputs estandarizados
    public static JTextField crearInput() {
        JTextField txt = new JTextField();
        txt.setBackground(MANZANA_100);
        txt.setForeground(MANZANA_950);
        txt.setFont(FONT_TEXTO);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MANZANA_500, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return txt;
    }
}