package org.game.ui;

import org.game.game.Game;

import javax.swing.*;
import java.awt.*;

import static org.game.ui.login.EnterCredentials.setupEnterCredentials;
import static org.game.ui.login.ChooseAcc.setupChooseAcc;
import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;
import static org.game.ui.utils.UIUtils.createButton;

public class LoWUI extends JFrame {
    public void startApplication(Game game) {
        JFrame frame = createMainFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel contentPanel = createStartPage(cardLayout, mainPanel);
        JPanel enterCredentials = createEnterCredentials(game, cardLayout, frame);
        JPanel chooseAcc = createChooseAcc(game, cardLayout, frame);

        mainPanel.add(contentPanel, "StartPage");
        mainPanel.add(enterCredentials, "EnterCredentials");
        mainPanel.add(chooseAcc, "ChooseAcc");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("League of Warriors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private JPanel createStartPage(CardLayout cardLayout, JPanel mainPanel) {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        addStartPageLabels(contentPanel, gbc);
        addStartPageButtons(contentPanel, gbc, cardLayout, mainPanel);

        return contentPanel;
    }

    private void addStartPageLabels(JPanel contentPanel, GridBagConstraints gbc) {
        JLabel label = new JLabel("Welcome to League of Warriors");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(label, gbc);

        JLabel label1 = new JLabel("Prepare for an epic battle!");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridy = 1;
        contentPanel.add(label1, gbc);

        JLabel label2 = new JLabel("Please, login first!");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        contentPanel.add(label2, gbc);
    }

    private void addStartPageButtons(JPanel contentPanel, GridBagConstraints gbc, CardLayout cardLayout, JPanel mainPanel) {
        // Create buttons with adjusted size to fit the text
        JButton button = createButton("Enter Credentials", Color.BLACK,
                new Font("Arial", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(280, 75));

        JButton button2 = createButton("Choose from existing accounts", Color.BLACK,
                new Font("Arial", Font.BOLD, 15));
        button2.setPreferredSize(new Dimension(280, 75));

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Add first button
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;
        contentPanel.add(button, gbc);

        // Add second button
        gbc.gridx = 1;
        contentPanel.add(button2, gbc);

        // Add action listeners
        button.addActionListener(e -> cardLayout.show(mainPanel, "EnterCredentials"));
        button2.addActionListener(e -> cardLayout.show(mainPanel, "ChooseAcc"));
    }

    private JPanel createEnterCredentials(Game game, CardLayout cardLayout, JFrame frame) {
        JPanel enterCredentials = new JPanel();
        setupEnterCredentials(enterCredentials, game, cardLayout, frame);
        return enterCredentials;
    }

    private JPanel createChooseAcc(Game game, CardLayout cardLayout, JFrame frame) {
        JPanel chooseAcc = new JPanel(new BorderLayout());
        setupChooseAcc(chooseAcc, game, cardLayout, frame);
        return chooseAcc;
    }

    public static void setFullscreen(JFrame frame) {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
