package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Iterator;

class Game {
    Resource resource = new Resource();
    Board board = new Board();
    ArrayList<Bot> botList = new ArrayList<>();
    int[] score_bots;
    int[] mission_done;
    private int nbMissions = 6;

    Game(int[] numBots){
        score_bots = new int[numBots.length];
        mission_done = new int[numBots.length];
        for(int i = 0; i < numBots.length; i++){
            botList.add(new Bot(numBots[i]));
        }
    }

    void play() {
        int numBot = 0;

        while(isContinue()) {
            botList.get(numBot).play(resource, board);
            missionDone(numBot,board);
            numBot = (numBot+1) % botList.size();
        }
    }

    boolean isContinue(){
        for (int mission : mission_done){
            if( mission < nbMissions){
                return true;
            }
        }
        return false;
    }

    void missionDone(int idBot, Board board) {
        Iterator<Mission> it = botList.get(idBot).inventoryMission.iterator();
        while (it.hasNext()) {
            int count = it.next().checkMission(board);
            if (count != 0) {
                it.remove();
                mission_done[idBot]++;
                score_bots[idBot] += count;
            }
        }
    }

    int[] getData() {
        return score_bots;
    }
}