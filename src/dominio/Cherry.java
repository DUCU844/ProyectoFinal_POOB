package dominio;

import java.util.Random;

/**
 * Cherry fruit - teleports randomly around the map.
 * Worth 150 points.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Cherry extends Fruit {
    
    private static final int CHERRY_POINTS = 150;
    private Random random;
    private int tickCounter;
    private static final int TELEPORT_INTERVAL = 50; // Teletransporta cada 50 ticks (~5 segundos)
    
    /**
     * Creates a cherry at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Cherry(int row, int column) {
        super(row, column, CHERRY_POINTS);
        random = new Random();
        tickCounter = 0;
    }
    
    @Override
    public void update() {
        tickCounter++;
        
        // Teletransportarse cada cierto tiempo
        if (tickCounter >= TELEPORT_INTERVAL) {
            teleport();
            tickCounter = 0;
        }
    }
    
    /**
     * Teleports the cherry to a random position.
     * Note: Game class should call this with valid bounds.
     */
    private void teleport() {
        // Esta posición será ajustada por el Game para estar en un lugar válido
        // Por ahora solo marcamos que necesita teletransportarse
    }
    
    /**
     * Teleports cherry to a specific valid position.
     * Called by Game class to ensure valid placement.
     * 
     * @param newRow new row position
     * @param newCol new column position
     */
    public void teleportTo(int newRow, int newCol) {
        setPosition(newRow, newCol);
        tickCounter = 0;
    }
    
    /**
     * @return true if cherry should teleport on next update
     */
    public boolean shouldTeleport() {
        return tickCounter >= TELEPORT_INTERVAL;
    }
    
    @Override
    public String getType() {
        return "Cherry";
    }
}