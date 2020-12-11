package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

/**
 * Classe representant une mission panda
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PandaMission extends Mission {

    PandaMission(ColorType colorType, int points){
        super(MissionType.PANDA,colorType,points);
    }

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