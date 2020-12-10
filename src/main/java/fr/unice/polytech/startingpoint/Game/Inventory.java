package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Type.*;

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

public class Inventory {
    private int stamina;
    private final List<Mission> inventoryMission = new ArrayList<>();
    private final List<Canal> inventoryCanal = new ArrayList<>();
    private final int[] inventoryBamboo = new int[ColorType.values().length-1];

    /**
     * <h2>{@link #Inventory()} :</h2>
     *
     * Initialize {@link #stamina} and {@link #inventoryBamboo} variables.
     */
    public Inventory(){
        stamina = 2;
        for (int nbBamboo : inventoryBamboo){
            nbBamboo = 0;
        }
    }

    /**
     * <h2>{@link #pickCanal()} :</h2>
     *
     * @return <b>A canal and remove it from the inventory.</b>
     *
     * @see Resource
     * @see Canal
     */
    public Canal pickCanal(){
        if (!inventoryCanal.isEmpty()){
            return inventoryCanal.remove(0);
        }
        return null;
    }

    /**
     * <h2>{@link #setStamina(int)} :</h2>
     *
     * <p>Set {@link #stamina} variable.</b>
     *
     * @param sp
     *          <b>The {@link Integer} we set {@link #stamina} from.</b>
     */
    public void setStamina(int sp){
        stamina = sp;
    }

    /**
     * <h2>{@link #addBamboo(ColorType)} :</h2>
     *
     * <p>Add one bamboo of the {@link ColorType} specified in parameter.</p>
     *
     * @param colorType
     *              <b>The {@link ColorType} of the bamboo we want to add.</b>
     *
     * @see ColorType
     */
    public void addBamboo(ColorType colorType){
        if (!colorType.equals(ColorType.NO_COLOR))
            inventoryBamboo[colorType.ordinal()] ++;
    }

    /**
     * <h2>{@link #addCanal(Canal)} :</h2>
     *
     * <p>Add a {@link Canal} in the inventory.</p>
     *
     * @see Canal
     */
    public void addCanal(Canal canal){
        inventoryCanal.add(canal);
    }

    /**
     * <h2>{@link #addMission(Mission)} :</h2>
     *
     * <p>Add a {@link Mission} in the inventory.</p>
     *
     * @see Mission
     * @see ParcelMission
     * @see PeasantMission
     * @see PandaMission
     */
    public void addMission(Mission mission){
        inventoryMission.add(mission);
    }

    /**
     * <h2>{@link #subStamina()} :</h2>
     *
     * <p>Sub one to the {@link #stamina} variable.</b>
     *
     */
    public void subStamina(){
        stamina --;
    }
    /**
     * <h2>{@link #subBamboo(ColorType)} :</h2>
     *
     * <p>Sub one bamboo of the {@link ColorType} specified in parameter.</p>
     *
     * @param colorType
     *          <b>The {@link ColorType} of the bamboo we want to remove.</b>
     *
     * @see ColorType
     */
    public void subBamboo(ColorType colorType){
        if(inventoryBamboo[colorType.ordinal()]>0 && !colorType.equals(ColorType.NO_COLOR))
            inventoryBamboo[colorType.ordinal()] --;
    }

    /**
     * <h2>{@link #subMissions(List)} :</h2>
     *
     * <p>Remove all {@link Mission} specified in the {@link List} in parameters.</p>
     *
     * @param missions
     *              <b>The {@link List}.</b>
     *
     * @see Mission
     * @see ParcelMission
     * @see PandaMission
     * @see PeasantMission
     */
    public void subMissions(List<Mission> missions){
        inventoryMission.removeAll(missions);
    }

    /**
     * <h2>{@link #getBamboo(ColorType)} :</h2>
     *
     * @param colorType
     *          <b>The {@link ColorType} of the bamboo we want the number from.</b>
     *
     * @return <b>The number of bamboo of the {@link ColorType} specified in parameter.</b>
     *
     * @see ColorType
     */
    public int getBamboo(ColorType colorType){
        return inventoryBamboo[colorType.ordinal()];
    }

    /**
     * <h2>{@link #getBamboo()} :</h2>
     *
     * @return <b>The list containing the number of bamboos of each {@link ColorType}.</b>
     * @see ColorType
     */
    public int[] getBamboo() {
        return inventoryBamboo.clone();
    }

    /**
     * <h2>{@link #getMission()} :</h2>
     *
     * @return <b>The list of all missions.</b>
     * @see Mission
     * @see ParcelMission
     * @see PandaMission
     * @see PeasantMission
     */
    public List<Mission> getMission(){
        return inventoryMission;
    }

    /**
     * <h2>{@link #getParcelMissions()} :</h2>
     *
     * @return <b>The list of {@link ParcelMission} missions.</b>
     * @see ParcelMission
     */
    public List<ParcelMission> getParcelMissions(){
        List<ParcelMission> parcelMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PARCEL)
                parcelMissions.add((ParcelMission) mission);
        }
        return parcelMissions;
    }

    /**
     * <h2>{@link #getPandaMissions()} :</h2>
     *
     * @return <b>The list of {@link PandaMission} missions.</b>
     * @see PandaMission
     */
    public List<PandaMission> getPandaMissions(){
        List<PandaMission> pandaMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PANDA)
                pandaMissions.add((PandaMission) mission);
        }
        return pandaMissions;
    }

    /**
     * <h2>{@link #getPeasantMissions()} :</h2>
     *
     * @return <b>The list of {@link PeasantMission} missions.</b>
     * @see PeasantMission
     */
    public List<PeasantMission> getPeasantMissions(){
        List<PeasantMission> peasantMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PEASANT)
                peasantMissions.add((PeasantMission) mission);
        }
        return peasantMissions;
    }
}
