package org.game.ui.game;

import org.game.game.Game;
import org.game.ui.LoWUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class ControlsInfo {
    public static void setupControlsInfo(JPanel panel, Game game, JFrame frame, CardLayout cardLayout) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Game Controls", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel playerLabel = new JLabel("<html><body style='width: 500px; text-align: center;'>"
                + "First of all, in this game the controls are a bit weird.<br>"
                + "You will need to press on buttons from the left side of the window.<br>"
                + "For example, if you move to north on the map, you must press the \"North\" button."
                + "</body></html>", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerLabel.setForeground(Color.WHITE);
        panel.add(playerLabel, BorderLayout.CENTER);

        // Add text to the panel
        JLabel keyToContinue = new JLabel("Press any key to continue", SwingConstants.CENTER);
        keyToContinue.setFont(new Font("Arial", Font.BOLD, 20));
        keyToContinue.setForeground(Color.WHITE);
        panel.add(keyToContinue, BorderLayout.SOUTH);

        // Create a pulsing effect using a Timer
        Timer timer = new Timer(15, new ActionListener() {
            float alpha = 1.0f;
            boolean fadingOut = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Adjust the alpha value smoothly
                if (fadingOut) {
                    alpha -= 0.02f;
                    if (alpha <= 0.2f) {
                        fadingOut = false;
                    }
                } else {
                    alpha += 0.02f;
                    if (alpha >= 1.0f) {
                        fadingOut = true;
                    }
                }

                // Update label color with the new alpha value
                keyToContinue.setForeground(new Color(1.0f, 1.0f, 1.0f, Math.max(0.0f, Math.min(1.0f, alpha))));
            }
        });
        timer.start();

        // Make the panel focusable
        panel.setFocusable(true);
        panel.setFocusTraversalKeysEnabled(false);

        // Add key listener for "press any key" functionality
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                JOptionPane.showMessageDialog(frame, "By pressing \"ok\" you understand the game controls " +
                        "and don't cry later because you don't know how to move", "Game Controls", JOptionPane.PLAIN_MESSAGE);

                // Define what happens when any key is pressed
                JPanel gamePanel = new JPanel();
                GameUI.setupGameUI(gamePanel, game, frame, cardLayout);

                LoWUI.setFullscreen(frame);
                panel.getParent().add(gamePanel, "Game");
                cardLayout.show(panel.getParent(), "Game");
            }
        });

        // Request focus after adding the panel to the frame
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
    }

}
