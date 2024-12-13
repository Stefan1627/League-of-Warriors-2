package org.game.ui;

import javax.swing.*;
import java.awt.*;

import static org.game.ui.game.FinalPageUI.setupFinalPageUI;

public class EnterAppUI extends JFrame {

    public static void main(String[] args) {
        EnterAppUI app = new EnterAppUI();
        app.startApplication();
    }

    public void startApplication() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Color background = new Color(30, 30, 30);

        JFrame frame = createMainFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel contentPanel = createStartPage(cardLayout, mainPanel, frame, background);
        JPanel finalPage = createFinalPage(screenSize);

        mainPanel.add(contentPanel, "StartPage");
        mainPanel.add(finalPage, "EndPage");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("League of Warriors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private JPanel createStartPage(CardLayout cardLayout, JPanel mainPanel, JFrame frame, Color background) {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(background);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        addStartPageLabels(contentPanel, gbc);
        addStartPageButtons(contentPanel, gbc, cardLayout, mainPanel, frame);

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

    private void addStartPageButtons(JPanel contentPanel, GridBagConstraints gbc, CardLayout cardLayout, JPanel mainPanel, JFrame frame) {
        JButton button = new JButton("Go to Final Page");
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(Color.CYAN);
        button.setPreferredSize(new Dimension(200, 75));

        JButton button2 = new JButton("Exit");
        button2.setFont(new Font("Arial", Font.BOLD, 15));
        button2.setBackground(Color.CYAN);
        button2.setPreferredSize(new Dimension(200, 75));

        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;
        contentPanel.add(button, gbc);

        gbc.gridx = 1;
        contentPanel.add(button2, gbc);

        button.addActionListener(e -> {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            cardLayout.show(mainPanel, "EndPage");
        });

        button2.addActionListener(e -> System.exit(0));
    }

    private JPanel createFinalPage(Dimension screenSize) {
        JPanel finalPage = new JPanel(new BorderLayout());
        setupFinalPageUI(finalPage, "/heroes/warrior-male2.png", screenSize);
        return finalPage;
    }
}
