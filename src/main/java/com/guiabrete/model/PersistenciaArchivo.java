package com.guiabrete.model;

import java.io.File;
import java.util.ArrayList;

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

    public void cargarServicios(RepositorioServicios repoServicios){

    }

    public void guardarServicios(RepositorioServicios repoServicios){

    }


}
