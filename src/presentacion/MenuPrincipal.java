package presentacion;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JPanel {

    public MenuPrincipal(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("BAD DOPO-CREAM", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        add(titulo, BorderLayout.NORTH);

        JButton jugar = new JButton("Jugar");
        JButton configurar = new JButton("Configuración");
        JButton salir = new JButton("Salir");

        jugar.setPreferredSize(new Dimension(200, 40));
        configurar.setPreferredSize(new Dimension(200, 40));
        salir.setPreferredSize(new Dimension(200, 40));

        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(3, 1, 10, 10));
        botones.add(jugar);
        botones.add(configurar);
        botones.add(salir);

        add(botones, BorderLayout.CENTER);

        jugar.addActionListener(e -> ventana.mostrarSeleccionNivel());
        configurar.addActionListener(e -> ventana.mostrarConfiguracion());
        salir.addActionListener(e -> System.exit(0));
    }
}
