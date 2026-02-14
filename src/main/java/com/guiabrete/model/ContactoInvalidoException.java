package com.guiabrete.model;

/**
 * Se lanza si el contacto no tiene exactamente 8 dígitos.
 */
public class ContactoInvalidoException extends GuiaBreteException {
    public ContactoInvalidoException() {
        super("El contacto debe ser un número de teléfono válido de 8 dígitos.");
    }
}