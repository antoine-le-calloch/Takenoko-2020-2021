package fr.unice.polytech.startingpoint.Bot;

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

    Coordinate coordinate1;
    Coordinate coordinate2;
    Coordinate coordinate3;
    Coordinate coordinate4;
    Board board;
    ParcelBot parcelBot;

    @BeforeEach
    public void setUp() {
        board = new Board();
        parcelBot = new ParcelBot(new Resource(), board);
        coordinate1 = new Coordinate(1, 0, -1); //0-2h
        coordinate2 = new Coordinate(1, -1, 0); //2-4h
        coordinate3 = new Coordinate(0, -1, 1); //4-6h
        coordinate4 = new Coordinate(1,-2,1); //4h éloigné de un
    }

    @Test
    public void placesForLineStartAtCoord1Line_Empty() {
        List<Coordinate> placesForLine = new ArrayList<>(parcelBot.parcelsToPlaceToDoForm(coordinate1, FormType.LINE, ColorType.RED));

        assertEquals(3, placesForLine.size());
        assertEquals(coordinate1, placesForLine.get(0));
        assertEquals(coordinate2, placesForLine.get(1));
        assertEquals(coordinate4, placesForLine.get(2));
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Empty() {
        List<Coordinate> placesForTriangle = new ArrayList<>(parcelBot.parcelsToPlaceToDoForm(coordinate2, FormType.TRIANGLE, ColorType.RED));

        assertEquals(3, placesForTriangle.size());
        assertEquals(coordinate2, placesForTriangle.get(0));
        assertEquals(coordinate4, placesForTriangle.get(1));
        assertEquals(coordinate3, placesForTriangle.get(2));
    }

    @Test
    public void placesForLineStartAtCoord1Line_1Parcel() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);//parcel haute

        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate1, FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_1Parcel() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel haute

        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate2, FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Before


    @Test
    public void placesForLineStartAtCoord1Line_Full() {
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate1);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate2);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate4);
        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate1, FormType.LINE, ColorType.RED);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Full() {
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate2);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate4);
        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate2, FormType.TRIANGLE, ColorType.RED);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void lineform_1ParcelLeft() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);//parcel haute
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel milieu
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);//deuxième parcel à coté de la parcel du bas

        Coordinate coord = parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED);

        assertEquals(coordinate4, coord);//parcel du bas
    }

    @Test
    public void lineform_2ParcelLeft_MidPlacePossible() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);//parcel haute

        assertEquals(coordinate2, parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));//parcel milieu
    }

    @Test
    public void lineform_2ParcelLeft_LowPlacePossible() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel bas
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, 1, -1));//parcel qui active la partel haute

        assertEquals(coordinate1, parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));//parcel bas
    }

    @Test
    public void lineform_0ParcelPut() {
        assertNotNull(parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));
    }

    @Test
    public void strategyIrrigation() {
        Parcel parcel1 = new Parcel(ColorType.NO_COLOR);
        Parcel parcel2 = new Parcel(ColorType.NO_COLOR);
        parcel1.setIrrigated();
        parcel2.setIrrigated();
        board.placeParcel(parcel1, coordinate2);
        board.placeParcel(parcel2, coordinate3);

        assertFalse(parcelBot.putCanal());
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate4);
        board.placeCanal(new Canal(), coordinate2, coordinate3);
        assertTrue(parcelBot.putCanal());
    }

    @Test
    public void putParcel() {
        assertNotNull(parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));
    }
}