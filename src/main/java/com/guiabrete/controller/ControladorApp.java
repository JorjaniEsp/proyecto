package com.guiabrete.controller;

import com.guiabrete.model.*;
import com.guiabrete.view.MainVista;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase controladora principal del sistema GuiaBrete.
 * Actúa como intermediario entre el modelo (Repositorios y Persistencia) y la vista (MainVista),
 * gestionando el flujo de la aplicación, la autenticación de usuarios y la lógica de servicios.
 * * @author Grupo 04
 * @version 1.0
 */
public class ControladorApp {

    // --- 1. DEPENDENCIAS ---
    private RepositorioUsuarios repoUsuarios;
    private RepositorioServicios repoServicios;
    private PersistenciaArchivo persistencia;
    private MainVista vistaPrincipal;

    /** * El usuario que ha iniciado sesión actualmente en el sistema.
     * Puede ser una instancia de {@link Proveedor} o {@link Visitante}.
     */
    private Usuario usuarioLogueado;

    /**
     * Constructor de la clase ControladorApp.
     * * @param repoUsuarios Repositorio que gestiona la lista de usuarios.
     * @param repoServicios Repositorio que gestiona el catálogo de servicios.
     * @param persistencia Componente encargado de la lectura/escritura de archivos.
     * @param vistaPrincipal Ventana principal de la interfaz gráfica.
     */
    public ControladorApp(RepositorioUsuarios repoUsuarios, RepositorioServicios repoServicios, PersistenciaArchivo persistencia, MainVista vistaPrincipal) {
        this.repoUsuarios = repoUsuarios;
        this.repoServicios = repoServicios;
        this.persistencia = persistencia;
        this.vistaPrincipal = vistaPrincipal;

        // Inyectamos este controlador en la vista para que los botones puedan llamarnos
        this.vistaPrincipal.setControlador(this);
    }

    /**
     * Inicializa el sistema cargando los datos desde la persistencia y
     * preparando la vista principal.
     */
    public void iniciar() {
        try {
            persistencia.cargarUsurios(repoUsuarios);
            repoUsuarios.inicializarIdUsuario();

            persistencia.cargarServicios(repoServicios, repoUsuarios);
            repoServicios.inicializarIdServicio();
            vistaPrincipal.setVisible(true);

        } catch (IOException | GuiaBreteException e) {
            vistaPrincipal.mostrarMensaje("Error crítico al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Registra un nuevo proveedor en el sistema y persiste los cambios.
     * * @param nombre Nombre completo del proveedor.
     * @param contacto Teléfono o medio de contacto.
     * @param zona Zona de operación.
     * @param horario Horario de atención.
     * @param email Correo electrónico (identificador único).
     * @param password Contraseña de acceso.
     */
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

    /**
     * Registra un nuevo visitante en el sistema.
     * * @param nombre Nombre del visitante.
     * @param contacto Teléfono de contacto.
     * @param email Correo electrónico.
     * @param password Contraseña.
     */
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

    /**
     * Valida las credenciales de un proveedor y establece la sesión activa.
     * * @param email Email ingresado.
     * @param password Contraseña ingresada.
     */
    public void iniciarSesionProveedor(String email, String password) {
        Proveedor p = repoUsuarios.buscarProveedorPorEmailYClave(email, password);
        if (p != null) {
            this.usuarioLogueado = p;
            vistaPrincipal.mostrarMensaje("Bienvenido, " + p.getNombre());
            cargarMisServicios();
        } else {
            vistaPrincipal.mostrarMensaje("Credenciales incorrectas o usuario no encontrado.");
        }
    }

    /**
     * Valida las credenciales de un visitante y establece la sesión activa.
     * * @param email Email ingresado.
     * @param password Contraseña ingresada.
     */
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

    /**
     * Finaliza la sesión actual y redirige a la pantalla de inicio.
     */
    public void cerrarSesion() {
        this.usuarioLogueado = null;
        vistaPrincipal.cambiarVista("inicio");
    }

    /**
     * Crea y publica un nuevo servicio vinculado al proveedor logueado.
     * * @param nombre Nombre del servicio.
     * @param descripcion Detalle del servicio ofrecido.
     * @param cat Categoría a la que pertenece el servicio.
     * @param zona Zona de cobertura.
     * @param horario Horario de disponibilidad.
     * @param contacto Contacto específico para el servicio.
     */
    public void anadirServicio(String nombre, String descripcion, Categoria cat, String zona, String horario, String contacto) {
        if (usuarioLogueado instanceof Proveedor) {
            Proveedor miPerfil = (Proveedor) usuarioLogueado;
            try {
                Servicio nuevo = new Servicio(0, nombre, cat, descripcion, zona, horario, miPerfil, contacto);
                repoServicios.agregarServ(nuevo);
                persistencia.guardarServicios(repoServicios);

                vistaPrincipal.mostrarMensaje("Servicio publicado exitosamente.");
                cargarMisServicios();

            } catch (GuiaBreteException | IOException e) {
                vistaPrincipal.mostrarMensaje("Error al guardar servicio: " + e.getMessage());
            }
        }
    }

    /**
     * Modifica los datos de un servicio existente.
     * * @param servicioActual La instancia del servicio a modificar.
     * @param nuevoNombre Nuevo nombre.
     * @param nuevaDesc Nueva descripción.
     * @param nuevaCat Nueva categoría.
     * @param nuevaZona Nueva zona.
     * @param nuevoHorario Nuevo horario.
     * @param nuevoContacto Nuevo contacto.
     */
    public void modificarServicio(Servicio servicioActual, String nuevoNombre, String nuevaDesc, Categoria nuevaCat, String nuevaZona, String nuevoHorario, String nuevoContacto) {
        try {
            servicioActual.setNombreServ(nuevoNombre);
            servicioActual.setDescripcionServ(nuevaDesc);
            servicioActual.setCategoria(nuevaCat);
            servicioActual.setZona(nuevaZona);
            servicioActual.setHorario(nuevoHorario);
            servicioActual.setContacto(nuevoContacto);

            repoServicios.editarServicio(servicioActual);

            vistaPrincipal.mostrarMensaje("Servicio actualizado exitosamente.");
            cargarMisServicios();

        } catch (IOException e) {
            vistaPrincipal.mostrarMensaje("Error al actualizar: " + e.getMessage());
        }
    }

    /**
     * Elimina un servicio del repositorio y actualiza la persistencia.
     * * @param servicio El servicio que se desea eliminar.
     */
    public void eliminarServicio(Servicio servicio) {
        try {
            repoServicios.eliminarServicio(servicio);
            vistaPrincipal.mostrarMensaje("Servicio eliminado.");
            cargarMisServicios();
        } catch (IOException e) {
            vistaPrincipal.mostrarMensaje("Error al eliminar: " + e.getMessage());
        }
    }

    /**
     * Filtra los servicios del sistema para mostrar únicamente los que pertenecen
     * al proveedor que ha iniciado sesión.
     */
    public void cargarMisServicios() {
        if (usuarioLogueado instanceof Proveedor) {
            int miId = usuarioLogueado.getIdUsuario();

            List<Servicio> misServicios = repoServicios.obtenerTodos().stream()
                    .filter(s -> s.getProveedor().getIdUsuario() == miId)
                    .collect(Collectors.toList());

            vistaPrincipal.mostrarPanelProveedor(misServicios);
        }
    }

    /**
     * Actualiza la información personal del perfil del proveedor logueado.
     * * @param telefono Nuevo teléfono.
     * @param zona Nueva zona base.
     * @param horario Nuevo horario base.
     */
    public void actualizarPerfilProveedor(String telefono, String zona, String horario) {
        if (usuarioLogueado instanceof Proveedor) {
            Proveedor p = (Proveedor) usuarioLogueado;
            try {
                p.setTelefono(telefono);
                p.setZona(zona);
                p.setHorario(horario);

                repoUsuarios.modificarUsuario(p);
                vistaPrincipal.mostrarMensaje("Perfil actualizado exitosamente.");
                cargarMisServicios();

            } catch (IOException e) {
                vistaPrincipal.mostrarMensaje("Error al guardar perfil: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene todos los servicios disponibles y los muestra en el panel del visitante.
     */
    public void mostrarCatalogoCompleto() {
        List<Servicio> todos = repoServicios.obtenerTodos();
        vistaPrincipal.mostrarPanelVisitante(todos);
    }

    /**
     * Filtra los servicios por zona geográfica.
     * @param zona Nombre de la zona a buscar.
     */
    public void buscarPorZona(String zona) {
        List<Servicio> resultados = repoServicios.buscarPorZona(zona);
        vistaPrincipal.mostrarPanelVisitante(resultados);
    }

    /**
     * Filtra los servicios por categoría.
     * @param cat Categoría seleccionada.
     */
    public void buscarPorCategoria(Categoria cat) {
        List<Servicio> resultados = repoServicios.buscarPorCategoria(cat);
        vistaPrincipal.mostrarPanelVisitante(resultados);
    }

    /**
     * Realiza una búsqueda de servicios basada en un texto libre.
     * @param texto Palabra clave para la búsqueda.
     */
    public void buscarPorTexto(String texto) {
        List<Servicio> resultados = repoServicios.buscarPorTexto(texto);
        vistaPrincipal.mostrarPanelVisitante(resultados);
    }

    /**
     * Solicita a la vista mostrar la ventana de detalles de un servicio específico.
     * @param s El servicio del cual se quieren ver los detalles.
     */
    public void verDetalleServicio(Servicio s) {
        if (vistaPrincipal != null) {
            vistaPrincipal.mostrarDetalleServicio(s);
        }
    }

    /**
     * Solicita a la vista mostrar la información del perfil del proveedor logueado.
     */
    public void verPerfil() {
        if (usuarioLogueado instanceof Proveedor) {
            vistaPrincipal.mostrarPerfilProveedor((Proveedor) usuarioLogueado);
        }
    }
}