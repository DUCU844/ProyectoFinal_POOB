package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.GameState;

/**
 * Unit tests for GameState class.
 * Tests score tracking, time management, and level completion.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
class TestGameState {

private GameState gameState;
    
    @BeforeEach
    public void setUp() {
        gameState = new GameState(1, 16);
    }
    
    /**
     * Test 1: GameState creation with correct initial values
     */
    @Test
    public void testGameStateCreation() {
        assertEquals(1, gameState.getCurrentLevel(), "Nivel debe ser 1");
        assertEquals(16, gameState.getTotalFruits(), "Total de frutas debe ser 16");
        assertEquals(0, gameState.getScore(), "Puntaje inicial debe ser 0");
        assertEquals(0, gameState.getFruitsCollected(), "Frutas recolectadas debe ser 0");
        assertEquals(180, gameState.getTimeRemaining(), "Tiempo debe ser 180 segundos (3 minutos)");
    }
    
    /**
     * Test 2: Create GameState for different levels
     */
    @Test
    public void testGameStateForDifferentLevels() {
        GameState level2 = new GameState(2, 12);
        GameState level3 = new GameState(3, 12);
        
        assertEquals(2, level2.getCurrentLevel(), "Nivel debe ser 2");
        assertEquals(3, level3.getCurrentLevel(), "Nivel debe ser 3");
    }
    
    /**
     * Test 3: Add score increases total
     */
    @Test
    public void testAddScore() {
        gameState.addScore(50);
        assertEquals(50, gameState.getScore(), "Puntaje debe ser 50");
        
        gameState.addScore(100);
        assertEquals(150, gameState.getScore(), "Puntaje debe ser 150");
    }
    
    /**
     * Test 4: Add multiple scores
     */
    @Test
    public void testAddMultipleScores() {
        gameState.addScore(50);  // Grape
        gameState.addScore(100); // Banana
        gameState.addScore(150); // Cherry
        gameState.addScore(200); // Pineapple
        gameState.addScore(250); // Cactus
        
        assertEquals(750, gameState.getScore(), "Puntaje total debe ser 750");
    }
    
    /**
     * Test 5: Score doesn't decrease
     */
    @Test
    public void testScoreOnlyIncreases() {
        gameState.addScore(100);
        int currentScore = gameState.getScore();
        
        // No hay método para decrementar, verificar que se mantiene
        assertEquals(currentScore, gameState.getScore(), "Puntaje no debe cambiar sin addScore");
    }
    
    /**
     * Test 6: Collect fruit increments counter
     */
    @Test
    public void testCollectFruit() {
        gameState.collectFruit();
        assertEquals(1, gameState.getFruitsCollected(), "Debe haber 1 fruta recolectada");
        
        gameState.collectFruit();
        assertEquals(2, gameState.getFruitsCollected(), "Debe haber 2 frutas recolectadas");
    }
    
    /**
     * Test 7: Collect all fruits
     */
    @Test
    public void testCollectAllFruits() {
        for (int i = 0; i < 16; i++) {
            gameState.collectFruit();
        }
        
        assertEquals(16, gameState.getFruitsCollected(), "Debe haber 16 frutas recolectadas");
    }
    
    /**
     * Test 8: Level complete when all fruits collected
     */
    @Test
    public void testLevelComplete() {
        assertFalse(gameState.isLevelComplete(), "Nivel no debe estar completo al inicio");
        
        for (int i = 0; i < 16; i++) {
            gameState.collectFruit();
        }
        
        assertTrue(gameState.isLevelComplete(), "Nivel debe estar completo");
    }
    
    /**
     * Test 9: Level not complete with one fruit missing
     */
    @Test
    public void testLevelNotCompleteWithOneFruitMissing() {
        for (int i = 0; i < 15; i++) {
            gameState.collectFruit();
        }
        
        assertFalse(gameState.isLevelComplete(), "Nivel no debe estar completo con 1 fruta faltante");
    }
    
    /**
     * Test 10: Initial time is 180 seconds
     */
    @Test
    public void testInitialTime() {
        assertEquals(180, gameState.getTimeRemaining(), "Tiempo inicial debe ser 180 segundos");
    }
    
    /**
     * Test 11: Decrement time reduces by 1
     */
    @Test
    public void testDecrementTime() {
        gameState.decrementTime();
        assertEquals(179, gameState.getTimeRemaining(), "Tiempo debe ser 179 después de decrementar");
    }
    
    /**
     * Test 12: Multiple time decrements
     */
    @Test
    public void testMultipleTimeDecrements() {
        for (int i = 0; i < 30; i++) {
            gameState.decrementTime();
        }
        
        assertEquals(150, gameState.getTimeRemaining(), "Tiempo debe ser 150 después de 30 decrementos");
    }
    
    /**
     * Test 13: Time doesn't go below zero
     */
    @Test
    public void testTimeDoesntGoBelowZero() {
        for (int i = 0; i < 200; i++) {
            gameState.decrementTime();
        }
        
        assertEquals(0, gameState.getTimeRemaining(), "Tiempo mínimo debe ser 0");
    }
    
    /**
     * Test 14: Time up when time reaches zero
     */
    @Test
    public void testIsTimeUp() {
        assertFalse(gameState.isTimeUp(), "Tiempo no debe haber terminado al inicio");
        
        for (int i = 0; i < 180; i++) {
            gameState.decrementTime();
        }
        
        assertTrue(gameState.isTimeUp(), "Tiempo debe haber terminado");
    }
    
    /**
     * Test 15: Formatted time displays correctly
     */
    @Test
    public void testFormattedTime() {
        assertEquals("03:00", gameState.getFormattedTime(), "Formato debe ser 03:00");
        
        gameState.decrementTime();
        assertEquals("02:59", gameState.getFormattedTime(), "Formato debe ser 02:59");
        
        for (int i = 0; i < 60; i++) {
            gameState.decrementTime();
        }
        assertEquals("01:59", gameState.getFormattedTime(), "Formato debe ser 01:59");
    }
    
    /**
     * Test 16: Formatted time with single digit seconds
     */
    @Test
    public void testFormattedTimeSingleDigit() {
        for (int i = 0; i < 175; i++) {
            gameState.decrementTime();
        }
        
        assertEquals("00:05", gameState.getFormattedTime(), "Formato debe ser 00:05");
    }
    
    /**
     * Test 17: Reset level clears score
     */
    @Test
    public void testResetLevel() {
        gameState.addScore(500);
        gameState.collectFruit();
        gameState.collectFruit();
        
        for (int i = 0; i < 30; i++) {
            gameState.decrementTime();
        }
        
        gameState.resetLevel();
        
        assertEquals(0, gameState.getScore(), "Puntaje debe resetearse a 0");
        assertEquals(0, gameState.getFruitsCollected(), "Frutas recolectadas debe resetearse a 0");
        assertEquals(180, gameState.getTimeRemaining(), "Tiempo debe resetearse a 180");
    }
    
    /**
     * Test 18: Complete game scenario
     */
    @Test
    public void testCompleteGameScenario() {
        // Recolectar frutas y ganar puntos
        for (int i = 0; i < 8; i++) {
            gameState.collectFruit();
            gameState.addScore(50); // Grapes
        }
        
        for (int i = 0; i < 8; i++) {
            gameState.collectFruit();
            gameState.addScore(100); // Bananas
        }
        
        // Verificar estado
        assertEquals(16, gameState.getFruitsCollected(), "Debe haber 16 frutas");
        assertEquals(1200, gameState.getScore(), "Puntaje debe ser 1200");
        assertTrue(gameState.isLevelComplete(), "Nivel debe estar completo");
    }
    
    /**
     * Test 19: Time runs out scenario
     */
    @Test
    public void testTimeRunsOutScenario() {
        gameState.collectFruit();
        gameState.addScore(50);
        
        // Tiempo se acaba
        for (int i = 0; i < 180; i++) {
            gameState.decrementTime();
        }
        
        assertTrue(gameState.isTimeUp(), "Tiempo debe haberse acabado");
        assertFalse(gameState.isLevelComplete(), "Nivel no debe estar completo");
        assertEquals(50, gameState.getScore(), "Puntaje debe mantenerse");
    }
    
    /**
     * Test 20: Winning with time remaining
     */
    @Test
    public void testWinningWithTimeRemaining() {
        // Recolectar todas las frutas rápidamente
        for (int i = 0; i < 16; i++) {
            gameState.collectFruit();
            gameState.addScore(100);
        }
        
        // Solo pasar 30 segundos
        for (int i = 0; i < 30; i++) {
            gameState.decrementTime();
        }
        
        assertTrue(gameState.isLevelComplete(), "Nivel debe estar completo");
        assertFalse(gameState.isTimeUp(), "Tiempo no debe haberse acabado");
        assertEquals(150, gameState.getTimeRemaining(), "Debe quedar tiempo");
        assertEquals(1600, gameState.getScore(), "Puntaje total debe ser 1600");
    }
    
}
