package org.game.spells;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Spell {
    private int damage;
    private int mana;

    public Spell(int damage, int mana) {
        this.damage = damage;
        this.mana = mana;
    }

    @Override
    public String toString() {
        return "damage= " + damage +
                ", mana= " + mana;
    }
}
