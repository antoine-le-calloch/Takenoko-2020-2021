package fr.unice.polytech.startingpoint.game.mission;

import fr.unice.polytech.startingpoint.game.playerdata.Inventory;
import fr.unice.polytech.startingpoint.type.ColorType;

/**
 * <h1>{@link PandaMission} :</h1>
 *
 * <p>This class create and check if the {@link PandaMission} is done.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see PeasantMission
 * @see ParcelMission
 * @version 0.5
 */

public final class PandaMission{
    private final ColorType colorType;
    private final int points;
    int NB_BAMBOO = 2;

    /**
     * <p>Set up a panda mission. Initialize all variables.</p>
     *
     * @param colorType
     *            <b>the colorType of the mission</b>
     * @param points
     *            <b>the points of the mission</b>
     */
    public PandaMission(ColorType colorType, int points){
        this.colorType = colorType;
        this.points = points;
    }

    /**<p>check panda if a mission is done</p>
     */
    public boolean checkMission(Inventory inventory) {
        if (colorType.equals(ColorType.ALL_COLOR))
            return checkMissionAllColor(inventory);
        else
            return checkMissionOneColor(inventory, colorType);
    }

    boolean checkMissionAllColor(Inventory inventory){
        int[] inventoryBamboo = inventory.getInventoryBamboo();
        if (inventoryBamboo[0] > 0 && inventoryBamboo[1] > 0 && inventoryBamboo[2] > 0){
            inventory.subOneBambooPerColor();
            return true;
        }
        return false;
    }

    boolean checkMissionOneColor(Inventory inventory, ColorType colorType){
        if (inventory.getInventoryBamboo(colorType) >= NB_BAMBOO) {
            inventory.subTwoBamboos(colorType);
            return true;
        }
        return false;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public int getPoints() {
        return points;
    }
}