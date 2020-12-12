package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe collecte les données de chaque partie et calcule des statistiques
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class Stat {
    private final List<List<Integer>> gameData;
    private final BotType[] botList;
    private final int[][] botScores;

    public Stat(BotType[] botList){
        gameData = new ArrayList<>();
        this.botList = botList;
        botScores = new int[botList.length][3];
        for (int i = 0; i < botList.length; i++){
            for (int j = 0; j < 3; j++){
                botScores[i][j] = 0;
            }
        }
    }

    //Ajoute les stats d'une nouvelles parties
    void add(List<Integer> integerList) {
        gameData.add(integerList);
        setWinner(getWinner(integerList));
        for (int i = 0; i < integerList.size(); i++){
            botScores[i][0] += integerList.get(i);
        }
    }

    //Fixe le nombre de victoires et d'égalités pour chaque joueur
    private void setWinner(List<Integer> winner){
        if (winner.size() == 1)
            botScores[winner.get(0)][1]++;
        else
            for(int win : winner){
                botScores[win][2]++;
            }
    }

    //Fixe le nombre de victoires et d'égalités pour chaque joueur
    private List<Integer> getWinner(List<Integer> scores){
        int bestScore = 0;
        List<Integer> winner = new ArrayList<>();
        for(int score : scores){
            if (score >= bestScore)
                bestScore = score;
        }
        for(int i = 0; i < botList.length; i++){
            if (scores.get(i) == bestScore)
                winner.add(i);
        }
        return winner;
    }

    //Renvoie le nombres de points moyens du joueur passé en paramètre
    public double getPointsAverage(int joueur){
        return (botScores[joueur][0]*1.0)/(gameData.size());
    }

    //Renvoie le taux de victoire du joueur passé en paramètre
    public double getWinRate(int joueur){
        return botScores[joueur][1]/((gameData.size())/100.0);
    }

    //Renvoie le taux d'égalité du joueur passé en paramètre
    public double getEqualityRate(int joueur){
        return botScores[joueur][2]/((gameData.size())/100.0);
    }

    //Renvoie les statistiques des parties sous forme de String
    public String toString(){
        StringBuilder displayStat = new StringBuilder();
        for (int i = 0; i < botScores.length; i++) {
            displayStat.append("Joueur ").append(botList[i]).append(" : ").append(getWinRate(i)).append("% win rate and ").append(getEqualityRate(i)).append("% equality rate with a ").append(getPointsAverage(i)).append(" points average\n");
        }
        return displayStat.toString();
    }
}