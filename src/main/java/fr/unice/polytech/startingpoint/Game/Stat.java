package fr.unice.polytech.startingpoint.Game;

/**
 * La classe collecte les données de chaque partie et calcule des statistiques
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


public class Stat {
    private final int NB_PLAYER;
    private final int NB_GAME;
    private final int[] points;
    private final int[] nbWinNbEquality;

    public Stat(int NB_GAME, int NB_PLAYER){
        this.NB_GAME = NB_GAME;
        this.NB_PLAYER = NB_PLAYER;
        points = new int[NB_PLAYER];
        nbWinNbEquality = new int[NB_PLAYER*2];
    }

    //Ajoute les stats d'une nouvelles parties
    public void add(int[] data) {
        setWinner(data);
        for (int i = 0; i < NB_PLAYER; i++) {
            points[i] += data[i];
        }
    }

    //Fixe le nombre de victoires et d'égalités pour chaque joueur
    public void setWinner(int[] score){
        int bestScore = 0;
        int nbWinner = 0;
        int[] Winner = new int[NB_PLAYER];
        
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

    //Renvoie le taux de victoire du joueur passé en paramètre
    public double getWinRate(int joueur){
        return nbWinNbEquality[joueur]/(NB_GAME/100.0);
    }

    //Renvoie le taux d'égalité du joueur passé en paramètre
    public double getEqualityRate(int joueur){
        return nbWinNbEquality[joueur+NB_PLAYER]/(NB_GAME/100.0);
    }

    //Renvoie le nombres de points moyens du joueur passé en paramètre
    public double getPointsAverage(int joueur){
        return (points[joueur]*1.0)/NB_GAME;
    }

    //Renvoie les statistiques des parties sous forme de String
    public String toString(){
        String displayStat = "";
        for (int i = 0; i < NB_PLAYER; i++) {
            displayStat += "Joueur "+(i+1)+" : "+getWinRate(i)+"% win rate and "+ getEqualityRate(i)+"% equality rate with a "+ getPointsAverage(i)+" points average\n";
        }
        return  displayStat;
    }
}