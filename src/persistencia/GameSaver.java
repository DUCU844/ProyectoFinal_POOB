package persistencia;

import dominio.*;
import java.io.*;

/**
 * Handles saving game state to a file.
 * Uses Java serialization to save game data.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class GameSaver {
    
    private static final String SAVE_DIRECTORY = "saves/";
    private static final String FILE_EXTENSION = ".poob";
    
    /**
     * Saves the current game state to a file.
     * 
     * @param game the game to save
     * @param fileName name of the save file (without extension)
     * @throws POOBException if save fails
     */
    public static void saveGame(Game game, String fileName) throws POOBException {
        try {
            // Crear directorio de guardados si no existe
            File saveDir = new File(SAVE_DIRECTORY);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            
            // Crear objeto GameData con toda la información del juego
            GameData data = new GameData();
            
            // Guardar datos del jugador
            data.setPlayerRow(game.getPlayer().getRow());
            data.setPlayerColumn(game.getPlayer().getColumn());
            
            // Guardar estado del juego
            data.setCurrentLevel(game.getCurrentLevel());
            data.setScore(game.getGameState().getScore());
            data.setTimeRemaining(game.getGameState().getTimeRemaining());
            data.setFruitsCollected(game.getGameState().getFruitsCollected());
            data.setTotalFruits(game.getGameState().getTotalFruits());
            data.setGameOver(game.isGameOver());
            data.setGameWon(game.isGameWon());
            
            // Guardar mapa
            IceMap map = game.getMap();
            data.setMapWidth(map.getWidth());
            data.setMapHeight(map.getHeight());
            
            // Copiar el grid de hielo
            boolean[][] iceGrid = new boolean[map.getHeight()][map.getWidth()];
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    iceGrid[y][x] = map.hasIce(x, y);
                }
            }
            data.setIceGrid(iceGrid);
            
            // Guardar enemigos
            for (Enemy enemy : game.getEnemies()) {
                GameData.EnemyData enemyData = new GameData.EnemyData(
                    enemy.getType(),
                    enemy.getRow(),
                    enemy.getColumn(),
                    enemy.getDirectionRow(),
                    enemy.getDirectionColumn()
                );
                data.getEnemies().add(enemyData);
            }
            
            // Guardar frutas
            for (Fruit fruit : game.getFruits()) {
                GameData.FruitData fruitData = new GameData.FruitData(
                    fruit.getType(),
                    fruit.getRow(),
                    fruit.getColumn(),
                    fruit.isCollected()
                );
                data.getFruits().add(fruitData);
            }
            
            // Guardar archivo
            String fullPath = SAVE_DIRECTORY + fileName + FILE_EXTENSION;
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(fullPath))) {
                out.writeObject(data);
            }
            
            System.out.println("Partida guardada exitosamente en: " + fullPath);
            
        } catch (IOException e) {
            throw new POOBException("Error al guardar la partida: " + e.getMessage());
        }
    }
    
    /**
     * Lists all available save files.
     * 
     * @return array of save file names (without extension)
     */
    public static String[] listSaveFiles() {
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            return new String[0];
        }
        
        File[] files = saveDir.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
        if (files == null || files.length == 0) {
            return new String[0];
        }
        
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            fileNames[i] = name.substring(0, name.length() - FILE_EXTENSION.length());
        }
        
        return fileNames;
    }
    
    /**
     * Checks if a save file exists.
     * 
     * @param fileName name of the save file
     * @return true if file exists
     */
    public static boolean saveExists(String fileName) {
        File file = new File(SAVE_DIRECTORY + fileName + FILE_EXTENSION);
        return file.exists();
    }
    
    /**
     * Deletes a save file.
     * 
     * @param fileName name of the save file to delete
     * @return true if deletion was successful
     */
    public static boolean deleteSave(String fileName) {
        File file = new File(SAVE_DIRECTORY + fileName + FILE_EXTENSION);
        return file.delete();
    }
}