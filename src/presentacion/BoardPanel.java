package presentacion;

import dominio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Visual representation of the game board. Renders all game elements (player,
 * enemies, fruits, ice blocks).
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class BoardPanel extends JPanel {

	private Game game;
	private static final int CELL_SIZE = 40; // Tamaño de cada celda en píxeles

	private BufferedImage backgroundImg;
	private BufferedImage vanillaImg, strawberryImg, chocolateImg;
	private BufferedImage grapeImg, bananaImg, cherryImg, pineappleImg, cactusImg, cactusONImg;
	private BufferedImage trollImg, potImg, squidImg;
	private BufferedImage iceBlockImg;

	private BufferedImage firepitLitImg, firepitOutImg;
	private BufferedImage hotTileImg;
	private BufferedImage iglooImg;

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
		vanillaImg = ImageLoader.scaleImage(ImageLoader.loadImage("vanilla_ice.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		strawberryImg = ImageLoader.scaleImage(ImageLoader.loadImage("strawberry_ice.png"), CELL_SIZE - 10,
				CELL_SIZE - 10);
		chocolateImg = ImageLoader.scaleImage(ImageLoader.loadImage("chocolate_ice.png"), CELL_SIZE - 10,
				CELL_SIZE - 10);

		// Frutas
		grapeImg = ImageLoader.scaleImage(ImageLoader.loadImage("grape.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		bananaImg = ImageLoader.scaleImage(ImageLoader.loadImage("banana.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		cherryImg = ImageLoader.scaleImage(ImageLoader.loadImage("cherry.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		pineappleImg = ImageLoader.scaleImage(ImageLoader.loadImage("pineapple.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		cactusImg = ImageLoader.scaleImage(ImageLoader.loadImage("cactus.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		cactusONImg = ImageLoader.scaleImage(ImageLoader.loadImage("cactusON.png"), CELL_SIZE - 10, CELL_SIZE - 10);

		// Enemigos
		trollImg = ImageLoader.scaleImage(ImageLoader.loadImage("troll.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		potImg = ImageLoader.scaleImage(ImageLoader.loadImage("pot.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		squidImg = ImageLoader.scaleImage(ImageLoader.loadImage("orange_squid.png"), CELL_SIZE - 10, CELL_SIZE - 10);

		// Obstaculos
		iceBlockImg = ImageLoader.scaleImage(ImageLoader.loadImage("ice_block.png"), CELL_SIZE, CELL_SIZE);
		firepitLitImg = ImageLoader.scaleImage(ImageLoader.loadImage("firepit.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		firepitOutImg = ImageLoader.scaleImage(ImageLoader.loadImage("firepitOut.png"), CELL_SIZE - 10, CELL_SIZE - 10);
		hotTileImg = ImageLoader.scaleImage(ImageLoader.loadImage("hotTile.png"), CELL_SIZE, CELL_SIZE);
		iglooImg = ImageLoader.scaleImage(ImageLoader.loadImage("iglu.png"), CELL_SIZE * 2, CELL_SIZE * 2);
	}

	/**
	 * Draws all game elements on the screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Mejorar calidad de renderizado
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		drawBackground(g);

		drawHotTiles(g);
		drawFirepits(g);
		drawMap(g);
		drawIgloos(g);
		drawFruits(g);
		drawEnemies(g);
		drawPlayers(g);
		drawGrid(g); // dibujar líneas de grid
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

			GradientPaint gradient = new GradientPaint(0, 0, new Color(44, 62, 80), 0, height, new Color(52, 73, 94));
			g2d.setPaint(gradient);
			g2d.fillRect(0, 0, width, height);
		}
	}

	/**
	 * Draws hot tiles with orange/red glow effect.
	 */
	private void drawHotTiles(Graphics g) {
		for (HotTile tile : game.getMap().getHotTiles()) {
			int px = tile.getColumn() * CELL_SIZE;
			int py = tile.getRow() * CELL_SIZE;

			if (hotTileImg != null) {
				g.drawImage(hotTileImg, px, py, null);
			} else {
				// Fallback: baldosa roja brillante
				g.setColor(new Color(255, 100, 50, 150));
				g.fillRect(px, py, CELL_SIZE, CELL_SIZE);

				// Efecto de calor (gradiente)
				Graphics2D g2d = (Graphics2D) g;
				GradientPaint heat = new GradientPaint(px, py, new Color(255, 200, 0, 100), px + CELL_SIZE,
						py + CELL_SIZE, new Color(255, 50, 0, 50));
				g2d.setPaint(heat);
				g2d.fillRect(px + 5, py + 5, CELL_SIZE - 10, CELL_SIZE - 10);

				// Borde
				g.setColor(new Color(255, 150, 0));
				g.drawRect(px, py, CELL_SIZE - 1, CELL_SIZE - 1);
			}
		}
	}

	/**
	 * Draws firepits (lit or extinguished).
	 */
	private void drawFirepits(Graphics g) {
		for (Firepit firepit : game.getMap().getFirepits()) {
			int px = firepit.getColumn() * CELL_SIZE + 5;
			int py = firepit.getRow() * CELL_SIZE + 5;

			if (firepit.isLit()) {
				if (firepitLitImg != null) {
					g.drawImage(firepitLitImg, px, py, null);
				} else {
					// Fallback: fogata encendida (llamas)
					g.setColor(new Color(255, 100, 0));
					g.fillOval(px + 5, py + 10, 20, 15);

					g.setColor(new Color(255, 200, 0));
					g.fillOval(px + 8, py + 5, 14, 20);

					g.setColor(new Color(255, 255, 100, 200));
					g.fillOval(px + 10, py + 8, 10, 12);
				}
			} else {
				if (firepitOutImg != null) {
					g.drawImage(firepitOutImg, px, py, null);
				} else {
					// Fallback: fogata apagada
					g.setColor(new Color(80, 80, 80));
					g.fillOval(px + 5, py + 15, 20, 10);

					g.setColor(new Color(60, 60, 60));
					g.fillRect(px + 12, py + 10, 6, 15);

					// Humo si está recién apagada
					if (firepit.getExtinguishTimer() < 30) {
						g.setColor(new Color(150, 150, 150, 100));
						g.fillOval(px + 8, py, 14, 10);
					}
				}
			}
		}
	}

	/**
	 * Draws igloos (2x2 structures).
	 */
	private void drawIgloos(Graphics g) {
		for (Igloo igloo : game.getMap().getIgloos()) {
			int px = igloo.getColumn() * CELL_SIZE;
			int py = igloo.getRow() * CELL_SIZE;
			int width = igloo.getWidth() * CELL_SIZE;
			int height = igloo.getHeight() * CELL_SIZE;

			if (iglooImg != null) {
				// Si tenemos la imagen, escalarla al tamaño correcto
				BufferedImage scaledIgloo = ImageLoader.scaleImage(iglooImg, width, height);
				g.drawImage(scaledIgloo, px, py, null);
			} else {
				// Fallback: iglú dibujado (cúpula blanca)
				g.setColor(Color.WHITE);
				g.fillArc(px, py, width, height, 0, 180);

				// Detalles de bloques de hielo
				g.setColor(new Color(220, 230, 240));
				for (int i = 0; i < 3; i++) {
					g.drawArc(px + 5, py + 10 + (i * 10), width - 10, 15, 0, 180);
				}

				// Entrada oscura
				g.setColor(new Color(50, 50, 80));
				g.fillArc(px + width / 3, py + height - 20, width / 3, 20, 180, 180);

				// Borde
				g.setColor(new Color(200, 220, 240));
				((Graphics2D) g).setStroke(new BasicStroke(2));
				g.drawArc(px, py, width, height, 0, 180);
			}
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
				case "Cactus":
					fruitImg = cactusImg;
					fallbackColor = new Color(100, 200, 100);

					// Si es cactus, mostrar indicador de puas
					Cactus cactus = (Cactus) fruit;
					if (cactus.hasSpikes()) {
						// Dibujar aura roja de peligro
						g.setColor(new Color(255, 0, 0, 80));
						g.fillOval(x - 8, y - 8, CELL_SIZE + 6, CELL_SIZE + 6);

						// Dibujar puas alrededor
						g.setColor(new Color(180, 0, 0));
						for (int i = 0; i < 8; i++) {
							double angle = (Math.PI * 2 * i) / 8;
							int spikeX = (int) (x + 15 + Math.cos(angle) * 20);
							int spikeY = (int) (y + 15 + Math.sin(angle) * 20);
							g.drawLine(x + 15, y + 15, spikeX, spikeY);
						}
					}
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

			}
		}
	}

	/**
	 * Draws the player on the board.
	 */
	private void drawPlayers(Graphics g) {
		for (Player player : game.getPlayers()) {
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
				((Graphics2D) g).setStroke(new BasicStroke(2));
				g.drawOval(x, y, CELL_SIZE - 10, CELL_SIZE - 10);

				// Número del jugador en modo 2 jugadores
				if (game.isTwoPlayerMode()) {
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial", Font.BOLD, 16));
					g.drawString(String.valueOf(player.getPlayerNumber()), x + 10, y + 20);
				}
			}
		}
	}

	/**
	 * Draws grid lines for better visualization (optional).
	 */
	private void drawGrid(Graphics g) {
		g.setColor(new Color(255, 255, 255, 30)); // Blanco muy transparente
		((Graphics2D) g).setStroke(new BasicStroke(1));

		// Líneas verticales
		for (int x = 0; x <= game.getMap().getWidth(); x++) {
			g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, game.getMap().getHeight() * CELL_SIZE);
		}

		// Líneas horizontales
		for (int y = 0; y <= game.getMap().getHeight(); y++) {
			g.drawLine(0, y * CELL_SIZE, game.getMap().getWidth() * CELL_SIZE, y * CELL_SIZE);
		}
	}

	/**
	 * Forces a redraw of the board. Call this after game state changes.
	 */
	public void refresh() {
		repaint();
	}
}
