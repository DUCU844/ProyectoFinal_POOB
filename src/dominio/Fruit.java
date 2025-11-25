package Dominio;

/**
 * Represents a collectible fruit.
 */
public class Fruit {

    private int row;
    private int column;

    /**
     * Creates a new fruit.
     *
     * @param row row of the fruit
     * @param column column of the fruit
     */
    public Fruit(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /** @return row of the fruit */
    public int getRow() { return row; }

    /** @return column of the fruit */
    public int getColumn() { return column; }
}