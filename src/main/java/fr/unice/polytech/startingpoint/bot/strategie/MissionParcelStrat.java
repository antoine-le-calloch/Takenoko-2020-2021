package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
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

    public void stratOneTurn(Mission mission){
        System.out.println("Parcel Strat");
        ParcelMission parcelMission = (ParcelMission) mission;
        if (isJudiciousPutCanal(parcelMission))
            putCanal(parcelMission);
        else if(isJudiciousPutParcel())
            putParcel(parcelMission);
    }

    /**
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousPutCanal(ParcelMission parcelMission){
        if (bestCoordinatesForMission(parcelMission).size() == 0)
            return bot.getGameInteraction().getResourceSize(ResourceType.CANAL)  > 0 && possibleCoordinatesCanal().size() > 0 && !bot.getGameInteraction().contains(ActionType.PLACE_CANAL);
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
        if(!isAlreadyFinished(parcelMission)){
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
        List<Coordinate> bestCoordinatesForMission = bestCoordinatesForMission(parcelMission);
        int nbMove = 0;
        for (Coordinate coordinate1 : bestCoordinatesForMission){
            if (coordinate1.isNextTo(new Coordinate(0,0,0)))
                nbMove ++ ;
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
            List<ColorType> colorAvailable = new ArrayList<>();
            parcelInformationList.forEach(parcel -> colorAvailable.add(parcel.getColorType()));
            List<Coordinate> bestCoords = bestCoordinatesForMission(parcelmission);

            if(bestCoords.size() != 0) {
                parcelInformationList.removeIf(parcelInfo -> !parcelInfo.getColorType().equals(colorAvailable.get(0)));
                bot.selectParcel(parcelInformationList.get(0));
                bot.placeParcel(bestCoords.get(0));
            }
            else{
                bot.selectParcel(parcelInformationList.get(0));
                bot.placeParcel(possibleCoordinatesParcel().get(0));
            }

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
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
            if(coord.isCentral() || (bot.getGameInteraction().isPlacedParcel(coord) &&
                    !bot.getGameInteraction().getPlacedParcelInformation(coord).getColorType().equals(mission.getColorType())))
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
    public void putCanal(ParcelMission mission) {
        try {
            List<Coordinate> fullForm = coordEndMissionNoIrrigate(mission);
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

    public List<Coordinate> coordEndMissionNoIrrigate(ParcelMission mission) {
        for (Coordinate coordinate : allPlaces()) {
            if(coordNeedToDoMission(coordinate,mission) != null && coordNeedToDoMission(coordinate,mission).size() == 0) {
                return setForm(coordinate, mission.getFormType());
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