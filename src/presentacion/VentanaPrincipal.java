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
        
        ImageLoader.preloadImages(); //Precargar imagenes

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
     * @param nivel level number (1-3)
     * @param twoPlayerMode true for 2 players, false for 1 player
     * @param flavor1 player 1 ice cream flavor
     * @param flavor2 player 2 ice cream flavor
     */
    public void iniciarJuego(int nivel, boolean twoPlayerMode, String flavor1, String flavor2) {
        cambiarPanel(new PantallaJuego(this, nivel, twoPlayerMode, flavor1, flavor2));
    }
    
    /**
     * Backwards compatibility method for single player.
     */
    public void iniciarJuego(int nivel, String helado, String modalidad) {
        iniciarJuego(nivel, false, helado, "Fresa");
    }
    
    /**
     * Loads a saved game.
     */
    public void cargarJuegoGuardado(Game game) {
        PantallaJuegoCargado pantallaJuego = new PantallaJuegoCargado(this, game);
        cambiarPanel(pantallaJuego);
    }
}

