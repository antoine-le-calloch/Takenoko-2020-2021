package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.Rules;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.List;

public class RushPandaStrat extends Strategie{

    /**@param bot
     */
    public RushPandaStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(){
        if (bot.playerInteraction.getInventoryMissions().size() < 5 && bot.playerInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0)
            bot.drawMission(MissionType.PANDA);
        if (strategyMovePanda(possibleCoordinatesPanda()) != null)
            bot.movePanda(strategyMovePanda(possibleCoordinatesPanda()));
    }

    /**@param coordinateList
     *            <b>The list of coordinates containing places where we want to move the Panda.</b>
     * @return <b>Return the first coordinate where the parcel has at least one bamboo.</b>
     */
    public Coordinate strategyMovePanda(List<Coordinate> coordinateList) {
        for (Coordinate coordinate : coordinateList) {
            if (bot.playerInteraction.getPlacedParcelsNbBamboo(coordinate) > 0) {
                return coordinate;
            }
        }
        return null;
    }
}