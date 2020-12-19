package fr.unice.polytech.startingpoint.exception;

public class CantDeleteBambooException extends Exception {
    public CantDeleteBambooException(){
        super();
    }

    @Override
    public String toString() {
        return "You can't take a bamboo from the parcel.";
    }
}
