package dominio;

/**
 * Represents the game map made of walkable tiles and ice blocks.
 * A value of true in the grid means there is an ice block.
 */
public class IceMap {

    private int width;
    private int height;
    private boolean[][] iceGrid;

    /**
     * Creates a new IceMap with the given size.
     * @param width number of columns
     * @param height number of rows
     */
    public IceMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.iceGrid = new boolean[height][width];

        generateBasicWalls();
    }

    /**
     * Generates simple border walls around the map.
     */
    private void generateBasicWalls() {
        for (int x = 0; x < width; x++) {
            iceGrid[0][x] = true;
            iceGrid[height - 1][x] = true;
        }

        for (int y = 0; y < height; y++) {
            iceGrid[y][0] = true;
            iceGrid[y][width - 1] = true;
        }
    }

    /**
     * Checks if a tile can be walked on (no ice block).
     * @param x tile x
     * @param y tile y
     * @return true if the tile is inside bounds and has no ice
     */
    public boolean isWalkable(int x, int y) {
        if (!inBounds(x, y)) return false;
        return !iceGrid[y][x];
    }

    /**
     * Toggles (creates or destroys) an ice block at the given position.
     * @param x tile x
     * @param y tile y
     */
    public void toggleIce(int x, int y) {
        if (!inBounds(x, y)) return;
        iceGrid[y][x] = !iceGrid[y][x];
    }

    /**
     * Checks if coordinates are within the map.
     * @param x position x
     * @param y position y
     * @return true if valid
     */
    private boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Returns whether there is ice at a tile.
     * @param x tile x
     * @param y tile y
     * @return true if there is ice
     */
    public boolean hasIce(int x, int y) {
        if (!inBounds(x, y)) return false;
        return iceGrid[y][x];
    }

    /**
     * @return map width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return map height
     */
    public int getHeight() {
        return height;
    }
}