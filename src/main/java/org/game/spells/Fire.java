package org.game.spells;

import org.game.entities.Entity;

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

    @Override
    public void visit(Entity entity) {
        if (entity.isFireProof()) {
            System.out.println("No effect. The entity is fireproof.");
        } else {
            super.visit(entity);
        }
    }
}
