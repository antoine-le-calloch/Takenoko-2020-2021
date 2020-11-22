package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatTest {
    Stat stat1Game;
    Stat stat1000Game;
    int[] j1w;
    int[] j2w;
    int[] equality;

    @BeforeEach
    public void initialize(){
        stat1Game = new Stat(1);
        stat1000Game = new Stat(1000);
        j1w = new int[]{2, 1};
        j2w = new int[]{1, 2};
        equality = new int[]{1, 1};
    }

    @Test
    public void playerOneWinner(){
        stat1Game.add(j1w);
        assertEquals("Joueur 1 : 100.0% win rate with a 2.0 points average\n" +
                "Joueur 2 : 0.0% win rate with a 1.0 points average",stat1Game.toString());
    }

    @Test
    public void P1Win700GameLose300(){
        for (int i = 0; i < 700; i++) {
            stat1000Game.add(j1w);
        }
        for (int i = 0; i < 300; i++) {
            stat1000Game.add(j2w);
        }
        assertEquals("Joueur 1 : 70.0% win rate with a 1.7 points average\n" +
                "Joueur 2 : 30.0% win rate with a 1.3 points average",stat1000Game.toString());
    }

    @Test
    public void playerTwoWinner(){
        stat1Game.add(j2w);
        assertEquals("Joueur 1 : 0.0% win rate with a 1.0 points average\n" +
                "Joueur 2 : 100.0% win rate with a 2.0 points average",stat1Game.toString());
    }

    @Test
    public void equalityCase(){
        stat1Game.add(equality);
        assertEquals("Joueur 1 : 0.0% win rate with a 1.0 points average\n" +
                "Joueur 2 : 0.0% win rate with a 1.0 points average",stat1Game.toString());
    }
}
