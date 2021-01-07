package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.bot.strategie.MissionParcelStrat;
import fr.unice.polytech.startingpoint.bot.strategie.MissionPeasantStrat;
import fr.unice.polytech.startingpoint.bot.strategie.RushPandaStrat;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class IntelligentBot extends Bot {
    RushPandaStrat rushPandaStrat = new RushPandaStrat(this);
    MissionParcelStrat stratMissionParcel = new MissionParcelStrat(this);
    MissionPeasantStrat stratMissionPeasant = new MissionPeasantStrat(this);
    
    public IntelligentBot(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    @Override
    public void botPlay(WeatherType weatherType) {
        if (isJudiciousPlayWeather())
            playWeather(weatherType);
        for (int i = gameInteraction.getStamina(); i > 0; i--) {
            if( isJudiciousDrawMission()) {
                drawMission(bestMissionTypeToDraw());
            }
            else {
                Mission mission = determineBestMissionToDo();
                playBestMission(mission);
            }
        }
    }

    @Override
    public MissionType bestMissionTypeToDraw() {
        int NB_MAX_MISSION_PARCEL = 3;
        int NB_MISSION_DONE = 6;
        if (gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0
            && (gameInteraction.getInventoryParcelMissions().size() + gameInteraction.getMissionsParcelDone() < NB_MAX_MISSION_PARCEL))
            return MissionType.PARCEL;
        else if (gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0
                && gameInteraction.getNumberMissionsDone() < NB_MISSION_DONE)
            return MissionType.PEASANT;
        else if (gameInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0)
            return MissionType.PANDA;
        else
            return chooseMissionTypeDrawable();
    }

    MissionType chooseMissionTypeDrawable() {
        if (gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
            return MissionType.PEASANT;
        else if (gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0)
            return MissionType.PARCEL;
        else
            return MissionType.PANDA;
    }

}

    /*
    public Coordinate stratThunderstorm() {

        for(PandaMission pandaMission : bot.getGameInteraction().getInventoryPandaMissions()){

            Coordinate coordinateAllColor = strategyMissionAllColor();
            Coordinate coordinateOneColor = strategyMissionOneColor(pandaMission.getColorType());

            if( coordinateAllColor != null && pandaMission.getColorType().equals(ColorType.ALL_COLOR)){
                bot.getGameInteraction().thunderstormAction( coordinateAllColor );
                return coordinateAllColor;
            }
            else if( coordinateOneColor != null) {
                bot.getGameInteraction().thunderstormAction( coordinateOneColor );
                return coordinateOneColor;

            }
        }
        return null;
    }

    @Override
    public Coordinate stratRain() {
        for(PeasantMission peasantMission:bot.getGameInteraction().getInventoryPeasantMissions()){
            ColorType peasantMissionColor = peasantMission.getColorType();

            List<Coordinate> parcelsIrrigatedSameColorAsMission=bot.getGameInteraction().
                    getPlacedCoordinatesByColor(peasantMissionColor).stream()
                    .filter(bot.getGameInteraction()::isIrrigatedParcel)
                    .collect(Collectors.toList() );

            if(!parcelsIrrigatedSameColorAsMission.isEmpty()) {
                bot.getGameInteraction().rainAction(parcelsIrrigatedSameColorAsMission.get(0));
                return parcelsIrrigatedSameColorAsMission.get(0);
            }
        }
        return null;
    }

    parcel


        public void playWeather(WeatherType weatherType){
        if(weatherType.equals(WeatherType.RAIN))
            stratRain();
        else if(weatherType.equals(WeatherType.THUNDERSTORM))
            stratThunderstorm();
    }

 */
