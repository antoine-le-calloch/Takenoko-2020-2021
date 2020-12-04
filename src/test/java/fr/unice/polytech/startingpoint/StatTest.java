package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.PlayerData;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */
/*
public class StatTest {
    Stat stat1Game1P;
    Stat stat1Game2P;
    Stat stat1000Game2P;
    Stat stat1000Game4P;

    PlayerData p1w;

    PlayerData p1w2Player;
    PlayerData p2w2Player;
    PlayerData equality2Player;

    PlayerData p1w4Player;
    PlayerData p2w4Player;

    @BeforeEach
    public void initialize(){

    }

    @Test
    public void p1Winner2Player(){
        stat1Game2P.add(p1w2Player);
        assertEquals(100,stat1Game2P.getWinRate(0));
        assertEquals(0,stat1Game2P.getWinRate(1));

        assertEquals(0,stat1Game2P.getEqualityRate(0));
        assertEquals(0,stat1Game2P.getEqualityRate(1));

        assertEquals(2,stat1Game2P.getPointsAverage(0));
        assertEquals(1,stat1Game2P.getPointsAverage(1));
    }

    @Test
    public void p2Winner2Player(){
        stat1Game2P.add(p2w2Player);
        assertEquals(0,stat1Game2P.getWinRate(0));
        assertEquals(100,stat1Game2P.getWinRate(1));

        assertEquals(0,stat1Game2P.getEqualityRate(0));
        assertEquals(0,stat1Game2P.getEqualityRate(1));

        assertEquals(1,stat1Game2P.getPointsAverage(0));
        assertEquals(2,stat1Game2P.getPointsAverage(1));
    }

    @Test
    public void equalityCase2Player(){
        stat1Game2P.add(equality2Player);
        assertEquals(0,stat1Game2P.getWinRate(0));
        assertEquals(0,stat1Game2P.getWinRate(1));

        assertEquals(100,stat1Game2P.getEqualityRate(0));
        assertEquals(100,stat1Game2P.getEqualityRate(1));

        assertEquals(1,stat1Game2P.getPointsAverage(0));
        assertEquals(1,stat1Game2P.getPointsAverage(1));
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

        assertEquals(0,stat1000Game2P.getEqualityRate(0));
        assertEquals(0,stat1000Game2P.getEqualityRate(1));

        assertEquals(1.7,stat1000Game2P.getPointsAverage(0));
        assertEquals(1.3,stat1000Game2P.getPointsAverage(1));
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

        assertEquals(0,stat1000Game4P.getEqualityRate(0));
        assertEquals(0,stat1000Game4P.getEqualityRate(1));
        assertEquals(0,stat1000Game4P.getEqualityRate(2));
        assertEquals(0,stat1000Game4P.getEqualityRate(3));

        assertEquals(6.9,stat1000Game4P.getPointsAverage(0));
        assertEquals(5.6,stat1000Game4P.getPointsAverage(1));
        assertEquals(3.3,stat1000Game4P.getPointsAverage(2));
        assertEquals(1.6,stat1000Game4P.getPointsAverage(3));
    }

    @Test
    public void p1W1PToString(){
        stat1Game1P.add(p1w);
        assertEquals("Joueur 1 : 100.0% win rate and 0.0% equality rate with a 2.0 points average\n",stat1Game1P.toString());
    }
}
*/