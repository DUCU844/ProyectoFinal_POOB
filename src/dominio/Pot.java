package dominio;

/**
 * Pot enemy (Maceta) - chases the player but cannot break ice blocks.
 * Used in Level 2.
 * Speed: move 1500ms 
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Pot extends Enemy {
    
    /**
     * Creates a pot enemy at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Pot(int row, int column) {
        super(row, column);
        movementSpeed = 15;
    }
    
    /**
     * Pot chases the player using simple pathfinding.
     * Moves towards player but cannot break ice.
     */
    @Override
    public void update(Game game) {
        Player player = game.getPlayer();
        
        // Calcular dirección hacia el jugador
        int deltaRow = player.getRow() - row;
        int deltaCol = player.getColumn() - column;
        
        // Decidir si moverse vertical u horizontalmente
        // Priorizar el eje con mayor distancia
        int newRow = row;
        int newCol = column;
        
        if (Math.abs(deltaRow) > Math.abs(deltaCol)) {
            // Moverse verticalmente
            if (deltaRow > 0) {
                newRow = row + 1;
            } else if (deltaRow < 0) {
                newRow = row - 1;
            }
        } else {
            // Moverse horizontalmente
            if (deltaCol > 0) {
                newCol = column + 1;
            } else if (deltaCol < 0) {
                newCol = column - 1;
            }
        }
        
        // Intentar moverse si la posición es válida
        if (game.getMap().isWalkable(newCol, newRow)) {
            moveTo(newRow, newCol);
        } else {
            // Si está bloqueado, intentar el otro eje
            if (Math.abs(deltaRow) > Math.abs(deltaCol)) {
                // Intentar horizontal
                newRow = row;
                newCol = column;
                if (deltaCol > 0) {
                    newCol = column + 1;
                } else if (deltaCol < 0) {
                    newCol = column - 1;
                }
            } else {
                // Intentar vertical
                newRow = row;
                newCol = column;
                if (deltaRow > 0) {
                    newRow = row + 1;
                } else if (deltaRow < 0) {
                    newRow = row - 1;
                }
            }
            
            if (game.getMap().isWalkable(newCol, newRow)) {
                moveTo(newRow, newCol);
            }
        }
    }
    
    @Override
    public String getType() {
        return "Pot";
    }
}