package dominio;

/**
 * Abstract base class for all collectible fruits in the game.
 * Each fruit type has a point value and specific behavior.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public abstract class Fruit {
    
    protected int row;
    protected int column;
    protected int points;
    protected boolean collected;
    
    /**
     * Creates a new fruit at the specified position.
     * 
     * @param row initial row position
     * @param column initial column position
     * @param points point value when collected
     */
    public Fruit(int row, int column, int points) {
        this.row = row;
        this.column = column;
        this.points = points;
        this.collected = false;
    }
    
    /**
     * Updates the fruit's state each game tick.
     * Some fruits have special behaviors like teleporting.
     */
    public abstract void update();
    
    /**
     * Marks this fruit as collected by the player.
     */
    public void collect() {
        this.collected = true;
    }
    
    /**
     * @return true if fruit has been collected
     */
    public boolean isCollected() {
        return collected;
    }
    
    /**
     * @return points awarded when collected
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * @return current row position
     */
    public int getRow() {
        return row;
    }
    
    /**
     * @return current column position
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Updates the fruit's position.
     * Used by fruits that can move.
     * 
     * @param row new row position
     * @param column new column position
     */
    protected void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * @return string identifier for the fruit type
     */
    public abstract String getType();
}