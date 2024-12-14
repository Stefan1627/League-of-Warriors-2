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
    private static final int MAX_MANA = 100;
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
        int numOfSpells = rand.nextInt(4) + 3;

        boolean hasIce = false;
        boolean hasFire = false;
        boolean hasEarth = false;

        spells.clear();

        for (int i = 0; i < numOfSpells; i++) {
            int damage = rand.nextInt(5,16);
            int manaCost = rand.nextInt(2, 6);

            Spell spell;

            // 0 = Ice, 1 = Fire, 2 = Earth
            int type = rand.nextInt(3);

            switch (type) {
                case 0:
                    spell = new Ice(damage, manaCost);
                    hasIce = true;
                    break;
                case 1:
                    spell = new Fire(damage, manaCost);
                    hasFire = true;
                    break;
                case 2:
                    spell = new Earth(damage, manaCost);
                    hasEarth = true;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }

            spells.add(spell);
        }

        // verifying that the list of spells contains at least one spell of each type
        if (spells.size() < 6) {
            if (!hasIce) spells.add(new Ice(rand.nextInt(31) + 10, rand.nextInt(21) + 5));
            if (!hasFire) spells.add(new Fire(rand.nextInt(31) + 10, rand.nextInt(21) + 5));
            if (!hasEarth) spells.add(new Earth(rand.nextInt(31) + 10, rand.nextInt(21) + 5));
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