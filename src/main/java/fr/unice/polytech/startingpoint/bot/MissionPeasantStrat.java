package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

public class MissionPeasantStrat extends Strategie{

    /**
     * @param bot
     */
    public MissionPeasantStrat(Bot bot) {
        super(bot);
    }

    /**
     * <p>The actions of the bot during his turn.</p>
     */
    public void stratOneTurn(){
        if (isJudiciousDrawMission()) {
            bot.drawMission(MissionType.PEASANT);
        }
        else if (isJudiciousMovePeasant()) {
            bot.movePeasant(strategyMovePeasant());
        }
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousDrawMission(){
        return bot.gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0 && !bot.gameInteraction.contains(ActionType.DRAW_MISSION);
    }

    /**
     * @return <b>True if the bot can move the paesant.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousMovePeasant(){
        return strategyMovePeasant() != null && !bot.gameInteraction.contains(ActionType.MOVE_PEASANT);
    }

    /**
     *            <b>The list of coordinates containing places where we want to move the Peasant.</b>
     * @return <b>Return the first coordinate where the parcel has at least two bamboos.</b>
     */
    public Coordinate strategyMovePeasant() {
        for(PeasantMission mission : bot.gameInteraction.getInventoryPeasantMissions()) {
            if (!mission.getImprovementType().equals(ImprovementType.WHATEVER)) {
                for (Coordinate coordinate : possibleCoordinatesPeasant()) {
                    if (bot.gameInteraction.getPlacedParcelInformation(coordinate).getColorType().equals(mission.getColorType())) {
                        return coordinate;
                    }
                }
            }
        }
        return null;
    }
}