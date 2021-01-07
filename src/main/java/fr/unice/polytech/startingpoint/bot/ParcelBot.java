package fr.unice.polytech.startingpoint.bot;


import fr.unice.polytech.startingpoint.bot.strategie.MissionParcelStrat;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;
import fr.unice.polytech.startingpoint.type.WeatherType;

/**
 * <h1>{@link ParcelBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link ParcelMission} missions.</p>
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

public class ParcelBot extends Bot {
    public MissionParcelStrat getStratMissionParcel() {
        return stratMissionParcel;
    }

    MissionParcelStrat stratMissionParcel = new MissionParcelStrat(this);

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param gameInteraction
     *            <b>Game object.</b>
     */
    public ParcelBot(GameInteraction gameInteraction) {
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
            Mission mission = determineBestMissionToDo();
            playBestMission(mission);
        }
    }

    @Override
    public MissionType bestMissionTypeToDraw() {
        if (gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0)
            return MissionType.PARCEL;
        else
            return chooseMissionTypeDrawable();
    }

    MissionType chooseMissionTypeDrawable() {
        if (gameInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0)
            return MissionType.PANDA;
        else if (gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
            return MissionType.PEASANT;
        else
            return MissionType.PARCEL;
    }
}