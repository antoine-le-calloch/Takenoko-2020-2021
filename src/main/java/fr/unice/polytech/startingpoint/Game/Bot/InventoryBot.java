package fr.unice.polytech.startingpoint.Game.Bot;

import fr.unice.polytech.startingpoint.Game.Board.Object.Canal;
import fr.unice.polytech.startingpoint.Type.*;
import fr.unice.polytech.startingpoint.Game.Board.Mission.*;

import java.util.*;

public class InventoryBot {
    private final List<Mission> inventoryMission = new ArrayList<>();
    private final List<Canal> inventoryCanal = new ArrayList<>();
    private final int[] inventoryBamboo = new int[ColorType.values().length-1];

    public InventoryBot(){
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

    public List<Mission> getMission(){
        return inventoryMission;
    }

    public List<Canal> getCanal() {
        return inventoryCanal;
    }
}
