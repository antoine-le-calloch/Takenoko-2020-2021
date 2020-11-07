package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {


    Game game;
    Game game2;
    Game game3;
    Game game4;
    Game game5;

    @BeforeEach public void Setup(){

        game=new Game(new int []{1,1});
        game2=new Game(new int[]{1,3});
        game3=new Game(new int[]{1});
        game4=new Game(new int[]{1,1,1});
        game5=new Game(new int[]{});


    }



    @Test public void numberPlayers(){

        assertNotEquals(game,game2);
        assertNotEquals(game,game3);
        assertNotEquals(game,game4);
        assertNotEquals(game,game5);
        assertNotEquals(game,null);


    }

    @Test public void bots(){


        assertEquals( 2,game.botList.size() );
        assertEquals( 0,game5.botList.size() );
        assertEquals(1,game.botList.get(0).num_bot);
        assertEquals(game4.botList.get(2).num_bot,game.botList.get(0).num_bot);
        assertNotEquals( 3,game.botList.get(0).num_bot);
        assertNotEquals( 2,game4.botList.size());
        assertNotEquals(game5.botList, null);

    }

    @Test public void ressource(){

        assertEquals(game.resource,game.resource);
        assertTrue(!game.resource.equals(game2.resource));
        assertTrue(!game.resource.equals(null));
    }











}