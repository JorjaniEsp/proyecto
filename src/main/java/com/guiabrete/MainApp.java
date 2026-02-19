package com.guiabrete;

import com.guiabrete.controller.ControladorApp;
import com.guiabrete.model.PersistenciaArchivo;
import com.guiabrete.model.RepositorioServicios;
import com.guiabrete.model.RepositorioUsuarios;
import com.guiabrete.view.MainVista;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Instancias del Modelo (Singleton)
                RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstance();
                RepositorioServicios repoServicios = RepositorioServicios.getInstance();
                PersistenciaArchivo persistencia = PersistenciaArchivo.getInstance();

                // 2. Instancia de la Vista
                MainVista vista = new MainVista();

                // 3. Instancia del Controlador (Conecta Modelo y Vista)
                ControladorApp controlador = new ControladorApp(repoUsuarios, repoServicios, persistencia, vista);

                // 4. Iniciar aplicación
                controlador.iniciar();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fatal: " + e.getMessage());
            }
        });
    }
}