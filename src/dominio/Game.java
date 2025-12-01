package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main game logic controller for Bad DOPO Cream. Manages the player, enemies,
 * fruits, map, game state, and all game mechanics. This class serves as the
 * core "brain" of the game.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Game {

	private Player player;
	private List<Enemy> enemies;
	private List<Fruit> fruits;
	private IceMap map;
	private GameState gameState;
	private Random random;

	private boolean gameOver;
	private boolean gameWon;
	private int currentLevel;

	// Constantes para el tamaño del mapa
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 15;

	/**
	 * Creates a new Game instance with default settings. Initializes the map,
	 * player, enemies, and fruits for level 1.
	 */
	public Game() {
		this(1); // Por defecto inicia en nivel 1
	}

	/**
	 * Creates a new Game instance for a specific level.
	 * 
	 * @param level the level number to initialize
	 */
	public Game(int level) {
		this.currentLevel = level;
		this.map = new IceMap(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.enemies = new ArrayList<>();
		this.fruits = new ArrayList<>();
		this.random = new Random();
		this.gameOver = false;
		this.gameWon = false;

		initializeLevel(level);
	}

	/**
	 * Initializes all elements for a specific level. Creates player, enemies, and
	 * fruits based on level configuration.
	 * 
	 * @param level level number
	 */
	private void initializeLevel(int level) {
		initPlayer();
		initEnemiesForLevel(level);
		initFruitsForLevel(level);

		// Crear el GameState con el número total de frutas
		this.gameState = new GameState(level, fruits.size());
	}

	/**
	 * Initializes the player at the center of the map.
	 */
	private void initPlayer() {
		int startX = map.getWidth() / 2;
		int startY = map.getHeight() / 2;
		this.player = new Player(startY, startX);
	}

	/**
	 * Initializes enemies based on the current level. Higher levels have more
	 * enemies and different types.
	 * 
	 * @param level current level number
	 */
	private void initEnemiesForLevel(int level) {
		enemies.clear();

		switch (level) {
		case 1:
			// Nivel 1: 2 Trolls básicos
			enemies.add(new Troll(2, 2));
			enemies.add(new Troll(map.getHeight() - 3, map.getWidth() - 3));
			break;

		case 2:
			// Nivel 2: 3 enemigos (2 Trolls + 1 más complejo cuando lo implementen)
			enemies.add(new Troll(2, 2));
			enemies.add(new Troll(map.getHeight() - 3, map.getWidth() - 3));
			enemies.add(new Troll(map.getHeight() / 2, 2));
			break;

		case 3:
			// Nivel 3: 4 enemigos
			enemies.add(new Troll(2, 2));
			enemies.add(new Troll(map.getHeight() - 3, map.getWidth() - 3));
			enemies.add(new Troll(2, map.getWidth() - 3));
			enemies.add(new Troll(map.getHeight() - 3, 2));
			break;

		default:
			// Niveles adicionales: aumentar progresivamente
			int enemyCount = 2 + level;
			for (int i = 0; i < enemyCount; i++) {
				int row = random.nextInt(map.getHeight() - 4) + 2;
				int col = random.nextInt(map.getWidth() - 4) + 2;
				enemies.add(new Troll(row, col));
			}
		}
	}

	/**
	 * Initializes fruits based on the current level. Higher levels have more fruits
	 * and different types.
	 * 
	 * @param level current level number
	 */
	private void initFruitsForLevel(int level) {
		fruits.clear();

		switch (level) {
		case 1:
			// Nivel 1: 2 Uvas y 1 Plátano
			fruits.add(new Grape(3, 3));
			fruits.add(new Grape(map.getHeight() - 4, map.getWidth() - 4));
			fruits.add(new Banana(map.getHeight() / 2, map.getWidth() - 4));
			break;

		case 2:
			// Nivel 2: 3 Uvas y 2 Plátanos
			fruits.add(new Grape(2, map.getWidth() / 2));
			fruits.add(new Grape(map.getHeight() - 3, map.getWidth() / 2));
			fruits.add(new Grape(map.getHeight() / 2, 2));
			fruits.add(new Banana(3, 3));
			fruits.add(new Banana(map.getHeight() - 4, map.getWidth() - 4));
			break;

		case 3:
			// Nivel 3: Mix de frutas
			fruits.add(new Grape(2, 2));
			fruits.add(new Grape(2, map.getWidth() - 3));
			fruits.add(new Grape(map.getHeight() - 3, 2));
			fruits.add(new Banana(map.getHeight() / 2, map.getWidth() / 2));
			fruits.add(new Banana(3, map.getWidth() / 2));
			fruits.add(new Banana(map.getHeight() - 4, map.getWidth() / 2));
			break;

		default:
			// Niveles adicionales: generar aleatoriamente
			int fruitCount = 3 + (level * 2);
			for (int i = 0; i < fruitCount; i++) {
				int row = random.nextInt(map.getHeight() - 4) + 2;
				int col = random.nextInt(map.getWidth() - 4) + 2;

				// Alternar entre Uvas y Plátanos
				if (i % 2 == 0) {
					fruits.add(new Grape(row, col));
				} else {
					fruits.add(new Banana(row, col));
				}
			}
		}
	}

	/**
	 * Moves the player in the specified direction. Handles collision detection,
	 * fruit collection, and enemy encounters.
	 * 
	 * @param dx movement in x direction (-1 left, 1 right, 0 none)
	 * @param dy movement in y direction (-1 up, 1 down, 0 none)
	 */
	public void movePlayer(int dx, int dy) {
		if (gameOver || gameWon)
			return;

		int newX = player.getColumn() + dx;
		int newY = player.getRow() + dy;

		// Verificar si la nueva posición es válida
		if (!map.isWalkable(newX, newY)) {
			return; // Bloqueado por hielo o fuera del mapa
		}

		// Mover al jugador
		player.moveTo(newY, newX);

		// Verificar colisión con frutas
		checkFruitCollision();

		// Verificar colisión con enemigos
		checkEnemyCollision();
	}

	/**
	 * Allows the player to create or destroy ice blocks in a direction. Creates a
	 * line of ice blocks or destroys them in domino effect.
	 * 
	 * @param dx direction x (-1 left, 1 right, 0 none)
	 * @param dy direction y (-1 up, 1 down, 0 none)
	 */
	public void playerShootIce(int dx, int dy) {
		if (gameOver || gameWon)
			return;

		int startX = player.getColumn() + dx;
		int startY = player.getRow() + dy;

		// Verificar si hay hielo en la posición inicial
		if (map.hasIce(startX, startY)) {
			// Destruir hielo en efecto dominó
			destroyIceInLine(startX, startY, dx, dy);
		} else {
			// Crear línea de hielo
			createIceInLine(startX, startY, dx, dy);
		}
	}

	/**
	 * Creates a line of ice blocks in the specified direction. Stops when hitting
	 * an existing ice block or map edge.
	 * 
	 * @param startX starting x position
	 * @param startY starting y position
	 * @param dx     direction x
	 * @param dy     direction y
	 */
	private void createIceInLine(int startX, int startY, int dx, int dy) {
		int x = startX;
		int y = startY;

		// Crear hasta 5 bloques o hasta encontrar obstáculo
		for (int i = 0; i < 5; i++) {
			// Verificar límites del mapa
			if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
				break;
			}

			// Si ya hay hielo, no crear más
			if (map.hasIce(x, y)) {
				break;
			}

			// Crear bloque de hielo
			map.toggleIce(x, y);

			// Avanzar en la dirección
			x += dx;
			y += dy;
		}
	}

	/**
	 * Destroys ice blocks in domino effect in the specified direction.
	 * 
	 * @param startX starting x position
	 * @param startY starting y position
	 * @param dx     direction x
	 * @param dy     direction y
	 */
	private void destroyIceInLine(int startX, int startY, int dx, int dy) {
		int x = startX;
		int y = startY;

		// Destruir bloques consecutivos
		while (x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight() && map.hasIce(x, y)) {

			map.toggleIce(x, y); // Eliminar el hielo

			x += dx;
			y += dy;
		}
	}

	/**
	 * Updates all enemies' positions and behaviors. Called once per game tick
	 * (approximately every 100ms).
	 */
	public void updateEnemies() {
		if (gameOver || gameWon)
			return;

		for (Enemy enemy : enemies) {
			enemy.update(this);
		}

		// Verificar colisión después de mover enemigos
		checkEnemyCollision();
	}

	/**
	 * Updates all fruits' special behaviors. Some fruits like Cherry can teleport.
	 */
	public void updateFruits() {
		if (gameOver || gameWon)
			return;

		for (Fruit fruit : fruits) {
			if (!fruit.isCollected()) {
				fruit.update();
			}
		}
	}

	/**
	 * Checks if the player is touching any fruit and collects it. Updates score and
	 * fruit collection counter.
	 */
	private void checkFruitCollision() {
		for (Fruit fruit : fruits) {
			if (!fruit.isCollected() && fruit.getRow() == player.getRow() && fruit.getColumn() == player.getColumn()) {

				// Recolectar fruta
				fruit.collect();

				// Actualizar estadísticas
				gameState.addScore(fruit.getPoints());
				gameState.collectFruit();

				// Verificar si se completó el nivel
				if (gameState.isLevelComplete()) {
					gameWon = true;
				}
			}
		}
	}

	/**
	 * Checks if the player is touching any enemy. Triggers game over if collision
	 * detected.
	 */
	private void checkEnemyCollision() {
		for (Enemy enemy : enemies) {
			if (enemy.getRow() == player.getRow() && enemy.getColumn() == player.getColumn()) {
				gameOver = true;
				return;
			}
		}
	}

	/**
	 * Resets the current level. Called when player dies or time runs out.
	 */
	public void resetLevel() {
		gameOver = false;
		gameWon = false;
		enemies.clear();
		fruits.clear();

		initializeLevel(currentLevel);
	}

	/**
	 * Advances to the next level. Called when player completes current level.
	 */
	public void nextLevel() {
		currentLevel++;
		gameOver = false;
		gameWon = false;
		enemies.clear();
		fruits.clear();

		initializeLevel(currentLevel);
	}

	/**
	 * @return true if the game is over (player lost)
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * @return true if the player won (collected all fruits)
	 */
	public boolean isGameWon() {
		return gameWon;
	}

	/**
	 * @return reference to the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return list of all enemies (both alive)
	 */
	public List<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * @return list of all fruits (including collected ones)
	 */
	public List<Fruit> getFruits() {
		return fruits;
	}

	/**
	 * @return reference to the game map
	 */
	public IceMap getMap() {
		return map;
	}

	/**
	 * @return reference to the game state (score, time, etc.)
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @return current level number
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * @return total number of fruits in current level
	 */
	public int getTotalFruits() {
		return fruits.size();
	}

	/**
	 * @return number of fruits collected so far
	 */
	public int getCollectedFruits() {
		int count = 0;
		for (Fruit fruit : fruits) {
			if (fruit.isCollected()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Sets the game over state manually. Used for special game over conditions.
	 * 
	 * @param gameOver new game over state
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Sets the game won state manually.
	 * 
	 * @param gameWon new game won state
	 */
	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}
}