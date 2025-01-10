package org.game.entities;

import java.util.Random;

public class Warrior extends Character{
    private final Random random = new Random();

    public Warrior(String name, int currExp, int currLvl) {
        super(name, currExp, currLvl);
    }

    /**
     * Method evolve
     * Incrementing the attributes of the character after incrementing the level
     */
    public void evolve() {
        setStrength(Math.min((getStrength() + 5) * getCurrLvl(), 1000));
        setDexterity(Math.min((getDexterity() + 1) * getCurrLvl(), 1000));
        setCharisma(Math.min((getCharisma() + 1) * getCurrLvl(), 1000));
    }

    @Override
    public void receiveDamage(int damage) {
        if (getCharisma() > 50 && getDexterity() > 50) {
            if (random.nextBoolean()) {
                damage /= 2;
            }
        }

        super.receiveDamage(damage);
    }

    @Override
    public void setImage() {
        if (random.nextBoolean()) {
            setImagePath("warrior-male" + random.nextInt(1, 3) + ".png");
        } else {
            setImagePath("warrior-female" + random.nextInt(1, 3) + ".png");
        }
    }

    @Override
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