package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game1;
    Game game2;
    Game game3;
    Game game4;
    Game game5;

    @BeforeEach public void Setup() throws ExceptionTakenoko {
        game1 = new Game(new BotName[]{BotName.RANDOM,BotName.INTELLIGENT});
        game2 = new Game(new BotName[]{BotName.INTELLIGENT,BotName.INTELLIGENT});
        game3 = new Game(new BotName[]{BotName.INTELLIGENT});
        game4 = new Game(new BotName[]{BotName.RANDOM,BotName.INTELLIGENT,BotName.RANDOM});
        game5 = new Game(new BotName[]{});
    }

    @Test public void numberPlayers(){
        assertNotEquals(game1,game2);
        assertNotEquals(game1,game3);
        assertNotEquals(game1,game4);
        assertNotEquals(game1,game5);
        assertNotEquals(game1,null);
    }

    @Test public void bots(){
        assertEquals( 2,game1.getBotList().size() );
        assertEquals( 0,game5.getBotList().size() );
        assertNotEquals( 2,game4.getBotList().size());
        assertNotEquals(game5.getBotList(), null);
    }

    @Test public void resource(){
        assertEquals(game1.getResource(),game1.getResource());
        assertNotEquals(game1.getResource(),game2.getResource());
        assertNotEquals(game1.getResource(),null);
    }
}