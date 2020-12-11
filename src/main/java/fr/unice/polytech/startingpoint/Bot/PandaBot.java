package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.MissionType;
import fr.unice.polytech.startingpoint.Type.ResourceType;

import java.util.*;

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
     * @param game
     *            <b>Game object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public PandaBot(Game game, Rules rules) {
        super(game, rules);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        if (game.getInventoryMission().size() < 5 && game.getResourceSize(ResourceType.PANDA_MISSION) > 0)
            drawMission(MissionType.PANDA);
        else if (strategyMovePanda(possibleCoordinatesPanda()) != null)
                movePanda(strategyMovePanda(possibleCoordinatesPanda()));
    }

    /**@param coordinateList
     *            <b>The list of coordinates containing places where we want to move the Panda.</b>
     * @return <b>Return the first coordinate where the parcel has at least one bamboo.</b>
     */
    public Coordinate strategyMovePanda(List<Coordinate> coordinateList) {
        for (Coordinate coordinate : coordinateList) {
            if (game.getPlacedParcelsNbBamboo(coordinate) > 0) {
                return coordinate;
            }
        }
        return null;
    }
}