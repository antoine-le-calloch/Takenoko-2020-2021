package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;
import fr.unice.polytech.startingpoint.type.WeatherType;


public class IntelligentBot extends Bot {
    
    public IntelligentBot(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    @Override
    public void botPlay(WeatherType weatherType) {
        if (isJudiciousPlayWeather())
            playWeather(weatherType);
        for (int i = gameInteraction.getStamina(); i > 0; i--) {
            if(isJudiciousDrawMission())
                drawMission(bestMissionTypeToDraw());
            else
                playMission(determineBestMissionToDo());
        }
    }

    @Override
    public MissionType bestMissionTypeToDraw() {
        int NB_MAX_MISSION_PARCEL = 2;
        int NB_MISSION_DONE = 2;
        if (gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0
            && (gameInteraction.getInventoryParcelMissions().size() + gameInteraction.getMissionsParcelDone()) < NB_MAX_MISSION_PARCEL)
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