package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    private Bot bot1;
    private Bot bot2;
    private Board board;
    private Parcel parcel1;
    private Canal canal;



    @BeforeEach public void setUp(){
        Resource resource = new Resource();
        board = new Board();
        parcel1 = new Parcel("noColor");
        canal = new Canal();
        bot1 = new IntelligentBot(resource,board);
        bot2 = new IntelligentBot(resource,board);
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
        List<Coordinate> nextTocentral = board.getFreePlaces();
        assertEquals(6,nextTocentral.size());
        Coordinate randomco=nextTocentral.get(0);
        assertEquals(2,    Coordinate.getNorm(new Coordinate(0,0,0),randomco));
        int[] tabco = randomco.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertEquals(0,sumco);
    }

    @Test void initializeNextCoordinatesAwayFromCentral(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        List<Coordinate> awayFromCentral = board.getAllPlaces();
        Collections.shuffle(awayFromCentral);
        Coordinate randomCo=awayFromCentral.get(0);
        int [] tabco= randomCo.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)<19);
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)>=0);
        assertEquals(0,sumco);
    }

    @Test void possibleCoordinatesParceltest(){
        List<Coordinate> possibleCo = board.getFreePlaces();
        Collections.shuffle(possibleCo);
        assertTrue(board.freeParcel(possibleCo.get(0)));
    }

    @Test void notPossibleCoordinatesCanal(){
        ArrayList<Coordinate[]> possiblecanals = bot1.possibleCoordinatesCanal();
        assertEquals(possiblecanals.size(),0);
        board.placeParcel(parcel1,new Coordinate(2,-2,0));
        board.putCanal(canal,new Coordinate(0,0,0),new Coordinate(1,-1,0));
        ArrayList<Coordinate[]>possiblecanals2=bot1.possibleCoordinatesCanal();
        assertEquals(possiblecanals2.size(),0);
    }
    @Test void possibleCoordinatesCanal(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        ArrayList<Coordinate[]>possiblecanals=bot1.possibleCoordinatesCanal();
        Collections.shuffle(possiblecanals);
        Coordinate[] tabco =possiblecanals.get(0);
        assertTrue(board.playableCanal(tabco[0],tabco[1]));
    }
}