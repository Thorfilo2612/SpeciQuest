/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plants;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Arbol {
    private Nodo raiz;
    private Nodo nodoActual;

    // Establecer el nodo raíz del árbol
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
        this.nodoActual = raiz;
    }

    // Obtener el nodo raíz
    public Nodo getRaiz() {
        return raiz;
    }

    // Reiniciar el recorrido del árbol
    public void reiniciar() {
        this.nodoActual = raiz;
    }

    // Avanzar en el árbol según la respuesta (true = sí, false = no)
    public String avanzar(boolean respuesta) {
        if (nodoActual != null) {
            if (respuesta) {
                nodoActual = nodoActual.getNodoSi();
            } else {
                nodoActual = nodoActual.getNodoNo();
            }
            // Si el nodo es hoja, devuelve el valor como resultado
            if (nodoActual != null && nodoActual.esHoja()) {
                return nodoActual.getValor(); // Resultado final (especie)
            }
            return nodoActual != null ? nodoActual.getValor() : "Fin del árbol";
        }
        return "No hay más opciones.";
    }

    // Verificar si se ha alcanzado un resultado final
    public boolean esResultadoFinal() {
        return nodoActual != null && nodoActual.esHoja();
    }

    // Obtener el valor del nodo actual
    public String getValorActual() {
        return nodoActual != null ? nodoActual.getValor() : "No hay valor disponible";
    }
    
    // Búsqueda por BFS (ancho)
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

    // Búsqueda por DFS (profundidad)
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
