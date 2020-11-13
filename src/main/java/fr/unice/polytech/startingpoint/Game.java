package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Game {
    private final Resource resource = new Resource();
    private final Board board = new Board();
    private final ArrayList<Bot> botList = new ArrayList<>();
    private final int[] score_bots;
    private final int[] mission_done;

    Game(String[] botNames){
        score_bots = new int[botNames.length];
        mission_done = new int[botNames.length];
        for(int i = 0; i < botNames.length; i++){
            botList.add(new Bot(botNames[i]));
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
            int nbMissions = 3;
            if( mission < nbMissions){
                return true;
            }
        }
        return false;
    }

    void missionDone(int idBot, Board board) {
        int count;
        for(Mission mission : botList.get(idBot).getInventoryMission()){
            count = mission.checkMission(board);
            if( count != 0){
                mission_done[idBot]++;
                score_bots[idBot] += count;
                botList.get(idBot).deleteMission(mission);
            }
        }
    }

    Resource getResource() {
        return resource;
    }

    Board getBoard() {
        return board;
    }

    public ArrayList<Bot> getBotList() {
        return (ArrayList<Bot>) botList.clone();
    }

    public int[] getData() {
        return score_bots;
    }
}