package presentacion;

import dominio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Visual representation of the game board.
 * Renders all game elements (player, enemies, fruits, ice blocks).
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class BoardPanel extends JPanel {
    
    private Game game;
    private static final int CELL_SIZE = 40; // Tamaño de cada celda en píxeles
    
    /**
     * Creates a new board panel for the given game.
     * 
     * @param game reference to the game model
     */
    public BoardPanel(Game game) {
        this.game = game;
        
        // Calcular tamaño del panel basado en el mapa
        int width = game.getMap().getWidth() * CELL_SIZE;
        int height = game.getMap().getHeight() * CELL_SIZE;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        
        // Hacer el panel focusable para recibir eventos de teclado
        setFocusable(true);
    }
    
    /**
     * Draws all game elements on the screen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawMap(g);
        drawFruits(g);
        drawEnemies(g);
        drawPlayer(g);
        drawGrid(g); // Opcional: dibujar líneas de grid
    }
    
    /**
     * Draws the ice blocks on the map.
     */
    private void drawMap(Graphics g) {
        IceMap map = game.getMap();
        
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.hasIce(x, y)) {
                    g.setColor(new Color(173, 216, 230)); // Celeste para hielo
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    
                    // Borde del bloque de hielo
                    g.setColor(Color.WHITE);
                    g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
    
    /**
     * Draws all fruits on the board.
     */
    private void drawFruits(Graphics g) {
        for (Fruit fruit : game.getFruits()) {
            if (!fruit.isCollected()) {
                int x = fruit.getColumn() * CELL_SIZE;
                int y = fruit.getRow() * CELL_SIZE;
                
                // Color según tipo de fruta
                switch (fruit.getType()) {
                    case "GRAPE":
                        g.setColor(new Color(128, 0, 128)); // Púrpura
                        break;
                    case "BANANA":
                        g.setColor(Color.YELLOW);
                        break;
                    default:
                        g.setColor(Color.GREEN);
                }
                
                // Dibujar fruta como círculo
                g.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
            }
        }
    }
    
    /**
     * Draws all enemies on the board.
     */
    private void drawEnemies(Graphics g) {
        for (Enemy enemy : game.getEnemies()) {
            int x = enemy.getColumn() * CELL_SIZE;
            int y = enemy.getRow() * CELL_SIZE;
            
            // Color según tipo de enemigo
            switch (enemy.getType()) {
                case "TROLL":
                    g.setColor(Color.green); // Troll es verde
                    break;
                default:
                    g.setColor(Color.RED);
            }
            
            // Dibujar enemigo como cuadrado redondeado
            g.fillRoundRect(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10, 10, 10);
        }
    }
    
    /**
     * Draws the player on the board.
     */
    private void drawPlayer(Graphics g) {
        Player player = game.getPlayer();
        int x = player.getColumn() * CELL_SIZE;
        int y = player.getRow() * CELL_SIZE;
        
        // Color del helado (por ahora blanco)
        g.setColor(Color.WHITE);
        
        // Dibujar helado como círculo
        g.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
        
        // Borde negro
        g.setColor(Color.BLACK);
        g.drawOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
    }
    
    /**
     * Draws grid lines for better visualization (optional).
     */
    private void drawGrid(Graphics g) {
        g.setColor(new Color(50, 50, 50)); // Gris oscuro
        
        // Líneas verticales
        for (int x = 0; x <= game.getMap().getWidth(); x++) {
            g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, 
                      game.getMap().getHeight() * CELL_SIZE);
        }
        
        // Líneas horizontales
        for (int y = 0; y <= game.getMap().getHeight(); y++) {
            g.drawLine(0, y * CELL_SIZE, 
                      game.getMap().getWidth() * CELL_SIZE, y * CELL_SIZE);
        }
    }
    
    /**
     * Forces a redraw of the board.
     * Call this after game state changes.
     */
    public void refresh() {
        repaint();
    }
}
