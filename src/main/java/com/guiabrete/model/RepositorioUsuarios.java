package com.guiabrete.model;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {

    private List<Visitante> visitantes;
    private List<Proveedor> proveedores;
    private int siguienteIdUsuario;

    private static RepositorioUsuarios instance;

    public RepositorioUsuarios() {
        visitantes = new ArrayList<>();
        proveedores = new ArrayList<>();
    }

    public static synchronized RepositorioUsuarios getInstance(){
        if(instance == null) {
            instance = new RepositorioUsuarios();
        }
        return instance;
    }


}
