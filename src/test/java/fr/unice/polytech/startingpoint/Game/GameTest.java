package fr.unice.polytech.startingpoint.Game;

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

class GameTest {
    Game game1;
    Game game2;
    Game game3;
    Game game4;
    Game game5;


    @BeforeEach public void Setup() {
        game1 = new Game(new BotType[]{BotType.RANDOM, BotType.PARCELBOT});
        game2 = new Game(new BotType[]{BotType.PARCELBOT, BotType.PARCELBOT});
        game3 = new Game(new BotType[]{BotType.PARCELBOT});
        game4 = new Game(new BotType[]{BotType.RANDOM, BotType.PARCELBOT, BotType.RANDOM});
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


    //vérifie que le partie se finie au bout de 4 missions faites par un bot
    @Test public void isFinished(){

        assertTrue(game1.isContinue());
        for(int i=0; i<4;i++) {
            game1.getPlayerData().completedMission(0, 1);
        }
        assertFalse(game1.isContinue());
    }



    @Test public void missionsDone(){
        assertEquals(0,game1.getPlayerData().getScores().get(0));
        assertEquals(0,game1.getPlayerData().get(0).getInventory().getMission().size());
        game1.getPlayerData().get(0).getInventory().getMission().add(new PandaMission(ColorType.RED,3));
        game1.getPlayerData().get(0).getInventory().addBamboo(ColorType.RED);
        game1.missionDone(0);
        assertTrue(game1.getPlayerData().getScores().get(0)>game1.getPlayerData().getScores().get(1));
        assertEquals(0,game1.getPlayerData().get(0).getInventory().getMission().size());
    }


}