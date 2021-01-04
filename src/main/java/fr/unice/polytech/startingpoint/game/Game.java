package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Moteur de jeu, creation d'une partie, fait jouer les bots, verifie les missions faites et termine la partie
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class Game{
    private final Board board;
    private final Rules rules;
    private final Resource resource;
    private final GameInteraction gameInteraction;
    private final List<PlayerData> botData;
    private final int NB_MISSION;
    private int numBot;
    private int round;
    private final int FIRST_BOT=0;
    WeatherDice weatherDice;

    private Game(BotType[] botTypes, int nbMission, int stamina){
        board = new Board();
        rules = new Rules(board);
        resource = new Resource();
        gameInteraction = new GameInteraction(this);
        botData = new LinkedList<>();
        NB_MISSION = nbMission;
        numBot = FIRST_BOT;
        round=0;
        initializeBot(botTypes, stamina);
        weatherDice=new WeatherDice(new Random());
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
                    botData.add( new PlayerData( new RandomBot(gameInteraction), stamina ) );
                    break;
                case PARCEL_BOT:
                    botData.add( new PlayerData( new ParcelBot(gameInteraction), stamina ) );
                    break;
                case PEASANT_BOT:
                    botData.add( new PlayerData( new PeasantBot(gameInteraction), stamina ) );
                    break;
                case PANDA_BOT:
                    botData.add( new PlayerData( new PandaBot(gameInteraction), stamina ) );
                    break;
                case INTELLIGENT_BOT:
                    botData.add( new PlayerData( new IntelligentBot(gameInteraction), stamina ) );
                    break;
            }
        }
    }

    void newRound(){
        round++;
    }
    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    void play() {
        while( isContinue() ) {
            if(numBot==FIRST_BOT)
                newRound();
            botPlay();
            nextBot();
        }
    }


    WeatherType weatherDiceRoll(){
        return weatherDice.roll();
    }


    /**<p>à partir du 2e tour le dé peut être roll</p>
     *
     *
     */
    void botPlay() {
        if(round<2)
            getPlayerData().botPlay(WeatherType.NO_WEATHER);
        else
            getPlayerData().botPlay(weatherDiceRoll());
        getPlayerData().checkMissions(board.getPlacedParcels());
    }

    /**Set the next bot to play.
     */
    private void nextBot() {
        numBot = (numBot+1) % botData.size();
    }

    /**@return <b>True if nobody has done the number of missions required to win.</b>
     */
    private boolean isContinue(){
        for (int missionDoneBy1P : getMissionsDone()) {
            if (missionDoneBy1P >= NB_MISSION)
                return false;
        }
        if(resource.isEmpty()) {
            return false;
        }
        return true;
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

    GameInteraction getGameInteraction() {
        return gameInteraction;
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
    private List<Integer> getMissionsDone() {
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