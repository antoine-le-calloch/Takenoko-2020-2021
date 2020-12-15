package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class TemporaryInventory {
    private final boolean cheat;
    private int stamina;
    private Parcel parcel;
    private List<Parcel> parcelList;
    private boolean hasDrawn;
    private boolean hasSelected;
    private boolean hasPlayed;

    //Normal Constructor
    TemporaryInventory(int stamina){
        this.stamina = stamina;
        parcel = null;
        parcelList = new ArrayList<>();
        hasDrawn = false;
        hasSelected = false;
        hasPlayed = false;
        cheat = false;
    }

    //Test Constructor
    TemporaryInventory(){
        stamina = 0;
        parcel = null;
        parcelList = new ArrayList<>();
        hasDrawn = false;
        hasSelected = false;
        hasPlayed = false;
        cheat = true;
    }

    void looseStamina() throws OutOfResourcesException {
        stamina --;
        if (cheat)
            stamina ++;
        if (stamina < -1)
            throw new OutOfResourcesException("No more stamina.");
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

    void hasPlayedCorrectly() {
        if (!(hasDrawn == hasSelected) && !(hasSelected == hasPlayed))
            throw new NoSuchElementException("Player has not placed his parcel.");
    }

    List<Parcel> getParcelsSaved() {
        return parcelList;
    }
}
