package presentacion;

import javax.swing.*;
import java.awt.*;

public class PantallaSeleccionNivel extends JPanel {

    public PantallaSeleccionNivel(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("SELECCIONA EL NIVEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        add(titulo, BorderLayout.NORTH);

        JButton nivel1 = new JButton("Nivel 1");
        JButton volver = new JButton("Volver");

        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(2, 1, 10, 10));
        botones.add(nivel1);
        botones.add(volver);
        add(botones, BorderLayout.CENTER);

        nivel1.addActionListener(e -> {
            // Por ahora no iniciamos juego porque PantallaJuego no existe aún
            JOptionPane.showMessageDialog(this, "Aquí se iniciaría el Nivel 1.");
        });

        volver.addActionListener(e -> ventana.mostrarMenuPrincipal());
    }
}
