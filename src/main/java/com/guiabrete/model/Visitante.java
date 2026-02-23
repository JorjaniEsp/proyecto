package com.guiabrete.model;

/**
 * Representa a un usuario de tipo Visitante dentro del sistema GuiaBrete.
 * <p>El Visitante es un usuario que consume el catálogo de servicios. Esta clase
 * no añade atributos adicionales a la clase base {@link Usuario}, pero define
 * su propio formato de persistencia mediante la sobreescritura del método {@code toString()}.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class Visitante extends Usuario {

    /**
     * Constructor por defecto de la clase Visitante.
     */
    public Visitante() {
    }

    /**
     * Constructor con parámetros para inicializar un Visitante.
     * <p>Delega la validación de los datos (nombre, email, contraseña) a la clase padre.</p>
     * * @param idUsuario Identificador único del usuario.
     * @param nombre Nombre completo del visitante.
     * @param telefono Número de contacto.
     * @param email Correo electrónico (validado y normalizado por la superclase).
     * @param contrasenia Clave de acceso al sistema.
     * @throws GuiaBreteException Si los datos proporcionados no cumplen con las reglas de negocio definidas en {@link Usuario}.
     */
    public Visitante(int idUsuario, String nombre, String telefono, String email, String contrasenia) throws GuiaBreteException {
        super(idUsuario, nombre, telefono, email, contrasenia);
    }

    /**
     * Genera una representación en cadena del Visitante para su almacenamiento en archivo.
     * <p>El formato utilizado es: {@code VISITANTE|id|nombre|telefono|email|contraseña}</p>
     * * @return Una cadena de texto formateada para ser procesada por {@link PersistenciaArchivo}.
     */
    @Override
    public String toString() {
        return "VISITANTE" + "|" + getIdUsuario() + "|" + getNombre() + "|" + getTelefono() + "|" + getEmail() + "|" + getContrasenia();
    }
}