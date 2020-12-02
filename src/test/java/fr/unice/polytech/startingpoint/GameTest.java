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

    @Test public void wrongBotName(){
        Exception exception = assertThrows(ExceptionTakenoko.class, () -> {new Game(new String[]{"ranom","intelgent"});});
        assertEquals(exception.getMessage(), "invalid bot's name");
        Exception exception2 = assertThrows(ExceptionTakenoko.class, () -> {new Game(new String[]{""});});
        assertEquals(exception2.getMessage(), "invalid bot's name");
    }

    @Test public void goodBotName() throws ExceptionTakenoko {
        game1 = new Game(new String[]{"random","intelligent"});
        game1 = new Game(new String[]{"random"});
        game1 = new Game(new String[]{"intelligent"});

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