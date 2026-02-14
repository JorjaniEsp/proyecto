package com.guiabrete.model;

public class Proveedor extends Usuario{
    private String zona;
    private String horario;

    public Proveedor() {
    }

    public Proveedor(int idUsuario, String nombre, String telefono, String email, String contrasenia,  String zona, String horario) throws GuiaBreteException {
        super(idUsuario, nombre, telefono, email, contrasenia);
        this.zona = zona;
        this.horario = horario;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "PROVEEDOR" + "|" + getIdUsuario()+ "|" + getNombre() + "|" + getTelefono() + "|" + getEmail() + "|" + getContrasenia() + "|" + zona + "|" + horario;
    }
}
