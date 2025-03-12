/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author Thony
 */
public class Nodo {
    String pregunta;
    Nodo si;
    Nodo no;
    
    public Nodo(String pregunta) {
        this.pregunta = pregunta;
        this.si = null;
        this.no = null;
    }
}
