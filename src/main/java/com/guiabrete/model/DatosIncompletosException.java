package com.guiabrete.model;

/**
 * Se lanza cuando un campo obligatorio (Nombre, Zona, etc.) está vacío.
 */
public class DatosIncompletosException extends GuiaBreteException {
    public DatosIncompletosException(String campo) {
        super("El campo '" + campo + "' es obligatorio para la comunidad de Limón.");
    }
}