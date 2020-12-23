package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.Rules;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.List;

public class MissionPeasantStrat extends Strategie{

    /**@param bot
     */
    public MissionPeasantStrat(Bot bot, Rules rules) {
        super(bot, rules);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void stratOneTurn() {
        if (bot.playerInteraction.getInventoryMissions().size() < 5 && bot.playerInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
            bot.drawMission(MissionType.PEASANT);

        if (strategyMovePeasant(possibleCoordinatesPeasant()) != null)
            bot.movePeasant(strategyMovePeasant(possibleCoordinatesPeasant()));

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