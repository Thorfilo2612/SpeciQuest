/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package specquest;

/**
 * @autor Santiago, Fernando, Thony
 * Implementación básica de una tabla hash para almacenar pares (clave, valor).
 * Se utiliza para hacer búsquedas rápidas en base a un string (por ejemplo, nombre de especie).
 */
public class TablaHash {
    
    /** Tamaño de la tabla de hash. */
    private static final int TAMANO = 100;
    
    /** Arreglo de listas enlazadas (a través de NodoHash). */
    private NodoHash[] tabla;

    /**
     * Clase interna que representa un nodo en la lista enlazada para
     * manejar colisiones en la tabla hash.
     */
    private static class NodoHash {
        /** Clave del elemento. */
        String clave;
        /** Valor asociado a esa clave. */
        String valor;
        /** Siguiente nodo en la lista de colisión. */
        NodoHash siguiente;

        /**
         * Construye un nodo para la tabla hash.
         * @param clave Clave a almacenar.
         * @param valor Valor asociado a la clave.
         */
        NodoHash(String clave, String valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    /**
     * Constructor que inicializa el arreglo de la tabla con el tamaño fijo.
     */
    public TablaHash() {
        tabla = new NodoHash[TAMANO];
    }

    /**
     * Función de dispersión (hash) que calcula un índice a partir de la cadena.
     * 
     * @param clave Cadena a dispersar.
     * @return Índice entre 0 y TAMANO-1.
     */
    private int hash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (31 * hash + clave.charAt(i)) % TAMANO;
        }
        return hash;
    }

    /**
     * Inserta un par (clave, valor) en la tabla hash. 
     * Si hay colisión, lo enlaza al final de la lista en esa posición.
     * 
     * @param clave La clave a usar en la tabla.
     * @param valor El valor asociado a la clave.
     */
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

    /**
     * Busca en la tabla el valor asociado a una clave dada.
     * 
     * @param clave Clave que se desea buscar.
     * @return El valor asociado si se encuentra, o la cadena "No encontrado" en caso contrario.
     */
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

    /**
     * Elimina de la tabla la entrada asociada a una clave dada.
     * 
     * @param clave Clave cuyo par se desea eliminar.
     */
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
