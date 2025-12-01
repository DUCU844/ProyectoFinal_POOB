package dominio;

/**
 * Represents the player controlled by the user.
 * Inherits basic position attributes from Character.
 */
public class Player extends Character {

    /**
     * Creates a new player at the given position.
     *
     * @param row starting row of the player
     * @param column starting column of the player
     */
    public Player(int row, int column) {
        super(row, column);
    }

    /**
     * Moves the player upward by one tile.
     *
     * @return the new row after moving
     */
    public int moveUp() {
        row--;
        return row;
    }

    /**
     * Moves the player downward by one tile.
     *
     * @return the new row after moving
     */
    public int moveDown() {
        row++;
        return row;
    }

    /**
     * Moves the player left by one tile.
     *
     * @return the new column after moving
     */
    public int moveLeft() {
        column--;
        return column;
    }

    /**
     * Moves the player right by one tile.
     *
     * @return the new column after moving
     */
    public int moveRight() {
        column++;
        return column;
    }

    /**
     * Creates or destroys an ice block in the direction the player is facing.
     * (The Game class will handle interaction with the IceMap.)
     *
     * @param dx horizontal direction (-1 left, +1 right, 0 none)
     * @param dy vertical direction (-1 up, +1 down, 0 none)
     */
    public void interactWithIce(int dx, int dy) {
        // This method intentionally left unimplemented here.
        // Game.playerShootIce(dx, dy) performs the actual ice toggle.
    }

    /**
     * Called when the player collects a fruit.
     * (Useful for counting score later.)
     */
    public void onCollectFruit() {
        //increment score here in the future.
    }
}