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

    public boolean startFight(Character character, Enemy enemy) {
        System.out.println(character);
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

    public void attackEnemy(Character character, Enemy enemy) {
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
                choice = scanner.nextInt();
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Error! Please select one option from above");
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

    public void attackPlayer(Character character, Enemy enemy) {
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

    public void normalAttack(Character character, Enemy enemy) {
        if (turn == 2) {
            enemy.setHealth(enemy.getHealth() - character.getDamage());
            turn = 1;
        } else {
            character.setHealth(character.getHealth() - enemy.getDamage());
            turn = 2;
        }

        if (character.getHealth() <= 0) {
            characterDead = true;
        }

        if (enemy.getHealth() <= 0) {
            enemyDead = true;
        }
    }

    public void useAbilityPlayer2_0(Character character, Enemy enemy) {
        System.out.println("Please choose what ability you want to use:");
        System.out.println("These are the spells you have mana for");
        character.printSpells();
        System.out.println("Write the number in front of the ability you want to use");

        choice = scanner.nextInt();
        choice--;
        try {
            if (!hasEnoughMana(character)) {
                throw new NotEnoughManaException();
            }
            if (choice < 0 || choice > character.getSpells().size()) {
                throw new InvalidSpellException();
            }
        } catch (NotEnoughManaException | InvalidSpellException e) {
            System.out.println(e.getMessage());
            useAbilityPlayer2_0(character, enemy);
        }

        Spell currSpell = character.getSpells().remove(choice);
        System.out.println("Used Ability: " + character.printClass(currSpell) + ", " + currSpell);
        character.setMana(character.getMana() - currSpell.getMana());

        if (currSpell.getClass() == Fire.class && enemy.isFireProof()) {
            System.out.println("Oops... Your enemy is fireproof");
            return;
        } else if (currSpell.getClass() == Ice.class && enemy.isIceProof()) {
            System.out.println("Oops... Your enemy is iceproof");
            return;
        } else if (currSpell.getClass() == Earth.class && enemy.isEarthProof()) {
            System.out.println("Oops... Your enemy is earthproof");
            return;
        }

        enemy.recieveDamage(currSpell.getDamage());
        if (enemy.getHealth() <= 0) {
            enemyDead = true;
        }
    }

    public void useAbilityEnemy(Character character, Enemy enemy) {
        while (true) {
            choice = rand.nextInt(enemy.getSpells().size());
            if (hasEnoughMana(enemy)) {
                break;
            }
        }

        Spell currSpell = enemy.getSpells().remove(choice);
        System.out.println("Used Ability: " + enemy.printClass(currSpell) + " " + currSpell);
        enemy.setMana(enemy.getMana() - currSpell.getMana());

        if ((currSpell.getClass() == Fire.class && character.getClass() == Warrior.class)
            || (currSpell.getClass() == Ice.class && character.getClass() == Mage.class)
            || (currSpell.getClass() == Earth.class && character.getClass() == Rogue.class)) {
            System.out.println("Oops... Your enemy got scammed");
            return;
        }

        character.recieveDamage(currSpell.getDamage());
        if (character.getHealth() <= 0) {
            characterDead = true;
        }
    }

    public boolean hasEnoughMana(Entity character) {
        return character.getMana() > character.getSpells().get(choice).getMana();
    }

    public boolean notEnoughManaForAbilities(Entity character) {
        int min = character.getMana();
        for (Spell spell : character.getSpells()) {
            if (spell.getMana() < min) {
                min = spell.getMana();
            }
        }
        return min == character.getMana();
    }
}
