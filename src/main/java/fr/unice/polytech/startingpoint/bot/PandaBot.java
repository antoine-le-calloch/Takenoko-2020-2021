package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;
import fr.unice.polytech.startingpoint.type.WeatherType;

/**
 * <h1>{@link PandaBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link PandaMission} missions.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link Bot#botPlay(WeatherType)} method from the {@link Bot}.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Bot
 * @see PandaBot
 * @see ParcelBot
 * @see PeasantBot
 * @see RandomBot
 * @version 0.5
 */

public class PandaBot extends Bot {
    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param gameInteraction
     *            <b>Game object.</b>
     */
    public PandaBot(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    /**<p>The actions of the bot during his turn.</p>
     * @param weatherType
     */
    @Override
    public void botPlay(WeatherType weatherType) {
        if (isJudiciousPlayWeather())
            playWeather(weatherType);
        for (int i = gameInteraction.getStamina(); i > 0; i--) {
            if( isJudiciousDrawMission())
                drawMission(bestMissionTypeToDraw());
            else
                playBestMission(determineBestMissionToDo());
        }
    }

    @Override
    public MissionType bestMissionTypeToDraw() {
        int NB_MAX_MISSION_PARCEL = 2;
        if (gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0
                && (gameInteraction.getInventoryParcelMissions().size() + gameInteraction.getMissionsParcelDone() < NB_MAX_MISSION_PARCEL))
            return MissionType.PARCEL;
        else if (gameInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0)
            return MissionType.PANDA;
        else
            return chooseMissionTypeDrawable();
    }

    MissionType chooseMissionTypeDrawable() {
        if (gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0)
            return MissionType.PARCEL;
        else if (gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
            return MissionType.PEASANT;
        else
            return MissionType.PANDA;
    }

    @Override
    public void stratThunderstorm() {
        for (PandaMission pandaMission : getGameInteraction().getInventoryPandaMissions()) {
            Coordinate coordinateAllColor = rushPandaStrat.strategyMissionAllColor();
            Coordinate coordinateOneColor = rushPandaStrat.strategyMissionOneColor(pandaMission.getColorType());
            if (coordinateAllColor != null && pandaMission.getColorType().equals(ColorType.ALL_COLOR)){
                getGameInteraction().thunderstormAction(coordinateAllColor);
                return;
            }
            else if (coordinateOneColor != null){
                getGameInteraction().thunderstormAction(coordinateOneColor);
            return;
            }
        }
    }
}