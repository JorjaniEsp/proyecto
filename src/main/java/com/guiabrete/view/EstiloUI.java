package com.guiabrete.view;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clase de utilidad encargada de centralizar el estilo visual de la interfaz gráfica.
 * <p>Define la paleta de colores corporativa ("Manzana"), las tipografías estándar
 * y proporciona métodos estáticos para la creación de componentes Swing con un estilo
 * predefinido y consistente.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class EstiloUI {

    /** Color de fondo general (Verde muy claro). */
    public static final Color MANZANA_50  = new Color(0xf2fbf3);
    /** Color para campos de entrada de texto. */
    public static final Color MANZANA_100 = new Color(0xe0f8e2);
    /** Color principal para botones y elementos destacados. */
    public static final Color MANZANA_500 = new Color(0x36b144);
    /** Color para efectos de hover en botones. */
    public static final Color MANZANA_600 = new Color(0x2ba139);
    /** Color para textos principales y títulos. */
    public static final Color MANZANA_900 = new Color(0x1c4b23);
    /** Color para bordes y textos de alto contraste. */
    public static final Color MANZANA_950 = new Color(0x0a290f);

    /** Fuente para títulos principales (Tamaño 24, Negrita). */
    public static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    /** Fuente para subtítulos de sección (Tamaño 18, Negrita). */
    public static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    /** Fuente para cuerpo de texto estándar (Tamaño 14, Plano). */
    public static final Font FONT_TEXTO = new Font("Segoe UI", Font.PLAIN, 14);
    /** Fuente específica para el texto dentro de botones. */
    public static final Font FONT_BOTON = new Font("Segoe UI", Font.BOLD, 14);
    /** Fuente utilizada en campos de entrada de datos. */
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);

    /**
     * Crea un botón estilizado con los colores y fuentes de la aplicación.
     * <p>El botón incluye un cursor de mano, elimina el borde de enfoque y
     * aplica un margen interno (padding) para mejorar la estética.</p>
     * * @param texto El texto que se mostrará en el botón.
     * @return Un objeto {@link JButton} configurado con el estilo "Manzana".
     */
    public static JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(MANZANA_500);
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BOTON);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Crea un campo de texto (JTextField) con el estilo visual estándar.
     * <p>Incluye un borde compuesto con una línea de color {@code MANZANA_500}
     * y un margen interno para que el texto no toque los bordes.</p>
     * * @return Un objeto {@link JTextField} estilizado.
     */
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