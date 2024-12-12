package org.game.parttwo;

import javax.swing.*;
import java.awt.*;

public class GameUI extends JFrame {
    public static void main(String[] args) {
        Color background = new Color(30, 30, 30);

        JFrame frame = new JFrame("League of Warriors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel("Welcome to League of Warriors");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 40));



        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        contentPanel.add(label, gbc);

        // Second Label
        JLabel label1 = new JLabel("Prepare for an epic battle!");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        contentPanel.add(label1, gbc);

        JLabel label2 = new JLabel("Please, login first!");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Arial", 0, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        contentPanel.add(label2, gbc);

        // Buttons
        JButton button = new JButton("Click Me1");
        button.setBackground(Color.CYAN);
        button.setPreferredSize(new Dimension(150, 75));

        JButton button2 = new JButton("Click Me");
        button2.setBackground(Color.CYAN);
        button2.setPreferredSize(new Dimension(150, 75));

        // Button 1
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 50, 10, 50);
        contentPanel.add(button, gbc);

        // Button 2
        gbc.gridx = 1;
        contentPanel.add(button2, gbc);

        // Button actions
        button.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Button Clicked!"));
        button2.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Button Clicked!2"));

        // Frame setup
        frame.add(contentPanel); // No need to pass GridBagConstraints here
        frame.setVisible(true);
    }
}
