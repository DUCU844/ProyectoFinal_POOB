package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.*;
import dominio.Character;

/**
 * Unit tests for Enemy classes. Tests Troll, Pot, and OrangeSquid enemy types.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class TestEnemy {

	private Troll troll;
	private Pot pot;
	private OrangeSquid squid;
	private Game game;

	@BeforeEach
	public void setUp() {
		troll = new Troll(5, 5);
		pot = new Pot(10, 10);
		squid = new OrangeSquid(15, 15);
		game = new Game(1);
	}

	/**
	 * Test 1: Troll creation and initial position
	 */
	@Test
	public void testTrollCreation() {
		assertEquals(5, troll.getRow(), "Troll debe estar en fila 5");
		assertEquals(5, troll.getColumn(), "Troll debe estar en columna 5");
		assertEquals("TROLL", troll.getType(), "El tipo debe ser TROLL");
	}

	/**
	 * Test 2: Troll initial direction
	 */
	@Test
	public void testTrollInitialDirection() {
		assertEquals(0, troll.getDirectionRow(), "Dirección inicial en fila debe ser 0");
		assertEquals(1, troll.getDirectionColumn(), "Dirección inicial en columna debe ser 1");
	}

	/**
	 * Test 3: Troll can move
	 */
	@Test
	public void testTrollMovement() {
		int initialRow = troll.getRow();
		int initialCol = troll.getColumn();

		troll.moveTo(initialRow + 1, initialCol + 1);

		assertEquals(initialRow + 1, troll.getRow(), "Troll debe moverse a nueva fila");
		assertEquals(initialCol + 1, troll.getColumn(), "Troll debe moverse a nueva columna");
	}

	/**
	 * Test 4: Troll reverse direction
	 */
	@Test
	public void testTrollReverseDirection() {
		int originalDirRow = troll.getDirectionRow();
		int originalDirCol = troll.getDirectionColumn();

		troll.reverseDirection();

		assertEquals(-originalDirRow, troll.getDirectionRow(), "Dirección fila debe invertirse");
		assertEquals(-originalDirCol, troll.getDirectionColumn(), "Dirección columna debe invertirse");
	}

	/**
	 * Test 5: Troll setDirection method
	 */
	@Test
	public void testTrollSetDirection() {
		troll.setDirection(-1, 0);

		assertEquals(-1, troll.getDirectionRow(), "Dirección fila debe ser -1");
		assertEquals(0, troll.getDirectionColumn(), "Dirección columna debe ser 0");
	}

	/**
	 * Test 6: Pot creation and initial position
	 */
	@Test
	public void testPotCreation() {
		assertEquals(10, pot.getRow(), "Pot debe estar en fila 10");
		assertEquals(10, pot.getColumn(), "Pot debe estar en columna 10");
		assertEquals("Pot", pot.getType(), "El tipo debe ser Pot");
	}

	/**
	 * Test 7: Pot can move
	 */
	@Test
	public void testPotMovement() {
		int initialRow = pot.getRow();
		int initialCol = pot.getColumn();

		pot.moveTo(initialRow - 1, initialCol - 1);

		assertEquals(initialRow - 1, pot.getRow(), "Pot debe moverse a nueva fila");
		assertEquals(initialCol - 1, pot.getColumn(), "Pot debe moverse a nueva columna");
	}

	/**
	 * Test 8: Pot direction management
	 */
	@Test
	public void testPotDirectionManagement() {
		pot.setDirection(1, 1);

		assertEquals(1, pot.getDirectionRow(), "Pot debe tener dirección fila 1");
		assertEquals(1, pot.getDirectionColumn(), "Pot debe tener dirección columna 1");
	}

	/**
	 * Test 9: OrangeSquid creation and initial position
	 */
	@Test
	public void testSquidCreation() {
		assertEquals(15, squid.getRow(), "Squid debe estar en fila 15");
		assertEquals(15, squid.getColumn(), "Squid debe estar en columna 15");
		assertEquals("OrangeSquid", squid.getType(), "El tipo debe ser OrangeSquid");
	}

	/**
	 * Test 10: OrangeSquid can move
	 */
	@Test
	public void testSquidMovement() {
		int initialRow = squid.getRow();
		int initialCol = squid.getColumn();

		squid.moveTo(initialRow + 2, initialCol + 2);

		assertEquals(initialRow + 2, squid.getRow(), "Squid debe moverse a nueva fila");
		assertEquals(initialCol + 2, squid.getColumn(), "Squid debe moverse a nueva columna");
	}

	/**
	 * Test 11: OrangeSquid direction management
	 */
	@Test
	public void testSquidDirectionManagement() {
		squid.setDirection(-1, -1);

		assertEquals(-1, squid.getDirectionRow(), "Squid debe tener dirección fila -1");
		assertEquals(-1, squid.getDirectionColumn(), "Squid debe tener dirección columna -1");
	}

	/**
	 * Test 12: All enemies inherit from Character
	 */
	@Test
	public void testEnemiesAreCharacters() {
		assertTrue(troll instanceof Character, "Troll debe heredar de Character");
		assertTrue(pot instanceof Character, "Pot debe heredar de Character");
		assertTrue(squid instanceof Character, "Squid debe heredar de Character");
	}

	/**
	 * Test 13: All enemies have getX and getY methods
	 */
	@Test
	public void testEnemiesHaveXYMethods() {
		assertEquals(troll.getColumn(), troll.getX(), "getX debe retornar columna");
		assertEquals(troll.getRow(), troll.getY(), "getY debe retornar fila");

		assertEquals(pot.getColumn(), pot.getX());
		assertEquals(pot.getRow(), pot.getY());

		assertEquals(squid.getColumn(), squid.getX());
		assertEquals(squid.getRow(), squid.getY());
	}

	/**
	 * Test 14: Enemies can be positioned anywhere
	 */
	@Test
	public void testEnemiesCanBeRepositioned() {
		troll.setPosition(100, 200);
		assertEquals(100, troll.getRow(), "Troll debe estar en fila 100");
		assertEquals(200, troll.getColumn(), "Troll debe estar en columna 200");

		pot.setPosition(50, 75);
		assertEquals(50, pot.getRow(), "Pot debe estar en fila 50");
		assertEquals(75, pot.getColumn(), "Pot debe estar en columna 75");
	}

	/**
	 * Test 15: Different enemy types have different type strings
	 */
	@Test
	public void testEnemyTypeDifferences() {
		assertNotEquals(troll.getType(), pot.getType(), "Troll y Pot deben tener tipos diferentes");
		assertNotEquals(pot.getType(), squid.getType(), "Pot y Squid deben tener tipos diferentes");
		assertNotEquals(troll.getType(), squid.getType(), "Troll y Squid deben tener tipos diferentes");
	}

	/**
	 * Test 16: Enemies can update (no exceptions thrown)
	 */
	@Test
	public void testEnemiesCanUpdate() {
		assertDoesNotThrow(() -> {
			troll.update(game);
			pot.update(game);
			squid.update(game);
		}, "Los enemigos deben poder actualizarse sin lanzar excepciones");
	}

	/**
	 * Test 17: Multiple direction reversals
	 */
	@Test
	public void testMultipleDirectionReversals() {
		int originalDirRow = troll.getDirectionRow();
		int originalDirCol = troll.getDirectionColumn();

		troll.reverseDirection();
		troll.reverseDirection();

		assertEquals(originalDirRow, troll.getDirectionRow(), "Doble reversión debe retornar a dirección original");
		assertEquals(originalDirCol, troll.getDirectionColumn(), "Doble reversión debe retornar a dirección original");
	}

	/**
	 * Test 18: Enemy collision detection helper
	 */
	@Test
	public void testEnemyCollisionDetection() {
		Player player = new Player(5, 5);

		// Enemigo en misma posición
		assertTrue(troll.getRow() == player.getRow() && troll.getColumn() == player.getColumn(),
				"Troll debe estar en misma posición que el jugador");

		// Mover enemigo
		troll.moveTo(10, 10);

		assertFalse(troll.getRow() == player.getRow() && troll.getColumn() == player.getColumn(),
				"Troll no debe estar en misma posición después de moverse");
	}

	/**
	 * Test 19: Enemies start with default direction
	 */
	@Test
	public void testEnemiesHaveDefaultDirection() {
		Troll newTroll = new Troll(0, 0);
		Pot newPot = new Pot(0, 0);
		OrangeSquid newSquid = new OrangeSquid(0, 0);

		// Todos deben tener alguna dirección inicial
		assertTrue(newTroll.getDirectionRow() != 0 || newTroll.getDirectionColumn() != 0,
				"Troll debe tener dirección inicial");
		assertTrue(newPot.getDirectionRow() != 0 || newPot.getDirectionColumn() != 0,
				"Pot debe tener dirección inicial");
		assertTrue(newSquid.getDirectionRow() != 0 || newSquid.getDirectionColumn() != 0,
				"Squid debe tener dirección inicial");
	}

	/**
	 * Test 20: Enemies in game context
	 */
	@Test
	public void testEnemiesInGameContext() {
		assertNotNull(game.getEnemies(), "Game debe tener lista de enemigos");
		assertTrue(game.getEnemies().size() > 0, "Game debe tener al menos un enemigo en nivel 1");
	}
}