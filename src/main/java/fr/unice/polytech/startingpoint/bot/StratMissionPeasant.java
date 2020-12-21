package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.List;

public class StratMissionPeasant {
    Bot bot;

    /**
     * <p></p>
     *
     * @param bot
     */
    public StratMissionPeasant(Bot bot) {
        this.bot = bot;
    }


    /**<p>The actions of the bot during his turn.</p>
     */
    public void stratPeasant() {
        if (bot.playerInteraction.getInventoryMissions().size() < 5 && bot.playerInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
            bot.drawMission(MissionType.PEASANT);

        if (strategyMovePeasant(bot.possibleCoordinatesPeasant()) != null)
            bot.movePeasant(strategyMovePeasant(bot.possibleCoordinatesPeasant()));

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
