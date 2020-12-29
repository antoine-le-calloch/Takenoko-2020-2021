package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.PandaMission;
import fr.unice.polytech.startingpoint.game.PlayerInteraction;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

public class RushPandaStrat extends Strategie{

    /**@param bot
     */
    public RushPandaStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(){
        if (isJudiciousDrawMission())
            bot.drawMission(MissionType.PANDA);
        else if (isJudiciousMovePanda())
            bot.movePanda(strategyMovePanda());
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousDrawMission(){
        return bot.playerInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0 && !bot.playerInteraction.contains(ActionType.DRAW_MISSION);
    }

    /**
     * @return <b>True if the bot can move the panda.</b>
     * @see PlayerInteraction
     */
    public boolean isJudiciousMovePanda(){
        return strategyMovePanda() != null && !bot.playerInteraction.contains(ActionType.MOVE_PANDA);
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