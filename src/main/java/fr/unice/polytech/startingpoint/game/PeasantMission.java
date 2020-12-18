package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

/**
 * <h1>{@link PeasantMission} :</h1>
 *
 * <p>This class create and check if the {@link PeasantMission} is done.</p>
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

public class PeasantMission extends Mission {

    /**
     * <p>Set up a peasant mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    PeasantMission(ColorType colorType, int points){
        super(MissionType.Peasant,colorType,points);
    }

    /**
     * <p>check peasant if a mission is done</p>
     *
     * @param board
     *            <b>Board object.</b>
     * @param inventory
     *            <b>Inventory object.</b>
     */
    int checkMission(Board board, Inventory inventory) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            int NB_BAMBOO = 2;
            if (parcel.getNbBamboo() == NB_BAMBOO && parcel.getColor() == colorType)
                return points;
        }
        return 0;
    }
}