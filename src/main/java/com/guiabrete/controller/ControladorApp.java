package com.guiabrete.controller;

import com.guiabrete.model.*;
import com.guiabrete.view.MainVista;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class ControladorApp {

    // --- 1. DEPENDENCIAS ---
    private RepositorioUsuarios repoUsuarios;
    private RepositorioServicios repoServicios;
    private PersistenciaArchivo persistencia;
    private MainVista vistaPrincipal;

    // --- 2. ESTADO DE LA SESIÓN ---
    private Usuario usuarioLogueado; // Puede ser Proveedor o Visitante

    // --- CONSTRUCTOR ---
    public ControladorApp(RepositorioUsuarios repoUsuarios, RepositorioServicios repoServicios, PersistenciaArchivo persistencia, MainVista vistaPrincipal) {
        this.repoUsuarios = repoUsuarios;
        this.repoServicios = repoServicios;
        this.persistencia = persistencia;
        this.vistaPrincipal = vistaPrincipal;

        // Inyectamos este controlador en la vista para que los botones puedan llamarnos
        this.vistaPrincipal.setControlador(this);
    }

    // --- 3. INICIALIZACIÓN DEL SISTEMA ---
    public void iniciar() {
        try {
            // 1. Cargar Usuarios del TXT
            persistencia.cargarUsurios(repoUsuarios);
            repoUsuarios.inicializarIdUsuario();

            // 2. Cargar Servicios del TXT (Requiere usuarios cargados para vincular al proveedor)
            // persistencia.cargarServicios(repoServicios, repoUsuarios); // Descomentar cuando tengas datos en servicios.txt
            repoServicios.inicializarIdServicio();

            System.out.println("Datos cargados correctamente.");
            vistaPrincipal.setVisible(true);

        } catch (IOException | GuiaBreteException e) {
            vistaPrincipal.mostrarMensaje("Error crítico al cargar datos: " + e.getMessage());
        }
    }

    // =========================================================================
    //                            REGIÓN: AUTENTICACIÓN
    // =========================================================================

    public void registrarProveedor(String nombre, String contacto, String zona, String horario, String email, String password) {
        try {
            // Crear objeto (Validaciones ocurren dentro del constructor del modelo)
            Proveedor nuevo = new Proveedor(0, nombre, contacto, email, password, zona, horario);

            // Guardar en memoria y archivo
            repoUsuarios.anadirProveedor(nuevo);
            persistencia.guardarUsuarios(repoUsuarios);

            vistaPrincipal.mostrarMensaje("¡Registro exitoso! Por favor inicie sesión.");
            vistaPrincipal.cambiarVista("inicioProveedor");

        } catch (GuiaBreteException | IOException e) {
            vistaPrincipal.mostrarMensaje("Error de registro: " + e.getMessage());
        }
    }

    public void registrarVisitante(String nombre, String contacto, String email, String password) {
        try {
            Visitante nuevo = new Visitante(0, nombre, contacto, email, password);
            repoUsuarios.anadirVisitante(nuevo);
            persistencia.guardarUsuarios(repoUsuarios);

            vistaPrincipal.mostrarMensaje("¡Bienvenido! Registro exitoso.");
            vistaPrincipal.cambiarVista("inicio");

        } catch (GuiaBreteException | IOException e) {
            vistaPrincipal.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    public void iniciarSesionProveedor(String email, String password) {
        Proveedor p = repoUsuarios.buscarProveedorPorEmailYClave(email, password);
        if (p != null) {
            this.usuarioLogueado = p;
            vistaPrincipal.mostrarMensaje("Bienvenido, " + p.getNombre());
            vistaPrincipal.cambiarVista("panelProveedor");

            // AQUÍ: Cargar la lista de "Mis Servicios" en la vista (Falta implementar en vista)
            // cargarMisServicios();
        } else {
            vistaPrincipal.mostrarMensaje("Credenciales incorrectas o usuario no encontrado.");
        }
    }

    public void iniciarSesionVisitante(String email, String password) {
        // Lógica similar para visitante (puedes implementarla si el visitante requiere login)
        // Por requerimiento, el visitante puede consultar sin registrarse, pero si se registra, aquí va el login.
        vistaPrincipal.cambiarVista("panelVisitante");
        mostrarCatalogoCompleto();
    }

    public void cerrarSesion() {
        this.usuarioLogueado = null;
        vistaPrincipal.cambiarVista("inicio");
    }

    // =========================================================================
    //                            REGIÓN: SERVICIOS (PROVEEDOR)
    // =========================================================================

    public void anadirServicio(String nombre, String descripcion, Categoria cat) {
        if (usuarioLogueado instanceof Proveedor) {
            Proveedor miPerfil = (Proveedor) usuarioLogueado;
            try {
                // Usamos la zona y horario del perfil del proveedor por defecto
                Servicio nuevo = new Servicio(0, nombre, cat, descripcion, miPerfil.getZona(), miPerfil.getHorario(), miPerfil, miPerfil.getTelefono());

                repoServicios.agregarServ(nuevo);
                persistencia.guardarServicios(repoServicios);

                vistaPrincipal.mostrarMensaje("Servicio publicado exitosamente.");
                vistaPrincipal.cambiarVista("panelProveedor");

            } catch (GuiaBreteException | IOException e) {
                vistaPrincipal.mostrarMensaje("Error al guardar servicio: " + e.getMessage());
            }
        }
    }

    public void cargarMisServicios() {
        if (usuarioLogueado instanceof Proveedor) {
            // Filtrar servicios donde el proveedor soy yo
            // List<Servicio> misServicios = repoServicios.buscarPorProveedor(...)
            // vistaPrincipal.actualizarTablaMisServicios(misServicios);
        }
    }

    // =========================================================================
    //                            REGIÓN: CATÁLOGO (VISITANTE)
    // =========================================================================

    public void mostrarCatalogoCompleto() {
        List<Servicio> todos = repoServicios.obtenerTodos();
        vistaPrincipal.mostrarPanelVisitante(todos);
    }

    public void buscarPorZona(String zona) {
        List<Servicio> resultados = repoServicios.buscarPorZona(zona);
        vistaPrincipal.mostrarPanelVisitante(resultados);
    }

    public void buscarPorCategoria(Categoria cat) {
        List<Servicio> resultados = repoServicios.buscarPorCategoria(cat);
        vistaPrincipal.mostrarPanelVisitante(resultados);
    }
}