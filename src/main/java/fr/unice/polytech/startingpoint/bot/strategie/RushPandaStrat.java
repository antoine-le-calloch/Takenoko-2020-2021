package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.List;

public class RushPandaStrat extends Strategie {

    /**@param bot
     */
    public RushPandaStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(Mission mission){
        System.out.println("Panda Strat");
        if (isJudiciousMovePanda(mission.getColorType()))
            bot.movePanda(strategyMovePanda(mission.getColorType()));
        else if (isJudiciousMovePeasant())
            bot.movePeasant(strategyMovePeasant(mission.getColorType()));
        else if (!bot.getGameInteraction().contains(ActionType.MOVE_PANDA) && !possibleCoordinatesPanda().isEmpty())
            bot.movePanda(possibleCoordinatesPanda().get(0));
    }

    public int howManyMoveToDoMission(Mission mission) {
        if (!isAlreadyFinished(mission) ||
                (!isJudiciousMovePanda(mission.getColorType())) &&
                        isJudiciousMovePeasant() &&
                        !(!bot.getGameInteraction().contains(ActionType.MOVE_PANDA) && !possibleCoordinatesPanda().isEmpty())){
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

    public boolean isAlreadyFinished(Mission mission) {
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

    public boolean isFinishedInOneTurn(Mission mission) {
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

    public boolean notExistGoodMovableParcel(ColorType colorTypeMission){
        int nbEnclosure = 0;
        List<Coordinate> coordinates = bot.getGameInteraction().getPlacedCoordinatesByColor(colorTypeMission);
        for (Coordinate coordinate : coordinates){
            if (!bot.getGameInteraction().getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE))
                nbEnclosure++;
        }
        return nbEnclosure == 0;
    }

    public int nbMoveOneColor(ColorType colorTypeMission){
        if (strategyMovePanda(colorTypeMission) != null){
            if (bot.getGameInteraction().getInventoryBamboo()[colorTypeMission.ordinal()] == 1)
                return 1;
            return 4;
        }
        else if(bot.getGameInteraction().getInventoryBamboo()[colorTypeMission.ordinal()] == 1)
            return 3;
        return 5;
    }

    public int nbMoveAllColor(){
        int nbBamboos = 0;
        for (int i = 0; i < bot.getGameInteraction().getInventoryBamboo().length; i++) {
            if (bot.getGameInteraction().getInventoryBamboo()[i] == 0)
                if (strategyMovePanda(ColorType.values()[i]) == null)
                    nbBamboos ++;
                nbBamboos ++;
        }
        return nbBamboos;
    }

    /**
     * @return <b>True if the bot can move the panda.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousMovePanda(ColorType colorTypeMission){
        return strategyMovePanda(colorTypeMission) != null && !bot.getGameInteraction().contains(ActionType.MOVE_PANDA);
    }

    public boolean isJudiciousMovePeasant(){
        return !bot.getGameInteraction().contains(ActionType.MOVE_PEASANT) && !possibleCoordinatesPeasant().isEmpty();
    }

    public Coordinate strategyMovePanda(ColorType colorTypeMission) {
        if (colorTypeMission.equals(ColorType.ALL_COLOR))
            return strategyMissionAllColor();
        else
            return strategyMissionOneColor(colorTypeMission);
    }

    /** @return <b>Return the first coordinate where the parcel has at least one bamboo and the same color
     * as the list of Panda mission</b>
     */
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
}