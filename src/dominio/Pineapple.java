package dominio;

/**
 * Pineapple fruit - moves constantly in a pattern.
 * Worth 120 points.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Pineapple extends Fruit {
    
    private static final int pineapple_points = 120;
    private int directionRow;
    private int directionColumn;
    
    /**
     * Creates a pineapple at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Pineapple(int row, int column) {
        super(row, column, pineapple_points);
        // Empieza moviéndose a la derecha
        this.directionRow = 0;
        this.directionColumn = 1;
    }
    
    @Override
    public void update() {
        // La piña se mueve en su dirección actual
        // El Game verificará si puede moverse y actualizará la posición
    }
    
    /**
     * Attempts to move pineapple in current direction.
     * 
     * @param newRow proposed new row
     * @param newCol proposed new column
     * @return true if movement was valid
     */
    public boolean tryMove(int newRow, int newCol) {
        setPosition(newRow, newCol);
        return true;
    }
    
    /**
     * Changes direction when hitting an obstacle.
     */
    public void reverseDirection() {
        directionRow = -directionRow;
        directionColumn = -directionColumn;
    }
    
    /**
     * @return current direction in rows
     */
    public int getDirectionRow() {
        return directionRow;
    }
    
    /**
     * @return current direction in columns
     */
    public int getDirectionColumn() {
        return directionColumn;
    }
    
    @Override
    public String getType() {
        return "Pineapple";
    }
}