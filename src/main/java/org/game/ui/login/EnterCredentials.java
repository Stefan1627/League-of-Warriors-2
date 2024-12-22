package org.game.ui.login;

import org.game.game.Game;
import org.game.ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class EnterCredentials {
    private static int loginAttempts = 0;

    public static void setupEnterCredentials(JPanel panel, Game game, CardLayout cardLayout, JFrame frame) {
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create and add components
        JLabel emailLabel = UIUtils.createLabel("Email: ", 16, Color.WHITE);
        JTextField emailField = UIUtils.createTextField(20, Color.BLACK);

        JLabel passwordLabel = UIUtils.createLabel("Password: ", 16, Color.WHITE);
        JPasswordField passwordField = UIUtils.createPasswordField(20, Color.BLACK);

        JButton loginButton = UIUtils.createButton("Login", Color.BLACK, new Font("Arial", Font.BOLD, 14));
        loginButton.addActionListener(_ -> handleLogin(panel, game, cardLayout, emailField,
                                                                    passwordField, frame));

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
    }

    private static void handleLogin(JPanel panel, Game game, CardLayout cardLayout, JTextField emailField,
                                    JPasswordField passwordField, JFrame frame) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (game.handleLoginInput(email, password)) {
            System.out.println("Login successful");
            loginAttempts = 0;

            JPanel chooseCharacterPanel = new JPanel();
            ChooseCharacter.setupChooseCharacter(chooseCharacterPanel, game, cardLayout, frame);

            panel.getParent().add(chooseCharacterPanel, "ChooseCharacter");
            cardLayout.show(panel.getParent(), "ChooseCharacter");
        } else {
            loginAttempts++;
            JOptionPane.showMessageDialog(panel, "Invalid credentials. Attempts left: " + (3 - loginAttempts), "Error", JOptionPane.ERROR_MESSAGE);
            if (loginAttempts >= 3) {
                JOptionPane.showMessageDialog(panel, "Too many failed attempts. Returning to Start Page.", "Error", JOptionPane.ERROR_MESSAGE);
                loginAttempts = 0;
                cardLayout.show(panel.getParent(), "StartPage");
            }
        }
    }
}
