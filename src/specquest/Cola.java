/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package specquest;

/**
 * @autor Santiago, Fernando, Thony
 * Clase que implementa una cola (FIFO) de Nodos.
 * Se utiliza para recorridos BFS en el árbol.
 */
public class Cola {
    
    /** Referencia al frente de la cola. */
    private NodoCola frente;
    
    /** Referencia al final (fondo) de la cola. */
    private NodoCola fondo;

    /**
     * Clase interna que representa cada nodo dentro de la cola.
     */
    private static class NodoCola {
        /** El valor (nodo del árbol) almacenado en este elemento de la cola. */
        Nodo valor;
        /** Siguiente nodo en la cola. */
        NodoCola siguiente;

        /**
         * Construye un nodo de cola con el valor dado.
         * @param valor Nodo de árbol que se almacena en la cola.
         */
        NodoCola(Nodo valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    /**
     * Encola un nodo al final de la cola.
     * @param valor El nodo que se desea encolar.
     */
    public void encolar(Nodo valor) {
        NodoCola nuevoNodo = new NodoCola(valor);
        if (fondo != null) {
            fondo.siguiente = nuevoNodo;
        }
        fondo = nuevoNodo;
        if (frente == null) {
            frente = nuevoNodo;
        }
    }

    /**
     * Desencola (retira) el nodo del frente de la cola.
     * @return El nodo del árbol que estaba en el frente, o null si la cola está vacía.
     */
    public Nodo desencolar() {
        if (frente == null) {
            return null;
        }
        Nodo valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) {
            fondo = null;
        }
        return valor;
    }

    /**
     * Indica si la cola está vacía (sin elementos).
     * @return true si está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return frente == null;
    }
}
