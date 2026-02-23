package com.guiabrete.model;

import java.io.*;
import java.util.List;

/**
 * Clase encargada de la persistencia de datos en archivos de texto plano.
 * Implementa el patrón <b>Singleton</b> para asegurar una única gestión de los archivos.
 * * <p>Esta clase maneja la lectura y escritura de usuarios (Proveedores y Visitantes)
 * y servicios, utilizando un formato de texto delimitado por tuberías ("|").</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class PersistenciaArchivo {
    private File carpeta;
    private File usuarios;
    private File servicios;
    private static PersistenciaArchivo instance;

    /** Flag que indica si la carpeta de datos fue creada exitosamente en esta sesión. */
    boolean creada = false;

    /**
     * Constructor privado para aplicar el patrón Singleton.
     * Crea la carpeta "datos" si no existe e inicializa las referencias a los archivos TXT.
     */
    private PersistenciaArchivo() {
        this.carpeta = new File("datos");
        if (!carpeta.exists()){
            creada = carpeta.mkdir();
        }
        this.usuarios = new File(carpeta, "usuarios.txt");
        this.servicios = new File(carpeta, "servicios.txt");
    }

    /**
     * Obtiene la instancia única de PersistenciaArchivo.
     * @return La instancia única (Singleton).
     */
    public static synchronized PersistenciaArchivo getInstance() {
        if (instance == null) {
            instance = new PersistenciaArchivo();
        }
        return instance;
    }

    /**
     * Lee el archivo de usuarios y puebla el repositorio proporcionado.
     * Identifica el tipo de usuario mediante el primer token de cada línea ("PROVEEDOR" o "VISITANTE").
     * * @param repoUsuarios El repositorio donde se cargarán los objetos creados.
     * @throws IOException Si ocurre un error de lectura física del archivo.
     * @throws GuiaBreteException Si los datos del archivo violan las reglas de negocio.
     */
    public void cargarUsurios(RepositorioUsuarios repoUsuarios) throws IOException, GuiaBreteException {
        if (usuarios.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(usuarios))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] infoUsuario = linea.split("\\|");
                    if (infoUsuario[0].equals("PROVEEDOR")) {
                        int id = Integer.parseInt(infoUsuario[1]);
                        String nombre = infoUsuario[2];
                        String telefono = infoUsuario[3];
                        String email = infoUsuario[4];
                        String contrasena = infoUsuario[5];
                        String zona = infoUsuario[6];
                        String horario = infoUsuario[7];
                        repoUsuarios.anadirProveedor(new Proveedor(id, nombre, telefono, email, contrasena, zona, horario));
                    } else if (infoUsuario[0].equals("VISITANTE")) {
                        int id = Integer.parseInt(infoUsuario[1]);
                        String nombre = infoUsuario[2];
                        String telefono = infoUsuario[3];
                        String email = infoUsuario[4];
                        String contrasena = infoUsuario[5];
                        repoUsuarios.anadirVisitante(new Visitante(id, nombre, telefono, email, contrasena));
                    }
                }
            } catch (IOException e) {
                throw new IOException("Error al cargar usuarios: " + e.getMessage());
            } catch (GuiaBreteException e){
                throw new GuiaBreteException(e.getMessage());
            }
        }
    }

    /**
     * Sobreescribe el archivo de usuarios con la información actual de los repositorios.
     * Utiliza el método {@code toString()} de cada clase de modelo para dar formato a la línea.
     * * @param repoUsuarios Repositorio que contiene las listas de proveedores y visitantes.
     * @throws IOException Si no se puede escribir en el archivo.
     */
    public void guardarUsuarios(RepositorioUsuarios repoUsuarios) throws IOException {
        List<Proveedor> listaProveedorGuardar = repoUsuarios.obtenerProveedor();
        List<Visitante> listaVisitanteGuardar = repoUsuarios.obtenerVisitante();

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(usuarios, false))) {
            for (Proveedor proveedor : listaProveedorGuardar) {
                escritor.write(proveedor.toString());
                escritor.newLine();
            }
            for (Visitante visitante : listaVisitanteGuardar) {
                escritor.write(visitante.toString());
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error al escribir en el archivo de usuarios: " + e.getMessage());
        }
    }

    /**
     * Carga los servicios desde el archivo y los vincula con sus respectivos proveedores.
     * <p>Es crucial que los usuarios se carguen <b>antes</b> que los servicios,
     * ya que se requiere buscar el objeto Proveedor por ID para reconstruir la relación.</p>
     * * @param repoServicios Repositorio donde se añadirán los servicios.
     * @param repoUsuarios Repositorio de usuarios necesario para buscar la referencia del dueño del servicio.
     * @throws IOException Si ocurre un error de lectura o formato de datos.
     * @throws GuiaBreteException Si ocurre un error de lógica de negocio al agregar el servicio.
     */
    public void cargarServicios(RepositorioServicios repoServicios, RepositorioUsuarios repoUsuarios) throws IOException, GuiaBreteException {
        if (servicios.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(servicios))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] atributos = linea.split("\\|");

                    int id = Integer.parseInt(atributos[0]);
                    Categoria cat = Categoria.valueOf(atributos[2]);
                    int idProveedor = Integer.parseInt(atributos[6]);

                    Proveedor proveedor = repoUsuarios.buscarProveedorPorId(idProveedor);

                    if (proveedor != null) {
                        repoServicios.agregarServ(new Servicio(id, atributos[1], cat, atributos[3], atributos[4], atributos[5], proveedor, atributos[7]));
                    }
                }
            } catch (IOException | IllegalArgumentException e) {
                throw new IOException("Error al cargar servicios: " + e.getMessage());
            } catch (GuiaBreteException e) {
                throw new GuiaBreteException(e.getMessage());
            }
        }
    }

    /**
     * Guarda la lista completa de servicios en el archivo correspondiente.
     * * @param repoServicios Repositorio que contiene los servicios a persistir.
     * @throws IOException Si ocurre un error al acceder o escribir en el archivo.
     */
    public void guardarServicios(RepositorioServicios repoServicios) throws IOException {
        List<Servicio> listaAGuardar = repoServicios.obtenerTodos();

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(servicios, false))) {
            for (Servicio serv : listaAGuardar) {
                escritor.write(serv.toString());
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error al escribir en el archivo de servicios: " + e.getMessage());
        }
    }
}