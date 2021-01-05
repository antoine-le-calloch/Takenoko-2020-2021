package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.board.Resource;
import fr.unice.polytech.startingpoint.game.board.Rules;
import fr.unice.polytech.startingpoint.game.board.WeatherDice;
import fr.unice.polytech.startingpoint.game.playerdata.PlayerData;
import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.WeatherType;

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

public class Game{
    private final Board board;
    private final Rules rules;
    private final Resource resource;
    private final GameInteraction gameInteraction;
    private final List<PlayerData> botData;
    private final int NB_MISSION;
    private final int FIRST_BOT = 0;
    private final WeatherDice weatherDice;
    private boolean isFirstPlayer;
    private int numBot;
    private int round;
    private int lastRound;

    public Game(BotType[] botTypes){
        board = new Board();
        rules = new Rules(board);
        resource = new Resource();
        gameInteraction = new GameInteraction(this);
        botData = new LinkedList<>();
        NB_MISSION = 11 - botTypes.length;
        isFirstPlayer = true;
        numBot = FIRST_BOT;
        round = 0;
        lastRound = 0;
        weatherDice = new WeatherDice(new Random());
        initializeBot(botTypes);
    }

    public Game(){
        this(new BotType[]{BotType.RANDOM});
    }

    /**Initialize all bots.
     *
     * @param botTypes
     *                  <b>The lists of bots to initialize.</b>
     */
    private void initializeBot(BotType[] botTypes){
        for (BotType botType : botTypes) {
            switch (botType) {
                case RANDOM:
                    botData.add( new PlayerData( new RandomBot(gameInteraction) ) );
                    break;
                case PARCEL_BOT:
                    botData.add( new PlayerData( new ParcelBot(gameInteraction) ) );
                    break;
                case PEASANT_BOT:
                    botData.add( new PlayerData( new PeasantBot(gameInteraction) ) );
                    break;
                case PANDA_BOT:
                    botData.add( new PlayerData( new PandaBot(gameInteraction) ) );
                    break;
                case INTELLIGENT_BOT:
                    botData.add( new PlayerData( new IntelligentBot(gameInteraction) ) );
                    break;
            }
        }
    }

    void newRound(){
        round++;
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    public void play() {
        while( isContinue() ) {
            if(numBot==FIRST_BOT)
                newRound();
            botPlay();
            nextBot();
        }
    }

    /**<p>à partir du 2e tour le dé peut être roll</p>
     *
     *
     */
    void botPlay() {
        getPlayerData().botPlay((round < 2) ? WeatherType.NO_WEATHER : weatherDice.roll());
        getPlayerData().checkMissions(board.getPlacedParcels());
    }

    /**Set the next bot to play.
     */
    void nextBot() {
        numBot = (numBot+1) % botData.size();
    }

    /**@return <b>True if nobody has done the number of missions required to win.</b>
     */
    boolean isSomebodyFinished(){
        for (int missionDoneBy1P : getMissionsDone()) {
            if (missionDoneBy1P >= NB_MISSION) {
                if (isFirstPlayer) {
                    getPlayerData().addMissionDone(2);
                    isFirstPlayer = false;
                }
                return true;
            }
        }
        return false;
    }

    /**@return <b>True if the game is not done because a player finished his missions and the round is finished, and when resources aren't empty.</b>
     */
    boolean isContinue(){
        if (isSomebodyFinished()){
            lastRound++;
        }
        if (lastRound >= botData.size())
            return false;
        return !resource.isEmpty();
    }

    public Board getBoard() {
        return board;
    }

    public  Resource getResource() {
        return resource;
    }

    public Rules getRules() {
        return rules;
    }

    public GameInteraction getGameInteraction() {
        return gameInteraction;
    }

    /**
     * @return <b>The current playerData in use.</b>
     */
    public PlayerData getPlayerData(){
        return botData.get(numBot);
    }

    /**
     * @return <b>The number of mission done by all bots.</b>
     */
    public List<Integer> getMissionsDone() {
        List<Integer> missionsDone = new ArrayList<>();
        for (PlayerData playerData : botData){
            missionsDone.add(playerData.getMissionsDone());
        }
        return missionsDone;
    }

    /**
     * @return <b>The score of all bots.</b>
     */
    public List<Integer> getScores(){
        List<Integer> Score = new ArrayList<>();
        for (PlayerData playerData : botData) {
            Score.add(playerData.getScore());
        }
        return Score;
    }
}