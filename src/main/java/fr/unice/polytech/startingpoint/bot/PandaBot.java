package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;

/**
 * <h1>{@link PandaBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link PandaMission} missions.</p>
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

public class PandaBot extends Bot {
    RushPandaStrat rushPandaStrat = new RushPandaStrat(this);

    public RushPandaStrat getRushPandaStrat() {
        return rushPandaStrat;
    }

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param gameInteraction
     *            <b>Game object.</b>
     */
    public PandaBot(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        for (int i = gameInteraction.getStamina(); i > 0; i--){
            rushPandaStrat.stratOneTurn();
        }
    }
}