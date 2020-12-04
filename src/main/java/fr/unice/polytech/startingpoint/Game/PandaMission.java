package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Type.*;

/**
 * Classe representant une mission panda
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PandaMission extends Mission {

    public PandaMission(ColorType colorType, int points){
        super(MissionType.PANDA,colorType,points);
    }

    public int checkMission(Board board, InventoryBot inventoryBot) {
        if (inventoryBot.subBamboo(colorType)){
            return points;
        }
        return 0;
    }
}