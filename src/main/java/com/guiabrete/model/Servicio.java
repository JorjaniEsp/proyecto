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

    public Servicio(int idServ, String nombreServ, Categoria categoria, String descripcionServ,
                    String zona, String horario, Proveedor proveedor, String contacto)
            throws GuiaBreteException {

        if (nombreServ == null || nombreServ.isBlank()) throw new DatosIncompletosException("Nombre");
        if (descripcionServ == null || descripcionServ.isBlank()) throw new DatosIncompletosException("Descripción");
        if (zona == null || zona.isBlank()) throw new DatosIncompletosException("Zona");
        if (categoria == null) throw new DatosIncompletosException("Categoría");
        if (proveedor == null) throw new DatosIncompletosException("Proveedor");

        String soloNumeros = (contacto != null) ? contacto.replaceAll("[^0-9]", "") : "";

        if (soloNumeros.length() != 8) {
            throw new ContactoInvalidoException();
        }

        this.idServ = idServ;
        this.nombreServ = nombreServ;
        this.categoria = categoria;
        this.descripcionServ = descripcionServ;
        this.zona = zona;
        this.horario = horario;
        this.proveedor = proveedor;

        this.contacto = "506" + soloNumeros;
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

    @Override
    public String toString(){
        return idServ + "|" + nombreServ + "|" + categoria + "|" + descripcionServ + "|" + zona + "|" + horario + "|" + proveedor.getIdUsuario() + "|" + contacto;
    }
}
