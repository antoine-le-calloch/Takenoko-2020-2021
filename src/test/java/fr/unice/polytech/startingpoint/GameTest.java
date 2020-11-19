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

    @BeforeEach public void Setup(){
        game1 = new Game(new String[]{"random","intelligent"});
        game2 = new Game(new String[]{"intelligent","intelligent"});
        game3 = new Game(new String[]{"intelligent"});
        game4 = new Game(new String[]{"random","intelligent","random"});
        game5 = new Game(new String[]{});
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
        assertEquals("random",game1.getBotList().get(0).getBotName());
        assertEquals(game4.getBotList().get(2).getBotName(),game1.getBotList().get(0).getBotName());
        assertNotEquals( "intelligent",game1.getBotList().get(0).getBotName());
        assertNotEquals( 2,game4.getBotList().size());
        assertNotEquals(game5.getBotList(), null);
    }

    @Test public void resource(){
        assertEquals(game1.getResource(),game1.getResource());
        assertNotEquals(game1.getResource(),game2.getResource());
        assertNotEquals(game1.getResource(),null);
    }
}