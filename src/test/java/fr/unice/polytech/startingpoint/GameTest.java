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
        game1 = new Game(new String[]{"Bob","Bob"});
        game2 = new Game(new String[]{"Bob","Albert"});
        game3 = new Game(new String[]{"Bob"});
        game4 = new Game(new String[]{"Bob","Bob","Bob"});
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
        assertEquals("Bob",game1.getBotList().get(0).getBotName());
        assertEquals(game4.getBotList().get(2).getBotName(),game1.getBotList().get(0).getBotName());
        assertNotEquals( "Albert",game1.getBotList().get(0).getBotName());
        assertNotEquals( 2,game4.getBotList().size());
        assertNotEquals(game5.getBotList(), null);
    }

    @Test public void resource(){
        assertEquals(game1.getResource(),game1.getResource());
        assertTrue(!game1.getResource().equals(game2.getResource()));
        assertTrue(!game1.getResource().equals(null));
    }

    @Test
    public void missionDeletedAndIncreaseScore(){
        game1.getBotList().get(0).play(game1.getResource(), game1.getBoard());
        game1.missionDone(0,game1.getBoard());
        assertEquals(0,game1.getBotList().get(0).getInventoryMission().size());
        assertNotEquals(0,game1.getData()[0]);
    }

    @Test
    public void wrongPlacementSoNoDeletedMissionAndNoIncreaseScore(){
        game1.getBotList().get(0).drawMission(game1.getResource());
        game1.getBotList().get(0).placeParcel(game1.getResource(), game1.getBoard());
        game1.missionDone(0,game1.getBoard());
        assertEquals(0,game1.getBotList().get(0).getInventoryMission().size());
        assertEquals(2,game1.getData()[0]);
    }
}