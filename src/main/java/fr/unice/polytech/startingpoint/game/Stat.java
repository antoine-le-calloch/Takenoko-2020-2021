package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.BotType;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>{@link Stat} :</h1>
 *
 * <p>This class provides a treatment of the scores given at the end of each game.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Game
 * @version 0.5
 */

public class Stat {
    private final List<List<Integer>> gameData;
    private final BotType[] botList;
    private final int[][] botScores;

    /**Initialize all variables.
     */
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

    /**Add the scores of a game to {@link #botScores}.
     */
    void add(List<Integer> integerList) {
        gameData.add(integerList);
        setWinner(getWinner(integerList));
        for (int i = 0; i < integerList.size(); i++){
            botScores[i][0] += integerList.get(i);
        }
    }

    /**Add a win to the winner, and add an equality to the winners .
     */
    private void setWinner(List<Integer> winner){
        if (winner.size() == 1)
            botScores[winner.get(0)][1]++;
        else
            for(int win : winner){
                botScores[win][2]++;
            }
    }

    /**@return <b>A list containing the bot(s) that have the highest score.</b>
     */
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

    /**@param player
     *              <b>The player we want the points’ average from.</b>
     *
     * @return <b>The average points from the player.</b>
     */
    public double getPointsAverage(int player){
        return (botScores[player][0]*1.0)/(gameData.size());
    }

    /**@param player
     *              <b>The player we want the win rate from.</b>
     *
     * @return <b>The win rate from the player.</b>
     */
    public double getWinRate(int player){
        return botScores[player][1]/((gameData.size())/100.0);
    }

    /**@param player
     *              <b>The player we want the equality rate from.</b>
     *
     * @return <b>The equality rate from the player.</b>
     */
    public double getEqualityRate(int player){
        return botScores[player][2]/((gameData.size())/100.0);
    }

    /**
     * @return <b>The stats from all players.</b>
     */
    public String toString(){
        StringBuilder displayStat = new StringBuilder();
        for (int i = 0; i < botScores.length; i++) {
            displayStat.append("Joueur ").append(botList[i]).append(" ").append(i+1).append(" : ").append(getWinRate(i)).append("% win rate and ").append(getEqualityRate(i)).append("% equality rate with a ").append(getPointsAverage(i)).append(" points average\n");
        }
        return displayStat.toString();
    }
}