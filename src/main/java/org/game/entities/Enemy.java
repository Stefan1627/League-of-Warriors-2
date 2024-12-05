package org.game.entities;

import java.util.Random;

public class Enemy extends Entity {
    private static final Random RANDOM = new Random();
    private int damage;

    public Enemy() {
        damage = RANDOM.nextInt(5) + 1;
        setHealth(RANDOM.nextInt(60, 100));
        setMana(RANDOM.nextInt(5, 10));
        setEarthProof(RANDOM.nextBoolean());
        setFireProof(RANDOM.nextBoolean());
        setIceProof(RANDOM.nextBoolean());
        // de ales spellurile
    }

    @Override
    public void recieveDamage(int dmg) {
        if (RANDOM.nextBoolean()) {
            super.receiveDamage(0);
            return;
        }
        super.receiveDamage(dmg);
    }

    @Override
    public int getDamage() {
        if (RANDOM.nextBoolean()) {
            damage *= 2;
        }
        return damage;
    }
}
