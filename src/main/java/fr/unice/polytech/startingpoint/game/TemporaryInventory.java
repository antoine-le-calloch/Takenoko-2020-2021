package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ActionType;

import java.util.*;

class TemporaryInventory {
    private int stamina;
    private Parcel parcel;
    private List<Parcel> parcelList;
    private final boolean cheat;
    private final Set<ActionType> actionTypeList;

    //Normal Constructor
    TemporaryInventory(int stamina){
        this.stamina = stamina;
        parcel = null;
        actionTypeList = new HashSet<>();
        parcelList = new ArrayList<>();
        cheat = false;
    }

    //Test Constructor
    TemporaryInventory(){
        stamina = -1;
        parcel = null;
        actionTypeList = new HashSet<>();
        parcelList = new ArrayList<>();
        cheat = true;
    }

    void looseStamina() throws OutOfResourcesException {
        if (!cheat){
            stamina --;
            if (stamina < 0){
                throw new OutOfResourcesException("No more stamina.");
            }
        }
    }

    void saveParcels(List<Parcel> parcelList){
        this.parcelList = parcelList;
    }

    void saveParcel(Parcel parcel){
        this.parcel = parcel;
    }

    Parcel getParcel(){
        return parcel;
    }

    void hasPlayedCorrectly() {
        if ( !(actionTypeList.contains(ActionType.DrawParcels) == actionTypeList.contains(ActionType.SelectParcel)) ||
                !(actionTypeList.contains(ActionType.DrawParcels) == actionTypeList.contains(ActionType.PlaceParcel)) )
            throw new NoSuchElementException("Player has not played correctly.");
    }

    void reset(int stamina) {
        this.stamina = stamina;
        parcel = null;
        actionTypeList.clear();
        parcelList.clear();
    }

    boolean contains(ActionType actionType) {
        if (!cheat)
            return actionTypeList.contains(actionType);
        return true;
    }

    boolean add(ActionType actionType){
        if (!cheat)
            return actionTypeList.add(actionType);
        return true;
    }

    void remove(ActionType actionType) {
        if (!cheat)
            actionTypeList.remove(actionType);
    }

    List<Parcel> getParcelsSaved() {
        return parcelList;
    }

    int getStamina() {
        return stamina;
    }
}
