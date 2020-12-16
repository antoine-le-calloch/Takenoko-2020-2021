package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

/**
 * Classe representant une mission paysan
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
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
        super(MissionType.PEASANT,colorType,points);
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