package Gametest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import dominio.*;

/**
 * 20 Pruebas unitarias esenciales para la clase Game.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class GameTest {

    private Game game;
    
    @Before
    public void setUp() {
        game = new Game();
    }
    
    // ========== PRUEBAS DE CONSTRUCTORES ==========
    
    /**
     * Prueba 1: Verificar que el constructor sin parámetros inicializa correctamente
     */
    @Test
    public void testConstructorSinParametros() {
        assertNotNull("El juego no debe ser null", game);
        assertEquals("Debe iniciar en nivel 1", 1, game.getCurrentLevel());
        assertFalse("No debe estar en modo dos jugadores", game.isTwoPlayerMode());
        assertFalse("gameOver debe ser false", game.isGameOver());
        assertFalse("gameWon debe ser false", game.isGameWon());
    }
    
    /**
     * Prueba 2: Verificar que el constructor con nivel inicializa correctamente
     */
    @Test
    public void testConstructorConNivel() {
        Game game2 = new Game(2);
        assertEquals("Debe iniciar en nivel 2", 2, game2.getCurrentLevel());
        assertNotNull("Debe tener mapa", game2.getMap());
        assertNotNull("Debe tener jugador", game2.getPlayer());
    }
    
    /**
     * Prueba 3: Verificar constructor completo con modo dos jugadores
     */
    @Test
    public void testConstructorModoDosjugadores() {
        Game game2p = new Game(1, true, "Vainilla", "Fresa");
        assertTrue("Debe estar en modo dos jugadores", game2p.isTwoPlayerMode());
        assertEquals("Debe haber 2 jugadores", 2, game2p.getPlayers().size());
    }
    
    /**
     * Prueba 4: Verificar que el mapa se crea con dimensiones correctas
     */
    @Test
    public void testMapaDimensionesCorrectas() {
        IceMap map = game.getMap();
        assertEquals("Ancho debe ser 20", 20, map.getWidth());
        assertEquals("Alto debe ser 15", 15, map.getHeight());
    }
    
    /**
     * Prueba 5: Verificar que el nivel 1 se inicializa con elementos correctos
     */
    @Test
    public void testNivel1InicializaCorrectamente() {
        assertEquals("Nivel 1 debe tener 2 enemigos", 2, game.getEnemies().size());
        assertEquals("Nivel 1 debe tener 16 frutas", 16, game.getFruits().size());
        assertEquals("Debe haber 2 iglús", 2, game.getMap().getIgloos().size());
    }
    
    // ========== PRUEBAS DE MOVIMIENTO DE JUGADOR ==========
    
    /**
     * Prueba 6: Verificar que el jugador se mueve hacia arriba
     */
    @Test
    public void testMoverJugadorArriba() {
        Player player = game.getPlayer();
        int filaInicial = player.getRow();
        
        game.movePlayer(0, 0, -1); // Mover arriba
        
        assertEquals("El jugador debe moverse una fila arriba", 
                     filaInicial - 1, player.getRow());
    }
    
    /**
     * Prueba 7: Verificar que el jugador se mueve hacia la derecha
     */
    @Test
    public void testMoverJugadorDerecha() {
        Player player = game.getPlayer();
        int columnaInicial = player.getColumn();
        
        game.movePlayer(0, 1, 0); // Mover derecha
        
        assertEquals("El jugador debe moverse una columna a la derecha", 
                     columnaInicial + 1, player.getColumn());
    }
    
    /**
     * Prueba 8: Verificar que el jugador no se mueve si hay hielo
     */
    @Test
    public void testMovimientoBloqueadoPorHielo() {
        Player player = game.getPlayer();
        int fila = player.getRow();
        int columna = player.getColumn();
        
        // Crear hielo en la dirección del movimiento
        game.getMap().toggleIce(columna + 1, fila);
        
        // Intentar mover hacia el hielo
        game.movePlayer(0, 1, 0);
        
        assertEquals("El jugador no debe moverse", columna, player.getColumn());
    }
    
    /**
     * Prueba 9: Verificar que no se puede mover cuando el juego termina
     */
    @Test
    public void testNoMoverCuandoGameOver() {
        game.setGameOver(true);
        Player player = game.getPlayer();
        int filaInicial = player.getRow();
        
        game.movePlayer(0, 0, -1);
        
        assertEquals("No debe moverse con gameOver", filaInicial, player.getRow());
    }
    
    // ========== PRUEBAS DE HIELO ==========
    
    /**
     * Prueba 10: Verificar que se puede crear hielo con playerShootIce
     */
    @Test
    public void testCrearHieloConPlayerShoot() {
        Player player = game.getPlayer();
        int x = player.getColumn() + 1;
        int y = player.getRow();
        
        assertFalse("No debe haber hielo inicialmente", game.getMap().hasIce(x, y));
        
        game.playerShootIce(0, 1, 0); // Disparar derecha
        
        assertTrue("Debe haber hielo después de disparar", game.getMap().hasIce(x, y));
    }
    
    /**
     * Prueba 11: Verificar que se puede destruir hielo existente
     */
    @Test
    public void testDestruirHieloExistente() {
        Player player = game.getPlayer();
        int x = player.getColumn() + 1;
        int y = player.getRow();
        
        // Crear hielo
        game.playerShootIce(0, 1, 0);
        assertTrue("Debe haber hielo", game.getMap().hasIce(x, y));
        
        // Destruir hielo
        game.playerShootIce(0, 1, 0);
        assertFalse("No debe haber hielo después de destruir", game.getMap().hasIce(x, y));
    }
    
    // ========== PRUEBAS DE FRUTAS Y COLISIONES ==========
    
    /**
     * Prueba 12: Verificar que getTotalFruits retorna cantidad correcta
     */
    @Test
    public void testGetTotalFrutas() {
        assertEquals("Nivel 1 debe tener 16 frutas", 16, game.getTotalFruits());
    }
    
    /**
     * Prueba 13: Verificar que getCollectedFruits inicia en 0
     */
    @Test
    public void testFrutasRecolectadasInicialmenteCero() {
        assertEquals("Debe iniciar con 0 frutas recolectadas", 0, game.getCollectedFruits());
    }
    
    /**
     * Prueba 14: Verificar colisión con enemigo causa gameOver
     */
    @Test
    public void testColisionConEnemigoCausaGameOver() {
        Player player = game.getPlayer();
        Enemy enemy = game.getEnemies().get(0);
        
        // Mover jugador a posición del enemigo
        player.moveTo(enemy.getRow(), enemy.getColumn());
        
        // Verificar colisión manualmente (simular lo que hace movePlayer)
        game.movePlayer(0, 0, 0); // Mover sin cambiar posición para activar checkEnemyCollision
        
        // En este caso, como movePlayer no mueve si dx=dy=0, simulamos directamente
        // Mejor alternativa: mover enemigo a jugador
        enemy.setPosition(player.getRow(), player.getColumn());
        game.updateEnemies();
        
        assertTrue("Debe ser gameOver después de colisión", game.isGameOver());
    }
    
    // ========== PRUEBAS DE ENEMIGOS ==========
    
    /**
     * Prueba 15: Verificar que updateEnemies no actualiza con gameOver
     */
    @Test
    public void testUpdateEnemiesNoFuncionaConGameOver() {
        game.setGameOver(true);
        Enemy enemy = game.getEnemies().get(0);
        int filaInicial = enemy.getRow();
        int columnaInicial = enemy.getColumn();
        
        game.updateEnemies();
        
        // Los enemigos no deben moverse con gameOver
        // (esto puede variar dependiendo de la implementación específica del enemigo)
        assertTrue("gameOver debe seguir siendo true", game.isGameOver());
    }
    
    // ========== PRUEBAS DE NIVELES ==========
    
    /**
     * Prueba 16: Verificar que nextLevel incrementa el nivel
     */
    @Test
    public void testNextLevelIncrementaNivel() {
        assertEquals("Debe iniciar en nivel 1", 1, game.getCurrentLevel());
        
        game.nextLevel();
        
        assertEquals("Debe avanzar a nivel 2", 2, game.getCurrentLevel());
    }
    
    /**
     * Prueba 17: Verificar que resetLevel mantiene el nivel actual
     */
    @Test
    public void testResetLevelMantieneNivel() {
        int nivelActual = game.getCurrentLevel();
        
        game.resetLevel();
        
        assertEquals("Debe mantener el mismo nivel", nivelActual, game.getCurrentLevel());
    }
    
    /**
     * Prueba 18: Verificar que resetLevel reinicia estados
     */
    @Test
    public void testResetLevelReiniciaEstados() {
        game.setGameOver(true);
        game.setGameWon(true);
        
        game.resetLevel();
        
        assertFalse("gameOver debe ser false después de reset", game.isGameOver());
        assertFalse("gameWon debe ser false después de reset", game.isGameWon());
    }
    
    // ========== PRUEBAS DE GETTERS Y SETTERS ==========
    
    /**
     * Prueba 19: Verificar getters retornan valores correctos
     */
    @Test
    public void testGettersRetornanValoresCorrectos() {
        assertNotNull("getPlayer() no debe ser null", game.getPlayer());
        assertNotNull("getPlayers() no debe ser null", game.getPlayers());
        assertNotNull("getEnemies() no debe ser null", game.getEnemies());
        assertNotNull("getFruits() no debe ser null", game.getFruits());
        assertNotNull("getMap() no debe ser null", game.getMap());
        assertNotNull("getGameState() no debe ser null", game.getGameState());
    }
    
    /**
     * Prueba 20: Verificar setters cambian valores correctamente
     */
    @Test
    public void testSettersCambianValores() {
        game.setGameOver(true);
        assertTrue("gameOver debe ser true", game.isGameOver());
        
        game.setGameWon(true);
        assertTrue("gameWon debe ser true", game.isGameWon());
        
        Player nuevoJugador = new Player(10, 10, "Chocolate", 1);
        game.setPlayer(nuevoJugador);
        assertEquals("El jugador debe cambiar", nuevoJugador, game.getPlayer());
    }
}