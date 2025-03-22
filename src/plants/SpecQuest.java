/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package specquest;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Thony
 */
public class SpecQuest extends JFrame {
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

    private static void cargarJSON(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                // Cargar árbol y tabla hash desde el JSON
                Arbol arbol = JSONLoader.cargarArbolDesdeJson(fileChooser.getSelectedFile().getPath());
                TablaHash tabla = JSONLoader.cargarTablaHashDesdeJSON(fileChooser.getSelectedFile().getPath());

                // Iniciar interfaz con los datos cargados
                SwingUtilities.invokeLater(() -> {
                    ClaveDicotomica gui = new ClaveDicotomica(arbol);
                    gui.tablaHash = tabla; // Asegúrate de agregar un setter en ClaveDicotomica
                });
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Error al cargar el JSON: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
