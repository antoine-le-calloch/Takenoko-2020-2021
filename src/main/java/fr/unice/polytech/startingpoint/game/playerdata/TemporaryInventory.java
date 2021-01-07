package fr.unice.polytech.startingpoint.game.playerdata;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.*;


/**
 * Inventaire temporaire d'un joueur qui permet de contrôler ses actions lors d'un round
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


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


    /**
     * <p>The player loose an ability to do an action </p>
     *
     */
    public void looseStamina() throws OutOfResourcesException {
        stamina --;
        if (stamina < 0)
            throw new OutOfResourcesException("No more stamina.");
    }

    /**
     * <p>The player save 3 parcels between the moment he has drawn and he has selected one parcel </p>
     *
     */
    public void saveParcels(List<Parcel> parcelList){
        this.parcelList = parcelList;
    }

    /**
     * <p>The player save the parcel between the moment he has selected and he placed his parcel </p>
     */
    public void saveParcel(Parcel parcel){
        this.parcel = parcel;
    }

    public Parcel getParcel(){
        return parcel;
    }



    /**
     * <p>Check if the bot did the actions in the good order and in the good way during a round</p>
     */

    public void hasPlayedCorrectly() {
        if ( !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.SELECT_PARCEL)) ||
                !(actionTypeList.contains(ActionType.DRAW_PARCELS) == actionTypeList.contains(ActionType.PLACE_PARCEL)) )
            throw new NoSuchElementException("Player has not played correctly.");
    }


    /**
     * <p>Check if an action could be done twice during the same round</p>
     *
     */
    public boolean isActionCouldBeDoneTwice() {
        return actionCouldBeDoneTwice;
    }


    /**
     * <p>Reset the temporary inventory of a player with a weather given</p>
     *
     * @param weatherType
     *
     *          <p>change the weather during a round</p>
     */

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



    /**
     * <p>Add an action in the list of actions done by a player by the help of the rules</p>
     */
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


    /**
     * <p>Remove an action of the list of actions </p>
     */
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
    
    /**
     * <p>set the weather type </p>
     */
    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }
}