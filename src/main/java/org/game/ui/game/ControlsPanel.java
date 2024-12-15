package org.game.ui.game;

import org.game.game.Game;

import javax.swing.*;
import java.awt.*;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class ControlsPanel extends JPanel {

    private final JButton northButton;
    private final JButton westButton;
    private final JButton southButton;
    private final JButton eastButton;

    public ControlsPanel(Game game, Frame frame, JPanel panel, CardLayout cardLayout) {
        setBackground(BACKGROUND_COLOR);
        setLayout(new GridLayout(5, 1, 10, 10));

        // Create Buttons
        northButton = createControlButton("North");
        westButton = createControlButton("West");
        southButton = createControlButton("South");
        eastButton = createControlButton("East");
        JButton chooseCharacterButton = createControlButton("Choose Another Character");

        // Add buttons to the panel
        add(northButton);
        add(westButton);
        add(southButton);
        add(eastButton);
        add(chooseCharacterButton);

        // Add Action Listeners
        northButton.addActionListener(e -> {
            game.getMap().goNorth();
            game.handleCellEvent(false);
            GameUI.updateUI();
        });
        westButton.addActionListener(e -> {
            game.getMap().goWest();
            game.handleCellEvent(false);
            GameUI.updateUI();
        });
        southButton.addActionListener(e -> {
            game.getMap().goSouth();
            game.handleCellEvent(false);
            GameUI.updateUI();
        });
        eastButton.addActionListener(e -> {
            game.getMap().goEast();
            game.handleCellEvent(false);
            GameUI.updateUI();
        });
        chooseCharacterButton.addActionListener(e -> chooseAnotherCharacter(frame, panel, cardLayout));

        // Initial visibility check
        updateControls(game);
    }

    // Helper to create styled buttons
    private JButton createControlButton(String text) {
        String buttonText;

        if (text.equals("Choose Another Character")) {
            buttonText = "<html><center>Choose Another<br>Character</center></html>";
        } else {
            buttonText = text;
        }

        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        return button;
    }


    // Update button visibility based on game conditions
    public void updateControls(Game game) {
        northButton.setVisible(game.canMove("North"));
        westButton.setVisible(game.canMove("West"));
        southButton.setVisible(game.canMove("South"));
        eastButton.setVisible(game.canMove("East"));
    }

    private void chooseAnotherCharacter(Frame frame, JPanel panel, CardLayout cardLayout) {
        System.out.println("Choosing another character...");

        // Exit fullscreen mode
        frame.setExtendedState(JFrame.NORMAL);

        // Resize and reposition the frame
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        // Show the "ChooseCharacter" panel
        cardLayout.show(panel.getParent(), "ChooseCharacter");
    }
}
