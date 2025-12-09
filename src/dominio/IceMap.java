package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game map made of walkable tiles and ice blocks. A value of
 * true in the grid means there is an ice block.
 */
public class IceMap {

	private int width;
	private int height;
	private boolean[][] iceGrid; // true, hay hielo

	private List<Firepit> firepits;
	private List<HotTile> hotTiles;
	private List<Igloo> igloos;

	/**
	 * Creates a new IceMap with the given size.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public IceMap(int width, int height) {
		this.width = width;
		this.height = height;
		iceGrid = new boolean[height][width];
		firepits = new ArrayList<>();
		hotTiles = new ArrayList<>();
		igloos = new ArrayList<>();

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
	 * Checks if a tile can be walked on. Considers ice blocks, igloos, and firepits
	 * 
	 * @param x tile x
	 * @param y tile y
	 * @return true if the tile is inside bounds and has no ice
	 */
	public boolean isWalkable(int x, int y) {
		if (!inBounds(x, y))
			return false;

		if (iceGrid[y][x])
			return false; // Verificar que no sea hielo

		// Verifica que no sea iglus
		for (Igloo igloo : igloos) {
			if (igloo.occupiesPosition(y, x)) {
				return false;
			}
		}
		// Las fogatas y baldosas calientes si se pueden caminar
		// fogatas eliminan al jugador
		return true;

	}

	/**
	 * Checks if a position is safe for the player. Returns false if there's a lit
	 * firepit.
	 * 
	 * @param x tile x
	 * @param y tile y
	 * @return true if safe for player
	 */
	public boolean isSafeForPlayer(int x, int y) {
		// Verificar si hay una fogata encendida en esta posición
		for (Firepit firepit : firepits) {
			if (firepit.getColumn() == x && firepit.getRow() == y && firepit.isLit()) {
				return false; // Fogata encendida
			}
		}
		return true;
	}

	/**
	 * Toggles (creates or destroys) an ice block at the given position. Hot tiles
	 * melt ice instantly. Creating ice on a firepit extinguishes it temporarily.
	 * 
	 * @param x tile x
	 * @param y tile y
	 * @return true if ice was successfully created/destroyed
	 */
	public boolean toggleIce(int x, int y) {
		if (!inBounds(x, y))
			return false;

		// Si hay un iglú, no se puede poner hielo
		for (Igloo igloo : igloos) {
			if (igloo.occupiesPosition(y, x)) {
				return false;
			}
		}

		// Si estamos creando hielo
		if (!iceGrid[y][x]) {
			// Verificar si hay una fogata aquí
			for (Firepit firepit : firepits) {
				if (firepit.getColumn() == x && firepit.getRow() == y) {
					// No crear hielo, pero apagar la fogata
					firepit.extinguish();
					return true;
				}
			}

			// Verificar si hay una baldosa caliente
			if (hasHotTile(x, y)) {
				// El hielo se derrite instantáneamente, no se crea
				return false;
			}

			// Crear hielo normalmente
			iceGrid[y][x] = true;
			return true;
		} else {
			// Destruir hielo
			iceGrid[y][x] = false;
			return true;
		}

	}

	/**
	 * Checks if coordinates are within the map.
	 * 
	 * @param x position x
	 * @param y position y
	 * @return true if valid
	 */
	private boolean inBounds(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	/**
	 * Returns whether there is ice at a tile.
	 * 
	 * @param x tile x
	 * @param y tile y
	 * @return true if there is ice
	 */
	public boolean hasIce(int x, int y) {
		if (!inBounds(x, y))
			return false;
		return iceGrid[y][x];
	}

	/**
	 * Checks if there is a hot tile at a position.
	 * 
	 * @param x tile x
	 * @param y tile y
	 * @return true if there is a hot tile
	 */
	public boolean hasHotTile(int x, int y) {
		for (HotTile tile : hotTiles) {
			if (tile.getColumn() == x && tile.getRow() == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a firepit to the map.
	 * 
	 * @param firepit the firepit to add
	 */
	public void addFirepit(Firepit firepit) {
		firepits.add(firepit);
	}

	/**
	 * Adds a hot tile to the map.
	 * 
	 * @param hotTile the hot tile to add
	 */
	public void addHotTile(HotTile hotTile) {
		hotTiles.add(hotTile);
	}

	/**
	 * Adds an igloo to the map.
	 * 
	 * @param igloo the igloo to add
	 */
	public void addIgloo(Igloo igloo) {
		igloos.add(igloo);
	}

	/**
	 * Updates all dynamic obstacles (firepits).
	 */
	public void updateObstacles() {
		for (Firepit firepit : firepits) {
			firepit.update();
		}
	}

	/**
	 * @return list of all firepits
	 */
	public List<Firepit> getFirepits() {
		return firepits;
	}

	/**
	 * @return list of all hot tiles
	 */
	public List<HotTile> getHotTiles() {
		return hotTiles;
	}

	/**
	 * @return list of all igloos
	 */
	public List<Igloo> getIgloos() {
		return igloos;
	}
	
	/**
     * Creates a pattern of ice blocks for level design.
     * @param pattern 2D array where true = ice block
     */
    public void setIcePattern(boolean[][] pattern) {
        for (int y = 0; y < Math.min(pattern.length, height); y++) {
            for (int x = 0; x < Math.min(pattern[y].length, width); x++) {
                iceGrid[y][x] = pattern[y][x];
            }
        }
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

	/**
	 * Clears all obstacles (except walls). Used when resetting a level.
	 */
	public void clearObstacles() {
		firepits.clear();
		hotTiles.clear();
		igloos.clear();

		// Limpiar hielo interno (mantener bordes)
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				iceGrid[y][x] = false;
			}
		}
	}
}