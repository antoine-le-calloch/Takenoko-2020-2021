package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Game {
    GameState gameState = new GameState();
    ArrayList<Bot> bots = new ArrayList<>();
    int[] scores;
    Bot winner;

    Game(int[] numBots){
        scores = new int[numBots.length];
        for(int i = 0; i < numBots.length; i++){
            bots.add(new Bot("bot"+(i+1),numBots[i]));
        }
    }

    void play() {
        int botTurn = 0;

        while(gameOver()){
            scores[botTurn] += bots.get(botTurn).play(gameState);
            botTurn = (botTurn + 1) % bots.size();
        }

        winner = getWinner();
    }

    private boolean gameOver() {
        return false;
    }

    private Bot getWinner() {
        return null;
    }

    GameData getData() {
        return new GameData(winner,scores);
    }
}