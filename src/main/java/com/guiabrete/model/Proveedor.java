package com.guiabrete.model;

/**
 * Representa a un usuario de tipo Proveedor dentro del sistema GuiaBrete.
 * <p>Esta clase extiende de {@link Usuario} y añade atributos específicos para la
 * prestación de servicios, como la zona geográfica de cobertura y el horario de atención.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class Proveedor extends Usuario {

    /** Zona geográfica donde el proveedor ofrece sus servicios. */
    private String zona;

    /** Disponibilidad horaria del proveedor para ser contactado o prestar servicios. */
    private String horario;

    /**
     * Constructor por defecto de la clase Proveedor.
     */
    public Proveedor() {
    }

    /**
     * Constructor con parámetros para inicializar un Proveedor con toda su información.
     * * @param idUsuario Identificador único del usuario.
     * @param nombre Nombre completo del proveedor.
     * @param telefono Número de contacto.
     * @param email Correo electrónico (utilizado como credencial).
     * @param contrasenia Clave de acceso al sistema.
     * @param zona Ubicación o área de trabajo.
     * @param horario Rango de horas de atención.
     * @throws GuiaBreteException Si alguno de los datos heredados no cumple con las validaciones de negocio.
     */
    public Proveedor(int idUsuario, String nombre, String telefono, String email, String contrasenia, String zona, String horario) throws GuiaBreteException {
        super(idUsuario, nombre, telefono, email, contrasenia);
        this.zona = zona;
        this.horario = horario;
    }

    /**
     * Obtiene la zona de cobertura del proveedor.
     * @return String con la zona.
     */
    public String getZona() {
        return zona;
    }

    /**
     * Define la zona de cobertura del proveedor.
     * @param zona Nueva zona a asignar.
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * Obtiene el horario de atención del proveedor.
     * @return String con el horario.
     */
    public String getHorario() {
        return horario;
    }

    /**
     * Define el horario de atención del proveedor.
     * @param horario Nuevo horario a asignar.
     */
    public void setHorario(String horario) {
        this.horario = horario;
    }

    /**
     * Genera una representación en cadena del Proveedor siguiendo el formato de persistencia.
     * <p>El formato es: {@code PROVEEDOR|id|nombre|telefono|email|contraseña|zona|horario}</p>
     * * @return Una cadena de texto formateada para almacenamiento en archivo plano.
     */
    @Override
    public String toString() {
        return "PROVEEDOR" + "|" + getIdUsuario()+ "|" + getNombre() + "|" + getTelefono() + "|" + getEmail() + "|" + getContrasenia() + "|" + zona + "|" + horario;
    }
}