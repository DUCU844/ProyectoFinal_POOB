package presentacion;

import dominio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Visual representation of the game board.
 * Renders all game elements (player, enemies, fruits, ice blocks).
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class BoardPanel extends JPanel {
    
    private Game game;
    private static final int CELL_SIZE = 40; // Tamaño de cada celda en píxeles
    
    private BufferedImage backgroundImg;
    private BufferedImage vanillaImg, strawberryImg, chocolateImg;
    private BufferedImage grapeImg, bananaImg, cherryImg, pineappleImg;
    private BufferedImage trollImg, potImg, squidImg;
    private BufferedImage iceBlockImg;
    
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
        setBackground(new Color(44, 62, 80));
        
        // Hacer el panel focusable para recibir eventos de teclado
        setFocusable(true);
        
        // Cargar y escalar imágenes
        loadImages();
    }
    
    /**
     * Loads and scales all game images.
     */
    private void loadImages() {
    	BufferedImage rawBackground = ImageLoader.loadBackground();
        if (rawBackground != null) {
            int width = game.getMap().getWidth() * CELL_SIZE;
            int height = game.getMap().getHeight() * CELL_SIZE;
            backgroundImg = ImageLoader.scaleImage(rawBackground, width, height);
        }
        
        // Helados
        vanillaImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("vanilla_ice.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        strawberryImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("strawberry_ice.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        chocolateImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("chocolate_ice.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        
        // Frutas
        grapeImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("grape.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        bananaImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("banana.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        cherryImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("cherry.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        pineappleImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("pineapple.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        
        // Enemigos
        trollImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("troll.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        potImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("pot.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        squidImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("orange_squid.png"), CELL_SIZE - 10, CELL_SIZE - 10);
        
        // Obstaculos
        iceBlockImg = ImageLoader.scaleImage(
            ImageLoader.loadImage("ice_block.png"), CELL_SIZE, CELL_SIZE);
    }
    
    /**
     * Draws all game elements on the screen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
     // Mejorar calidad de renderizado
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                            RenderingHints.VALUE_RENDER_QUALITY);
        drawBackground(g);
        
        drawMap(g);
        drawFruits(g);
        drawEnemies(g);
        drawPlayers(g);
        drawGrid(g); // Opcional: dibujar líneas de grid
    }
    
    /**
     * Draws the background image.
     */
    private void drawBackground(Graphics g) {
        if (backgroundImg != null) {
            // Dibujar la imagen de fondo escalada
            g.drawImage(backgroundImg, 0, 0, null);
        } else {
            // Si no hay imagen, usar un gradiente de respaldo
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(44, 62, 80),
                0, height, new Color(52, 73, 94)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
        }
    }
    
    /**
     * Draws the ice blocks on the map.
     */
    private void drawMap(Graphics g) {
        IceMap map = game.getMap();
        
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.hasIce(x, y)) {
                	int px = x * CELL_SIZE;
                    int py = y * CELL_SIZE;
                    
                    if (iceBlockImg != null) {
                        // Dibujar imagen de hielo
                        g.drawImage(iceBlockImg, px, py, null);
                    } else {
                        // Fallback: rectángulo celeste
                        g.setColor(new Color(173, 216, 230));
                        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                        
                        // Efecto de brillo
                        g.setColor(new Color(255, 255, 255, 100));
                        g.fillRect(px, py, CELL_SIZE / 2, CELL_SIZE / 2);
                        
                        // Borde
                        g.setColor(Color.WHITE);
                        g.drawRect(px, py, CELL_SIZE - 1, CELL_SIZE - 1);
                    }
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
                int x = fruit.getColumn() * CELL_SIZE + 5;
                int y = fruit.getRow() * CELL_SIZE + 5;
                
                BufferedImage fruitImg = null;
                Color fallbackColor = Color.GREEN;
                
                switch (fruit.getType()) {
                    case "GRAPE":
                        fruitImg = grapeImg;
                        fallbackColor = new Color(128, 0, 128);
                        break;
                    case "BANANA":
                        fruitImg = bananaImg;
                        fallbackColor = Color.YELLOW;
                        break;
                    case "Cherry":
                        fruitImg = cherryImg;
                        fallbackColor = Color.RED;
                        break;
                    case "Pineapple":
                        fruitImg = pineappleImg;
                        fallbackColor = new Color(255, 200, 0);
                        break;
                }
                
                if (fruitImg != null) {
                    g.drawImage(fruitImg, x, y, null);
                } else {
                    // Fallback: círculo de color
                    g.setColor(fallbackColor);
                    g.fillOval(x, y, CELL_SIZE - 10, CELL_SIZE - 10);
                    
                    // Brillo
                    g.setColor(new Color(255, 255, 255, 150));
                    g.fillOval(x + 5, y + 5, 10, 10);    
                }
            }
        }
    }
    
    /**
     * Draws all enemies on the board.
     */
    private void drawEnemies(Graphics g) {
        for (Enemy enemy : game.getEnemies()) {
            int x = enemy.getColumn() * CELL_SIZE + 5;
            int y = enemy.getRow() * CELL_SIZE + 5;
            
            BufferedImage enemyImg = null;
            Color fallbackColor = Color.RED;
            
            switch (enemy.getType()) {
                case "TROLL":
                    enemyImg = trollImg;
                    fallbackColor = Color.GREEN;
                    break;
                case "Pot":
                    enemyImg = potImg;
                    fallbackColor = new Color(139, 69, 19);
                    break;
                case "OrangeSquid":
                    enemyImg = squidImg;
                    fallbackColor = Color.ORANGE;
                    break;
            }
            
            if (enemyImg != null) {
                g.drawImage(enemyImg, x, y, null);
            } else {
                // Fallback: cuadrado redondeado
                g.setColor(fallbackColor);
                g.fillRoundRect(x, y, CELL_SIZE - 10, CELL_SIZE - 10, 10, 10);
                
                // Ojos simples
                g.setColor(Color.WHITE);
                g.fillOval(x + 8, y + 8, 6, 6);
                g.fillOval(x + 16, y + 8, 6, 6);
                g.setColor(Color.BLACK);
                g.fillOval(x + 10, y + 10, 3, 3);
                g.fillOval(x + 18, y + 10, 3, 3);
            }
        }
    }
    
    /**
     * Draws the player on the board.
     */
    private void drawPlayers(Graphics g) {
        for (Player player : game.getPlayers()){
	        int x = player.getColumn() * CELL_SIZE + 5;
	        int y = player.getRow() * CELL_SIZE + 5;
	        
	        BufferedImage playerImg = null;
	        Color fallbackColor = Color.WHITE;
	        
	        switch (player.getFlavor()) {
	            case "Vainilla":
	                playerImg = vanillaImg;
	                fallbackColor = Color.WHITE;
	                break;
	            case "Fresa":
	                playerImg = strawberryImg;
	                fallbackColor = new Color(255, 182, 193);
	                break;
	            case "Chocolate":
	                playerImg = chocolateImg;
	                fallbackColor = new Color(139, 69, 19);
	                break;
	        }
	        
	        if (playerImg != null) {
	            g.drawImage(playerImg, x, y, null);
	        } else {
	            // Fallback: círculo con borde
	            g.setColor(fallbackColor);
	            g.fillOval(x, y, CELL_SIZE - 10, CELL_SIZE - 10);
	            
	            // Borde negro
	            g.setColor(Color.BLACK);
	            ((Graphics2D)g).setStroke(new BasicStroke(2));
	            g.drawOval(x, y, CELL_SIZE - 10, CELL_SIZE - 10);
	            
	            // Número del jugador en modo 2 jugadores
	            if (game.isTwoPlayerMode()) {
	                g.setColor(Color.BLACK);
	                g.setFont(new Font("Arial", Font.BOLD, 16));
	                g.drawString(String.valueOf(player.getPlayerNumber()), 
	                            x + 10, y + 20);
	            }
	        }
	    }
	}
    
    /**
     * Draws grid lines for better visualization (optional).
     */
    private void drawGrid(Graphics g) {
        g.setColor(new Color(255, 255, 255, 30)); // Blanco muy transparente
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        
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
