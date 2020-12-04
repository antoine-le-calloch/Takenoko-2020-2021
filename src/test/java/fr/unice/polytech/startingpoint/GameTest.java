package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
import org.junit.jupiter.api.*;
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
class GameTest {
    Game game1;
    Game game2;
    Game game3;
    Game game4;
    Game game5;

    @BeforeEach public void Setup() {
        game1 = new Game(new BotType[]{BotType.RANDOM, BotType.INTELLIGENT});
        game2 = new Game(new BotType[]{BotType.INTELLIGENT, BotType.INTELLIGENT});
        game3 = new Game(new BotType[]{BotType.INTELLIGENT});
        game4 = new Game(new BotType[]{BotType.RANDOM, BotType.INTELLIGENT, BotType.RANDOM});
        game5 = new Game(new BotType[]{});
    }

    @Test public void numberPlayers(){
        assertNotEquals(game1,game2);
        assertNotEquals(game1,game3);
        assertNotEquals(game1,game4);
        assertNotEquals(game1,game5);
        assertNotEquals(game1,null);
    }

    @Test public void bots(){
        assertEquals( 2,game1.getPlayerData().getScores().size() );
        assertEquals( 0,game5.getPlayerData().getScores().size() );
        assertNotEquals( 2,game4.getPlayerData().getScores().size());
        assertNotEquals(game5.getPlayerData().getScores(), null);
    }

    @Test public void resource(){
        assertEquals(game1.getResource(),game1.getResource());
        assertNotEquals(game1.getResource(),game2.getResource());
        assertNotEquals(game1.getResource(),null);
    }
}*/