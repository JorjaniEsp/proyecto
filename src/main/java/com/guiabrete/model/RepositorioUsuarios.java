package com.guiabrete.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio centralizado para la gestión de usuarios del sistema GuiaBrete.
 * <p>Esta clase administra dos colecciones independientes (Visitantes y Proveedores)
 * bajo el patrón <b>Singleton</b>, garantizando que el estado de los usuarios sea
 * consistente en toda la aplicación.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class RepositorioUsuarios {

    /** Lista de usuarios registrados como visitantes. */
    private List<Visitante> visitantes;

    /** Lista de usuarios registrados como proveedores. */
    private List<Proveedor> proveedores;

    /** Contador secuencial para asignar identificadores únicos a nuevos usuarios. */
    private int siguienteIdUsuario;

    /** Instancia única del repositorio para el patrón Singleton. */
    private static RepositorioUsuarios instance;

    /** Referencia al componente de persistencia para el guardado automático de cambios. */
    private PersistenciaArchivo archivo;

    /**
     * Constructor privado. Inicializa las listas y obtiene la instancia de persistencia.
     */
    private RepositorioUsuarios() {
        visitantes = new ArrayList<>();
        proveedores = new ArrayList<>();
        archivo = PersistenciaArchivo.getInstance();
    }

    /**
     * Obtiene la instancia única de RepositorioUsuarios.
     * @return La instancia Singleton de la clase.
     */
    public static synchronized RepositorioUsuarios getInstance() {
        if (instance == null) {
            instance = new RepositorioUsuarios();
        }
        return instance;
    }

    /**
     * Registra un nuevo visitante asignándole un ID único generado automáticamente.
     * @param v El objeto {@link Visitante} a registrar.
     */
    public void anadirVisitante(Visitante v) {
        v.setIdUsuario(generarIdUsuario());
        visitantes.add(v);
    }

    /**
     * Registra un nuevo proveedor asignándole un ID único generado automáticamente.
     * @param p El objeto {@link Proveedor} a registrar.
     */
    public void anadirProveedor(Proveedor p) {
        p.setIdUsuario(generarIdUsuario());
        proveedores.add(p);
    }

    /**
     * Actualiza la información de un usuario existente y sincroniza con el archivo físico.
     * <p>Utiliza {@code instanceof} para determinar si el usuario es Proveedor o Visitante
     * y actualiza su posición en la lista correspondiente basándose en el ID.</p>
     * * @param u El objeto usuario con los datos actualizados.
     * @throws IOException Si ocurre un error al guardar los cambios en el disco.
     */
    public void modificarUsuario(Usuario u) throws IOException {
        if (u instanceof Proveedor) {
            for (int i = 0; i < proveedores.size(); i++) {
                if(proveedores.get(i).getIdUsuario() == u.getIdUsuario()){
                    proveedores.set(i, (Proveedor) u);
                    break;
                }
            }
        } else if (u instanceof Visitante){
            for (int i = 0; i < visitantes.size(); i++) {
                if(visitantes.get(i).getIdUsuario() == u.getIdUsuario()){
                    visitantes.set(i, (Visitante) u);
                    break;
                }
            }
        }
        archivo.guardarUsuarios(instance);
    }

    /**
     * Busca un visitante que coincida con las credenciales proporcionadas.
     * @param email Correo electrónico del visitante (no distingue mayúsculas).
     * @param contrasena Contraseña del visitante.
     * @return El objeto {@link Visitante} si coincide; {@code null} en caso contrario.
     */
    public Visitante buscarVisitantePorEmailYClave(String email, String contrasena) {
        for (Visitante v : visitantes) {
            if (v.getEmail().equalsIgnoreCase(email) && v.getContrasenia().equals(contrasena)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Retorna la lista completa de visitantes registrados.
     * @return List de visitantes.
     */
    public List<Visitante> obtenerVisitante() {
        return visitantes;
    }

    /**
     * Retorna la lista completa de proveedores registrados.
     * @return List de proveedores.
     */
    public List<Proveedor> obtenerProveedor() {
        return proveedores;
    }

    /**
     * Busca un proveedor que coincida con las credenciales proporcionadas.
     * @param email Correo electrónico del proveedor (no distingue mayúsculas).
     * @param contrasena Contraseña del proveedor.
     * @return El objeto {@link Proveedor} si coincide; {@code null} en caso contrario.
     */
    public Proveedor buscarProveedorPorEmailYClave(String email, String contrasena) {
        for (Proveedor p : proveedores) {
            if (p.getEmail().equalsIgnoreCase(email) && p.getContrasenia().equals(contrasena)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Genera un nuevo identificador único para un usuario de manera secuencial.
     * @return El siguiente valor entero disponible.
     */
    public int generarIdUsuario() {
        return siguienteIdUsuario++;
    }

    /**
     * Sincroniza el contador {@code siguienteIdUsuario} con el ID más alto
     * existente entre ambas listas de usuarios (Visitantes y Proveedores).
     * <p>Este método es vital tras cargar los datos desde el archivo para evitar
     * duplicidad de IDs al crear nuevos registros.</p>
     */
    public void inicializarIdUsuario() {
        int maxId = 0;
        for (Visitante v : visitantes) {
            if (v.getIdUsuario() > maxId) maxId = v.getIdUsuario();
        }
        for (Proveedor p : proveedores) {
            if (p.getIdUsuario() > maxId) maxId = p.getIdUsuario();
        }
        this.siguienteIdUsuario = maxId + 1;
    }

    /**
     * Busca un proveedor específico utilizando su identificador único.
     * @param idABuscar El ID del proveedor.
     * @return El objeto {@link Proveedor} encontrado o {@code null} si no existe.
     */
    public Proveedor buscarProveedorPorId(int idABuscar){
        for(Proveedor proveedor : proveedores){
            if(proveedor.getIdUsuario() == idABuscar){
                return proveedor;
            }
        }
        return null;
    }
}