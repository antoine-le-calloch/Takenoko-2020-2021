package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.MissionType;

/**
 * <h1>{@link Mission} :</h1>
 *
 * <p>This class provides a skeletal implementation of the {@link PeasantMission},
 * {@link ParcelMission}, {@link PandaMission} classes.</p>
 *
 * <p>The programmer needs only to extend this class and provide
 * implementations for the {@link #checkMission(Inventory)} method.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see MissionType
 * @see ColorType
 * @version 0.5
 */

abstract class Mission {
    protected final Board board;
    protected final MissionType missionType;
    protected final ColorType colorType;
    protected final int points;

    /**
     * <p>Set up a mission. Initialize all variables.</p>
     *
     * @param missionType
     *            <b>the missionType of the mission</b>
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    Mission(Board board, MissionType missionType, ColorType colorType, int points){
        this.board = board;
        this.missionType = missionType;
        this.colorType = colorType;
        this.points = points;
    }

    /**<p>check if a mission is done</p>
     */
    abstract boolean checkMission(Inventory inventory);

    /**
     * @return <b>the missionType of the mission</b>
     */
    public MissionType getMissionType() {
        return missionType;
    }

    /**
     * @return <b>the colorType of the mission</b>
     */
    public ColorType getColor() {
        return colorType;
    }

    /**
     * @return <b>the points of the mission</b>
     */
    public int getPoints() {
        return points;
    }
}