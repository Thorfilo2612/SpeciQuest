/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Clase que representa la interfaz gráfica de usuario para interactuar con el árbol dicotómico.
 * Permite al usuario responder preguntas (Sí/No) para navegar por el árbol y llegar a una especie.
 * También incluye funcionalidades para buscar especies en el árbol y en una tabla hash.
 * 
 * @author Santiago, Fernando y Anthony
 */

package specquest;
import javax.swing.*;
import java.awt.*;

public class ClaveDicotomica extends JFrame {
    private JLabel labelPregunta; // Etiqueta para mostrar la pregunta actual
    private JButton btnSi; // Botón para responder "Sí"
    private JButton btnNo; // Botón para responder "No"
    private JLabel labelResultado; // Etiqueta para mostrar el resultado final
    private JLabel labelTiempo; // Etiqueta para mostrar el tiempo de búsqueda
    private Arbol arbol; // Árbol dicotómico
    private TablaHash tablaHash; // Tabla hash para búsquedas rápidas

    /**
     * Constructor de la clase ClaveDicotomica.
     * @param arbol El árbol dicotómico que se utilizará en la interfaz.
     */
    public ClaveDicotomica(Arbol arbol) {
        this.arbol = arbol;
        inicializar();
        actualizarPregunta();
    }

    /**
     * Inicializa la interfaz gráfica.
     */
    private void inicializar() {
        setTitle("Clave Dicotómica");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panel central
        JPanel panelCentro = new JPanel(new GridLayout(4, 1, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        labelPregunta = new JLabel("La pregunta aparecerá aquí", SwingConstants.CENTER);
        labelPregunta.setFont(new Font("Arial", Font.BOLD, 20));
        panelCentro.add(labelPregunta);

        btnSi = new JButton("Sí");
        estilizarBoton(btnSi);
        btnSi.addActionListener(e -> manejarRespuesta(true));
        panelCentro.add(btnSi);

        btnNo = new JButton("No");
        estilizarBoton(btnNo);
        btnNo.addActionListener(e -> manejarRespuesta(false));
        panelCentro.add(btnNo);

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        labelResultado = new JLabel("Resultado: -", SwingConstants.CENTER);
        labelResultado.setFont(new Font("Arial", Font.BOLD, 18));
        panelInferior.add(labelResultado);

        labelTiempo = new JLabel("Tiempo: -", SwingConstants.CENTER);
        panelInferior.add(labelTiempo);

        JButton btnReiniciar = new JButton("Reiniciar");
        estilizarBoton(btnReiniciar);
        btnReiniciar.addActionListener(e -> reiniciarJuego());
        panelInferior.add(btnReiniciar);

        JButton btnBuscarHash = new JButton("Buscar en Hash");
        estilizarBoton(btnBuscarHash);
        btnBuscarHash.addActionListener(e -> buscarEnHash());
        panelInferior.add(btnBuscarHash);

        JButton btnBuscarArbol = new JButton("Buscar en Árbol");
        estilizarBoton(btnBuscarArbol);
        btnBuscarArbol.addActionListener(e -> buscarEnArbol());
        panelInferior.add(btnBuscarArbol);

        JButton btnMostrarArbol = new JButton("Mostrar Árbol");
        estilizarBoton(btnMostrarArbol);
        btnMostrarArbol.addActionListener(e -> arbol.mostrarArbolGrafico());
        panelInferior.add(btnMostrarArbol);

        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Estiliza un botón con colores y fuentes específicas.
     * @param boton El botón a estilizar.
     */
    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(new Color(100, 150, 255));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Maneja la respuesta del usuario (Sí/No) y avanza en el árbol.
     * @param respuesta true para "Sí", false para "No".
     */
    private void manejarRespuesta(boolean respuesta) {
        if (arbol.esResultadoFinal()) return;
        String resultado = arbol.avanzar(respuesta);
        if (arbol.esResultadoFinal()) {
            mostrarResultado(resultado);
        } else {
            actualizarPregunta();
        }
    }

    /**
     * Actualiza la pregunta mostrada en la interfaz.
     */
    private void actualizarPregunta() {
        labelPregunta.setText(arbol.getValorActual());
        labelResultado.setText("Resultado: -");
    }

    /**
     * Muestra el resultado final en la interfaz.
     * @param resultado El resultado final a mostrar.
     */
    private void mostrarResultado(String resultado) {
        labelResultado.setText("Resultado: " + resultado);
        btnSi.setEnabled(false);
        btnNo.setEnabled(false);
    }

    /**
     * Reinicia el juego, volviendo al inicio del árbol.
     */
    private void reiniciarJuego() {
        arbol.reiniciar();
        actualizarPregunta();
        btnSi.setEnabled(true);
        btnNo.setEnabled(true);
    }

    /**
     * Busca un valor en la tabla hash y muestra el resultado.
     */
    private void buscarEnHash() {
        String clave = JOptionPane.showInputDialog(this, "Ingrese el valor a buscar:");
        if (clave == null || clave.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long inicio = System.nanoTime();
        String resultado = tablaHash.buscar(clave);
        long fin = System.nanoTime();

        if (resultado.equals("No encontrado")) {
            JOptionPane.showMessageDialog(this, "El valor '" + clave + "' no fue encontrado.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            labelResultado.setText("Resultado: " + resultado);
            labelTiempo.setText("Tiempo: " + (fin - inicio) + " ns");
        }
    }

    /**
     * Busca un valor en el árbol utilizando BFS y DFS, y muestra los resultados.
     */
    private void buscarEnArbol() {
        String clave = JOptionPane.showInputDialog(this, "Ingrese el valor a buscar:");
        if (clave == null || clave.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long inicio = System.nanoTime();
        String resultadoBFS = arbol.buscarBFS(clave);
        long finBFS = System.nanoTime();
        String resultadoDFS = arbol.buscarDFS(clave);
        long finDFS = System.nanoTime();

        String mensaje = resultadoBFS + "\nTiempo BFS: " + (finBFS - inicio) + " ns\n" +
                        resultadoDFS + "\nTiempo DFS: " + (finDFS - finBFS) + " ns";
        JOptionPane.showMessageDialog(this, mensaje, "Resultados", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Establece la tabla hash que se utilizará para las búsquedas.
     * @param tablaHash La tabla hash a utilizar.
     */
    public void setTablaHash(TablaHash tablaHash) {
        this.tablaHash = tablaHash;
    }
}
