package org.game.entities;

public class Rogue extends Character {
    public void evolve() {
        super.setStrength(super.getStrength() + 1);
        super.setDexterity(super.getDexterity() + 5);
        super.setCharisma(super.getCharisma() + 1);
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage - super.getCharisma() / 2 - super.getStrength() / 5);
    }

    public int getDamage() {
        return super.getStrength() / 2 + super.getDexterity() * 2 + super.getCharisma();
    }
}
