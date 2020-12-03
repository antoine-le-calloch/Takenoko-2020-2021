package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.Bot;
import fr.unice.polytech.startingpoint.Type.*;

/**
 * Classe representant une mission paysan
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PeasantMission implements Mission {
    private final int points;
    private final ColorType colorType;

    public PeasantMission(int points, ColorType colorType){
        this.points = points;
        this.colorType = colorType;
    }

    public int checkMission(Board board, Bot bot) {
        return checkMissionPeasant(board);
    }

    public int checkMissionPeasant(Board board) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            if (parcel.getNbBamboo() == 2)
                return points;
        }
        return 0;
    }
}