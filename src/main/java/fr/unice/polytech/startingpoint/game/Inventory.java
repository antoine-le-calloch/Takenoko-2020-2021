package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

import java.util.*;

/**
 * <h1>{@link Inventory} :</h1>
 *
 * <p>This class provides an organised storage for different objects, from {@link Mission} and {@link Canal}
 * classes, for the {@link Bot},{@link PandaBot}, {@link ParcelBot} and {@link PeasantBot} classes.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Bot
 * @see PandaBot
 * @see ParcelBot
 * @see PeasantBot
 * @see RandomBot
 * @version 0.5
 */

class Inventory {
    private final List<Mission> inventoryMission;
    private final List<Canal> inventoryCanal;
    private final int[] inventoryBamboo;
    private final int[] score;

    /**
     * <p>Set up the Inventory. Initialize all variables.</p>
     */
    Inventory(){
        inventoryMission = new ArrayList<>();
        inventoryCanal = new ArrayList<>();
        inventoryBamboo = new int[]{0,0};
        score = new int[]{0,0};
    }

    /**@return <b>A canal and remove it from the inventory.</b>
     */
    Canal pickCanal() throws OutOfResourcesException {
        if (!inventoryCanal.isEmpty()){
            return inventoryCanal.remove(0);
        }
        throw new OutOfResourcesException("No more Canal in the inventory.");
    }

    /**<p>Add to the score the number of points specified in parameter.</p>
     *
     * @param count
     *              <b>The number of points to add to the score.</b>
     */
    void addScore(int count) {
        score[0] += count;
        score[1] ++;
    }

    /**<p>Add one bamboo of the {@link ColorType} specified in parameter.</p>
     *
     * @param colorType
     *              <b>The {@link ColorType} of the bamboo we want to add.</b>
     */
    void addBamboo(ColorType colorType){
        if (colorType != ColorType.NO_COLOR)
            inventoryBamboo[colorType.ordinal()] ++;
    }

    /**<p>Add a {@link Canal} in the inventory.</p>
     */
    void addCanal(Canal canal){
        inventoryCanal.add(canal);
    }

    /**<p>Add a {@link Mission} in the inventory.</p>
     */
    void addMission(Mission mission){
        inventoryMission.add(mission);
    }

    /**<p>Sub one bamboo of the {@link ColorType} specified in parameter.</p>
     *
     * @param colorType
     *          <b>The {@link ColorType} of the bamboo we want to remove.</b>
     */
    void subBamboo(ColorType colorType){
        if(inventoryBamboo[colorType.ordinal()]>0 && !colorType.equals(ColorType.NO_COLOR))
            inventoryBamboo[colorType.ordinal()] --;
    }

    /**<p>Remove all {@link Mission} specified in the {@link List} in parameters.</p>
     *
     * @param missions
     *              <b>The {@link List}.</b>
     */
    void subMissions(List<Mission> missions){
        inventoryMission.removeAll(missions);
    }

    /**@param colorType
     *          <b>The {@link ColorType} of the bamboo we want the number from.</b>
     *
     * @return <b>The number of bamboo of the {@link ColorType} specified in parameter.</b>
     */
    int getBamboo(ColorType colorType){
        return inventoryBamboo[colorType.ordinal()];
    }

    /**@return <b>The list containing the number of bamboos of each {@link ColorType}.</b>
     */
    int[] getBamboo() {
        return inventoryBamboo.clone();
    }

    /**@return <b>The list of all missions.</b>
     */
    List<Mission> getMissions(){
        return new ArrayList<>(inventoryMission);
    }

    /**@return <b>The list of {@link ParcelMission} missions.</b>
     */
    List<ParcelMission> getParcelMissions(){
        List<ParcelMission> parcelMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PARCEL)
                parcelMissions.add((ParcelMission) mission);
        }
        return parcelMissions;
    }

    /**
     * @return <b>The list of {@link PandaMission} missions.</b>
     */
    List<PandaMission> getPandaMissions(){
        List<PandaMission> pandaMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PANDA)
                pandaMissions.add((PandaMission) mission);
        }
        return pandaMissions;
    }

    /**
     * @return <b>The list of {@link PeasantMission} missions.</b>
     */
    List<PeasantMission> getPeasantMissions(){
        List<PeasantMission> peasantMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PEASANT)
                peasantMissions.add((PeasantMission) mission);
        }
        return peasantMissions;
    }

    /**
     * @return <b>The score.</b>
     */
    int getScore() {
        return score[0];
    }

    /**
     * @return <b>The number of missions done.</b>
     */
    int getMissionsDone() {
        return score[1];
    }
}
