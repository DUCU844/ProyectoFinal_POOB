package dominio;

/**
 * Igloo structure - solid obstacle that blocks movement.
 * Cannot be destroyed by players or enemies.
 * Used for level design and decoration.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Igloo {
    
    private int row;
    private int column;
    private int width;  // Ancho del igloo (en celdas)
    private int height; // Alto del igloo (en celdas)
    
    /**
     * Creates an igloo at the specified position.
     * 
     * @param row top-left row position
     * @param column top-left column position
     * @param width width in cells (default 2)
     * @param height height in cells (default 2)
     */
    public Igloo(int row, int column, int width, int height) {
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Creates a standard 2x2 igloo.
     * 
     * @param row top-left row position
     * @param column top-left column position
     */
    public Igloo(int row, int column) {
        this(row, column, 2, 2);
    }
    
    /**
     * Checks if a position is occupied by this igloo.
     * 
     * @param checkRow row to check
     * @param checkCol column to check
     * @return true if the position is part of the igloo
     */
    public boolean occupiesPosition(int checkRow, int checkCol) {
        return checkRow >= row && checkRow < row + height &&
               checkCol >= column && checkCol < column + width;
    }
    
    /**
     * @return top-left row position
     */
    public int getRow() {
        return row;
    }
    
    /**
     * @return top-left column position
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * @return width in cells
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * @return height in cells
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * @return type identifier
     */
    public String getType() {
        return "Igloo";
    }
}
