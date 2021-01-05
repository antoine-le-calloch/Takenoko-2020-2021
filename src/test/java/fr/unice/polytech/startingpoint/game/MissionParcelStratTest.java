package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.bot.ParcelBot;
import fr.unice.polytech.startingpoint.bot.MissionParcelStrat;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.WeatherType;
import org.junit.jupiter.api.*;

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
    ParcelMission missionRedLine;

    Coordinate coordinate1;
    Coordinate coordinate2;
    Coordinate coordinate3;
    Coordinate coordinate4;
    Coordinate coordinate5;
    Coordinate coordinate7;
    Coordinate coordinate8;
    Coordinate coordinate9;

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
        coordinate8 = new Coordinate(0, -2, 2); //4-6h éloigné de un
        coordinate9 = new Coordinate(-1, -1, 2); //6h éloigné de un

        game = new Game();
        board = game.getBoard();
        parcelBot = new ParcelBot(game.getGameInteraction());

        stratMissionParcel = new MissionParcelStrat(parcelBot);

        missionBlueTriangle = new ParcelMission(ColorType.GREEN, FormType.TRIANGLE, 1);
        missionRedLine = new ParcelMission(ColorType.RED, FormType.LINE, 1);
    }


    @Test
    void LineForm() {
        List<Coordinate> triangleForm = stratMissionParcel.setForm(coordinate1, FormType.LINE);

        assertEquals(coordinate1, triangleForm.get(0));
        assertEquals(coordinate2, triangleForm.get(1));
        assertEquals(coordinate7, triangleForm.get(2));
        assertEquals(3,triangleForm.size());
    }

    @Test
    void TriangleForm() {
        List<Coordinate> triangleForm = stratMissionParcel.setForm(coordinate2, FormType.TRIANGLE);

        assertEquals(coordinate2, triangleForm.get(0));
        assertEquals(coordinate7, triangleForm.get(1));
        assertEquals(coordinate3, triangleForm.get(2));
        assertEquals(3,triangleForm.size());
    }

    @Test
    void diamandForm() {
        List<Coordinate> triangleForm = stratMissionParcel.setForm(coordinate2, FormType.DIAMOND);

        assertEquals(coordinate2, triangleForm.get(0));
        assertEquals(coordinate7, triangleForm.get(1));
        assertEquals(coordinate3, triangleForm.get(2));
        assertEquals(coordinate8, triangleForm.get(3));
        assertEquals(4,triangleForm.size());
    }

    @Test
    void arcForm() {
        List<Coordinate> triangleForm = stratMissionParcel.setForm(coordinate1, FormType.ARC);

        assertEquals(coordinate1, triangleForm.get(0));
        assertEquals(coordinate2, triangleForm.get(1));
        assertEquals(coordinate3, triangleForm.get(2));
        assertEquals(3,triangleForm.size());
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
        board.placeParcel(new Parcel(ColorType.GREEN), coordinate2);
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
        board.placeParcel(new Parcel(ColorType.GREEN), coordinate1);
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

   /* @Test
    void putParcel_1MissionBlueTriangle() {
        game.getGameInteraction().getPlayerData().getInventory().addMission(missionBlueTriangle);
        assertEquals(1, board.getPlacedParcels().size());
        stratMissionParcel.putParcel();
        assertEquals(2, board.getPlacedParcels().size());
    }*/
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

    /*@Test
    void judiciousPutCanal() {
        board.placeParcel(new Parcel(), coordinate1);
        board.placeParcel(new Parcel(), coordinate2);
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
    }*/
///////////////////////////////

    @Test
    void DrawMissionAndPutParcel() {
        assertEquals(0, game.getGameInteraction().getInventoryParcelMissions().size());
        assertEquals(1, board.getPlacedParcels().size());
        stratMissionParcel.stratOneTurn(WeatherType.NO_WEATHER);
        assertEquals(1, game.getGameInteraction().getInventoryParcelMissions().size());
        assertEquals(1, board.getPlacedParcels().size());
    }
}