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

    //ajoute les stats d'une nouvelles game
    void add(int[] data) {
        setWinner(data);
        for (int i = 0; i < NB_PLAYER; i++) {
            points[i] += data[i];
        }
    }

    //Set le nombre de victoire et d'égalité pour chaque player
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

    //return le win rate du joueur [joueur]
    double getWinRate(int joueur){
        return nbWinNbEquality[joueur]/(NB_GAME/100.0);
    }

    double getequalityRate(int joueur){
        return nbWinNbEquality[joueur+NB_PLAYER]/(NB_GAME/100.0);
    }

    double getpointsAverage(int joueur){
        return (points[joueur]*1.0)/NB_GAME;
    }

    //affiche le message
    public String toString(){
        String displayStat = "";
        for (int i = 0; i < NB_PLAYER; i++) {
            displayStat += "Joueur "+(i+1)+" : "+getWinRate(i)+"% win rate and "+getequalityRate(i)+"% equality rate with a "+getpointsAverage(i)+" points average\n";
        }
        return  displayStat;
    }
}
