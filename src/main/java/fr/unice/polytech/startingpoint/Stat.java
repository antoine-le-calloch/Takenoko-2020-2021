package fr.unice.polytech.startingpoint;

class Stat {
    private final int NB_PLAYER = 2;
    private final int nbGame;
    private final int[] points = new int[NB_PLAYER];
    private final int[] winRate = new int[NB_PLAYER];

    Stat(int NB_GAME){
        nbGame = NB_GAME;
    }

    void add(int[] data) {
        getWinner(data);
        for (int i = 0; i < NB_PLAYER; i++) {
            points[i] += data[i];
        }
    }

    //return le message final
    void getWinner(int[] score){
        if(score[0]>score[1]) {
            winRate[0]++;
        }
        else if (score[0]<score[1]){
            winRate[1]++;
        }
    }

    //affiche le message
    public String toString(){
        return  "Joueur 1 : " + winRate[0]/(nbGame/100.0) + "% win rate with a " + (points[0]*1.0)/nbGame + " points average\n" +
                "Joueur 2 : " + winRate[1]/(nbGame/100.0) + "% win rate with a " + (points[1]*1.0)/nbGame + " points average";
    }
}
