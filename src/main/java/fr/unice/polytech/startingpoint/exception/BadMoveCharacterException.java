package fr.unice.polytech.startingpoint.exception;

import fr.unice.polytech.startingpoint.game.Coordinate;

public class BadMoveCharacterException extends Exception {
    public BadMoveCharacterException(Coordinate coordinate){
        super(String.valueOf(coordinate));
    }

    @Override
    public String toString() {
        return "The character can't move to this coordinate :" + getMessage();
    }
}
