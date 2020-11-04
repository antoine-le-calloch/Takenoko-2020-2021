package fr.unice.polytech.startingpoint;

public class Game {
    Game(int num_bot1, int num_bot2){
        Bot bot = new Bot(num_bot1);
        Bot bot1 = new Bot(num_bot2);
        Board board = new Board();
    }

    void play() {
    }

    int[] getData() {
        return null;
    }
}