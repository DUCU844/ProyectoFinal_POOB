package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.Player;

/**
 * Unit tests for Player class. Tests player creation, movement, direction
 * tracking, and score management.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
class TestPlayer {

	private Player player1;
	private Player player2;

	/**
	 * Setup method that runs before each test. Creates fresh player instances.
	 */
	@BeforeEach
	public void setUp() {
		player1 = new Player(5, 5);
		player2 = new Player(10, 10, "Fresa", 2);
	}

	/**
	 * Test 1: Verify player is created in correct position
	 */
	@Test
	public void testPlayerCreation() {
		assertEquals(5, player1.getRow(), "El jugador debe estar en la fila 5");
		assertEquals(5, player1.getColumn(), "El jugador debe estar en la columna 5");
		assertEquals("Vainilla", player1.getFlavor(), "El sabor por defecto debe ser Vainilla");
		assertEquals(1, player1.getPlayerNumber(), "El número de jugador por defecto debe ser 1");
	}

	/**
	 * Test 2: Verify player with custom flavor and number
	 */
	@Test
	public void testPlayerCreationWithFlavor() {
		assertEquals(10, player2.getRow(), "El jugador 2 debe estar en la fila 10");
		assertEquals(10, player2.getColumn(), "El jugador 2 debe estar en la columna 10");
		assertEquals("Fresa", player2.getFlavor(), "El sabor debe ser Fresa");
		assertEquals(2, player2.getPlayerNumber(), "El número de jugador debe ser 2");
	}

	/**
	 * Test 3: Test moving up
	 */
	@Test
	public void testMoveUp() {
		int originalRow = player1.getRow();
		player1.moveUp();
		assertEquals(originalRow - 1, player1.getRow(), "Mover arriba debe decrementar la fila");
		assertEquals(0, player1.getLastDirectionX(), "Dirección X debe ser 0 al moverse arriba");
		assertEquals(-1, player1.getLastDirectionY(), "Dirección Y debe ser -1 al moverse arriba");
	}

	/**
	 * Test 4: Test moving down
	 */
	@Test
	public void testMoveDown() {
		int originalRow = player1.getRow();
		player1.moveDown();
		assertEquals(originalRow + 1, player1.getRow(), "Mover abajo debe incrementar la fila");
		assertEquals(0, player1.getLastDirectionX(), "Dirección X debe ser 0 al moverse abajo");
		assertEquals(1, player1.getLastDirectionY(), "Dirección Y debe ser 1 al moverse abajo");
	}

	/**
	 * Test 5: Test moving left
	 */
	@Test
	public void testMoveLeft() {
		int originalCol = player1.getColumn();
		player1.moveLeft();
		assertEquals(originalCol - 1, player1.getColumn(), "Mover izquierda debe decrementar la columna");
		assertEquals(-1, player1.getLastDirectionX(), "Dirección X debe ser -1 al moverse a la izquierda");
		assertEquals(0, player1.getLastDirectionY(), "Dirección Y debe ser 0 al moverse a la izquierda");
	}

	/**
	 * Test 6: Test moving right
	 */
	@Test
	public void testMoveRight() {
		int originalCol = player1.getColumn();
		player1.moveRight();
		assertEquals(originalCol + 1, player1.getColumn(), "Mover derecha debe incrementar la columna");
		assertEquals(1, player1.getLastDirectionX(), "Dirección X debe ser 1 al moverse a la derecha");
		assertEquals(0, player1.getLastDirectionY(), "Dirección Y debe ser 0 al moverse a la derecha");
	}

	/**
	 * Test 7: Test multiple moves
	 */
	@Test
	public void testMultipleMoves() {
		player1.moveRight();
		player1.moveRight();
		player1.moveDown();

		assertEquals(7, player1.getColumn(), "Después de 2 movimientos a la derecha, columna debe ser 7");
		assertEquals(6, player1.getRow(), "Después de 1 movimiento abajo, fila debe ser 6");
	}

	/**
	 * Test 8: Test moveTo method
	 */
	@Test
	public void testMoveTo() {
		player1.moveTo(15, 20);
		assertEquals(15, player1.getRow(), "La fila debe ser 15 después de moveTo");
		assertEquals(20, player1.getColumn(), "La columna debe ser 20 después de moveTo");
	}

	/**
	 * Test 9: Test score start in zero
	 */
	@Test
	public void testInitialScore() {
		assertEquals(0, player1.getScore(), "El puntaje inicial debe ser 0");
	}

	/**
	 * Test 10: Test add score
	 */
	@Test
	public void testAddScore() {
		player1.addScore(50);
		assertEquals(50, player1.getScore(), "El puntaje debe ser 50 después de agregar 50");

		player1.addScore(100);
		assertEquals(150, player1.getScore(), "El puntaje debe ser 150 después de agregar 50 y 100");
	}

	/**
	 * Test 11: Test add multiple scores
	 */
	@Test
	public void testMultipleScoreAdditions() {
		player1.addScore(50); // Grape
		player1.addScore(100); // Banana
		player1.addScore(150); // Cherry
		player1.addScore(200); // Pineapple
		player1.addScore(250); // Cactus

		assertEquals(750, player1.getScore(), "El puntaje total debe ser 750");
	}

	/**
	 * Test 12: Test updateDirection
	 */
	@Test
	public void testUpdateDirection() {
		player1.updateDirection(1, 0);
		assertEquals(1, player1.getLastDirectionX(), "Dirección X debe actualizarse a 1");
		assertEquals(0, player1.getLastDirectionY(), "Dirección Y debe actualizarse a 0");
		assertEquals(5, player1.getRow(), "La posición no debe cambiar al actualizar dirección");
		assertEquals(5, player1.getColumn(), "La posición no debe cambiar al actualizar dirección");
	}

	/**
	 * Test 13: Test direction tracking after movement
	 */
	@Test
	public void testDirectionTracking() {
		player1.moveLeft();
		assertEquals(-1, player1.getLastDirectionX(), "Última dirección X debe ser -1");

		player1.moveUp();
		assertEquals(-1, player1.getLastDirectionY(), "Última dirección Y debe ser -1");
		assertEquals(0, player1.getLastDirectionX(), "Última dirección X debe cambiar a 0");
	}

	/**
	 * Test 14: Test setFlavor method
	 */
	@Test
	public void testSetFlavor() {
		player1.setFlavor("Chocolate");
		assertEquals("Chocolate", player1.getFlavor(), "El sabor debe cambiar a Chocolate");
	}

	/**
	 * Test 15: Test different player flavor
	 */
	@Test
	public void testDifferentFlavors() {
		Player vanilla = new Player(0, 0, "Vainilla", 1);
		Player strawberry = new Player(0, 0, "Fresa", 2);
		Player chocolate = new Player(0, 0, "Chocolate", 3);

		assertEquals("Vainilla", vanilla.getFlavor());
		assertEquals("Fresa", strawberry.getFlavor());
		assertEquals("Chocolate", chocolate.getFlavor());
	}
}
