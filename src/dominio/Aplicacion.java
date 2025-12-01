package dominio;

import java.util.ArrayList;

/**
 * Represents the main game logic for the Bad Ice Cream–style game.
 * It manages the board, the player, enemies, fruits, and ice blocks.
 */
public class Aplicacion {

    private int rows;
    private int columns;
    private Cell[][] board;

    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Fruit> fruits;

    private boolean gameOver;

    /**
     * Creates a new game with the given board size.
     *
     * @param rows    number of board rows
     * @param columns number of board columns
     */
    public Aplicacion(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        board = new Cell[rows][columns];
        enemies = new ArrayList<>();
        fruits = new ArrayList<>();

        initializeBoard();
        gameOver = false;
    }

    /**
     * Initializes the board with empty cells.
     */
    private void initializeBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                board[r][c] = new Cell(r, c);
            }
        }
    }

    /**
     * Adds the player to the board at the given position.
     *
     * @param row row position
     * @param col column position
     */
    public void addPlayer(int row, int col) {
        player = new Player(row, col);
        board[row][col].setPlayer(player);
    }

    /**
     * Adds a fruit to the board at the given position.
     *
     * @param row row position
     * @param col column position
     */
    public void addFruit(int row, int col) {
        Fruit fruit = new Fruit(row, col);
        fruits.add(fruit);
        board[row][col].setFruit(fruit);
    }

    /**
     * Adds an enemy to the board at the given position.
     *
     * @param row row position
     * @param col column position
     */
    public void addEnemy(int row, int col) {
        Enemy e = new Enemy(row, col);
        enemies.add(e);
        board[row][col].setEnemy(e);
    }

    /**
     * Updates the game state. Moves enemies and checks collisions.
     */
    public void update() {
        if (gameOver) return;

        moveEnemies();
        checkPlayerEnemyCollision();
        checkWinCondition();
    }

    /**
     * Attempts to move the player in the given direction.
     *
     * @param dr change in row
     * @param dc change in column
     */
    public void movePlayer(int dr, int dc) {
        if (gameOver) return;

        int newRow = player.getRow() + dr;
        int newCol = player.getColumn() + dc;

        if (!validPosition(newRow, newCol)) return;

        Cell target = board[newRow][newCol];

        // Blocked by ice
        if (target.getIce() != null) return;

        // Fruit collected
        if (target.getFruit() != null) {
            fruits.remove(target.getFruit());
            target.setFruit(null);
        }

        // Enemy collision
        if (target.getEnemy() != null) {
            gameOver = true;
            return;
        }

        // Move player
        board[player.getRow()][player.getColumn()].setPlayer(null);
        player.moveTo(newRow, newCol);
        target.setPlayer(player);
    }

    /**
     * Player shoots ice in a direction; creates or destroys a block.
     *
     * @param dr direction row
     * @param dc direction column
     */
    public void shootIce(int dr, int dc) {
        int row = player.getRow() + dr;
        int col = player.getColumn() + dc;

        if (!validPosition(row, col)) return;

        Cell target = board[row][col];

        if (target.getIce() == null) {
            target.setIce(new Ice(row, col));
        } else {
            target.setIce(null);
        }
    }

    /**
     * Moves all enemies according to their direction.
     */
    private void moveEnemies() {
        for (Enemy e : enemies) {
            int dr = e.getDirectionRow();
            int dc = e.getDirectionColumn();

            int nr = e.getRow() + dr;
            int nc = e.getColumn() + dc;

            if (!validPosition(nr, nc) || board[nr][nc].getIce() != null) {
                e.reverseDirection();
                continue;
            }

            board[e.getRow()][e.getColumn()].setEnemy(null);
            e.moveTo(nr, nc);
            board[nr][nc].setEnemy(e);
        }
    }

    /**
     * Checks if the player is touching an enemy.
     */
    private void checkPlayerEnemyCollision() {
        Cell current = board[player.getRow()][player.getColumn()];
        if (current.getEnemy() != null) {
            gameOver = true;
        }
    }

    /**
     * Checks whether all fruits have been collected.
     */
    private void checkWinCondition() {
        if (fruits.isEmpty()) {
            gameOver = true;
        }
    }

    /**
     * Validates whether a position is inside the board.
     *
     * @param r row
     * @param c column
     * @return true if valid; false otherwise
     */
    private boolean validPosition(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < columns;
    }

    /**
     * Returns whether the game is finished.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
