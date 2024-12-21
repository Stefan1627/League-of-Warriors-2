package org.game.entities;

import java.util.Random;

public class Enemy extends Entity {
    private static final Random RANDOM = new Random();
    private final int damage;

    /**
     * Method Enemy
     * Randomly generates an enemy
     */
    public Enemy() {
        setImage();
        damage = RANDOM.nextInt(15, 25);
        setHealth(RANDOM.nextInt(60, 100));
        setMana(RANDOM.nextInt(30, 40));
        setEarthProof(RANDOM.nextBoolean());
        setFireProof(RANDOM.nextBoolean());
        setIceProof(RANDOM.nextBoolean());
    }

    @Override
    public void receiveDamage(int dmg) {
        if (RANDOM.nextBoolean()) {
            super.receiveDamage(0);
            return;
        }
        super.receiveDamage(dmg);
    }

    @Override
    public void setImage() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            setImagePath("enemy-male.png");
        } else {
            setImagePath("enemy-female.png");
        }
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public int dealDamage() {
        int res = damage;
        if (RANDOM.nextBoolean()) {
            res *= 2;
        }
        return res;
    }
}