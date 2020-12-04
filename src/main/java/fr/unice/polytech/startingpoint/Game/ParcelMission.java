package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Type.*;

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

    public ParcelMission(ColorType colorType, int points,FormType formType) {
        super(MissionType.PARCEL,colorType,points);
        this.formType = formType;
    }

    //Renvoie le nombre de points que les missions rapportent si elles ont été accomplies
    public int checkMission(Board board, InventoryBot inventoryBot) {
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
    public FormType getFormType(){
        return formType;
    }
}