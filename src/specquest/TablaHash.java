/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package specquest;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class TablaHash {
    private static final int TAMANO = 100;
    private NodoHash[] tabla;

    private static class NodoHash {
        String clave;
        String valor;
        NodoHash siguiente;

        NodoHash(String clave, String valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    public TablaHash() {
        tabla = new NodoHash[TAMANO];
    }

    // Función de hash personalizada
    private int hash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (31 * hash + clave.charAt(i)) % TAMANO;
        }
        return hash;
    }

    // Insertar valor en la tabla
    public void insertar(String clave, String valor) {
        int indice = hash(clave);
        NodoHash nuevo = new NodoHash(clave, valor);

        if (tabla[indice] == null) {
            tabla[indice] = nuevo;
        } else {
            NodoHash actual = tabla[indice];
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    // Buscar valor en la tabla
    public String buscar(String clave) {
        int indice = hash(clave);
        NodoHash actual = tabla[indice];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return "No encontrado";
    }

    // Eliminar valor de la tabla
    public void eliminar(String clave) {
        int indice = hash(clave);
        NodoHash actual = tabla[indice];
        NodoHash previo = null;

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                if (previo == null) {
                    tabla[indice] = actual.siguiente;
                } else {
                    previo.siguiente = actual.siguiente;
                }
                return;
            }
            previo = actual;
            actual = actual.siguiente;
        }
    }
}
