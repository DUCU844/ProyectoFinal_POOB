package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.*;

/**
 * Unit tests for Obstacle classes. Tests Firepit, HotTile, Igloo, and Ice
 * obstacles.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
class TestObstacle {
	
	private Firepit firepit;
    private HotTile hotTile;
    private Igloo igloo;
    private Ice ice;
    
    @BeforeEach
    public void setUp() {
        firepit = new Firepit(5, 5);
        hotTile = new HotTile(10, 10);
        igloo = new Igloo(3, 3);
        ice = new Ice(8, 8);
    }
    
    /**
     * Test 1: Firepit creation and initial state
     */
    @Test
    public void testFirepitCreation() {
        assertEquals(5, firepit.getRow(), "Firepit debe estar en fila 5");
        assertEquals(5, firepit.getColumn(), "Firepit debe estar en columna 5");
        assertTrue(firepit.isLit(), "Firepit debe estar encendida inicialmente");
        assertEquals("Firepit", firepit.getType(), "El tipo debe ser Firepit");
    }
    
    /**
     * Test 2: Firepit can be extinguished
     */
    @Test
    public void testFirepitExtinguish() {
        firepit.extinguish();
        assertFalse(firepit.isLit(), "Firepit debe estar apagada después de extinguish()");
    }
    
    /**
     * Test 3: Firepit relight timer
     */
    @Test
    public void testFirepitRelightTimer() {
        firepit.extinguish();
        assertFalse(firepit.isLit(), "Firepit debe estar apagada");
        
        // Simular 100 ticks (10 segundos)
        for (int i = 0; i < 100; i++) {
            firepit.update();
        }
        
        assertTrue(firepit.isLit(), "Firepit debe volver a encenderse después de 10 segundos");
    }
    
    /**
     * Test 4: Firepit timer countdown
     */
    @Test
    public void testFirepitTimerCountdown() {
        firepit.extinguish();
        assertEquals(0, firepit.getExtinguishTimer(), "Timer debe empezar en 0");
        
        firepit.update();
        assertEquals(1, firepit.getExtinguishTimer(), "Timer debe incrementar con update()");
        
        for (int i = 0; i < 49; i++) {
            firepit.update();
        }
        
        assertEquals(50, firepit.getExtinguishTimer(), "Timer debe ser 50 después de 50 updates");
    }
    
    /**
     * Test 5: Firepit doesn't update timer when lit
     */
    @Test
    public void testFirepitNoTimerWhenLit() {
        assertTrue(firepit.isLit(), "Firepit debe estar encendida");
        
        firepit.update();
        firepit.update();
        
        assertEquals(0, firepit.getExtinguishTimer(), "Timer no debe cambiar si está encendida");
    }
    
    /**
     * Test 6: Multiple extinguish calls
     */
    @Test
    public void testMultipleExtinguish() {
        firepit.extinguish();
        firepit.extinguish();
        firepit.extinguish();
        
        assertFalse(firepit.isLit(), "Firepit debe permanecer apagada");
        assertEquals(0, firepit.getExtinguishTimer(), "Timer debe reiniciarse");
    }
    
    /**
     * Test 7: HotTile creation and position
     */
    @Test
    public void testHotTileCreation() {
        assertEquals(10, hotTile.getRow(), "HotTile debe estar en fila 10");
        assertEquals(10, hotTile.getColumn(), "HotTile debe estar en columna 10");
        assertEquals("HotTile", hotTile.getType(), "El tipo debe ser HotTile");
    }
    
    /**
     * Test 8: HotTile is passive (update does nothing)
     */
    @Test
    public void testHotTileIsPassive() {
        assertDoesNotThrow(() -> {
            hotTile.update();
            hotTile.update();
            hotTile.update();
        }, "HotTile update no debe lanzar excepciones");
    }
    
    /**
     * Test 9: HotTile position doesn't change
     */
    @Test
    public void testHotTilePositionStatic() {
        int row = hotTile.getRow();
        int col = hotTile.getColumn();
        
        hotTile.update();
        
        assertEquals(row, hotTile.getRow(), "HotTile no debe cambiar de posición");
        assertEquals(col, hotTile.getColumn(), "HotTile no debe cambiar de posición");
    }
    
    /**
     * Test 10: Igloo creation with default size
     */
    @Test
    public void testIglooCreation() {
        assertEquals(3, igloo.getRow(), "Igloo debe estar en fila 3");
        assertEquals(3, igloo.getColumn(), "Igloo debe estar en columna 3");
        assertEquals(2, igloo.getWidth(), "Igloo por defecto debe tener ancho 2");
        assertEquals(2, igloo.getHeight(), "Igloo por defecto debe tener alto 2");
        assertEquals("Igloo", igloo.getType(), "El tipo debe ser Igloo");
    }
    
    /**
     * Test 11: Igloo creation with custom size
     */
    @Test
    public void testIglooCreationCustomSize() {
        Igloo bigIgloo = new Igloo(0, 0, 3, 4);
        
        assertEquals(3, bigIgloo.getWidth(), "Igloo debe tener ancho 3");
        assertEquals(4, bigIgloo.getHeight(), "Igloo debe tener alto 4");
    }
    
    /**
     * Test 12: Igloo occupies position correctly
     */
    @Test
    public void testIglooOccupiesPosition() {
        // Igloo en (3,3) con tamaño 2x2 ocupa: (3,3), (3,4), (4,3), (4,4)
        assertTrue(igloo.occupiesPosition(3, 3), "Igloo debe ocupar (3,3)");
        assertTrue(igloo.occupiesPosition(3, 4), "Igloo debe ocupar (3,4)");
        assertTrue(igloo.occupiesPosition(4, 3), "Igloo debe ocupar (4,3)");
        assertTrue(igloo.occupiesPosition(4, 4), "Igloo debe ocupar (4,4)");
        
        assertFalse(igloo.occupiesPosition(2, 2), "Igloo no debe ocupar (2,2)");
        assertFalse(igloo.occupiesPosition(5, 5), "Igloo no debe ocupar (5,5)");
    }
    
    /**
     * Test 13: Igloo boundary checking
     */
    @Test
    public void testIglooBoundaries() {
        Igloo smallIgloo = new Igloo(10, 10, 1, 1);
        
        assertTrue(smallIgloo.occupiesPosition(10, 10), "Debe ocupar posición inicial");
        assertFalse(smallIgloo.occupiesPosition(11, 10), "No debe ocupar fuera del límite");
        assertFalse(smallIgloo.occupiesPosition(10, 11), "No debe ocupar fuera del límite");
    }
    
    /**
     * Test 14: Large igloo occupancy
     */
    @Test
    public void testLargeIglooOccupancy() {
        Igloo largeIgloo = new Igloo(0, 0, 5, 5);
        
        assertTrue(largeIgloo.occupiesPosition(0, 0), "Debe ocupar esquina superior izquierda");
        assertTrue(largeIgloo.occupiesPosition(4, 4), "Debe ocupar esquina inferior derecha");
        assertTrue(largeIgloo.occupiesPosition(2, 2), "Debe ocupar centro");
        
        assertFalse(largeIgloo.occupiesPosition(5, 5), "No debe ocupar fuera del límite");
    }
    
    /**
     * Test 15: Ice creation and position
     */
    @Test
    public void testIceCreation() {
        assertEquals(8, ice.getRow(), "Ice debe estar en fila 8");
        assertEquals(8, ice.getColumn(), "Ice debe estar en columna 8");
    }
    
    /**
     * Test 16: Ice blocks are simple objects
     */
    @Test
    public void testIceSimplicity() {
        assertNotNull(ice, "Ice no debe ser null");
        assertTrue(ice instanceof Ice, "Debe ser instancia de Ice");
    }
    
    /**
     * Test 17: Multiple ice blocks can exist
     */
    @Test
    public void testMultipleIceBlocks() {
        Ice ice1 = new Ice(0, 0);
        Ice ice2 = new Ice(1, 1);
        Ice ice3 = new Ice(2, 2);
        
        assertNotEquals(ice1, ice2, "Bloques de hielo deben ser objetos diferentes");
        assertNotEquals(ice2, ice3, "Bloques de hielo deben ser objetos diferentes");
        
        assertEquals(0, ice1.getRow());
        assertEquals(1, ice2.getRow());
        assertEquals(2, ice3.getRow());
    }
    
    /**
     * Test 18: Obstacles don't interfere with each other
     */
    @Test
    public void testObstaclesIndependent() {
        Firepit f1 = new Firepit(0, 0);
        Firepit f2 = new Firepit(1, 1);
        
        f1.extinguish();
        
        assertFalse(f1.isLit(), "Primera fogata debe estar apagada");
        assertTrue(f2.isLit(), "Segunda fogata debe seguir encendida");
    }
    
    /**
     * Test 19: Different obstacle types can coexist
     */
    @Test
    public void testDifferentObstacleTypes() {
        assertNotNull(firepit, "Firepit debe existir");
        assertNotNull(hotTile, "HotTile debe existir");
        assertNotNull(igloo, "Igloo debe existir");
        assertNotNull(ice, "Ice debe existir");
        
        // Todos deben tener posiciones válidas
        assertTrue(firepit.getRow() >= 0 && firepit.getColumn() >= 0);
        assertTrue(hotTile.getRow() >= 0 && hotTile.getColumn() >= 0);
        assertTrue(igloo.getRow() >= 0 && igloo.getColumn() >= 0);
        assertTrue(ice.getRow() >= 0 && ice.getColumn() >= 0);
    }
    
    /**
     * Test 20: Obstacles have unique type identifiers
     */
    @Test
    public void testObstacleTypeIdentifiers() {
        assertEquals("Firepit", firepit.getType());
        assertEquals("HotTile", hotTile.getType());
        assertEquals("Igloo", igloo.getType());
        
        assertNotEquals(firepit.getType(), hotTile.getType());
        assertNotEquals(hotTile.getType(), igloo.getType());
        assertNotEquals(firepit.getType(), igloo.getType());
    }

}
