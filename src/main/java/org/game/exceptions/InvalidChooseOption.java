package org.game.exceptions;

public class InvalidChooseOption extends RuntimeException {
    public InvalidChooseOption(int upperLimit) {
        super("Invalid input. Please enter a number between 1 and " + upperLimit + ".");
    }
}
