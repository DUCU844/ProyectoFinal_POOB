package dominio;

/**
 * Orange Squid enemy (Calamar Naranja) - chases the player 
 * and can break ice blocks one at a time.
 * Used in Level 3 (hardest enemy).
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class OrangeSquid extends Enemy {
    
    private int breakCooldown;
    private static final int breakDelay = 10; // Espera 10 ticks antes de romper hielo
    
    /**
     * Creates an orange squid at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public OrangeSquid(int row, int column) {
        super(row, column);
        this.breakCooldown = 0;
    }
    
    /**
     * Orange Squid chases player and breaks ice blocks in its path.
     */
    @Override
    public void update(Game game) {
        Player player = game.getPlayer();
        
        // Calcular dirección hacia el jugador
        int deltaRow = player.getRow() - row;
        int deltaCol = player.getColumn() - column;
        
        // Decidir dirección de movimiento
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
        
        // Verificar si puede moverse
        if (game.getMap().isWalkable(newCol, newRow)) {
            moveTo(newRow, newCol);
            breakCooldown = 0;
        } else if (game.getMap().hasIce(newCol, newRow)) {
            // Hay hielo, intentar romperlo
            breakCooldown++;
            if (breakCooldown >= breakDelay) {
                game.getMap().toggleIce(newCol, newRow); // Rompe el hielo
                breakCooldown = 0;
            }
        } else {
            // Bloqueado por borde del mapa, intentar otro eje
            if (Math.abs(deltaRow) > Math.abs(deltaCol)) {
                newRow = row;
                newCol = column;
                if (deltaCol > 0) {
                    newCol = column + 1;
                } else if (deltaCol < 0) {
                    newCol = column - 1;
                }
            } else {
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
                breakCooldown = 0;
            } else if (game.getMap().hasIce(newCol, newRow)) {
                breakCooldown++;
                if (breakCooldown >= breakDelay) {
                    game.getMap().toggleIce(newCol, newRow);
                    breakCooldown = 0;
                }
            }
        }
    }
    
    @Override
    public String getType() {
        return "OrangeSquid";
    }
}