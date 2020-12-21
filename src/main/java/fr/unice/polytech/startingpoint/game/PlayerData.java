package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;

import java.util.ArrayList;
import java.util.List;

public final class PlayerData {
    private final Bot bot;
    private final Inventory inventory;
    private final TemporaryInventory temporaryInventory;
    private int score;
    private int missionsDone;

    public PlayerData(Bot bot, Inventory inventory,TemporaryInventory temporaryInventory){
        this.bot = bot;
        this.inventory = inventory;
        this.temporaryInventory = temporaryInventory;
        score = 0;
        missionsDone = 0;
    }

    void botPlay() {
        resetTemporaryInventory();
        bot.botPlay();
        hasPlayedCorrectly();
        missionDone();
    }

    void missionDone(){
        List<Mission> toRemove = new ArrayList<>();
        for(Mission mission : getMissions()){
            if(mission.checkMission(inventory)){
                addScore(mission.getPoints());
                toRemove.add(mission);
            }
        }
        subMissions(toRemove);
    }

    boolean add(ActionType actionType) {
        return temporaryInventory.add(actionType);
    }

    void remove(ActionType actionType) {
        temporaryInventory.remove(actionType);
    }

    boolean contains(ActionType actionType) {
        return temporaryInventory.contains(actionType);
    }

    void looseStamina() throws OutOfResourcesException {
        temporaryInventory.looseStamina();
    }

    void saveParcels(List<Parcel> parcelList) {
        temporaryInventory.saveParcels(parcelList);
    }

    void saveParcel(Parcel parcel) {
        temporaryInventory.saveParcel(parcel);
    }

    Parcel getParcel(){
        return temporaryInventory.getParcel();
    }

    List<Parcel> getParcelsSaved() {
        return temporaryInventory.getParcelsSaved();
    }

    void hasPlayedCorrectly() {
        temporaryInventory.hasPlayedCorrectly();
    }

    void resetTemporaryInventory() {
        temporaryInventory.reset();
    }

    void addCanal(Canal canal) {
        inventory.addCanal(canal);
    }

    Canal pickCanal() throws OutOfResourcesException {
        return inventory.pickCanal();
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

    TemporaryInventory getTemporaryInventory() {
        return temporaryInventory;
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