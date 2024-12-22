package org.game.spells;

import org.game.entities.Entity;

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

    @Override
    public void visit(Entity entity) {
        if (entity.isIceProof()) {
            System.out.println("No effect. The entity is iceproof.");
        } else {
            super.visit(entity);
        }
    }
}
