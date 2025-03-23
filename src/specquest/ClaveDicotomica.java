/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package specquest;

import javax.swing.*;
import java.awt.*;

/**
 * @autor Santiago, Fernando, Thony
 * Clase que gestiona la interfaz gráfica para recorrer
 * el árbol dicotómico y mostrar preguntas y respuestas.
 */
public class ClaveDicotomica extends JFrame {
    
    /** Etiqueta que muestra la pregunta actual. */
    private JLabel labelPregunta;
    
    /** Botón para responder "Sí". */
    private JButton btnSi;
    
    /** Botón para responder "No". */
    private JButton btnNo;
    
    /** Botón para reiniciar la clave dicotómica. */
    private JButton btnReiniciar;
    
    /** Etiqueta que muestra el resultado final. */
    private JLabel labelResultado;
    
    /** Etiqueta que muestra el tiempo de ejecución (en ns). */
    private JLabel labelTiempo;

    /** Referencia al árbol dicotómico que se recorre. */
    protected Arbol arbol;
    
    /** Tabla hash para búsquedas complementarias. */
    protected TablaHash tablaHash = new TablaHash();

    /**
     * Constructor que recibe el árbol a usar en la interfaz.
     * @param arbol Árbol dicotómico ya construido con preguntas/especies.
     */
    public ClaveDicotomica(Arbol arbol) {
        this.arbol = arbol;
        inicializarGUI();
        actualizarPregunta();
    }

    /**
     * Método privado para configurar los elementos visuales de la interfaz.
     */
    private void inicializarGUI() {
        // Configuración de la ventana principal
        setTitle("Clave Dicotómica");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panel central (preguntas y botones)
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(4, 1, 10, 10));
        panelCentro.setBackground(new Color(240, 240, 240));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Label con la pregunta actual
        labelPregunta = new JLabel("La pregunta aparecerá aquí", SwingConstants.CENTER);
        labelPregunta.setFont(new Font("Arial", Font.BOLD, 20));
        labelPregunta.setForeground(new Color(50, 50, 50));
        panelCentro.add(labelPregunta);

        // Botón "Sí"
        btnSi = new JButton("Sí");
        estilizarBoton(btnSi);
        btnSi.addActionListener(e -> manejarRespuesta(true));
        panelCentro.add(btnSi);

        // Botón "No"
        btnNo = new JButton("No");
        estilizarBoton(btnNo);
        btnNo.addActionListener(e -> manejarRespuesta(false));
        panelCentro.add(btnNo);

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior para resultado y búsqueda
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 2, 10, 10));
        panelInferior.setBackground(new Color(240, 240, 240));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Etiqueta para mostrar el resultado
        labelResultado = new JLabel("Resultado: -", SwingConstants.CENTER);
        labelResultado.setFont(new Font("Arial", Font.BOLD, 18));
        labelResultado.setForeground(new Color(60, 60, 60));
        panelInferior.add(labelResultado);

        // Etiqueta que muestra el tiempo de alguna operación (búsqueda)
        labelTiempo = new JLabel("Tiempo: -", SwingConstants.CENTER);
        labelTiempo.setFont(new Font("Arial", Font.PLAIN, 16));
        labelTiempo.setForeground(new Color(100, 100, 100));
        panelInferior.add(labelTiempo);

        // Botón para reiniciar la navegación en el árbol
        btnReiniciar = new JButton("Reiniciar");
        estilizarBoton(btnReiniciar);
        btnReiniciar.addActionListener(e -> reiniciarJuego());
        panelInferior.add(btnReiniciar);

        // Botón para búsqueda en la tabla hash
        JButton btnBuscarHash = new JButton("Buscar en Tabla Hash");
        estilizarBoton(btnBuscarHash);
        btnBuscarHash.addActionListener(e -> buscarEnHash());
        panelInferior.add(btnBuscarHash);

        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Aplica un estilo uniforme a los botones,
     * incluyendo color, fuente y efectos de hover.
     * 
     * @param boton El botón al que se aplicará el estilo.
     */
    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(new Color(100, 150, 255));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(80, 130, 255));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 150, 255));
            }
        });
    }

    /**
     * Maneja la respuesta (sí/no) dada por el usuario y
     * avanza en el árbol si no se ha llegado a un resultado.
     * 
     * @param respuesta true para "sí", false para "no".
     */
    private void manejarRespuesta(boolean respuesta) {
        // Si ya se llegó a un resultado final, no avanza
        if (arbol.esResultadoFinal()) {
            return;
        }

        String resultado = arbol.avanzar(respuesta);

        // Si el nodo actual es hoja, mostramos el resultado
        if (arbol.esResultadoFinal()) {
            mostrarResultado(resultado);
        } else {
            actualizarPregunta();
        }
    }

    /**
     * Actualiza la pregunta mostrada en la etiqueta
     * con el valor actual del árbol.
     */
    private void actualizarPregunta() {
        labelPregunta.setText(arbol.getValorActual());
        labelResultado.setText("Resultado: -");
    }

    /**
     * Muestra el resultado final (especie) y deshabilita
     * los botones de respuesta.
     * 
     * @param resultado Texto o especie resultante.
     */
    private void mostrarResultado(String resultado) {
        labelResultado.setText("Resultado: " + resultado);
        btnSi.setEnabled(false);
        btnNo.setEnabled(false);
    }

    /**
     * Reinicia el juego, es decir, vuelve la navegación
     * al nodo raíz y habilita los botones.
     */
    private void reiniciarJuego() {
        arbol.reiniciar();
        actualizarPregunta();
        btnSi.setEnabled(true);
        btnNo.setEnabled(true);
    }

    /**
     * Pide al usuario una clave para buscarla en la tabla hash,
     * mostrando el resultado y el tiempo de la operación.
     */
    private void buscarEnHash() {
        String clave = JOptionPane.showInputDialog(this, "Ingrese el valor a buscar en la tabla:");
        if (clave == null || clave.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor válido.", 
                                          "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long inicio = System.nanoTime();
        String resultado = tablaHash.buscar(clave);
        long fin = System.nanoTime();

        if (resultado.equals("No encontrado")) {
            JOptionPane.showMessageDialog(this,
                "El valor '" + clave + "' no fue encontrado en la tabla.",
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            labelResultado.setText("Resultado: " + resultado);
            labelTiempo.setText("Tiempo: " + (fin - inicio) + " ns");
        }
    }
}
