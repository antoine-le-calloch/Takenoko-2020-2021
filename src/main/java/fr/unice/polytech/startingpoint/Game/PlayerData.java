package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Type.BotType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe stockant les données des joueurs au cours d'une partie
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.04
 */

public class PlayerData {
    private final Map<Bot,int []>  botData = new HashMap<>(); // Bot - int[ nbMission , score]

    PlayerData(BotType[] botTypes, Resource resource, Board board) {
        initializeBot(botTypes, resource, board);
    }

    PlayerData(int ... score){

    }

    //Initialise les robots en fonction de leur nom associé passé en paramètre
    public void initializeBot(BotType[] botTypes, Resource resource, Board board){
        for (BotType botType : botTypes) {
            switch (botType) {
                case RANDOM:
                    botData.put(new RandomBot(resource, board), new int[]{0, 0});
                    break;
                case PARCELBOT:
                    botData.put(new ParcelBot(resource, board), new int[]{0, 0});
                    break;
                case PEASANTBOT:
                    botData.put(new PeasantBot(resource, board), new int[]{0, 0});
                    break;
                case PANDABOT:
                    botData.put(new PandaBot(resource, board), new int[]{0, 0});
                    break;
            }
        }
    }

    public Bot get(int numBot) {
        return new ArrayList<>(botData.keySet()).get(numBot);
    }

    int size(){
        return botData.size();
    }

    List<Integer> getMissions() {
        List<Integer> missionsDone = new ArrayList<>();
        for (int[] value : botData.values())
            missionsDone.add(value[0]);
        return missionsDone;
    }

    public List<Integer> getScores() {
        List<Integer> Score = new ArrayList<>();
        for (int[] value : botData.values())
            Score.add(value[1]);
        return Score;
    }

    public void completedMission(int numBot, int count) {
        int[] nb = botData.get(get(numBot));
        nb[0] ++;
        nb[1] += count;
        botData.replace(get(numBot), nb);
    }

}
