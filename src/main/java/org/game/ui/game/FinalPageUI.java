package org.game.ui.game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class FinalPageUI {

    public static void setupFinalPageUI(JPanel panel, String photoPath, Dimension screenSize) {
        // Load image
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(FinalPageUI.class.getResource(photoPath)));

        // Create and configure JLabel for the image
        JLabel imageLabel = new JLabel(new ImageIcon(originalIcon.getImage()));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Wrap the image in a JPanel with fixed dimensions
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setBorder(null);
        imagePanel.setBackground(BACKGROUND_COLOR);

        // Text Area for Final Page
        JTextArea textArea = new JTextArea("Hero Description:\n\nName: Warrior\nClass: Fighter\nAbilities: \n- Slash\n- Shield Block\n- Battle Cry");
        textArea.setFont(new Font("Serif", Font.PLAIN, 18));
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(BACKGROUND_COLOR);

        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        textScrollPane.setBackground(BACKGROUND_COLOR);
        textScrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(textScrollPane, BorderLayout.CENTER);
        textPanel.setBorder(null);
        textPanel.setBackground(BACKGROUND_COLOR);

        // Create JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, textPanel);
        splitPane.setDividerLocation(screenSize.width / 2);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);
        splitPane.setResizeWeight(0);
        splitPane.setBorder(null);

        // Add split pane to the panel
        panel.add(splitPane, BorderLayout.CENTER);
        panel.setBackground(BACKGROUND_COLOR);
    }
}
