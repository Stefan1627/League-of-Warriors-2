package org.game.ui.utils;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);

    public static JLabel createLabel(String text, int fontSize, Color foreground) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setForeground(foreground);
        return label;
    }

    public static JTextField createTextField(int columns, Color foreground) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);
        textField.setForeground(foreground);
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return textField;
    }

    public static JPasswordField createPasswordField(int columns, Color foreground) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(foreground);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return passwordField;
    }

    public static JButton createButton(String text, Color foreground, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.CYAN);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static JPanel createListWrapper(JList<?> list) {
        JPanel listWrapper = new JPanel(new GridBagLayout());
        listWrapper.setBackground(BACKGROUND_COLOR);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 400));

        // Hide scrollbars
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        // Add the JScrollPane to the center of the wrapper
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        listWrapper.add(scrollPane, gbc);

        return listWrapper;
    }

}
