package dominio;

import java.util.Random;

/**
 * Cherry fruit - teleports randomly around the map.
 * Worth 150 points.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Cherry extends Fruit {
    
    private static final int cherry_points = 150;
    private Random random;
    private int tickCounter;
    private static final int teleport_interval = 50; // Teletransporta cada 50 ticks (~5 segundos)
    
    /**
     * Creates a cherry at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Cherry(int row, int column) {
        super(row, column, cherry_points);
        random = new Random();
        tickCounter = 0;
    }
    
    @Override
    public void update() {
        tickCounter++;
        
        // Teletransportarse cada cierto tiempo
        if (tickCounter >= teleport_interval) {
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
        return tickCounter >= teleport_interval;
    }
    
    @Override
    public String getType() {
        return "Cherry";
    }
}