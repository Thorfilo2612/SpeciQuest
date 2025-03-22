/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plants;

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
        String primeraPregunta = null;
        
        //Constructor de la estructura del arbolito
        for (int i=0; i<especiesArray.length(); i++){
            JSONObject especieObj=especiesArray.getJSONObject(i);
            String nombreEspecie=especieObj.keys().next();
            JSONArray preguntasArray = especieObj.getJSONArray(nombreEspecie);
            
            //Aqui se valida que la primera pregunta sea comun
            String preguntaActual = preguntasArray.getJSONObject(0).keys().next();
            if(primeraPregunta==null) {
                primeraPregunta=preguntaActual;
                raiz=new Nodo(primeraPregunta);
                arbol.setRaiz(raiz);}
            else if (!primeraPregunta.equals(preguntaActual)) {
                throw new org.json.JSONException("Primera pregunta inconsistente"
                        + "en especie" + nombreEspecie);
            }
            //Constructor de camino de nodos
            Nodo nodoActual=raiz;
            for(int j=0; j<preguntasArray.length();j++){
                JSONObject preguntaObj=preguntasArray.getJSONObject(j);
                String pregunta=preguntaObj.keys().next();
                boolean respuesta=preguntaObj.getBoolean(pregunta);
                
                //Esto identifica la ultima pregunta y la convierte en una hoja
                if (j==preguntasArray.length()-1){
                    Nodo hoja=new Nodo(nombreEspecie);
                    if(respuesta){
                        if(nodoActual.getNodoSi()!=null && !nodoActual.getNodoSi()
                                .esHoja()) {
                            throw new org.json.JSONException("Conflicto en ruta para" +
                                    nombreEspecie);}
                        nodoActual.setNodoNo(hoja);}
                }else {
                    //Esto crea los nodos intermedios
                    Nodo siguienteNodo=respuesta ? nodoActual.getNodoSi():
                            nodoActual.getNodoNo();
                    if (siguienteNodo==null){
                        String siguientePregunta=preguntasArray.getJSONObject(j+1).keys().next();
                        siguienteNodo=new Nodo(siguientePregunta);
                        if (respuesta){
                            nodoActual.setNodoSi(siguienteNodo);
                        }else{
                            nodoActual.setNodoNo(siguienteNodo);
                        }
                    }
                    nodoActual=siguienteNodo;
                }
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
