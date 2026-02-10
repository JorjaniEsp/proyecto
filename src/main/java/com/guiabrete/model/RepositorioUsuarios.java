package com.guiabrete.model;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {

    private List<Visitante> visitantes;
    private List<Proveedor> proveedores;
    private int siguienteIdUsuario;

    private static RepositorioUsuarios instance;

    private RepositorioUsuarios() {
        visitantes = new ArrayList<>();
        proveedores = new ArrayList<>();
    }

    public static synchronized RepositorioUsuarios getInstance() {
        if (instance == null) {
            instance = new RepositorioUsuarios();
        }
        return instance;
    }

    public void anadirVisitante(Visitante v) {

    }

    public void anadirProveedor(Proveedor p) {

    }

    public void modificarUsuario(Usuario u) {

    }

    public List<Visitante> obtenerVisitante() {
        return null;

    }

    public List<Proveedor> obtenerProveedor() {
        return null;
    }

    public Proveedor buscarProveedorPorEmailYClave(String email, String contrasena) {
        return null;
    }

    public int generarIdUsuario() {
        return 0;
    }

    public void inicializarIdUsuario() {

    }
}
