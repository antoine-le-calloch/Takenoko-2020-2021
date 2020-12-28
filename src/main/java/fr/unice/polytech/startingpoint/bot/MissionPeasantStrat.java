package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.PlayerInteraction;
import fr.unice.polytech.startingpoint.game.Rules;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.List;

public class MissionPeasantStrat extends Strategie{

    /**@param bot
     */
    public MissionPeasantStrat(Bot bot) {
        super(bot);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void stratOneTurn(List<ActionType> actionAlreadyPlay){
        if (isJudiciousDrawMission(actionAlreadyPlay)){
            bot.drawMission(MissionType.PEASANT);
            actionAlreadyPlay.add(ActionType.DRAW_MISSION);
        }

        else if (isJudiciousMovePeasant(actionAlreadyPlay)) {
            bot.movePeasant(strategyMovePeasant(possibleCoordinatesPeasant()));
            actionAlreadyPlay.add(ActionType.MOVE_PEASANT);
        }
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousDrawMission(List<ActionType> actionAlreadyPlay){
        return bot.playerInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0 && !actionAlreadyPlay.contains(ActionType.DRAW_MISSION);
    }

    /**
     * @return <b>True if the bot can move the paesant.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousMovePeasant(List<ActionType> actionAlreadyPlay){
        return strategyMovePeasant(possibleCoordinatesPeasant()) != null && !actionAlreadyPlay.contains(ActionType.MOVE_PEASANT);
    }

    /**@param coordinateList
     *            <b>The list of coordinates containing places where we want to move the Peasant.</b>
     * @return <b>Return the first coordinate where the parcel has at least two bamboos.</b>
     */
    public Coordinate strategyMovePeasant(List<Coordinate> coordinateList) {
        for (Coordinate coordinate : coordinateList) {
            if (bot.playerInteraction.getPlacedParcelsNbBamboo(coordinate) > 1) {
                return coordinate;
            }
        }
        return null;
    }
}