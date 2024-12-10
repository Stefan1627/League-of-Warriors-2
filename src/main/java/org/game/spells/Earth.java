package org.game.spells;

public class Earth extends Spell{
    public Earth(int damage, int mana) {
        super(damage, mana);
    }

    @Override
    public String toString() {
        return "Earth -> damage = " + getDamage() +
                ", mana = " + getMana();
    }
}
