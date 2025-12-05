package persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds all game data in a serializable format for saving/loading.
 * This class acts as a simple data transfer object (DTO).
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class GameData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Player data
    private int playerRow;
    private int playerColumn;
    
    // Game state
    private int currentLevel;
    private int score;
    private int timeRemaining;
    private int fruitsCollected;
    private int totalFruits;
    private boolean gameOver;
    private boolean gameWon;
    
    // Map data
    private int mapWidth;
    private int mapHeight;
    private boolean[][] iceGrid;
    
    // Enemies data
    private List<EnemyData> enemies;
    
    // Fruits data
    private List<FruitData> fruits;
    
    /**
     * Creates an empty GameData object.
     */
    public GameData() {
        this.enemies = new ArrayList<>();
        this.fruits = new ArrayList<>();
    }
    
    // Getters and Setters
    
    public int getPlayerRow() { return playerRow; }
    public void setPlayerRow(int playerRow) { this.playerRow = playerRow; }
    
    public int getPlayerColumn() { return playerColumn; }
    public void setPlayerColumn(int playerColumn) { this.playerColumn = playerColumn; }
    
    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public int getTimeRemaining() { return timeRemaining; }
    public void setTimeRemaining(int timeRemaining) { this.timeRemaining = timeRemaining; }
    
    public int getFruitsCollected() { return fruitsCollected; }
    public void setFruitsCollected(int fruitsCollected) { this.fruitsCollected = fruitsCollected; }
    
    public int getTotalFruits() { return totalFruits; }
    public void setTotalFruits(int totalFruits) { this.totalFruits = totalFruits; }
    
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    
    public boolean isGameWon() { return gameWon; }
    public void setGameWon(boolean gameWon) { this.gameWon = gameWon; }
    
    public int getMapWidth() { return mapWidth; }
    public void setMapWidth(int mapWidth) { this.mapWidth = mapWidth; }
    
    public int getMapHeight() { return mapHeight; }
    public void setMapHeight(int mapHeight) { this.mapHeight = mapHeight; }
    
    public boolean[][] getIceGrid() { return iceGrid; }
    public void setIceGrid(boolean[][] iceGrid) { this.iceGrid = iceGrid; }
    
    public List<EnemyData> getEnemies() { return enemies; }
    public void setEnemies(List<EnemyData> enemies) { this.enemies = enemies; }
    
    public List<FruitData> getFruits() { return fruits; }
    public void setFruits(List<FruitData> fruits) { this.fruits = fruits; }
    
    /**
     * Inner class to store enemy data.
     */
    public static class EnemyData implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String type;
        private int row;
        private int column;
        private int directionRow;
        private int directionColumn;
        
        public EnemyData(String type, int row, int column, int directionRow, int directionColumn) {
            this.type = type;
            this.row = row;
            this.column = column;
            this.directionRow = directionRow;
            this.directionColumn = directionColumn;
        }
        
        public String getType() { return type; }
        public int getRow() { return row; }
        public int getColumn() { return column; }
        public int getDirectionRow() { return directionRow; }
        public int getDirectionColumn() { return directionColumn; }
    }
    
    /**
     * Inner class to store fruit data.
     */
    public static class FruitData implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String type;
        private int row;
        private int column;
        private boolean collected;
        
        public FruitData(String type, int row, int column, boolean collected) {
            this.type = type;
            this.row = row;
            this.column = column;
            this.collected = collected;
        }
        
        public String getType() { return type; }
        public int getRow() { return row; }
        public int getColumn() { return column; }
        public boolean isCollected() { return collected; }
    }
}