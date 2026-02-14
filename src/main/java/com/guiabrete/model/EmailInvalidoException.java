package com.guiabrete.model;

/**
 * Se lanza si el correo no tiene un formato válido (ej: usuario@dominio.com).
 */
public class EmailInvalidoException extends GuiaBreteException {
    public EmailInvalidoException() {
        super("El correo electrónico ingresado no tiene un formato válido.");
    }
}
