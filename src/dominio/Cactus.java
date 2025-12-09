package dominio;

/**
 * Cactus fruit - static fruit worth 250 points.
 * Every 30 seconds, it grows spikes that eliminate the player on contact.
 * After another 30 seconds, returns to normal and can be collected.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Cactus extends Fruit {
    
    private static final int CACTUS_POINTS = 250;
    private static final int SPIKE_CYCLE_TIME = 300; // 30 segundos (300 ticks a 100ms)
    
    private boolean hasSpikes; // Si tiene púas actualmente
    private int cycleTimer; // Contador para el ciclo de púas
    
    /**
     * Creates a cactus at the specified position.
     * Starts without spikes.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Cactus(int row, int column) {
        super(row, column, CACTUS_POINTS);
        this.hasSpikes = false;
        this.cycleTimer = 0;
    }
    
    @Override
    public void update() {
        if (!collected) {
            cycleTimer++;
            
            // Cada 30 segundos, alternar entre con púas y sin púas
            if (cycleTimer >= SPIKE_CYCLE_TIME) {
                hasSpikes = !hasSpikes;
                cycleTimer = 0;
            }
        }
    }
    
    /**
     * @return true if the cactus currently has spikes
     */
    public boolean hasSpikes() {
        return hasSpikes;
    }
    
    /**
     * @return remaining time until spike state changes (in ticks)
     */
    public int getCycleTimer() {
        return cycleTimer;
    }
    
    /**
     * @return time remaining in seconds until spike state changes
     */
    public int getSecondsUntilChange() {
        return (SPIKE_CYCLE_TIME - cycleTimer) / 10;
    }
    
    @Override
    public String getType() {
        return "Cactus";
    }
}
