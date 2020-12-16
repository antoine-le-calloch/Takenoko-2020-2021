package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.List;

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
                if (checkFormIrrigateWithColor(board, new Coordinate(1, 0, -1), new Coordinate(1, -1, 0)))
                    return points;
                return 0;
            case LINE:
                if (checkFormIrrigateWithColor(board, new Coordinate(0, -1, 1), new Coordinate(0, 1, -1)))
                    return points;
                return 0;
            default:
                return 0;
        }
    }

    //retourne vrai si il y a un triangle sur le plateau
    boolean checkFormIrrigateWithColor(Board board, Coordinate coordinate1, Coordinate coordinate2) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            if (board.isPlacedAndIrrigatedParcel(parcel.getCoordinates()) && board.isPlacedAndIrrigatedParcel(new Coordinate(parcel.getCoordinates(), coordinate1)) && board.isPlacedAndIrrigatedParcel(new Coordinate(parcel.getCoordinates(), coordinate2))){
                if (parcel.getColor().equals(colorType) &&
                        board.getPlacedParcels().get(new Coordinate(parcel.getCoordinates(), coordinate1)).getColor().equals(colorType) &&
                        board.getPlacedParcels().get(new Coordinate(parcel.getCoordinates(), coordinate2)).getColor().equals(colorType) )
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