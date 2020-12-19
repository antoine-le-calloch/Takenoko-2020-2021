package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.type.ColorType;

import java.util.List;

public class PlayerData {
    private final Bot bot;
    private final Inventory inventory;
    private int score;
    private int missionsDone;

    public PlayerData(Bot bot, Inventory inventory){
        this.bot = bot;
        this.inventory = inventory;
        score = 0;
        missionsDone = 0;
    }

    void addCanal(Canal canal) {
        inventory.addCanal(canal);
    }

    void addBamboo(ColorType colorType){
        inventory.addBamboo(colorType);
    }

    void addMission(Mission mission){
        inventory.addMission(mission);
    }

    void subMissions(List<Mission> toRemove) {
        inventory.subMissions(toRemove);
    }

    void addScore(int score){
        this.score += score;
        missionsDone ++;
    }

    int getScore() {
        return score;
    }

    int getMissionsDone() {
        return missionsDone;
    }

    Bot getBot() {
        return bot;
    }

    Inventory getInventory() {
        return inventory;
    }

    List<ParcelMission> getParcelMissions() {
        return inventory.getParcelMissions();
    }

    List<PandaMission> getPandaMissions() {
        return inventory.getPandaMissions();
    }

    List<PeasantMission> getPeasantMissions() {
        return inventory.getPeasantMissions();
    }

    List<Mission> getMissions() {
        return inventory.getMissions();
    }
}
