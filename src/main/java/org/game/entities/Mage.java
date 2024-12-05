package org.game.entities;

public class Mage extends Character {
    public void evolve() {
        super.setStrength(super.getStrength() + 1);
        super.setDexterity(super.getDexterity() + 1);
        super.setCharisma(super.getCharisma() + 5);
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage - super.getDexterity() / 2 - super.getStrength() / 5);
    }

    public int getDamage() {
        return super.getStrength() + super.getDexterity() / 2 + 2 * super.getCharisma();
    }
}
