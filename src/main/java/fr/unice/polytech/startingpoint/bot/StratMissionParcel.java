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

public class StratMissionParcel{
    Bot bot;

    /**
     * <p></p>
     *
     * @param bot
     */
    public StratMissionParcel(Bot bot) {
        this.bot = bot;
    }

    public void stratParcel(){
        int cptAction = bot.NB_ACTION;
        if (isJudiciousDrawMission(cptAction)) {
            bot.drawMission(MissionType.PARCEL);
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
        return bot.playerInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0 && cptAction != 0;
    }

    /**
     * @return <b>True if the bot can draw a parcel and haven’t finished a form or still have 2 actions.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousPutParcel(int cptAction){
        if(cptAction != bot.NB_ACTION) {
            for (ParcelMission mission : bot.playerInteraction.getInventoryParcelMissions()) {
                if (bestCoordinatesForMission(mission).size() == 0)
                    return false;
            }
        }
        return bot.playerInteraction.getResourceSize(ResourceType.PARCEL) > 0 && bot.possibleCoordinatesParcel().size()>0 && cptAction != 0;
    }

    /**
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousPutCanal(int cptAction){
        return bot.playerInteraction.getResourceSize(ResourceType.CANAL)  > 0 && bot.possibleCoordinatesCanal().size() > 0 && cptAction != 0;
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
            List<ParcelInformation> parcelInformationList = bot.playerInteraction.drawParcels();
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
                    if(bot.rules.isPlayableParcel(coordinate)) {
                        bot.selectParcel(bestColor);
                        bot.placeParcel(coordinate);
                        break;
                    }

                }
            }
            else {
                bot.selectParcel(parcelInformationList.get(0));
                bot.placeParcel(bot.possibleCoordinatesParcel().get(0));
            }

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> bestCoordsInAllMission(ColorType colorAvailable) {
        List<Coordinate> bestCoords = null;
        int minTurnToEndOneMission = -1;

        for (ParcelMission mission : bot.playerInteraction.getInventoryParcelMissions()) {
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

        for (Coordinate hightCoord : bot.allPlaces()) {
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
            if(coord.isCentral() || (bot.playerInteraction.isPlacedParcel(coord) && !bot.playerInteraction.getPlacedParcelInformation(coord).getColorType().equals(mission.getColor())))
                return null;

            if(!bot.playerInteraction.isPlacedParcel(coord))
                coordNeedeToDoMission.add(coord);

            if(!bot.rules.isPlayableParcel(coord) && !bot.playerInteraction.isPlacedParcel(coord) && coordAroundUse(coord) != null){
                for (Coordinate laidCoord : Coordinate.getInCommonAroundCoordinates(coord,coordAroundUse(coord))) {
                    if(bot.rules.isPlayableParcel(laidCoord)) {
                        coordNeedeToDoMission.add(laidCoord);
                        break;
                    }
                }
            }
            else if(!bot.rules.isPlayableParcel(coord) && !bot.playerInteraction.isPlacedParcel(coord))
                if(coordNotPossible == null)
                    coordNotPossible = coord;
                else
                    return null;
        }

        if(coordNotPossible != null && coordNotPossible.coordinatesAround().stream().filter(coordNeedeToDoMission::contains).count() < 2){
            List<Coordinate>  coordsNeed =  Coordinate.getInCommonAroundCoordinates(coordNotPossible,coordNotPossible.coordinatesAround().stream().filter(coordNeedeToDoMission::contains).collect(Collectors.toList()).get(0));
            if(bot.rules.isPlayableParcel(coordsNeed.get(0)))
                coordNeedeToDoMission.add(coordsNeed.get(0));
            else if(bot.rules.isPlayableParcel(coordsNeed.get(1)))
                coordNeedeToDoMission.add(coordsNeed.get(1));
            else
                return null;
        }

        return new ArrayList<>(coordNeedeToDoMission);
    }

    public Coordinate coordAroundUse(Coordinate coord){
        for (Coordinate coordAround : coord.coordinatesAround()) {
            if(bot.playerInteraction.isPlacedParcel(coordAround))
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
            bot.playerInteraction.drawCanal();
            List<Coordinate> fullForm = coordEndMissionNoIrrigate();
            Coordinate[] bestCoordinatesCanal = null;

            for (Coordinate coordinateForm : fullForm) {
                if (!bot.playerInteraction.isIrrigatedParcel(coordinateForm))
                    bestCoordinatesCanal = getBestCanal(coordinateForm);
            }

            if (bestCoordinatesCanal != null && fullForm.size() != 0) {
                bot.placeCanal(bestCoordinatesCanal);
                return true;
            }

            bot.placeCanal(bot.possibleCoordinatesCanal().get(0));
            return false;
        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Coordinate> coordEndMissionNoIrrigate() {
        for (ParcelMission mission : bot.playerInteraction.getInventoryParcelMissions()) {
            for (Coordinate coordinate : bot.allPlaces()) {
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

        for (Coordinate[] coordinatesCanal : bot.possibleCoordinatesCanal()) {
            if(normMin == -1 || (Coordinate.getNorm(coordinateToIrrigate,coordinatesCanal[0])+Coordinate.getNorm(coordinateToIrrigate,coordinatesCanal[1])) < normMin) {
                normMin = Coordinate.getNorm(coordinateToIrrigate, coordinatesCanal[0]) + Coordinate.getNorm(coordinateToIrrigate, coordinatesCanal[1]);
                bestCanal = coordinatesCanal;
            }
        }
        return bestCanal;
    }






}
