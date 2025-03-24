/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Clase que representa una cola utilizada para implementar el algoritmo BFS.
 * La cola almacena nodos del árbol dicotómico.
 * 
 * @author Santiago, Fernando y Anthony
 */

package specquest;

public class Cola {
    private NodoCola frente; // Primer elemento de la cola
    private NodoCola fondo; // Último elemento de la cola

    /**
     * Clase interna que representa un nodo de la cola.
     */
    private static class NodoCola {
        Nodo valor; // Valor del nodo (un nodo del árbol)
        NodoCola siguiente; // Referencia al siguiente nodo en la cola

        NodoCola(Nodo valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    /**
     * Añade un nodo al final de la cola.
     * @param valor El nodo a añadir.
     */
    public void encolar(Nodo valor) {
        NodoCola nuevoNodo = new NodoCola(valor);
        if (fondo != null) fondo.siguiente = nuevoNodo;
        fondo = nuevoNodo;
        if (frente == null) frente = nuevoNodo;
    }

    /**
     * Elimina y devuelve el nodo al frente de la cola.
     * @return El nodo eliminado, o null si la cola está vacía.
     */
    public Nodo desencolar() {
        if (frente == null) return null;
        Nodo valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) fondo = null;
        return valor;
    }

    /**
     * Verifica si la cola está vacía.
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return frente == null;
    }
}
