package fr.unice.polytech.startingpoint;

public class Bot {
    String name;
    int num_bot;

    Bot(String name, int num_bot) {
        this.name = name;
        this.num_bot = num_bot;
    }

    int play(GameState gameState) {
        Card board = gameState.getBoard();
        return 0;
    }
}
