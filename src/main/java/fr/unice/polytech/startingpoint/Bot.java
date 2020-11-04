package fr.unice.polytech.startingpoint;

public class Bot {
    GameState gameState;
    int num_bot;

    Bot(int num_bot, GameState gameState) {
        this.gameState = gameState;
        this.num_bot = num_bot;
    }

    boolean play() {
        return false;
    }

    int score() {
        return 0;
    }
}
