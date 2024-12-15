package org.game.entities;

import lombok.Getter;
import lombok.Setter;
import org.game.spells.Earth;
import org.game.spells.Fire;
import org.game.spells.Ice;
import org.game.spells.Spell;

import java.util.ArrayList;
import java.util.Random;

@Getter @Setter
public abstract class Entity implements Battle {
    public static final int MAX_HEALTH = 100;
    public static final int MAX_MANA = 100;
    private ArrayList<Spell> spells;
    private int health;
    private int mana;
    private boolean fireProof;
    private boolean iceProof;
    private boolean earthProof;

    public Entity() {
        spells = new ArrayList<>();
        health = MAX_HEALTH;
        mana = MAX_MANA;
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

    /**
     * Method useAbility
     * Verifying if the enemy is imune to the spell, then applying the spell on him
     * @param spell the chosen spell to be used
     * @param enemy the entity on which the spell will be used
     */
    public void useAbility(Spell spell, Entity enemy) {
        System.out.println("Used Ability: " + spell);

        if ((spell.getClass() == Fire.class &&
                (enemy.isFireProof() || enemy.getClass() == Warrior.class))
                || (spell.getClass() == Ice.class &&
                (enemy.isIceProof() || enemy.getClass() == Mage.class))
                || (spell.getClass() == Earth.class &&
                (enemy.isEarthProof() || enemy.getClass() == Rogue.class))) {

            if (enemy.isFireProof() || enemy.isIceProof() || enemy.isEarthProof()) {
                System.out.println("Oops... Your enemy is " +
                        (spell.getClass() == Fire.class ? "fireproof" :
                                spell.getClass() == Ice.class ? "iceproof" : "earthproof"));
            } else {
                System.out.println("Oops... Your enemy got scammed");
            }
            return;
        }

        enemy.receiveDamage(spell.getDamage());

    }
}