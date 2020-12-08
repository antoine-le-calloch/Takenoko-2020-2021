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
        coordinate = new Coordinate(1, -1, 0);
        coordinate2 = new Coordinate(1, 0, -1);
        coordinate3=new Coordinate(1,-2,1);
    }

    @Test
    public void placesForLineStartAtCoord1Line_Empty() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForLine = new ArrayList<>(smartBot.parcelsToPlaceToDoForm(coordinate2, FormType.LINE, ColorType.RED));

        assertEquals(3, placesForLine.size());
        assertEquals(coordinate2, placesForLine.get(0));
        assertEquals(coordinate, placesForLine.get(1));
        assertEquals(coordinate3, placesForLine.get(2));
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Empty() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForLine = new ArrayList<>(smartBot.parcelsToPlaceToDoForm(coordinate, FormType.TRIANGLE, ColorType.RED));

        assertEquals(3, placesForLine.size());
        assertEquals(coordinate, placesForLine.get(0));
        assertEquals(new Coordinate(0, -1, 1), placesForLine.get(1));
        assertEquals(coordinate3, placesForLine.get(2));
    }

    @Test
    public void placesForLineStartAtCoord1Line_1Parcel() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        board1.placeParcel(new Parcel(ColorType.RED),coordinate2);//parcel haute

        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(new Coordinate(1, 0, -1), FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_1Parcel() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        board1.placeParcel(new Parcel(ColorType.RED), coordinate);//parcel haute

        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(coordinate, FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Before


    @Test
    public void placesForLineStartAtCoord1Line_Full() {
        Board board1 = new Board();
        board1.placeParcel(new Parcel(ColorType.NO_COLOR),coordinate2);
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate);
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, -1, 1));
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(new Coordinate(1, 0, -1), FormType.LINE, ColorType.RED);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Full() {
        Board board1 = new Board();
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate);
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, -1, 1));
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        List<Coordinate> placesForLine = smartBot.parcelsToPlaceToDoForm(coordinate, FormType.TRIANGLE, ColorType.RED);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void lineform_1ParcelLeft() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);
        board1.placeParcel(new Parcel(ColorType.RED),coordinate2);//parcel haute
        board1.placeParcel(new Parcel(ColorType.RED), coordinate);//parcel milieu
        board1.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, -1, 1));//deuxième parcel à coté de la parcel du bas

        Coordinate coord = smartBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED);

        assertEquals(coordinate3, coord);//parcel du bas
    }

    @Test
    public void lineform_2ParcelLeft_MidPlacePossible() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);

        board1.placeParcel(new Parcel(ColorType.RED),coordinate2);//parcel haute

        assertEquals(coordinate, smartBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));//parcel milieu
    }

    @Test
    public void lineform_2ParcelLeft_LowPlacePossible() {
        Board board1 = new Board();
        ParcelBot smartBot = new ParcelBot(new Resource(), board1);

        board1.placeParcel(new Parcel(ColorType.RED), coordinate);//parcel bas
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
        board.placeParcel(parcel1,coordinate);
        board.placeParcel(parcel2,coordinate2);
        assertTrue(!bot.putCanal());
        board.placeParcel(new Parcel(ColorType.NO_COLOR),coordinate3);
        board.placeCanal(new Canal(),coordinate, coordinate2);
        assertTrue(bot.putCanal());
    }



}