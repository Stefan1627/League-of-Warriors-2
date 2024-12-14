package org.game.ui.login;

import javax.swing.*;
import java.awt.*;

public class EnterCredentials {
    static JTextField t1;
    static JPasswordField t2;
    static JButton btn;
    static JLabel l1, l2;
    private static final Color background = new Color(30, 30, 30);

    public static void setupEnterCredentials(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email Label and Field
        l1 = new JLabel("Email: ");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(l1, gbc);

        t1 = new JTextField(20);
        t1.setFont(new Font("Arial", Font.PLAIN, 14));
        t1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(t1, gbc);

        // Password Label and Field
        l2 = new JLabel("Password: ");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(l2, gbc);

        t2 = new JPasswordField(20);
        t2.setFont(new Font("Arial", Font.PLAIN, 14));
        t2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(t2, gbc);

        // Login Button
        btn = new JButton("Login");
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(Color.CYAN);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.addActionListener(e -> {
            String email = t1.getText();
            String password = new String(t2.getPassword());
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Welcome " + email + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btn, gbc);
    }
}
