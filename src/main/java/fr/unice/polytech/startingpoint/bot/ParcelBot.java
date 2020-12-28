package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.ActionType;

import javax.swing.text.DefaultEditorKit;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>{@link ParcelBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link ParcelMission} missions.</p>
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

public class ParcelBot extends Bot {
    MissionParcelStrat stratMissionParcel = new MissionParcelStrat(this);

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param playerInteraction
     *            <b>Game object.</b>
     */
    public ParcelBot(PlayerInteraction playerInteraction) {
        super(playerInteraction);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        int stamina = playerInteraction.getStamina();
        List<ActionType> actionPlay = new ArrayList<>();
        while(stamina!=0){
            stratMissionParcel.stratOneTurn(actionPlay);
            stamina--;
        }
    }
}