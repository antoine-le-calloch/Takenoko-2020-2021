package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MissionParcelStrat extends Strategie{

    /**@param bot
     */
    public MissionParcelStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(){
        if (isJudiciousDrawMission())
            bot.drawMission(MissionType.PARCEL);
        else if(isJudiciousPutParcel())
            putParcel();
        else if (isJudiciousPutCanal())
            putCanal();
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousDrawMission(){
        return bot.gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0  && !bot.gameInteraction.contains(ActionType.DRAW_MISSION);
    }

    /**
     * @return <b>True if the bot can draw a parcel and haven’t finished a form or still have 2 actions.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutParcel(){
        if(bot.gameInteraction.getActionTypeList().size() != 0) {
            for (ParcelMission mission : bot.gameInteraction.getInventoryParcelMissions()) {
                if (bestCoordinatesForMission(mission).size() == 0)
                    return false;
            }
        }
        return bot.gameInteraction.getResourceSize(ResourceType.PARCEL) > 0 && possibleCoordinatesParcel().size()>0 && !bot.gameInteraction.contains(ActionType.DRAW_PARCELS);
    }

    /**
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutCanal(){
        return bot.gameInteraction.getResourceSize(ResourceType.CANAL)  > 0 && possibleCoordinatesCanal().size() > 0 && !bot.gameInteraction.contains(ActionType.DRAW_CANAL);
    }

    /**
     * <p>For each mission, put a parcel to best place to finish it or place a random one.</p>
     *
     * @see GameInteraction
     * @see FormType
     * @see ColorType
     */
    //Pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    public void putParcel() {
        try {
            List<ParcelInformation> parcelInformationList = bot.gameInteraction.drawParcels();
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
                        bot.selectParcel(bestColor);
                        bot.placeParcel(coordinate);
                        break;
                    }

                }
            }
            else {
                bot.selectParcel(parcelInformationList.get(0));
                bot.placeParcel(possibleCoordinatesParcel().get(0));
            }

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> bestCoordsInAllMission(ColorType colorAvailable) {
        List<Coordinate> bestCoords = null;
        int minTurnToEndOneMission = -1;

        for (ParcelMission mission : bot.gameInteraction.getInventoryParcelMissions()) {
            if (colorAvailable.equals(mission.getColorType()) && (minTurnToEndOneMission == -1 || bestCoordinatesForMission(mission).size() < minTurnToEndOneMission)){
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
     * @see GameInteraction
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
     * @see GameInteraction
     */
    public List<Coordinate> coordNeedToDoMission(Coordinate hightCoord, ParcelMission mission){
        Coordinate coordNotPossible = null;
        Set<Coordinate> coordNeedeToDoMission = new HashSet<>();
        List<Coordinate> form = setForm(hightCoord, mission.getFormType());

        for (Coordinate coord : form) {
            if(coord.isCentral() || (bot.gameInteraction.isPlacedParcel(coord) && !bot.gameInteraction.getPlacedParcelInformation(coord).getColorType().equals(mission.getColorType())))
                return null;

            if(!bot.gameInteraction.isPlacedParcel(coord))
                coordNeedeToDoMission.add(coord);

            if(!rules.isPlayableParcel(coord) && !bot.gameInteraction.isPlacedParcel(coord) && coordAroundUse(coord) != null){
                for (Coordinate laidCoord : Coordinate.getInCommonAroundCoordinates(coord,coordAroundUse(coord))) {
                    if(rules.isPlayableParcel(laidCoord)) {
                        coordNeedeToDoMission.add(laidCoord);
                        break;
                    }
                }
            }
            else if(!rules.isPlayableParcel(coord) && !bot.gameInteraction.isPlacedParcel(coord))
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
            if(bot.gameInteraction.isPlacedParcel(coordAround))
                return coordAround;
        }
        return null;
    }

    public List<Coordinate> setForm(Coordinate hightCoord, FormType form){
        List<Coordinate> coordForm = new ArrayList<>();
        coordForm.add(hightCoord);
        coordForm.add(new Coordinate(hightCoord,Coordinate.offSets().get(2)));

        switch (form){
            case LINE:
                coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(2)));
                break;
            case TRIANGLE:
                coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(4)));
                break;
            case DIAMOND:
                coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(4)));
                coordForm.add(new Coordinate(coordForm.get(2),Coordinate.offSets().get(2)));
                break;
            case ARC:
                coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(3)));
                break;
        }
        return coordForm;
    }

    /**
     * @return <b>True, if a canal can be place and irrigate a parcel, else, it returns false and place a random canal.</b>
     *
     * @see Coordinate
     * @see GameInteraction
     */
    public boolean putCanal() {
        try {
            bot.gameInteraction.drawCanal();
            List<Coordinate> fullForm = coordEndMissionNoIrrigate();
            Coordinate[] bestCoordinatesCanal = null;

            for (Coordinate coordinateForm : fullForm) {
                if (!bot.gameInteraction.isIrrigatedParcel(coordinateForm))
                    bestCoordinatesCanal = getBestCanal(coordinateForm);
            }

            if (bestCoordinatesCanal != null && fullForm.size() != 0) {
                bot.placeCanal(bestCoordinatesCanal);
                return true;
            }

            bot.placeCanal(possibleCoordinatesCanal().get(0));
            return false;
        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Coordinate> coordEndMissionNoIrrigate() {
        for (ParcelMission mission : bot.gameInteraction.getInventoryParcelMissions()) {
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