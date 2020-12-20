package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.*;
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
    private final int NB_ACTION = 2;
    private final int NB_TURN_MAX = 7;

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

    /**
     * <h2>{@link #botPlay()} :</h2>
     *
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
     * <h2>{@link #isJudiciousDrawMission(int cptAction)} :</h2>
     *
     * @return <b>True if the bot can draw a mission.</b>
     * @see Game
     */

    public boolean isJudiciousDrawMission(int cptAction){
        return game.getResourceSize(ResourceType.PARCEL_MISSION) > 0 && cptAction != 0;
    }

    /**
     * <h2>{@link #isJudiciousPutParcel(int cptAction)} :</h2>
     *
     * @return <b>True if the bot can draw a parcel and haven’t finished a form or still have 2 actions.</b>
     * @see Game
     */
    public boolean isJudiciousPutParcel(int cptAction){
        if(cptAction != NB_ACTION) {
            for (ParcelMission mission : game.getInventoryParcelMission()) {
                if (bestCoordinatesForMission(mission).size() == 0)
                    return false;
            }
        }
        return game.getResourceSize(ResourceType.PARCEL) > 0 && possibleCoordinatesParcel().size()>0 && cptAction != 0;
    }

    /**
     * <h2>{@link #isJudiciousPutCanal(int cptAction)} :</h2>
     *
     * @return <b>True if the bot can draw a canal and place a canal on the game.</b>
     * @see Game
     */
    public boolean isJudiciousPutCanal(int cptAction){
        return game.getResourceSize(ResourceType.CANAL)  > 0 && possibleCoordinatesCanal().size() > 0 && cptAction != 0;
    }

    /**
     * <h2>{@link #putParcel()} :</h2>
     *
     * <p>For each mission, put a parcel to best place to finish it or place a random one.</p>
     *
     * @see Game
     * @see FormType
     * @see ColorType
     */
    //Pour chaque mission, pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    public void putParcel() {
        try {
            List<ColorType> colorsAvailable = game.drawParcels();
            List<Coordinate> bestCoords = new ArrayList<>();
            ColorType bestColor = ColorType.NO_COLOR;
            int minTurn = NB_TURN_MAX;

            for (ColorType color : colorsAvailable) {
                if(BestCoordsInAllMission(color) != null && BestCoordsInAllMission(color).size() < minTurn) {
                    bestCoords = BestCoordsInAllMission(color);
                    bestColor = color;
                    minTurn = bestCoords.size();
                }
            }

            if(bestCoords.size() != 0) {
                for (Coordinate coord : bestCoords) {
                    if(rules.isPlayableParcel(coord)) {
                        selectParcel(bestColor);
                        placeParcel(coord);
                        break;
                    }

                }
            }
            else {
                selectParcel(colorsAvailable.get(0));
                placeParcel(possibleCoordinatesParcel().get(0));
            }

        } catch (IllegalAccessException | OutOfResourcesException e) {
            e.printStackTrace();
        }
    }

    public List<Coordinate> BestCoordsInAllMission(ColorType colorAvailable) {
        List<Coordinate> bestCoords = null;
        int minTurnToEndOneMission = NB_TURN_MAX;

        for (ParcelMission mission : game.getInventoryParcelMission()) {
            if (colorAvailable.equals(mission.getColor()) && bestCoordinatesForMission(mission).size() < minTurnToEndOneMission){
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
     * @see Game
     */
    public List<Coordinate> bestCoordinatesForMission(ParcelMission mission){
        List<Coordinate> bestCoordinates = new ArrayList<>();
        int minTurnToEndForm = NB_TURN_MAX;

        for (Coordinate hightCoord : allPlaces()) {
            List<Coordinate> parcelsToPlaceToDoForm = coordNeedToDoMission(hightCoord, mission);

            if(parcelsToPlaceToDoForm != null && parcelsToPlaceToDoForm.size() < minTurnToEndForm){
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
     * @see Game
     */
    public List<Coordinate> coordNeedToDoMission(Coordinate hightCoord, ParcelMission mission){
        Coordinate bestCoordNeed = new Coordinate();
        Set<Coordinate> coordNeedeToDoMission = new HashSet<>();
        List<Coordinate> form = setForm(hightCoord, mission.getFormType());

        if(mission.getFormType().equals(FormType.LINE) && hightCoord.equals(new Coordinate(0,-1,1)) && !game.isPlacedParcel(new Coordinate(0,-2,2)))
            return null;

        for (Coordinate coord : form) {
            if(coord.isCentral() || (game.isPlacedParcel(coord) && !game.getPlacedParcelsColor(coord).equals(mission.getColor())))
                return null;

            if(!game.isPlacedParcel(coord))
                coordNeedeToDoMission.add(coord);

            if(!rules.isPlayableParcel(coord) && !game.isPlacedParcel(coord) && coordAroundUse(coord) != null){
                for (Coordinate laidCoord : Coordinate.getInCommonAroundCoordinates(coord,coordAroundUse(coord))) {
                    if(rules.isPlayableParcel(laidCoord)) {
                        coordNeedeToDoMission.add(laidCoord);
                        break;
                    }
                }
            }
            else if(!rules.isPlayableParcel(coord) && !game.isPlacedParcel(coord))
                if(rules.isPlayableParcel(bestCoordNeed = Coordinate.getInCommonAroundCoordinates(coord,form.get(1)).get(0)))
                    coordNeedeToDoMission.add(bestCoordNeed);
                else
                    coordNeedeToDoMission.add(Coordinate.getInCommonAroundCoordinates(coord,form.get(1)).get(1));
        }
        return new ArrayList<>(coordNeedeToDoMission);
    }

    public Coordinate coordAroundUse(Coordinate coord){
        for (Coordinate coordAround : coord.coordinatesAround()) {
            if(game.isPlacedParcel(coordAround))
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
     * <h2>{@link #putParcel()} :</h2>
     *
     * @return <b>True, if a canal can be place and irrigate a parcel, else, it returns false and place a random canal.</b>
     *
     * @see Coordinate
     * @see Game
     */
    public boolean putCanal() {
        try {
            game.drawCanal();
            List<Coordinate> fullForm = findFullFormInMission();
            Coordinate[] bestCoordCanal = null;

            for (Coordinate coordForm : fullForm) {
                if (!game.isIrrigatedParcel(coordForm))
                    bestCoordCanal = getBestCanal(coordForm);
            }

            if (bestCoordCanal != null && fullForm.size() != 0) {
                placeCanal(bestCoordCanal);
                return true;
            }

            placeCanal(possibleCoordinatesCanal().get(0));
            return false;
        } catch (OutOfResourcesException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Coordinate> findFullFormInMission() {
        for (ParcelMission mission : game.getInventoryParcelMission()) {
            for (Coordinate coord : allPlaces()) {
                if(coordNeedToDoMission(coord,mission) != null && coordNeedToDoMission(coord,mission).size() == 0) {
                    return setForm(coord, mission.getFormType());
                }
            }
        }
        return new ArrayList<>();
    }

    public Coordinate[] getBestCanal(Coordinate coordToIrrigate){
        int normMin = Coordinate.getNorm(coordToIrrigate,possibleCoordinatesCanal().get(0)[0])+Coordinate.getNorm(coordToIrrigate,possibleCoordinatesCanal().get(0)[1]);
        Coordinate[] bestCanal = possibleCoordinatesCanal().get(0);

        for (Coordinate[] coordCanal : possibleCoordinatesCanal()) {
            if(Coordinate.getNorm(coordToIrrigate,coordCanal[0])+Coordinate.getNorm(coordToIrrigate,coordCanal[1]) < normMin) {
                normMin = Coordinate.getNorm(coordToIrrigate, coordCanal[0]) + Coordinate.getNorm(coordToIrrigate, coordCanal[1]);
                bestCanal = coordCanal;
            }
        }
        return bestCanal;
    }
}