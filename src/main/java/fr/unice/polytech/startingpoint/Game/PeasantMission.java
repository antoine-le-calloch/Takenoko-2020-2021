package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Type.*;

/**
 * Classe representant une mission paysan
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PeasantMission extends Mission {

    public PeasantMission(ColorType colorType, int points){
        super(MissionType.PEASANT,colorType,points);
    }

    public int checkMission(Board board, Inventory inventory) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            if (parcel.getNbBamboo() == 2)
                return points;
        }
        return 0;
    }
}