package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class ParcelMissionTest {
    Game game;
    Board board;
    Resource resource;
    Inventory inventory;

    Parcel parcel1;
    Parcel parcel2;
    Parcel parcel3;
    Parcel parcel4;
    Parcel parcel5;
    Parcel parcel6;

    ParcelMission missionTR;
    ParcelMission missionLR;
    ParcelMission missionDR;
    ParcelMission missionAR;
    ParcelMission missionDBR;
    ParcelMission missionTG;
    ParcelMission missionLG;
    ParcelMission missionDG;
    ParcelMission missionAG;
    ParcelMission missionDBG;

    @BeforeEach
    void setUp(){
        game = new Game();
        board = game.getBoard();
        resource = game.getResource();
        inventory = game.getPlayerData().getInventory();

        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.RED);
        parcel3 = new Parcel(ColorType.RED);
        parcel4 = new Parcel(ColorType.RED);
        parcel5 = new Parcel(ColorType.GREEN);
        parcel6 = new Parcel(ColorType.GREEN);

        missionTR = new ParcelMission(board,ColorType.RED, 0, FormType.TRIANGLE);
        missionLR = new ParcelMission(board,ColorType.RED, 0, FormType.LINE);
        missionDR = new ParcelMission(board,ColorType.RED, 0, FormType.DIAMOND);
        missionAR = new ParcelMission(board,ColorType.RED, 0, FormType.ARC);
        missionDBR = new ParcelMission(board,ColorType.RED,ColorType.GREEN, 0, FormType.DIAMOND);
        missionTG = new ParcelMission(board,ColorType.GREEN, 0, FormType.TRIANGLE);
        missionLG = new ParcelMission(board,ColorType.GREEN, 0, FormType.LINE);
        missionDG = new ParcelMission(board,ColorType.GREEN, 0, FormType.DIAMOND);
        missionAG = new ParcelMission(board,ColorType.GREEN, 0, FormType.ARC);
        missionDBG = new ParcelMission(board,ColorType.YELLOW,ColorType.GREEN, 0, FormType.DIAMOND);
    }

    @Test
    void newMission(){
        assertNotEquals(missionTR,null);
        assertNotEquals(missionTR, missionLR);
        assertNotEquals(missionTR.getFormType(), missionLR.getFormType());
        assertEquals(missionTR, missionTR);
        assertEquals(missionTR.getFormType(), missionTG.getFormType());
    }

    /**
     * <h1><u>CAS TRIANGLE</u></h1>
     */

    @Test
    void checkNoMissionTriangle(){
        assertFalse(missionTR.checkMission(inventory));
    }

    @Test
    void triangleOnBoardGoodColor(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionTR.checkMission(inventory));
    }

    @Test
    void triangleOnBoardBadColor() { //checkTriangle
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.placeParcel(parcel2, new Coordinate(0, -1, 1));
        board.placeParcel(parcel3, new Coordinate(1, -2, 1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionTG.checkMission(inventory));
    }

    @Test
    void triangleNotIrrigated() { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(missionTR.checkMission(inventory));
    }

    @Test
    void wrongTriangle() { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        assertFalse(missionTR.checkMission(inventory));
    }

    /**
     * <h1><u>CAS LINE</u></h1>
     */

    @Test
    void checkNoMissionLine(){
        assertFalse(missionLR.checkMission(inventory));
    }

    @Test
    void lineOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionLR.checkMission(inventory));
    }

    @Test
    void lineOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionLG.checkMission(inventory));
    }

    @Test
    void lineNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(missionLR.checkMission(inventory));
    }

    @Test
    void wrongLine() { //checkLine
        board.placeParcel(parcel1, new Coordinate(0, -1, 1));
        board.placeParcel(parcel2, new Coordinate(1, -1, 0));
        board.placeParcel(parcel3, new Coordinate(2, -2, 0));
        board.getPlacedParcels().get(new Coordinate(2,-2,0)).setIrrigated();
        assertFalse(missionLR.checkMission(inventory));
    }

    /**
     * <h1><u>CAS DIAMOND</u></h1>
     */

    @Test
    void checkNoMissionDiamond(){
        assertFalse(missionDR.checkMission(inventory));
    }

    @Test
    void diamondOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionDR.checkMission(inventory));
    }

    @Test
    void diamondOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDG.checkMission(inventory));
    }

    @Test
    void diamondNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        assertFalse(missionDR.checkMission(inventory));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertFalse(missionDR.checkMission(inventory));
    }

    @Test
    void wrongDiamond() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,1,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDR.checkMission(inventory));
    }

    /**
     * <h1><u>CAS ARC</u></h1>
     */

    @Test
    void checkNoMissionArc(){
        assertFalse(missionDR.checkMission(inventory));
    }

    @Test
    void arcOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertTrue(missionAR.checkMission(inventory));
    }

    @Test
    void arcOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertFalse(missionAG.checkMission(inventory));
    }

    @Test
    void arcNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(0,-2,2));
        assertFalse(missionAR.checkMission(inventory));
    }

    @Test
    void wrongArc() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,1,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,0,-1));
        assertFalse(missionDR.checkMission(inventory));
    }

    /**
     * <h1><u>CAS BICOLORE DIAMOND</u></h1>
     */

    @Test
    void checkNoMissionDiamondBicolor(){
        assertFalse(missionDBR.checkMission(inventory));
    }

    @Test
    void diamondBicolorOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-2,2));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionDBR.checkMission(inventory));
    }

    @Test
    void diamondBicolorOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-2,2));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDBG.checkMission(inventory));
    }

    @Test
    void diamondBicolorNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-2,2));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        assertFalse(missionDR.checkMission(inventory));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertFalse(missionDR.checkMission(inventory));
    }

    @Test
    void wrongDiamondBicolor() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-3,3));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(0,-3,3)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDR.checkMission(inventory));
    }
}