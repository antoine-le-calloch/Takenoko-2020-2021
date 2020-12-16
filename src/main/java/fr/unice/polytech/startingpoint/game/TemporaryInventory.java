package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class TemporaryInventory {
    private int stamina;
    private Parcel parcel;
    private List<Parcel> parcelList;
    private final boolean[] hasPlayedCorrectly;

    //Normal Constructor
    TemporaryInventory(int stamina){
        this.stamina = stamina;
        parcel = null;
        parcelList = new ArrayList<>();
        hasPlayedCorrectly = new boolean[]{false,false,false,false};
    }

    //Test Constructor
    TemporaryInventory(){
        stamina = 0;
        parcel = null;
        parcelList = new ArrayList<>();
        hasPlayedCorrectly = new boolean[]{false,false,false,true};
    }

    void looseStamina() throws OutOfResourcesException {
        stamina --;
        if (hasPlayedCorrectly[3])
            stamina ++;
        if (stamina < 0)
            throw new OutOfResourcesException("No more stamina.");
    }

    void saveParcels(List<Parcel> parcelList){
        hasPlayedCorrectly[0] = true;
        this.parcelList = parcelList;
    }

    void add(Parcel parcel) {
        hasPlayedCorrectly[1] = true;
        this.parcel = parcel;
    }

    Parcel getParcel() {
        hasPlayedCorrectly[2] = true;
        return parcel;
    }

    boolean hasAlreadyDrawn() {
        return hasPlayedCorrectly[0];
    }

    void hasPlayedCorrectly() {
        if (!(hasPlayedCorrectly[0] == hasPlayedCorrectly[1]) && !(hasPlayedCorrectly[0] == hasPlayedCorrectly[2]))
            throw new NoSuchElementException("Player has not placed his parcel.");
    }

    List<Parcel> getParcelsSaved() {
        return parcelList;
    }
}
