package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

/**
 * Interface representant les caractéristiques communes des missions
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 0.5
 */

abstract class Mission {
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
    Mission(MissionType missionType, ColorType colorType, int points){
        this.missionType = missionType;
        this.colorType = colorType;
        this.points = points;
    }


    /**
     * <p>check if a mission is done</p>
     *
     * @param board
     *            <b>Board object.</b>
     * @param inventory
     *            <b>Inventory object.</b>
     */
    abstract int checkMission(Board board, Inventory inventory);


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
