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

    /**
     * <p>Set up a parcel mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    ParcelMission(ColorType colorType, int points,FormType formType) {
        super(MissionType.PARCEL,colorType,points);
        this.formType = formType;
    }

    /**
     * <p>check if a parcel mission is done, use checkFormIrrigateWithColor as a function of the form</p>
     *
     * @param board
     *            <b>Board object.</b>
     * @param inventory
     *            <b>Inventory object.</b>
     * @return <b>the number of point if the mission is done, if not , return 0</b>
     */
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

    /**
     * <p>check if a form in on the board, all parcels have to be irrigated and must have the good color.</p>
     *
     * @param board
     *            <b>Board object.</b>
     * @param coordinate1
     *            <b>first coordinate to add to the parcel's coordinate
     *            to check is the there is a parcel irrigated and with a good color on the board.</b>
     * @param coordinate2
     *            <b>second coordinate to add to the parcel's coordinate
     *      *            to check is the there is a parcel irrigated and with a good color on the board.</b>
     * @return <b>true if the form is on the board</b>
     */
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

    /**
     * @return <b>the form of the mission</b>
     */
    public FormType getFormType(){
        return formType;
    }
}