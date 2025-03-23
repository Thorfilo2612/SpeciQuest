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
import java.util.Iterator;

/**
 *
 * @author Santiago Atacho
 */
public class JSONLoader {
    public static Arbol cargarArbolDesdeJson(String rutaArchivo) throws IOException, 
            org.json.JSONException{
        String contenido= new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        JSONObject json= new JSONObject(contenido);
        
        //Esto sirve para obtener el nombre de la clave dicotomica 
        Iterator<String> keys = json.keys();
        if (!keys.hasNext()) throw new org.json.JSONException("Formato invalido:"
                + "No hay clave dicotomica");
        String nombreClave= keys.next();
        JSONArray especiesArray= json.getJSONArray(nombreClave);
        
         Arbol arbol = new Arbol();
        Nodo raiz = null;
    

    // Validar que el JSON tenga al menos una especie
    if (especiesArray.length() == 0) {
        throw new org.json.JSONException("El JSON no contiene especies.");
    }

    // Construir la raíz a partir de la primera especie
    JSONObject primeraEspecieObj = especiesArray.getJSONObject(0);
    String nombrePrimeraEspecie = primeraEspecieObj.keys().next();
    JSONArray primeraPreguntasArray = primeraEspecieObj.getJSONArray(nombrePrimeraEspecie);

    // Obtener la primera pregunta (debe existir)
    if (primeraPreguntasArray.length() == 0) {
        throw new org.json.JSONException("La primera especie no tiene preguntas.");
    }
    String primeraPregunta = primeraPreguntasArray.getJSONObject(0).keys().next();
    raiz = new Nodo(primeraPregunta);
    arbol.setRaiz(raiz);

    // Procesar todas las especies
    for (int i = 0; i < especiesArray.length(); i++) {
        JSONObject especieObj = especiesArray.getJSONObject(i);
        String nombreEspecie = especieObj.keys().next();
        JSONArray preguntasArray = especieObj.getJSONArray(nombreEspecie);

    // Validar que todas las especies tengan al menos una pregunta
    if (preguntasArray.length() == 0) {
        throw new org.json.JSONException("La especie '" + nombreEspecie + "' no tiene preguntas.");
    }

    // Validar primera pregunta coincidente
    String preguntaActual = preguntasArray.getJSONObject(0).keys().next();
    if (!primeraPregunta.equals(preguntaActual)) {
    throw new org.json.JSONException("Primera pregunta inconsistente en especie: " + nombreEspecie);
       }

    Nodo nodoActual = raiz; // Reiniciar a la raíz para cada especie

    // Construir ruta para la especie
    for (int j = 0; j < preguntasArray.length(); j++) {
        JSONObject preguntaObj = preguntasArray.getJSONObject(j);
        String pregunta = preguntaObj.keys().next();
        boolean respuesta = preguntaObj.getBoolean(pregunta);
            
    // Crear nodos SIEMPRE para ambas respuestas (true/false)
    String siguientePregunta = (j < preguntasArray.length() - 1) 
    ? preguntasArray.getJSONObject(j + 1).keys().next() 
    : nombreEspecie;
    // Crear nodos hijos
    Nodo nodoSi = (j == preguntasArray.length() - 1) 
    ? new Nodo(nombreEspecie) 
    : new Nodo(siguientePregunta);

    Nodo nodoNo = (j == preguntasArray.length() - 1) 
    ? new Nodo(nombreEspecie) 
    : new Nodo(siguientePregunta);

    // Asignar ambos hijos al nodo actual
    nodoActual.setNodoSi(nodoSi);
    nodoActual.setNodoNo(nodoNo);

    // Mover al nodo correspondiente según la respuesta del JSON
    nodoActual = respuesta ? nodoSi : nodoNo;
    }
        }

    return arbol;

    }    
       //Aqui se hace lo de la tabla hash
     public static TablaHash cargarTablaHashDesdeJSON(String rutaArchivo) throws IOException, org.json.JSONException {
  
        String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
    JSONObject json = new JSONObject(contenido);
    
    // Obtener el nombre de la clave dicotómica
    Iterator<String> keys = json.keys();
    if (!keys.hasNext()) throw new org.json.JSONException("Formato inválido");
    String nombreClave = keys.next();
    JSONArray especiesArray = json.getJSONArray(nombreClave);

    TablaHash tablaHash = new TablaHash();

    for (int i = 0; i < especiesArray.length(); i++) {
        JSONObject especieObj = especiesArray.getJSONObject(i);
        String nombreEspecie = especieObj.keys().next();
        JSONArray preguntasArray = especieObj.getJSONArray(nombreEspecie);

    // Construir la cadena de características
    StringBuilder caracteristicas = new StringBuilder();
    for (int j = 0; j < preguntasArray.length(); j++) {
        JSONObject preguntaObj = preguntasArray.getJSONObject(j);
        String pregunta = preguntaObj.keys().next();
        boolean respuesta = preguntaObj.getBoolean(pregunta);
            
        caracteristicas.append(pregunta)
                       .append(": ")
                       .append(respuesta ? "Sí" : "No")
                       .append(j < preguntasArray.length() - 1 ? "; " : "");
        }

    // Insertar en la tabla hash
    tablaHash.insertar(nombreEspecie, caracteristicas.toString());
    }

    return tablaHash;
       
            }
}

