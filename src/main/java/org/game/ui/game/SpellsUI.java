package org.game.ui.game;

import org.game.entities.Character;
import org.game.entities.Enemy;

import javax.swing.*;
import java.awt.*;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class SpellsUI {
    public static void setupSpellsUI(JPanel panel, Character character,
                                     Enemy enemy, CardLayout cardLayout,
                                     boolean comingFromTest) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridLayout(0, 3, 20, 20));

        for (int i = 0; i < character.getSpells().size(); i++) {
            SpellPanel spellPanel = new SpellPanel(character.getSpells().get(i), panel,
                    cardLayout, character, enemy, comingFromTest);
            panel.add(spellPanel);
        }

        // Fill remaining slots with placeholders
        int placeholdersNeeded = 6 - character.getSpells().size();
        for (int i = 0; i < placeholdersNeeded; i++) {
            JPanel placeholder = new JPanel();
            placeholder.setBackground(BACKGROUND_COLOR);

            // Add placeholder to maintain grid size
            panel.add(placeholder);
        }
    }
}
