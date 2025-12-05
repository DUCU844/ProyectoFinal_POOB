package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * Screen for level selection.
 * Allows the player to choose which level to play.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class PantallaSeleccionNivel extends JPanel {

    public PantallaSeleccionNivel(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("SELECCIONA EL NIVEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        add(titulo, BorderLayout.NORTH);

        // Crear botones para los 3 niveles
        JButton nivel1 = new JButton("Nivel 1");
        JButton nivel2 = new JButton("Nivel 2");
        JButton nivel3 = new JButton("Nivel 3");
        JButton volver = new JButton("Volver");

        // Panel con los botones
        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(4, 1, 10, 10));
        botones.add(nivel1);
        botones.add(nivel2);
        botones.add(nivel3);
        botones.add(volver);
        add(botones, BorderLayout.CENTER);

        // Action Listeners para iniciar el juego
        nivel1.addActionListener(e -> {
            ventana.iniciarJuego(1, "Vainilla", "Player"); // Iniciar nivel 1
        });
        
        nivel2.addActionListener(e -> {
            ventana.iniciarJuego(2, "Vainilla", "Player"); // Iniciar nivel 2
        });
        
        nivel3.addActionListener(e -> {
            ventana.iniciarJuego(3, "Vainilla", "Player"); // Iniciar nivel 3
        });

        volver.addActionListener(e -> ventana.mostrarMenuPrincipal());
    }
}