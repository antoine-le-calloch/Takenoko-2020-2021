package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.MissionType;

import java.util.List;

import static fr.unice.polytech.startingpoint.type.FormType.*;

/**
 * <h1>{@link ParcelMission} :</h1>
 *
 * <p>This class create and check if the {@link ParcelMission} is done.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link #checkMission(Inventory)} method from the {@link Mission}.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Mission
 * @see PeasantMission
 * @see ParcelMission
 * @version 0.5
 */

public final class ParcelMission extends Mission {

    private final FormType formType;
    private final ColorType colorTypeTwoColorMission;

    /**
     * <p>Set up a parcel mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    ParcelMission(Board board, ColorType colorType, int points, FormType formType) {
        this(board,colorType,colorType,points,formType);
    }

    /**
     * <p>Set up a parcel mission. Initialize all variables.</p>
     *
     * @param colorType1
     *            <b>the colorType of the mission</b>
     * @param colorType2
     *            <b>the second colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    ParcelMission(Board board, ColorType colorType1, ColorType colorType2, int points, FormType formType) {
        super(board, MissionType.PARCEL,colorType1,points);
        this.colorTypeTwoColorMission = colorType2;
        this.formType = formType;
    }

    /**
     * <p>check if a parcel mission is done, use checkFormIrrigateWithColor as a function of the form</p>
     *
     * @return <b>the number of point if the mission is done, if not , return 0</b>
     */
    boolean checkMission(Inventory inventory) {
        switch (formType) {
            case TRIANGLE:
                return checkOneColorForm(TRIANGLE.getOffsetsList());
            case LINE:
                return checkOneColorForm(LINE.getOffsetsList());
            case ARC:
                return checkOneColorForm(ARC.getOffsetsList());
            case DIAMOND:
                return checkTwoColorsForm(DIAMOND.getOffsetsList1(),DIAMOND.getOffsetsList2());
            default:
                return false;
        }
    }

    boolean checkTwoColorsForm(List<Coordinate> coordinateList1, List<Coordinate> coordinateList2) {
        for (Coordinate coordinate : board.getPlacedParcels().keySet())
            if (checkBasicForm(colorType,Coordinate.coordinatesOfOffsets(coordinate,coordinateList1)))
                if (checkBasicForm(colorTypeTwoColorMission,Coordinate.coordinatesOfOffsets(coordinate,coordinateList2)))
                    return true;
        return false;
    }

    boolean checkOneColorForm(List<Coordinate> coordinateList) {
        for (Coordinate coordinate : board.getPlacedParcels().keySet())
            if (checkBasicForm(colorType,Coordinate.coordinatesOfOffsets(coordinate,coordinateList)))
                return true;
        return false;
    }

    boolean checkBasicForm(ColorType colorType, List<Coordinate> coordinateList){
        int correctParcels = 0;
        for (Coordinate coordinate : coordinateList)
            if (board.isPlacedAndIrrigatedParcel(coordinate))
                if (board.getPlacedParcels().get(coordinate).getColor().equals(colorType))
                    correctParcels++;
        return correctParcels == coordinateList.size();
    }

    public FormType getFormType(){
        return formType;
    }
}