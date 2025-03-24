/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Clase que representa un árbol dicotómico utilizado para la clasificación de especies.
 * El árbol está compuesto por nodos que contienen preguntas y respuestas (Sí/No) que
 * guían al usuario hacia la identificación de una especie.
 * 
 * Esta clase también incluye métodos para realizar búsquedas en el árbol utilizando
 * algoritmos BFS (Búsqueda en Anchura) y DFS (Búsqueda en Profundidad), así como
 * una representación gráfica del árbol.
 * 
 * @author Santiago, Fernando y Anthony
 */
package specquest;
import javax.swing.*;
import java.awt.*;

public class Arbol {
    private Nodo raiz; // Nodo raíz del árbol
    private Nodo nodoActual; // Nodo actual en el que se encuentra el usuario

    /**
     * Establece la raíz del árbol.
     * @param raiz El nodo raíz del árbol.
     */
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
        this.nodoActual = raiz;
    }

    /**
     * Reinicia el árbol, colocando el nodo actual en la raíz.
     */
    public void reiniciar() {
        this.nodoActual = raiz;
    }

    /**
     * Avanza en el árbol según la respuesta del usuario (Sí/No).
     * @param respuesta true para avanzar por la rama "Sí", false para la rama "No".
     * @return El valor del nodo actual después de avanzar.
     */
    public String avanzar(boolean respuesta) {
        if (nodoActual == null) return "No hay más opciones.";
        nodoActual = respuesta ? nodoActual.getNodoSi() : nodoActual.getNodoNo();
        return nodoActual != null ? nodoActual.getValor() : "Ruta no definida.";
    }

    /**
     * Verifica si el nodo actual es un nodo hoja (es decir, un resultado final).
     * @return true si el nodo actual es una hoja, false en caso contrario.
     */
    public boolean esResultadoFinal() {
        return nodoActual != null && nodoActual.esHoja();
    }

    /**
     * Obtiene el valor del nodo actual.
     * @return El valor del nodo actual.
     */
    public String getValorActual() {
        return nodoActual != null ? nodoActual.getValor() : "No hay valor disponible";
    }

    /**
     * Realiza una búsqueda en anchura (BFS) en el árbol para encontrar un valor específico.
     * @param valorBuscado El valor a buscar en el árbol.
     * @return La ruta tomada para encontrar el valor o un mensaje de "No encontrado".
     */
    public String buscarBFS(String valorBuscado) {
        Cola cola = new Cola();
        cola.encolar(raiz);
        StringBuilder ruta = new StringBuilder();

        while (!cola.estaVacia()) {
            Nodo nodo = cola.desencolar();
            ruta.append(nodo.getValor()).append(" -> ");
            if (nodo.getValor().equalsIgnoreCase(valorBuscado)) {
                return "Ruta (BFS): " + ruta.substring(0, ruta.length() - 4);
            }
            if (nodo.getNodoSi() != null) cola.encolar(nodo.getNodoSi());
            if (nodo.getNodoNo() != null) cola.encolar(nodo.getNodoNo());
        }
        return "No encontrado";
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) en el árbol para encontrar un valor específico.
     * @param valorBuscado El valor a buscar en el árbol.
     * @return La ruta tomada para encontrar el valor o un mensaje de "No encontrado".
     */
    public String buscarDFS(String valorBuscado) {
        Pila pila = new Pila();
        pila.apilar(raiz);
        StringBuilder ruta = new StringBuilder();

        while (!pila.estaVacia()) {
            Nodo nodo = pila.desapilar();
            ruta.append(nodo.getValor()).append(" -> ");
            if (nodo.getValor().equalsIgnoreCase(valorBuscado)) {
                return "Ruta (DFS): " + ruta.substring(0, ruta.length() - 4);
            }
            if (nodo.getNodoNo() != null) pila.apilar(nodo.getNodoNo());
            if (nodo.getNodoSi() != null) pila.apilar(nodo.getNodoSi());
        }
        return "No encontrado";
    }

    /**
     * Muestra una representación gráfica del árbol en una ventana.
     */
    public void mostrarArbolGrafico() {
        JFrame frame = new JFrame("Árbol Dicotómico");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ArbolPanel(raiz));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Clase interna que representa el panel gráfico para dibujar el árbol.
     */
    private static class ArbolPanel extends JPanel {
        private final Nodo raiz;

        public ArbolPanel(Nodo raiz) {
            this.raiz = raiz;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            dibujarNodo(g, raiz, 400, 50, 200);
        }

        /**
         * Dibuja un nodo y sus conexiones en el panel.
         * @param g El objeto Graphics utilizado para dibujar.
         * @param nodo El nodo a dibujar.
         * @param x La posición x del nodo.
         * @param y La posición y del nodo.
         * @param espacio El espacio entre los nodos.
         */
        private void dibujarNodo(Graphics g, Nodo nodo, int x, int y, int espacio) {
            if (nodo == null) return;
            
            g.setColor(Color.BLUE);
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(nodo.getValor(), x - 10, y + 5);

            if (nodo.getNodoSi() != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y, x - espacio, y + 50);
                dibujarNodo(g, nodo.getNodoSi(), x - espacio, y + 50, espacio / 2);
            }
            if (nodo.getNodoNo() != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y, x + espacio, y + 50);
                dibujarNodo(g, nodo.getNodoNo(), x + espacio, y + 50, espacio / 2);
            }
        }
    }
}
