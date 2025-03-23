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
/*
*
* @autor Fernando, Anthony, Santiago
*/
public class JSONLoader {

    public static Arbol cargarArbolDesdeJson(String rutaArchivo) throws IOException, org.json.JSONException {
        String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        JSONObject json = new JSONObject(contenido);

        // Verificar que exista al menos una clave en el JSON (p.ej. "Especies")
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

        // Insertar todas las especies (ahora sin exigir que tengan todas el mismo número de preguntas)
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
     * en el árbol, empezando en nodoActual (generalmente la raíz).
     * No requiere que todas las especies tengan la misma cantidad de preguntas.
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

            // Si el nodo actual es null (muy raro), lo creamos
            if (nodoActual == null) {
                nodoActual = new Nodo(pregunta);
            }

            // Si el nodo actual ya es hoja pero aún quedan preguntas,
            // lo convertimos en un nodo de pregunta para poder continuar.
            if (nodoActual.esHoja() && j < preguntasArray.length() - 1) {
                nodoActual.setValor(pregunta);
                nodoActual.setNodoSi(null);
                nodoActual.setNodoNo(null);
            }

            // Forzar la pregunta si difiere del valor del nodo actual
            if (!nodoActual.getValor().equals(pregunta)) {
                nodoActual.setValor(pregunta);
                nodoActual.setNodoSi(null);
                nodoActual.setNodoNo(null);
            }

            // Si estamos en la última pregunta (j == preguntasArray.length()-1),
            // se crea la hoja final con el nombre de la especie.
            if (j == preguntasArray.length() - 1) {
                Nodo nodoEspecie = new Nodo(nombreEspecie);
                if (respuesta) {
                    nodoActual.setNodoSi(nodoEspecie);
                } else {
                    nodoActual.setNodoNo(nodoEspecie);
                }
                break;
            }
            // De lo contrario, seguimos a la siguiente pregunta en la rama que corresponda
            else {
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
     * Carga la tabla hash desde el mismo archivo JSON (lógica existente).
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
