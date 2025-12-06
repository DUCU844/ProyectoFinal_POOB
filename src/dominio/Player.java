package dominio;

/**
 * Represents the player controlled by the user. Supports multiple players with
 * different ice cream flavors.
 * 
 * @author Alejandra Beltran - Adrian Ducuara
 */
public class Player extends Character {

	private String flavor; // Helado
	private int playerNumber; // 1 o 2
	private int score; // Puntaje individual

    private int lastDirectionX; // -1 (izquierda), 0 (ninguno), 1 (derecha)
    private int lastDirectionY; // -1 (arriba), 0 (ninguno), 1 (abajo)

	/**
	 * Creates a new player at the given position.
	 *
	 * @param row    starting row of the player
	 * @param column starting column of the player
	 */
	public Player(int row, int column) {
		super(row, column);
		this.flavor = "Vainilla";
		this.playerNumber = 1;
		this.score = 0;
	}

	/**
	 * Creates a new player with specific flavor and number.
	 * 
	 * @param row          starting row
	 * @param column       starting column
	 * @param flavor       ice cream flavor ("Vainilla", "Fresa", "Chocolate")
	 * @param playerNumber player number (1 or 2)
	 */
	public Player(int row, int column, String flavor, int playerNumber) {
		super(row, column);
		this.flavor = flavor;
		this.playerNumber = playerNumber;
		this.score = 0;
	}

	/**
	 * Moves the player upward by one tile.
	 *
	 * @return the new row after moving
	 */
	public int moveUp() {
		row--;
		lastDirectionX = 0;
        lastDirectionY = -1; // Ahora mira hacia arriba
		return row;
	}

	/**
	 * Moves the player downward by one tile.
	 *
	 * @return the new row after moving
	 */
	public int moveDown() {
		row++;
		lastDirectionX = 0;
        lastDirectionY = 1; // Ahora mira hacia arriba
		return row;
	}

	/**
	 * Moves the player left by one tile.
	 *
	 * @return the new column after moving
	 */
	public int moveLeft() {
		column--;
		lastDirectionX = -1; // Ahora mira a la izquierda
        lastDirectionY = 0;
		return column;
	}

	/**
	 * Moves the player right by one tile.
	 *
	 * @return the new column after moving
	 */
	public int moveRight() {
		column++;
		lastDirectionX = 1; // Ahora mira a la derecha
        lastDirectionY = 0;
		return column;
	}

	/**
     * Updates the player's facing direction without moving.
     * Used when shooting ice.
     * 
     * @param dx direction x
     * @param dy direction y
     */
    public void updateDirection(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            this.lastDirectionX = dx;
            this.lastDirectionY = dy;
        }
    }
    
    /**
     * @return the X component of the last movement direction
     */
    public int getLastDirectionX() {
        return lastDirectionX;
    }
    
    /**
     * @return the Y component of the last movement direction
     */
    public int getLastDirectionY() {
        return lastDirectionY;
    }
	
	/**
	 * Adds points to this player's score.
	 * 
	 * @param points points to add
	 */
	public void addScore(int points) {
		this.score += points;
	}

	/**
	 * @return the player's ice cream flavor
	 */
	public String getFlavor() {
		return flavor;
	}

	/**
	 * @return the player number (1 or 2)
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * @return the player's individual score
	 */
	public int getScore() {
		return score;
	}
	
	/**
     * Sets the player's flavor.
     * 
     * @param flavor new flavor ("Vainilla", "Fresa", "Chocolate")
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

	/**
	 * Creates or destroys an ice block in the direction the player is facing. (The
	 * Game class will handle interaction with the IceMap.)
	 *
	 * @param dx horizontal direction (-1 left, +1 right, 0 none)
	 * @param dy vertical direction (-1 up, +1 down, 0 none)
	 */
	public void interactWithIce(int dx, int dy) {
		// This method intentionally left unimplemented here.
		// Game.playerShootIce(dx, dy) performs the actual ice toggle.
	}

	/**
	 * Called when the player collects a fruit.
	 */
	public void onCollectFruit() {
		// increment score in Game
	}
}