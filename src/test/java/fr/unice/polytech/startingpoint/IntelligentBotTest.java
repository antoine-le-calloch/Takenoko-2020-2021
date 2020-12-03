package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class IntelligentBotTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void placesForLineStartAtCoord1Line_Empty(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = new ArrayList<>(smartBot.parcelToPlaceToDoForm(new Coordinate(1,0,-1),Form.LINE,Color.RED));

        assertEquals(3,placesForLine.size());
        assertEquals(new Coordinate(1,0,-1),placesForLine.get(0));
        assertEquals(new Coordinate(1,-1,0),placesForLine.get(1));
        assertEquals(new Coordinate(1,-2,1),placesForLine.get(2));
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Empty(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = new ArrayList<>(smartBot.parcelToPlaceToDoForm(new Coordinate(1,-1,0),Form.TRIANGLE,Color.RED));

        assertEquals(3,placesForLine.size());
        assertEquals(new Coordinate(1,-1,0),placesForLine.get(0));
        assertEquals(new Coordinate(0,-1,1),placesForLine.get(1));
        assertEquals(new Coordinate(1,-2,1),placesForLine.get(2));
    }

    @Test
    public void placesForLineStartAtCoord1Line_1Parcel(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        board1.placeParcel(new Parcel(Color.RED), new Coordinate(1,0,-1));//parcel haute

        List<Coordinate> placesForLine = smartBot.parcelToPlaceToDoForm(new Coordinate(1,0,-1),Form.LINE,Color.RED);

        assertEquals(2,placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_1Parcel(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        board1.placeParcel(new Parcel(Color.RED), new Coordinate(1,-1,0));//parcel haute

        List<Coordinate> placesForLine = smartBot.parcelToPlaceToDoForm(new Coordinate(1,-1,0),Form.LINE,Color.RED);

        assertEquals(2,placesForLine.size());
    }

    @Test
    public void placesForLineStartAtCoord1Line_Full(){
        Board board1 = new Board();
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(1,0,-1));
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(1,-1,0));
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(0,-1,1));
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(1,-2,1));
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = smartBot.parcelToPlaceToDoForm(new Coordinate(1,0,-1),Form.LINE,Color.RED);

        assertEquals(0,placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Full(){
        Board board1 = new Board();
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(1,-1,0));
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(0,-1,1));
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(1,-2,1));
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        List<Coordinate> placesForLine = smartBot.parcelToPlaceToDoForm(new Coordinate(1,-1,0),Form.TRIANGLE,Color.RED);

        assertEquals(0,placesForLine.size());
    }

    @Test
    public void lineform_1ParcelLeft(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);
        board1.placeParcel(new Parcel(Color.RED), new Coordinate(1,0,-1));//parcel haute
        board1.placeParcel(new Parcel(Color.RED), new Coordinate(1,-1,0));//parcel milieu
        board1.placeParcel(new Parcel(Color.NO_COLOR), new Coordinate(0,-1,1));//deuxième parcel à coté de la parcel du bas

        Coordinate coord = smartBot.bestCoordinatesForForm(Form.LINE,Color.RED);

        assertEquals(new Coordinate(1,-2,1),coord);//parcel du bas
    }

    @Test
    public void lineform_2ParcelLeft_MidPlacePossible(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);

        board1.placeParcel(new Parcel(Color.RED), new Coordinate(1,0,-1));//parcel haute

        assertEquals(new Coordinate(1,-1,0),smartBot.bestCoordinatesForForm(Form.LINE,Color.RED));//parcel milieu
    }

    @Test
    public void lineform_2ParcelLeft_LowPlacePossible(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);

        board1.placeParcel(new Parcel(Color.RED), new Coordinate(1,-1,0));//parcel bas
        board1.placeParcel(new Parcel(Color.BLUE), new Coordinate(0,1,-1));//parcel qui active la partel haute

        assertEquals(new Coordinate(1,0,-1),smartBot.bestCoordinatesForForm(Form.LINE,Color.RED));//parcel bas
    }

    @Test
    public void lineform_0ParcelPut(){
        Board board1 = new Board();
        IntelligentBot smartBot = new IntelligentBot(new Resource(),board1);

        assertNotNull(smartBot.bestCoordinatesForForm(Form.LINE,Color.RED));
    }
}