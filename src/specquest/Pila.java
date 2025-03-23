/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package specquest;

/**
 * @ Santiago, Fernando, Thony
 * Clase que implementa una pila (LIFO) de Nodos, útil para
 * realizar búsquedas en profundidad (DFS) en el árbol.
 */
public class Pila {
    
    /** Referencia al nodo que está en la cima de la pila. */
    private NodoPila cima;

    /**
     * Clase interna que representa cada elemento (Nodo) en la pila.
     */
    private static class NodoPila {
        /** Valor que se almacena en la pila (un nodo del árbol). */
        Nodo valor;
        /** Referencia al siguiente elemento en la pila. */
        NodoPila siguiente;

        /**
         * Crea un nodo de pila a partir de un nodo del árbol.
         * @param valor Nodo del árbol que se almacena en la pila.
         */
        NodoPila(Nodo valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    /**
     * Apila (coloca en la cima) un nodo.
     * @param valor El nodo que se desea apilar.
     */
    public void apilar(Nodo valor) {
        NodoPila nuevoNodo = new NodoPila(valor);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
    }

    /**
     * Desapila (extrae) el nodo de la cima de la pila.
     * @return El nodo del árbol que estaba en la cima, o null si está vacía.
     */
    public Nodo desapilar() {
        if (cima == null) {
            return null;
        }
        Nodo valor = cima.valor;
        cima = cima.siguiente;
        return valor;
    }

    /**
     * Indica si la pila está vacía.
     * @return true si no hay nodos, false en caso contrario.
     */
    public boolean estaVacia() {
        return cima == null;
    }
}
