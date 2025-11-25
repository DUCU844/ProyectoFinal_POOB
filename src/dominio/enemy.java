package Dominio;

/**
 * Represents an enemy that moves automatically.
 */
public class Enemy extends Character {

    private int dirR = 0;
    private int dirC = 1;

    /**
     * Creates a new enemy at the given position.
     *
     * @param row initial row
     * @param column initial column
     */
    public Enemy(int row, int column) {
        super(row, column);
    }

    /**
     * Computes the movement in rows to get closer to the player.
     *
     * @param player reference to the player
     * @return -1, 0, or 1 depending on vertical direction
     */
    public int computeMoveX(Player player) {
        if (player.getRow() > this.row) return 1;
        if (player.getRow() < this.row) return -1;
        return 0;
    }

    /**
     * Computes the movement in columns to get closer to the player.
     *
     * @param player reference to the player
     * @return -1, 0, or 1 depending on horizontal direction
     */
    public int computeMoveY(Player player) {
        if (player.getColumn() > this.column) return 1;
        if (player.getColumn() < this.column) return -1;
        return 0;
    }

    /**
     * Reverses the enemy's direction of movement.
     */
    public void reverseDirection() {
        dirR = -dirR;
        dirC = -dirC;
    }

    /** @return direction row of the enemy */
    public int getDirectionRow() { return dirR; }

    /** @return direction column of the enemy */
    public int getDirectionColumn() { return dirC; }
}