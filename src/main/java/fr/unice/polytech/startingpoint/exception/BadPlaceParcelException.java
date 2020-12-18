package fr.unice.polytech.startingpoint.exception;

import fr.unice.polytech.startingpoint.game.Coordinate;

public class BadPlaceParcelException extends Exception {
    public BadPlaceParcelException(Coordinate coordinate){
        super(String.valueOf(coordinate));
    }

    @Override
    public String toString() {
        return "The Parcel can't be place on this coordinate :" + getMessage();
    }
}
