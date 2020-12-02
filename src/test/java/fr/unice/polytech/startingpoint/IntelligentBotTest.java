package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntelligentBotTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void placesForLineStartAtCoord1LineEmpty(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = new ArrayList<>(smartBot.parcelToPlaceToDoForm(new Coordinate(1,0,-1),"line","red"));

        assertEquals(3,placesForLine.size());
        assertEquals(new Coordinate(1,0,-1),placesForLine.get(0));
        assertEquals(new Coordinate(1,-1,0),placesForLine.get(1));
        assertEquals(new Coordinate(1,-2,1),placesForLine.get(2));
    }

    @Test
    public void placesForTriangleStartAtCoord1TriangleEmpty(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = new ArrayList<>(smartBot.parcelToPlaceToDoForm(new Coordinate(1,-1,0),"triangle","red"));

        assertEquals(3,placesForLine.size());
        assertEquals(new Coordinate(1,-1,0),placesForLine.get(0));
        assertEquals(new Coordinate(0,-1,1),placesForLine.get(1));
        assertEquals(new Coordinate(1,-2,1),placesForLine.get(2));
    }

    @Test
    public void placesForLineStartAtCoord1LineFull(){
        Board board1 = new Board();
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(1,0,-1));
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(1,-1,0));
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(0,-1,1));
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(1,-2,1));
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = smartBot.parcelToPlaceToDoForm(new Coordinate(1,0,-1),"line","red");

        assertEquals(0,placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1TriangleFull(){
        Board board1 = new Board();
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(1,-1,0));
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(0,-1,1));
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(1,-2,1));
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = smartBot.parcelToPlaceToDoForm(new Coordinate(1,-1,0),"triangle","red");

        assertEquals(0,placesForLine.size());
    }

    @Test
    public void lineform_1ParcelLeft(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        board1.isPlacedParcel(new Parcel("red"), new Coordinate(1,0,-1));//parcel haute
        board1.isPlacedParcel(new Parcel("red"), new Coordinate(1,-1,0));//parcel milieu
        board1.isPlacedParcel(new Parcel("no"), new Coordinate(0,-1,1));//deuxième parcel à coté de la parcel du bas

        Coordinate coord = smartBot.bestCoordForForm("line","red");

        assertEquals(new Coordinate(1,-2,1),coord);//parcel du bas
    }

    @Test
    public void lineform_2ParcelLeft_MidPlacePossible(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);

        board1.isPlacedParcel(new Parcel("red"), new Coordinate(1,0,-1));//parcel haute

        assertEquals(new Coordinate(1,-1,0),smartBot.bestCoordForForm("line","red"));//parcel milieu
    }

    @Test
    public void lineform_3ParcelLeft(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);

        assertNotNull(smartBot.bestCoordForForm("line","red"));
    }
}