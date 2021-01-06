package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.List;
import java.util.stream.Collectors;

public class RushPandaStrat extends Strategie{

    /**@param bot
     */
    public RushPandaStrat(Bot bot) {
        super(bot);
    }

    public void stratOneTurn(WeatherType weatherType){
        if(isJudiciousPlayWeather())
            playWeather(weatherType);
        if (isJudiciousDrawMission())
            bot.drawMission(MissionType.PANDA);
        else if (isJudiciousMovePanda())
            bot.movePanda(strategyMovePanda());
    }


    public boolean isJudiciousPlayWeather(){
        if(!bot.gameInteraction.contains(ActionType.WEATHER)){
            return true;
        }
        return false;
    }

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousDrawMission(){
        int NB_MISSION_MAX = 5;
        return bot.gameInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0 && !bot.gameInteraction.contains(ActionType.DRAW_MISSION) && bot.gameInteraction.getInventoryParcelMissions().size() <= NB_MISSION_MAX;
    }

    /**
     * @return <b>True if the bot can move the panda.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousMovePanda(){
        return strategyMovePanda() != null && !bot.gameInteraction.contains(ActionType.MOVE_PANDA);
    }


    public Coordinate strategyMovePanda() {
        for (PandaMission pandaMission : bot.gameInteraction.getInventoryPandaMissions() ) {
            if (pandaMission.getColorType().equals(ColorType.ALL_COLOR))
                return strategyMissionAllColor();
            else
                return strategyMissionOneColor(pandaMission.getColorType());
        }
        return null;
    }


    public void playWeather(WeatherType weatherType){
        if(weatherType.equals(WeatherType.RAIN))
            stratRain();
        else if(weatherType.equals(WeatherType.THUNDERSTORM))
            stratThunderstorm();
    }

    @Override
    public Coordinate stratThunderstorm() {

        for(PandaMission pandaMission:bot.gameInteraction.getInventoryPandaMissions()){
            ColorType colorMissionPanda=pandaMission.getColorType();

            if(colorMissionPanda.equals(ColorType.ALL_COLOR) && strategyMissionAllColor()!=null){
                Coordinate coordinate=strategyMissionAllColor();
                bot.gameInteraction.thunderstromAction(strategyMissionAllColor());
                return coordinate;

            }

            else if( strategyMissionOneColor(colorMissionPanda)!=null) {
                Coordinate coordinate=strategyMissionOneColor(colorMissionPanda);
                bot.gameInteraction.thunderstromAction(coordinate);
                return coordinate;

            }
        }
        return null;
    }

    /** @return <b>Return the first coordinate where the parcel has at least one bamboo and the same color
     * as the list of Panda mission</b>
     */
    public Coordinate strategyMissionOneColor(ColorType colorType) {
        for (Coordinate coordinate : possibleCoordinatesPanda()) {
            if (bot.gameInteraction.getPlacedParcelsNbBamboo(coordinate) > 0
                    && !bot.gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE)) {
                if (bot.gameInteraction.getPlacedParcelInformation(coordinate).getColorType().equals(colorType))
                    return coordinate;
            }
        }
        return null;
    }

    public Coordinate strategyMissionAllColor(){
        for (Coordinate coordinate : possibleCoordinatesPanda()) {
            if (bot.gameInteraction.getPlacedParcelsNbBamboo(coordinate) > 0
                    && !bot.gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE)) {
                if (bot.gameInteraction.getInventoryBamboo()[bot.gameInteraction.getPlacedParcelInformation(coordinate).getColorType().ordinal()] == 0)
                    return coordinate;
            }
        }
        return null;
    }
}


