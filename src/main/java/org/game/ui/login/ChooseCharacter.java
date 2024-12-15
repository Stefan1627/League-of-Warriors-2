package org.game.ui.login;

import org.game.entities.Character;
import org.game.game.Game;
import org.game.ui.utils.CustomRenderer;
import org.game.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class ChooseCharacter {
    public static void setupChooseCharacter(JPanel panel, Game game) {
        // Set background color
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BorderLayout());

        DefaultListModel<Character> characterModel = new DefaultListModel<>();
        for (Character character : game.getCurrAccount().getCharacters()) {
            characterModel.addElement(character);
        }

        JList<Character> characterJList = new JList<>(characterModel);
        characterJList.setCellRenderer(new CustomRenderer<>());
        characterJList.setBackground(new Color(30, 30, 30));

        // Center the JList in its container
        JPanel listWrapper = UIUtils.createListWrapper(characterJList);

        characterJList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                Character selectedCharacter = characterJList.getSelectedValue();
                if (selectedCharacter != null) {
                    game.setCurrCharacter(selectedCharacter);
                    // Set text in the fields
                }
            }
        });

        // Main Panel with BorderLayout
        panel.add(listWrapper, BorderLayout.CENTER);
    }
}
