package presentacion;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private JPanel panelActual;

    public VentanaPrincipal() {
        setTitle("Bad Dopo-Cream");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        mostrarMenuPrincipal();
        setVisible(true);
    }

    public void cambiarPanel(JPanel nuevo) {
        if (panelActual != null) {
            remove(panelActual);
        }
        panelActual = nuevo;
        add(panelActual);
        revalidate();
        repaint();
    }

    public void mostrarMenuPrincipal() {
        cambiarPanel(new MenuPrincipal(this));
    }

    public void mostrarConfiguracion() {
        cambiarPanel(new PantallaConfiguracion(this));
    }

    public void mostrarSeleccionNivel() {
        cambiarPanel(new PantallaSeleccionNivel(this));
    }

    public void iniciarJuego(int nivel, String helado, String modalidad) {
        cambiarPanel(new PantallaJuego(this, nivel, helado, modalidad));
    }
}
