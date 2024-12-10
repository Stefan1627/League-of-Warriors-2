package org.game.spells;

public class Fire extends Spell {
    public Fire(int damage, int mana) {
        super(damage, mana);
    }

    @Override
    public String toString() {
        return "Fire -> damage = " + getDamage() +
                ", mana = " + getMana();
    }
}
