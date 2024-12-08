package org.game.entities;

public class Rogue extends Character {
    public void evolve() {
        setStrength((getStrength() + 1) * getCurrLvl());
        setDexterity((getDexterity() + 5) * getCurrLvl());
        setCharisma((getCharisma() + 1) * getCurrLvl());
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage - getCharisma() / 2 - getStrength() / 5);
    }

    public int getDamage() {
        return getStrength() / 2 + getDexterity() * 2 + getCharisma();
    }
}
