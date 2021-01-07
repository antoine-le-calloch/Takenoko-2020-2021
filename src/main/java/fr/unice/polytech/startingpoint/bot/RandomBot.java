package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <h1>{@link RandomBot} :</h1>
 *
 * <p>This class provides a bot playing randomly.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link Bot#botPlay(WeatherType)} method from the {@link Bot}.</p>
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
     * @param gameInteraction
     *            <b>GameInteraction object.</b>
     */
    public RandomBot(GameInteraction gameInteraction) {
        super(gameInteraction);
        random = new Random();
        random2 = new Random();
    }

    @Override
    public MissionType bestMissionTypeToDraw() {
        return null;
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
     * @param weatherType
     */
    @Override
    public void botPlay(WeatherType weatherType){
        int randAction = random.nextInt(5);

        if (randAction == 0 && getGameInteraction().getResourceSize(ResourceType.ALL_MISSION) > 0 && !getGameInteraction().contains(ActionType.DRAW_MISSION)) {// pioche mission
            int randMission = random2.nextInt(3);

            if (randMission == 0 && getGameInteraction().getResourceSize(ResourceType.PARCEL_MISSION) > 0)
                drawMission(MissionType.PARCEL);
            if (randMission == 1 && getGameInteraction().getResourceSize(ResourceType.PANDA_MISSION) > 0)
                drawMission(MissionType.PANDA);
            if (randMission == 2 && getGameInteraction().getResourceSize(ResourceType.PEASANT_MISSION) > 0)
                drawMission(MissionType.PEASANT);
        }

        else if (randAction == 1 && getGameInteraction().getResourceSize(ResourceType.CANAL) > 0 && !getGameInteraction().contains(ActionType.DRAW_CANAL)) {  // place canal
            if (stratMissionParcel.possibleCoordinatesCanal().size() > 0) {
                List<Coordinate[]> list = stratMissionParcel.possibleCoordinatesCanal();
                Collections.shuffle(list);
                drawCanal();
                placeCanal(list.get(0));
            }
        }

        else if (randAction == 2 && getGameInteraction().getResourceSize(ResourceType.PARCEL) > 0 && !getGameInteraction().contains(ActionType.DRAW_PARCELS)){ // place parcel
            List<ParcelInformation> parcelList = drawParcel();
            Collections.shuffle(parcelList);
            selectParcel(parcelList.get(0));
            List<Coordinate> list = stratMissionParcel.possibleCoordinatesParcel();
            Collections.shuffle(list);
            placeParcel(list.get(0));
        }

        else if (randAction == 3 && stratMissionParcel.possibleCoordinatesPanda().size() != 0 && !getGameInteraction().contains(ActionType.MOVE_PANDA)) {
            List<Coordinate> list = stratMissionParcel.possibleCoordinatesPanda();
            Collections.shuffle(list);
            movePanda(list.get(0));
        }

        else if (stratMissionParcel.possibleCoordinatesPeasant().size() != 0 && !getGameInteraction().contains(ActionType.MOVE_PEASANT)) {
            List<Coordinate> list = stratMissionParcel.possibleCoordinatesPeasant();
            Collections.shuffle(list);
            movePeasant(list.get(0));
        }
    }
}