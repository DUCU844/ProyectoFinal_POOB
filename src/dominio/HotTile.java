package dominio;

/**
 * Hot Tile obstacle - melts ice blocks instantly when created on top.
 * If the player creates a line of ice blocks, only the blocks directly
 * on hot tiles melt; the rest remain intact.
 * Players and enemies can walk freely on hot tiles.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class HotTile {
    
    private int row;
    private int column;
    
    /**
     * Creates a hot tile at the specified position.
     * 
     * @param row row position
     * @param column column position
     */
    public HotTile(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * Updates the hot tile state.
     * Hot tiles are passive and don't need updates.
     */
    public void update() {
        // Las baldosas calientes no cambian de estado
    }
    
    /**
     * @return row position
     */
    public int getRow() {
        return row;
    }
    
    /**
     * @return column position
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * @return type identifier
     */
    public String getType() {
        return "HotTile";
    }
}
