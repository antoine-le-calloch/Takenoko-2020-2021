package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    private Bot bot1;
    private Bot bot2;
    private Board board;
    Parcel parcel1;
    Parcel parcel2;

    @BeforeEach public void setUp(){
        Resource resource = new Resource();
        board = new Board();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
        bot1 = new Bot(resource,board);
        bot2 = new Bot(resource,board);
    }

    @Test public void testEquals(){
        assertEquals(bot1,bot1);
        assertNotEquals(bot1, null);
        assertNotEquals(bot1,bot2);
    }

    @Test
    public void missionIncrease(){
        bot1.drawMission();
        assertEquals(1,bot1.getInventoryMission().size());
    }

    @Test
    public void missionDecrease(){
        bot1.drawMission();
        Mission todelete = bot1.getInventoryMission().get(0);
        bot1.deleteMission(todelete);
        assertEquals(0,bot1.getInventoryMission().size());
        bot1.deleteMission(todelete);
        assertNotEquals(-1,bot1.getInventoryMission().size());
    }


    @Test void initializeNextCoordinatesNextToCentral(){
        ArrayList<Coordinate> nextTocentral = bot1.coordinatesAroundBoard();
        assertEquals(6,nextTocentral.size());
        Coordinate randomco=nextTocentral.get(0);
        assertEquals(2,    Coordinate.getNorm(new Coordinate(0,0,0),randomco));
        int[] tabco = randomco.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertEquals(0,sumco);
    }

    @Test void initializeNextCoordinatesAwayFromCentral(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        ArrayList<Coordinate> awayFromcentral = bot1.coordinatesAroundBoard();
        Collections.shuffle(awayFromcentral);
        Coordinate randomco=awayFromcentral.get(0);
        int [] tabco= randomco.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomco)<19);
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomco)>1);
        assertEquals(0,sumco);
    }

    @Test void possibleCoordinatestest(){
        ArrayList<Coordinate> possibleco = bot1.possibleCoordinates();
        Collections.shuffle(possibleco);
        assertTrue(board.playableParcel(possibleco.get(0)));
    }
}