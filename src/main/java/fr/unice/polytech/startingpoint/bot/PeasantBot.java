package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.WeatherType;

/**
 * <h1>{@link PeasantBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link PeasantMission} missions.</p>
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

public class PeasantBot extends Bot {
    MissionPeasantStrat stratMissionPeasant = new MissionPeasantStrat(this);
    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     */
    public PeasantBot(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    public MissionPeasantStrat getStratMissionPeasant() {
        return stratMissionPeasant;
    }

    /**<p>The actions of the bot during his turn.</p>
     * @param weatherType
     */

    @Override
    public void botPlay(WeatherType weatherType) {
        for (int i = gameInteraction.getStamina();i > 0; i--){
            stratMissionPeasant.stratOneTurn(weatherType);
        }
    }
}