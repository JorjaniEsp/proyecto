package com.guiabrete;

import com.guiabrete.controller.ControladorApp;
import com.guiabrete.model.PersistenciaArchivo;
import com.guiabrete.model.RepositorioServicios;
import com.guiabrete.model.RepositorioUsuarios;
import com.guiabrete.view.MainVista;
import javax.swing.*;

/**
 * Clase principal que actúa como el orquestador de inicio de la aplicación Guía-Brete.
 * <p>Esta clase se encarga de implementar el patrón de diseño <b>MVC (Modelo-Vista-Controlador)</b>
 * mediante la instanciación de los repositorios de datos (Modelo), la interfaz gráfica (Vista)
 * y el controlador que gestiona la lógica de negocio.</p>
 * * <p>Asegura que la interfaz de usuario se ejecute de forma segura en el <b>Event Dispatch Thread (EDT)</b>
 * de Swing para evitar condiciones de carrera y asegurar la responsividad del sistema.</p>
 * * @author Grupo 04
 * @version 1.0
 */
public class MainApp {

    /**
     * Método de entrada al programa.
     * <p>Sigue los siguientes pasos secuenciales:</p>
     * <ol>
     * <li>Inicializa el <b>Modelo</b> accediendo a las instancias Singleton de repositorios y persistencia.</li>
     * <li>Inicializa la <b>Vista</b> creando el marco principal {@link MainVista}.</li>
     * <li>Vincula ambos mediante el <b>Controlador</b> {@link ControladorApp}.</li>
     * <li>Lanza la ejecución inicial del sistema.</li>
     * </ol>
     * * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing para garantizar seguridad de hilos (Thread Safety)
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Instancias del Modelo (Implementación del patrón Singleton)
                RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstance();
                RepositorioServicios repoServicios = RepositorioServicios.getInstance();
                PersistenciaArchivo persistencia = PersistenciaArchivo.getInstance();

                // 2. Instancia de la Vista (Contenedor principal de pantallas)
                MainVista vista = new MainVista();

                // 3. Instancia del Controlador (Cerebro que conecta Modelo y Vista)
                ControladorApp controlador = new ControladorApp(repoUsuarios, repoServicios, persistencia, vista);

                // 4. Iniciar aplicación
                controlador.iniciar();

            } catch (Exception e) {
                // Captura de errores críticos durante la inicialización
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fatal al iniciar la aplicación: " + e.getMessage(),
                        "Error de Sistema", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}