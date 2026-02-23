package com.guiabrete.model;

/**
 * Clase base para todas las excepciones personalizadas del sistema Guía-Brete.
 * <p>Esta clase extiende de {@link Exception}, lo que la convierte en una
 * "Checked Exception". Su propósito es centralizar la gestión de errores
 * de lógica de negocio y validación de datos en toda la aplicación.</p>
 * * <p>Al utilizar esta jerarquía, el controlador puede capturar múltiples tipos
 * de errores específicos (como {@link EmailInvalidoException} o {@link DatosIncompletosException})
 * mediante un solo bloque catch de la clase padre si así se desea, simplificando
 * el flujo de control.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class GuiaBreteException extends Exception {

    /**
     * Construye una nueva excepción con un mensaje de error detallado.
     * * @param message El mensaje que describe el error específico, el cual
     * será mostrado al usuario final a través de la interfaz gráfica.
     */
    public GuiaBreteException(String message) {
        super(message);
    }
}