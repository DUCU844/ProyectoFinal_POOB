package presentacion;

import dominio.*;
import persistencia.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Game screen for loaded saved games.
 * Similar to PantallaJuego but receives a pre-loaded Game object.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class PantallaJuegoCargado extends JPanel {
    
    private Game game;
    private BoardPanel boardPanel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel fruitsLabel;
    private JLabel levelLabel;
    private Timer gameTimer;
    private Timer timeTimer;
    
    /**
     * Creates game screen from a loaded game.
     * 
     * @param ventana reference to main window
     * @param loadedGame the loaded game instance
     */
    public PantallaJuegoCargado(VentanaPrincipal ventana, Game loadedGame) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        this.game = loadedGame;
        
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
        
        // Dar foco al panel del tablero
        boardPanel.requestFocusInWindow();
    }
    
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
    
    private JPanel createControlPanel(VentanaPrincipal ventana) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        
        JButton pauseButton = new JButton("Pausar");
        JButton saveButton = new JButton("Guardar");
        JButton menuButton = new JButton("Menú");
        
        pauseButton.addActionListener(e -> pauseGame());
        
        saveButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this,
                "Nombre de la partida guardada:",
                "Guardar Partida",
                JOptionPane.QUESTION_MESSAGE);
            
            if (fileName != null && !fileName.trim().isEmpty()) {
                try {
                    GameSaver.saveGame(game, fileName.trim());
                    JOptionPane.showMessageDialog(this,
                        "Partida guardada exitosamente!",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (POOBException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error al guardar:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                boardPanel.requestFocusInWindow();
            }
        });
        
        menuButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Salir sin guardar?",
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
    
    private void setupKeyBindings() {
        InputMap inputMap = boardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = boardPanel.getActionMap();
        
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "shootIce");
        
        actionMap.put("moveUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 0, -1); // playerIndex, dx, dy
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("moveDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 0, 1);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, -1, 0);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 1, 0);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("shootIce", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.playerShootIce(0); // playerIndex = 0
                boardPanel.refresh();
            }
        });
    }
    
    private void startGameTimer() {
        gameTimer = new Timer(100, e -> {
            game.updateEnemies();
            game.updateFruits();
            boardPanel.refresh();
            checkGameConditions();
        });
        gameTimer.start();
        
        timeTimer = new Timer(1000, e -> {
            game.getGameState().decrementTime();
            updateGameInfo();
            
            if (game.getGameState().isTimeUp()) {
                gameOver("¡Se acabó el tiempo!");
            }
        });
        timeTimer.start();
    }
    
    private void stopGameTimers() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
        if (timeTimer != null && timeTimer.isRunning()) {
            timeTimer.stop();
        }
    }
    
    private void pauseGame() {
        if (gameTimer.isRunning()) {
            gameTimer.stop();
            timeTimer.stop();
            JOptionPane.showMessageDialog(this, "Juego pausado. Presiona OK para continuar.");
            gameTimer.start();
            timeTimer.start();
            boardPanel.requestFocusInWindow();
        }
    }
    
    private void updateGameInfo() {
        GameState state = game.getGameState();
        levelLabel.setText("Nivel: " + game.getCurrentLevel());
        scoreLabel.setText("Puntos: " + state.getScore());
        timeLabel.setText("Tiempo: " + state.getFormattedTime());
        fruitsLabel.setText("Frutas: " + state.getFruitsCollected() + 
                           "/" + state.getTotalFruits());
    }
    
    private void checkGameConditions() {
        if (game.isGameOver()) {
            gameOver("¡Te atrapó un enemigo!");
        } else if (game.isGameWon()) {
            victory();
        }
    }
    
    private void gameOver(String message) {
        stopGameTimers();
        int score = game.getGameState().getScore();
        JOptionPane.showMessageDialog(this, 
            message + "\nPuntuación final: " + score,
            "Game Over",
            JOptionPane.ERROR_MESSAGE);
    }
    
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
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
            ventana.iniciarJuego(nextLevel, "Vainilla", "Player");
        } else {
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
            ventana.mostrarMenuPrincipal();
        }
    }
}