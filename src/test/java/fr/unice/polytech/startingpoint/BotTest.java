package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    private Bot bot1;
    private Bot bot2;
    private Resource resource;
    private Board board;

    @BeforeEach public void setUp(){
        bot1 = new Bot("Bob");
        bot2 = new Bot("Bob");
        resource = new Resource();
        board = new Board();
    }

    @Test public void testEquals(){
        assertEquals(bot1,bot1);
        assertTrue(!bot1.equals(null));
        assertNotEquals(bot1,bot2);
    }

    @Test
    public void parcelIncrease(){
        bot1.placeParcel(resource, board);
        assertEquals(2,board.getParcel().size());
    }

    @Test
    public void missionIncrease(){
        bot1.drawMission(resource);
        assertEquals(1,bot1.getInventoryMission().size());
    }
}