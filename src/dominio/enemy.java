package dominio;

/**
 * Abstract base class for all enemies.
 * Each enemy type has different movement and behavior patterns.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public abstract class Enemy extends Character {
    
    protected int directionRow;
    protected int directionColumn;
    
    /**
     * Creates a new enemy at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Enemy(int row, int column) {
        super(row, column);
        this.directionRow = 0;
        this.directionColumn = 1; // Empieza moviéndose a la derecha
    }
    
    /**
     * Updates enemy position and behavior each game tick.
     * Each enemy type implements its own movement logic.
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
     * Public method for loading saved games.
     * 
     * @param dr direction row
     * @param dc direction column
     */
    public void setDirection(int dr, int dc) {
        this.directionRow = dr;
        this.directionColumn = dc;
    }
    
    /**
     * @return string identifier for the enemy type
     */
    public abstract String getType();
}