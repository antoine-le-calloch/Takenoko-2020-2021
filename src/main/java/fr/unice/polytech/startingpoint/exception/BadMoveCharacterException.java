package fr.unice.polytech.startingpoint.exception;

import fr.unice.polytech.startingpoint.Game.Coordinate;

public class BadMoveCharacterException extends Exception {

    public BadMoveCharacterException(Coordinate coord){
        super(String.valueOf(coord));
    }

    @Override
    public String toString() {
        return "The character can't move to this coordinate :" + getMessage();
    }
}
