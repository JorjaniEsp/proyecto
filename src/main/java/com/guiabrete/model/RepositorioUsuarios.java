package com.guiabrete.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {

    private List<Visitante> visitantes;
    private List<Proveedor> proveedores;
    private int siguienteIdUsuario;
    private PersistenciaArchivo archivo;

    private static RepositorioUsuarios instance;

    private RepositorioUsuarios() {
        visitantes = new ArrayList<>();
        proveedores = new ArrayList<>();
        archivo = PersistenciaArchivo.getInstance();
    }

    public static synchronized RepositorioUsuarios getInstance() {
        if (instance == null) {
            instance = new RepositorioUsuarios();
        }
        return instance;
    }

    public void anadirVisitante(Visitante v) {
        v.setIdUsuario(generarIdUsuario());
        visitantes.add(v);
    }

    public void anadirProveedor(Proveedor p) {
        p.setIdUsuario(generarIdUsuario());
        proveedores.add(p);
    }

    public void modificarUsuario(Usuario u) throws IOException {
        if (u instanceof Proveedor) {
            for (int i = 0; i < proveedores.size(); i++) {
                if(proveedores.get(i).getIdUsuario() == u.getIdUsuario()){
                    proveedores.set(i, (Proveedor) u);
                    break;
                }
            }
        }else if (u instanceof Visitante){
            for (int i = 0; i < visitantes.size(); i++) {
                if(visitantes.get(i).getIdUsuario() == u.getIdUsuario()){
                    visitantes.set(i, (Visitante) u);
                    break;
                }
            }
        }
        archivo.guardarUsuarios(instance);
    }

    public Visitante buscarVisitantePorEmailYClave(String email, String contrasena) {
        for (Visitante v : visitantes) {
            if (v.getEmail().equalsIgnoreCase(email) && v.getContrasenia().equals(contrasena)) {
                return v;
            }
        }
        return null;
    }

    public List<Visitante> obtenerVisitante() {
        return visitantes;
    }

    public List<Proveedor> obtenerProveedor() {
        return proveedores;
    }

    public Proveedor buscarProveedorPorEmailYClave(String email, String contrasena) {
        for (Proveedor p : proveedores) {
            if (p.getEmail().equalsIgnoreCase(email) && p.getContrasenia().equals(contrasena)) {
                return p;
            }
        }
        return null;
    }

    public int generarIdUsuario() {
        return siguienteIdUsuario++;
    }

    public void inicializarIdUsuario() {
        int maxId = 0;
        //Vamos a buscar el Id mas alto entre los visitantes
        for (Visitante v : visitantes) {
            if (v.getIdUsuario() > maxId) maxId = v.getIdUsuario();
        }

        for (Proveedor p : proveedores) {
            if (p.getIdUsuario() > maxId) maxId = p.getIdUsuario();
        }
        this.siguienteIdUsuario = maxId + 1;
    }

    public Proveedor buscarProveedorPorId(int idABuscar){
        for(Proveedor proveedor : proveedores){
            if(proveedor.getIdUsuario() == idABuscar){
                return proveedor;
            }
        }
        return null;
    }
}
