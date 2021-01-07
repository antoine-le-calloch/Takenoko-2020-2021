package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionParcelStrat extends Strategie {
    /**@param bot
     */
    public MissionParcelStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(Mission mission){
        ParcelMission parcelMission = (ParcelMission) mission;
        if (isJudiciousPutCanal(parcelMission))
            putCanal(parcelMission);
        else if(isJudiciousPutParcel())
            putParcel(parcelMission);
        else if (!bot.getGameInteraction().contains(ActionType.MOVE_PANDA) && !possibleCoordinatesPanda().isEmpty())
            bot.movePanda(possibleCoordinatesPanda().get(0));
        else if (!bot.getGameInteraction().contains(ActionType.MOVE_PEASANT) && !possibleCoordinatesPeasant().isEmpty())
            bot.movePeasant(possibleCoordinatesPeasant().get(0));
    }

    /**
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutCanal(ParcelMission parcelMission){
        if (bestCoordinatesForMission(parcelMission).size() == 0)
            return bot.getGameInteraction().getResourceSize(ResourceType.CANAL) > 0 &&
                    possibleCoordinatesCanal().size() > 0 &&
                    !bot.getGameInteraction().contains(ActionType.PLACE_CANAL);
        return false;
    }

    /**
     * @return <b>True if the bot can draw a parcel and haven’t finished a form or still have 2 actions.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutParcel(){
        return bot.getGameInteraction().getResourceSize(ResourceType.PARCEL) > 0 && !bot.getGameInteraction().contains(ActionType.DRAW_PARCELS);
    }

    public int howManyMoveToDoMission(Mission mission) {
        ParcelMission parcelMission = (ParcelMission) mission;
        if(!isAlreadyFinished(parcelMission) &&
                isJudiciousPutParcel() &&
                        isJudiciousPutCanal((ParcelMission) mission)){
            if (coordEndMissionNoIrrigate(parcelMission).size() > bot.getGameInteraction().getResourceSize(ResourceType.PARCEL))
                return -1;
            else if (isFinishedInOneTurn(parcelMission))
                return 1;
            return nbMoveParcel(parcelMission);
        }
        return -1;
    }

    private boolean isFinishedInOneTurn(ParcelMission parcelMission) {
        for (Coordinate coordinate : bot.getGameInteraction().getPlacedCoordinates()){
            List<Coordinate> parcelForm = setForm(coordinate, parcelMission.getFormType());
            List<Coordinate> coordinateNotPlaced = new ArrayList<>();
            List<Coordinate> coordinateNotIrrigated = new ArrayList<>();
            for (Coordinate coordinate1 : parcelForm){
                if (!bot.getGameInteraction().isIrrigatedParcel(coordinate1))
                    coordinateNotPlaced.add(coordinate1);
                if (bot.getGameInteraction().isPlacedParcel(coordinate1) && !bot.getGameInteraction().isIrrigatedParcel(coordinate1))
                    coordinateNotIrrigated.add(coordinate1);
            }
            if (coordinateNotPlaced.size() == 1 && coordinateNotPlaced.get(0).isNextTo(new Coordinate(0,0,0)))
                return true;
            if (coordinateNotPlaced.size() == 0 && coordinateNotIrrigated.size() == 1
                    && getBestCanal(coordinateNotIrrigated.get(0)) != null
                    && (getBestCanal(coordinateNotIrrigated.get(0))[0] == coordinateNotIrrigated.get(0)
                        ||  getBestCanal(coordinateNotIrrigated.get(0))[1] == coordinateNotIrrigated.get(0)))
                return true;
        }
        return false;
    }

    public boolean isAlreadyFinished(ParcelMission parcelMission) {
        for (Coordinate coordinate : bot.getGameInteraction().getPlacedCoordinates()){
            List<Coordinate> parcelForm = setForm(coordinate, parcelMission.getFormType());
            int cpt = 0;
            for (Coordinate coordinate1 : parcelForm){
                if (bot.getGameInteraction().isIrrigatedParcel(coordinate1))
                    cpt++;
            }
            if (cpt == parcelForm.size())
                return true;
        }
        return false;
    }

    private int nbMoveParcel(ParcelMission parcelMission) {
        Map<Coordinate,Boolean> bestCoordinatesForMission = bestCoordinatesForMission(parcelMission);
        int nbMove = 0;
        for (Coordinate coordinate1 : bestCoordinatesForMission.keySet()){
            if (coordinate1.isNextTo(new Coordinate(0,0,0)))
                nbMove ++;
            else
                nbMove += 2;
        }
        return nbMove;
    }

    /**
     * <p>For each mission, put a parcel to best place to finish it or place a random one.</p>
     *
     * @see GameInteraction
     * @see FormType
     * @see ColorType
     */

    public void putParcel(ParcelMission parcelmission) {
        try {
            List<ParcelInformation> parcelInformationList = bot.getGameInteraction().drawParcels();
            List<Coordinate> bestCoords = coordsToPut(parcelInformationList, parcelmission);

            bot.selectParcel(parcelInformationList.get(0));
            bot.placeParcel(bestCoords.get(0));

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> coordsToPut(List<ParcelInformation> parcelInformationList, ParcelMission parcelmission) {
        Map<Coordinate, Boolean> bestCoords = bestCoordinatesForMission(parcelmission);
        List<Coordinate> coordsNeeded= new ArrayList<>();
        List<Coordinate> coordsUsed= new ArrayList<>();
        ColorType NeededColor = parcelmission.getColorType();

        bestCoords.forEach((coord, Needed) -> {
            if(boardRules.isPlayableParcel(coord))
                if(Needed && boardRules.isPlayableParcel(coord))
                    coordsNeeded.add(coord);
                else
                    coordsUsed.add(coord);
        });

        if(parcelInformationList.stream().anyMatch(parcelInfo -> parcelInfo.getColorType().equals(NeededColor)) && coordsNeeded.size() > 0) {
            parcelInformationList.removeIf(parcelInfo -> !parcelInfo.getColorType().equals(NeededColor));
            return coordsNeeded;
        }

        if(coordsUsed.size() > 0) {
            return coordsUsed;
        }

        List<Coordinate> OtherCoords = possibleCoordinatesParcel();
        OtherCoords.removeAll(bestCoords.keySet());
        return OtherCoords;
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
    public Map<Coordinate, Boolean> bestCoordinatesForMission(ParcelMission mission){
        Map<Coordinate, Boolean> bestCoordinates = new HashMap<>();
        int minTurnToEndForm = -1;

        for (Coordinate coordinate : allPlaces()) {
            Map<Coordinate, Boolean> parcelsToPlaceToDoForm = coordNeedToDoMission(coordinate, mission);

            if(parcelsToPlaceToDoForm != null && parcelsToPlaceToDoForm.size() > 0 && (minTurnToEndForm == -1 || parcelsToPlaceToDoForm.size() < minTurnToEndForm)){
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
    public Map<Coordinate, Boolean> coordNeedToDoMission(Coordinate coordinate, ParcelMission mission){
        List<Coordinate> distantCoord = new ArrayList<>();
        Map<Coordinate, Boolean> coordToDoMission = new HashMap<>();
        List<Coordinate> form = setForm(coordinate, mission.getFormType());

        if(form.contains(new Coordinate(0,0,0)) || form.stream().anyMatch(coord -> bot.getGameInteraction().isPlacedParcel(coord) && !bot.getGameInteraction().getPlacedParcelInformation(coord).getColorType().equals(mission.getColorType())))
            return null;

        form.removeIf(bot.getGameInteraction()::isPlacedParcel);

        for (Coordinate coord : form) {
            coordToDoMission.put(coord,true);
            if (!boardRules.isPlayableParcel(coord) && coordAroundUse(coord) != null) {
                List<Coordinate> laidCoord = Coordinate.getInCommonAroundCoordinates(coord, coordAroundUse(coord));
                if (boardRules.isPlayableParcel(laidCoord.get(0)))
                    coordToDoMission.put(laidCoord.get(0), false);
                else if (boardRules.isPlayableParcel(laidCoord.get(1)))
                    coordToDoMission.put(laidCoord.get(1), false);
                else
                    return null;
            } else if (!boardRules.isPlayableParcel(coord))
                distantCoord.add(coord);
        }

        for (Coordinate coord : distantCoord)
            treatDistantCoord(coord,coordToDoMission);

        return new HashMap<>(coordToDoMission);
    }

    public Map<Coordinate, Boolean> treatDistantCoord (Coordinate distantCoord, Map<Coordinate, Boolean> coordToDoMission){
        if(distantCoord.coordinatesAround().stream().filter(coordToDoMission.keySet()::contains).count() < 2){
            List<Coordinate>  coordsNeed =  Coordinate.getInCommonAroundCoordinates(distantCoord,distantCoord.coordinatesAround().stream().filter(coordToDoMission.keySet()::contains).collect(Collectors.toList()).get(0));
            if(boardRules.isPlayableParcel(coordsNeed.get(0)))
                coordToDoMission.put(coordsNeed.get(0),false);
            else if(boardRules.isPlayableParcel(coordsNeed.get(1)))
                coordToDoMission.put(coordsNeed.get(1),false);
            else if(coordAroundUse(coordsNeed.get(0)) != null)
                coordToDoMission.put(coordsNeed.get(0),false);
            else if(coordAroundUse(coordsNeed.get(1)) != null)
                coordToDoMission.put(coordsNeed.get(1),false);
            else {
                coordToDoMission.put(coordsNeed.get(0), false);
                coordToDoMission.put(coordsNeed.get(1), false);
            }
        }
        return coordToDoMission;
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
    public void putCanal(ParcelMission mission) {
        try {
            List<Coordinate> fullForm = coordEndMissionNoIrrigate(mission);
            Coordinate[] bestCoordinatesCanal = null;

            for (Coordinate coordinateForm : fullForm)
                if (!bot.getGameInteraction().isIrrigatedParcel(coordinateForm))
                    bestCoordinatesCanal = getBestCanal(coordinateForm);

            if (bestCoordinatesCanal != null && fullForm.size() != 0) {
                bot.drawCanal();
                bot.placeCanal(bestCoordinatesCanal);
                return;
            }
            bot.drawCanal();
            bot.placeCanal(possibleCoordinatesCanal().get(0));

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> coordEndMissionNoIrrigate(ParcelMission mission) {
        for (Coordinate coordinate : allPlaces()) {
            if(coordNeedToDoMission(coordinate,mission) != null && coordNeedToDoMission(coordinate,mission).size() == 0) {
                return setForm(coordinate, mission.getFormType());
            }
        }
        return new ArrayList<>();
    }
}