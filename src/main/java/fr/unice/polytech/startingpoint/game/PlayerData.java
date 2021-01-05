package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PlayerData {
    private final Bot bot;
    private final Inventory inventory;
    private final TemporaryInventory temporaryInventory;
    private int score;
    private int missionsDone;

    public PlayerData(Bot bot, int stamina){
        this.bot = bot;
        inventory = new Inventory();
        temporaryInventory = new TemporaryInventory(stamina);
        score = 0;
        missionsDone = 0;
    }

    void botPlay(WeatherType weatherType) {
        resetTemporaryInventory(weatherType);
        bot.botPlay(weatherType);
        hasPlayedCorrectly();
    }

    public void checkMissions(Map<Coordinate,Parcel> coordinateParcelMap){
        List<PandaMission> toRemovePandaMission = new ArrayList<>();
        List<ParcelMission> toRemoveParcelMission = new ArrayList<>();
        List<PeasantMission> toRemovePeasantMission = new ArrayList<>();

        for(PandaMission pandaMission : inventory.getPandaMissions()){
            if(pandaMission.checkMission(inventory)){
                addScore(pandaMission.getPoints());
                toRemovePandaMission.add(pandaMission);
            }
        }

        for(ParcelMission parcelMission : inventory.getParcelMissions()){
            if(parcelMission.checkMission(coordinateParcelMap)){
                addScore(parcelMission.getPoints());
                toRemoveParcelMission.add(parcelMission);
            }
        }

        for(PeasantMission peasantMission : inventory.getPeasantMissions()){
            if(peasantMission.checkMission(new ArrayList<>(coordinateParcelMap.values()))){
                addScore(peasantMission.getPoints());
                toRemovePeasantMission.add(peasantMission);
            }
        }

        inventory.subPandaMissions(toRemovePandaMission);
        inventory.subParcelMissions(toRemoveParcelMission);
        inventory.subPeasantMissions(toRemovePeasantMission);
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

    void looseStamina(){
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

    void resetTemporaryInventory(WeatherType weatherType) {
        temporaryInventory.reset(weatherType);
    }

    void addCanal(Canal canal) {
        inventory.addCanal(canal);
    }

    Canal pickCanal(){
        return inventory.pickCanal();
    }

    void addBamboo(ColorType colorType){
        inventory.addBamboo(colorType);
    }

    void addMission(PandaMission mission){
        inventory.addPandaMission(mission);
    }

    void addMission(ParcelMission mission){
        inventory.addParcelMission(mission);
    }

    void addMission(PeasantMission mission){
        inventory.addPeasantMission(mission);
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

    int getStamina() {
        return temporaryInventory.getStamina();
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

    public List<ParcelMission> getParcelMissions() {
        return inventory.getParcelMissions();
    }

    public List<PandaMission> getPandaMissions() {
        return inventory.getPandaMissions();
    }

    public List<PeasantMission> getPeasantMissions() {
        return inventory.getPeasantMissions();
    }

    public int[] getInventoryBamboo() { return  inventory.getInventoryBamboo();
    }
}