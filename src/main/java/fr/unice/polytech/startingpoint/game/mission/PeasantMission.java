package fr.unice.polytech.startingpoint.game.mission;

import fr.unice.polytech.startingpoint.game.Parcel;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;

import java.util.List;

/**
 * <h1>{@link PeasantMission} :</h1>
 *
 * <p>This class create and check if the {@link PeasantMission} is done.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see PeasantMission
 * @see ParcelMission
 * @version 0.5
 */

public final class PeasantMission{
    private final ColorType colorType;
    private final ImprovementType improvementType;
    private final int points;

    /**
     * <p>Set up a peasant mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    public PeasantMission(ColorType colorType, ImprovementType improvementType, int points){
        this.colorType = colorType;
        this.improvementType = improvementType;
        this.points = points;
    }

    public boolean checkMission(List<Parcel> parcelList) {
        if(improvementType.equals(ImprovementType.WHATEVER))
            return checkMissionSpecial(parcelList);
        else
            return checkMissionClassic(parcelList);
    }

    boolean checkMissionClassic(List<Parcel> parcelList) {
        int NB_BAMBOO = 4;
        for (Parcel parcel : parcelList) {
            if (parcel.getNbBamboo() == NB_BAMBOO && parcel.getColor() == colorType && parcel.getImprovement().equals(improvementType)){
                return true;
            }
        }
        return false;
    }

    boolean checkMissionSpecial(List<Parcel> parcelList){
        switch(colorType){
            case GREEN:
                return checkMissionFewParcel(parcelList,4);
            case YELLOW:
                return checkMissionFewParcel(parcelList,3);
            case RED:
                return checkMissionFewParcel(parcelList,2);
            default:
                return false;
        }
    }

    boolean checkMissionFewParcel(List<Parcel> parcelList, int nbParcel) {
        int NB_BAMBOO = 3;
        int cpt = 0;
        for (Parcel parcel : parcelList) {
            if (parcel.getNbBamboo() == NB_BAMBOO && parcel.getColor() == colorType)
                cpt++;
        }
        return cpt >= nbParcel;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public ImprovementType getImprovementType(){
        return improvementType;
    }

    public int getPoints() {
        return points;
    }
}