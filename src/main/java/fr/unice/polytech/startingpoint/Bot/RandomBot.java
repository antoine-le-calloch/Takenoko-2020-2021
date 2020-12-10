package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;

import java.util.Collections;
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
    private Random random;
    private Random random2;

    /**
     * <h2>{@link #RandomBot(Resource, Board)} :</h2>
     *
     * <p>Set up the bot. Call the constructor from {@link Bot} superclass and initialize two {@link Random} objects.</p>
     *
     * @param resource
     *            <b>Resource object.</b>
     * @param board
     *            <b>Board object.</b>
     */
    public RandomBot(Resource resource, Board board) {
        super(resource, board);
        random = new Random();
        random2 = new Random();
    }

    /**
     * <h2>{@link #setRand(Random, Random)} :</h2>
     *
     * <p>Set the {@link #random} and {@link #random2} to new objects specified in the parameters.</p>
     *
     * @param rand1
     *            <b>The first {@link Random} object.</b>
     * @param rand2
     *            <b>The second {@link Random} object.</b>
     * @see Random
     */
    public void setRand(Random rand1, Random rand2){
        random = rand1;
        random2 = rand2;
    }

    /**
     * <h2>{@link #botPlay()} :</h2>
     *
     * <p>The actions of the bot during his turn.</p>
     */
    public void botPlay(){
        int randAction = random.nextInt(5);

        if (randAction == 0 && resource.getNbMission() > 0) {// pioche mission
            int randMission = random2.nextInt(3);

            if (randMission == 0 && resource.getDeckParcelMission().size() > 0)
                drawMission(MissionType.PARCEL);
            if (randMission == 1 && resource.getDeckPandaMission().size() > 0)
                drawMission(MissionType.PANDA);
            if (randMission == 2 && resource.getDeckPeasantMission().size() > 0)
                drawMission(MissionType.PEASANT);
        }

        else if (randAction == 1 && resource.getCanal().size() > 0 && resource.getCanal().size() > 0) {  // place canal
            if (possibleCoordinatesCanal().size() > 0) {
                List<Coordinate[]> list = possibleCoordinatesCanal();
                Collections.shuffle(list);
                placeCanal(list.get(0));
            }
        }

        else if (randAction == 2 && possibleCoordinatesParcel().size() > 0 && resource.getParcel().size() > 0){ // place parcel
            List<Parcel> parcelList = drawParcel();
            Collections.shuffle(parcelList);
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            placeParcel(list.get(0), parcelList.get(0));
        }

        else if (randAction == 3 && possibleCoordinatesPanda().size() != 0) {
            List<Coordinate> list = possibleCoordinatesPanda();
            Collections.shuffle(list);
            movePanda(list.get(0));
        }

        else if (possibleCoordinatesPeasant().size() != 0 ) {
            List<Coordinate> list = possibleCoordinatesPeasant();
            Collections.shuffle(list);
            movePeasant(list.get(0));
        }
    }
}