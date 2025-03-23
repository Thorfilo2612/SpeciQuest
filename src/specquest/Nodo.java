/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package specquest;

/**
 * @autor Santiago, Fernando, Thony
 * Representa un nodo de un árbol dicotómico.
 * Puede contener una pregunta o el nombre de una especie (si es hoja).
 */
public class Nodo {
    
    /** Valor que guarda el nodo (pregunta o especie). */
    private String valor;
    
    /** Referencia al nodo hijo para respuesta "Sí". */
    private Nodo nodoSi;
    
    /** Referencia al nodo hijo para respuesta "No". */
    private Nodo nodoNo;

    /**
     * Construye un nodo con el valor especificado.
     * @param valor Texto que el nodo representa (pregunta o especie).
     */
    public Nodo(String valor) {
        this.valor = valor;
        this.nodoSi = null;
        this.nodoNo = null;
    }

    /**
     * Asigna un nodo hijo para la respuesta "Sí".
     * @param nodo Nodo que representa la rama "Sí".
     */
    public void setNodoSi(Nodo nodo) {
        this.nodoSi = nodo;
    }

    /**
     * Asigna un nodo hijo para la respuesta "No".
     * @param nodo Nodo que representa la rama "No".
     */
    public void setNodoNo(Nodo nodo) {
        this.nodoNo = nodo;
    }

    /**
     * Cambia el valor (pregunta/especie) de este nodo.
     * @param valor Nuevo valor que se desea asignar.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Obtiene la cadena de texto representada por este nodo.
     * @return El valor (pregunta/especie) del nodo.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Obtiene la rama correspondiente a la respuesta "Sí".
     * @return El nodo hijo "Sí", o null si no existe.
     */
    public Nodo getNodoSi() {
        return nodoSi;
    }

    /**
     * Obtiene la rama correspondiente a la respuesta "No".
     * @return El nodo hijo "No", o null si no existe.
     */
    public Nodo getNodoNo() {
        return nodoNo;
    }

    /**
     * Indica si el nodo es una hoja (no tiene nodos hijos).
     * @return true si es hoja, false en caso contrario.
     */
    public boolean esHoja() {
        return nodoSi == null && nodoNo == null;
    }
}
