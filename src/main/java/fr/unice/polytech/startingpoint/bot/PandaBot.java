package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.List;

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

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param playerInteraction
     *            <b>Game object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public PandaBot(PlayerInteraction playerInteraction, Rules rules) {
        super(playerInteraction, rules);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        if (playerInteraction.getInventoryMissions().size() < 5 && playerInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0)
            drawMission(MissionType.PANDA);
        if (strategyMovePanda(possibleCoordinatesPanda()) != null)
                movePanda(strategyMovePanda(possibleCoordinatesPanda()));
    }

    /**@param coordinateList
     *            <b>The list of coordinates containing places where we want to move the Panda.</b>
     * @return <b>Return the first coordinate where the parcel has at least one bamboo.</b>
     */
    public Coordinate strategyMovePanda(List<Coordinate> coordinateList) {
        for (Coordinate coordinate : coordinateList) {
            if (playerInteraction.getPlacedParcelsNbBamboo(coordinate) > 0) {
                return coordinate;
            }
        }
        return null;
    }
}