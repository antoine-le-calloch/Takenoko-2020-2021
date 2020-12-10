package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;

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

    /**
     * <h2>{@link #ParcelBot(Resource, Board)} :</h2>
     *
     * <p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param resource
     *            <b>Resource object.</b>
     * @param board
     *            <b>Board object.</b>
     */
    public ParcelBot(Resource resource, Board board) {
        super(resource, board);
    }

    /**
     * <h2>{@link #botPlay()} :</h2>
     *
     * <p>The actions of the bot during his turn.</p>
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

    /**
     * <h2>{@link #isJudiciousDrawMission()} :</h2>
     *
     * @return <b>True if the bot can draw a mission.</b>
     * @see Resource
     */
    public boolean isJudiciousDrawMission(){
        return resource.getDeckParcelMission().size() > 0;
    }

    /**
     * <h2>{@link #isJudiciousPutParcel()} :</h2>
     *
     * @return <b>True if the bot can draw a parcel and haven’t finished a form.</b>
     * @see Resource
     */
    public boolean isJudiciousPutParcel(){
        for (ParcelMission mission : inventory.getParcelMissions()) {
            if(bestCoordinatesForForm(mission.getFormType(), mission.getColor()) == null)
                return false;
        }
        return (resource.getParcel().size() > 0 && possibleCoordinatesParcel().size()>0);
    }

    /**
     * <h2>{@link #isJudiciousPutCanal()} :</h2>
     *
     * @return <b>True if the bot can draw a canal and place a canal on the board.</b>
     * @see Resource
     */
    public boolean isJudiciousPutCanal(){
        return (resource.getCanal().size() > 0 && possibleCoordinatesCanal().size()>0) ;
    }

    /**
     * <h2>{@link #putParcel()} :</h2>
     *
     * <p>For each mission, put a parcel to best place to finish it or place a random one.</p>
     *
     * @see Resource
     * @see Mission
     * @see FormType
     * @see ColorType
     */
    //Pour chaque mission, pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    public void putParcel() {
        ParcelMission mission = (ParcelMission) getInventory().getMission().get(0);
        FormType formType = mission.getFormType();
        ColorType colorType = mission.getColor();
        List<Parcel> newParcel = resource.drawParcel();
        Parcel parcel = new Parcel(ColorType.NO_COLOR);
        for (Parcel p : newParcel){
            if(p.getColor().equals(colorType)){
                parcel = p;
            }
        }
        if (parcel.getColor() == ColorType.NO_COLOR){
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            placeParcel(list.get(0), newParcel.get(0));
        }
        else {
            placeParcel(bestCoordinatesForForm(formType, colorType),resource.selectParcel(parcel));
        }
    }

    /**
     * <h2>{@link #bestCoordinatesForForm(FormType, ColorType)} :</h2>
     *
     * @param formType
     *            <b>The form wanted to be completed.</b>
     * @param colorType
     *            <b>The color of the form wanted to be completed.</b>
     * @return <b>The best coordinate where a parcel need to be placed to complete the form.</b>
     *
     * @see Coordinate
     * @see FormType
     * @see ColorType
     * @see Board
     * @see Resource
     */
    public Coordinate bestCoordinatesForForm(FormType formType, ColorType colorType){
        Coordinate bestCoordinate = possibleCoordinatesParcel().get(0);
        int minTurnToEndForm = 3;

        for (Coordinate HightCoord : allPlaces()) {
            List<Coordinate> parcelToPlaceToDoForm = parcelsToPlaceToDoForm(HightCoord, formType, colorType);
            if(parcelToPlaceToDoForm != null) {

                if (parcelToPlaceToDoForm.size() == 0)
                    return null;

                if (parcelToPlaceToDoForm.size() == 1 && board.isPlayableParcel(parcelToPlaceToDoForm.get(0))) {
                    bestCoordinate = parcelToPlaceToDoForm.get(0);
                    minTurnToEndForm = 1;
                } else if (parcelToPlaceToDoForm.size() == 2 && minTurnToEndForm > 1) {

                    if (board.isPlayableParcel(parcelToPlaceToDoForm.get(0)))
                        bestCoordinate = parcelToPlaceToDoForm.get(0);

                    else if (board.isPlayableParcel(parcelToPlaceToDoForm.get(1)))
                        bestCoordinate = parcelToPlaceToDoForm.get(1);
                }
            }
        }
        return bestCoordinate;
    }

    /**
     * <h2>{@link #parcelsToPlaceToDoForm(Coordinate, FormType, ColorType)} :</h2>
     *
     * @param coordinate
     *            <b>The coordinate of the parcel already placed on the board.</b>
     * @param formType
     *            <b>The form wanted to be completed.</b>
     * @param colorType
     *            <b>The color of the form wanted to be completed.</b>
     * @return <b>The list of required coordinates where parcels need to be placed to complete the form.</b>
     *
     * @see Coordinate
     * @see FormType
     * @see ColorType
     * @see Board
     * @see Resource
     */
    public List<Coordinate> parcelsToPlaceToDoForm(Coordinate coordinate, FormType formType, ColorType colorType){
        List<Coordinate> parcelsToPlaceToDoForm = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if((coordinate.isCentral()) || (board.isPlacedParcel(coordinate) && !board.getPlacedParcels().get(coordinate).getColor().equals(colorType)))
                return null;

            if(formType.equals(FormType.LINE)) {
                if (!board.isPlacedParcel(coordinate))
                    parcelsToPlaceToDoForm.add(coordinate);
                coordinate = new Coordinate(coordinate,Coordinate.offSets().get(2));
            }
            else if(formType.equals(FormType.TRIANGLE)) {
                if(!board.isPlacedParcel(coordinate))
                    parcelsToPlaceToDoForm.add(coordinate);
                coordinate = new Coordinate(coordinate,Coordinate.offSets().get((2+i*2)%6));//
            }
        }
        return parcelsToPlaceToDoForm;
    }

    /**
     * <h2>{@link #putParcel()} :</h2>
     *
     * @return <b>True, if a canal can be place and irrigate a parcel, else, it returns false and place a random canal.</b>
     *
     * @see Coordinate
     * @see Board
     * @see Resource
     */
    public boolean putCanal() {
        for(Parcel parcel : board.getPlacedParcels().values()){
            for(Coordinate[] canal : possibleCoordinatesCanal()){
                if(!parcel.getIrrigated() && (canal[0].equals(parcel.getCoordinates()) || canal[1].equals(parcel.getCoordinates()))){
                    placeCanal(canal);
                    return true;
                }
            }
        }
        placeCanal(possibleCoordinatesCanal().get(0));
        return false;
    }
}