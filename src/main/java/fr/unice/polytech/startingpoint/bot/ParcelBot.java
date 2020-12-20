package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final int NB_ACTION = 2;

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param playerInteraction
     *            <b>Game object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public ParcelBot(PlayerInteraction playerInteraction, Rules rules) {
        super(playerInteraction, rules);
    }

    /**
     * <p>The actions of the bot during his turn.</p>
     */
    public void botPlay(){
        int cptAction = NB_ACTION;
        if (isJudiciousDrawMission(cptAction)) {
            drawMission(MissionType.PARCEL);
            cptAction--;
        }

        if(isJudiciousPutParcel(cptAction)) {
            putParcel();
            cptAction--;
        }

        if (isJudiciousPutCanal(cptAction)){
            putCanal();
        }
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see PlayerInteraction
     */

    public boolean isJudiciousDrawMission(int cptAction){
        return playerInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0 && cptAction != 0;
    }

    /**
     * @return <b>True if the bot can draw a parcel and haven’t finished a form or still have 2 actions.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousPutParcel(int cptAction){
        if(cptAction != NB_ACTION) {
            for (ParcelMission mission : playerInteraction.getInventoryParcelMissions()) {
                if (bestCoordinatesForMission(mission).size() == 0)
                    return false;
            }
        }
        return playerInteraction.getResourceSize(ResourceType.PARCEL) > 0 && possibleCoordinatesParcel().size()>0 && cptAction != 0;
    }

    /**
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousPutCanal(int cptAction){
        return playerInteraction.getResourceSize(ResourceType.CANAL)  > 0 && possibleCoordinatesCanal().size() > 0 && cptAction != 0;
    }

    /**
     * <p>For each mission, put a parcel to best place to finish it or place a random one.</p>
     *
     * @see PlayerInteraction
     * @see FormType
     * @see ColorType
     */
    //Pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    public void putParcel() {
        try {
            List<ParcelInformation> parcelInformationList = playerInteraction.drawParcels();
            List<Coordinate> bestCoords = new ArrayList<>();
            ParcelInformation bestColor = new ParcelInformation();
            int minTurn = -1;

            for (ParcelInformation parcelInformation : parcelInformationList) {
                if(bestCoordsInAllMission(parcelInformation.getColorType()) != null && (minTurn == -1 || bestCoordsInAllMission(parcelInformation.getColorType()).size() < minTurn)) {
                    bestCoords = bestCoordsInAllMission(parcelInformation.getColorType());
                    bestColor = parcelInformation;
                    minTurn = bestCoords.size();
                }
            }

            if(bestCoords.size() != 0) {
                for (Coordinate coordinate : bestCoords) {
                    if(rules.isPlayableParcel(coordinate)) {
                        selectParcel(bestColor);
                        placeParcel(coordinate);
                        break;
                    }

                }
            }
            else {
                selectParcel(parcelInformationList.get(0));
                placeParcel(possibleCoordinatesParcel().get(0));
            }

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> bestCoordsInAllMission(ColorType colorAvailable) {
        List<Coordinate> bestCoords = null;
        int minTurnToEndOneMission = -1;

        for (ParcelMission mission : playerInteraction.getInventoryParcelMissions()) {
            if (colorAvailable.equals(mission.getColor()) && (minTurnToEndOneMission == -1 || bestCoordinatesForMission(mission).size() < minTurnToEndOneMission)){
                bestCoords = bestCoordinatesForMission(mission);
                minTurnToEndOneMission = bestCoords.size();
            }
        }
        return bestCoords;
    }

    /**
     * <h2>{@link #bestCoordinatesForMission(ParcelMission mission)} :</h2>
     *
     * @param mission
     *            <b>The mission wanted to be completed.</b>
     * @return <b>The best coordinates where a parcel need to be placed to complete the mission.</b>
     *
     * @see Coordinate
     * @see FormType
     * @see ColorType
     * @see PlayerInteraction
     */
    public List<Coordinate> bestCoordinatesForMission(ParcelMission mission){
        List<Coordinate> bestCoordinates = new ArrayList<>();
        int minTurnToEndForm = -1;

        for (Coordinate hightCoord : allPlaces()) {
            List<Coordinate> parcelsToPlaceToDoForm = coordNeedToDoMission(hightCoord, mission);

            if(parcelsToPlaceToDoForm != null && (minTurnToEndForm == -1 || parcelsToPlaceToDoForm.size() < minTurnToEndForm)){
                minTurnToEndForm = parcelsToPlaceToDoForm.size();
                bestCoordinates = parcelsToPlaceToDoForm;
            }
        }
        return bestCoordinates;
    }

    /**
     * <h2>{@link #coordNeedToDoMission(Coordinate hightCoord, ParcelMission mission)} :</h2>
     *
     * @param hightCoord
     *            <b>The higth coordinate of a form.</b>
     * @param mission
     *            <b>The mission wanted to be completed.</b>
     * @return <b>The list of required coordinates where parcels need to be placed to complete the mission.</b>
     *
     * @see Coordinate
     * @see FormType
     * @see ColorType
     * @see PlayerInteraction
     */
    public List<Coordinate> coordNeedToDoMission(Coordinate hightCoord, ParcelMission mission){
        Coordinate coordNotPossible = null;
        Set<Coordinate> coordNeedeToDoMission = new HashSet<>();
        List<Coordinate> form = setForm(hightCoord, mission.getFormType());

        //if(mission.getFormType().equals(FormType.LINE) && hightCoord.equals(new Coordinate(0,-1,1)) && !playerInteraction.isPlacedParcel(new Coordinate(0,-2,2)))
        //    return null;

        for (Coordinate coord : form) {
            if(coord.isCentral() || (playerInteraction.isPlacedParcel(coord) && !playerInteraction.getPlacedParcelsColor(coord).equals(mission.getColor())))
                return null;

            if(!playerInteraction.isPlacedParcel(coord))
                coordNeedeToDoMission.add(coord);

            if(!rules.isPlayableParcel(coord) && !playerInteraction.isPlacedParcel(coord) && coordAroundUse(coord) != null){
                for (Coordinate laidCoord : Coordinate.getInCommonAroundCoordinates(coord,coordAroundUse(coord))) {
                    if(rules.isPlayableParcel(laidCoord)) {
                        coordNeedeToDoMission.add(laidCoord);
                        break;
                    }
                }
            }
            else if(!rules.isPlayableParcel(coord) && !playerInteraction.isPlacedParcel(coord))
                if(coordNotPossible == null)
                    coordNotPossible = coord;
                else
                    return null;
        }

        if(coordNotPossible != null && coordNotPossible.coordinatesAround().stream().filter(coordNeedeToDoMission::contains).count() < 2){
            List<Coordinate>  coordsNeed =  Coordinate.getInCommonAroundCoordinates(coordNotPossible,coordNotPossible.coordinatesAround().stream().filter(coordNeedeToDoMission::contains).collect(Collectors.toList()).get(0));
            if(rules.isPlayableParcel(coordsNeed.get(0)))
                coordNeedeToDoMission.add(coordsNeed.get(0));
            else if(rules.isPlayableParcel(coordsNeed.get(1)))
                coordNeedeToDoMission.add(coordsNeed.get(1));
            else
                return null;
        }

        return new ArrayList<>(coordNeedeToDoMission);
    }

    public Coordinate coordAroundUse(Coordinate coord){
        for (Coordinate coordAround : coord.coordinatesAround()) {
            if(playerInteraction.isPlacedParcel(coordAround))
                return coordAround;
        }
        return null;
    }

    public List<Coordinate> setForm(Coordinate hightCoord, FormType form){
        List<Coordinate> coordForm = new ArrayList<>();
        coordForm.add(hightCoord);
        coordForm.add(new Coordinate(hightCoord,Coordinate.offSets().get(2)));
        if(form.equals(FormType.LINE))
            coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(2)));
        else
            coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(4)));
        return coordForm;
    }

    /**
     * @return <b>True, if a canal can be place and irrigate a parcel, else, it returns false and place a random canal.</b>
     *
     * @see Coordinate
     * @see PlayerInteraction
     */
    public boolean putCanal() {
        try {
            playerInteraction.drawCanal();
            List<Coordinate> fullForm = coordEndMissionNoIrrigate();
            Coordinate[] bestCoordinatesCanal = null;

            for (Coordinate coordinateForm : fullForm) {
                if (!playerInteraction.isIrrigatedParcel(coordinateForm))
                    bestCoordinatesCanal = getBestCanal(coordinateForm);
            }

            if (bestCoordinatesCanal != null && fullForm.size() != 0) {
                placeCanal(bestCoordinatesCanal);
                return true;
            }

            placeCanal(possibleCoordinatesCanal().get(0));
            return false;
        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Coordinate> coordEndMissionNoIrrigate() {
        for (ParcelMission mission : playerInteraction.getInventoryParcelMissions()) {
            for (Coordinate coordinate : allPlaces()) {
                if(coordNeedToDoMission(coordinate,mission) != null && coordNeedToDoMission(coordinate,mission).size() == 0) {
                    return setForm(coordinate, mission.getFormType());
                }
            }
        }
        return new ArrayList<>();
    }

    public Coordinate[] getBestCanal(Coordinate coordinateToIrrigate){
        int normMin = -1;
        Coordinate[] bestCanal = null;

        for (Coordinate[] coordinatesCanal : possibleCoordinatesCanal()) {
            if(normMin == -1 || (Coordinate.getNorm(coordinateToIrrigate,coordinatesCanal[0])+Coordinate.getNorm(coordinateToIrrigate,coordinatesCanal[1])) < normMin) {
                normMin = Coordinate.getNorm(coordinateToIrrigate, coordinatesCanal[0]) + Coordinate.getNorm(coordinateToIrrigate, coordinatesCanal[1]);
                bestCanal = coordinatesCanal;
            }
        }
        return bestCanal;
    }
}