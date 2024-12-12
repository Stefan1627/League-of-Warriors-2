package org.game.exceptions;

public class InvalidMoveException extends RuntimeException{
    public InvalidMoveException() {
        super("Invalid move, please choose from what is printed on screen");
    }
}
