package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.BotType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Moteur de jeu, creation d'une partie, fait jouer les bots, verifie les missions faites et termine la partie
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class Game{
    private final Resource resource;
    private final Board board;
    private final Rules rules;
    private final PlayerInteraction playerInteraction;
    private final List<PlayerData> botData;
    private final int NB_MISSION;
    private int numBot;

    Game(BotType[] botTypes,int nbMission,int stamina){
        resource = new Resource();
        board = new Board();
        rules = new Rules(board);
        playerInteraction = new PlayerInteraction(this);
        botData = new LinkedList<>();
        NB_MISSION = nbMission;
        numBot = 0;
        initializeBot(botTypes, stamina);
    }

    Game(BotType[] botTypes){
        this(botTypes,4,2);
    }

    Game(){
        this(new BotType[]{BotType.RANDOM});
    }

    /**Initialize all bots.
     *
     * @param botTypes
     *                  <b>The lists of bots to initialize.</b>
     * @param stamina
     *                  <b>The base stamina.</b>
     */
    private void initializeBot(BotType[] botTypes, int stamina){
        for (BotType botType : botTypes) {
            switch (botType) {
                case RANDOM:
                    botData.add( new PlayerData( new RandomBot(playerInteraction, rules ) , new Inventory() , new TemporaryInventory(stamina) ) );
                    break;
                case PARCEL_BOT:
                    botData.add( new PlayerData( new ParcelBot(playerInteraction, rules ) , new Inventory() , new TemporaryInventory(stamina) ) );
                    break;
                case PEASANT_BOT:
                    botData.add( new PlayerData( new PeasantBot(playerInteraction, rules ) , new Inventory() , new TemporaryInventory(stamina) ) );
                    break;
                case PANDA_BOT:
                    botData.add( new PlayerData( new PandaBot(playerInteraction, rules ) , new Inventory() , new TemporaryInventory(stamina) ) );
                    break;
                case INTELLIGENT_BOT:
                    botData.add( new PlayerData( new IntelligentBot(playerInteraction, rules ) , new Inventory() , new TemporaryInventory(stamina) ) );
                    break;
            }
        }
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    void play() {
        while( isContinue() && (!resource.isEmpty())) {
            botPlay();
            missionDone();
            nextBot();
        }
    }

    private void botPlay() {
        getPlayerData().resetTemporaryInventory();
        getPlayerData().getBot().botPlay();
        getPlayerData().hasPlayedCorrectly();
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
        int count;
        for(Mission mission : getPlayerData().getMissions()){
            if( (count = mission.checkMission(board,getPlayerData().getInventory())) != 0){
                completedMission(count);
                toRemove.add(mission);
            }
        }
        getPlayerData().subMissions(toRemove);
    }

    /**Add points to the current inventory.
     *
     * @param count
     *              <b>The number of points to add.</b>
     */
    void completedMission(int count) {
        getPlayerData().addScore(count);
    }

    Board getBoard() {
        return board;
    }

    Resource getResource() {
        return resource;
    }

    Rules getRules() {
        return rules;
    }

    PlayerInteraction getGameInteraction() {
        return playerInteraction;
    }

    /**
     * @return <b>The current playerData in use.</b>
     */
    PlayerData getPlayerData(){
        return botData.get(numBot);
    }

    TemporaryInventory getTemporaryInventory(){
        return getPlayerData().getTemporaryInventory();
    }

    /**
     * @return <b>The number of mission done by all bots.</b>
     */
    List<Integer> getMissionsDone() {
        List<Integer> missionsDone = new ArrayList<>();
        for (PlayerData playerData : botData){
            missionsDone.add(playerData.getMissionsDone());
        }
        return missionsDone;
    }

    /**
     * @return <b>The score of all bots.</b>
     */
    List<Integer> getScores(){
        List<Integer> Score = new ArrayList<>();
        for (PlayerData playerData : botData) {
            Score.add(playerData.getScore());
        }
        return Score;
    }
}