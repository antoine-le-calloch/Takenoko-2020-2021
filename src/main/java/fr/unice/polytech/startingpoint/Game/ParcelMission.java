package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

/**
 * Classe representant une mission parcelle
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class ParcelMission extends Mission {
    private final FormType formType;

    ParcelMission(ColorType colorType, int points,FormType formType) {
        super(MissionType.PARCEL,colorType,points);
        this.formType = formType;
    }

    //Renvoie le nombre de points que les missions rapportent si elles ont été accomplies
    int checkMission(Board board, Inventory inventory) {
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
    boolean checkFormIrrigateWithColor(Board board, int offset1, int offset2) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            Coordinate c1 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset1));
            Coordinate c2 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset2));
            if (board.isPlacedAndIrrigatedParcel(c1) && board.isPlacedAndIrrigatedParcel(c2)){
                if (parcel.getColor().equals(colorType) &&
                        board.getPlacedParcels().get(c1).getColor().equals(colorType) &&
                        board.getPlacedParcels().get(c2).getColor().equals(colorType) )
                    return true;
            }
        }
        return false;
    }

    //Renvoie l'objectif de la mission
    public FormType getFormType(){
        return formType;
    }
}