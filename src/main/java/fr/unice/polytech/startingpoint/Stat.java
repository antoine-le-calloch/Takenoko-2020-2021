package fr.unice.polytech.startingpoint;

class Stat {
    private final int NB_PLAYER;
    private final int NB_GAME;
    private final int[] points;
    private final int[] nbWinNbEquality;

    Stat(int NB_GAME, int NB_PLAYER){
        this.NB_GAME = NB_GAME;
        this.NB_PLAYER = NB_PLAYER;
        points = new int[NB_PLAYER];
        nbWinNbEquality = new int[NB_PLAYER*2];
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
        int nbWinner = 0;
        int []Winner = new int[NB_PLAYER];
        
        for (int i = 0; i < NB_PLAYER; i++) {
            if(score[i] > bestScore) {
                bestScore=score[i];
            }
        }

        for (int i = 0; i < NB_PLAYER; i++) {
            if(score[i] == bestScore) {
                Winner[nbWinner]=i;
                nbWinner++;
            }
        }

        if(nbWinner==1)
            nbWinNbEquality[Winner[0]]++;
        else
            for (int i = 0; i < nbWinner; i++) {
                nbWinNbEquality[Winner[i]+NB_PLAYER]++;
            }
    }

    //affiche le message
    public String toString(){
        String displayStat = "";
        for (int i = 0; i < NB_PLAYER; i++) {
            displayStat += "Joueur " + (i+1) + " : " + nbWinNbEquality[i]/(NB_GAME/100.0) + "% win rate and " + nbWinNbEquality[i+NB_PLAYER]/(NB_GAME/100.0) + "% equality rate with a " + (points[i]*1.0)/NB_GAME + " points average\n";
        }
        return  displayStat;
    }
}
