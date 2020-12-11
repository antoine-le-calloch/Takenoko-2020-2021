package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.*;

import java.util.*;

/**
 * Classe stockant les données des joueurs au cours d'une partie
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.04
 */

public class PlayerData {
    private final Map<Bot,Inventory>  botData = new HashMap<>(); // Bot - int[ nbMission , score]
    private int numBot = 0;

    public PlayerData(BotType[] botTypes, Game game) {
        initializeBot(botTypes, game);
    }

    //Initialise les robots en fonction de leur nom associé passé en paramètre
    private void initializeBot(BotType[] botTypes, Game game){
        for (BotType botType : botTypes) {
            switch (botType) {
                case RANDOM:
                    botData.put(new RandomBot(game, game.getRules()), new Inventory());
                    break;
                case PARCELBOT:
                    botData.put(new ParcelBot(game, game.getRules()), new Inventory());
                    break;
                case PEASANTBOT:
                    botData.put(new PeasantBot(game, game.getRules()), new Inventory());
                    break;
                case PANDABOT:
                    botData.put(new PandaBot(game, game.getRules()), new Inventory());
                    break;
            }
        }
    }

    void completedMission( int count) {
        botData.get(getBot()).addScore(count);
    }

    void completedMission(int numBot, int count) {
        botData.get(new ArrayList<>(botData.keySet()).get(numBot)).addScore(count);
    }

    void nextBot() {
        numBot = (numBot+1) % botData.size();
    }

    void addMission(Mission mission) {
        botData.get(getBot()).addMission(mission);
    }

    void addCanal(Canal canal) {
        botData.get(getBot()).addCanal(canal);
    }

    void addBamboo(ColorType colorType){
        getInventory().addBamboo(colorType);
    }

    void subMissions(List<Mission> toRemove) {
        getInventory().subMissions(toRemove);
    }

    List<Integer> getMissionsDone() {
        List<Integer> missionsDone = new ArrayList<>();
        for (Inventory inventory : botData.values())
            missionsDone.add(inventory.getMissionsDone());
        return missionsDone;
    }

    public List<Integer> getScores() {
        List<Integer> Score = new ArrayList<>();
        for (Inventory inventory : botData.values())
            Score.add(inventory.getScore());
        return Score;
    }

    Bot getBot() {
        return new ArrayList<>(botData.keySet()).get(numBot);
    }

    Inventory getInventory() {
        return botData.get(getBot());
    }

    List<ParcelMission> getParcelMissions(){
        return getInventory().getParcelMissions();
    }

    List<PandaMission> getPandaMissions(){
        return getInventory().getPandaMissions();
    }

    List<PeasantMission> getPeasantMissions(){
        return getInventory().getPeasantMissions();
    }

    List<Mission> getMissions() {
        return getInventory().getMissions();
    }
}
