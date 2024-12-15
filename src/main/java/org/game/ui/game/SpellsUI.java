package org.game.ui.game;

import org.game.entities.Character;

import javax.swing.*;
import java.awt.*;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class SpellsUI {
    public static void setupSpellsUI(JPanel panel, Character character) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(20,20,20,20);

        for (int i = 0; i < character.getSpells().size(); i++) {
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;

            SpellPanel spellPanel = new SpellPanel(character.getSpells().get(i));
            panel.add(spellPanel, gbc);
        }
    }
}
