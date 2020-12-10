package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.MissionType;

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

    /**
     * <h2>{@link #PeasantBot(Resource, Board)} :</h2>
     *
     * <p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param resource
     *            <b>Resource object.</b>
     * @param board
     *            <b>Board object.</b>
     */
    public PeasantBot(Resource resource, Board board) {
        super(resource, board);
    }

    /**
     * <h2>{@link #botPlay()} :</h2>
     *
     * <p>The actions of the bot during his turn.</p>
     */
    public void botPlay() {
        if (inventory.getMission().size() < 5 && resource.getDeckPeasantMission().size() > 0)
            drawMission(MissionType.PEASANT);
        else if (strategyMovePeasant(possibleCoordinatesPeasant()) != null)
            movePeasant(strategyMovePeasant(possibleCoordinatesPeasant()));
    }

    /**
     * <h2>{@link #strategyMovePeasant(List)} : </h2>
     *
     * @param coordinateList
     *            <b>The list of coordinates containing places where we want to move the Peasant.</b>
     * @return <b>Return the first coordinate where the parcel has at least two bamboos.</b>
     * @see Coordinate
     * @see Board
     */
    public Coordinate strategyMovePeasant(List<Coordinate> coordinateList) {
        for (Coordinate coordinate : coordinateList) {
            if (board.getPlacedParcels().get(coordinate).getNbBamboo() > 1) {
                return coordinate;
            }
        }
        return null;
    }
}