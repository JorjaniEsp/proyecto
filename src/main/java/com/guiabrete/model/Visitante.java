package com.guiabrete.model;

public class Visitante extends Usuario{

    public Visitante() {
    }

    public Visitante(int idUsuario, String nombre, String telefono, String email, String contrasenia) {
        super(idUsuario, nombre, telefono, email, contrasenia);
    }

    @Override
    public String toString() {
        return "VISITANTE" + "\\|" + getIdUsuario() + "\\|" + getNombre() + "\\|" + getTelefono() + "\\|" + getEmail() + "\\|" + getContrasenia();
    }
}
