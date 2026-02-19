package com.guiabrete.controller;

import com.guiabrete.model.*;
import com.guiabrete.view.MainVista;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
            persistencia.cargarUsurios(repoUsuarios);
            repoUsuarios.inicializarIdUsuario();

            // Descomentado: Ya podemos cargar los servicios porque los usuarios existen
            persistencia.cargarServicios(repoServicios, repoUsuarios);
            repoServicios.inicializarIdServicio();
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
            Proveedor nuevo = new Proveedor(0, nombre, contacto, email, password, zona, horario);
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
            cargarMisServicios(); // Muestra el dashboard con los datos
        } else {
            vistaPrincipal.mostrarMensaje("Credenciales incorrectas o usuario no encontrado.");
        }
    }

    public void iniciarSesionVisitante(String email, String password) {
        Visitante v = repoUsuarios.buscarVisitantePorEmailYClave(email, password);
        if (v != null) {
            this.usuarioLogueado = v;
            vistaPrincipal.mostrarMensaje("Bienvenido, " + v.getNombre());
            vistaPrincipal.cambiarVista("panelVisitante");
            mostrarCatalogoCompleto();
        } else {
            vistaPrincipal.mostrarMensaje("Credenciales incorrectas o usuario no encontrado.");
        }
    }

    public void cerrarSesion() {
        this.usuarioLogueado = null;
        vistaPrincipal.cambiarVista("inicio");
    }

    // =========================================================================
    //                            REGIÓN: SERVICIOS (PROVEEDOR)
    // =========================================================================

    public void anadirServicio(String nombre, String descripcion, Categoria cat, String zona, String horario, String contacto) {
        if (usuarioLogueado instanceof Proveedor) {
            Proveedor miPerfil = (Proveedor) usuarioLogueado;
            try {
                // Ahora usamos los datos específicos que el usuario escribió en la ventana
                Servicio nuevo = new Servicio(0, nombre, cat, descripcion, zona, horario, miPerfil, contacto);

                repoServicios.agregarServ(nuevo);
                persistencia.guardarServicios(repoServicios);

                vistaPrincipal.mostrarMensaje("Servicio publicado exitosamente.");
                cargarMisServicios(); // Recargar la tabla y volver al panel

            } catch (GuiaBreteException | IOException e) {
                vistaPrincipal.mostrarMensaje("Error al guardar servicio: " + e.getMessage());
            }
        }
    }

    // NUEVO: Método para modificar un servicio existente
    public void modificarServicio(Servicio servicioActual, String nuevoNombre, String nuevaDesc, Categoria nuevaCat, String nuevaZona, String nuevoHorario, String nuevoContacto) {
        try {
            servicioActual.setNombreServ(nuevoNombre);
            servicioActual.setDescripcionServ(nuevaDesc);
            servicioActual.setCategoria(nuevaCat);
            servicioActual.setZona(nuevaZona);
            servicioActual.setHorario(nuevoHorario);
            servicioActual.setContacto(nuevoContacto);

            repoServicios.editarServicio(servicioActual); // El repo ya guarda en TXT internamente

            vistaPrincipal.mostrarMensaje("Servicio actualizado exitosamente.");
            cargarMisServicios(); // Volver al panel proveedor recargado

        } catch (IOException e) {
            vistaPrincipal.mostrarMensaje("Error al actualizar: " + e.getMessage());
        }
    }

    // NUEVO: Método para eliminar un servicio
    public void eliminarServicio(Servicio servicio) {
        try {
            repoServicios.eliminarServicio(servicio);
            vistaPrincipal.mostrarMensaje("Servicio eliminado.");
            cargarMisServicios();
        } catch (IOException e) {
            vistaPrincipal.mostrarMensaje("Error al eliminar: " + e.getMessage());
        }
    }

    public void cargarMisServicios() {
        if (usuarioLogueado instanceof Proveedor) {
            int miId = usuarioLogueado.getIdUsuario();

            // Filtramos la lista para obtener solo los de este proveedor
            List<Servicio> misServicios = repoServicios.obtenerTodos().stream()
                    .filter(s -> s.getProveedor().getIdUsuario() == miId)
                    .collect(Collectors.toList());

            vistaPrincipal.mostrarPanelProveedor(misServicios);
        }
    }

    // NUEVO: Actualizar el perfil del proveedor
    public void actualizarPerfilProveedor(String telefono, String zona, String horario) {
        if (usuarioLogueado instanceof Proveedor) {
            Proveedor p = (Proveedor) usuarioLogueado;
            try {
                p.setTelefono(telefono);
                p.setZona(zona);
                p.setHorario(horario);

                repoUsuarios.modificarUsuario(p);
                vistaPrincipal.mostrarMensaje("Perfil actualizado exitosamente.");
                cargarMisServicios(); // Volver al dashboard

            } catch (IOException e) {
                vistaPrincipal.mostrarMensaje("Error al guardar perfil: " + e.getMessage());
            }
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

    public void buscarPorTexto(String texto) {
        List<Servicio> resultados = repoServicios.buscarPorTexto(texto);
        vistaPrincipal.mostrarPanelVisitante(resultados);
    }

    public void verDetalleServicio(Servicio s) {
        if (vistaPrincipal != null) {
            vistaPrincipal.mostrarDetalleServicio(s);
        }
    }
    public void verPerfil() {
        if (usuarioLogueado instanceof Proveedor) {
            vistaPrincipal.mostrarPerfilProveedor((Proveedor) usuarioLogueado);
        }
    }
}