/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package specquest;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;

/**
 * @autor Fernando, Thony, Santiago
 * Clase principal que inicia la aplicación. Muestra un botón
 * para cargar un archivo JSON y crear el árbol y la tabla hash,
 * luego despliega la interfaz ClaveDicotomica.
 */
public class SpecQuest extends JFrame {

    /**
     * Método principal de la aplicación.
     * @param args argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cargar JSON");
            JButton btnCargar = new JButton("Cargar archivo JSON");
            btnCargar.addActionListener(e -> cargarJSON(frame));
            frame.add(btnCargar);
            frame.pack();
            frame.setVisible(true);
        });
    }

    /**
     * Abre un diálogo para seleccionar el archivo JSON.
     * Al cargarlo con éxito, crea un {@link ClaveDicotomica} con el árbol
     * y asigna la tabla hash al GUI.
     * 
     * @param parent Ventana padre para el diálogo de selección.
     */
    private static void cargarJSON(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                // Cargar árbol y tabla hash desde el JSON
                Arbol arbol = JSONLoader.cargarArbolDesdeJson(fileChooser.getSelectedFile().getPath());
                TablaHash tabla = JSONLoader.cargarTablaHashDesdeJSON(fileChooser.getSelectedFile().getPath());

                // Iniciar interfaz gráfica
                SwingUtilities.invokeLater(() -> {
                    ClaveDicotomica gui = new ClaveDicotomica(arbol);
                    gui.tablaHash = tabla; // Se asigna la tabla
                });
                parent.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    parent, 
                    "Error al cargar el JSON: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
