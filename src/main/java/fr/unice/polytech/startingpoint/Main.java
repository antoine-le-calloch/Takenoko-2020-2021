package fr.unice.polytech.startingpoint;

public class Main {

    public static void main(String... args) throws ExceptionTakenoko {
        final int NB_GAME = 1000;
        final int NB_PLAYER = 2;
        Stat statGame = new Stat(NB_GAME, NB_PLAYER); //creation des statistiques de l'ensemble des parties

        for (int i = 0; i < NB_GAME; i++) {
            Game game = new Game(new BotName[]{BotName.RANDOM,BotName.INTELLIGENT}); //creation d'une partie
            game.play();
            statGame.add(game.getData());
        }

        System.out.println(statGame);
    }
}