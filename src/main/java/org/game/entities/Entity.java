package org.game.entities;

import lombok.Getter;
import lombok.Setter;
import org.game.game.Battle;
import org.game.spells.Earth;
import org.game.spells.Fire;
import org.game.spells.Ice;
import org.game.spells.Spell;

import java.util.ArrayList;
import java.util.Random;

@Getter @Setter
public abstract class Entity implements Battle {
    public static final int MAX_HEALTH = 100;
    private  int maxMana;
    private ArrayList<Spell> spells;
    private int health;
    private int mana;
    private boolean fireProof;
    private boolean iceProof;
    private boolean earthProof;

    public Entity() {
        spells = new ArrayList<>();
        health = 100;
        mana = 300;
        fireProof = false;
        iceProof = false;
        earthProof = false;
    }

    public void regenerateHealth(int health) {
        this.health = Math.min(health, MAX_HEALTH);
    }

    public void regenerateMana() {
        mana = maxMana;
    }

    public void receiveDamage(int damage) {
        health -= damage;
    }

    public void printSpells() {
        int i = 1;
        for (Spell spell : spells) {
            if (spell.getMana() > mana) {
                continue;
            }
            System.out.print(i + ". ");
            System.out.print(printClass(spell) + " ");
            System.out.println(spell);
            i++;
        }
    }

    public String printClass(Spell spell) {
        if (spell.getClass() == Fire.class) {
            return "Fire";
        } else if (spell.getClass() == Ice.class) {
            return "Ice";
        } else {
            return "Earth";
        }
    }

    public void generateSpells() {
        Random rand = new Random();
        int numOfSpells = rand.nextInt(4) + 3;

        boolean hasIce = false;
        boolean hasFire = false;
        boolean hasEarth = false;

        for (int i = 0; i < numOfSpells; i++) {
            int damage = rand.nextInt(31) + 10; // Damage intre 10 si 40
            int manaCost = rand.nextInt(21) + 5; // Mana cost intre 5 si 25

            Spell spell;
            int type = rand.nextInt(3); // 0 = Ice, 1 = Fire, 2 = Earth

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

        if (spells.size() < 6) {
            if (!hasIce) spells.add(new Ice(rand.nextInt(31) + 10, rand.nextInt(21) + 5));
            if (!hasFire) spells.add(new Fire(rand.nextInt(31) + 10, rand.nextInt(21) + 5));
            if (!hasEarth) spells.add(new Earth(rand.nextInt(31) + 10, rand.nextInt(21) + 5));
        }

    }
}