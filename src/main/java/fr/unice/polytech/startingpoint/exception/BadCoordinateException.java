package fr.unice.polytech.startingpoint.exception;

import fr.unice.polytech.startingpoint.game.Coordinate;

public class BadCoordinateException extends Exception{

    public BadCoordinateException(String message, Coordinate coordinate){
        super(message + coordinate.toString());
    }

    public BadCoordinateException(String message,Coordinate coordinate1, Coordinate coordinate2){
        super(message + coordinate1.toString() + " " + coordinate2.toString());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
