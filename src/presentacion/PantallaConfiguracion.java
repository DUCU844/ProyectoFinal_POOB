package presentacion;

import javax.swing.*;
import java.awt.*;

public class PantallaConfiguracion extends JPanel {

    private String modalidadSeleccionada = "Jugador vs Jugador";
    private String heladoSeleccionado = "Vainilla";

    public PantallaConfiguracion(VentanaPrincipal ventana) {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("CONFIGURACIÓN DEL JUEGO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(6, 1, 10, 10));

        // Modalidades
        panelCentral.add(new JLabel("Modalidad:", SwingConstants.CENTER));

        JRadioButton jvj = new JRadioButton("Jugador vs Jugador", true);
        JRadioButton jvm = new JRadioButton("Jugador vs Máquina");
        JRadioButton mvm = new JRadioButton("Máquina vs Máquina");

        ButtonGroup grupoModalidad = new ButtonGroup();
        grupoModalidad.add(jvj);
        grupoModalidad.add(jvm);
        grupoModalidad.add(mvm);

        panelCentral.add(jvj);
        panelCentral.add(jvm);
        panelCentral.add(mvm);
        
        // Helado
        panelCentral.add(new JLabel("Selecciona tu helado:", SwingConstants.CENTER));

        String[] helados = {"Vainilla", "Fresa", "Chocolate"};
        JComboBox<String> comboHelados = new JComboBox<>(helados);

        comboHelados.addActionListener(e -> {
            heladoSeleccionado = (String) comboHelados.getSelectedItem();
        });

        panelCentral.add(comboHelados);

        add(panelCentral, BorderLayout.CENTER);

        // Botón volver
        JButton volver = new JButton("Volver al menú");
        volver.addActionListener(e -> ventana.mostrarMenuPrincipal());
        add(volver, BorderLayout.SOUTH);

        // Listeners de modalidad
        jvj.addActionListener(e -> modalidadSeleccionada = "JvJ");
        jvm.addActionListener(e -> modalidadSeleccionada = "JvM");
        mvm.addActionListener(e -> modalidadSeleccionada = "MvM");
    }

    public String getModalidadSeleccionada() {
        return modalidadSeleccionada;
    }

    public String getHeladoSeleccionado() {
        return heladoSeleccionado;
    }
}

