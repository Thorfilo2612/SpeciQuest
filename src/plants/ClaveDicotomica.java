/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package plants;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Santiago, Fernando y Anthony
 */

public class ClaveDicotomica extends JFrame{
    private JLabel labelPregunta;
    private JButton btnSi;
    private JButton btnNo;
    private JButton btnReiniciar;
    private JLabel labelResultado;
    private JLabel labelTiempo;

    private Arbol arbol;
    private TablaHash tablaHash = new TablaHash();

    public ClaveDicotomica(Arbol arbol) {
        this.arbol = arbol;
        inicializarGUI();
        actualizarPregunta();
    }

    private void inicializarGUI() {
        // Configurar ventana
        setTitle("Clave Dicotómica");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panel de preguntas y botones
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(4, 1, 10, 10));
        panelCentro.setBackground(new Color(240, 240, 240));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Mostrar pregunta
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

        // Panel inferior para resultados y controles
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 2, 10, 10));
        panelInferior.setBackground(new Color(240, 240, 240));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Resultado
        labelResultado = new JLabel("Resultado: -", SwingConstants.CENTER);
        labelResultado.setFont(new Font("Arial", Font.BOLD, 18));
        labelResultado.setForeground(new Color(60, 60, 60));
        panelInferior.add(labelResultado);

        // Tiempo de búsqueda
        labelTiempo = new JLabel("Tiempo: -", SwingConstants.CENTER);
        labelTiempo.setFont(new Font("Arial", Font.PLAIN, 16));
        labelTiempo.setForeground(new Color(100, 100, 100));
        panelInferior.add(labelTiempo);

        // Botón para reiniciar
        btnReiniciar = new JButton("Reiniciar");
        estilizarBoton(btnReiniciar);
        btnReiniciar.addActionListener(e -> reiniciarJuego());
        panelInferior.add(btnReiniciar);

        // Botón para búsqueda en hash
        JButton btnBuscarHash = new JButton("Buscar en Tabla Hash");
        estilizarBoton(btnBuscarHash);
        btnBuscarHash.addActionListener(e -> buscarEnHash());
        panelInferior.add(btnBuscarHash);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Método para aplicar estilo uniforme a los botones.
     */
    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(new Color(100, 150, 255));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto de hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(80, 130, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 150, 255));
            }
        });
    }

    /**
     * Manejar respuestas y avanzar en el árbol.
     */
    private void manejarRespuesta(boolean respuesta) {
        if (arbol.esResultadoFinal()) {
            return;
        }

        String resultado = arbol.avanzar(respuesta);

        if (arbol.esResultadoFinal()) {
            mostrarResultado(resultado);
        } else {
            actualizarPregunta();
        }
    }

    /**
     * Actualizar la pregunta actual.
     */
    private void actualizarPregunta() {
        labelPregunta.setText(arbol.getValorActual());
        labelResultado.setText("Resultado: -");
    }

    /**
     * Mostrar el resultado final.
     */
    private void mostrarResultado(String resultado) {
        labelResultado.setText("Resultado: " + resultado);
        btnSi.setEnabled(false);
        btnNo.setEnabled(false);
    }

    /**
     * Reiniciar juego.
     */
    private void reiniciarJuego() {
        arbol.reiniciar();
        actualizarPregunta();
        btnSi.setEnabled(true);
        btnNo.setEnabled(true);
    }

    /**
     * Buscar en la tabla de hash.
     */
    private void buscarEnHash() {
        String clave = JOptionPane.showInputDialog(this, "Ingrese el valor a buscar en la tabla:");
        if (clave == null || clave.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long inicio = System.nanoTime();
        String resultado = tablaHash.buscar(clave);
        long fin = System.nanoTime();

        if (resultado.equals("No encontrado")) {
            JOptionPane.showMessageDialog(this, "El valor '" + clave + "' no fue encontrado en la tabla.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            labelResultado.setText("Resultado: " + resultado);
            labelTiempo.setText("Tiempo: " + (fin - inicio) + " ns");
        }
    }
}
