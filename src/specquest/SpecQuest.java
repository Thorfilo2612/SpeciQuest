/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 * Clase principal que inicia la aplicación SpecQuest.
 * Permite al usuario cargar un archivo JSON que contiene un árbol dicotómico y una tabla hash,
 * y luego inicia la interfaz gráfica para interactuar con el árbol.
 * 
 * @author Santiago, Fernando y Anthony
 */

package specquest;
import javax.swing.*;

public class SpecQuest extends JFrame {
    /**
     * Método principal que inicia la aplicación.
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
     * Carga un archivo JSON y crea un árbol dicotómico y una tabla hash a partir de él.
     * @param parent El frame padre desde el cual se invoca el método.
     */
    private static void cargarJSON(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                Arbol arbol = JSONLoader.cargarArbolDesdeJson(fileChooser.getSelectedFile().getPath());
                TablaHash tabla = JSONLoader.cargarTablaHashDesdeJSON(fileChooser.getSelectedFile().getPath());

                SwingUtilities.invokeLater(() -> {
                    ClaveDicotomica gui = new ClaveDicotomica(arbol);
                    gui.setTablaHash(tabla);
                    parent.dispose();
                });
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
