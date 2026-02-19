package com.guiabrete.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioServicios {
    private List<Servicio> servicios;
    private int siguienteIdServ;
    private static RepositorioServicios instance;
    private PersistenciaArchivo archivo;

    private RepositorioServicios(){
        servicios = new ArrayList<>();
        archivo = PersistenciaArchivo.getInstance();
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

    public void eliminarServicio(Servicio servicio) throws IOException {
        if (servicio != null) {
            servicios.remove(servicio);
        }
        archivo.guardarServicios(instance);
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
