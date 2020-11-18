package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Game {
    private final Resource resource = new Resource();
    private final Board board = new Board();
    private final ArrayList<Bot> botList = new ArrayList<>();
    private final int[] score_bots;
    private final int[] mission_done;
    private int nbMissions = 1;

    Game(String[] botNames){
        score_bots = new int[botNames.length];
        mission_done = new int[botNames.length];
        initializeBot(botNames);
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    void play() {
        int numBot = 0;

        while(isContinue()) {
            botList.get(numBot).play(resource, board);
            missionDone(numBot,board);
            numBot = (numBot+1) % botList.size();
        }
        for(int i = 0; i<botList.size()-1 ; i++){
            botList.get(numBot).play(resource, board);
            missionDone(numBot,board);
            numBot = (numBot+1) % botList.size();
        }
    }

    //Permet de verifier si un bot à fait suffisament de mission pour que la partie s'arrête
    boolean isContinue(){
        for (int mission : mission_done){
            if( mission >= nbMissions){
                return false;
            }
        }
        return true;
    }

    /*Si une mission qu'un bot a est faites, sa mission est supprimée de son deck,
    il gagne les points de cette mission et on ajoute 1 à son compteur de mission faites*/
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

    void initializeBot(String[] botNames){
        for (int i=0; i<botNames.length; i++) {
            if (botNames[i].equals("random"))
                botList.add(new RandomBot(botNames[i]));
            if (botNames[i].equals("intelligent"))
                botList.add(new IntelligentBot(botNames[i]));
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