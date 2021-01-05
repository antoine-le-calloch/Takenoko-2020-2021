package fr.unice.polytech.startingpoint.game.playerdata;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.*;

public class TemporaryInventory {
    private final Set<ActionType> actionTypeList;
    private final int INITIAL_STAMINA;
    private int stamina;
    private boolean actionCouldBeDoneTwice;
    private Parcel parcel;
    private List<Parcel> parcelList;
    private WeatherType weatherType;

    public TemporaryInventory(){
        actionTypeList = new HashSet<>();
        INITIAL_STAMINA = 2;
        this.stamina = INITIAL_STAMINA;
        actionCouldBeDoneTwice = false;
        parcel = null;
        parcelList = new ArrayList<>();
        weatherType = WeatherType.NO_WEATHER;
    }

    public void looseStamina() throws OutOfResourcesException {
        stamina --;
        if (stamina < 0)
            throw new OutOfResourcesException("No more stamina.");
    }

    public void saveParcels(List<Parcel> parcelList){
        this.parcelList = parcelList;
    }

    public void saveParcel(Parcel parcel){
        this.parcel = parcel;
    }

    public Parcel getParcel(){
        return parcel;
    }

    public void hasPlayedCorrectly() {
        if ( !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.SELECT_PARCEL)) ||
                !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.PLACE_PARCEL)) )
            throw new NoSuchElementException("Player has not played correctly.");
    }

    public boolean isActionCouldBeDoneTwice() {
        return actionCouldBeDoneTwice;
    }

    public void reset(WeatherType weatherType) {
        this.weatherType=weatherType;
        actionCouldBeDoneTwice=false;
        parcel = null;
        actionTypeList.clear();
        parcelList.clear();
        if(this.weatherType.equals(WeatherType.WIND))
            actionCouldBeDoneTwice=true;
        if(this.weatherType.equals(WeatherType.SUN))
            this.stamina=INITIAL_STAMINA+1;
        else
            this.stamina = INITIAL_STAMINA;

    }

    public boolean contains(ActionType actionType) {
        return actionTypeList.contains(actionType);
    }

    public boolean add(ActionType actionType) {
        if (!actionTypeList.contains(actionType) && actionCouldBeDoneTwice){
            if( !(  actionType.equals(ActionType.DRAW_PARCELS) ||
                    actionType.equals(ActionType.SELECT_PARCEL) ||
                    actionType.equals(ActionType.PLACE_PARCEL) )){
                actionCouldBeDoneTwice = false;
                return true;
            }
        }
        else if(actionTypeList.contains(ActionType.DRAW_PARCELS) &&
                actionTypeList.contains(ActionType.SELECT_PARCEL) &&
                actionTypeList.contains(ActionType.PLACE_PARCEL) &&
                actionType.equals(ActionType.DRAW_PARCELS) &&
                actionCouldBeDoneTwice){
            actionTypeList.remove(ActionType.SELECT_PARCEL);
            actionTypeList.remove(ActionType.PLACE_PARCEL);
            actionCouldBeDoneTwice=false;
            return true;
        }
        return actionTypeList.add(actionType);
    }

    public void remove(ActionType actionType) {
        actionTypeList.remove(actionType);
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public List<Parcel> getParcelsSaved() {
        return parcelList;
    }

    public List<ActionType> getActionTypeList() {
        return new ArrayList<>(actionTypeList);
    }

    public int getStamina() {
        return stamina;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }
}