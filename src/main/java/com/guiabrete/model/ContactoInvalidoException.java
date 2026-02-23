package com.guiabrete.model;

/**
 * Excepción personalizada para validar el formato de contacto telefónico.
 * <p>Esta excepción se dispara cuando un usuario intenta registrarse o modificar
 * un servicio utilizando un número de teléfono que no cumple con el estándar
 * costarricense de <b>8 dígitos exactos</b>.</p>
 * * <p>Al ser una excepción de dominio, permite al controlador diferenciar entre
 * errores de formato de datos y errores críticos de persistencia o conectividad.</p>
 * * @author Grupo 04
 * @version 1.0
 * @see GuiaBreteException
 */
public class ContactoInvalidoException extends GuiaBreteException {

    /**
     * Construye una nueva excepción con un mensaje de error predefinido.
     * <p>El mensaje informa al usuario de la longitud requerida para que
     * la interfaz gráfica pueda mostrar la sugerencia de corrección adecuada.</p>
     */
    public ContactoInvalidoException() {
        super("El contacto debe ser un número de teléfono válido de 8 dígitos.");
    }
}