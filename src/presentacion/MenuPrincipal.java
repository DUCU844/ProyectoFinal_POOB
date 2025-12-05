package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * Main menu screen with game options.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class MenuPrincipal extends JPanel {

    public MenuPrincipal(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 40));

        JLabel titulo = new JLabel("BAD DOPO-CREAM", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.CYAN);
        add(titulo, BorderLayout.NORTH);

        JButton jugar = new JButton("Jugar");
        JButton cargar = new JButton("Cargar Partida");
        JButton configurar = new JButton("Configuración");
        JButton salir = new JButton("Salir");

        jugar.setPreferredSize(new Dimension(200, 40));
        cargar.setPreferredSize(new Dimension(200, 40));
        configurar.setPreferredSize(new Dimension(200, 40));
        salir.setPreferredSize(new Dimension(200, 40));

        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(4, 1, 10, 10));
        botones.setBackground(new Color(20, 20, 40));
        botones.add(jugar);
        botones.add(cargar);
        botones.add(configurar);
        botones.add(salir);

        add(botones, BorderLayout.CENTER);

        jugar.addActionListener(e -> ventana.mostrarSeleccionNivel());
        cargar.addActionListener(e -> ventana.mostrarSaveLoad());
        configurar.addActionListener(e -> ventana.mostrarConfiguracion());
        salir.addActionListener(e -> System.exit(0));
    }
}
