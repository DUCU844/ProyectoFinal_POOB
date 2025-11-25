package Dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Main game logic controller.
 * Manages the player, enemies, map, fruits and game tick updates.
 */
public class Game {

    private Player player;
    private List<Enemy> enemies;
    private List<Fruit> fruits;
    private IceMap map;
    private boolean gameOver;
    private boolean gameWon;

    /**
     * Creates a new Game instance with default settings.
     * Initializes the map, player, enemies, and fruits.
     */
    public Game() {
        this.map = new IceMap(20, 15); // simple grid size
        this.enemies = new ArrayList<>();
        this.fruits = new ArrayList<>();
        this.gameOver = false;
        this.gameWon = false;

        initPlayer();
        initEnemies();
        initFruits();
    }

    /**
     * Initializes the player in the center of the map.
     */
    private void initPlayer() {
        int startX = map.getWidth() / 2;
        int startY = map.getHeight() / 2;
        this.player = new Player(startX, startY);
    }

    /**
     * Initializes some enemies with simple positions.
     */
    private void initEnemies() {
        enemies.add(new Enemy(2, 2));
        enemies.add(new Enemy(map.getWidth() - 3, 2));
        enemies.add(new Enemy(map.getWidth() - 3, map.getHeight() - 3));
    }

    /**
     * Initializes fruits in basic positions on the map.
     */
    private void initFruits() {
        fruits.add(new Fruit(1, 1));
        fruits.add(new Fruit(5, 10));
        fruits.add(new Fruit(12, 4));
    }

    /**
     * Moves the player in the chosen direction.
     * @param dx movement in x direction
     * @param dy movement in y direction
     */
    public void movePlayer(int dx, int dy) {
        if (gameOver || gameWon) return;

        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (map.isWalkable(newX, newY)) {
            player.setPosition(newX, newY);
            checkFruitCollision();
            checkEnemyCollision();
        }
    }

    /**
     * Allows the player to create or break ice in the facing direction.
     * @param dx direction x
     * @param dy direction y
     */
    public void playerShootIce(int dx, int dy) {
        if (gameOver || gameWon) return;

        int targetX = player.getX() + dx;
        int targetY = player.getY() + dy;

        map.toggleIce(targetX, targetY);
    }

    /**
     * Updates all enemy positions with simple AI.
     * Called once per game tick.
     */
    public void updateEnemies() {
        if (gameOver || gameWon) return;

        for (Enemy enemy : enemies) {
            int dx = enemy.computeMoveX(player);
            int dy = enemy.computeMoveY(player);

            int newX = enemy.getX() + dx;
            int newY = enemy.getY() + dy;

            if (map.isWalkable(newX, newY)) {
                enemy.setPosition(newX, newY);
            }
        }

        checkEnemyCollision();
    }

    /**
     * Checks if the player touches a fruit and removes it.
     */
    private void checkFruitCollision() {
    fruits.removeIf(fruit ->
        fruit.getRow() == player.getRow() &&
        fruit.getColumn() == player.getColumn()
    );

    if (fruits.isEmpty()) {
        gameWon = true;
    }
    }


    /**
     * Checks if an enemy touches the player.
     */
    private void checkEnemyCollision() {
        for (Enemy enemy : enemies) {
            if (enemy.getX() == player.getX() &&
                enemy.getY() == player.getY()) {
                gameOver = true;
            }
        }
    }

    /**
     * @return true if the game is over because the player lost
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @return true if the player collected all fruits
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
     * @return list of all enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * @return list of all fruits
     */
    public List<Fruit> getFruits() {
        return fruits;
    }

    /**
     * @return reference to the map
     */
    public IceMap getMap() {
        return map;
    }
}