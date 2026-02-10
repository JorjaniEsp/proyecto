package com.guiabrete.model;

import java.util.ArrayList;
import java.util.List;

public class RepositorioServicios {
    private List<Servicio> servicios;
    private int siguienteIdServ;
    private static RepositorioServicios instance;

    private RepositorioServicios(){
        servicios = new ArrayList<>();
    }

    public static synchronized RepositorioServicios getInstance() {
        if (instance == null) {
            instance = new RepositorioServicios();
        }
        return instance;
    }

    public void agregarServ(Servicio servicio){

    }

    public List<Servicio> buscarPorZona(String zona){
        return null;
    }

    public List<Servicio> buscarPorCategoria(Categoria categoria){
        return null;
    }

    public List<Servicio> buscarPorTexto(String texto){
        return null;
    }

    public void editarServicio(Servicio servicio){

    }

    public void eliminarServicio(Servicio servicio){

    }

    public List<Servicio> obtenerTodos(){
        return null;
    }

    public int generarIdServicio(){
        return 0;
    }

    public void inicializarIdServicio(){

    }



}
