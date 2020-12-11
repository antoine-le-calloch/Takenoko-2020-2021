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

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass and initialize two {@link Random} objects.</p>
     *
     * @param game
     *            <b>Game object.</b>
     * @param rules
     *            <b>Rules object.</b>
     */
    public RandomBot(Game game, Rules rules) {
        super(game, rules);
        random = new Random();
        random2 = new Random();
    }

    /**<p>Set the {@link #random} and {@link #random2} to new objects specified in the parameters.</p>
     *
     * @param rand1
     *            <b>The first {@link Random} object.</b>
     * @param rand2
     *            <b>The second {@link Random} object.</b>
     */
    public void setRand(Random rand1, Random rand2){
        random = rand1;
        random2 = rand2;
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public void botPlay(){
        int randAction = random.nextInt(5);

        if (randAction == 0 && game.getResourceSize(ResourceType.ALL_MISSION) > 0) {// pioche mission
            int randMission = random2.nextInt(3);

            if (randMission == 0 && game.getResourceSize(ResourceType.PARCEL_MISSION) > 0)
                drawMission(MissionType.PARCEL);
            if (randMission == 1 && game.getResourceSize(ResourceType.PANDA_MISSION) > 0)
                drawMission(MissionType.PANDA);
            if (randMission == 2 && game.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
                drawMission(MissionType.PEASANT);
        }

        else if (randAction == 1 && game.getResourceSize(ResourceType.CANAL) > 0) {  // place canal
            if (possibleCoordinatesCanal().size() > 0) {
                List<Coordinate[]> list = possibleCoordinatesCanal();
                Collections.shuffle(list);
                drawCanal();
                placeCanal(list.get(0));
            }
        }

        else if (randAction == 2 && game.getResourceSize(ResourceType.PARCEL) > 0){ // place parcel
            List<ColorType> parcelList = drawParcel();
            Collections.shuffle(parcelList);
            selectParcel(parcelList.get(0));
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            placeParcel(list.get(0));
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