package org.game.entities;

import java.util.Random;

public class Rogue extends Character {
    private final Random random = new Random();

    public void evolve() {
        setStrength((getStrength() + 1) * getCurrLvl());
        setDexterity((getDexterity() + 5) * getCurrLvl());
        setCharisma((getCharisma() + 1) * getCurrLvl());
    }

    @Override
    public void receiveDamage(int damage) {
        if (getCharisma() > 50 && getStrength() > 50) {
            if (random.nextBoolean()) {
                damage /= 2;
            }
        }

        super.receiveDamage(damage);
    }

    @Override
    public int getDamage() {
        int multiplier = 1;
        if (getDexterity() > 50) {
            if (random.nextBoolean()) {
                multiplier = 2;
            }
        }
        return (getStrength() * 2 + getDexterity() + getCharisma()) * multiplier;
    }
}
