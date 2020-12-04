package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Type.*;

/**
 * Interface representant les caractéristiques communes des missions
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public abstract class Mission {
    protected final MissionType missionType;
    protected final ColorType colorType;
    protected final int points;

    public Mission(MissionType missionType, ColorType colorType, int points){
        this.missionType = missionType;
        this.colorType = colorType;
        this.points = points;
    }

    //Vérifie si la mission est faite
    public abstract int checkMission(Board board, InventoryBot inventoryBot);

    //Renvoie la couleur de la mission
    public MissionType getMissionType() {
        return missionType;
    }

    //Renvoie la couleur de la mission
    public ColorType getColor() {
        return colorType;
    }

    //Renvoie la couleur de la mission
    public int getPoints() {
        return points;
    }
}
