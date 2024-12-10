package org.game.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    public void recieveDamage(int dmg) {
        super.receiveDamage(dmg);
    }

    @Override
    public int getDamage() {
        return 0;
    }

    public void wonFight(int exp) {
        regenerateHealth(getHealth() * 2);
        regenerateMana();
        incrementExp(exp);
    }

    public void evolve() {
        System.out.println("Evolving " + getName());
    }

    public void incrementExp(int exp) {
        currExp += exp;
        if (currExp >= 200) {
            currExp -= 200;
            currLvl++;
            evolve();
        }
    }

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
