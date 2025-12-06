package presentacion;

import dominio.*;
import persistencia.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main game screen that combines the board, controls, and info display.
 * Supports both single player and two player modes.
 * 
 * CONTROLES:
 * Jugador 1: Flechas (movimiento) + ESPACIO (disparar hielo)
 * Jugador 2: WASD (movimiento) + F (disparar hielo)
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
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private Timer gameTimer;
    private Timer timeTimer;
    
    /**
     * Creates the game screen with all UI elements.
     * 
     * @param ventana reference to main window
     * @param nivel level number
     * @param twoPlayerMode true for 2 players, false for 1 player
     * @param flavor1 player 1 flavor
     * @param flavor2 player 2 flavor (ignored if single player)
     */
    public PantallaJuego(VentanaPrincipal ventana, int nivel, boolean twoPlayerMode, 
                        String flavor1, String flavor2) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        // Crear el juego con la configuración especificada
        game = new Game(nivel, twoPlayerMode, flavor1, flavor2);
        
        // Panel superior con información del juego
        JPanel infoPanel = createInfoPanel(twoPlayerMode);
        add(infoPanel, BorderLayout.NORTH);
        
        // Panel central con el tablero
        boardPanel = new BoardPanel(game);
        add(boardPanel, BorderLayout.CENTER);
        
        // Panel inferior con controles
        JPanel controlPanel = createControlPanel(ventana);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Configurar controles de teclado
        setupKeyBindings(twoPlayerMode);
        
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
    private JPanel createInfoPanel(boolean twoPlayerMode) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        levelLabel = new JLabel("Nivel: 1");
        levelLabel.setForeground(Color.CYAN);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        scoreLabel = new JLabel("Puntos Totales: 0");
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
        
        // Si es modo 2 jugadores, agregar puntajes individuales
        if (twoPlayerMode) {
            player1ScoreLabel = new JLabel("J1: 0");
            player1ScoreLabel.setForeground(new Color(100, 200, 255));
            player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
            
            player2ScoreLabel = new JLabel("J2: 0");
            player2ScoreLabel.setForeground(new Color(255, 150, 150));
            player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
            
            panel.add(new JLabel(" | ") {{
                setForeground(Color.GRAY);
            }});
            panel.add(player1ScoreLabel);
            panel.add(player2ScoreLabel);
        }
        
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
     * Jugador 1: Flechas (movimiento) + ESPACIO (disparar)
     * Jugador 2: WASD (movimiento) + F (disparar)
     */
    private void setupKeyBindings(boolean twoPlayerMode) {
        InputMap inputMap = boardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = boardPanel.getActionMap();
        
        // JUGADOR 1 
        // Movimiento con flechas
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "p1_moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "p1_moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "p1_moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "p1_moveRight");
        
        // Disparar hielo con ESPACIO
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "p1_shootIce");
        
        // Acciones de movimiento Jugador 1
        actionMap.put("p1_moveUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 0, -1);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("p1_moveDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 0, 1);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("p1_moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, -1, 0);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        actionMap.put("p1_moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.movePlayer(0, 1, 0);
                boardPanel.refresh();
                updateGameInfo();
            }
        });
        
        // Acción de disparar hielo Jugador 1
        actionMap.put("p1_shootIce", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.playerShootIce(0); // Dispara en la última dirección
                boardPanel.refresh();
            }
        });
        
        // JUGADOR 2 (solo si es modo 2 jugadores)
        if (twoPlayerMode) {
            // Movimiento con IJKL
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "p2_moveUp");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "p2_moveDown");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "p2_moveLeft");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "p2_moveRight");
            
            // Disparar hielo con F
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "p2_shootIce");
            
            // Acciones de movimiento Jugador 2
            actionMap.put("p2_moveUp", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    game.movePlayer(1, 0, -1);
                    boardPanel.refresh();
                    updateGameInfo();
                }
            });
            
            actionMap.put("p2_moveDown", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    game.movePlayer(1, 0, 1);
                    boardPanel.refresh();
                    updateGameInfo();
                }
            });
            
            actionMap.put("p2_moveLeft", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    game.movePlayer(1, -1, 0);
                    boardPanel.refresh();
                    updateGameInfo();
                }
            });
            
            actionMap.put("p2_moveRight", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    game.movePlayer(1, 1, 0);
                    boardPanel.refresh();
                    updateGameInfo();
                }
            });
            
            // Acción de disparar hielo Jugador 2
            actionMap.put("p2_shootIce", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    game.playerShootIce(1); // Dispara en la última dirección
                    boardPanel.refresh();
                }
            });
        }
    }
    
    /**
     * Starts the main game timer.
     */
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
            boardPanel.requestFocusInWindow();
        }
    }
    
    /**
     * Updates the displayed game information.
     */
    private void updateGameInfo() {
        GameState state = game.getGameState();
        levelLabel.setText("Nivel: " + game.getCurrentLevel());
        scoreLabel.setText("Puntos Totales: " + state.getScore());
        timeLabel.setText("Tiempo: " + state.getFormattedTime());
        fruitsLabel.setText("Frutas: " + state.getFruitsCollected() + 
                           "/" + state.getTotalFruits());
        
        // Actualizar puntajes individuales si es modo 2 jugadores
        if (game.isTwoPlayerMode() && player1ScoreLabel != null && player2ScoreLabel != null) {
            player1ScoreLabel.setText("J1: " + game.getPlayers().get(0).getScore());
            player2ScoreLabel.setText("J2: " + game.getPlayers().get(1).getScore());
        }
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
        
        if (game.isTwoPlayerMode()) {
            Player p1 = game.getPlayers().get(0);
            Player p2 = game.getPlayers().get(1);
            
            String winner = p1.getScore() > p2.getScore() ? 
                "¡Jugador 1 gana!" : 
                (p2.getScore() > p1.getScore() ? "¡Jugador 2 gana!" : "¡Empate!");
            
            JOptionPane.showMessageDialog(this, 
                message + "\n" + winner + 
                "\nJ1: " + p1.getScore() + " puntos" +
                "\nJ2: " + p2.getScore() + " puntos",
                "Game Over",
                JOptionPane.ERROR_MESSAGE);
        } else {
            int score = game.getGameState().getScore();
            JOptionPane.showMessageDialog(this, 
                message + "\nPuntuación final: " + score,
                "Game Over",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Handles victory scenario.
     */
    private void victory() {
        stopGameTimers();
        int nextLevel = game.getCurrentLevel() + 1;
        
        String message;
        if (game.isTwoPlayerMode()) {
            Player p1 = game.getPlayers().get(0);
            Player p2 = game.getPlayers().get(1);
            
            String winner = p1.getScore() > p2.getScore() ? 
                "¡Jugador 1 va ganando!" : 
                (p2.getScore() > p1.getScore() ? "¡Jugador 2 va ganando!" : "¡Van empatados!");
            
            message = "¡Nivel completado!\n" + winner +
                     "\nJ1: " + p1.getScore() + " | J2: " + p2.getScore();
        } else {
            message = "¡Nivel completado!\nPuntuación: " + game.getGameState().getScore();
        }
        
        int option = JOptionPane.showOptionDialog(this,
            message,
            "¡Victoria!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Siguiente Nivel", "Menú Principal"},
            "Siguiente Nivel");
        
        if (option == 0 && nextLevel <= 3) {
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
            
            if (game.isTwoPlayerMode()) {
                ventana.iniciarJuego(nextLevel, true, 
                    game.getPlayers().get(0).getFlavor(),
                    game.getPlayers().get(1).getFlavor());
            } else {
                ventana.iniciarJuego(nextLevel, false, 
                    game.getPlayers().get(0).getFlavor(), "Fresa");
            }
        } else {
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