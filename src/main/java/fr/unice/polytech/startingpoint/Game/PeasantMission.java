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

    PeasantMission(ColorType colorType, int points){
        super(MissionType.PEASANT,colorType,points);
    }

    int checkMission(Board board, Inventory inventory) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            int NB_BAMBOO = 2;
            if (parcel.getNbBamboo() == NB_BAMBOO && parcel.getColor() == colorType)
                return points;
        }
        return 0;
    }
}