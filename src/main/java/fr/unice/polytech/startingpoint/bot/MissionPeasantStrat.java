package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.PlayerInteraction;
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
    public void stratOneTurn(){
        if (isJudiciousDrawMission())
            bot.drawMission(MissionType.PEASANT);
        else if (isJudiciousMovePeasant())
            bot.movePeasant(strategyMovePeasant(possibleCoordinatesPeasant()));
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousDrawMission(){
        return bot.playerInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0 && !bot.playerInteraction.contains(ActionType.DRAW_MISSION);
    }

    /**
     * @return <b>True if the bot can move the paesant.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousMovePeasant(){
        return strategyMovePeasant(possibleCoordinatesPeasant()) != null && !bot.playerInteraction.contains(ActionType.MOVE_PEASANT);
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