package org.game.spells;

public class Earth extends Spell{
    private static final String imagePath = "/spells/earth.png";

    public Earth(int damage, int mana) {
        super(damage, mana);
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Earth -> damage = " + getDamage() +
                ", mana = " + getMana();
    }
}
