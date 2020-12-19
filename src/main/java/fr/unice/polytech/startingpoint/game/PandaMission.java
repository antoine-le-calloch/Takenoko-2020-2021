package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

/**
 * <h1>{@link PandaMission} :</h1>
 *
 * <p>This class create and check if the {@link PandaMission} is done.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link #checkMission(Board, Inventory)} method from the {@link Mission}.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Mission
 * @see PeasantMission
 * @see ParcelMission
 * @version 0.5
 */

public class PandaMission extends Mission {

    /**
     * <p>Set up a panda mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    PandaMission(ColorType colorType, int points){
        super(MissionType.PANDA,colorType,points);
    }

    /**
     * <p>check panda if a mission is done</p>
     *
     * @param board
     *            <b>Board object.</b>
     * @param inventory
     *            <b>Inventory object.</b>
     */
    int checkMission(Board board, Inventory inventory){
        int NB_BAMBOO = 1;
        if (inventory.getBamboo(colorType) >= NB_BAMBOO){
            for (int i = 0; i < NB_BAMBOO; i++)
                inventory.subBamboo(colorType);
            return points;
        }
        return 0;
    }
}