package enemytest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import dominio.*;

/**
 * 10 Pruebas unitarias esenciales para la clase Enemy.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class EnemyTest {

    // Clase concreta para probar Enemy (que es abstracta)
    private class TestEnemy extends Enemy {
        
        public TestEnemy(int row, int column) {
            super(row, column);
        }
        
        @Override
        public void update(Game game) {
            if (canMove()) {
                row += directionRow;
                column += directionColumn;
            }
        }
        
        @Override
        public String getType() {
            return "TestEnemy";
        }
    }
    
    private TestEnemy enemy;
    
    @Before
    public void setUp() {
        enemy = new TestEnemy(5, 5);
    }
    
    /**
     * Prueba 1: Verificar que el constructor inicializa la posición correctamente
     */
    @Test
    public void testConstructorInicializaPosicion() {
        assertEquals("La fila inicial debe ser 5", 5, enemy.getRow());
        assertEquals("La columna inicial debe ser 5", 5, enemy.getColumn());
    }
    
    /**
     * Prueba 2: Verificar que la dirección inicial es correcta (0, 1)
     */
    @Test
    public void testDireccionInicialEsHaciaDerecha() {
        assertEquals("directionRow inicial debe ser 0", 0, enemy.getDirectionRow());
        assertEquals("directionColumn inicial debe ser 1", 1, enemy.getDirectionColumn());
    }
    
    /**
     * Prueba 3: Verificar que canMove() funciona con el sistema de cooldown
     */
    @Test
    public void testCanMoveRespetaCooldown() {
        // Con movementSpeed = 3 (default), debe retornar false, false, true
        assertFalse("Primera llamada debe ser false", enemy.canMove());
        assertFalse("Segunda llamada debe ser false", enemy.canMove());
        assertTrue("Tercera llamada debe ser true", enemy.canMove());
    }
    
    /**
     * Prueba 4: Verificar que reverseDirection() invierte la dirección
     */
    @Test
    public void testReverseDireccionInvierteDireccion() {
        enemy.setDirection(1, 1); // Diagonal abajo-derecha
        enemy.reverseDirection();
        
        assertEquals("directionRow debe invertirse a -1", -1, enemy.getDirectionRow());
        assertEquals("directionColumn debe invertirse a -1", -1, enemy.getDirectionColumn());
    }
    
    /**
     * Prueba 5: Verificar que setDirection() cambia la dirección correctamente
     */
    @Test
    public void testSetDireccionCambiaDireccion() {
        enemy.setDirection(-1, 0); // Mover hacia arriba
        
        assertEquals("directionRow debe ser -1", -1, enemy.getDirectionRow());
        assertEquals("directionColumn debe ser 0", 0, enemy.getDirectionColumn());
    }
    
    /**
     * Prueba 6: Verificar que setMovementSpeed() cambia la velocidad
     */
    @Test
    public void testSetMovementSpeedCambiaVelocidad() {
        enemy.setMovementSpeed(1); // Velocidad rápida
        
        assertTrue("Con speed=1, debe poder moverse inmediatamente", enemy.canMove());
    }
    
    /**
     * Prueba 7: Verificar que el enemigo se mueve después de suficientes updates
     */
    @Test
    public void testUpdateMueveEnemigoDespuesDeCooldown() {
        Game game = new Game(1);
        enemy.setDirection(1, 0); // Mover hacia abajo
        
        int filaInicial = enemy.getRow();
        
        // Llamar update 3 veces para que se mueva
        enemy.update(game);
        enemy.update(game);
        enemy.update(game);
        
        assertEquals("El enemigo debe moverse una fila hacia abajo", 
                     filaInicial + 1, enemy.getRow());
    }
    
    /**
     * Prueba 8: Verificar que getType() retorna el tipo correcto
     */
    @Test
    public void testGetTypeRetornaTipoCorrecto() {
        assertEquals("El tipo debe ser 'TestEnemy'", "TestEnemy", enemy.getType());
    }
    
    /**
     * Prueba 9: Verificar que doble reverse retorna a dirección original
     */
    @Test
    public void testDobleReverseRetornaADireccionOriginal() {
        int rowOriginal = enemy.getDirectionRow();
        int colOriginal = enemy.getDirectionColumn();
        
        enemy.reverseDirection();
        enemy.reverseDirection();
        
        assertEquals("Debe retornar a directionRow original", rowOriginal, enemy.getDirectionRow());
        assertEquals("Debe retornar a directionColumn original", colOriginal, enemy.getDirectionColumn());
    }
    
    /**
     * Prueba 10: Verificar movimiento completo en diferentes direcciones
     */
    @Test
    public void testMovimientoEnTodasLasDirecciones() {
        Game game = new Game(1);
        
        // Mover derecha
        enemy.setDirection(0, 1);
        for (int i = 0; i < 3; i++) enemy.update(game);
        assertEquals("Debe moverse a la derecha", 6, enemy.getColumn());
        
        // Mover abajo
        enemy.setDirection(1, 0);
        for (int i = 0; i < 3; i++) enemy.update(game);
        assertEquals("Debe moverse hacia abajo", 6, enemy.getRow());
    }
}