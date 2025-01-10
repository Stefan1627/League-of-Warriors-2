package org.game.ui.game;

import lombok.Getter;
import lombok.Setter;
import org.game.entities.Enemy;
import org.game.entities.Character;
import org.game.entities.Entity;
import org.game.game.EnemyFight;
import org.game.game.Game;

import javax.swing.*;
import java.awt.*;

import static org.game.game.Game.RANDOM;
import static org.game.ui.game.GameUI.createStatLabel;
import static org.game.ui.utils.UIUtils.*;

@Getter
@Setter
public class FightUI {
    private static Character character;
    private static JPanel statsPanelCharacter;

    private static Enemy enemy;
    private static JPanel statsPanelEnemy;

    private static Game game;

    public static void setupFight(Game game, JPanel panel,
                                  CardLayout cardLayout, boolean comingFromTest) {
        FightUI.game = game;
        panel.setLayout(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        character = game.getCurrCharacter();
        enemy = new Enemy();

        character.generateSpells();
        enemy.generateSpells();

        statsPanelCharacter = createStatsPanel(character);
        statsPanelEnemy = createStatsPanel(enemy);


        JPanel characterImagePanel = createPhotoPanel("/heroes/" + character.getImagePath());

        JSplitPane newBottomComponent = getJSplitPane(statsPanelCharacter, panel, cardLayout, comingFromTest);

        JSplitPane characterSide = new JSplitPane(JSplitPane.VERTICAL_SPLIT, characterImagePanel, newBottomComponent);
        characterSide.setDividerLocation((int) (screenSize.height * 0.75));
        characterSide.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 5));
        characterSide.setDividerSize(0);
        characterSide.setEnabled(false);


        JPanel enemyImagePanel = createPhotoPanel(enemy.getImagePath());

        JSplitPane enemySide = new JSplitPane(JSplitPane.VERTICAL_SPLIT, enemyImagePanel, statsPanelEnemy);
        enemySide.setDividerLocation((int) (screenSize.height * 0.75));
        enemySide.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 5));
        enemySide.setDividerSize(0);
        enemySide.setEnabled(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, characterSide, enemySide);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerLocation((int) (screenSize.width * 0.5));
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        panel.add(splitPane, BorderLayout.CENTER);
    }

    public static JPanel createStatsPanel(Entity character) {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);

        panel.setLayout(new GridLayout(3, 1, 5, 5));

        JLabel damageLabel = createStatLabel("Damage: " + character.getDamage());
        damageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel manaLabel = createStatLabel("Mana: " + character.getMana()
                + " / " + Entity.MAX_MANA);
        manaLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel healthLabel = createStatLabel("Health: " + character.getHealth()
                + " / " + Entity.MAX_HEALTH);
        healthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(damageLabel);
        panel.add(manaLabel);
        panel.add(healthLabel);

        panel.putClientProperty("Damage", damageLabel);
        panel.putClientProperty("Mana", manaLabel);
        panel.putClientProperty("Health", healthLabel);

        return panel;
    }

    private static JSplitPane getJSplitPane(JPanel statsLeftPanel, JPanel panel,
                                            CardLayout cardLayout, boolean comingFromTest) {
        JPanel statsRightPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        statsRightPanel.setBackground(BACKGROUND_COLOR);

        JButton button1 = createButton("Attack", Color.BLACK,
                new Font("Arial", Font.BOLD, 20));
        JButton button2 = createButton("Use Ability", Color.BLACK,
                new Font("Arial", Font.BOLD, 20));

        button1.addActionListener(_ -> {
            int health = enemy.getHealth();

            EnemyFight.normalAttack(character, enemy, 2);

            if (health != enemy.getHealth()) {
                JOptionPane.showMessageDialog(panel, "Attack successful, enemy received "
                        + (health - enemy.getHealth())
                        + " damage!");
            } else {
                JOptionPane.showMessageDialog(panel, "Oh no, you missed! Damage dealt: 0");
            }

            if (enemy.getHealth() <= 0) {
                characterWon(panel, cardLayout, character);
                JOptionPane.showMessageDialog(panel, "You won!");
                return;
            }
            updateUI();

            health = character.getHealth();
            EnemyFight.attackEnemy(character, enemy);

            JOptionPane.showMessageDialog(panel, "It was your enemy's turn. Damage received: " + (health - character.getHealth()));

            if (character.getHealth() <= 0) {
                enemyWon(panel, cardLayout, character, comingFromTest);
                return;
            }
            updateUI();
        });

        button2.addActionListener(_ -> {
            if (character.getSpells().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "You don't have any spells in this fight.",
                        "Error", JOptionPane.ERROR_MESSAGE);

            } else if (EnemyFight.notEnoughManaForAbilities(character)) {
                JOptionPane.showMessageDialog(panel, "You don't have enough mana "
                                + "for any spells in this fight.",
                            "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JPanel spellPanel = new JPanel();
                SpellsUI.setupSpellsUI(spellPanel, character, enemy, cardLayout, comingFromTest);

                panel.getParent().add(spellPanel, "Spells");
                cardLayout.show(panel.getParent(), "Spells");
            }
        });

        statsRightPanel.add(button1);
        statsRightPanel.add(button2);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, statsLeftPanel, statsRightPanel);
        splitPane.setDividerLocation((int) (screenSize.width * 0.35));
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        return splitPane;
    }

    public static void updateUI() {
        updateStatsCharacter();
        updateStatsEnemy();
    }

    public static void updateStatsCharacter() {
        JLabel damageLabel = (JLabel) statsPanelCharacter.getClientProperty("Damage");
        JLabel manaLabel = (JLabel) statsPanelCharacter.getClientProperty("Mana");
        JLabel healthLabel = (JLabel) statsPanelCharacter.getClientProperty("Health");

        damageLabel.setText("Damage: " + character.getDamage());
        manaLabel.setText("Mana: " + character.getMana()
                + " / " + Entity.MAX_MANA);
        healthLabel.setText("Health: " + character.getHealth()
                + " / " + Entity.MAX_HEALTH);

        statsPanelCharacter.revalidate();
        statsPanelCharacter.repaint();
    }

    public static void updateStatsEnemy() {
        JLabel damageLabel = (JLabel) statsPanelEnemy.getClientProperty("Damage");
        JLabel manaLabel = (JLabel) statsPanelEnemy.getClientProperty("Mana");
        JLabel healthLabel = (JLabel) statsPanelEnemy.getClientProperty("Health");

        damageLabel.setText("Damage: " + enemy.getDamage());
        manaLabel.setText("Mana: " + enemy.getMana()
                + " / " + Entity.MAX_MANA);
        healthLabel.setText("Health: " + enemy.getHealth()
                + " / " + Entity.MAX_HEALTH);

        statsPanelEnemy.revalidate();
        statsPanelEnemy.repaint();
    }

    public static void characterWon(JPanel panel, CardLayout cardLayout, Character character) {
        character.wonFight(RANDOM.nextInt(40,101));
        GameUI.updateUI();
        cardLayout.show(panel.getParent(), "Game");
    }

    public static void enemyWon(JPanel panel, CardLayout cardLayout,
                                Character character, boolean comingFromTest) {
        JPanel finalPanel = new JPanel();
        FinalPageUI.setupFinalPageUI(character, finalPanel, "/heroes/" + character.getImagePath(),
                                    cardLayout, panel, game, comingFromTest);

        // Show the "Final" panel
        panel.getParent().add(finalPanel, "Final");
        cardLayout.show(panel.getParent(), "Final");
    }
}
