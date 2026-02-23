package com.guiabrete.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de gestionar la colección de servicios en memoria.
 * <p>Implementa el patrón <b>Singleton</b> y proporciona métodos avanzados de filtrado
 * por zona, categoría y búsqueda por texto, además de gestionar el ciclo de vida de los IDs.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class RepositorioServicios {
    /** Lista que almacena todos los servicios cargados en el sistema. */
    private List<Servicio> servicios;

    /** Contador para la generación automática de identificadores de servicios. */
    private int siguienteIdServ;

    /** Instancia única del repositorio. */
    private static RepositorioServicios instance;

    /** Referencia al componente de persistencia para guardar cambios automáticamente. */
    private PersistenciaArchivo archivo;

    /**
     * Constructor privado que inicializa la lista de servicios y obtiene la instancia de persistencia.
     */
    private RepositorioServicios(){
        servicios = new ArrayList<>();
        archivo = PersistenciaArchivo.getInstance();
    }

    /**
     * Obtiene la instancia única de RepositorioServicios.
     * @return La instancia única (Singleton).
     */
    public static synchronized RepositorioServicios getInstance() {
        if (instance == null) {
            instance = new RepositorioServicios();
        }
        return instance;
    }

    /**
     * Agrega un servicio a la lista en memoria.
     * @param servicio El objeto Servicio a añadir.
     */
    public void agregarServ(Servicio servicio){
        if (servicio != null){
            servicios.add(servicio);
        }
    }

    /**
     * Filtra los servicios según una zona específica.
     * @param zona Nombre de la zona (ignora mayúsculas/minúsculas). Si está vacío, retorna todos.
     * @return Lista de servicios que coinciden con la zona.
     */
    public List<Servicio> buscarPorZona(String zona){
        if (zona.isBlank()){
            return servicios;
        } else {
            return servicios.stream().filter(serv -> serv.getZona().equalsIgnoreCase(zona)).toList();
        }
    }

    /**
     * Filtra los servicios por su categoría.
     * @param categoria La {@link Categoria} a filtrar.
     * @return Lista de servicios pertenecientes a dicha categoría.
     */
    public List<Servicio> buscarPorCategoria(Categoria categoria){
        return servicios.stream().filter(serv -> serv.getCategoria().equals(categoria)).toList();
    }

    /**
     * Realiza una búsqueda avanzada en el nombre y descripción del servicio.
     * <p>Divide el texto de búsqueda en palabras y retorna los servicios que contengan
     * al menos una coincidencia (en palabras de más de 2 caracteres).</p>
     * @param texto Cadena de texto introducida por el usuario.
     * @return Lista de servicios que coinciden con los criterios de búsqueda.
     */
    public List<Servicio> buscarPorTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return servicios;
        }
        String[] palabrasBuscadas = texto.toLowerCase().split("\\s+");

        return servicios.stream().filter(serv -> {
            String textoServicio = serv.getNombreServ().toLowerCase() + " " + serv.getDescripcionServ().toLowerCase();
            int coincidencias = 0;

            for (String palabra : palabrasBuscadas) {
                if (palabra.length() > 2 && textoServicio.contains(palabra)) {
                    coincidencias++;
                }
            }
            return coincidencias > 0;
        }).toList();
    }

    /**
     * Actualiza los datos de un servicio existente y sincroniza los cambios en el archivo físico.
     * @param servicio Objeto con los nuevos datos (debe coincidir el ID mediante {@code equals}).
     * @throws IOException Si ocurre un error al persistir los cambios en el disco.
     */
    public void editarServicio(Servicio servicio) throws IOException {
        for (Servicio serv : servicios){
            if (serv.equals(servicio)){
                serv.setNombreServ(servicio.getNombreServ());
                serv.setDescripcionServ(servicio.getDescripcionServ());
                serv.setCategoria(servicio.getCategoria());
                serv.setContacto(servicio.getContacto());
                serv.setHorario(servicio.getHorario());
                serv.setZona(servicio.getZona());
                break;
            }
        }
        archivo.guardarServicios(instance);
    }

    /**
     * Elimina un servicio de la lista y actualiza el archivo de persistencia.
     * @param servicio El servicio a eliminar.
     * @throws IOException Si ocurre un error al guardar los cambios.
     */
    public void eliminarServicio(Servicio servicio) throws IOException {
        if (servicio != null) {
            servicios.remove(servicio);
        }
        archivo.guardarServicios(instance);
    }

    /**
     * Retorna la colección completa de servicios registrados.
     * @return Lista de todos los servicios.
     */
    public List<Servicio> obtenerTodos(){
        return servicios;
    }

    /**
     * Genera un nuevo ID secuencial para un servicio.
     * @return El siguiente valor entero disponible para un ID.
     */
    public int generarIdServicio(){
        return siguienteIdServ++;
    }

    /**
     * Escanea los servicios existentes para encontrar el ID más alto y configurar
     * el contador {@code siguienteIdServ} adecuadamente. Evita colisiones de ID al reiniciar la app.
     */
    public void inicializarIdServicio(){
        int maxId = 0;
        for (Servicio s : servicios) {
            if (s.getIdServ() > maxId) {
                maxId = s.getIdServ();
            }
        }
        this.siguienteIdServ = maxId + 1;
    }
}