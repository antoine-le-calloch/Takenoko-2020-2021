package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.MissionType;

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

    /**
     * <p>Set up a parcel mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    ParcelMission(Board board, ColorType colorType, int points,FormType formType) {
        super(board, MissionType.PARCEL,colorType,points);
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
                return checkFormOneColor(new Coordinate(1, 0, -1), new Coordinate(1, -1, 0));
            case LINE:
                return checkFormOneColor(new Coordinate(0, -1, 1), new Coordinate(0, 1, -1));
            case DIAMOND:
                return checkFormOneColor(new Coordinate(0, -1, 1), new Coordinate(1, -1, 0), new Coordinate(1, 0, -1));
            case ARC:
                return checkFormOneColor(new Coordinate(1, 0, -1), new Coordinate(0, -1, 1));
            default:
                return false;
        }
    }

    /**
     * <p>check if a form in on the board, all parcels have to be irrigated and must have the good color.</p>
     *
     * @param coordinate1
     *            <b>first coordinate to add to the parcel's coordinate
     *            to check is the there is a parcel irrigated and with a good color on the board.</b>
     * @param coordinate2
     *            <b>second coordinate to add to the parcel's coordinate
     *      *            to check is the there is a parcel irrigated and with a good color on the board.</b>
     * @return <b>true if the form is on the board</b>
     */
    boolean checkFormOneColor(Coordinate ... coordinates) {
        for (Coordinate coordinate : board.getPlacedParcels().keySet()) {
            if ( board.isPlacedAndIrrigatedParcel(coordinate) ){
                if ( board.getPlacedParcels().get(coordinate).getColor().equals(colorType) ){
                    int correctParcels = 0;
                    for (Coordinate c : coordinates) {
                        if ( board.isPlacedAndIrrigatedParcel(new Coordinate(coordinate,c)) ){
                            if ( board.getPlacedParcels().get(new Coordinate(coordinate,c)).getColor().equals(colorType) ){
                                correctParcels ++;
                            }
                        }
                    }
                    if (correctParcels == coordinates.length)
                        return true;
                }
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