package fr.unice.polytech.startingpoint;

import java.util.*;

class Inventory {
    private final List<Mission> inventoryMission = new ArrayList<>();
    private final List<Canal> inventoryCanal = new ArrayList<>();
    private final int[] inventoryBamboo = new int[Color.values().length-1];

    Inventory(){
        for (int nbBamboo : inventoryBamboo){
            nbBamboo = 0;
        }
    }

    void addBamboo(Color color){
        if (!color.equals(Color.NO_COLOR))
            inventoryBamboo[color.ordinal()] ++;
    }

    void addMission(Mission mission){
        inventoryMission.add(mission);
    }

    boolean subBamboo(Color color){
        if(inventoryBamboo[color.ordinal()]>0){
            inventoryBamboo[color.ordinal()] --;
            return true;
        }
        return false;
    }

    void subMissions(List<Mission> missions){
        inventoryMission.removeAll(missions);
    }

    int getBamboo(Color color){
        return inventoryBamboo[color.ordinal()];
    }

    int[] getBamboo() {
        return inventoryBamboo.clone();
    }

    List<Mission> getMission(){
        return inventoryMission;
    }

    List<Canal> getCanal() {
        return inventoryCanal;
    }
}
