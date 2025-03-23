/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package specquest;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *  
 * @author Fernando, Anthony, Santiago 
 *
 * Clase que maneja la carga de un árbol y una tabla hash 
 * a partir de un archivo JSON.
 */
public class JSONLoader {

    /**
     * Carga un árbol dicotómico desde un archivo JSON, sin
     * requerir que todas las especies tengan la misma cantidad de preguntas.
     *
     * @param rutaArchivo Ruta al archivo JSON con la estructura de preguntas/especies.
     * @return El árbol dicotómico construido.
     * @throws IOException Si ocurre un problema al leer el archivo.
     * @throws org.json.JSONException Si la estructura JSON no es válida.
     */
    public static Arbol cargarArbolDesdeJson(String rutaArchivo) throws IOException, org.json.JSONException {
        String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        JSONObject json = new JSONObject(contenido);

        // Verificar que exista al menos una clave en el JSON (por ejemplo "Especies")
        JSONArray claves = json.names();
        if (claves == null || claves.length() == 0) {
            throw new org.json.JSONException("Formato inválido: No hay clave dicotómica en el JSON.");
        }
        String nombreClave = claves.getString(0);
        JSONArray especiesArray = json.getJSONArray(nombreClave);

        // Verificar que haya al menos una especie
        if (especiesArray.length() == 0) {
            throw new org.json.JSONException("El JSON no contiene ninguna especie en '" + nombreClave + "'.");
        }

        // Tomar la primera especie para crear la raíz
        JSONObject primeraEspecieObj = especiesArray.getJSONObject(0);
        String nombrePrimeraEspecie = primeraEspecieObj.keys().next();
        JSONArray primeraPreguntasArray = primeraEspecieObj.getJSONArray(nombrePrimeraEspecie);

        // Validar que la primera especie tenga al menos 1 pregunta
        if (primeraPreguntasArray.length() == 0) {
            throw new org.json.JSONException("La primera especie '" + nombrePrimeraEspecie + "' no tiene preguntas.");
        }

        // Obtener la primera pregunta
        String primeraPregunta = primeraPreguntasArray.getJSONObject(0).keys().next();

        // Crear el árbol y la raíz
        Arbol arbol = new Arbol();
        Nodo raiz = new Nodo(primeraPregunta);
        arbol.setRaiz(raiz);

        // Insertar todas las especies (sin exigir igual número de preguntas)
        for (int i = 0; i < especiesArray.length(); i++) {
            JSONObject especieObj = especiesArray.getJSONObject(i);
            String nombreEspecie = especieObj.keys().next();
            JSONArray preguntasArray = especieObj.getJSONArray(nombreEspecie);

            // Asegurarnos de que al menos tenga 1 pregunta
            if (preguntasArray.length() == 0) {
                throw new org.json.JSONException("La especie '" + nombreEspecie + "' no tiene preguntas.");
            }

            // Insertar la ruta de esta especie partiendo de la raíz
            insertarEspecie(raiz, preguntasArray, nombreEspecie);
        }

        return arbol;
    }

    /**
     * Inserta una secuencia de preguntas (pregunta -> respuesta [true/false])
     * en el árbol, empezando en {@code nodoActual} (generalmente la raíz).
     * No requiere que todas las especies tengan la misma cantidad de preguntas.
     *
     * @param nodoActual El nodo de inicio (típicamente la raíz).
     * @param preguntasArray Arreglo JSON que contiene preguntas y respuestas booleanas.
     * @param nombreEspecie Nombre de la especie (último elemento si se llega a la hoja).
     */
    private static void insertarEspecie(Nodo nodoActual, JSONArray preguntasArray, String nombreEspecie) {
        for (int j = 0; j < preguntasArray.length(); j++) {
            JSONObject preguntaObj = preguntasArray.getJSONObject(j);

            // Control: verificar que no sea un objeto vacío
            if (!preguntaObj.keys().hasNext()) {
                throw new RuntimeException("El JSON de '" + nombreEspecie 
                        + "' contiene un objeto vacío en el índice " + j);
            }

            String pregunta = preguntaObj.keys().next();
            boolean respuesta = preguntaObj.getBoolean(pregunta);

            // Si el nodo actual es null (muy raro), se crea
            if (nodoActual == null) {
                nodoActual = new Nodo(pregunta);
            }

            // Si el nodo actual ya es hoja pero aún quedan preguntas,
            // lo convertimos en nodo de pregunta
            if (nodoActual.esHoja() && j < preguntasArray.length() - 1) {
                nodoActual.setValor(pregunta);
                nodoActual.setNodoSi(null);
                nodoActual.setNodoNo(null);
            }

            // Forzamos la pregunta si difiere del valor actual
            if (!nodoActual.getValor().equals(pregunta)) {
                nodoActual.setValor(pregunta);
                nodoActual.setNodoSi(null);
                nodoActual.setNodoNo(null);
            }

            // Si es la última pregunta, se crea la hoja con el nombre de la especie
            if (j == preguntasArray.length() - 1) {
                Nodo nodoEspecie = new Nodo(nombreEspecie);
                if (respuesta) {
                    nodoActual.setNodoSi(nodoEspecie);
                } else {
                    nodoActual.setNodoNo(nodoEspecie);
                }
                break;
            } else {
                // Aún no es la última pregunta
                JSONObject siguienteObj = preguntasArray.getJSONObject(j + 1);
                if (!siguienteObj.keys().hasNext()) {
                    throw new RuntimeException("Objeto JSON vacío en índice " + (j+1) 
                            + " para la especie '" + nombreEspecie + "'.");
                }
                String siguientePregunta = siguienteObj.keys().next();

                if (respuesta) {
                    // Rama "Sí"
                    if (nodoActual.getNodoSi() == null) {
                        nodoActual.setNodoSi(new Nodo(siguientePregunta));
                    }
                    nodoActual = nodoActual.getNodoSi();
                } else {
                    // Rama "No"
                    if (nodoActual.getNodoNo() == null) {
                        nodoActual.setNodoNo(new Nodo(siguientePregunta));
                    }
                    nodoActual = nodoActual.getNodoNo();
                }
            }
        }
    }

    /**
     * Carga una tabla hash desde el mismo archivo JSON.
     *
     * @param rutaArchivo Ruta al archivo JSON a procesar.
     * @return Instancia de {@code TablaHash} con todos los valores insertados.
     * @throws IOException Si hay problemas al leer el archivo.
     * @throws org.json.JSONException Si el contenido JSON no es válido.
     */
    public static TablaHash cargarTablaHashDesdeJSON(String rutaArchivo) throws IOException, org.json.JSONException {
        String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        JSONObject json = new JSONObject(contenido);

        JSONArray claves = json.names();
        if (claves == null || claves.length() == 0) {
            throw new org.json.JSONException("Formato inválido: no hay clave en el JSON.");
        }
        String nombreClave = claves.getString(0);
        JSONArray especiesArray = json.getJSONArray(nombreClave);

        TablaHash tablaHash = new TablaHash();
        for (int i = 0; i < especiesArray.length(); i++) {
            JSONObject especieObj = especiesArray.getJSONObject(i);
            String nombreEspecie = especieObj.keys().next();
            JSONArray preguntasArray = especieObj.getJSONArray(nombreEspecie);

            StringBuilder caracteristicas = new StringBuilder();
            for (int j = 0; j < preguntasArray.length(); j++) {
                JSONObject preguntaObj = preguntasArray.getJSONObject(j);
                if (!preguntaObj.keys().hasNext()) {
                    throw new RuntimeException(
                        "Objeto JSON vacío en la especie '" + nombreEspecie + "', índice j=" + j
                    );
                }
                String pregunta = preguntaObj.keys().next();
                boolean resp = preguntaObj.getBoolean(pregunta);
                caracteristicas
                    .append(pregunta)
                    .append(": ")
                    .append(resp ? "Sí" : "No")
                    .append("; ");
            }
            tablaHash.insertar(nombreEspecie, caracteristicas.toString());
        }
        return tablaHash;
    }
}
