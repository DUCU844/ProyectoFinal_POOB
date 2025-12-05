package dominio;

/**
 * Represents an ice block placed or destroyed by the player.
 */
public class Ice {

    private int row;
    private int column;

    /**
     * Creates a new ice block.
     *
     * @param row row of the block
     * @param column column of the block
     */
    public Ice(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /** @return row of the ice block */
    public int getRow() { return row; }

    /** @return column of the ice block */
    public int getColumn() { return column; }
}
