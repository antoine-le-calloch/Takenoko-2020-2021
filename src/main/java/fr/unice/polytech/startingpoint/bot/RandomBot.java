package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.ActionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h1>{@link RandomBot} :</h1>
 *
 * <p>This class provides a bot playing randomly.</p>
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

public class RandomBot extends Bot {
    RandomStrat randomStrat = new RandomStrat(this);

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass and initialize two {@link Random} objects.</p>
     *
     * @param playerInteraction
     *            <b>PlayerInteraction object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public RandomBot(PlayerInteraction playerInteraction) {
        super(playerInteraction);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        int stamina = playerInteraction.getStamina();
        List<ActionType> actionPlay = new ArrayList<>();
        while(stamina!=0){
            randomStrat.stratOneTurn(actionPlay);
            stamina--;
        }
    }
}