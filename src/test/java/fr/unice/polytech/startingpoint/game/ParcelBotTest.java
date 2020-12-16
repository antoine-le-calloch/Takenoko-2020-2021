package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.ParcelBot;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.MissionType;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

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
    ParcelBot parcelBot;
    Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        parcelBot = new ParcelBot(game, new Rules(new Resource(),new Board()));
        coordinate1 = new Coordinate(1, 0, -1); //0-2h
        coordinate2 = new Coordinate(1, -1, 0); //2-4h
        coordinate3 = new Coordinate(0, -1, 1); //4-6h
        coordinate4 = new Coordinate(1,-2,1); //4h éloigné de un
    }

    @Test void LineForm(){
        List<Coordinate> triangleForm = parcelBot.setForm(coordinate1, FormType.LINE);
        assertEquals(coordinate1,triangleForm.get(0));
        assertEquals(coordinate2,triangleForm.get(1));
        assertEquals(coordinate4,triangleForm.get(2));
    }


    @Test void TriangleForm(){
        List<Coordinate> triangleForm = parcelBot.setForm(coordinate2, FormType.TRIANGLE);
        assertEquals(coordinate2,triangleForm.get(0));
        assertEquals(coordinate4,triangleForm.get(1));
        assertEquals(coordinate3,triangleForm.get(2));
    }

    @Test void coordAroundUse_Central(){
        Coordinate coordAroundUse = parcelBot.coordAroundUse(coordinate1);
        assertEquals(new Coordinate(0,0,0),coordAroundUse);
    }

    @Test void coordAroundUse_NextToCentral(){
        game.getBoard().placeParcel(new Parcel(),coordinate3);
        Coordinate coordAroundUse = parcelBot.coordAroundUse(coordinate1);
        assertEquals(new Coordinate(0,0,0),coordAroundUse);
    }

    /*@Test
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
    public void placesForLineStartAtCoord1Line_1Parcel() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);//parcel haute

        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate1, FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_1Parcel() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel haute

        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate2, FormType.LINE, ColorType.RED);

        assertEquals(2, placesForLine.size());
    }

    @Before


    @Test
    public void placesForLineStartAtCoord1Line_Full() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate1);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate2);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate4);
        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate1, FormType.LINE, ColorType.NO_COLOR);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void placesForTriangleStartAtCoord1Triangle_Full() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate2);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate4);
        List<Coordinate> placesForLine = parcelBot.parcelsToPlaceToDoForm(coordinate2, FormType.TRIANGLE, ColorType.NO_COLOR);

        assertEquals(0, placesForLine.size());
    }

    @Test
    public void lineform_1ParcelLeft() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);//parcel haute
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel milieu
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);//deuxième parcel à coté de la parcel du bas

        Coordinate coord = parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED);

        assertEquals(coordinate4, coord);//parcel du bas
    }

    @Test
    public void lineform_2ParcelLeft_MidPlacePossible() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);//parcel haute

        assertEquals(coordinate2, parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));//parcel milieu
    }

    @Test
    public void lineform_2ParcelLeft_LowPlacePossible() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);//parcel bas
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, 1, -1));//parcel qui active la partel haute

        assertEquals(coordinate1, parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));//parcel bas
    }

    @Test
    public void lineform_0ParcelPut() {
        assertNotNull(parcelBot.bestCoordinatesForForm(FormType.LINE, ColorType.RED));
    }

    @Test
    public void strategyIrrigation() throws BadPlaceParcelException, BadPlaceCanalException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate2);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate3);
        assertFalse(parcelBot.putCanal());
        board.placeParcel(new Parcel(ColorType.NO_COLOR), coordinate4);
        assertTrue(parcelBot.putCanal());
    }
    
    @Test
    public void drawMissionParcel(){
        assertEquals(0,parcelBot.getInventory().getMission().size());
        parcelBot.botPlay();
        assertEquals(1,parcelBot.getInventory().getMission().size());
        assertEquals(MissionType.PARCEL,parcelBot.getInventory().getMission().get(0).getMissionType());
    }

    @Test
    public void putParcel(){
        Resource mockResource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        Mockito.when(mockResource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        ParcelBot parcelBot1 = new ParcelBot(mockResource,board);

        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(2)).thenReturn(0);//donne une val au random pour poser une parel

        assertEquals(1,board.getPlacedParcels().size());//1 parcel posée (central)
        parcelBot1.botPlay();//pose une parcel
        //assertEquals(2,board.getPlacedParcels().size());//1 parcel posée
    }

    @Test
    public void putCanal() throws BadPlaceParcelException {
        Resource mockResource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        board.placeParcel(new Parcel(ColorType.NO_COLOR),coordinate1);//pose une partel pour mettre le canal
        board.placeParcel(new Parcel(ColorType.NO_COLOR),coordinate2);//pose une autre partel pour mettre le canal
        Mockito.when(mockResource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        ParcelBot parcelBot1 = new ParcelBot(mockResource,board);

        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(2)).thenReturn(1);//donne une val au random pour poser une parel
        assertEquals(0,board.getPlacedCanals().size());//0 canal posée
        parcelBot1.botPlay();//pose un canal
        assertEquals(0,board.getPlacedCanals().size());//0 canal posée
    }*/
}