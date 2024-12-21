package org.game.ui.login;

import org.game.entities.Character;
import org.game.game.Game;
import org.game.ui.LoWUI;
import org.game.ui.game.ControlsInfo;
import org.game.ui.game.FinalPageUI;
import org.game.ui.game.GameUI;
import org.game.ui.game.SpellsUI;
import org.game.ui.utils.CustomRenderer;
import org.game.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class ChooseCharacter {
    public static void setupChooseCharacter(JPanel panel, Game game, CardLayout cardLayout, JFrame frame) {
        // Set background color
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BorderLayout());

        DefaultListModel<Character> characterModel = new DefaultListModel<>();
        for (Character character : game.getCurrAccount().getCharacters()) {
            characterModel.addElement(character);
        }

        JList<Character> characterJList = new JList<>(characterModel);
        characterJList.setCellRenderer(new CustomRenderer<>());
        characterJList.setBackground(BACKGROUND_COLOR);

        // Center the JList in its container
        JPanel listWrapper = UIUtils.createListWrapper(characterJList);

        characterJList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                Character selectedCharacter = characterJList.getSelectedValue();
                if (selectedCharacter != null) {
                    game.setCurrCharacter(selectedCharacter);
                    game.setCharacterAttributes();
                    game.getCurrCharacter().setImage();
                    game.generateMapUI();

                    JPanel controlsInfoPanel = new JPanel();
                    ControlsInfo.setupControlsInfo(controlsInfoPanel, game, frame, cardLayout);

                    panel.getParent().add(controlsInfoPanel, "Controls");
                    cardLayout.show(panel.getParent(), "Controls");
                }
            }
        });

        // Main Panel with BorderLayout
        panel.add(listWrapper, BorderLayout.CENTER);
    }
}
