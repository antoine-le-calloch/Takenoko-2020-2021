package fr.unice.polytech.startingpoint;

public class GameData {
    Bot winner;
    int[] scores;

    GameData(Bot winner,int[] scores) {
        this.winner = winner;
        this.scores = scores;
    }
}
