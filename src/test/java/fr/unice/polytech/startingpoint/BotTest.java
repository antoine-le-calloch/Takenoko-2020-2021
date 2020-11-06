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

        bot1= new Bot(1);
        bot2=new Bot(2);
        resource=new Resource();
        board=new Board();


    }


    @Test public void testEquals(){


        assertEquals(bot1,bot1);
        assertTrue(!bot1.equals(null));
        assertNotEquals(bot1,bot2);

    }




}