package org.game.exceptions;

public class InvalidSpellException extends RuntimeException {
    public InvalidSpellException() {
        super("Error! Please select one option from above");
    }
}
