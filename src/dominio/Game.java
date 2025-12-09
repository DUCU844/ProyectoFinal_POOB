package dominio;

import java.util.*;

/**
 * Main game logic controller for Bad DOPO Cream. Manages the player, enemies,
 * fruits, map, game state, and all game mechanics. This class serves as the
 * core "brain" of the game.
 * 
 * Now supports single player and two player modes.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Game {

	private List<Player> players; // Varios jugadores
	private List<Enemy> enemies;
	private List<Fruit> fruits;
	private IceMap map;
	private GameState gameState;
	private Random random;

	private boolean gameOver;
	private boolean gameWon;
	private int currentLevel;
	private boolean twoPlayerMode; // Activa modo 2 jugadores

	// Constantes para el tamaño del mapa
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 15;

	/**
	 * Creates a new Game instance with default settings. Initializes the map,
	 * player, enemies, and fruits for level 1.
	 */
	public Game() {
		this(1, false, "Vainilla", "Fresa"); // Por defecto inicia en nivel 1
	}

	/**
	 * Creates a new Game instance for a specific level (1 player).
	 * 
	 * @param level the level number to initialize
	 */
	public Game(int level) {
		this(level, false, "Vainilla", "Fresa");
	}

	/**
	 * Creates a new Game with full configuration.
	 * 
	 * @param level         level number
	 * @param twoPlayerMode true for 2 players, false for 1 player
	 * @param flavor1       flavor for player 1
	 * @param flavor2       flavor for player 2 (ignored if twoPlayerMode is false)
	 */
	public Game(int level, boolean twoPlayerMode, String flavor1, String flavor2) {
		currentLevel = level;
		this.twoPlayerMode = twoPlayerMode;
		map = new IceMap(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		players = new ArrayList<>();
		enemies = new ArrayList<>();
		fruits = new ArrayList<>();
		random = new Random();
		gameOver = false;
		gameWon = false;

		initializeLevel(level, twoPlayerMode, flavor1, flavor2);
	}

	/**
	 * Initializes all elements for a specific level. Creates player, enemies, and
	 * fruits based on level configuration.
	 * 
	 * @param level         level number
	 * @param twoPlayerMode whether it's 2 player mode
	 * @param flavor1       player 1 flavor
	 * @param flavor2       player 2 flavor
	 */
	private void initializeLevel(int level, boolean twoPlayerMode, String flavor1, String flavor2) {
		// Limpiar el mapa
		map.clearObstacles();

		// Crear el diseño único de cada nivel
		switch (level) {
		case 1:
			initLevel1();
			break;
		case 2:
			initLevel2();
			break;
		case 3:
			initLevel3();
			break;
		default:
			initLevel1(); // Fallback
		}

		// Inicializar jugadores después de crear el mapa
		initPlayers(twoPlayerMode, flavor1, flavor2);

		// Crear el GameState con el número total de frutas
		this.gameState = new GameState(level, fruits.size());
	}

	/**
	 * LEVEL 1: "Ice Cave" Theme: Tutorial level with igloos and basic ice
	 * structures. Enemies: 2 Trolls Fruits: 8 Grapes + 8 Bananas Obstacles: Igloos,
	 * ice walls
	 */
	private void initLevel1() {
		// Crear estructuras de hielo tipo cuadrícula
		for (int y = 3; y < 12; y += 4) {
			for (int x = 3; x < 17; x += 4) {
				map.toggleIce(x, y);
				map.toggleIce(x + 1, y);
				map.toggleIce(x, y + 1);
				map.toggleIce(x + 1, y + 1);
			}
		}

		// Agregar iglús en las esquinas (decoración nivel 1)
		map.addIgloo(new Igloo(2, 2, 2, 2));
		map.addIgloo(new Igloo(2, 16, 2, 2));

		// Enemigos: 2 Trolls
		enemies.add(new Troll(2, 9));
		enemies.add(new Troll(12, 10));

		// Frutas: 8 Uvas + 8 Plátanos distribuidos
		addGrapesPattern1();
		addBananasPattern1();
	}

	/**
	 * LEVEL 2: "Volcano Arena" Theme: Lava theme with firepits and hot tiles.
	 * Enemies: 1 Pot (chases player) Fruits: 4 Pineapples + 4 Bananas + 4 Cherries
	 * Obstacles: Firepits, hot tiles, ice barriers
	 */
	private void initLevel2() {
		// Crear paredes de hielo tipo laberinto
		for (int y = 4; y < 11; y++) {
			map.toggleIce(7, y);
			map.toggleIce(12, y);
		}

		for (int x = 4; x < 16; x++) {
			if (x != 7 && x != 12) {
				map.toggleIce(x, 7);
			}
		}

		// Agregar fogatas en puntos estratégicos
		map.addFirepit(new Firepit(3, 5));
		map.addFirepit(new Firepit(3, 14));
		map.addFirepit(new Firepit(11, 5));
		map.addFirepit(new Firepit(11, 14));

		// Agregar baldosas calientes en el centro
		for (int x = 8; x <= 11; x++) {
			map.addHotTile(new HotTile(7, x));
		}
		map.addHotTile(new HotTile(8, 9));
		map.addHotTile(new HotTile(8, 10));

		// Enemigo: 1 Maceta
		enemies.add(new Pot(2, 2));

		// Frutas mixtas
		addPineapplesPattern2();
		addBananasPattern2();
		addCherriesPattern2();
	}

	/**
	 * LEVEL 3: "Cactus Desert" Theme: Desert with cactus and advanced enemies.
	 * Enemies: 1 Orange Squid (breaks ice) Fruits: 4 Pineapples + 4 Cactus + 4
	 * Cherries Obstacles: Hot tiles, scattered ice
	 */
	private void initLevel3() {
		// Crear estructuras de hielo dispersas
		// Pirámides de hielo
		map.toggleIce(5, 5);
		map.toggleIce(5, 6);
		map.toggleIce(6, 5);

		map.toggleIce(14, 5);
		map.toggleIce(14, 6);
		map.toggleIce(13, 5);

		map.toggleIce(5, 9);
		map.toggleIce(5, 10);
		map.toggleIce(6, 10);

		map.toggleIce(14, 9);
		map.toggleIce(14, 10);
		map.toggleIce(13, 10);

		// Baldosas calientes formando caminos
		for (int y = 3; y < 12; y++) {
			map.addHotTile(new HotTile(y, 10));
		}

		for (int x = 5; x < 15; x++) {
			if (x != 10) {
				map.addHotTile(new HotTile(7, x));
			}
		}

		// Enemigo: 1 Calamar Naranja
		enemies.add(new OrangeSquid(20, 20));

		// Frutas especiales del nivel 3
		addPineapplesPattern3();
		addCactusPattern3();
		addCherriesPattern3();
	}

	/**
	 * Patterns de frutas para Nivel 1
	 */
	private void addGrapesPattern1() {
		fruits.add(new Grape(5, 5));
		fruits.add(new Grape(5, 14));
		fruits.add(new Grape(9, 5));
		fruits.add(new Grape(9, 14));
		fruits.add(new Grape(7, 7));
		fruits.add(new Grape(7, 12));
		fruits.add(new Grape(11, 7));
		fruits.add(new Grape(11, 12));
	}

	private void addBananasPattern1() {
		fruits.add(new Banana(4, 9));
		fruits.add(new Banana(6, 9));
		fruits.add(new Banana(8, 9));
		fruits.add(new Banana(10, 9));
		fruits.add(new Banana(5, 7));
		fruits.add(new Banana(5, 12));
		fruits.add(new Banana(9, 7));
		fruits.add(new Banana(9, 12));
	}

	/**
	 * Patterns de frutas para Nivel 2
	 */
	private void addPineapplesPattern2() {
		fruits.add(new Pineapple(5, 5));
		fruits.add(new Pineapple(5, 14));
		fruits.add(new Pineapple(9, 5));
		fruits.add(new Pineapple(9, 14));
	}

	private void addBananasPattern2() {
		fruits.add(new Banana(5, 9));
		fruits.add(new Banana(9, 9));
		fruits.add(new Banana(7, 5));
		fruits.add(new Banana(7, 14));
	}

	private void addCherriesPattern2() {
		fruits.add(new Cherry(4, 10));
		fruits.add(new Cherry(6, 10));
		fruits.add(new Cherry(8, 10));
		fruits.add(new Cherry(10, 10));
	}

	/**
	 * Patterns de frutas para Nivel 3
	 */
	private void addPineapplesPattern3() {
		fruits.add(new Pineapple(3, 3));
		fruits.add(new Pineapple(3, 16));
		fruits.add(new Pineapple(11, 3));
		fruits.add(new Pineapple(11, 16));
	}

	private void addCactusPattern3() {
		fruits.add(new Cactus(7, 5));
		fruits.add(new Cactus(7, 15));
		fruits.add(new Cactus(5, 10));
		fruits.add(new Cactus(9, 10));
	}

	private void addCherriesPattern3() {
		fruits.add(new Cherry(4, 7));
		fruits.add(new Cherry(4, 13));
		fruits.add(new Cherry(10, 7));
		fruits.add(new Cherry(10, 13));
	}

	/**
	 * Initializes the players based on game mode.
	 */
	private void initPlayers(boolean twoPlayerMode, String flavor1, String flavor2) {
		players.clear();

		if (twoPlayerMode) {
			// Jugador 1: lado izquierdo
			int startX1 = map.getWidth() / 4;
			int startY1 = map.getHeight() / 2;
			players.add(new Player(startY1, startX1, flavor1, 1));

			// Jugador 2: lado derecho
			int startX2 = (map.getWidth() * 3) / 4;
			int startY2 = map.getHeight() / 2;
			players.add(new Player(startY2, startX2, flavor2, 2));
		} else {
			// Un solo jugador en el centro
			int startX = map.getWidth() / 2;
			int startY = map.getHeight() / 2;
			players.add(new Player(startY, startX, flavor1, 1));
		}
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
			// Nivel 2: 1 Maceta (persigue al jugador)
			enemies.add(new Pot(map.getHeight() / 2, map.getWidth() / 2));
			break;

		case 3:
			// Nivel 3: 1 Calamar Naranja (persigue y rompe hielo)
			enemies.add(new OrangeSquid(map.getHeight() / 2, map.getWidth() / 2));
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
			// Nivel 1: 8 Uvas + 8 Plátanos (16 frutas estáticas)
			addGrapesInPattern(8);
			addBananasInPattern(8);
			break;

		case 2:
			// Nivel 2: 8 Piñas (móviles) + 8 Plátanos (estáticos)
			addPineapplesInPattern(8);
			addBananasInPattern(8);
			break;

		case 3:
			// Nivel 3: 8 Piñas (móviles) + 8 Cerezas (se teletransportan)
			addPineapplesInPattern(8);
			addCherriesInPattern(8);
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
	 * Adds grapes in a distributed pattern across the map.
	 */
	private void addGrapesInPattern(int count) {
		int width = map.getWidth();
		int height = map.getHeight();

		for (int i = 0; i < count; i++) {
			int row = 2 + (i % 3) * (height - 5) / 3;
			int col = 2 + (i / 3) * (width - 5) / 3;
			fruits.add(new Grape(row, col));
		}
	}

	/**
	 * Adds bananas in a distributed pattern across the map.
	 */
	private void addBananasInPattern(int count) {
		int width = map.getWidth();
		int height = map.getHeight();

		for (int i = 0; i < count; i++) {
			int row = 3 + (i % 3) * (height - 6) / 3;
			int col = 3 + (i / 3) * (width - 6) / 3;
			fruits.add(new Banana(row, col));
		}
	}

	/**
	 * Adds pineapples in a distributed pattern across the map.
	 */
	private void addPineapplesInPattern(int count) {
		int width = map.getWidth();
		int height = map.getHeight();

		for (int i = 0; i < count; i++) {
			int row = 2 + (i % 3) * (height - 5) / 3;
			int col = 2 + (i / 3) * (width - 5) / 3;
			fruits.add(new Pineapple(row, col));
		}
	}

	/**
	 * Adds cherries in a distributed pattern across the map.
	 */
	private void addCherriesInPattern(int count) {
		int width = map.getWidth();
		int height = map.getHeight();

		for (int i = 0; i < count; i++) {
			int row = 3 + (i % 3) * (height - 6) / 3;
			int col = 3 + (i / 3) * (width - 6) / 3;
			fruits.add(new Cherry(row, col));
		}
	}

	/**
	 * Moves the player in the specified direction. Handles collision detection,
	 * fruit collection, and enemy encounters.
	 * 
	 * @param playerIndex index of the player (0 o 1)
	 * @param dx          movement in x direction (-1 left, 1 right, 0 none)
	 * @param dy          movement in y direction (-1 up, 1 down, 0 none)
	 */
	public void movePlayer(int playerIndex, int dx, int dy) {
		if (gameOver || gameWon)
			return;
		if (playerIndex >= players.size())
			return;

		Player player = players.get(playerIndex);
		int newX = player.getColumn() + dx;
		int newY = player.getRow() + dy;

		// Verificar si la nueva posición es válida
		if (!map.isWalkable(newX, newY)) {
			return; // Bloqueado por hielo o fuera del mapa
		}

		player.updateDirection(dx, dy); // Actualizar dirección basándose en el movimiento
		player.moveTo(newY, newX); // Mover al jugador
		checkFruitCollision(player); // Verificar colisión con frutas
		checkEnemyCollision(); // Verificar colisión con enemigos
	}

	/**
	 * Allows the player to create or destroy ice blocks in a direction. Creates a
	 * line of ice blocks or destroys them in domino effect.
	 * 
	 * @param playerIndex index of the player
	 * @param dx          direction x (-1 left, 1 right, 0 none)
	 * @param dy          direction y (-1 up, 1 down, 0 none)
	 */
	public void playerShootIce(int playerIndex) {
		if (gameOver || gameWon)
			return;
		if (playerIndex >= players.size())
			return;

		Player player = players.get(playerIndex);
		int dx = player.getLastDirectionX();
		int dy = player.getLastDirectionY();

		playerShootIce(playerIndex, dx, dy);
	}

	/**
	 * Allows a player to create or destroy ice blocks in a specific direction.
	 * 
	 * @param playerIndex index of the player
	 * @param dx          direction x (-1 left, 1 right, 0 none)
	 * @param dy          direction y (-1 up, 1 down, 0 none)
	 */
	public void playerShootIce(int playerIndex, int dx, int dy) {
		if (gameOver || gameWon)
			return;
		if (playerIndex >= players.size())
			return;

		Player player = players.get(playerIndex);
		int startX = player.getColumn() + dx;
		int startY = player.getRow() + dy;

		if (map.hasIce(startX, startY)) {
			destroyIceInLine(startX, startY, dx, dy);
		} else {
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

		// Crear hasta encontrar obstáculo
		for (int i = 0; i < map.getWidth() && i < map.getHeight(); i++) {
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
	 * Updates all fruits' special behaviors. Some fruits like Cherry can teleport
	 * and Pineapple can move.
	 */
	public void updateFruits() {
		if (gameOver || gameWon)
			return;

		for (Fruit fruit : fruits) {
			if (!fruit.isCollected()) {
				fruit.update();

				// Manejar teletransporte de cerezas
				if (fruit instanceof Cherry) {
					Cherry cherry = (Cherry) fruit;
					if (cherry.shouldTeleport()) {
						teleportCherry(cherry);
					}
				}

				// Manejar movimiento de piñas
				if (fruit instanceof Pineapple) {
					movePineapple((Pineapple) fruit);
				}
			}
		}
	}

	/**
	 * Teleports a cherry to a random valid position.
	 */
	private void teleportCherry(Cherry cherry) {
		int newRow, newCol;
		int attempts = 0;

		do {
			newRow = random.nextInt(map.getHeight() - 4) + 2;
			newCol = random.nextInt(map.getWidth() - 4) + 2;
			attempts++;
		} while (!map.isWalkable(newCol, newRow) && attempts < 50);

		if (map.isWalkable(newCol, newRow)) {
			cherry.teleportTo(newRow, newCol);
		}
	}

	/**
	 * Moves a pineapple in its current direction.
	 */
	private void movePineapple(Pineapple pineapple) {
		int newRow = pineapple.getRow() + pineapple.getDirectionRow();
		int newCol = pineapple.getColumn() + pineapple.getDirectionColumn();

		if (map.isWalkable(newCol, newRow)) {
			pineapple.tryMove(newRow, newCol);
		} else {
			// Cambiar dirección si choca con algo
			pineapple.reverseDirection();
		}
	}

	/**
	 * Checks if the player is touching any fruit and collects it. Updates score and
	 * fruit collection counter. Now handles cactus with spikes
	 */
	private void checkFruitCollision(Player player) {
		for (Fruit fruit : fruits) {
			if (!fruit.isCollected() && fruit.getRow() == player.getRow() && fruit.getColumn() == player.getColumn()) {

				if (fruit instanceof Cactus) {
					Cactus cactus = (Cactus) fruit;
					if (cactus.hasSpikes()) {
						gameOver = true;
						return;
					}
				}

				// Recolectar fruta
				fruit.collect();

				// Actualizar estadísticas
				player.addScore(fruit.getPoints()); // Puntaje individual
				gameState.addScore(fruit.getPoints()); // Puntaje global
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
		for (Player player : players) {
			for (Enemy enemy : enemies) {
				if (enemy.getRow() == player.getRow() && enemy.getColumn() == player.getColumn()) {
					gameOver = true;
					return;
				}
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

		initializeLevel(currentLevel, twoPlayerMode, players.get(0).getFlavor(),
				players.size() > 1 ? players.get(1).getFlavor() : "Fresa");
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

		initializeLevel(currentLevel, twoPlayerMode, players.get(0).getFlavor(),
				players.size() > 1 ? players.get(1).getFlavor() : "Fresa");
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
	 * @return the first player (for backwards compatibility)
	 */
	public Player getPlayer() {
		return players.isEmpty() ? null : players.get(0);
	}

	/**
	 * @return reference to the player
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @return true if in 2 player mode
	 */
	public boolean isTwoPlayerMode() {
		return twoPlayerMode;
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

	/**
	 * Sets the player position (used when loading saved games).
	 */
	public void setPlayer(Player player) {
		if (!players.isEmpty()) {
			players.set(0, player);
		} else {
			players.add(player);
		}
	}

	/**
	 * Sets the enemies list (used when loading saved games).
	 */
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	/**
	 * Sets the fruits list (used when loading saved games).
	 */
	public void setFruits(List<Fruit> fruits) {
		this.fruits = fruits;
	}

	/**
	 * Sets the map (used when loading saved games).
	 */
	public void setMap(IceMap map) {
		this.map = map;
	}

	/**
	 * Sets the game state (used when loading saved games).
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}