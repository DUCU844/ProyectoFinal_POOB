package dominio;

/**
 * Firepit obstacle - eliminates the player on contact.
 * Can be temporarily extinguished by creating ice on it and breaking it.
 * After 10 seconds, the fire reignites.
 * Enemies are not affected by fire.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Firepit {
    
    private int row;
    private int column;
    private boolean isLit; // Si está encendida o apagada
    private int extinguishTimer; // Contador para reencender
    
    private static final int RELIGHT_TIME = 100; // 10 segundos (100 ticks a 100ms)
    
    /**
     * Creates a firepit at the specified position.
     * Starts lit by default.
     * 
     * @param row row position
     * @param column column position
     */
    public Firepit(int row, int column) {
        this.row = row;
        this.column = column;
        this.isLit = true;
        this.extinguishTimer = 0;
    }
    
    /**
     * Updates the firepit state each game tick.
     * Handles relight timer when extinguished.
     */
    public void update() {
        if (!isLit) {
            extinguishTimer++;
            
            // Después de 10 segundos, volver a encender
            if (extinguishTimer >= RELIGHT_TIME) {
                isLit = true;
                extinguishTimer = 0;
            }
        }
    }
    
    /**
     * Extinguishes the fire temporarily.
     * Called when ice is created and broken on top of it.
     */
    public void extinguish() {
        if (isLit) {
            isLit = false;
            extinguishTimer = 0;
        }
    }
    
    /**
     * @return true if the fire is currently lit
     */
    public boolean isLit() {
        return isLit;
    }
    
    /**
     * @return row position
     */
    public int getRow() {
        return row;
    }
    
    /**
     * @return column position
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * @return type identifier
     */
    public String getType() {
        return "Firepit";
    }
    
    /**
     * @return remaining time until relight (in ticks)
     */
    public int getExtinguishTimer() {
        return extinguishTimer;
    }
}
