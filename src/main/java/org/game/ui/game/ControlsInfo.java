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

        // Add text to the panel
        JLabel infoLabel = new JLabel("Press any key to continue", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        infoLabel.setForeground(Color.WHITE);
        panel.add(infoLabel, BorderLayout.CENTER);

        // Create a pulsing effect using a Timer
        Timer timer = new Timer(15, new ActionListener() { // Shorter interval for smoother transition
            float alpha = 1.0f; // Opacity value (1.0 = fully visible, 0.0 = fully transparent)
            boolean fadingOut = true; // Direction of fade

            @Override
            public void actionPerformed(ActionEvent e) {
                // Adjust the alpha value smoothly
                if (fadingOut) {
                    alpha -= 0.02f; // Smaller step for smoother fade-out
                    if (alpha <= 0.2f) {
                        fadingOut = false; // Switch to fading in
                    }
                } else {
                    alpha += 0.02f; // Smaller step for smoother fade-in
                    if (alpha >= 1.0f) {
                        fadingOut = true; // Switch to fading out
                    }
                }

                // Update label color with the new alpha value
                infoLabel.setForeground(new Color(1.0f, 1.0f, 1.0f, Math.max(0.0f, Math.min(1.0f, alpha))));
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
