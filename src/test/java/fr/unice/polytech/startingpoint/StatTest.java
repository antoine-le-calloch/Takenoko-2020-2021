package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatTest {
    Stat stat1Game1P;
    Stat stat1Game2P;
    Stat stat1000Game2P;
    Stat stat1000Game4P;

    int[] p1w;

    int[] p1w2Player;
    int[] p2w2Player;
    int[] equality2Player;

    int[] p1w4Player;
    int[] p2w4Player;

    @BeforeEach
    public void initialize(){
        stat1Game1P = new Stat(1,1);
        stat1Game2P = new Stat(1,2);
        stat1000Game2P = new Stat(1000,2);
        stat1000Game4P = new Stat(1000,4);

        p1w = new int[]{2};

        p1w2Player = new int[]{2, 1};
        p2w2Player = new int[]{1, 2};
        equality2Player = new int[]{1, 1};

        p1w4Player = new int[]{9, 5, 3, 1};
        p2w4Player = new int[]{2, 7, 4, 3};
    }

    @Test
    public void p1Winner2Player(){
        stat1Game2P.add(p1w2Player);
        assertEquals(100,stat1Game2P.getWinRate(0));
        assertEquals(0,stat1Game2P.getWinRate(1));

        assertEquals(0,stat1Game2P.getequalityRate(0));
        assertEquals(0,stat1Game2P.getequalityRate(1));

        assertEquals(2,stat1Game2P.getpointsAverage(0));
        assertEquals(1,stat1Game2P.getpointsAverage(1));
    }

    @Test
    public void p2Winner2Player(){
        stat1Game2P.add(p2w2Player);
        assertEquals(0,stat1Game2P.getWinRate(0));
        assertEquals(100,stat1Game2P.getWinRate(1));

        assertEquals(0,stat1Game2P.getequalityRate(0));
        assertEquals(0,stat1Game2P.getequalityRate(1));

        assertEquals(1,stat1Game2P.getpointsAverage(0));
        assertEquals(2,stat1Game2P.getpointsAverage(1));
    }

    @Test
    public void equalityCase2Player(){
        stat1Game2P.add(equality2Player);
        assertEquals(0,stat1Game2P.getWinRate(0));
        assertEquals(0,stat1Game2P.getWinRate(1));

        assertEquals(100,stat1Game2P.getequalityRate(0));
        assertEquals(100,stat1Game2P.getequalityRate(1));

        assertEquals(1,stat1Game2P.getpointsAverage(0));
        assertEquals(1,stat1Game2P.getpointsAverage(1));
    }

    @Test
    public void p1Win700GameLose3002Player(){
        for (int i = 0; i < 1000; i++) {
            if(i<700)
                stat1000Game2P.add(p1w2Player);
            else
                stat1000Game2P.add(p2w2Player);
        }
        assertEquals(70,stat1000Game2P.getWinRate(0));
        assertEquals(30,stat1000Game2P.getWinRate(1));

        assertEquals(0,stat1000Game2P.getequalityRate(0));
        assertEquals(0,stat1000Game2P.getequalityRate(1));

        assertEquals(1.7,stat1000Game2P.getpointsAverage(0));
        assertEquals(1.3,stat1000Game2P.getpointsAverage(1));
    }

    @Test
    public void p1Win700GameLose3004Player(){
        for (int i = 0; i < 1000; i++) {
            if(i<700)
                stat1000Game4P.add(p1w4Player);
            else
                stat1000Game4P.add(p2w4Player);
        }

        assertEquals(70,stat1000Game4P.getWinRate(0));
        assertEquals(30,stat1000Game4P.getWinRate(1));
        assertEquals(0,stat1000Game4P.getWinRate(2));
        assertEquals(0,stat1000Game4P.getWinRate(3));

        assertEquals(0,stat1000Game4P.getequalityRate(0));
        assertEquals(0,stat1000Game4P.getequalityRate(1));
        assertEquals(0,stat1000Game4P.getequalityRate(2));
        assertEquals(0,stat1000Game4P.getequalityRate(3));

        assertEquals(6.9,stat1000Game4P.getpointsAverage(0));
        assertEquals(5.6,stat1000Game4P.getpointsAverage(1));
        assertEquals(3.3,stat1000Game4P.getpointsAverage(2));
        assertEquals(1.6,stat1000Game4P.getpointsAverage(3));
    }

    @Test
    public void p1W1PToString(){
        stat1Game1P.add(p1w);
        assertEquals("Joueur 1 : 100.0% win rate and 0.0% equality rate with a 2.0 points average\n",stat1Game1P.toString());
    }
}
