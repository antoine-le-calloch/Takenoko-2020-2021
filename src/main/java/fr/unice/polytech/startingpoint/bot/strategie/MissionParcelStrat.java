package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MissionParcelStrat extends Strategie {

    /**@param bot
     */
    public MissionParcelStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(WeatherType weatherType, Mission mission){

        if(isJudiciousPlayWeather())
            playWeather(weatherType);

        if (isJudiciousDrawMission())
            bot.drawMission(MissionType.PARCEL);
        else if (isJudiciousPutCanal())
            putCanal();
        else if(isJudiciousPutParcel()) {
            putParcel();
        }
    }

    public int howManyMoveToDoMission(Mission mission) {
        return 0;
    }

    public boolean isJudiciousPlayWeather(){
        return !bot.getGameInteraction().contains(ActionType.WEATHER);
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousDrawMission(){
        int NB_MISSION_MAX = 5;
        return bot.getGameInteraction().getResourceSize(ResourceType.PARCEL_MISSION) > 0  && !bot.getGameInteraction().contains(ActionType.DRAW_MISSION) && bot.getGameInteraction().getMissionsSize() < NB_MISSION_MAX;
    }

    /**
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutCanal(){
        if(bot.getGameInteraction().getStamina() < 2) {
            for (ParcelMission mission : bot.getGameInteraction().getInventoryParcelMissions()) {
                if (bestCoordinatesForMission(mission).size() == 0)
                    return bot.getGameInteraction().getResourceSize(ResourceType.CANAL)  > 0 && possibleCoordinatesCanal().size() > 0 && !bot.getGameInteraction().contains(ActionType.PLACE_CANAL);
            }
            return false;
        }
        return bot.getGameInteraction().getResourceSize(ResourceType.CANAL)  > 0 && possibleCoordinatesCanal().size() > 0 && !bot.getGameInteraction().contains(ActionType.PLACE_CANAL);
    }

    /**
     * @return <b>True if the bot can draw a parcel and haven’t finished a form or still have 2 actions.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutParcel(){
        return bot.getGameInteraction().getResourceSize(ResourceType.PARCEL) > 0 && !bot.getGameInteraction().contains(ActionType.DRAW_PARCELS);
    }

    public void playWeather(WeatherType weatherType){
        if(weatherType.equals(WeatherType.RAIN))
            stratRain();
        else if(weatherType.equals(WeatherType.THUNDERSTORM))
            stratThunderstorm();
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
            List<ParcelInformation> parcelInformationList = bot.getGameInteraction().drawParcels();
            /*List<ColorType> colorAvailable = new ArrayList<>();
            parcelInformationList.forEach(parcel -> colorAvailable.add(parcel.getColorType()));
            List<Coordinate> bestCoords = bestCoordsInAllMission(colorAvailable);*/

            bot.selectParcel(parcelInformationList.get(0));
            bot.placeParcel(possibleCoordinatesParcel().get(0));
            /*if(bestCoords != null) {
                bot.placeParcel(bestCoords.get(0));
            }
            else{*/
            //}


        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> bestCoordsInAllMission(List<ColorType> colorAvailable) {
        ParcelMission bestMission = null;
        List<Coordinate> bestCoords = null;

        for (ParcelMission mission : bot.getGameInteraction().getInventoryParcelMissions()) {
            List<Coordinate> coordsForMission = bestCoordinatesForMission(mission);
            if ( bestCoords == null || bestCoords.size() > coordsForMission.size() ){
                bestMission = mission;
                bestCoords = coordsForMission;
            }
        }

        if( bestMission!= null && colorAvailable.contains(bestMission.getColorType()) ) {
            ParcelMission finalBestMission = bestMission;
            colorAvailable.removeIf(color -> !color.equals(finalBestMission.getColorType()));
            bestCoords.removeIf(coord -> !boardRules.isPlayableParcel(coord));
            return bestCoords;
        }

        List<Coordinate> OtherCoords = possibleCoordinatesParcel();
        OtherCoords.removeAll(bestCoords);
        return OtherCoords;
    }

    public List<Coordinate[]> bestCoordsOfEachMission() {
        List<Coordinate[]> bestCoordsOfEachMission = new ArrayList<>();
        List<ParcelMission> parcelMissionList;

        for (int i = 0; i < (parcelMissionList = bot.getGameInteraction().getInventoryParcelMissions()).size(); i++) {
            bestCoordsOfEachMission.add(bestCoordinatesForMission(parcelMissionList.get(i)).toArray(Coordinate[]::new));
        }
        return bestCoordsOfEachMission;
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
            if(coord.isCentral() || (bot.getGameInteraction().isPlacedParcel(coord) && !bot.getGameInteraction().getPlacedParcelInformation(coord).getColorType().equals(mission.getColorType())))
                return null;

            if(!bot.getGameInteraction().isPlacedParcel(coord))
                coordNeedToDoMission.add(coord);

            if(!boardRules.isPlayableParcel(coord) && !bot.getGameInteraction().isPlacedParcel(coord) && coordAroundUse(coord) != null){
                for (Coordinate laidCoord : Coordinate.getInCommonAroundCoordinates(coord,coordAroundUse(coord))) {
                    if(boardRules.isPlayableParcel(laidCoord)) {
                        coordNeedToDoMission.add(laidCoord);
                        break;
                    }
                }
            }
            else if(!boardRules.isPlayableParcel(coord) && !bot.getGameInteraction().isPlacedParcel(coord))
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
            if(bot.getGameInteraction().isPlacedParcel(coordAround))
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
            List<Coordinate> fullForm = coordEndMissionNoIrrigate();
            Coordinate[] bestCoordinatesCanal = null;

            for (Coordinate coordinateForm : fullForm)
                if (!bot.getGameInteraction().isIrrigatedParcel(coordinateForm))
                    bestCoordinatesCanal = getBestCanal(coordinateForm);

            if (bestCoordinatesCanal != null && fullForm.size() != 0) {
                bot.getGameInteraction().drawCanal();
                bot.placeCanal(bestCoordinatesCanal);
                return;
            }
            bot.getGameInteraction().drawCanal();
            bot.placeCanal(possibleCoordinatesCanal().get(0));

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> coordEndMissionNoIrrigate() {
        for (ParcelMission mission : bot.getGameInteraction().getInventoryParcelMissions()) {
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