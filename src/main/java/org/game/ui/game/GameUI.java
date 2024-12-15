package org.game.ui.game;

import org.game.entities.Entity;
import org.game.game.CallEntityType;
import org.game.game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class GameUI {
    // Reference to the grid panel
    private static JPanel gridPanel;

    // Reference to the controls panel
    private static JPanel controlsPanel;

    // Reference to the Game object
    private static Game currentGame;

    public static void setupGameUI(JPanel panel, Game game, Frame frame, CardLayout cardLayout) {
        currentGame = game;
        panel.setLayout(new BorderLayout());

        // Create left panel for text (includes ControlsPanel)
        JPanel leftPanel = createLeftPanel(game, frame, panel, cardLayout);

        // Create right panel for grid of cells
        gridPanel = new JPanel();
        setupGrid(gridPanel, game);

        // Add a JSplitPane to divide the two sections
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, gridPanel);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        panel.add(splitPane, BorderLayout.CENTER);
    }

    private static JPanel createLeftPanel(Game game, Frame frame, JPanel panel, CardLayout cardLayout) {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(50, 50, 50));

        // Controls Panel
        controlsPanel = new ControlsPanel(game, frame, panel, cardLayout);
        controlsPanel.setBorder(BorderFactory.createEmptyBorder());

        // Bottom Stats Panel
        JPanel bottomStatsPanel = createStatsPanel(game);

        // Split left panel vertically
        JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controlsPanel, bottomStatsPanel);
        verticalSplitPane.setDividerLocation(0.3);
        verticalSplitPane.setResizeWeight(0.7);
        verticalSplitPane.setDividerSize(2);
        verticalSplitPane.setBorder(BorderFactory.createEmptyBorder());
        verticalSplitPane.setEnabled(false);

        leftPanel.add(verticalSplitPane, BorderLayout.CENTER);

        return leftPanel;
    }

    private static JPanel createStatsPanel(Game game) {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(4, 1, 5, 5));
        statsPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel levelLabel = createStatLabel("Level: " + game.getCurrCharacter().getCurrLvl());
        JLabel experienceLabel = createStatLabel("Experience: " + game.getCurrCharacter().getCurrExp());
        JLabel manaLabel = createStatLabel("Mana: " + game.getCurrCharacter().getMana()
                + " / " + Entity.MAX_MANA);
        JLabel healthLabel = createStatLabel("Health: " + game.getCurrCharacter().getHealth()
                + " / " + Entity.MAX_HEALTH);

        statsPanel.add(levelLabel);
        statsPanel.add(experienceLabel);
        statsPanel.add(manaLabel);
        statsPanel.add(healthLabel);

        return statsPanel;
    }

    private static JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private static void setupGrid(JPanel panel, Game game) {
        // Clear the panel before rebuilding
        panel.removeAll();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridLayout(game.getLength(), game.getWidth(), 2, 2));

        for (int i = 0; i < game.getLength(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                JPanel cellPanel = createCell(game.getMap().get(i).get(j));
                panel.add(cellPanel);
            }
        }

        // Refresh layout
        panel.revalidate();
        panel.repaint();
    }

    private static JPanel createCell(org.game.game.Cell cell) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Determine the image path
        String imagePath;
        if (Objects.requireNonNull(cell.getType()) == CallEntityType.PLAYER) {
            imagePath = "/cells/player.png";
            panel.setBackground(Color.CYAN);
        } else if (cell.isVisited()) {
            imagePath = "/cells/null.png";
        } else {
            imagePath = "/cells/question.png";
        }

        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(GameUI.class.getResource(imagePath)));
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + imagePath);
            imageLabel.setText("?");
            imageLabel.setForeground(Color.CYAN);
            imageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        }

        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    public static void updateUI() {
        // Refresh the grid
        setupGrid(gridPanel, currentGame);

        // Update the controls
        ((ControlsPanel) controlsPanel).updateControls(currentGame);
    }
}
