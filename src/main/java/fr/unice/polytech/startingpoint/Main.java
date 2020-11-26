package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Main {

    public static void main(String... args) {
        final int NB_GAME = 100;
        final int NB_PLAYER = 2;
        Stat statGame = new Stat(NB_GAME, NB_PLAYER); //creation des statistiques de l'ensemble des parties
        for (int i = 0; i < NB_GAME; i++) {
            Game game = new Game(new String[]{"random","intelligent"}); //creation d'une partie
            game.play();
            statGame.add(game.getData());
        }
        System.out.println(statGame);
    }
}