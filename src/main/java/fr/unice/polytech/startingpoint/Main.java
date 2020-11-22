package fr.unice.polytech.startingpoint;

public class Main {

    public static void main(String... args) {

        Stat statGame = new Stat(); //creation des statistiques de l'ensemble des parties

        for (int i = 0; i < 1000; i++) {
            Game game = new Game(new String[]{"random","intelligent"}); //creation d'une partie
            game.play();
            statGame.add(game.getData());
        }
        System.out.println(statGame);
    }
}