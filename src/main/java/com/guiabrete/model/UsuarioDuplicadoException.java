package com.guiabrete.model;

/**
 * Excepción lanzada al detectar un intento de registro con un identificador ya existente.
 * <p>En el ecosistema de Guía-Brete, el correo electrónico funciona como la llave primaria
 * (Unique Key) de los usuarios. Esta excepción se dispara durante el proceso de registro
 * cuando el sistema valida que el email proporcionado ya pertenece a otro perfil activo.</p>
 * * <p>Su implementación ayuda a prevenir la redundancia de datos y garantiza que cada
 * proveedor o visitante tenga una identidad digital única y rastreable.</p>
 * * @author Grupo 04
 * @version 1.0
 * @see GuiaBreteException
 */
public class UsuarioDuplicadoException extends GuiaBreteException {

    /**
     * Construye una nueva excepción especificando el correo en conflicto.
     * <p>El mensaje generado orienta al usuario hacia dos soluciones posibles:
     * utilizar una cuenta de correo alternativa o dirigirse a la pantalla de inicio de sesión.</p>
     * * @param email La dirección de correo electrónico que causó el conflicto de duplicidad.
     */
    public UsuarioDuplicadoException(String email) {
        super("El correo '" + email + "' ya está registrado. Intente con otro o inicie sesión.");
    }
}