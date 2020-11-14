package fr.unice.polytech.startingpoint;

public class Main {

    public static void main(String... args) {
        Stat statGame = new Stat(); //creation des statistiques de l'ensemble des parties

        Game game = new Game(new String[]{"Bob","Bob"}); //creation d'une partie
        game.play();
        statGame.add(game.getData());

        System.out.println(statGame);
    }

}