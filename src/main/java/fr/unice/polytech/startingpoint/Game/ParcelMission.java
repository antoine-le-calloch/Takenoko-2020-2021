package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.Bot;
import fr.unice.polytech.startingpoint.Type.*;

/**
 * Classe representant une mission parcelle
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class ParcelMission implements Mission {
    private final int points;
    private final FormType formType;
    private final ColorType colorType;

    public ParcelMission(int points, FormType goalFormType, ColorType goalColorType) {
        this.formType = goalFormType;
        this.colorType = goalColorType;
        this.points = points;
    }

    //Vérifie si une mission est faite
    public int checkMission(Board board, Bot bot){
        return checkMissionParcel(board);
    }

    //Renvoie le nombre de points que les missions rapportent si elles ont été accomplies
    public int checkMissionParcel(Board board) {
        switch (formType) {
            case TRIANGLE:
                if (checkFormIrrigateWithColor(board,0,1))
                    return points;
                return 0;
            case LINE:
                if (checkFormIrrigateWithColor(board,2,5))
                    return points;
                return 0;
            default:
                return 0;
        }
    }

    //retourne vrai si il y a un triangle sur le plateau
    public boolean checkFormIrrigateWithColor(Board board, int offset1, int offset2) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            Coordinate coord1 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset1));
            Coordinate coord2 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset2));
            if (board.getIrrigatedParcels().contains(coord1) && board.getIrrigatedParcels().contains(coord2)) {
                if (parcel.getColor().equals(colorType)
                        && board.getPlacedParcels().get(coord1).getColor().equals(colorType)
                        && board.getPlacedParcels().get(coord2).getColor().equals(colorType))
                    return true;
            }
        }
        return false;
    }

    //Renvoie l'objectif de la mission
    public FormType getForm(){
        return formType;
    }

    //Renvoie la couleur de la mission
    public ColorType getColor() {
        return colorType;
    }
}