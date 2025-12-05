package presentacion;

import dominio.*;
import persistencia.*;
import javax.swing.*;
import java.awt.*;

/**
 * Screen for saving and loading game states.
 * Allows players to manage their saved games.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class PantallaSaveLoad extends JPanel {
    
    /**
     * Creates save/load screen.
     * 
     * @param ventana reference to main window
     */
    public PantallaSaveLoad(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());
        
        JLabel titulo = new JLabel("GUARDAR / CARGAR PARTIDA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        add(titulo, BorderLayout.NORTH);
        
        // Panel central con lista de partidas guardadas
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Lista de partidas guardadas
        DefaultListModel<String> listModel = new DefaultListModel<>();
        String[] saveFiles = GameSaver.listSaveFiles();
        
        if (saveFiles.length == 0) {
            listModel.addElement("No hay partidas guardadas");
        } else {
            for (String fileName : saveFiles) {
                listModel.addElement(fileName);
            }
        }
        
        JList<String> saveList = new JList<>(listModel);
        saveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        saveList.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JScrollPane scrollPane = new JScrollPane(saveList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton loadButton = new JButton("Cargar Partida");
        JButton deleteButton = new JButton("Eliminar Partida");
        JButton backButton = new JButton("Volver");
        
        // Cargar partida seleccionada
        loadButton.addActionListener(e -> {
            String selected = saveList.getSelectedValue();
            if (selected != null && !selected.equals("No hay partidas guardadas")) {
                try {
                    Game loadedGame = GameLoader.loadGame(selected);
                    JOptionPane.showMessageDialog(this,
                        "Partida cargada exitosamente!",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Ir a la pantalla de juego con el juego cargado
                    ventana.cargarJuegoGuardado(loadedGame);
                    
                } catch (POOBException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error al cargar la partida:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Por favor selecciona una partida para cargar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Eliminar partida seleccionada
        deleteButton.addActionListener(e -> {
            String selected = saveList.getSelectedValue();
            if (selected != null && !selected.equals("No hay partidas guardadas")) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar la partida '" + selected + "'?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (GameSaver.deleteSave(selected)) {
                        JOptionPane.showMessageDialog(this,
                            "Partida eliminada exitosamente.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        // Refrescar la lista
                        ventana.mostrarSaveLoad();
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Error al eliminar la partida.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Por favor selecciona una partida para eliminar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Volver al menú
        backButton.addActionListener(e -> ventana.mostrarMenuPrincipal());
        
        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
}