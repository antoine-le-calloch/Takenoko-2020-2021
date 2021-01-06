package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
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

    public void stratOneTurn(WeatherType weatherType){

        if(isJudiciousPlayWeather())
            playWeather(weatherType);
        if (isJudiciousDrawMission())
            bot.drawMission(MissionType.PARCEL);
        else if (isJudiciousPutCanal())
            putCanal();
        else if(isJudiciousPutParcel())
            putParcel();
    }

    public boolean isJudiciousPlayWeather(){
        return !bot.gameInteraction.contains(ActionType.WEATHER);
    }

    public void playWeather(WeatherType weatherType){
        if(weatherType.equals(WeatherType.RAIN))
            stratRain();
        else if(weatherType.equals(WeatherType.THUNDERSTORM))
            stratThunderstorm();
        else if(weatherType.equals(WeatherType.QUESTION_MARK))
            stratQuestionMark();
        else if(weatherType.equals(WeatherType.CLOUD))
            stratCloud();
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
        return bot.gameInteraction.getResourceSize(ResourceType.PARCEL) > 0 && possibleCoordinatesParcel().size()>0 && !bot.gameInteraction.contains(ActionType.DRAW_PARCELS);
    }

    /**
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutCanal(){
        if(bot.gameInteraction.getStamina() < 2) {
            for (ParcelMission mission : bot.gameInteraction.getInventoryParcelMissions()) {
                if (bestCoordinatesForMission(mission).size() == 0)
                    return bot.gameInteraction.getResourceSize(ResourceType.CANAL)  > 0 && possibleCoordinatesCanal().size() > 0 && !bot.gameInteraction.contains(ActionType.DRAW_CANAL);
            }
        }
        return false;
    }

    /**
     * <p>For each mission, put a parcel to best place to finish it or place a random one.</p>
     *
     * @see GameInteraction
     * @see FormType
     * @see ColorType
     */
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
                    if(boardRules.isPlayableParcel(coordinate)) {
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

        for (Coordinate coordinate : allPlaces()) {
            List<Coordinate> parcelsToPlaceToDoForm = coordNeedToDoMission(coordinate, mission);

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
     * @param coordinate
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
    public List<Coordinate> coordNeedToDoMission(Coordinate coordinate, ParcelMission mission){
        Coordinate coordNotPossible = null;
        Set<Coordinate> coordNeedToDoMission = new HashSet<>();
        List<Coordinate> form = setForm(coordinate, mission.getFormType());

        for (Coordinate coord : form) {
            if(coord.isCentral() || (bot.gameInteraction.isPlacedParcel(coord) && !bot.gameInteraction.getPlacedParcelInformation(coord).getColorType().equals(mission.getColorType())))
                return null;

            if(!bot.gameInteraction.isPlacedParcel(coord))
                coordNeedToDoMission.add(coord);

            if(!boardRules.isPlayableParcel(coord) && !bot.gameInteraction.isPlacedParcel(coord) && coordAroundUse(coord) != null){
                for (Coordinate laidCoord : Coordinate.getInCommonAroundCoordinates(coord,coordAroundUse(coord))) {
                    if(boardRules.isPlayableParcel(laidCoord)) {
                        coordNeedToDoMission.add(laidCoord);
                        break;
                    }
                }
            }
            else if(!boardRules.isPlayableParcel(coord) && !bot.gameInteraction.isPlacedParcel(coord))
                if(coordNotPossible == null)
                    coordNotPossible = coord;
                else
                    return null;
        }

        if(coordNotPossible != null && coordNotPossible.coordinatesAround().stream().filter(coordNeedToDoMission::contains).count() < 2){
            List<Coordinate>  coordsNeed =  Coordinate.getInCommonAroundCoordinates(coordNotPossible,coordNotPossible.coordinatesAround().stream().filter(coordNeedToDoMission::contains).collect(Collectors.toList()).get(0));
            if(boardRules.isPlayableParcel(coordsNeed.get(0)))
                coordNeedToDoMission.add(coordsNeed.get(0));
            else if(boardRules.isPlayableParcel(coordsNeed.get(1)))
                coordNeedToDoMission.add(coordsNeed.get(1));
            else
                return null;
        }

        return new ArrayList<>(coordNeedToDoMission);
    }

    public Coordinate coordAroundUse(Coordinate coordinate){
        for (Coordinate coordAround : coordinate.coordinatesAround()) {
            if(bot.gameInteraction.isPlacedParcel(coordAround))
                return coordAround;
        }
        return null;
    }

    public List<Coordinate> setForm(Coordinate coordinate, FormType form){
        List<Coordinate> coordForm = new ArrayList<>(Coordinate.coordinatesOfOffsets(coordinate, form.getOffsetsList1()));
        coordForm.addAll(Coordinate.coordinatesOfOffsets(coordinate, form.getOffsetsList2()));
        return coordForm;
    }

    /**
     * @return <b>True, if a canal can be place and irrigate a parcel, else, it returns false and place a random canal.</b>
     *
     * @see Coordinate
     * @see GameInteraction
     */
    public void putCanal() {
        try {
            bot.gameInteraction.drawCanal();
            List<Coordinate> fullForm = coordEndMissionNoIrrigate();
            Coordinate[] bestCoordinatesCanal = null;

            for (Coordinate coordinateForm : fullForm)
                if (!bot.gameInteraction.isIrrigatedParcel(coordinateForm))
                    bestCoordinatesCanal = getBestCanal(coordinateForm);

            if (bestCoordinatesCanal != null && fullForm.size() != 0)
                bot.placeCanal(bestCoordinatesCanal);

            bot.placeCanal(possibleCoordinatesCanal().get(0));
        }
        catch (OutOfResourcesException | RulesViolationException e){
            e.printStackTrace();
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