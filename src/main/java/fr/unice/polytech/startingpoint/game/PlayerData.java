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

class PlayerData {
    private final Game game;
    private final Map<Bot,Inventory> botData;// Bot - int[ nbMission , score]
    private int numBot;
    private static int NB_MISSION;

    PlayerData(BotType[] botTypes, Game game, int nbMission) {
        this.game = game;
        botData = new HashMap<>();
        NB_MISSION = nbMission;
        numBot = 0;
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

    void nextBot() {
        numBot = (numBot+1) % botData.size();
    }

    //Permet de verifier si un bot à fait suffisament de mission pour que la partie s'arrête
    boolean isContinue(){
        for (int mission : getMissionsDone()) {
            if (mission >= NB_MISSION)
                return false;
        }
        return true;
    }

    /*Si une mission qu'un bot a est faites, sa mission est supprimée de son deck,
    il gagne les points de cette mission et on ajoute 1 à son compteur de mission faites*/
    void missionDone(){
        List<Mission> toRemove = new ArrayList<>();
        int count;  // PB SI LE BOT A PAS DE MISSION
        for(Mission mission : getMissions()){
            if( (count = mission.checkMission(game.getBoard(),getInventory())) != 0){
                completedMission(count);
                toRemove.add(mission);
            }
        }
        subMissions(toRemove);
    }

    void completedMission( int count) {
        getInventory().addScore(count);
    }

    void completedMission(int numBot, int count) {
        getInventory().addScore(count);
    }

    void addMission(Mission mission) {
        getInventory().addMission(mission);
    }

    void addCanal(Canal canal) {
        getInventory().addCanal(canal);
    }

    void addBamboo(ColorType colorType){
        getInventory().addBamboo(colorType);
    }

    void subMissions(List<Mission> toRemove) {
        getInventory().subMissions(toRemove);
    }

    Bot getBot() {
        return new ArrayList<>(botData.keySet()).get(numBot);
    }

    Inventory getInventory() {
        return botData.get(getBot());
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
