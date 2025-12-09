package dominio;

/**
 * Abstract base class for all enemies. Each enemy type has different movement
 * and behavior patterns.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public abstract class Enemy extends Character {

	protected int directionRow;
	protected int directionColumn;

	protected int movementCooldown;
	protected int movementSpeed;

	/**
	 * Creates a new enemy at the specified position.
	 * 
	 * @param row    initial row
	 * @param column initial column
	 */
	public Enemy(int row, int column) {
		super(row, column);
		directionRow = 0;
		directionColumn = 1; // Empieza moviéndose a la derecha
		movementCooldown = 0;
		movementSpeed = 3; // Velocidad defecto de 300ms
	}

	/**
	 * Checks if the enemy can move this tick. Implements the cooldown system.
	 * 
	 * @return true if enough time has passed to move
	 */
	protected boolean canMove() {
		movementCooldown++;
		if (movementCooldown >= movementSpeed) {
			movementCooldown = 0; // Reiniciar el contador
			return true;
		}
		return false;
	}

	/**
	 * Updates enemy position and behavior each game tick. Each enemy type
	 * implements its own movement logic.
	 * 
	 * @param game reference to the game for collision checks
	 */
	public abstract void update(Game game);

	/**
	 * Reverses the enemy's current direction.
	 */
	public void reverseDirection() {
		directionRow = -directionRow;
		directionColumn = -directionColumn;
	}

	/**
	 * @return current direction in rows (-1, 0, or 1)
	 */
	public int getDirectionRow() {
		return directionRow;
	}

	/**
	 * @return current direction in columns (-1, 0, or 1)
	 */
	public int getDirectionColumn() {
		return directionColumn;
	}

	/**
	 * Sets a new direction for the enemy.
	 * 
	 * @param dr direction row
	 * @param dc direction column
	 */
	public void setDirection(int dr, int dc) {
		this.directionRow = dr;
		this.directionColumn = dc;
	}
	
	/**
     * Sets the movement speed for this enemy.
     * 
     * @param speed ticks between movements (1=Very fast, 10=Very slow)
     */
    public void setMovementSpeed(int speed) {
        this.movementSpeed = speed;
    }

	/**
	 * @return string identifier for the enemy type
	 */
	public abstract String getType();
}