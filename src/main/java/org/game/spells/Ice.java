package org.game.spells;

public class Ice extends Spell {
    private static final String imagePath = "/spells/ice.png";

    public Ice(int damage, int mana) {
        super(damage, mana);
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Ice -> damage = " + getDamage() +
                ", mana = " + getMana();
    }
}
