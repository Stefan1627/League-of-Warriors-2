package org.game.entities;

public class Mage extends Character {
    public void evolve() {
        setStrength((getStrength() + 1) * getCurrLvl());
        setDexterity((getDexterity() + 1)  * getCurrLvl());
        setCharisma((getCharisma() + 5) * getCurrLvl());
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage - getDexterity() / 2 - getStrength() / 5);
    }

    public int getDamage() {
        return getStrength() + getDexterity() / 2 + 2 * getCharisma();
    }
}
