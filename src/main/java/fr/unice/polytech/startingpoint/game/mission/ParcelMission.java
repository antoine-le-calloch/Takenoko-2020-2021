package fr.unice.polytech.startingpoint.game.mission;

import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;

import java.util.List;
import java.util.Map;

import static fr.unice.polytech.startingpoint.type.FormType.*;

/**
 * <h1>{@link ParcelMission} :</h1>
 *
 * <p>This class create and check if the {@link ParcelMission} is done.</p>
 *
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see PeasantMission
 * @see ParcelMission
 * @version 0.5
 */

public final class ParcelMission{
    private final ColorType colorType1;
    private final ColorType colorType2;
    private final FormType formType;
    private final int points;

    /**
     * <p>Set up a parcel mission. Initialize all variables.</p>
     *
     * @param colorType1
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    public ParcelMission(ColorType colorType1, FormType formType,int points) {
        this(colorType1, colorType1, formType, points);
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
    public ParcelMission(ColorType colorType1, ColorType colorType2, FormType formType, int points) {
        this.colorType1 = colorType1;
        this.colorType2 = colorType2;
        this.formType = formType;
        this.points = points;
    }

    /**
     * <p>check if a parcel mission is done, use checkFormIrrigateWithColor as a function of the form</p>
     *
     * @return <b>the number of point if the mission is done, if not , return 0</b>
     */
    public boolean checkMission(Map<Coordinate,Parcel> coordinateParcelMap) {
        switch (formType) {
            case TRIANGLE:
                return checkOneColorForm(coordinateParcelMap, TRIANGLE.getOffsetsList());
            case LINE:
                return checkOneColorForm(coordinateParcelMap, LINE.getOffsetsList());
            case ARC:
                return checkOneColorForm(coordinateParcelMap, ARC.getOffsetsList());
            case DIAMOND:
                return checkTwoColorsForm(coordinateParcelMap, DIAMOND.getOffsetsList1(),DIAMOND.getOffsetsList2());
            default:
                return false;
        }
    }

    boolean checkTwoColorsForm(Map<Coordinate, Parcel> coordinateParcelMap, List<Coordinate> coordinateList1, List<Coordinate> coordinateList2) {
        for (Coordinate coordinate : coordinateParcelMap.keySet())
            if (checkBasicForm(coordinateParcelMap, colorType1,Coordinate.coordinatesOfOffsets(coordinate,coordinateList1)))
                if (checkBasicForm(coordinateParcelMap, colorType2,Coordinate.coordinatesOfOffsets(coordinate,coordinateList2)))
                    return true;
        return false;
    }

    boolean checkOneColorForm(Map<Coordinate, Parcel> coordinateParcelMap, List<Coordinate> coordinateList) {
        for (Coordinate coordinate : coordinateParcelMap.keySet())
            if (checkBasicForm(coordinateParcelMap, colorType1,Coordinate.coordinatesOfOffsets(coordinate,coordinateList)))
                return true;
        return false;
    }

    boolean checkBasicForm(Map<Coordinate, Parcel> coordinateParcelMap, ColorType colorType, List<Coordinate> coordinateList){
        int correctParcels = 0;
        for (Coordinate coordinate : coordinateList)
            if (coordinateParcelMap.containsKey(coordinate))
                if (coordinateParcelMap.get(coordinate).getIrrigated() && coordinateParcelMap.get(coordinate).getColor().equals(colorType))
                    correctParcels++;
        return correctParcels == coordinateList.size();
    }

    public ColorType getColorType() {
        return colorType1;
    }

    public ColorType getColorType1() {
        return colorType1;
    }

    public ColorType getColorType2() {
        return colorType2;
    }

    public FormType getFormType() {
        return formType;
    }

    public int getPoints() {
        return points;
    }
}