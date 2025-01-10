package org.game.entities;

import java.util.Random;

public class Rogue extends Character {
    private final Random random = new Random();

    public Rogue(String name, int currExp, int currLvl) {
        super(name, currExp, currLvl);
    }

    /**
     * Method evolve
     * Incrementing the attributes of the character after incrementing the level
     */
    public void evolve() {
        setStrength(Math.min((getStrength() + 1) * getCurrLvl(), 1000));
        setDexterity(Math.min((getDexterity() + 5) * getCurrLvl(), 1000));
        setCharisma(Math.min((getCharisma() + 1) * getCurrLvl(), 1000));
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
    public void setImage() {
        if (random.nextBoolean()) {
            setImagePath("rogue-male" + random.nextInt(1, 3) + ".png");
        } else {
            setImagePath("rogue-female" + random.nextInt(1, 3) + ".png");
        }
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