/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package specquest;

/**
 * @author Thony, Fernando, Santiago
 *
 * Clase que representa un árbol dicotómico, donde cada nodo
 * tiene la pregunta/valor y dos posibles ramas ("sí" y "no").
 */
public class Arbol {
    
    /** Nodo raíz del árbol. */
    private Nodo raiz;
    
    /** Referencia al nodo que se recorre actualmente. */
    private Nodo nodoActual;

    /**
     * Asigna el nodo raíz del árbol y, además, inicializa
     * {@code nodoActual} con ese mismo nodo.
     *
     * @param raiz El nodo que será la raíz del árbol.
     */
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
        this.nodoActual = raiz;
    }

    /**
     * Devuelve la referencia al nodo raíz.
     *
     * @return El nodo raíz del árbol.
     */
    public Nodo getRaiz() {
        return raiz;
    }

    /**
     * Reinicia el recorrido, volviendo a apuntar {@code nodoActual}
     * a la raíz del árbol.
     */
    public void reiniciar() {
        this.nodoActual = raiz;
    }

    /**
     * Avanza en el árbol según la respuesta dada (true = "sí", false = "no").
     * Actualiza {@code nodoActual} a la rama correspondiente.
     *
     * @param respuesta Valor booleano: true (ramificación "sí") o false ("no").
     * @return El valor (pregunta o especie) del nuevo nodo; 
     *         o mensaje de aviso si se llegó a un nodo nulo.
     */
    public String avanzar(boolean respuesta) {
        if (nodoActual == null) {
            return "No hay más opciones.";
        }
        // Avanza hacia la rama correspondiente
        nodoActual = respuesta ? nodoActual.getNodoSi() : nodoActual.getNodoNo();

        if (nodoActual == null) {
            return "Ruta no definida en el árbol.";
        }
        
        // Retorna el valor del nodo actual (puede ser pregunta o especie)
        return nodoActual.getValor();
    }

    /**
     * Indica si el nodo actual es un resultado final (especie),
     * es decir, un nodo hoja.
     *
     * @return true si {@code nodoActual} es hoja, false en caso contrario.
     */
    public boolean esResultadoFinal() {
        return nodoActual != null && nodoActual.esHoja();
    }

    /**
     * Obtiene el valor (pregunta o especie) del nodo actual.
     *
     * @return El texto representado en el nodo actual, 
     *         o un mensaje indicando que no hay valor disponible.
     */
    public String getValorActual() {
        return (nodoActual != null) ? nodoActual.getValor() : "No hay valor disponible";
    }
    
    /**
     * Realiza una búsqueda en anchura (BFS) del {@code valorBuscado}.
     *
     * @param valorBuscado El valor que se desea localizar en el árbol.
     * @return El valor encontrado, o "Valor no encontrado" si no existe.
     */
    public String buscarBFS(String valorBuscado) {
        Cola cola = new Cola();
        cola.encolar(raiz);

        while (!cola.estaVacia()) {
            Nodo nodo = cola.desencolar();
            if (nodo.getValor().equalsIgnoreCase(valorBuscado)) {
                return nodo.getValor();
            }
            if (nodo.getNodoSi() != null) {
                cola.encolar(nodo.getNodoSi());
            }
            if (nodo.getNodoNo() != null) {
                cola.encolar(nodo.getNodoNo());
            }
        }
        return "Valor no encontrado";
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) del {@code valorBuscado}.
     *
     * @param valorBuscado El valor que se desea localizar.
     * @return El valor encontrado, o "Valor no encontrado" si no existe.
     */
    public String buscarDFS(String valorBuscado) {
        Pila pila = new Pila();
        pila.apilar(raiz);

        while (!pila.estaVacia()) {
            Nodo nodo = pila.desapilar();
            if (nodo.getValor().equalsIgnoreCase(valorBuscado)) {
                return nodo.getValor();
            }
            if (nodo.getNodoNo() != null) {
                pila.apilar(nodo.getNodoNo());
            }
            if (nodo.getNodoSi() != null) {
                pila.apilar(nodo.getNodoSi());
            }
        }
        return "Valor no encontrado";
    }
}
