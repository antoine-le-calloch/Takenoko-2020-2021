package fr.unice.polytech.startingpoint.exception;

import fr.unice.polytech.startingpoint.game.Coordinate;

public class CantDeleteBambooException extends Exception {
    public CantDeleteBambooException(Coordinate coordinate){
        super(String.valueOf(coordinate));
    }

    @Override
    public String toString() {
        return "You can't take a bamboo from the parcel on this coordinate :" + getMessage();
    }
}
