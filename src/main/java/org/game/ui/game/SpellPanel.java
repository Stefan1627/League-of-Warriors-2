package org.game.ui.game;

import org.game.entities.Enemy;
import org.game.spells.*;
import org.game.entities.Character;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.game.ui.utils.UIUtils.BACKGROUND_COLOR;

public class SpellPanel extends JPanel {
    public SpellPanel(Spell spell, JPanel panel, CardLayout cardLayout,
                      Character character, Enemy enemy) {
        setPreferredSize(new Dimension(200, 200));
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Upper Part: Image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(spell.getImagePath())));
        Image scaledImage = icon.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Middle Part: Description
        JPanel descriptionPanel = createDescriptionPanel(spell);

        // Lower Part: Button
        JButton selectButton = new JButton("Select");
        selectButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectButton.setBackground(Color.CYAN);
        selectButton.setForeground(Color.BLACK);
        selectButton.setFocusPainted(false);
        selectButton.addActionListener(_ -> {
            JOptionPane.showMessageDialog(this,
                    spell.getClass().getSimpleName() + " selected!");

            character.getSpells().remove(spell);
            character.setMana(character.getMana() - spell.getMana());
            spell.visit(enemy);

            FightUI.updateUI();
            cardLayout.show(panel.getParent(), "Fight");
        });

        // SplitPane for Upper (Image) and Middle (Description)
        JSplitPane upperSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imageLabel, descriptionPanel);
        upperSplit.setDividerSize(2);
        upperSplit.setDividerLocation(180);
        upperSplit.setResizeWeight(0.5);
        upperSplit.setEnabled(false);

        // SplitPane for Upper (Image + Description) and Lower (Button)
        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperSplit, selectButton);
        mainSplit.setDividerSize(2);
        mainSplit.setDividerLocation(400);
        mainSplit.setResizeWeight(0.8);
        mainSplit.setEnabled(false);

        add(mainSplit, BorderLayout.CENTER);
    }

    private JPanel createDescriptionPanel(Spell spell) {
        JPanel descriptionPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        descriptionPanel.setBackground(new Color(50, 50, 50));

        JLabel nameLabel = new JLabel(spell.getClass().getSimpleName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel damageLabel = new JLabel("Damage: " + spell.getDamage());
        damageLabel.setForeground(Color.WHITE);
        damageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        damageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel manaLabel = new JLabel("Mana: " + spell.getMana());
        manaLabel.setForeground(Color.WHITE);
        manaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        manaLabel.setHorizontalAlignment(SwingConstants.CENTER);

        descriptionPanel.add(nameLabel);
        descriptionPanel.add(damageLabel);
        descriptionPanel.add(manaLabel);

        return descriptionPanel;
    }
}
