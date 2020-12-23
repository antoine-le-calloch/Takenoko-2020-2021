package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.*;

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
    MissionPeasantStrat stratMissionPeasant = new MissionPeasantStrat(this, rules);
    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param game
     *            <b>Game object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public PeasantBot(PlayerInteraction playerInteraction, Rules rules) {
        super(playerInteraction, rules);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        stratMissionPeasant.stratOneTurn();
    }
}