package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;

/**
 * Main, lance 1000 parties, des statistiques sont affichées
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class Main {

    public static void main(String... args){
        final int NB_GAME = 100;
        final int NB_PLAYER = 2;
        Stat statGame = new Stat(NB_GAME, NB_PLAYER); //creation des statistiques de l'ensemble des parties

        for (int i = 0; i < NB_GAME; i++) {
            Game game = new Game(new BotType[]{BotType.RANDOM, BotType.RANDOM}); //creation d'une partie
            game.play();
            statGame.add(game.getPlayerData());
        }

        System.out.println(statGame);
    }
}