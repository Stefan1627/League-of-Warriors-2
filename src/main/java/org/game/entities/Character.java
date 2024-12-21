package org.game.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter @Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "profession"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Warrior.class, name = "Warrior"),
        @JsonSubTypes.Type(value = Mage.class, name = "Mage"),
        @JsonSubTypes.Type(value = Rogue.class, name = "Rogue")
})
public abstract class Character extends Entity{
    @JsonProperty("name")
    private String name;

    @JsonProperty("experience")
    private int currExp;

    @JsonProperty("level")
    private int currLvl;

    private int strength;
    private int dexterity;
    private int charisma;

    private int kills = 0;

    @Override
    public void receiveDamage(int dmg) {
        super.receiveDamage(dmg);
    }

    @Override
    public abstract int getDamage();

    public abstract void evolve();

    /**
     * Method wonFight
     * It regenrates health and mana after a fight
     * + it increments the character xp
     * @param exp the amount of exp to increment(randomly generated)
     */
    public void wonFight(int exp) {
        kills++;
        regenerateHealth(getHealth() * 2);
        regenerateMana();
        incrementExp(exp);
    }

    /**
     * Method incrementExp
     * It increments the character xp and handle the level up case
     * @param exp the amount of exp to increment(randomly generated)
     */
    public void incrementExp(int exp) {
        currExp += exp;
        if (currExp >= 100) {
            currExp -= 100;
            currLvl++;
            evolve();
        }
    }

    /**
     * Method toString
     * generating the string for printing in choosing list
     * @return all details about a character
     */
    @Override
    public String toString() {
        String res = "";
        if (getClass() == Warrior.class) {
            res = "Warrior";
        } else if (getClass() == Mage.class) {
            res = "Mage";
        } else if (getClass() == Rogue.class) {
            res = "Rogue";
        }
        return "Type: " + res + ", name: " + name + ", level: " + currLvl + ", exp: " + currExp;
    }
}