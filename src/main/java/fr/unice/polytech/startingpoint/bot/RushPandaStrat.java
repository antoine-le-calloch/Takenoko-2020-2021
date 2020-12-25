package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.PandaMission;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

public class RushPandaStrat extends Strategie{

    /**@param bot
     */
    public RushPandaStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(){
        if (bot.playerInteraction.getInventoryMissions().size() < 5 && bot.playerInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0)
            bot.drawMission(MissionType.PANDA);
        if (strategyMovePanda() != null)
            bot.movePanda(strategyMovePanda());
    }

    /** @return <b>Return the first coordinate where the parcel has at least one bamboo and the same color
     * as the list of Panda mission</b>
     */
    public Coordinate strategyMovePanda() {
        for (Coordinate coordinate : possibleCoordinatesPanda()) {
            if (bot.playerInteraction.getPlacedParcelsNbBamboo(coordinate) > 0) {
                for (PandaMission pandaMission : bot.playerInteraction.getInventoryPandaMissions() ) {
                    if (bot.playerInteraction.getPlacedParcelInformation(coordinate).getColorType().equals(pandaMission.getColor()))
                        return coordinate;
                }
            }
        }
        return null;
    }
}