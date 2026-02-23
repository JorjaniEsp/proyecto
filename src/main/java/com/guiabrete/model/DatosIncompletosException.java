package com.guiabrete.model;

/**
 * Excepción de validación utilizada para señalar la ausencia de información obligatoria.
 * <p>Esta clase se dispara durante los procesos de registro de usuarios o creación de servicios
 * cuando se detecta que un atributo esencial (como el nombre, la zona o el horario)
 * se encuentra vacío o solo contiene espacios en blanco.</p>
 * * <p>Al incluir el nombre del campo en el mensaje, facilita que la interfaz de usuario
 * informe con precisión al solicitante qué parte del formulario debe corregir.</p>
 * * @author Grupo 04
 * @version 1.0
 * @see GuiaBreteException
 */
public class DatosIncompletosException extends GuiaBreteException {

    /**
     * Construye una nueva excepción especificando el campo faltante.
     * <p>El mensaje resultante está personalizado para el contexto regional del proyecto,
     * enfatizando la importancia de la completitud de datos para la comunidad local.</p>
     * * @param campo Nombre descriptivo del atributo que no superó la validación
     * (ej. "Nombre", "Zona", "Contacto").
     */
    public DatosIncompletosException(String campo) {
        super("El campo '" + campo + "' es obligatorio para la comunidad de Limón.");
    }
}