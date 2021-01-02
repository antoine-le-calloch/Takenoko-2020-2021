package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import fr.unice.polytech.startingpoint.type.MissionType;

/**
 * <h1>{@link PeasantMission} :</h1>
 *
 * <p>This class create and check if the {@link PeasantMission} is done.</p>
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

public final class PeasantMission extends Mission {
    private final ImprovementType improvementType;

    /**
     * <p>Set up a peasant mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    PeasantMission(Board board, ColorType colorType, int points, ImprovementType improvementType){
        super(board, MissionType.PEASANT,colorType,points);
        this.improvementType=improvementType;
    }

    /**<p>check peasant if a mission is done</p>
     */
    boolean checkMission(Inventory inventory) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            int NB_BAMBOO = 2;
            if (parcel.getNbBamboo() == NB_BAMBOO && parcel.getColor() == colorType){
                if(improvementType.equals(ImprovementType.NOTHING))
                    return true;
                else if(parcel.getImprovement().equals(improvementType)){
                    return true;
                }
            }

        }
        return false;
    }
    
}