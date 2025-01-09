package org.game.entities;

public class CharacterFactory {
    public static Character createCharacter(String profession, String name, int currExp, int currLvl) {
        return switch (profession) {
            case "Warrior" -> new Warrior(name, currExp, currLvl);
            case "Mage" -> new Mage(name, currExp, currLvl);
            case "Rogue" -> new Rogue(name, currExp, currLvl);
            default -> throw new IllegalArgumentException("Unknown profession: " + profession);
        };
    }
}
