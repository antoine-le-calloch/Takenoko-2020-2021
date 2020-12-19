package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.*;

import java.util.*;

/**
 * <h1>{@link PlayerData} :</h1>
 *
 * <p>This class provides an object to manage the bot inventory and turn.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Game
 * @version 0.5
 */

class PlayerData {
    private final Game game;
    private final Map<Bot,Inventory> botData;// Bot - int[ nbMission , score]
    private int numBot;
    private static int NB_MISSION;

    /**Initialize PlayerData and all variables.
     *
     * @param botTypes
     *                  <b>The lists of bots to initialize.</b>
     * @param game
     *                  <b>The game object.</b>
     * @param nbMission
     *                  <b>The number of missions to do to end the game.</b>
     */
    PlayerData(BotType[] botTypes, Game game, int nbMission) {
        this.game = game;
        botData = new LinkedHashMap<>();
        NB_MISSION = nbMission;
        numBot = 0;
        initializeBot(botTypes, game);
    }

    /**Initialize all bots.
     *
     * @param botTypes
     *                  <b>The lists of bots to initialize.</b>
     * @param game
     *                  <b>The game object.</b>
     */
    private void initializeBot(BotType[] botTypes, Game game){
        for (BotType botType : botTypes) {
            switch (botType) {
                case RANDOM:
                    botData.put(new RandomBot(game, game.getRules()), new Inventory());
                    break;
                case PARCEL_BOT:
                    botData.put(new ParcelBot(game, game.getRules()), new Inventory());
                    break;
                case PEASANT_BOT:
                    botData.put(new PeasantBot(game, game.getRules()), new Inventory());
                    break;
                case PANDA_BOT:
                    botData.put(new PandaBot(game, game.getRules()), new Inventory());
                    break;
            }
        }
    }

    /**Set the next bot to play.
     */
    void nextBot() {
        numBot = (numBot+1) % botData.size();
    }

    /**@return <b>True if nobody has done the number of missions required to win.</b>
     */
    boolean isContinue(){
        for (int missionDoneBy1P : getMissionsDone()) {
            if (missionDoneBy1P >= NB_MISSION)
                return false;
        }
        return true;
    }

    /**Check missions of the current bot and, if done, remove it and add points to the current inventory.
     */
    void missionDone(){
        List<Mission> toRemove = new ArrayList<>();
        int count = 0;
        for(Mission mission : getMissions()){
            if( (count = mission.checkMission(game.getBoard(),getInventory())) != 0){
                completedMission(count);
                toRemove.add(mission);
            }
        }
        subMissions(toRemove);
    }

    /**Add points to the current inventory.
     *
     * @param count
     *              <b>The number of points to add.</b>
     */
    void completedMission( int count) {
        getInventory().addScore(count);
    }

    /**Add a mission to the current inventory.
     *
     * @param mission
     *                  <b>The mission.</b>
     */
    void addMission(Mission mission) {
        getInventory().addMission(mission);
    }

    /**Add a canal to the current inventory.
     *
     * @param canal
     *                  <b>The canal.</b>
     */
    void addCanal(Canal canal) {
        getInventory().addCanal(canal);
    }

    /**Add a bamboo to the current inventory with the {@link ColorType} associated.
     *
     * @param colorType
     *                  <b>The bamboo color.</b>
     */
    void addBamboo(ColorType colorType){
        getInventory().addBamboo(colorType);
    }

    /**Remove a list of missions to remove from the current inventory.
     *
     * @param toRemove
     *                  <b>List of missions to remove.</b>
     */
    void subMissions(List<Mission> toRemove) {
        getInventory().subMissions(toRemove);
    }

    /**
     * @return <b>The current bot.</b>
     */
    Bot getBot() {
        return new ArrayList<>(botData.keySet()).get(numBot);
    }

    /**
     * @return <b>The inventory of the current bot.</b>
     */
    Inventory getInventory() {
        return botData.get(getBot());
    }

    /**
     * @return <b>The number of mission done by all bots.</b>
     */
    List<Integer> getMissionsDone() {
        List<Integer> missionsDone = new ArrayList<>();
        for (Inventory inventory : botData.values()){
            missionsDone.add(inventory.getMissionsDone());
        }
        return missionsDone;
    }

    /**
     * @return <b>The score of all bots.</b>
     */
    public List<Integer> getScores() {
        List<Integer> Score = new ArrayList<>();
        for (Inventory inventory : botData.values()) {
            Score.add(inventory.getScore());
        }
        return Score;
    }

    /**
     * @return <b>The {@link ParcelMission} list of the current bot.</b>
     */
    List<ParcelMission> getParcelMissions(){
        return getInventory().getParcelMissions();
    }

    /**
     * @return <b>The {@link PandaMission} list of the current bot.</b>
     */
    List<PandaMission> getPandaMissions(){
        return getInventory().getPandaMissions();
    }

    /**
     * @return <b>The {@link PeasantMission} list of the current bot.</b>
     */
    List<PeasantMission> getPeasantMissions(){
        return getInventory().getPeasantMissions();
    }

    /**
     * @return <b>The {@link Mission} list of the current bot.</b>
     */
    List<Mission> getMissions() {
        return getInventory().getMissions();
    }
}
