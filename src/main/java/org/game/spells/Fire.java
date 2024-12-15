package org.game.spells;

public class Fire extends Spell {
    private static final String imagePath = "/spells/fire.png";

    public Fire(int damage, int mana) {
        super(damage, mana);
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Fire -> damage = " + getDamage() +
                ", mana = " + getMana();
    }
}
