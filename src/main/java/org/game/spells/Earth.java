package org.game.spells;

import org.game.entities.Entity;

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

    @Override
    public void visit(Entity entity) {
        if (entity.isEarthProof()) {
            System.out.println("No effect. The entity is earthproof.");
        } else {
            super.visit(entity);
        }
    }
}
