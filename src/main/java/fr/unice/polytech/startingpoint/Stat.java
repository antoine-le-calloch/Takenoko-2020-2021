package fr.unice.polytech.startingpoint;

class Stat {
    private final int NB_PLAYER;
    private final int NB_GAME;
    private final int[] points;
    private final int[] winRate;

    Stat(int NB_GAME, int NB_PLAYER){
        this.NB_GAME = NB_GAME;
        this.NB_PLAYER = NB_PLAYER;
        points = new int[NB_PLAYER];
        winRate = new int[NB_PLAYER];
    }

    void add(int[] data) {
        setWinner(data);
        for (int i = 0; i < NB_PLAYER; i++) {
            points[i] += data[i];
        }
    }

    //return le message final
    void setWinner(int[] score){
        int bestScore = 0;
        for (int i = 0; i < NB_PLAYER; i++) {
            if(score[i] > bestScore) {
                bestScore=score[i];
            }
        }
        for (int i = 0; i < NB_PLAYER; i++) {
            if(score[i] == bestScore) {
                winRate[i]++;
            }
        }
    }

    //affiche le message
    public String toString(){
        String displayStat = "";
        for (int i = 0; i < NB_PLAYER; i++) {
            displayStat += "Joueur " + (i+1) + " : " + winRate[i]/(NB_GAME/100.0) + "% win rate with a " + (points[i]*1.0)/NB_GAME + " points average\n";
        }
        return  displayStat;
    }
}
