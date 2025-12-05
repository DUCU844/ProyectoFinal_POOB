package persistencia;

import dominio.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading saved game state from a file.
 * Reconstructs the game from serialized data.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class GameLoader {
    
    private static final String SAVE_DIRECTORY = "saves/";
    private static final String FILE_EXTENSION = ".poob";
    
    /**
     * Loads a game from a save file.
     * 
     * @param fileName name of the save file (without extension)
     * @return reconstructed Game object
     * @throws POOBException if load fails
     */
    public static Game loadGame(String fileName) throws POOBException {
        try {
            String fullPath = SAVE_DIRECTORY + fileName + FILE_EXTENSION;
            
            // Leer el archivo
            GameData data;
            try (ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(fullPath))) {
                data = (GameData) in.readObject();
            }
            
            // Reconstruir el juego
            Game game = new Game(data.getCurrentLevel());
            
            // Restaurar jugador
            Player player = new Player(data.getPlayerRow(), data.getPlayerColumn());
            game.setPlayer(player);
            
            // Restaurar mapa
            IceMap map = new IceMap(data.getMapWidth(), data.getMapHeight());
            boolean[][] iceGrid = data.getIceGrid();
            for (int y = 0; y < data.getMapHeight(); y++) {
                for (int x = 0; x < data.getMapWidth(); x++) {
                    if (iceGrid[y][x]) {
                        map.toggleIce(x, y);
                    }
                }
            }
            game.setMap(map);
            
            // Restaurar enemigos
            List<Enemy> enemies = new ArrayList<>();
            for (GameData.EnemyData enemyData : data.getEnemies()) {
                Enemy enemy = createEnemy(
                    enemyData.getType(),
                    enemyData.getRow(),
                    enemyData.getColumn()
                );
                
                // Restaurar dirección si es necesario
                if (enemyData.getDirectionRow() != 0 || enemyData.getDirectionColumn() != 0) {
                    enemy.setDirection(enemyData.getDirectionRow(), enemyData.getDirectionColumn());
                }
                
                enemies.add(enemy);
            }
            game.setEnemies(enemies);
            
            // Restaurar frutas
            List<Fruit> fruits = new ArrayList<>();
            for (GameData.FruitData fruitData : data.getFruits()) {
                Fruit fruit = createFruit(
                    fruitData.getType(),
                    fruitData.getRow(),
                    fruitData.getColumn()
                );
                
                if (fruitData.isCollected()) {
                    fruit.collect();
                }
                
                fruits.add(fruit);
            }
            game.setFruits(fruits);
            
            // Restaurar GameState
            GameState gameState = new GameState(
                data.getCurrentLevel(),
                data.getTotalFruits()
            );
            
            // Ajustar valores del GameState
            for (int i = 0; i < data.getScore(); i++) {
                gameState.addScore(1); // Agregar score punto por punto
            }
            
            for (int i = 0; i < data.getFruitsCollected(); i++) {
                gameState.collectFruit();
            }
            
            // Ajustar tiempo
            int timeDifference = 180 - data.getTimeRemaining();
            for (int i = 0; i < timeDifference; i++) {
                gameState.decrementTime();
            }
            
            game.setGameState(gameState);
            
            // Restaurar flags de game over/won
            game.setGameOver(data.isGameOver());
            game.setGameWon(data.isGameWon());
            
            System.out.println("Partida cargada exitosamente desde: " + fullPath);
            
            return game;
            
        } catch (IOException | ClassNotFoundException e) {
            throw new POOBException("Error al cargar la partida: " + e.getMessage());
        }
    }
    
    /**
     * Creates an enemy based on type.
     */
    private static Enemy createEnemy(String type, int row, int column) throws POOBException {
        switch (type) {
            case "Troll":
                return new Troll(row, column);
            case "Pot":
                return new Pot(row, column);
            case "OrangeSquid":
                return new OrangeSquid(row, column);
            default:
                throw new POOBException("Tipo de enemigo desconocido: " + type);
        }
    }
    
    /**
     * Creates a fruit based on type.
     */
    private static Fruit createFruit(String type, int row, int column) throws POOBException {
        switch (type) {
            case "Grape":
                return new Grape(row, column);
            case "Banana":
                return new Banana(row, column);
            case "Cherry":
                return new Cherry(row, column);
            case "Pineapple":
                return new Pineapple(row, column);
            default:
                throw new POOBException("Tipo de fruta desconocida: " + type);
        }
    }
}