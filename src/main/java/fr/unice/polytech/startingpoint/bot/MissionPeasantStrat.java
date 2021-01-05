package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.List;
import java.util.stream.Collectors;

public class MissionPeasantStrat extends Strategie{

    /**
     * @param bot
     */
    public MissionPeasantStrat(Bot bot) {
        super(bot);
    }

    /**
     * <p>The actions of the bot during his turn.</p>
     * @param weatherType
     */
    public void stratOneTurn(WeatherType weatherType){

        if(isJudiciousPlayWeather())
            playWeather(weatherType);

        if (isJudiciousDrawMission()) {
            bot.drawMission(MissionType.PEASANT);
        }
        else if (isJudiciousMovePeasant()) {
            bot.movePeasant(strategyMovePeasant());
        }
    }


    public boolean isJudiciousPlayWeather(){
        if(!bot.gameInteraction.contains(ActionType.WEATHER)){
            return true;
        }
        return false;
    }

    public void playWeather(WeatherType weatherType){
        if(weatherType.equals(WeatherType.RAIN))
            stratRain();
        else if(weatherType.equals(WeatherType.THUNDERSTORM))
            stratThunderstorm();
    }



    @Override
    public Coordinate stratRain() {
        for(PeasantMission peasantMission:bot.gameInteraction.getInventoryPeasantMissions()){
            ColorType peasantMissionColor=peasantMission.getColorType();

            List<Coordinate> parcelsIrrigatedSameColorAsMission=bot.gameInteraction.
                    getPlacedCoordinatesByColor(peasantMissionColor).stream()
                    .filter(bot.gameInteraction::isIrrigatedParcel)
                    .collect(Collectors.toList() );

            if(!parcelsIrrigatedSameColorAsMission.isEmpty()) {
                bot.gameInteraction.rainAction(parcelsIrrigatedSameColorAsMission.get(0));
                return parcelsIrrigatedSameColorAsMission.get(0);
            }
        }
        return null;


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