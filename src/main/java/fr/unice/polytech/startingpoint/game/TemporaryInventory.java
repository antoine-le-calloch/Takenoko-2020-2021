package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.*;

class TemporaryInventory {
    private final int INITIAL_STAMINA;
    private int stamina;
    private boolean actionCouldBeDoneTwice=false;
    private Parcel parcel;
    private List<Parcel> parcelList;
    private final Set<ActionType> actionTypeList;
    private  WeatherType weatherType=WeatherType.NO_WEATHER;


    //Normal Constructor
    TemporaryInventory(int stamina){
        INITIAL_STAMINA = stamina;
        this.stamina = INITIAL_STAMINA;
        parcel = null;
        actionTypeList = new HashSet<>();
        parcelList = new ArrayList<>();
    }

    void looseStamina() throws OutOfResourcesException {
        stamina --;
        if (stamina < 0)
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

    public WeatherType getWeatherType() {
        return weatherType;
    }

    void hasPlayedCorrectly() {
        if ( !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.SELECT_PARCEL)) ||
                !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.PLACE_PARCEL)) )
            throw new NoSuchElementException("Player has not played correctly.");
    }

    public boolean isActionCouldBeDoneTwice() {
        return actionCouldBeDoneTwice;
    }

    /**<p>Reset temporary inventory pour un nouveau d'un joueur.</p>
     *
     * @param  weatherType
     *            <b>prend en compte la météo pour set la stamina +1 si sun ou double action si wind </b>
     */

    void reset(WeatherType weatherType) {
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


    boolean contains(ActionType actionType) {
        return actionTypeList.contains(actionType);
    }

    boolean add(ActionType actionType) {
        if (!actionTypeList.contains(actionType) && actionCouldBeDoneTwice){

            if( !(actionType.equals(ActionType.DRAW_PARCELS)
                    || actionType.equals(ActionType.SELECT_PARCEL)
                    || actionType.equals(ActionType.PLACE_PARCEL )) ) {

                actionCouldBeDoneTwice = false;
                return true;
            }
        }
        else if(actionTypeList.contains(ActionType.DRAW_PARCELS)
                && actionTypeList.contains(ActionType.SELECT_PARCEL)
                && actionTypeList.contains(ActionType.PLACE_PARCEL)
                && actionCouldBeDoneTwice
                && actionType.equals(ActionType.DRAW_PARCELS)){

            actionTypeList.remove(ActionType.SELECT_PARCEL);
            actionTypeList.remove(ActionType.PLACE_PARCEL);
            actionCouldBeDoneTwice=false;
            return true;
        }
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
    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

}