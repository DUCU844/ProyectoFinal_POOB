package presentacion;

import dominio.*;
import persistencia.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main game screen that combines the board, controls, and info display.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class PantallaJuego extends JPanel {
    
    private Game game;
    private BoardPanel boardPanel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel fruitsLabel;
    private JLabel levelLabel;
    private Timer gameTimer;
    private Timer timeTimer; // Timer separado para el tiempo
    
    /**
     * Creates the game screen with all UI elements.
     * 
     * @param ventana reference to main window
     * @param nivel level number
     * @param helado ice cream flavor
     * @param modalidad game mode
     */
    public PantallaJuego(VentanaPrincipal ventana, int nivel, String helado, String modalidad) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        // Crear el juego (Game ya crea su propio GameState internamente)
        game = new Game(nivel); // ✅ UNA SOLA VEZ
        
        // Panel superior con información del juego
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.NORTH);
        
        // Panel central con el tablero
        boardPanel = new BoardPanel(game);
        add(boardPanel, BorderLayout.CENTER);
        
        // Panel inferior con controles
        JPanel controlPanel = createControlPanel(ventana);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Configurar controles de teclado
        setupKeyBindings();
        
        // Iniciar el timer del juego
        startGameTimer();
        
        // Actualizar la información inicial
        updateGameInfo();
        
        // Dar foco al panel del tablero para recibir eventos de teclado
        boardPanel.requestFocusInWindow();
    }
    
    /**
     * Creates the top info panel with score, time, and fruits.
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        
        levelLabel = new JLabel("Nivel: 1");
        levelLabel.setForeground(Color.CYAN);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        scoreLabel = new JLabel("Puntos: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        timeLabel = new JLabel("Tiempo: 03:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        fruitsLabel = new JLabel("Frutas: 0/0");
        fruitsLabel.setForeground(Color.WHITE);
        fruitsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        panel.add(levelLabel);
        panel.add(scoreLabel);
        panel.add(timeLabel);
        panel.add(fruitsLabel);
        
        return panel;
    }
    
    /**
     * Creates the bottom control panel with buttons.
     */
    private JPanel createControlPanel(VentanaPrincipal ventana) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));

        JButton pauseButton = new JButton("Pausar");
        JButton saveButton = new JButton("Guardar");
        JButton menuButton = new JButton("Menú Principal");

        // Pausar juego
        pauseButton.addActionListener(e -> pauseGame());

        // Guardar partida
        saveButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this,
                "Nombre de la partida guardada:",
                "Guardar Partida",
                JOptionPane.QUESTION_MESSAGE);

            if (fileName != null && !fileName.trim().isEmpty()) {
                try {
                    GameSaver.saveGame(game, fileName.trim());
                    JOptionPane.showMessageDialog(this,
                        "¡Partida guardada exitosamente!",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (POOBException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error al guardar la partida:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                boardPanel.requestFocusInWindow();
            }
        });

        // Volver al menú principal
        menuButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres salir? Se perderá el progreso no guardado.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                stopGameTimers();
                ventana.mostrarMenuPrincipal();
            } else {
                boardPanel.requestFocusInWindow();
            }
        });

        panel.add(pauseButton);
        panel.add(saveButton);
        panel.add(menuButton);

        return panel;
    }
    
    /**
     * Sets up keyboard controls for the game.
     */
    private void setupKeyBindings() {
        // Obtener el InputMap y ActionMap del boardPanel
        InputMap inputMap = boardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = boardPanel.getActionMap();
        
        // Movimiento con flechas
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        
        // Disparar hielo con WASD
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "shootUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "shootDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "shootLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "shootRight");
        
        // Acciones de movimiento
        actionMap.put("moveUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, -1);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("moveDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 1);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(-1, 0);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(1, 0);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        // Acciones de disparar hielo
        actionMap.put("shootUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.playerShootIce(0, -1);
                boardPanel.refresh();
            }
        });
        
        actionMap.put("shootDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.playerShootIce(0, 1);
                boardPanel.refresh();
            }
        });
        
        actionMap.put("shootLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.playerShootIce(-1, 0);
                boardPanel.refresh();
            }
        });
        
        actionMap.put("shootRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.playerShootIce(1, 0);
                boardPanel.refresh();
            }
        });
    }
    
    /**
     * Starts the main game timer.
     */
    private void startGameTimer() {
        // Timer para actualizar enemigos y frutas (cada 100ms = 10 veces por segundo)
        gameTimer = new Timer(100, e -> {
            // Actualizar enemigos
            game.updateEnemies();
            // Actualizar frutas (piñas se mueven, cerezas se teletransportan)
            game.updateFruits();
            // Redibujar el tablero
            boardPanel.refresh();
            
            // Verificar condiciones de victoria/derrota
            checkGameConditions();
        });
        gameTimer.start();
        
        // Timer separado para el contador de tiempo (cada segundo)
        timeTimer = new Timer(1000, e -> {
            game.getGameState().decrementTime(); // ✅ UNA SOLA VEZ
            updateGameInfo();
            
            if (game.getGameState().isTimeUp()) {
                gameOver("¡Se acabó el tiempo!");
            }
        });
        timeTimer.start();
    }
    
    /**
     * Stops all game timers.
     */
    private void stopGameTimers() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
        if (timeTimer != null && timeTimer.isRunning()) {
            timeTimer.stop();
        }
    }
    
    /**
     * Pauses or resumes the game.
     */
    private void pauseGame() {
        if (gameTimer.isRunning()) {
            gameTimer.stop();
            timeTimer.stop();
            JOptionPane.showMessageDialog(this, "Juego pausado. Presiona OK para continuar.");
            gameTimer.start();
            timeTimer.start();
            boardPanel.requestFocusInWindow(); // Devolver el foco al tablero
        }
    }
    
    /**
     * Updates the displayed game information.
     */
    private void updateGameInfo() {
        GameState state = game.getGameState(); // ✅ Obtener el GameState del Game
        levelLabel.setText("Nivel: " + game.getCurrentLevel());
        scoreLabel.setText("Puntos: " + state.getScore());
        timeLabel.setText("Tiempo: " + state.getFormattedTime());
        fruitsLabel.setText("Frutas: " + state.getFruitsCollected() + 
                           "/" + state.getTotalFruits());
    }
    
    /**
     * Checks for win/lose conditions.
     */
    private void checkGameConditions() {
        if (game.isGameOver()) {
            gameOver("¡Te atrapó un enemigo!");
        } else if (game.isGameWon()) {
            victory();
        }
    }
    
    /**
     * Handles game over scenario.
     */
    private void gameOver(String message) {
        stopGameTimers();
        int score = game.getGameState().getScore();
        JOptionPane.showMessageDialog(this, 
            message + "\nPuntuación final: " + score,
            "Game Over",
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Handles victory scenario.
     */
    private void victory() {
        stopGameTimers();
        int score = game.getGameState().getScore();
        int nextLevel = game.getCurrentLevel() + 1;
        
        int option = JOptionPane.showOptionDialog(this,
            "¡Nivel completado!\nPuntuación: " + score,
            "¡Victoria!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Siguiente Nivel", "Menú Principal"},
            "Siguiente Nivel");
        
        if (option == 0 && nextLevel <= 3) {
            // Avanzar al siguiente nivel
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
            ventana.iniciarJuego(nextLevel, "Vainilla", "Player");
        } else {
            // Volver al menú principal
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
            ventana.mostrarMenuPrincipal();
        }
    }
    
    /**
     * Gets the current game instance (for saving).
     */
    public Game getGame() {
        return game;
    }
}