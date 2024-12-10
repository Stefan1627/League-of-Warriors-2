package org.game.spells;

public class Ice extends Spell {
    public Ice(int damage, int mana) {
        super(damage, mana);
    }

    @Override
    public String toString() {
        return "Ice -> damage = " + getDamage() +
                ", mana = " + getMana();
    }
}
