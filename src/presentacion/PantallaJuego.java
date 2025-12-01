package presentacion;

import dominio.*;
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
    private Timer gameTimer;
    private GameState gameState;
    
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
        
        // Crear el juego
        game = new Game();
        gameState = new GameState(nivel, game.getFruits().size());
        
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
        
        scoreLabel = new JLabel("Puntos: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        timeLabel = new JLabel("Tiempo: 03:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        fruitsLabel = new JLabel("Frutas: 0/3");
        fruitsLabel.setForeground(Color.WHITE);
        fruitsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
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
        JButton menuButton = new JButton("Menú Principal");
        
        pauseButton.addActionListener(e -> pauseGame());
        menuButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres salir? Se perderá el progreso.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                stopGameTimer();
                ventana.mostrarMenuPrincipal();
            }
        });
        
        panel.add(pauseButton);
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
                updateGameInfo();
            }
        });
        
        actionMap.put("moveDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 1);
                updateGameInfo();
            }
        });
        
        actionMap.put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(-1, 0);
                updateGameInfo();
            }
        });
        
        actionMap.put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(1, 0);
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
        gameTimer = new Timer(100, e -> {
            // Actualizar enemigos
            game.updateEnemies();
            
            // Redibujar el tablero
            boardPanel.refresh();
            
            // Verificar condiciones de victoria/derrota
            checkGameConditions();
        });
        gameTimer.start();
        
        // Timer separado para el contador de tiempo (cada segundo)
        Timer timeTimer = new Timer(1000, e -> {
            gameState.decrementTime();
            updateGameInfo();
            
            if (gameState.isTimeUp()) {
                gameOver("¡Se acabó el tiempo!");
            }
        });
        timeTimer.start();
    }
    
    /**
     * Stops the game timer.
     */
    private void stopGameTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
    
    /**
     * Pauses or resumes the game.
     */
    private void pauseGame() {
        if (gameTimer.isRunning()) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "Juego pausado");
            gameTimer.start();
        }
    }
    
    /**
     * Updates the displayed game information.
     */
    private void updateGameInfo() {
        scoreLabel.setText("Puntos: " + gameState.getScore());
        timeLabel.setText("Tiempo: " + gameState.getFormattedTime());
        fruitsLabel.setText("Frutas: " + gameState.getFruitsCollected() + 
                           "/" + gameState.getTotalFruits());
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
        stopGameTimer();
        JOptionPane.showMessageDialog(this, 
            message + "\nPuntuación final: " + gameState.getScore(),
            "Game Over",
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Handles victory scenario.
     */
    private void victory() {
        stopGameTimer();
        JOptionPane.showMessageDialog(this,
            "¡Nivel completado!\nPuntuación: " + gameState.getScore(),
            "¡Victoria!",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
