package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Game {
    private final Resource resource = new Resource();
    private final Board board = new Board();
    private final ArrayList<Bot> botList = new ArrayList<>();
    private final int[] score_bots;
    private final int[] mission_done;
    private final int nbBot;
    private int turnLeft;

    static final int NB_MISSION = 4;

    Game(String[] botNames){
        nbBot = botNames.length;
        score_bots = new int[nbBot];
        mission_done = new int[nbBot];
        initializeBot(botNames);
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    void play() {
        int numBot = 0;
        turnLeft = nbBot;

        while(isContinue() != 0 && (!isOutOfSources())) {
            botList.get(numBot).botPlay();
            missionDone(numBot);
            numBot = (numBot+1) % nbBot;
        }
    }

    //Permet de verifier si un bot à fait suffisament de mission pour que la partie s'arrête
    int isContinue(){
        if(turnLeft == nbBot) {
            for (int mission : mission_done) {
                if (mission >= NB_MISSION)
                    turnLeft--;
            }
            return 1;
        }
        else
            turnLeft--;
        return turnLeft;
    }

    //Renvoie true si l'une ou plusieurs des ressources sont épuisées
    boolean isOutOfSources(){
        if (resource.getNbMission()==0)
            return true;
        if(resource.getParcel().size()==0)
            return true;
        return false;
    }

    /*Si une mission qu'un bot a est faites, sa mission est supprimée de son deck,
    il gagne les points de cette mission et on ajoute 1 à son compteur de mission faites*/
    void missionDone(int idBot) {
        int count;
        for(Mission mission : botList.get(idBot).getInventoryMission()){
            count = mission.checkMission(board,botList.get(idBot));
            if( count != 0){
                mission_done[idBot]++;
                score_bots[idBot] += count;
                botList.get(idBot).deleteMission(mission);
            }
        }
    }

    //Initialise les robots en fonction de leur nom associé passé en paramètre
    void initializeBot(String[] botNames){
        for (int i=0; i<nbBot; i++) {
            if (botNames[i].equals("random"))
                botList.add(new RandomBot(resource, board));
            else if (botNames[i].equals("intelligent"))
                    botList.add(new IntelligentBot(resource, board));
        }
    }

    //Renvoie l'objet contenant les ressources
    Resource getResource() {
        return resource;
    }

    //Renvoie la liste des robots
    ArrayList<Bot> getBotList() {
        return botList;
    }

    //Renvoie les scores des robots de la partie actuelle
    int[] getData() {
        return score_bots;
    }
}