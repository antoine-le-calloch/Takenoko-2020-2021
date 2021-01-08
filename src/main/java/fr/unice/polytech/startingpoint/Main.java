package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.type.BotType;

/**
 * Main, lance 1000 parties, des statistiques sont affichées
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class Main{
    public static void main(String... args){
        final int NUMBER_OF_GAMES = 500;

        BotType[] botList = new BotType[]{BotType.INTELLIGENT_BOT,BotType.PANDA_BOT,BotType.PEASANT_BOT,BotType.PARCEL_BOT};
        Stat statGame = new Stat(botList);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Game game = new Game(false, botList);
            game.play();
            statGame.add(game.getScores());
        }

        BotType[] botList2 = new BotType[]{BotType.INTELLIGENT_BOT,BotType.INTELLIGENT_BOT};
        Stat statGame2 = new Stat(botList2);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Game game2 = new Game(false, botList2);
            game2.play();
            statGame2.add(game2.getScores());
        }

        System.out.println(statGame);

        System.out.println(statGame2);
    }
}