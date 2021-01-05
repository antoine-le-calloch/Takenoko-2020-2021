package fr.unice.polytech.startingpoint.game.playerdata;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.game.board.Canal;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerData {
    private final Bot bot;
    private final Inventory inventory;
    private final TemporaryInventory temporaryInventory;
    private int score;
    private int missionsDone;
    private int missionsPandaDone;

    public PlayerData(Bot bot){
        this.bot = bot;
        inventory = new Inventory();
        temporaryInventory = new TemporaryInventory();
        score = 0;
        missionsDone = 0;
        missionsPandaDone = 0;
    }

    public void botPlay(WeatherType weatherType) {
        resetTemporaryInventory(weatherType);
        bot.botPlay();
        hasPlayedCorrectly();
    }

    public void checkMissions(Map<Coordinate, Parcel> coordinateParcelMap){
        List<PandaMission> toRemovePandaMission = new ArrayList<>();
        List<ParcelMission> toRemoveParcelMission = new ArrayList<>();
        List<PeasantMission> toRemovePeasantMission = new ArrayList<>();

        for(PandaMission pandaMission : inventory.getPandaMissions()){
            if(pandaMission.checkMission(inventory)){
                addMissionDone(pandaMission.getPoints());
                missionsPandaDone ++;
                toRemovePandaMission.add(pandaMission);
            }
        }

        for(ParcelMission parcelMission : inventory.getParcelMissions()){
            if(parcelMission.checkMission(coordinateParcelMap)){
                addMissionDone(parcelMission.getPoints());
                toRemoveParcelMission.add(parcelMission);
            }
        }

        for(PeasantMission peasantMission : inventory.getPeasantMissions()){
            if(peasantMission.checkMission(new ArrayList<>(coordinateParcelMap.values()))){
                addMissionDone(peasantMission.getPoints());
                toRemovePeasantMission.add(peasantMission);
            }
        }

        inventory.subPandaMissions(toRemovePandaMission);
        inventory.subParcelMissions(toRemoveParcelMission);
        inventory.subPeasantMissions(toRemovePeasantMission);
    }

    public boolean add(ActionType actionType) {
        return temporaryInventory.add(actionType);
    }

    public void remove(ActionType actionType) {
        temporaryInventory.remove(actionType);
    }

    public boolean contains(ActionType actionType) {
        return temporaryInventory.contains(actionType);
    }

    public void looseStamina(){
        temporaryInventory.looseStamina();
    }

    public void saveParcels(List<Parcel> parcelList) {
        temporaryInventory.saveParcels(parcelList);
    }

    public void saveParcel(Parcel parcel) {
        temporaryInventory.saveParcel(parcel);
    }

    public Parcel getParcel(){
        return temporaryInventory.getParcel();
    }

    public List<Parcel> getParcelsSaved() {
        return temporaryInventory.getParcelsSaved();
    }

    public void hasPlayedCorrectly() {
        temporaryInventory.hasPlayedCorrectly();
    }

    public void resetTemporaryInventory(WeatherType weatherType) {
        temporaryInventory.reset(weatherType);
    }

    public void addCanal(Canal canal) {
        inventory.addCanal(canal);
    }

    public Canal pickCanal(){
        return inventory.pickCanal();
    }

    public void addBamboo(ColorType colorType){
        inventory.addBamboo(colorType);
    }

    public void addMission(PandaMission mission){
        inventory.addPandaMission(mission);
    }

    public void addMission(ParcelMission mission){
        inventory.addParcelMission(mission);
    }

    public void addMission(PeasantMission mission){
        inventory.addPeasantMission(mission);
    }

    public void addMissionDone(int score){
        this.score += score;
        missionsDone++;
    }

    public void addMissionDone(){
        addMissionDone(0);
    }

    public int[] getScore() {
        return new int[]{score,missionsPandaDone};
    }

    public int getMissionsDone() {
        return missionsDone;
    }

    public Bot getBot() {
        return bot;
    }

    public List<ActionType> getActionTypeList() {
        return temporaryInventory.getActionTypeList();
    }

    public int getStamina() {
        return temporaryInventory.getStamina();
    }

    public boolean isActionCouldBeDoneTwice() {
        return temporaryInventory.isActionCouldBeDoneTwice();
    }

    public WeatherType getWeatherType() {
        return temporaryInventory.getWeatherType();
    }

    public Inventory getInventory() {
        return inventory;
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

    public int[] getInventoryBamboo() {
        return  inventory.getInventoryBamboo();
    }
}