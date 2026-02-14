package com.guiabrete.model;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String telefono;
    private String email;
    private String contrasenia;

    public Usuario() {
    }
    public Usuario(int idUsuario, String nombre, String telefono, String email, String contrasenia) throws GuiaBreteException {

        if (nombre == null || nombre.isBlank()) throw new DatosIncompletosException("Nombre");
        if (email == null || email.isBlank()) throw new DatosIncompletosException("Correo");
        if (contrasenia == null || contrasenia.isBlank()) throw new DatosIncompletosException("Contraseña");

        if (!email.contains("@") || !email.substring(email.indexOf("@")).contains(".")) {
            throw new EmailInvalidoException();
        }

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email.toLowerCase().trim();
        this.contrasenia = contrasenia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
