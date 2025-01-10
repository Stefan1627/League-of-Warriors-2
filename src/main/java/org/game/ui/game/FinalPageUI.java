package org.game.ui.game;

import org.game.entities.Character;
import org.game.game.Game;

import javax.swing.*;
import java.awt.*;

import static org.game.ui.utils.UIUtils.*;

public class FinalPageUI {

    public static void setupFinalPageUI(Character character, JPanel panel,
                                        String photoPath, CardLayout cardLayout,
                                        JPanel mainPanel, Game game,
                                        boolean comingFromTest) {

        // Wrap the image in a JPanel with fixed dimensions
        JPanel imagePanel = createPhotoPanel(photoPath);

        // Text Area for Final Page
        JScrollPane textScrollPane = getJScrollPane(character);
        textScrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(textScrollPane, BorderLayout.CENTER);
        textPanel.setBorder(null);
        textPanel.setBackground(BACKGROUND_COLOR);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton button1 = createButton("Exit", Color.BLACK,
                new Font("Arial", Font.BOLD, 30));
        JButton button2 = createButton("Choose another character", Color.BLACK,
                new Font("Arial", Font.BOLD, 30));
        JButton button3 = createButton("Continue", Color.BLACK,
                new Font("Arial", Font.BOLD, 30));

        // Add actions to buttons
        button1.addActionListener(_ -> System.exit(0));
        button2.addActionListener(_ -> cardLayout.show(mainPanel.getParent(), "ChooseCharacter"));
        button3.addActionListener(_ -> {
            game.generateMapUI(comingFromTest);
            GameUI.updateUI();
            cardLayout.show(mainPanel.getParent(), "Game");

        });

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.setBackground(BACKGROUND_COLOR);

        // Split the right side into text at the top and buttons at the bottom
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textPanel, buttonPanel);
        rightSplitPane.setDividerLocation((int) (screenSize.height * 0.75));
        rightSplitPane.setDividerSize(0);
        rightSplitPane.setEnabled(false);
        rightSplitPane.setResizeWeight(1);
        rightSplitPane.setBorder(null);

        // Create JSplitPane to split the screen in half
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, rightSplitPane);
        mainSplitPane.setBorder(BorderFactory.createEmptyBorder());
        mainSplitPane.setDividerLocation((int) (screenSize.width * 0.5));
        mainSplitPane.setDividerSize(0);
        mainSplitPane.setEnabled(false);
        mainSplitPane.setResizeWeight(0.5);
        mainSplitPane.setBorder(null);

        // Add split pane to the panel
        panel.setLayout(new BorderLayout());
        panel.add(mainSplitPane);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.setBackground(BACKGROUND_COLOR);
    }

    private static JScrollPane getJScrollPane(Character character) {
        JTextArea textArea = new JTextArea("Name: " + character.getName()
                + "\n\nProfession: " + character.getClass().getSimpleName()
                + "\nLevel: " + character.getCurrLvl()
                + "\nExperience: " + character.getCurrExp()
                + "\nKilled Enemies: " + character.getKills()
                + "\nStrength: " + character.getStrength()
                + "\nCharisma: " + character.getCharisma()
                + "\nDexterity: " + character.getDexterity());
        textArea.setFont(new Font("Serif", Font.PLAIN, 25));
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(BACKGROUND_COLOR);

        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        textScrollPane.setBackground(BACKGROUND_COLOR);
        return textScrollPane;
    }
}
