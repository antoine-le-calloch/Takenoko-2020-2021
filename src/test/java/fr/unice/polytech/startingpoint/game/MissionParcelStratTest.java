package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.bot.ParcelBot;
import fr.unice.polytech.startingpoint.bot.MissionParcelStrat;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class MissionParcelStratTest {

    ParcelMission missionBlueTriangle;
    ParcelMission missionRedTriangle;
    ParcelMission missionBlueLine;
    ParcelMission missionRedLine;

    Coordinate coordinate1;
    Coordinate coordinate2;
    Coordinate coordinate3;
    Coordinate coordinate4;
    Coordinate coordinate5;
    Coordinate coordinate7;
    Coordinate coordinate8;

    Game game;
    Board board;
    Bot parcelBot;

    MissionParcelStrat stratMissionParcel;

    @BeforeEach
    public void setUp() {
        coordinate1 = new Coordinate(1, 0, -1); //0-2h
        coordinate2 = new Coordinate(1, -1, 0); //2-4h
        coordinate3 = new Coordinate(0, -1, 1); //4-6h
        coordinate4 = new Coordinate(-1, 0, 1); //6-8h
        coordinate5 = new Coordinate(-1, 1, 0); //8-10h

        coordinate7 = new Coordinate(1, -2, 1); //4h éloigné de un
        coordinate8 = new Coordinate(-1, -1, 2); //6h éloigné de un

        game = new Game();
        board = game.getBoard();
        parcelBot = new ParcelBot(game.getGameInteraction());

        stratMissionParcel = new MissionParcelStrat(parcelBot);

        missionBlueTriangle = new ParcelMission(board,ColorType.BLUE, 1, FormType.TRIANGLE);
        missionRedTriangle = new ParcelMission(board,ColorType.RED, 1, FormType.TRIANGLE);
        missionBlueLine = new ParcelMission(board,ColorType.BLUE, 1, FormType.LINE);
        missionRedLine = new ParcelMission(board,ColorType.RED, 1, FormType.LINE);
    }


    @Test
    void LineForm() {
        List<Coordinate> triangleForm = stratMissionParcel.setForm(coordinate1, FormType.LINE);

        assertEquals(coordinate1, triangleForm.get(0));
        assertEquals(coordinate2, triangleForm.get(1));
        assertEquals(coordinate7, triangleForm.get(2));
    }

    @Test
    void TriangleForm() {
        List<Coordinate> triangleForm = stratMissionParcel.setForm(coordinate2, FormType.TRIANGLE);

        assertEquals(coordinate2, triangleForm.get(0));
        assertEquals(coordinate7, triangleForm.get(1));
        assertEquals(coordinate3, triangleForm.get(2));
    }
///////////////////////////////

    @Test
    void coordAroundUse_Central() {
        Coordinate coordAroundUse = stratMissionParcel.coordAroundUse(coordinate1);

        assertEquals(new Coordinate(0, 0, 0), coordAroundUse);
    }

    @Test
    void coordAroundUse_Coordinate3() {
        board.placeParcel(new Parcel(), coordinate3);
        assertTrue(board.isPlacedParcel(coordinate3));
        Coordinate coordAroundUse = stratMissionParcel.coordAroundUse(coordinate7);

        assertEquals(coordinate3, coordAroundUse);
    }

    @Test
    void coordAroundUse_None() {
        Coordinate coordAroundUse = stratMissionParcel.coordAroundUse(coordinate7);

        assertNull(coordAroundUse);
    }
///////////////////////////////

    @Test
    void coordNeedToDoBlueTriangle_1BlueParcelPut() {
        board.placeParcel(new Parcel(ColorType.BLUE), coordinate2);
        List<Coordinate> coordNeedToDoBlueTriangle = stratMissionParcel.coordNeedToDoMission(coordinate2, missionBlueTriangle);

        assertEquals(2, coordNeedToDoBlueTriangle.size());
        assertEquals(coordinate3, coordNeedToDoBlueTriangle.get(0));
        assertEquals(coordinate7, coordNeedToDoBlueTriangle.get(1));
    }

    @Test
    void coordNeedToDoBlueTriangle_1RedParcelPut() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);
        List<Coordinate> coordNeedToDoBlueTriangle = stratMissionParcel.coordNeedToDoMission(coordinate2, missionBlueTriangle);

        assertNull(coordNeedToDoBlueTriangle);
    }

    @Test
    void coordNeedToDoBlueTriangle_0ParcelPut() {
        List<Coordinate> coordNeedToDoRedLine = stratMissionParcel.coordNeedToDoMission(coordinate2, missionBlueTriangle);

        assertEquals(3, coordNeedToDoRedLine.size());
        assertEquals(coordinate2, coordNeedToDoRedLine.get(0));
        assertEquals(coordinate3, coordNeedToDoRedLine.get(1));
        assertEquals(coordinate7, coordNeedToDoRedLine.get(2));
    }

    @Test
    void coordNeedToDoRedLine_1RedParcelPut() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);
        List<Coordinate> coordNeedToDoRedLine = stratMissionParcel.coordNeedToDoMission(coordinate1, missionRedLine);

        assertEquals(3, coordNeedToDoRedLine.size());
        assertEquals(coordinate2, coordNeedToDoRedLine.get(0));
        assertEquals(coordinate3, coordNeedToDoRedLine.get(1));
        assertEquals(coordinate7, coordNeedToDoRedLine.get(2));
    }

    @Test
    void coordNeedToDoRedLine_1BlueParcelPut() {
        board.placeParcel(new Parcel(ColorType.BLUE), coordinate1);
        List<Coordinate> coordNeedToDoRedLine = stratMissionParcel.coordNeedToDoMission(coordinate1, missionRedLine);

        assertNull(coordNeedToDoRedLine);
    }

    @Test
    void coordNeedToDoRedLine_0ParcelPut() {
        List<Coordinate> coordNeedToDoRedLine = stratMissionParcel.coordNeedToDoMission(coordinate1, missionRedLine);

        assertEquals(4, coordNeedToDoRedLine.size());
        assertEquals(coordinate2, coordNeedToDoRedLine.get(0));
        assertEquals(coordinate3, coordNeedToDoRedLine.get(1));
        assertEquals(coordinate7, coordNeedToDoRedLine.get(2));
        assertEquals(coordinate1, coordNeedToDoRedLine.get(3));
    }
///////////////////////////////

    @Test
    void bestCoordinatesForMissionRedLine_1RedParcelPut() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);
        List<Coordinate> coordNeedToDoRedLine = stratMissionParcel.bestCoordinatesForMission(missionRedLine);

        assertEquals(3, coordNeedToDoRedLine.size());
        assertEquals(coordinate3, coordNeedToDoRedLine.get(0));
        assertEquals(coordinate7, coordNeedToDoRedLine.get(1));
        assertEquals(coordinate1, coordNeedToDoRedLine.get(2));
    }
///////////////////////////////

    @Test
    void bestCoordinatesIn0Mission_RedParcelAvailable() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);
        List<Coordinate> bestCoordinatesForAllMission = stratMissionParcel.bestCoordsInAllMission(ColorType.RED);

        assertNull(bestCoordinatesForAllMission);
    }
///////////////////////////////

    @Test
    void putParcel_0Mission() {
        assertEquals(1, board.getPlacedParcels().size());
        stratMissionParcel.putParcel();
        assertEquals(2, board.getPlacedParcels().size());
    }
///////////////////////////////

    @Test
    void BestCanalToIrrigateCoordinate4_0CanalPut() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);
        board.placeParcel(new Parcel(ColorType.RED), coordinate3);
        board.placeParcel(new Parcel(ColorType.RED), coordinate7);
        Coordinate[] bestCanal = stratMissionParcel.getBestCanal(coordinate7);

        System.out.println(bestCanal[0]);
        System.out.println(bestCanal[1]);
        assertTrue(Arrays.stream(bestCanal).anyMatch(coord -> coord.equals(coordinate2)));
        assertTrue(Arrays.stream(bestCanal).anyMatch(coord -> coord.equals(coordinate3)));
    }

    @Test
    void BestCanalToIrrigateCoordinate4_1CanalPut() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);
        board.placeParcel(new Parcel(ColorType.RED), coordinate3);
        board.placeParcel(new Parcel(ColorType.RED), coordinate7);
        board.placeCanal(new Canal(), coordinate2, coordinate3);
        Coordinate[] bestCanal = stratMissionParcel.getBestCanal(coordinate7);

        assertTrue(Arrays.stream(bestCanal).anyMatch(coord -> coord.equals(coordinate7)));
    }
///////////////////////////////

    @Test
    void findEndMissionWithoutCanal_0Mission() {
        assertEquals(0, stratMissionParcel.coordEndMissionNoIrrigate().size());
    }
///////////////////////////////

    @Test
    void putCanal_0Mission() {
        board.placeParcel(new Parcel(ColorType.RED), coordinate1);
        board.placeParcel(new Parcel(ColorType.RED), coordinate2);

        assertEquals(0, board.getPlacedCanals().size());
        stratMissionParcel.putCanal();
        assertEquals(1, board.getPlacedCanals().size());
    }
///////////////////////////////

    @Test
    void judiciousDrawMission(){
        assertTrue(stratMissionParcel.isJudiciousDrawMission());
    }

    @Test
    void notJudiciousDrawMission_ActionAlreadyPlay() {
        game.getTemporaryInventory().add(ActionType.DRAW_MISSION);
        assertFalse(stratMissionParcel.isJudiciousDrawMission());
    }
///////////////////////////////

    @Test
    void judiciousPutParcel() {
        assertTrue(stratMissionParcel.isJudiciousPutParcel());
    }

    @Test
    void notJudiciousPutParcel_ActionAlreadyPlay() {
        game.getTemporaryInventory().add(ActionType.DRAW_PARCELS);
        assertFalse(stratMissionParcel.isJudiciousPutParcel());
    }
///////////////////////////////

    @Test
    void judiciousPutCanal() {
        board.placeParcel(new Parcel(), coordinate1);
        board.placeParcel(new Parcel(), coordinate2);
        assertTrue(stratMissionParcel.isJudiciousPutCanal());
    }

    @Test
    void notJudiciousPutCanal_0PossiblePlace() {
        assertFalse(stratMissionParcel.isJudiciousPutCanal());
    }

    @Test
    void notJudiciousPutCanal_ActionAlreadyPlay() {
        game.getTemporaryInventory().add(ActionType.DRAW_CANAL);
        assertFalse(stratMissionParcel.isJudiciousPutCanal());
    }
///////////////////////////////

    @Test
    void botDrawMissionAndPutParcel() {
        assertEquals(0, game.getGameInteraction().getInventoryMissions().size());
        assertEquals(1, board.getPlacedParcels().size());
        parcelBot.botPlay();
        assertEquals(1, game.getGameInteraction().getInventoryMissions().size());
        assertEquals(2, board.getPlacedParcels().size());
    }
///////////////////////////////
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