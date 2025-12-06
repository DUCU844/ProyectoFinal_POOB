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
        setBackground(new Color(30, 30, 50));

        JLabel titulo = new JLabel("SELECCIONA EL NIVEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.CYAN);
        add(titulo, BorderLayout.NORTH);

        // Panel central con opciones
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(30, 30, 50));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Selector de modo de juego
        JLabel modoLabel = new JLabel("Modo de juego:");
        modoLabel.setForeground(Color.WHITE);
        modoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JRadioButton onePlayerBtn = new JRadioButton("1 Jugador", true);
        onePlayerBtn.setBackground(new Color(30, 30, 50));
        onePlayerBtn.setForeground(Color.WHITE);
        onePlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JRadioButton twoPlayersBtn = new JRadioButton("2 Jugadores");
        twoPlayersBtn.setBackground(new Color(30, 30, 50));
        twoPlayersBtn.setForeground(Color.WHITE);
        twoPlayersBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ButtonGroup modoGroup = new ButtonGroup();
        modoGroup.add(onePlayerBtn);
        modoGroup.add(twoPlayersBtn);
        
        centerPanel.add(modoLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(onePlayerBtn);
        centerPanel.add(twoPlayersBtn);
        centerPanel.add(Box.createVerticalStrut(30));
        
        // Botones de niveles
        JButton nivel1 = new JButton("Nivel 1 - Tutorial");
        JButton nivel2 = new JButton("Nivel 2 - Intermedio");
        JButton nivel3 = new JButton("Nivel 3 - Avanzado");
        JButton volver = new JButton("Volver al Menú");
        
        // Configurar tamaño de botones
        Dimension btnSize = new Dimension(300, 50);
        nivel1.setMaximumSize(btnSize);
        nivel2.setMaximumSize(btnSize);
        nivel3.setMaximumSize(btnSize);
        volver.setMaximumSize(btnSize);
        
        nivel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        nivel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        nivel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        volver.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(nivel1);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(nivel2);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(nivel3);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(volver);
        
        add(centerPanel, BorderLayout.CENTER);

        // Action Listeners para iniciar el juego
        nivel1.addActionListener(e -> {
            boolean twoPlayer = twoPlayersBtn.isSelected();
            ventana.iniciarJuego(1, twoPlayer, "Vainilla", "Fresa");
        });
        
        nivel2.addActionListener(e -> {
            boolean twoPlayer = twoPlayersBtn.isSelected();
            ventana.iniciarJuego(2, twoPlayer, "Vainilla", "Fresa");
        });
        
        nivel3.addActionListener(e -> {
            boolean twoPlayer = twoPlayersBtn.isSelected();
            ventana.iniciarJuego(3, twoPlayer, "Vainilla", "Fresa");
        });

        volver.addActionListener(e -> ventana.mostrarMenuPrincipal());
    }
}