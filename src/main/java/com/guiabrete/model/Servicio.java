package com.guiabrete.model;

public class Servicio {
    private int idServ;
    private String nombreServ;
    private Categoria categoria;
    private String descripcionServ;
    private String zona;
    private String horario;
    private Proveedor proveedor;
    private String contacto;

    public Servicio(int idServ, String nombreServ, String descripcionServ, String zona, String horario, Proveedor proveedor, String contacto) {
        this.idServ = idServ;
        this.nombreServ = nombreServ;
        this.descripcionServ = descripcionServ;
        this.zona = zona;
        this.horario = horario;
        this.proveedor = proveedor;
        this.contacto = contacto;
    }

    public Categoria getCategoria() { return categoria; }

    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public int getIdServ() { return idServ; }

    public void setIdServ(int idServ) { this.idServ = idServ; }

    public String getNombreServ() { return nombreServ; }

    public void setNombreServ(String nombreServ) { this.nombreServ = nombreServ; }

    public String getDescripcionServ() { return descripcionServ; }

    public void setDescripcionServ(String descripcionServ) { this.descripcionServ = descripcionServ; }

    public String getZona() { return zona; }

    public void setZona(String zona) { this.zona = zona; }

    public String getHorario() { return horario; }

    public void setHorario(String horario) { this.horario = horario; }

    public Proveedor getProveedor() { return proveedor; }

    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public String getContacto() { return contacto; }

    public void setContacto(String contacto) { this.contacto = contacto; }
}
