package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class ParcelBotTest {

    Coordinate coordinate;
    Coordinate coordinate2;
    Coordinate coordinate3;

    @BeforeEach
    public void setUp() {
        coordinate = new Coordinate(1, 0, -1); //0-2h
        coordinate2 = new Coordinate(1, -1, 0); //2-4h
        coordinate3=new Coordinate(1,-2,1); //4h éloigné de un
    }

    @Test
    public void placesForLineStartAtCoord1Line_Empty() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForLine = new ArrayList<>(smartBot.parcelsToPlaceToDoForm(coordinate, FormType.LINE, ColorType.RED));

        assertEquals(3, placesForLine.size());
        assertEquals(coordinate, placesForLine.get(0));
        assertEquals(coordinate2, placesForLine.get(1));
        assertEquals(coordinate3, placesForLine.get(2));
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Empty() {
        Board board1 = new Board();
        ParcelBot parcelBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForTriangle = new ArrayList<>(parcelBot.parcelsToPlaceToDoForm(coordinate2, FormType.TRIANGLE, ColorType.RED));

        assertEquals(3, placesForTriangle.size());
        assertEquals(coordinate2, placesForTriangle.get(0));
        assertEquals(coordinate3, placesForTriangle.get(1));
        assertEquals(new Coordinate(0, -1, 1), placesForTriangle.get(2));
    }

    @Test
    public void placesForLineStartAtCoord1Line_1Parcel() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        board1.placeParcel(new Parcel(ColorType.RED), coordinate);//parcel haute

        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(new Coordinate(1, 0, -1), FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_1Parcel() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        board1.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel haute

        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(coordinate2, FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Before


    @Test
    public void placesForLineStartAtCoord1Line_Full() {
        Board board1 = new Board();
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate);
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate2);
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, -1, 1));
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(new Coordinate(1, 0, -1), FormType.LINE, ColorType.RED);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Full() {
        Board board1 = new Board();
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate2);
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, -1, 1));
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(coordinate2, FormType.TRIANGLE, ColorType.RED);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void lineform_1ParcelLeft() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        board1.placeParcel(new Parcel(ColorType.RED), coordinate);//parcel haute
        board1.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel milieu
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, -1, 1));//deuxième parcel à coté de la parcel du bas

        Coordinate coord = smartBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED);

        assertEquals(coordinate3, coord);//parcel du bas
    }

    @Test
    public void lineform_2ParcelLeft_MidPlacePossible() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);

        board1.placeParcel(new Parcel(ColorType.RED), coordinate);//parcel haute

        assertEquals(coordinate2, smartBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));//parcel milieu
    }

    @Test
    public void lineform_2ParcelLeft_LowPlacePossible() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);

        board1.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel bas
        board1.placeParcel(new Parcel(ColorType.BLUE), new Coordinate(0, 1, -1));//parcel qui active la partel haute

        assertEquals(new Coordinate(1, 0, -1), smartBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));//parcel bas
    }

    @Test
    public void lineform_0ParcelPut() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);

        assertNotNull(smartBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));
    }

    @Test
    public void strategyIrrigation() {

        Board board = new Board();
        ParcelBot bot = new ParcelBot(new Resource(), board);
        Parcel parcel1 = new Parcel(ColorType.NO_COLOR);
        Parcel parcel2 = new Parcel(ColorType.NO_COLOR);
        parcel1.setIrrigated();
        parcel2.setIrrigated();
        Coordinate coordinate2 = new Coordinate(0, -1, 1);
        board.placeParcel(parcel1, this.coordinate2);
        board.placeParcel(parcel2,coordinate2);
        assertTrue(!bot.putCanal());
        board.placeParcel(new Parcel(ColorType.NO_COLOR),coordinate3);
        board.placeCanal(new Canal(), this.coordinate2, coordinate2);
        assertTrue(bot.putCanal());
    }



}