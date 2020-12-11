package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.BotType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class StatTest {
    Game game;
    Rules rules;

    Stat statGame1P;
    Stat statGame2P;
    Stat statGame4P;

    PlayerData p1w;
    PlayerData p1w2Player;
    PlayerData p2w2Player;
    PlayerData equality2Player;
    PlayerData p1w4Player;
    PlayerData p2w4Player;

    BotType[] p1 = new BotType[]{BotType.RANDOM};
    BotType[] p2 = new BotType[]{BotType.RANDOM,BotType.PARCELBOT};
    BotType[] p4 = new BotType[]{BotType.RANDOM,BotType.PARCELBOT,BotType.PEASANTBOT,BotType.PANDABOT};

    @BeforeEach
    public void initialize(){
        game = new Game();

        statGame1P = new Stat(p1);
        statGame2P = new Stat(p2);
        statGame4P = new Stat(p4);

        p1w = new PlayerData(p1, game);
        p1w2Player = new PlayerData(p2, game);
        p2w2Player = new PlayerData(p2, game);
        equality2Player = new PlayerData(p2, game);
        p1w4Player = new PlayerData(p4, game);
        p2w4Player = new PlayerData(p4, game);

        p1w.completedMission(0,2);

        p1w2Player.completedMission(0,2);
        p1w2Player.completedMission(1,1);

        p2w2Player.completedMission(1,2);
        p2w2Player.completedMission(0,1);

        equality2Player.completedMission(1,1);
        equality2Player.completedMission(0,1);

        p1w4Player.completedMission(0,6);
        p1w4Player.completedMission(1,4);
        p1w4Player.completedMission(2,2);
        p1w4Player.completedMission(3,1);

        p2w4Player.completedMission(0,4);
        p2w4Player.completedMission(1,6);
        p2w4Player.completedMission(2,1);
        p2w4Player.completedMission(3,2);
    }

    @Test
    public void p1Winner2Player(){
        statGame2P.add(p1w2Player);

        assertEquals(100,statGame2P.getWinRate(0));
        assertEquals(0,statGame2P.getWinRate(1));

        assertEquals(0,statGame2P.getEqualityRate(0));
        assertEquals(0,statGame2P.getEqualityRate(1));

        assertEquals(2,statGame2P.getPointsAverage(0));
        assertEquals(1,statGame2P.getPointsAverage(1));
    }

    @Test
    public void p2Winner2Player(){
        statGame2P.add(p2w2Player);
        assertEquals(0,statGame2P.getWinRate(0));
        assertEquals(100,statGame2P.getWinRate(1));

        assertEquals(0,statGame2P.getEqualityRate(0));
        assertEquals(0,statGame2P.getEqualityRate(1));

        assertEquals(1,statGame2P.getPointsAverage(0));
        assertEquals(2,statGame2P.getPointsAverage(1));
    }

    @Test
    public void equalityCase2Player(){
        statGame2P.add(equality2Player);
        assertEquals(0,statGame2P.getWinRate(0));
        assertEquals(0,statGame2P.getWinRate(1));

        assertEquals(100,statGame2P.getEqualityRate(0));
        assertEquals(100,statGame2P.getEqualityRate(1));

        assertEquals(1,statGame2P.getPointsAverage(0));
        assertEquals(1,statGame2P.getPointsAverage(1));
    }

    @Test
    public void p1Win700GameLose3002Player(){
        for (int i = 0; i < 1000; i++) {
            if(i<700)
                statGame2P.add(p1w2Player);
            else
                statGame2P.add(p2w2Player);
        }
        assertEquals(70,statGame2P.getWinRate(0));
        assertEquals(30,statGame2P.getWinRate(1));

        assertEquals(0,statGame2P.getEqualityRate(0));
        assertEquals(0,statGame2P.getEqualityRate(1));

        assertEquals(1.7,statGame2P.getPointsAverage(0));
        assertEquals(1.3,statGame2P.getPointsAverage(1));
    }

    @Test
    public void p1Win700GameLose3004Player(){
        for (int i = 0; i < 1000; i++) {
            if(i<700)
                statGame4P.add(p1w4Player);
            else
                statGame4P.add(p2w4Player);
        }

        assertEquals(70,statGame4P.getWinRate(0));
        assertEquals(30,statGame4P.getWinRate(1));
        assertEquals(0,statGame4P.getWinRate(2));
        assertEquals(0,statGame4P.getWinRate(3));

        assertEquals(0,statGame4P.getEqualityRate(0));
        assertEquals(0,statGame4P.getEqualityRate(1));
        assertEquals(0,statGame4P.getEqualityRate(2));
        assertEquals(0,statGame4P.getEqualityRate(3));

        assertEquals(5.4,statGame4P.getPointsAverage(0));
        assertEquals(4.6,statGame4P.getPointsAverage(1));
        assertEquals(1.7,statGame4P.getPointsAverage(2));
        assertEquals(1.3,statGame4P.getPointsAverage(3));
    }

    @Test
    public void p1W1PToString(){
        statGame1P.add(p1w);
        assertEquals("Joueur Random Bot : 100.0% win rate and 0.0% equality rate with a 2.0 points average\n",statGame1P.toString());
    }
}