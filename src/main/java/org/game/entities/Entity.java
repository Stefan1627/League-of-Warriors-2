package org.game.entities;

import lombok.Getter;
import lombok.Setter;
import org.game.spells.*;

import java.util.ArrayList;
import java.util.Random;

@Getter @Setter
public abstract class Entity implements Battle, Element<Entity> {
    public static final int MAX_HEALTH = 100;
    public static final int MAX_MANA = 100;
    private ArrayList<Spell> spells;
    private int health;
    private int mana;
    private boolean fireProof;
    private boolean iceProof;
    private boolean earthProof;
    private String imagePath;

    public Entity() {
        spells = new ArrayList<>();
        health = MAX_HEALTH;
        mana = MAX_MANA;
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }

    public void regenerateHealth(int health) {
        this.health = Math.min(health, MAX_HEALTH);
    }

    public void regenerateMana() {
        mana = MAX_MANA;
    }

    @Override
    public void receiveDamage(int damage) {
        health -= damage;
    }

    public abstract void setImage();

    /**
     * Method printSpells
     * Printing the list of spells for choosing one
     */
    public void printSpells() {
        int i = 1;
        for (Spell spell : spells) {
            if (spell.getMana() > mana) {
                continue;
            }
            System.out.print(i + ". ");
            System.out.println(spell);
            i++;
        }
    }

    /**
     * Method generateSpells
     * Randomly generating the list of spells with all the attributes
     */
    public void generateSpells() {
        Random rand = new Random();
        Spell spell;

        spells.clear();

        int damage = rand.nextInt(5,16);
        int manaCost = rand.nextInt(2, 6);
        spell = new Earth(damage, manaCost);
        spells.add(spell);

        damage = rand.nextInt(5,16);
        manaCost = rand.nextInt(2, 6);
        spell = new Fire(damage, manaCost);
        spells.add(spell);

        damage = rand.nextInt(5,16);
        manaCost = rand.nextInt(2, 6);
        spell = new Ice(damage, manaCost);
        spells.add(spell);

        int numOfSpells = rand.nextInt(4);

        for (int i = 0; i < numOfSpells; i++) {
            damage = rand.nextInt(5,16);
            manaCost = rand.nextInt(2, 6);

            // 0 = Ice, 1 = Fire, 2 = Earth
            int type = rand.nextInt(3);

            spell = switch (type) {
                case 0 -> new Ice(damage, manaCost);
                case 1 -> new Fire(damage, manaCost);
                case 2 -> new Earth(damage, manaCost);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
            spells.add(spell);
        }
    }
}