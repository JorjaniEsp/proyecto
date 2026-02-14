package com.guiabrete.model;

/**
 * Se lanza cuando se intenta registrar un correo que ya existe en el sistema.
 */
public class UsuarioDuplicadoException extends GuiaBreteException {
    public UsuarioDuplicadoException(String email) {
        super("El correo '" + email + "' ya está registrado. Intente con otro o inicie sesión.");
    }
}