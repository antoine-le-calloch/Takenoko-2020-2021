package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Type.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Moteur de jeu, creation d'une partie, fait jouer les bots, verifie les missions faites et termine la partie
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class Game {
    private final Resource resource = new Resource();
    private final Board board = new Board();
    private final PlayerData playerData;
    static final int NB_MISSION = 4;

    public Game(BotType[] botTypes){
        this.playerData = new PlayerData(botTypes, resource, board);
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    public void play() {
        int numBot = 0;

        while(isContinue() && (!resource.isEmpty())) {
            playerData.get(numBot).botPlay();
            missionDone(numBot);
            numBot = (numBot+1) % playerData.size();
        }
    }

    //Permet de verifier si un bot à fait suffisament de mission pour que la partie s'arrête
    public boolean isContinue(){
        for (int mission : playerData.getMissions()) {
            if (mission >= NB_MISSION)
                return false;
        }
        return true;
    }

    /*Si une mission qu'un bot a est faites, sa mission est supprimée de son deck,
    il gagne les points de cette mission et on ajoute 1 à son compteur de mission faites*/
    public void missionDone(int numBot) {
        List<Mission> toRemove = new ArrayList<>();
        int count;  // PB SI LE BOT A PAS DE MISSION
        for(Mission mission : playerData.get(numBot).getInventory().getMissions()){
            if( (count = mission.checkMission(board,playerData.get(numBot).getInventory())) != 0){
                playerData.completedMission(numBot, count);
                toRemove.add(mission);
            }
        }
        playerData.get(numBot).getInventory().subMissions(toRemove);
    }

    //Renvoie l'objet contenant les ressources
    public Resource getResource() {
        return resource;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}