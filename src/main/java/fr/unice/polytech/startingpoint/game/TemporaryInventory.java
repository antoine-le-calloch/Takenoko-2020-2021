package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class TemporaryInventory {
    private int stamina;
    private Parcel parcel;
    private List<Parcel> parcelList;
    private boolean hasDrawn;
    private boolean hasSelected;
    private boolean hasPlayed;


    TemporaryInventory(int stamina){
        this.stamina = stamina;
        parcel = null;
        parcelList = new ArrayList<>();
        hasDrawn = false;
        hasSelected = false;
        hasPlayed = false;
    }

    void looseStamina() throws OutOfResourcesException {
        stamina --;
        if (stamina < 0){
            throw new OutOfResourcesException("No more stamina.");
        }
    }

    void saveParcels(List<Parcel> parcelList){
        hasDrawn = true;
        this.parcelList = parcelList;
    }

    boolean hasDrawn() {
        return hasDrawn;
    }

    void add(Parcel parcel) {
        hasSelected = true;
        this.parcel = parcel;
    }

    Parcel getParcel() {
        hasPlayed = true;
        return parcel;
    }

    boolean hasPlayedCorrectly() {
        return (hasDrawn == hasSelected) && (hasSelected == hasPlayed);
    }

    List<Parcel> getParcelsSaved() {
        return parcelList;
    }
}
