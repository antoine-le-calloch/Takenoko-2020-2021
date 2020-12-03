package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

class Game {
    private final Resource resource = new Resource();
    private final Board board = new Board();
    private final List<Bot> botList = new ArrayList<>();
    private final int[] scoreBots;
    private final int[] missionDone;
    private final int nbBot;
    private int turnLeft;

    static final int NB_MISSION = 4;

    Game(BotName[] botNames){
        nbBot = botNames.length;
        scoreBots = new int[nbBot];
        missionDone = new int[nbBot];
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
            for (int mission : missionDone) {
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
        List<Mission> toRemove = new ArrayList<>();
        int count;
        for(Mission mission : botList.get(idBot).getInventory().getMission()){
            if( (count = mission.checkMission(board,botList.get(idBot))) != 0){
                missionDone[idBot]++;
                scoreBots[idBot] += count;
                toRemove.add(mission);
            }
        }
        botList.get(idBot).inventory.subMissions(toRemove);
    }

    //Initialise les robots en fonction de leur nom associé passé en paramètre
    void initializeBot(BotName[] botNames){
        for (int i=0; i<nbBot; i++) {
            switch (botNames[i]) {
                case RANDOM:
                    botList.add(new RandomBot(resource, board));
                    break;
                case INTELLIGENT:
                    botList.add(new IntelligentBot(resource, board));
                    break;
            }
        }
    }

    //Renvoie l'objet contenant les ressources
    Resource getResource() {
        return resource;
    }

    //Renvoie la liste des robots
    List<Bot> getBotList() {
        return botList;
    }

    //Renvoie les scores des robots de la partie actuelle
    int[] getData() {
        return scoreBots;
    }
}