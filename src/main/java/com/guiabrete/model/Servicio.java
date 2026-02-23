package com.guiabrete.model;

/**
 * Representa un servicio específico ofrecido por un proveedor en el sistema.
 * <p>Esta clase valida la integridad de los datos al momento de su creación,
 * asegurando que campos esenciales como nombre, categoría y proveedor no estén vacíos.
 * Además, implementa una lógica de normalización para números telefónicos.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class Servicio {
    private int idServ;
    private String nombreServ;
    private Categoria categoria;
    private String descripcionServ;
    private String zona;
    private String horario;
    private Proveedor proveedor;
    private String contacto;

    /**
     * Constructor completo de la clase Servicio con validaciones de negocio.
     * * @param idServ Identificador único del servicio.
     * @param nombreServ Nombre representativo del servicio (ej. "Fontanería").
     * @param categoria Clasificación del servicio mediante el enum {@link Categoria}.
     * @param descripcionServ Explicación detallada de lo que ofrece el servicio.
     * @param zona Área geográfica donde se presta el servicio.
     * @param horario Rango de horas disponible.
     * @param proveedor Instancia de {@link Proveedor} que es dueño del servicio.
     * @param contacto Número telefónico. Se normaliza eliminando caracteres no numéricos.
     * * @throws GuiaBreteException Si faltan datos obligatorios o si el formato del contacto es inválido.
     * @throws DatosIncompletosException Si campos obligatorios como nombre, descripción o proveedor son nulos o vacíos.
     * @throws ContactoInvalidoException Si el número de contacto no tiene entre 8 y 11 dígitos numéricos.
     */
    public Servicio(int idServ, String nombreServ, Categoria categoria, String descripcionServ,
                    String zona, String horario, Proveedor proveedor, String contacto)
            throws GuiaBreteException {

        // Validaciones de integridad
        if (nombreServ == null || nombreServ.isBlank()) throw new DatosIncompletosException("Nombre");
        if (descripcionServ == null || descripcionServ.isBlank()) throw new DatosIncompletosException("Descripción");
        if (zona == null || zona.isBlank()) throw new DatosIncompletosException("Zona");
        if (categoria == null) throw new DatosIncompletosException("Categoría");
        if (proveedor == null) throw new DatosIncompletosException("Proveedor");

        // Normalización de contacto: extraer solo dígitos
        String soloNumeros = (contacto != null) ? contacto.replaceAll("[^0-9]", "") : "";

        if (soloNumeros.length() > 11 || soloNumeros.length() < 8) {
            throw new ContactoInvalidoException();
        }

        this.idServ = idServ;
        this.nombreServ = nombreServ;
        this.categoria = categoria;
        this.descripcionServ = descripcionServ;
        this.zona = zona;
        this.horario = horario;
        this.proveedor = proveedor;

        // Lógica de formateo: Si tiene 8 dígitos, asume código de país 506 (Costa Rica)
        if (soloNumeros.length() == 11){
            this.contacto = soloNumeros;
        } else if (soloNumeros.length() == 8) {
            this.contacto = "506" + soloNumeros;
        }
    }

    /** @return El objeto de la categoría asignada. */
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /** @return El ID numérico del servicio. */
    public int getIdServ() {
        return idServ;
    }

    public void setIdServ(int idServ) {
        this.idServ = idServ;
    }

    public String getNombreServ() {
        return nombreServ;
    }

    public void setNombreServ(String nombreServ) {
        this.nombreServ = nombreServ;
    }

    public String getDescripcionServ() {
        return descripcionServ;
    }

    public void setDescripcionServ(String descripcionServ) {
        this.descripcionServ = descripcionServ;
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

    /** @return El {@link Proveedor} responsable de este servicio. */
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /** @return El número de contacto normalizado (con o sin código 506). */
    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * Devuelve una representación en cadena del servicio para persistencia.
     * <p>Formato: {@code idServ|nombre|categoria|descripcion|zona|horario|idProveedor|contacto}</p>
     * * @return String formateado para el archivo servicios.txt.
     */
    @Override
    public String toString() {
        return idServ + "|" + nombreServ + "|" + categoria + "|" + descripcionServ + "|" + zona + "|" + horario + "|" + proveedor.getIdUsuario() + "|" + contacto;
    }
}