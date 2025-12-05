package dominio;

/**
 * Troll enemy - moves in straight lines and changes direction
 * when hitting obstacles or map edges.
 * Does not chase the player or break ice blocks.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Troll extends Enemy {
    
    /**
     * Creates a troll at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Troll(int row, int column) {
        super(row, column);
    }
    
    /**
     * Moves the troll in a straight line.
     * Changes direction when hitting ice blocks or edges.
     */
    @Override
    public void update(Game game) {
        int newRow = row + directionRow;
        int newCol = column + directionColumn;
        
        // Verificar si puede moverse a la nueva posición
        if (game.getMap().isWalkable(newCol, newRow)) {
            moveTo(newRow, newCol);
        } else {
            // Si choca con algo, cambia de dirección
            reverseDirection();
        }
    }
    
    @Override
    public String getType() {
        return "TROLL";
    }
}