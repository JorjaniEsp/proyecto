package com.guiabrete.model;

/**
 * Excepción de validación para el formato de correo electrónico.
 * <p>Esta clase se dispara cuando el sistema detecta que una cadena de texto
 * ingresada como email no cumple con el estándar sintáctico requerido
 * (ej: falta el símbolo '@', el dominio es inexistente o contiene caracteres prohibidos).</p>
 * * <p>Su propósito es prevenir que lleguen datos corruptos a la capa de persistencia,
 * asegurando que cada usuario registrado tenga un canal de comunicación formal válido.</p>
 * * @author Grupo 04
 * @version 1.0
 * @see GuiaBreteException
 */
public class EmailInvalidoException extends GuiaBreteException {

    /**
     * Construye una nueva excepción con un mensaje de error estandarizado.
     * <p>Este mensaje es el que capturará el controlador para mostrar en los
     * cuadros de diálogo de la interfaz gráfica cuando el usuario cometa un error tipográfico.</p>
     */
    public EmailInvalidoException() {
        super("El correo electrónico ingresado no tiene un formato válido.");
    }
}