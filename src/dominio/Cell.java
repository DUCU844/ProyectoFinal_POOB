package dominio;

/**
 * Represents a single cell on the game board.
 * A cell may contain a player, enemy, fruit, or ice block.
 */
public class Cell {

    private int row;
    private int column;

    private Player player;
    private Enemy enemy;
    private Fruit fruit;
    private Ice ice;

    /**
     * Creates an empty cell.
     *
     * @param row row of the cell
     * @param column column of the cell
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /** @return player in this cell or null */
    public Player getPlayer() { return player; }

    /** @return enemy in this cell or null */
    public Enemy getEnemy() { return enemy; }

    /** @return fruit in this cell or null */
    public Fruit getFruit() { return fruit; }

    /** @return ice block in this cell or null */
    public Ice getIce() { return ice; }

    public void setPlayer(Player p) { player = p; }
    public void setEnemy(Enemy e) { enemy = e; }
    public void setFruit(Fruit f) { fruit = f; }
    public void setIce(Ice i) { ice = i; }
}
