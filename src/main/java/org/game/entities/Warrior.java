package org.game.entities;

import java.util.Random;

public class Warrior extends Character{
    private final Random random = new Random();

    public void evolve() {
        setStrength((getStrength() + 5) * getCurrLvl());
        setDexterity((getDexterity() + 1) * getCurrLvl());
        setCharisma((getCharisma() + 1) * getCurrLvl());
    }

    public void receiveDamage(int damage) {
        if (getCharisma() > 50 && getDexterity() > 50) {
            if (random.nextBoolean()) {
                damage /= 2;
            }
        }

        super.receiveDamage(damage);
    }

    public int getDamage() {
        int multiplier = 1;
        if (getStrength() > 50) {
            if (random.nextBoolean()) {
                multiplier = 2;
            }
        }
        return (getStrength() * 2 + getDexterity() + getCharisma()) * multiplier;
    }
}
