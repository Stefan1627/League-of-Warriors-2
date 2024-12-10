package org.game.game;

import org.game.entities.*;
import org.game.entities.Character;
import org.game.exceptions.InvalidSpellException;
import org.game.exceptions.NotEnoughManaException;
import org.game.spells.*;

import java.util.Random;
import java.util.Scanner;

public class EnemyFight {
    private static final Random rand = new Random();
    private static final Scanner scanner = new Scanner(System.in);
    private static int choice;
    private int turn;
    private boolean characterDead = false;
    private boolean enemyDead = false;

    public EnemyFight() {
        turn = 1;
    }

    public boolean fight(Character character, Enemy enemy) {
        character.generateSpells();
        enemy.generateSpells();

        while (!characterDead && !enemyDead) {
            System.out.println("Your Health: " + character.getHealth()
            + "   Your Mana: " + character.getMana()
            + "   Your Damage: " + character.getDamage());
            System.out.println("Enemy`s Health: " + enemy.getHealth()
            + "   Enemy`s Mana: " + enemy.getMana()
            + "   Enemy`s Damage: " + enemy.getDamage());

            if (turn == 1) {
                attackPlayer(character, enemy);
            } else if (turn == 2) {
                attackEnemy(character, enemy);
            }

            if (character.getSpells().isEmpty()) {
                character.generateSpells();
            }
            if (enemy.getSpells().isEmpty()) {
                enemy.generateSpells();
            }
        }

        // handle someone died
        if (characterDead) {
            System.out.println("You are dead! GAME OVER");
        }

        if (enemyDead) {
            System.out.println("Congrats! You won the fight");
        }

        return characterDead;
    }

    private void attackEnemy(Character character, Enemy enemy) {
        System.out.println("No one died yet, prepare");
        System.out.println("It`s your turn!\n");

        if (notEnoughManaForAbilities(character)) {
            System.out.println("You don't have enough mana to use a spell");
            System.out.println("You will use a normal attack this turn!\n");
            choice = 1;
        } else {
            System.out.println("Please choose what you want to do:");
            System.out.println("1. Normal Attack    2. Use Ability");
            System.out.println("Press 1 or 2 in function of what you want to do:");
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.next());
                    if (choice == 1 || choice == 2) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a valid number");
                }
            }
        }

        switch (choice) {
            case 1:
                normalAttack(character, enemy);
                break;
            case 2:
                useAbilityPlayer2_0(character, enemy);
                break;
            default:
                System.out.println(choice + " is not a valid choice");
                break;
            }

        turn = 1;
        System.out.println("End of turn");
    }

    private void attackPlayer(Character character, Enemy enemy) {
        System.out.println("Enemy`s turn!");
        System.out.println("Please wait for the enemy to choose what he want to do");
        if (notEnoughManaForAbilities(enemy)) {
            choice = 1;
        } else {
            choice = rand.nextInt(2) + 1;
        }

        switch (choice) {
            case 1:
                normalAttack(character, enemy);
                break;
            case 2:
                useAbilityEnemy(character, enemy);
                break;
            default:
                System.out.println(choice + " is not a valid choice");
                break;
        }

        turn = 2;
        System.out.println("End of turn\n");
    }

    private void normalAttack(Character character, Enemy enemy) {
        if (turn == 2) {
            enemy.setHealth(enemy.getHealth() - character.getDamage());
            turn = 1;
        } else {
            character.setHealth(character.getHealth() - enemy.dealDamage());
            turn = 2;
        }

        if (character.getHealth() <= 0) {
            characterDead = true;
        }

        if (enemy.getHealth() <= 0) {
            enemyDead = true;
        }
    }

    private void useAbilityPlayer2_0(Character character, Enemy enemy) {
        System.out.println("Please choose what ability you want to use:");
        System.out.println("These are the spells you have mana for");
        character.printSpells();
        System.out.println("Write the number in front of the ability you want to use");

        try {
            choice = Integer.parseInt(scanner.next());
            choice--;
            if (hasEnoughMana(character)) {
                throw new NotEnoughManaException();
            }
            if (choice < 0 || choice > character.getSpells().size()) {
                throw new InvalidSpellException();
            }
        } catch (NotEnoughManaException | InvalidSpellException e) {
            System.out.println(e.getMessage());
            useAbilityPlayer2_0(character, enemy);
        } catch (Exception e) {
            System.out.println("Please enter a valid number.");
            useAbilityPlayer2_0(character, enemy);
        }

        Spell currSpell = character.getSpells().remove(choice);
        character.setMana(character.getMana() - currSpell.getMana());

        character.useAbility(currSpell, enemy);

        if (enemy.getHealth() <= 0) {
            enemyDead = true;
        }
    }

    private void useAbilityEnemy(Character character, Enemy enemy) {
        do {
            choice = rand.nextInt(enemy.getSpells().size());
        } while (hasEnoughMana(enemy));

        Spell currSpell = enemy.getSpells().remove(choice);
        enemy.setMana(enemy.getMana() - currSpell.getMana());

        enemy.useAbility(currSpell, character);

        if (character.getHealth() <= 0) {
            characterDead = true;
        }
    }

    private boolean hasEnoughMana(Entity character) {
        return character.getMana() < character.getSpells().get(choice).getMana();
    }

    private boolean notEnoughManaForAbilities(Entity character) {
        int min = character.getMana();
        for (Spell spell : character.getSpells()) {
            if (spell.getMana() < min) {
                min = spell.getMana();
            }
        }
        return min == character.getMana();
    }
}
