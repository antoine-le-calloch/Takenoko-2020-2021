package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.MissionType;

/**
 * <h1>{@link PandaMission} :</h1>
 *
 * <p>This class create and check if the {@link PandaMission} is done.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link #checkMission(Inventory)} method from the {@link Mission}.</p>
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

public final class PandaMission extends Mission {

    /**
     * <p>Set up a panda mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    PandaMission(Board board, ColorType colorType, int points){
        super(board, MissionType.PANDA,colorType,points);
    }

    /**<p>check panda if a mission is done</p>
     */
    boolean checkMission(Inventory inventory){
        return inventory.subBamboo(colorType);
    }
}