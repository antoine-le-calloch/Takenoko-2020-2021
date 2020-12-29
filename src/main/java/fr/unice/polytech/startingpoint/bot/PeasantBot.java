package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.PeasantMission;
import fr.unice.polytech.startingpoint.game.PlayerInteraction;

/**
 * <h1>{@link PeasantBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link PeasantMission} missions.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link #botPlay()} method from the {@link Bot}.</p>
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
    public PeasantBot(PlayerInteraction playerInteraction) {
        super(playerInteraction);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        for (int i = playerInteraction.getStamina();i > 0; i--){
            stratMissionPeasant.stratOneTurn();
        }
    }
}