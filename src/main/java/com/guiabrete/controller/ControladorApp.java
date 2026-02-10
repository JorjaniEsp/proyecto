package com.guiabrete.controller;

import com.guiabrete.MainApp;
import com.guiabrete.model.*;

import java.util.List;

public class ControladorApp {

    private RepositorioUsuarios repoUsuarios;
    private RepositorioServicios repoServicios;
    private PersistenciaArchivo persistencia;
    private MainApp vistaPrincipal;

    public ControladorApp() {
    }

    public ControladorApp(RepositorioUsuarios repoUsuarios, RepositorioServicios repoServicios, PersistenciaArchivo persistencia, MainApp vistaPrincipal) {
        this.repoUsuarios = repoUsuarios;
        this.repoServicios = repoServicios;
        this.persistencia = persistencia;
        this.vistaPrincipal = vistaPrincipal;
    }

    public void iniciar(){

    }

    public void registrarUsuario(){

    }

    public void iniciarSesionProveedor(){

    }

    public void anadirServicio(){

    }

    public void modificarServicio(){

    }

    public void eliminarServicio(){

    }

    public void mostrarCatalogo(){

    }

    public List<Servicio> buscarPorZona(String zona){
        return null;
    }

    public List<Servicio> buscarPorCategoria(Categoria cat){
        return null;
    }

    public void verDetalleServidor(Servicio serv){

    }

    public void contactarProveedor(Servicio serv){

    }

    public void actualizarPerfilProveedor(Proveedor p){

    }

    public List<Servicio> verServiciosPropios(Proveedor p){
        return null;
    }
}
