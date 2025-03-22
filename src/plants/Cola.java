/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plants;

/**
 *
 * @author Santiago, Fernando y Anthony
 */

public class Cola {
    private NodoCola frente;
    private NodoCola fondo;

    private static class NodoCola {
        Nodo valor;
        NodoCola siguiente;

        NodoCola(Nodo valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

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

    public Nodo desencolar() {
        if (frente == null) return null;
        Nodo valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) {
            fondo = null;
        }
        return valor;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}
