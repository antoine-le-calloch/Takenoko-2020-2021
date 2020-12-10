package fr.unice.polytech.startingpoint.exception;

import fr.unice.polytech.startingpoint.Game.Coordinate;

public class BadPlaceCanalException extends Exception{

    public BadPlaceCanalException(Coordinate coordinate1, Coordinate coordinate2){
        super(coordinate1 + ", " + coordinate2);
    }

    @Override
    public String toString() {
        return "The Canal can't be place on these coordinates : " + getMessage();
    }
}

