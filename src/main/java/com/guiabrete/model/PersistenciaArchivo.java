package com.guiabrete.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaArchivo {
    private File carpeta;
    private File usuarios;
    private File servicios;
    private static PersistenciaArchivo instance;
    boolean creada = false;

    private PersistenciaArchivo() {
        this.carpeta = new File("datos");
        if (!carpeta.exists()){
            creada = carpeta.mkdir();
        }
        this.usuarios = new File(carpeta, "usuarios.txt");
        this.servicios = new File(carpeta, "servicios.txt");
    }

    public static synchronized PersistenciaArchivo getInstance() {
        if (instance == null) {
            instance = new PersistenciaArchivo();
        }
        return instance;
    }

    public void cargarUsurios(RepositorioUsuarios repoUsuarios){

    }

    public void guardarUsuarios(RepositorioUsuarios repoUsuarios){

    }

    public void cargarServicios(RepositorioServicios repoServicios, RepositorioUsuarios repoUsuarios) throws IOException {
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
            }
        }
    }

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
