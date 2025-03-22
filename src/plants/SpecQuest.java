/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package plants;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Thony
 */
public class SpecQuest extends JFrame{

    public static void main(String[] args) {
        // Crear árbol de prueba
        Nodo raiz = new Nodo("¿Tiene hojas verdes?");
        Nodo nodoSi = new Nodo("¿Es un árbol frutal?");
        Nodo nodoNo = new Nodo("¿Tiene flores?");
        Nodo nodoFrutalSi = new Nodo("Manzano");
        Nodo nodoFrutalNo = new Nodo("Pino");
        Nodo nodoFlorSi = new Nodo("Rosa");
        Nodo nodoFlorNo = new Nodo("Cactus");

        raiz.setNodoSi(nodoSi);
        raiz.setNodoNo(nodoNo);
        nodoSi.setNodoSi(nodoFrutalSi);
        nodoSi.setNodoNo(nodoFrutalNo);
        nodoNo.setNodoSi(nodoFlorSi);
        nodoNo.setNodoNo(nodoFlorNo);

        Arbol arbol = new Arbol();
        arbol.setRaiz(raiz);

        TablaHash tabla = new TablaHash();
        tabla.insertar("Manzano", "Es un árbol frutal");
        tabla.insertar("Rosa", "Es una flor");

        SwingUtilities.invokeLater(() -> new ClaveDicotomica(arbol));
    }
    
}
