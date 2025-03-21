/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package specquest;

/**
 *
 * @author Santiago, Fernando y Anthony
 */

/**
 * Clase Pila, barrer por DFS usa esta pila
 */
public class Pila {
    private NodoPila cima;

    private static class NodoPila {
        Nodo valor;
        NodoPila siguiente;

        NodoPila(Nodo valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    public void apilar(Nodo valor) {
        NodoPila nuevoNodo = new NodoPila(valor);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
    }

    public Nodo desapilar() {
        if (cima == null) return null;
        Nodo valor = cima.valor;
        cima = cima.siguiente;
        return valor;
    }

    public boolean estaVacia() {
        return cima == null;
    }
}