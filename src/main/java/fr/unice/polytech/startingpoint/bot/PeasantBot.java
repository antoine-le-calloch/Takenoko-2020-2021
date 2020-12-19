package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;

import java.util.List;

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

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param game
     *            <b>Game object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public PeasantBot(Game game, Rules rules) {
        super(game, rules);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        if (game.getInventoryMission().size() < 5 && game.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
            drawMission(MissionType.PEASANT);

        if (strategyMovePeasant(possibleCoordinatesPeasant()) != null)
            movePeasant(strategyMovePeasant(possibleCoordinatesPeasant()));

    }

    /**@param coordinateList
     *            <b>The list of coordinates containing places where we want to move the Peasant.</b>
     * @return <b>Return the first coordinate where the parcel has at least two bamboos.</b>
     */
    public Coordinate strategyMovePeasant(List<Coordinate> coordinateList) {
        for (Coordinate coordinate : coordinateList) {
            if (game.getPlacedParcelsNbBamboo(coordinate) > 1) {
                return coordinate;
            }
        }
        return null;
    }
}