package fr.unice.polytech.startingpoint.exception;

import fr.unice.polytech.startingpoint.Game.Coordinate;

public class BadPlaceParcelException extends Exception {
    public BadPlaceParcelException(Coordinate coord){
        super(String.valueOf(coord));
    }

    @Override
    public String toString() {
        return "The Parcel can't be place on this coordinate :" + getMessage();
    }
}
