package Icemaptest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import dominio.*;

/**
 * 10 Pruebas unitarias esenciales para la clase IceMap.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class IceMapTest {

    private IceMap map;
    
    @Before
    public void setUp() {
        map = new IceMap(20, 15);
    }
    
    /**
     * Prueba 1: Verificar que el constructor inicializa dimensiones correctamente
     */
    @Test
    public void testConstructorInicializaDimensiones() {
        assertEquals("El ancho debe ser 20", 20, map.getWidth());
        assertEquals("La altura debe ser 15", 15, map.getHeight());
    }
    
    /**
     * Prueba 2: Verificar que se generan paredes en los bordes del mapa
     */
    @Test
    public void testGeneraParadesEnBordes() {
        // Verificar esquinas
        assertTrue("Esquina superior izquierda debe tener hielo", map.hasIce(0, 0));
        assertTrue("Esquina superior derecha debe tener hielo", map.hasIce(19, 0));
        assertTrue("Esquina inferior izquierda debe tener hielo", map.hasIce(0, 14));
        assertTrue("Esquina inferior derecha debe tener hielo", map.hasIce(19, 14));
        
        // Verificar pared superior
        assertTrue("Pared superior debe tener hielo", map.hasIce(10, 0));
        
        // Verificar pared lateral
        assertTrue("Pared lateral debe tener hielo", map.hasIce(0, 7));
    }
    
    /**
     * Prueba 3: Verificar que el centro del mapa está vacío inicialmente
     */
    @Test
    public void testCentroDelMapaEstaVacio() {
        assertFalse("El centro debe estar sin hielo", map.hasIce(10, 7));
        assertFalse("Posición (5,5) debe estar sin hielo", map.hasIce(5, 5));
        assertFalse("Posición (15,10) debe estar sin hielo", map.hasIce(15, 10));
    }
    
    /**
     * Prueba 4: Verificar que isWalkable funciona correctamente
     */
    @Test
    public void testIsWalkableEnDiferentesPosiciones() {
        // Centro debe ser caminable
        assertTrue("El centro debe ser caminable", map.isWalkable(10, 7));
        
        // Bordes NO deben ser caminables (tienen hielo)
        assertFalse("El borde no debe ser caminable", map.isWalkable(0, 0));
        assertFalse("El borde superior no debe ser caminable", map.isWalkable(10, 0));
        
        // Fuera de límites NO debe ser caminable
        assertFalse("Fuera de límites no debe ser caminable", map.isWalkable(-1, 5));
        assertFalse("Fuera de límites no debe ser caminable", map.isWalkable(25, 10));
    }
    
    /**
     * Prueba 5: Verificar que toggleIce crea hielo en posición vacía
     */
    @Test
    public void testToggleIceCreaHieloEnPosicionVacia() {
        int x = 10, y = 7;
        
        assertFalse("Inicialmente no debe haber hielo", map.hasIce(x, y));
        
        boolean resultado = map.toggleIce(x, y);
        
        assertTrue("toggleIce debe retornar true", resultado);
        assertTrue("Ahora debe haber hielo", map.hasIce(x, y));
    }
    
    /**
     * Prueba 6: Verificar que toggleIce destruye hielo existente
     */
    @Test
    public void testToggleIceDestruyeHieloExistente() {
        int x = 10, y = 7;
        
        // Crear hielo primero
        map.toggleIce(x, y);
        assertTrue("Debe haber hielo", map.hasIce(x, y));
        
        // Destruir hielo
        boolean resultado = map.toggleIce(x, y);
        
        assertTrue("toggleIce debe retornar true", resultado);
        assertFalse("Ya no debe haber hielo", map.hasIce(x, y));
    }
    
    /**
     * Prueba 7: Verificar que addIgloo agrega iglú y bloquea posición
     */
    @Test
    public void testAddIglooBloqueaPosicion() {
        Igloo igloo = new Igloo(5, 5, 2, 2); // Iglú en (5,5) de tamaño 2x2
        map.addIgloo(igloo);
        
        // Verificar que el iglú está en la lista
        assertEquals("Debe haber 1 iglú", 1, map.getIgloos().size());
        
        // Verificar que la posición del iglú no es caminable
        assertFalse("Posición del iglú no debe ser caminable", map.isWalkable(5, 5));
        assertFalse("Área del iglú no debe ser caminable", map.isWalkable(6, 6));
    }
    
    /**
     * Prueba 8: Verificar que addFirepit agrega fogata y isSafeForPlayer funciona
     */
    @Test
    public void testAddFirepitYVerificarSeguridad() {
        Firepit firepit = new Firepit(8, 8);
        map.addFirepit(firepit);
        
        // Verificar que la fogata está en la lista
        assertEquals("Debe haber 1 fogata", 1, map.getFirepits().size());
        
        // Si la fogata está encendida, no debe ser seguro
        if (firepit.isLit()) {
            assertFalse("Posición con fogata encendida no debe ser segura", 
                       map.isSafeForPlayer(8, 8));
        }
    }
    
    /**
     * Prueba 9: Verificar que addHotTile agrega baldosa caliente y derrite hielo
     */
    @Test
    public void testAddHotTileDerritieHielo() {
        HotTile hotTile = new HotTile(6, 6);
        map.addHotTile(hotTile);
        
        // Verificar que la baldosa está en la lista
        assertEquals("Debe haber 1 baldosa caliente", 1, map.getHotTiles().size());
        
        // Verificar que hay una baldosa caliente en esa posición
        assertTrue("Debe haber baldosa caliente en (6,6)", map.hasHotTile(6, 6));
        
        // Intentar crear hielo en baldosa caliente (debe fallar)
        boolean resultado = map.toggleIce(6, 6);
        
        assertFalse("No debe poder crear hielo en baldosa caliente", resultado);
        assertFalse("No debe haber hielo en baldosa caliente", map.hasIce(6, 6));
    }
    
    /**
     * Prueba 10: Verificar que clearObstacles limpia el mapa correctamente
     */
    @Test
    public void testClearObstaclesLimpiaMapaCorrectamente() {
        // Agregar varios obstáculos
        map.addIgloo(new Igloo(5, 5, 2, 2));
        map.addFirepit(new Firepit(8, 8));
        map.addHotTile(new HotTile(10, 10));
        map.toggleIce(7, 7); // Crear hielo en el centro
        
        // Verificar que hay obstáculos
        assertEquals("Debe haber 1 iglú", 1, map.getIgloos().size());
        assertEquals("Debe haber 1 fogata", 1, map.getFirepits().size());
        assertEquals("Debe haber 1 baldosa caliente", 1, map.getHotTiles().size());
        assertTrue("Debe haber hielo en el centro", map.hasIce(7, 7));
        
        // Limpiar obstáculos
        map.clearObstacles();
        
        // Verificar que se limpiaron
        assertEquals("No debe haber iglús", 0, map.getIgloos().size());
        assertEquals("No debe haber fogatas", 0, map.getFirepits().size());
        assertEquals("No debe haber baldosas calientes", 0, map.getHotTiles().size());
        assertFalse("No debe haber hielo en el centro", map.hasIce(7, 7));
        
        // Verificar que los bordes siguen teniendo hielo
        assertTrue("Los bordes deben mantener el hielo", map.hasIce(0, 0));
        assertTrue("Los bordes deben mantener el hielo", map.hasIce(19, 14));
    }
}