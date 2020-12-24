package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.BotType;

/**
 * Main, lance 1000 parties, des statistiques sont affichées
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class Main{

    public static void main(String... args){
        final int NUMBER_OF_GAMES=1000;
        //ne pas faire jouer que des peasents et ou que des pandabot (boucle infini)
        BotType[] botList = new BotType[]{BotType.PARCEL_BOT,BotType.INTELLIGENT_BOT, BotType.PANDA_BOT, BotType.RANDOM, BotType.PEASANT_BOT};
        Stat statGame = new Stat(botList); //creation des statistiques de l'ensemble des parties

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Game game = new Game(botList); //creation d'une partie
            game.play();
            statGame.add(game.getScores());
        }

        System.out.println(statGame);
    }
}