package com.guiabrete.model;

/**
 * Clase base que define la estructura y comportamiento común para todos los usuarios del sistema.
 * <p>Esta clase abstracta (aunque no declarada como tal, actúa como base) gestiona
 * la información de perfil básica y asegura que los datos mínimos de seguridad y
 * contacto sean válidos antes de permitir la creación de cualquier instancia.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class Usuario {

    private int idUsuario;
    private String nombre;
    private String telefono;
    private String email;
    private String contrasenia;

    /**
     * Constructor por defecto de la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor con parámetros que incluye lógica de validación de datos obligatorios y formato.
     * * @param idUsuario Identificador único asignado al usuario.
     * @param nombre Nombre completo del usuario.
     * @param telefono Número de teléfono de contacto.
     * @param email Dirección de correo electrónico (se normaliza a minúsculas).
     * @param contrasenia Clave de acceso al sistema.
     * @throws GuiaBreteException Si ocurre un error de validación de negocio.
     * @throws DatosIncompletosException Si el nombre, email o contraseña están vacíos.
     * @throws EmailInvalidoException Si el formato del correo no contiene un símbolo '@' seguido de un punto.
     */
    public Usuario(int idUsuario, String nombre, String telefono, String email, String contrasenia) throws GuiaBreteException {

        // Validaciones de presencia de datos
        if (nombre == null || nombre.isBlank()) throw new DatosIncompletosException("Nombre");
        if (email == null || email.isBlank()) throw new DatosIncompletosException("Correo");
        if (contrasenia == null || contrasenia.isBlank()) throw new DatosIncompletosException("Contraseña");

        // Validación básica de formato de correo electrónico
        if (!email.contains("@") || !email.substring(email.indexOf("@")).contains(".")) {
            throw new EmailInvalidoException();
        }

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        // Normalización para evitar problemas en el inicio de sesión
        this.email = email.toLowerCase().trim();
        this.contrasenia = contrasenia;
    }

    /** @return El identificador único del usuario. */
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /** @return El nombre completo registrado. */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return El número de teléfono del usuario. */
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /** @return El correo electrónico en formato minúsculas. */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /** @return La contraseña de acceso. */
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}