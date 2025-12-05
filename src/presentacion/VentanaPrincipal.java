package presentacion;

import dominio.Game;
import javax.swing.*;

/**
 * Main window of the game application.
 * Manages screen transitions and main navigation.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class VentanaPrincipal extends JFrame {

    private JPanel panelActual;

    public VentanaPrincipal() {
        setTitle("Bad Dopo-Cream");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        mostrarMenuPrincipal();
        setVisible(true);
    }

    /**
     * Changes the current panel to a new one.
     */
    public void cambiarPanel(JPanel nuevo) {
        if (panelActual != null) {
            remove(panelActual);
        }
        panelActual = nuevo;
        add(panelActual);
        revalidate();
        repaint();
    }

    /**
     * Shows the main menu.
     */
    public void mostrarMenuPrincipal() {
        cambiarPanel(new MenuPrincipal(this));
    }

    /**
     * Shows the configuration screen.
     */
    public void mostrarConfiguracion() {
        cambiarPanel(new PantallaConfiguracion(this));
    }

    /**
     * Shows the level selection screen.
     */
    public void mostrarSeleccionNivel() {
        cambiarPanel(new PantallaSeleccionNivel(this));
    }
    
    /**
     * Shows the save/load screen.
     */
    public void mostrarSaveLoad() {
        cambiarPanel(new PantallaSaveLoad(this));
    }

    /**
     * Starts a new game with the specified parameters.
     */
    public void iniciarJuego(int nivel, String helado, String modalidad) {
        cambiarPanel(new PantallaJuego(this, nivel, helado, modalidad));
    }
    
    /**
     * Loads a saved game.
     */
    public void cargarJuegoGuardado(Game game) {
        PantallaJuegoCargado pantallaJuego = new PantallaJuegoCargado(this, game);
        cambiarPanel(pantallaJuego);
    }
}

