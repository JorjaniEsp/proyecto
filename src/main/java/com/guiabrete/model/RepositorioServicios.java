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
        if (servicio != null){
            servicios.add(servicio);
        }
    }

    public List<Servicio> buscarPorZona(String zona){
        if (zona.isBlank()){
            return servicios;
        } else {
          return servicios.stream().filter(serv -> serv.getZona().equalsIgnoreCase(zona)).toList();
        }
    }

    public List<Servicio> buscarPorCategoria(Categoria categoria){
            return servicios.stream().filter(serv -> serv.getCategoria().equals(categoria)).toList();
    }

    public List<Servicio> buscarPorTexto(String texto){
        if (texto.isBlank()){
            return servicios;
        } else {
            return servicios.stream().filter(serv -> serv.getDescripcionServ().toLowerCase().contains(texto.toLowerCase())).toList();
        }
    }

    public void editarServicio(Servicio servicio){
        etiqueta:
        for (Servicio serv : servicios){
            if (serv.equals(servicio)){
                serv.setNombreServ(servicio.getNombreServ());
                serv.setDescripcionServ(servicio.getDescripcionServ());
                serv.setCategoria(servicio.getCategoria());
                serv.setContacto(servicio.getContacto());
                serv.setHorario(servicio.getHorario());
                serv.setZona(servicio.getZona());
                break etiqueta;
            }
        }
    }

    public void eliminarServicio(Servicio servicio) {
        if (servicio != null) {
            servicios.remove(servicio);
        }
    }

    public List<Servicio> obtenerTodos(){
        return servicios;
    }

    public int generarIdServicio(){
        return siguienteIdServ++;
    }

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
