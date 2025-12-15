package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.*;

/**
 * Unit tests for Fruit classes. Tests all fruit types.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
class TestFruit {

	private Grape grape;
    private Banana banana;
    private Cherry cherry;
    private Pineapple pineapple;
    private Cactus cactus;
    
    @BeforeEach
    public void setUp() {
        grape = new Grape(5, 5);
        banana = new Banana(10, 10);
        cherry = new Cherry(15, 15);
        pineapple = new Pineapple(3, 3);
        cactus = new Cactus(8, 8);
    }
    
    /**
     * Test 1: Grape creation with initial values
     */
    @Test
    public void testGrapeCreation() {
        assertEquals(5, grape.getRow(), "Grape debe estar en fila 5");
        assertEquals(5, grape.getColumn(), "Grape debe estar en columna 5");
        assertEquals(50, grape.getPoints(), "Grape debe valer 50 puntos");
        assertEquals("GRAPE", grape.getType(), "El tipo debe ser GRAPE");
        assertFalse(grape.isCollected(), "Grape no debe estar recolectada inicialmente");
    }
    
    /**
     * Test 2: Grape collection
     */
    @Test
    public void testGrapeCollection() {
        grape.collect();
        assertTrue(grape.isCollected(), "Grape debe estar recolectada después de collect()");
    }
    
    
    /**
     * Test 3: Banana creation and initial values
     */
    @Test
    public void testBananaCreation() {
        assertEquals(10, banana.getRow(), "Banana debe estar en fila 10");
        assertEquals(10, banana.getColumn(), "Banana debe estar en columna 10");
        assertEquals(100, banana.getPoints(), "Banana debe valer 100 puntos");
        assertEquals("BANANA", banana.getType(), "El tipo debe ser BANANA");
        assertFalse(banana.isCollected(), "Banana no debe estar recolectada inicialmente");
    }
    
    /**
     * Test 4: Banana collection
     */
    @Test
    public void testBananaCollection() {
        banana.collect();
        assertTrue(banana.isCollected(), "Banana debe estar recolectada después de collect()");
    }
    
    /**
     * Test 5: Cherry creation with initial values
     */
    @Test
    public void testCherryCreation() {
        assertEquals(15, cherry.getRow(), "Cherry debe estar en fila 15");
        assertEquals(15, cherry.getColumn(), "Cherry debe estar en columna 15");
        assertEquals(150, cherry.getPoints(), "Cherry debe valer 150 puntos");
        assertEquals("Cherry", cherry.getType(), "El tipo debe ser Cherry");
        assertFalse(cherry.isCollected(), "Cherry no debe estar recolectada inicialmente");
    }
    
    /**
     * Test 6: Cherry teleport behavior
     */
    @Test
    public void testCherryShouldTeleport() {
        // Inicialmente no debe teletransportarse
        assertFalse(cherry.shouldTeleport(), "Cherry no debe teletransportarse al inicio");
        
        // Después de muchos updates, debería querer teletransportarse
        for (int i = 0; i < 50; i++) {
            cherry.update();
        }
    }
    
    /**
     * Test 7: Cherry teleportTo method
     */
    @Test
    public void testCherryTeleportTo() {
        cherry.teleportTo(20, 25);
        
        assertEquals(20, cherry.getRow(), "Cherry debe estar en nueva fila 20");
        assertEquals(25, cherry.getColumn(), "Cherry debe estar en nueva columna 25");
        assertFalse(cherry.shouldTeleport(), "El temporizador debe reiniciarse después de teletransportarse");
    }
    
    /**
     * Test 8: Pineapple creation with initial values
     */
    @Test
    public void testPineappleCreation() {
        assertEquals(3, pineapple.getRow(), "Pineapple debe estar en fila 3");
        assertEquals(3, pineapple.getColumn(), "Pineapple debe estar en columna 3");
        assertEquals(200, pineapple.getPoints(), "Pineapple debe valer 200 puntos");
        assertEquals("Pineapple", pineapple.getType(), "El tipo debe ser Pineapple");
        assertFalse(pineapple.isCollected(), "Pineapple no debe estar recolectada inicialmente");
    }
    
    /**
     * Test 9: Pineapple initial direction
     */
    @Test
    public void testPineappleInitialDirection() {
        assertEquals(0, pineapple.getDirectionRow(), "Dirección inicial en fila debe ser 0");
        assertEquals(1, pineapple.getDirectionColumn(), "Dirección inicial en columna debe ser 1 (derecha)");
    }
    
    /**
     * Test 10: Pineapple movement
     */
    @Test
    public void testPineappleMovement() {
        boolean moved = pineapple.tryMove(3, 4);
        
        assertTrue(moved, "Pineapple debe poder moverse");
        assertEquals(3, pineapple.getRow(), "Pineapple debe estar en nueva fila");
        assertEquals(4, pineapple.getColumn(), "Pineapple debe estar en nueva columna");
    }
    
    /**
     * Test 11: Pineapple reverse direction
     */
    @Test
    public void testPineappleReverseDirection() {
        int originalDirRow = pineapple.getDirectionRow();
        int originalDirCol = pineapple.getDirectionColumn();
        
        pineapple.reverseDirection();
        
        assertEquals(-originalDirRow, pineapple.getDirectionRow(), "Dirección de fila debe invertirse");
        assertEquals(-originalDirCol, pineapple.getDirectionColumn(), "Dirección de columna debe invertirse");
    }
    
    /**
     * Test 12: Cactus creation with initial values
     */
    @Test
    public void testCactusCreation() {
        assertEquals(8, cactus.getRow(), "Cactus debe estar en fila 8");
        assertEquals(8, cactus.getColumn(), "Cactus debe estar en columna 8");
        assertEquals(250, cactus.getPoints(), "Cactus debe valer 250 puntos");
        assertEquals("Cactus", cactus.getType(), "El tipo debe ser Cactus");
        assertFalse(cactus.isCollected(), "Cactus no debe estar recolectado inicialmente");
    }
    
    /**
     * Test 13: Cactus starts without spikes
     */
    @Test
    public void testCactusInitialSpikes() {
        assertFalse(cactus.hasSpikes(), "Cactus debe empezar sin púas");
    }
    
    /**
     * Test 14: Cactus spike cycle
     */
    @Test
    public void testCactusSpikesCycle() {
        assertFalse(cactus.hasSpikes(), "Cactus debe empezar sin púas");
        
        // Simular 300 ticks (30 segundos)
        for (int i = 0; i < 300; i++) {
            cactus.update();
        }
        
        assertTrue(cactus.hasSpikes(), "Cactus debe tener púas después de 30 segundos");
        
        // Otros 300 ticks
        for (int i = 0; i < 300; i++) {
            cactus.update();
        }
        
        assertFalse(cactus.hasSpikes(), "Cactus no debe tener púas después de otros 30 segundos");
    }
    
    /**
     * Test 15: Cactus timer
     */
    @Test
    public void testCactusTimerCountdown() {
        int initialSeconds = cactus.getSecondsUntilChange();
        
        // Hacer algunos updates
        for (int i = 0; i < 50; i++) {
            cactus.update();
        }
        
        int newSeconds = cactus.getSecondsUntilChange();
        
        assertTrue(newSeconds < initialSeconds, "El tiempo hasta cambio debe disminuir");
    }
    
    /**
     * Test 16: Cactus does not change when collected
     */
    @Test
    public void testCactusNoChangeWhenCollected() {
        cactus.collect();
        
        boolean initialSpikes = cactus.hasSpikes();
        
        for (int i = 0; i < 300; i++) {
            cactus.update();
        }
        
        assertEquals(initialSpikes, cactus.hasSpikes(), "Cactus recolectado no debe cambiar estado de púas");
    }
    
    /**
     * Test 17: All fruits have correct point values
     */
    @Test
    public void testAllFruitPoints() {
        assertEquals(50, grape.getPoints(), "Grape debe valer 50");
        assertEquals(100, banana.getPoints(), "Banana debe valer 100");
        assertEquals(150, cherry.getPoints(), "Cherry debe valer 150");
        assertEquals(200, pineapple.getPoints(), "Pineapple debe valer 200");
        assertEquals(250, cactus.getPoints(), "Cactus debe valer 250");
    }
    
    /**
     * Test 18: Collection works for all fruit types
     */
    @Test
    public void testAllFruitsCanBeCollected() {
        grape.collect();
        banana.collect();
        cherry.collect();
        pineapple.collect();
        cactus.collect();
        
        assertTrue(grape.isCollected(), "Grape debe poder ser recolectada");
        assertTrue(banana.isCollected(), "Banana debe poder ser recolectada");
        assertTrue(cherry.isCollected(), "Cherry debe poder ser recolectada");
        assertTrue(pineapple.isCollected(), "Pineapple debe poder ser recolectada");
        assertTrue(cactus.isCollected(), "Cactus debe poder ser recolectado");
    }

}
