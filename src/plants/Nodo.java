/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plants;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Nodo {
    private String valor; // Pregunta o respuesta
    private Nodo nodoSi; // Nodo hijo para respuesta "Sí"
    private Nodo nodoNo; // Nodo hijo para respuesta "No"

    // Constructor para crear un nodo con valor
    public Nodo(String valor) {
        this.valor = valor;
        this.nodoSi = null;
        this.nodoNo = null;
    }

    // Establecer nodo para respuesta "Sí"
    public void setNodoSi(Nodo nodo) {
        this.nodoSi = nodo;
    }

    // Establecer nodo para respuesta "No"
    public void setNodoNo(Nodo nodo) {
        this.nodoNo = nodo;
    }

    // Obtener valor del nodo
    public String getValor() {
        return valor;
    }

    // Obtener nodo para respuesta "Sí"
    public Nodo getNodoSi() {
        return nodoSi;
    }

    // Obtener nodo para respuesta "No"
    public Nodo getNodoNo() {
        return nodoNo;
    }

    // Verificar si es una hoja (si no tiene hijos)
    public boolean esHoja() {
        return nodoSi == null && nodoNo == null;
    }
}
