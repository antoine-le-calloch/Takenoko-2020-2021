package fr.unice.polytech.startingpoint.game.playerdata;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.game.board.Canal;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.ColorType;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>{@link Inventory} :</h1>
 *
 * <p>This class provides an organised storage for different objects, from {@link PandaMission}, {@link ParcelMission}, {@link PeasantMission} and {@link Canal}
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

public class Inventory {
    private final List<PandaMission> inventoryPandaMission;
    private final List<ParcelMission> inventoryParcelMission;
    private final List<PeasantMission> inventoryPeasantMission;
    private final List<Canal> inventoryCanal;
    private final int[] inventoryBamboo;

    /**
     * <p>Set up the Inventory. Initialize all variables.</p>
     */
    public Inventory(){
        inventoryPandaMission = new ArrayList<>();
        inventoryParcelMission = new ArrayList<>();
        inventoryPeasantMission = new ArrayList<>();
        inventoryCanal = new ArrayList<>();
        inventoryBamboo = new int[]{0,0,0};
    }

    /**<p>Add a {@link Canal} in the inventory.</p>
     */
    public void addCanal(Canal canal){
        inventoryCanal.add(canal);
    }

    /**@return <b>A canal and remove it from the inventory.</b>
     */
    public Canal pickCanal() throws OutOfResourcesException {
        if (!inventoryCanal.isEmpty()){
            return inventoryCanal.remove(0);
        }
        throw new OutOfResourcesException("No more Canal in the inventory.");
    }

    /**<p>Add one bamboo of the {@link ColorType} specified in parameter.</p>
     *
     * @param colorType
     *              <b>The {@link ColorType} of the bamboo we want to add.</b>
     */
    public void addBamboo(ColorType colorType){
        if (colorType != ColorType.NO_COLOR)
            inventoryBamboo[colorType.ordinal()] ++;
    }

    /**<p>Sub bamboo(s) of the {@link ColorType} specified in parameter.</p>
     *
     * @param colorType
     *          <b>The {@link ColorType} of the bamboo we want to remove.</b>
     * @param colorType
     *          <b>The {@link ColorType} of the bamboo we want to remove.</b>
     * @return
     */
    public void subTwoBamboos(ColorType colorType){
        if(inventoryBamboo[colorType.ordinal()] >= 2 && !colorType.equals(ColorType.NO_COLOR))
            inventoryBamboo[colorType.ordinal()] -= 2;
    }

    public void subOneBambooPerColor() {
        if (inventoryBamboo[0] > 0 && inventoryBamboo[1] > 0 && inventoryBamboo[2] > 0) {
            inventoryBamboo[ColorType.YELLOW.ordinal()]--;
            inventoryBamboo[ColorType.GREEN.ordinal()]--;
            inventoryBamboo[ColorType.RED.ordinal()]--;
        }
    }

    public void addPandaMission(PandaMission mission){
        inventoryPandaMission.add(mission);
    }

    public void addParcelMission(ParcelMission mission){
        inventoryParcelMission.add(mission);
    }

    public void addPeasantMission(PeasantMission mission){
        inventoryPeasantMission.add(mission);
    }

    public void subPandaMissions(List<PandaMission> pandaMissions){
        inventoryPandaMission.removeAll(pandaMissions);
    }

    public void subParcelMissions(List<ParcelMission> parcelMissions){
        inventoryParcelMission.removeAll(parcelMissions);
    }

    public void subPeasantMissions(List<PeasantMission> peasantMissions){
        inventoryPeasantMission.removeAll(peasantMissions);
    }

    /**@param colorType
     *          <b>The {@link ColorType} of the bamboo we want the number from.</b>
     *
     * @return <b>The number of bamboo of the {@link ColorType} specified in parameter.</b>
     */
    public int getInventoryBamboo(ColorType colorType){
        return inventoryBamboo[colorType.ordinal()];
    }

    /**@return <b>The list containing the number of bamboos of each {@link ColorType}.</b>
     */
    public int[] getInventoryBamboo() {
        return inventoryBamboo.clone();
    }

    public List<Canal> getInventoryCanal() {
        return new ArrayList<>(inventoryCanal);
    }

    /**
     * @return <b>The list of {@link PandaMission} missions.</b>
     */
    public List<PandaMission> getPandaMissions(){
        return new ArrayList<>(inventoryPandaMission);
    }

    /**@return <b>The list of {@link ParcelMission} missions.</b>
     */
    public List<ParcelMission> getParcelMissions(){
        return new ArrayList<>(inventoryParcelMission);
    }

    /**
     * @return <b>The list of {@link PeasantMission} missions.</b>
     */
    public List<PeasantMission> getPeasantMissions(){
        return new ArrayList<>(inventoryPeasantMission);
    }
}