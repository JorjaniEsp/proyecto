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
}
