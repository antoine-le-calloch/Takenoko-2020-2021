package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

import java.util.*;

/**
 * <h1>{@link ParcelBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link ParcelMission} missions.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link #botPlay()} method from the {@link Bot}.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Bot
 * @see PandaBot
 * @see ParcelBot
 * @see PeasantBot
 * @see RandomBot
 * @version 0.5
 */

public class ParcelBot extends Bot {

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param game
     *            <b>Game object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public ParcelBot(Game game, Rules rules) {
        super(game, rules);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay(){
        if (isJudiciousDrawMission())
            drawMission(MissionType.PARCEL);
        if(isJudiciousPutParcel())
            putParcel();
        else if (isJudiciousPutCanal()){
            putCanal();
        }
    }

    /**@return <b>True if the bot can draw a mission.</b>
     */

    public boolean isJudiciousDrawMission(){
        return game.getResourceSize(ResourceType.PARCEL_MISSION) > 0;
    }

    /**@return <b>True if the bot can draw a parcel and haven’t finished a form.</b>
     */
    public boolean isJudiciousPutParcel(){
        for (ParcelMission mission : game.getInventoryParcelMission()) {
            if(bestCoordinatesForForm(mission.getFormType(), mission.getColor()) == null)
                return false;
        }
        return (game.getResourceSize(ResourceType.PARCEL) > 0 && possibleCoordinatesParcel().size()>0);
    }

    /**@return <b>True if the bot can draw a canal and place a canal on the board.</b>
     */
    public boolean isJudiciousPutCanal(){
        return (game.getResourceSize(ResourceType.CANAL) > 0 && possibleCoordinatesCanal().size()>0) ;
    }

    /**<p>For each mission, put a parcel to best place to finish it or place a random one.</p>
     */
    public void putParcel() {
        try {
            ParcelMission mission = game.getInventoryParcelMission().get(0);
            List<ColorType> colorTypeList = game.drawParcels();
            boolean selectedParcel = false;
            for (ColorType colorType : colorTypeList){
                if(colorType.equals(mission.getColor())){
                    selectParcel(colorType);
                    selectedParcel = true;
                }
            }
            if (!selectedParcel){
                Collections.shuffle(colorTypeList);
                selectParcel(colorTypeList.get(0));
                List<Coordinate> possibleCoordinatesParcel = possibleCoordinatesParcel();
                Collections.shuffle(possibleCoordinatesParcel);
                placeParcel(possibleCoordinatesParcel.get(0));
            }
            else {
                placeParcel(bestCoordinatesForForm(mission.getFormType(),mission.getColor()));
            }
        } catch (IllegalAccessException | OutOfResourcesException e) {
            e.printStackTrace();
        }
    }

    /**@param formType
     *            <b>The form wanted to be completed.</b>
     * @param colorType
     *            <b>The color of the form wanted to be completed.</b>
     * @return <b>The best coordinate where a parcel need to be placed to complete the form.</b>
     */
    public Coordinate bestCoordinatesForForm(FormType formType, ColorType colorType){
        Coordinate bestCoordinate = possibleCoordinatesParcel().get(0);
        int minTurnToEndForm = 3;

        for (Coordinate coordinate : allPlaces()) {
            List<Coordinate> parcelToPlaceToDoForm = parcelsToPlaceToDoForm(coordinate, formType, colorType);
            if(parcelToPlaceToDoForm != null) {

                if (parcelToPlaceToDoForm.size() == 0)
                    return null;

                if (parcelToPlaceToDoForm.size() == 1 && rules.isPlayableParcel(parcelToPlaceToDoForm.get(0))) {
                    bestCoordinate = parcelToPlaceToDoForm.get(0);
                    minTurnToEndForm = 1;
                } else if (parcelToPlaceToDoForm.size() == 2 && minTurnToEndForm > 1) {

                    if (rules.isPlayableParcel(parcelToPlaceToDoForm.get(0)))
                        bestCoordinate = parcelToPlaceToDoForm.get(0);

                    else if (rules.isPlayableParcel(parcelToPlaceToDoForm.get(1)))
                        bestCoordinate = parcelToPlaceToDoForm.get(1);
                }
            }
        }
        return bestCoordinate;
    }

    /**@param coordinate
     *            <b>The coordinate of the parcel already placed on the board.</b>
     * @param formType
     *            <b>The form wanted to be completed.</b>
     * @param colorType
     *            <b>The color of the form wanted to be completed.</b>
     * @return <b>The list of required coordinates where parcels need to be placed to complete the form.</b>
     */
    public List<Coordinate> parcelsToPlaceToDoForm(Coordinate coordinate, FormType formType, ColorType colorType){
        List<Coordinate> parcelsToPlaceToDoForm = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if((coordinate.isCentral()) || (game.isPlacedParcel(coordinate) && !game.getPlacedParcelsColor(coordinate).equals(colorType)))
                return null;

            if(formType.equals(FormType.LINE)) {
                if (!game.isPlacedParcel(coordinate))
                    parcelsToPlaceToDoForm.add(coordinate);
                coordinate = new Coordinate(coordinate,Coordinate.offSets().get(2));
            }
            else if(formType.equals(FormType.TRIANGLE)) {
                if(!game.isPlacedParcel(coordinate))
                    parcelsToPlaceToDoForm.add(coordinate);
                coordinate = new Coordinate(coordinate,Coordinate.offSets().get((2+i*2)%6));//
            }
        }
        return parcelsToPlaceToDoForm;
    }

    /**@return <b>True, if a canal can be place and irrigate a parcel, else, it returns false and place a random canal.</b>
     */
    public boolean putCanal() {
        for(Coordinate coordinate : game.getPlacedCoordinates()){
            for(Coordinate[] canal : possibleCoordinatesCanal()){
                if(!game.isIrrigatedParcel(coordinate) && (canal[0].equals(coordinate) || canal[1].equals(coordinate))){
                    placeCanal(canal);
                    return true;
                }
            }
        }
        placeCanal(possibleCoordinatesCanal().get(0));
        return false;
    }
}