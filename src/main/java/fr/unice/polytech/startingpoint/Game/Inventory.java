package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Type.*;

import java.util.*;

/**
 * Classe representant l'ensemble des inventaires que possède un bot
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


public class Inventory {
    private final List<Mission> inventoryMission = new ArrayList<>();
    private final List<Canal> inventoryCanal = new ArrayList<>();
    private final int[] inventoryBamboo = new int[ColorType.values().length-1];

    public Inventory(){
        for (int nbBamboo : inventoryBamboo){
            nbBamboo = 0;
        }
    }

    public void addBamboo(ColorType colorType){
        if (!colorType.equals(ColorType.NO_COLOR))
            inventoryBamboo[colorType.ordinal()] ++;
    }

    public void addMission(Mission mission){
        inventoryMission.add(mission);
    }

    public boolean subBamboo(ColorType colorType){
        if(inventoryBamboo[colorType.ordinal()]>0){
            inventoryBamboo[colorType.ordinal()] --;
            return true;
        }
        return false;
    }

    public void subMissions(List<Mission> missions){
        inventoryMission.removeAll(missions);
    }

    public int getBamboo(ColorType colorType){
        return inventoryBamboo[colorType.ordinal()];
    }

    public int[] getBamboo() {
        return inventoryBamboo.clone();
    }

    public List<Mission> getMissions(){
        return inventoryMission;
    }

    public List<ParcelMission> getParcelMissions(){
        List<ParcelMission> parcelMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PARCEL)
                parcelMissions.add((ParcelMission) mission);
        }
        return parcelMissions;
    }

    public List<PandaMission> getPandaMissions(){
        List<PandaMission> pandaMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PARCEL)
                pandaMissions.add((PandaMission) mission);
        }
        return pandaMissions;
    }

    public List<PeasantMission> getPeasantMissions(){
        List<PeasantMission> peasantMissions = new ArrayList<>();
        for (Mission mission : inventoryMission) {
            if(mission.missionType == MissionType.PARCEL)
                peasantMissions.add((PeasantMission) mission);
        }
        return peasantMissions;
    }
}
