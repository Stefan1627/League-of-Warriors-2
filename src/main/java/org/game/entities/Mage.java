package org.game.entities;

import java.util.Random;

public class Mage extends Character {
    private final Random random = new Random();

    /**
     * Method evolve
     * Incrementing the attributes of the character after incrementing the level
     */
    public void evolve() {
        setStrength((getStrength() + 1) * getCurrLvl());
        setDexterity((getDexterity() + 1)  * getCurrLvl());
        setCharisma((getCharisma() + 5) * getCurrLvl());
    }

    @Override
    public void receiveDamage(int damage) {
        if (getStrength() > 50 && getDexterity() > 50) {
            if (random.nextBoolean()) {
                damage /= 2;
            }
        }

        super.receiveDamage(damage);
    }

    @Override
    public int getDamage() {
        int multiplier = 1;
        if (getCharisma() > 50) {
            if (random.nextBoolean()) {
                multiplier = 2;
            }
        }
        return (getStrength() * 2 + getDexterity() + getCharisma()) * multiplier;
    }
}