package org.game.ui.utils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class CustomRenderer<T> extends JPanel implements ListCellRenderer<T> {
    private final JLabel titleLabel = new JLabel();
    private final JLabel subtitleLabel = new JLabel();
    private final JLabel iconLabel = new JLabel();

    public CustomRenderer() {
        setLayout(new BorderLayout(10, 10));
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        add(iconLabel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
        textPanel.setOpaque(false);
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
        // Customize the rendering for the specific type
        if (value instanceof org.game.account.Account account) {
            titleLabel.setText(account.getInfo().getFirst());
            subtitleLabel.setText(account.getInfo().get(1));
        } else if (value instanceof org.game.entities.Character character) {
            titleLabel.setText(character.getName());
            subtitleLabel.setText("Level: " + character.getCurrLvl());
        }

        titleLabel.setForeground(Color.WHITE);
        subtitleLabel.setForeground(Color.LIGHT_GRAY);

        // Load and scale a generic icon (can be parameterized further if needed)
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/blank.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));

        // Apply background colors for selection and alternation
        if (isSelected) {
            setBackground(Color.RED);
        } else if (index % 2 == 0) {
            setBackground(BACKGROUND_COLOR);
        } else {
            setBackground(new Color(45, 45, 45));
        }

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return this;
    }
}
