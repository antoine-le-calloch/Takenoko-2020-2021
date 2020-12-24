package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ActionType;

import java.util.*;

class TemporaryInventory {
    private final int NUMBER_MIN_OF_STAMINA=0;
    private final int INITIAL_STAMINA=2;
    private int stamina;
    private Parcel parcel;
    private List<Parcel> parcelList;
    private final Set<ActionType> actionTypeList;

    //Normal Constructor
    TemporaryInventory(int stamina){
        this.stamina = stamina;
        parcel = null;
        actionTypeList = new HashSet<>();
        parcelList = new ArrayList<>();
    }

    void looseStamina() throws OutOfResourcesException {
        stamina --;
        if (stamina < NUMBER_MIN_OF_STAMINA)
            throw new OutOfResourcesException("No more stamina.");
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
        if ( !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.SELECT_PARCEL)) ||
                !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.PLACE_PARCEL)) )
            throw new NoSuchElementException("Player has not played correctly.");
    }

    void reset() {
        this.stamina = INITIAL_STAMINA;
        parcel = null;
        actionTypeList.clear();
        parcelList.clear();
    }

    boolean contains(ActionType actionType) {
        return actionTypeList.contains(actionType);
    }

    boolean add(ActionType actionType){
        return actionTypeList.add(actionType);
    }

    void remove(ActionType actionType) {
        actionTypeList.remove(actionType);
    }

    List<Parcel> getParcelsSaved() {
        return parcelList;
    }

    List<ActionType> getActionTypeList() {
        return new ArrayList<>(actionTypeList);
    }

    int getStamina() {
        return stamina;
    }
}