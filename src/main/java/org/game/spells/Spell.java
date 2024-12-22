package org.game.spells;

import lombok.Getter;
import lombok.Setter;
import org.game.entities.Entity;

@Getter @Setter
public abstract class Spell implements Visitor<Entity> {
    private int damage;
    private int mana;

    public Spell(int damage, int mana) {
        this.damage = damage;
        this.mana = mana;
    }

    public abstract String getImagePath();

    @Override
    public abstract String toString();

    @Override
    public void visit(Entity entity) {
        if (entity.getHealth() <= 0) {
            System.out.println("Entity is already defeated.");
            return;
        }

        // Default behavior: apply damage
        System.out.println("Used Ability: " + this);
        entity.receiveDamage(damage);
    }
}
