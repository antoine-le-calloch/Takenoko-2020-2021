package fr.unice.polytech.startingpoint;

public class Main {

    public static void main(String... args) {
        Stat statGame = new Stat();

        Game game = new Game(new String[]{"Bob","Bob"});
        game.play();
        statGame.add(game.getData());

        System.out.println(statGame);
    }

}