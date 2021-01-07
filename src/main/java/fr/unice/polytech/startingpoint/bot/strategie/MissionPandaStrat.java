package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.List;
import java.util.stream.Collectors;

public class MissionPandaStrat extends Strategie {

    public MissionPandaStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(Mission mission){
        if (isJudiciousMovePanda(mission.getColorType()))
            bot.movePanda(strategyMovePanda(mission.getColorType()));
        else if (isJudiciousMovePeasant())
            bot.movePeasant(strategyMovePeasant(mission.getColorType()));
        else if (isJudiciousPlaceParcel())
            strategyPlaceParcel(mission.getColorType());
        else if (isJudiciousPlaceCanal())
            strategyPlaceCanal();
        else if (!bot.getGameInteraction().contains(ActionType.MOVE_PANDA) && !possibleCoordinatesPanda().isEmpty())
            bot.movePanda(possibleCoordinatesPanda().get(0));
    }

    /**<b><u>IS JUDICIOUS METHODS</u></b>
     */

    public boolean isJudiciousMovePanda(ColorType colorTypeMission){
        return strategyMovePanda(colorTypeMission) != null && !bot.getGameInteraction().contains(ActionType.MOVE_PANDA);
    }

    public boolean isJudiciousMovePeasant(){
        return !bot.getGameInteraction().contains(ActionType.MOVE_PEASANT) && !possibleCoordinatesPeasant().isEmpty();
    }

    public boolean isJudiciousPlaceParcel() {
        return !bot.getGameInteraction().contains(ActionType.DRAW_PARCELS) && (bot.getGameInteraction().getResourceSize(ResourceType.PARCEL) > 0) ;
    }

    boolean isJudiciousPlaceCanal() {
        return !bot.getGameInteraction().contains(ActionType.DRAW_CANAL) && !bot.getGameInteraction().contains(ActionType.PLACE_CANAL) && (bot.getGameInteraction().getResourceSize(ResourceType.CANAL) > 0) ;
    }

    /**<b><u>STRATEGIES METHODS</b>
     */

    public Coordinate strategyMovePanda(ColorType colorTypeMission) {
        if (colorTypeMission.equals(ColorType.ALL_COLOR))
            return strategyMissionAllColor();
        else
            return strategyMissionOneColor(colorTypeMission);
    }

    public Coordinate strategyMissionOneColor(ColorType colorTypeMission) {
        for (Coordinate coordinate : possibleCoordinatesPanda()) {
            if ( bot.getGameInteraction().getPlacedParcelsNbBamboo(coordinate) > 0 &&
                    !bot.getGameInteraction().getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE) &&
                    bot.getGameInteraction().getPlacedParcelInformation(coordinate).getColorType().equals(colorTypeMission) )
                return coordinate;
        }
        return null;
    }

    public Coordinate strategyMissionAllColor(){
        for (Coordinate coordinate : possibleCoordinatesPanda()) {
            if ( bot.getGameInteraction().getPlacedParcelsNbBamboo(coordinate) > 0 &&
                    !bot.getGameInteraction().getPlacedParcelInformation(coordinate).getColorType().equals(ColorType.NO_COLOR) &&
                    !bot.getGameInteraction().getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE) &&
                    bot.getGameInteraction().getInventoryBamboo()[bot.getGameInteraction().getPlacedParcelInformation(coordinate).getColorType().ordinal()] == 0 )
                return coordinate;
        }
        return null;
    }

    public Coordinate strategyMovePeasant(ColorType colorTypeMission) {
        for (Coordinate coordinate : possibleCoordinatesPeasant()) {
            if (bot.getGameInteraction().getPlacedParcelInformation(coordinate).getColorType().equals(colorTypeMission)) {
                return coordinate;
            }
        }
        return possibleCoordinatesPeasant().get(0);
    }

    public void strategyPlaceCanal() {
        bot.getGameInteraction().drawCanal();
        if(!possibleCoordinatesCanal().isEmpty()){
            Coordinate[] coordinates = possibleCoordinatesCanal().get(0);
            bot.getGameInteraction().placeCanal(coordinates[0],coordinates[1]);
        }
    }

    public void strategyPlaceParcel(ColorType colorType) {
        try {
            List<ParcelInformation> parcelInformationList = bot.getGameInteraction().drawParcels();
            if (parcelInformationList.stream().map(ParcelInformation::getColorType).collect(Collectors.toList()).contains(colorType)){
                Coordinate coordinate = null;
                for (Coordinate c : possibleCoordinatesParcel()){
                    if (bot.getGameInteraction().getRules().isMovableCharacter(CharacterType.PANDA,c))
                        coordinate = c;
                }
                if (coordinate == null)
                    coordinate = possibleCoordinatesParcel().get(0);
                bot.selectParcel(parcelInformationList.stream().filter(parcelInformation -> parcelInformation.getColorType().equals(colorType)).collect(Collectors.toList()).get(0));
                bot.placeParcel(coordinate);
            }
            else {
                bot.selectParcel(parcelInformationList.get(0));
                bot.placeParcel(possibleCoordinatesParcel().get(0));
            }

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**<b><u>NUMBER OF MOVES TO DO THE MISSION METHODS</b>
     */

    public int howManyMoveToDoMission(Mission mission) {
        if (!isAlreadyFinished(mission) &&
                isJudiciousMovePanda(mission.getColorType()) &&
                        isJudiciousMovePeasant() &&
                        !bot.getGameInteraction().contains(ActionType.MOVE_PANDA) && !possibleCoordinatesPanda().isEmpty()){
            if (!isFinishedInOneTurn(mission)){
                if (mission.getColorType().equals(ColorType.ALL_COLOR)){
                    if (notExistGoodMovableParcel(ColorType.GREEN) && notExistGoodMovableParcel(ColorType.YELLOW) && notExistGoodMovableParcel(ColorType.RED))
                        return -1;
                    return nbMoveAllColor();
                }
                else{
                    if (notExistGoodMovableParcel(mission.getColorType()))
                        return -1;
                    return nbMoveOneColor(mission.getColorType());
                }
            }
            return 1;
        }
        return -1;
    }

    boolean isAlreadyFinished(Mission mission) {
        if (mission.getColorType().equals(ColorType.ALL_COLOR)){
            for (int nbBamboo : bot.getGameInteraction().getInventoryBamboo()){
                if (nbBamboo == 0)
                    return false;
            }
            return true;
        }
        else
            return bot.getGameInteraction().getInventoryBamboo()[mission.getColorType().ordinal()] >= 2;
    }

    boolean isFinishedInOneTurn(Mission mission) {
        if (strategyMovePanda(mission.getColorType()) != null){
            if (mission.getColorType() == ColorType.ALL_COLOR){
                int nbColorInInventory = 0;
                for (int i = 0; i < bot.getGameInteraction().getInventoryBamboo().length ; i++) {
                    if (bot.getGameInteraction().getInventoryBamboo()[i] >= 1)
                        nbColorInInventory ++;
                    else if ( ColorType.values()[i].equals(bot.getGameInteraction().getPlacedParcelInformation(strategyMovePanda(mission.getColorType())).getColorType()) )
                        nbColorInInventory ++;
                }
                return nbColorInInventory == bot.getGameInteraction().getInventoryBamboo().length;
            }
            else
                return  bot.getGameInteraction().getInventoryBamboo()[mission.getColorType().ordinal()] == 1 &&
                        bot.getGameInteraction().getPlacedParcelsNbBamboo(strategyMovePanda(mission.getColorType())) >= 1;
        }
        return false;
    }

    boolean notExistGoodMovableParcel(ColorType colorTypeMission){
        int nbEnclosure = 0;
        List<Coordinate> coordinates = bot.getGameInteraction().getPlacedCoordinatesByColor(colorTypeMission);
        for (Coordinate coordinate : coordinates){
            if (!bot.getGameInteraction().getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE))
                nbEnclosure++;
        }
        return nbEnclosure == 0;
    }

    int nbMoveOneColor(ColorType colorTypeMission){
        if (strategyMovePanda(colorTypeMission) != null){
            if (bot.getGameInteraction().getInventoryBamboo()[colorTypeMission.ordinal()] == 1)
                return 1;
            return 3;
        }
        else if(bot.getGameInteraction().getInventoryBamboo()[colorTypeMission.ordinal()] == 1)
            return 2;
        return 4;
    }

    int nbMoveAllColor(){
        int nbBamboos = 0;
        for (int i = 0; i < bot.getGameInteraction().getInventoryBamboo().length; i++) {
            if (bot.getGameInteraction().getInventoryBamboo()[i] == 0)
                if (strategyMovePanda(ColorType.values()[i]) == null)
                    nbBamboos ++;
                nbBamboos ++;
        }
        return nbBamboos;
    }
}