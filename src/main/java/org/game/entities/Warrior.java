package org.game.entities;

public class Warrior extends Character{

    public void evolve() {
        setStrength((getStrength() + 5) * getCurrLvl());
        setDexterity((getDexterity() + 1) * getCurrLvl());
        setCharisma((getCharisma() + 1) * getCurrLvl();
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage - getCharisma() / 2 - getDexterity() / 5);
    }

    public int getDamage() {
        return getStrength() * 2 + getDexterity() + getCharisma();
    }
}
