package org.game.exceptions;

public class NotEnoughManaException extends RuntimeException {
    public NotEnoughManaException(){
        super("Not enough mana for this spell. Choose again!");
    }
}
