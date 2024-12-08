package org.game.entities;

public class Warrior extends Character{

    public void evolve() {
        super.setStrength(super.getStrength() + 5);
        super.setDexterity(super.getDexterity() + 1);
        super.setCharisma(super.getCharisma() + 1);
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage - super.getCharisma() / 2 - super.getDexterity() / 5);
    }

    public int getDamage() {
        return super.getStrength() * 2 + super.getDexterity() + super.getCharisma();
    }
}
