package dominio;

/**
 * Manages game state including score, time, and level information.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class GameState {
    
    private int score;
    private int timeRemaining; // en segundos
    private int currentLevel;
    private int fruitsCollected;
    private int totalFruits;
    
    private static final int MAX_TIME_PER_LEVEL = 180; // 3 minutos
    
    /**
     * Creates a new game state for the specified level.
     * 
     * @param level level number
     * @param totalFruits total fruits to collect
     */
    public GameState(int level, int totalFruits) {
        this.currentLevel = level;
        this.totalFruits = totalFruits;
        this.score = 0;
        this.fruitsCollected = 0;
        this.timeRemaining = MAX_TIME_PER_LEVEL;
    }
    
    /**
     * Adds points to the current score.
     * 
     * @param points points to add
     */
    public void addScore(int points) {
        this.score += points;
    }
    
    /**
     * Increments the fruit collection counter.
     */
    public void collectFruit() {
        this.fruitsCollected++;
    }
    
    /**
     * Decreases time by one second.
     * Called every second during gameplay.
     */
    public void decrementTime() {
        if (timeRemaining > 0) {
            timeRemaining--;
        }
    }
    
    /**
     * @return true if all fruits have been collected
     */
    public boolean isLevelComplete() {
        return fruitsCollected >= totalFruits;
    }
    
    /**
     * @return true if time has run out
     */
    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }
    
    /**
     * Resets the game state for a level restart.
     */
    public void resetLevel() {
        this.score = 0;
        this.fruitsCollected = 0;
        this.timeRemaining = MAX_TIME_PER_LEVEL;
    }
    
    // Getters
    public int getScore() { return score; }
    public int getTimeRemaining() { return timeRemaining; }
    public int getCurrentLevel() { return currentLevel; }
    public int getFruitsCollected() { return fruitsCollected; }
    public int getTotalFruits() { return totalFruits; }
    
    /**
     * @return formatted time string (MM:SS)
     */
    public String getFormattedTime() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}