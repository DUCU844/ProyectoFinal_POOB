package dominio;

/**
 * Represents a generic character on the board. Both Player and Enemy inherit
 * from this class.
 */
public abstract class Character {

	protected int row;
	protected int column;

	/**
	 * Creates a new character at the given position.
	 *
	 * @param row    initial row
	 * @param column initial column
	 */
	public Character(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Moves the character to a new position.
	 *
	 * @param r new row
	 * @param c new column
	 */
	public void moveTo(int r, int c) {
		this.row = r;
		this.column = c;
	}

	/** @return the row of the character */
	public int getRow() {
		return row;
	}

	/** @return the column of the character */
	public int getColumn() {
		return column;
	}

	public int getX() {
		return column;
	}

	public int getY() {
		return row;
	}

	public void setPosition(int r, int c) {
		this.row = r;
		this.column = c;
	}

}