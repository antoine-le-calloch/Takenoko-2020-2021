package fr.unice.polytech.startingpoint.game.mission;

import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.game.playerdata.Inventory;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        inventory = game.getPlayerData().getInventory();

        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.RED);
        parcel3 = new Parcel(ColorType.RED);
        parcel4 = new Parcel(ColorType.RED);
        parcel5 = new Parcel(ColorType.GREEN);
        parcel6 = new Parcel(ColorType.GREEN);

        missionTR = new ParcelMission(ColorType.RED, FormType.TRIANGLE, 1);
        missionLR = new ParcelMission(ColorType.RED, FormType.LINE, 2);
        missionDR = new ParcelMission(ColorType.RED, FormType.DIAMOND, 3);
        missionAR = new ParcelMission(ColorType.RED, FormType.ARC, 4);
        missionDBR = new ParcelMission(ColorType.RED,ColorType.GREEN, FormType.DIAMOND, 5);
        missionTG = new ParcelMission(ColorType.GREEN, FormType.TRIANGLE, 1);
        missionLG = new ParcelMission(ColorType.GREEN, FormType.LINE, 2);
        missionDG = new ParcelMission(ColorType.GREEN, FormType.DIAMOND, 3);
        missionAG = new ParcelMission(ColorType.GREEN, FormType.ARC, 4);
        missionDBG = new ParcelMission(ColorType.YELLOW,ColorType.GREEN, FormType.DIAMOND, 5);
    }

    @Test
    void newMission(){
        assertNotEquals(missionTR,null);
        assertNotEquals(missionTR, missionLR);
        assertNotEquals(missionTR.getFormType(), missionLR.getFormType());
        assertEquals(missionTR, missionTR);
        assertEquals(missionTR.getFormType(), missionTG.getFormType());
    }

    @Test
    void missionColors(){
        assertNotEquals(missionDBR.getColorType2(),missionDBR.getColorType1());
        assertNotEquals(missionDBG.getColorType2(),missionDBG.getColorType1());
        assertEquals(missionDG.getColorType2(),missionDG.getColorType1());
        assertEquals(missionDR.getColorType2(),missionDR.getColorType1());
    }

    @Test
    void missionPoints(){
        assertEquals(missionTR.getPoints(),missionTG.getPoints());
        assertEquals(missionLR.getPoints(),missionLG.getPoints());
        assertEquals(missionDR.getPoints(),missionDG.getPoints());
        assertEquals(missionAR.getPoints(),missionAG.getPoints());
        assertEquals(missionDBR.getPoints(),missionDBG.getPoints());
        assertNotEquals(missionTR.getPoints(),missionLR.getPoints());
        assertNotEquals(missionDR.getPoints(),missionDBR.getPoints());
    }

    /**
     * <h1><u>CAS TRIANGLE</u></h1>
     */

    @Test
    void checkNoMissionTriangle(){
        assertFalse(missionTR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void triangleOnBoardGoodColor(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionTR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void triangleOnBoardBadColor() { //checkTriangle
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.placeParcel(parcel2, new Coordinate(0, -1, 1));
        board.placeParcel(parcel3, new Coordinate(1, -2, 1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionTG.checkMission(board.getPlacedParcels()));
    }

    @Test
    void triangleNotIrrigated() { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(missionTR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void wrongTriangle() { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        assertFalse(missionTR.checkMission(board.getPlacedParcels()));
    }

    /**
     * <h1><u>CAS LINE</u></h1>
     */

    @Test
    void checkNoMissionLine(){
        assertFalse(missionLR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void lineOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionLR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void lineOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionLG.checkMission(board.getPlacedParcels()));
    }

    @Test
    void lineNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(missionLR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void wrongLine() { //checkLine
        board.placeParcel(parcel1, new Coordinate(0, -1, 1));
        board.placeParcel(parcel2, new Coordinate(1, -1, 0));
        board.placeParcel(parcel3, new Coordinate(2, -2, 0));
        board.getPlacedParcels().get(new Coordinate(2,-2,0)).setIrrigated();
        assertFalse(missionLR.checkMission(board.getPlacedParcels()));
    }

    /**
     * <h1><u>CAS DIAMOND</u></h1>
     */

    @Test
    void checkNoMissionDiamond(){
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void diamondOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionDR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void diamondOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDG.checkMission(board.getPlacedParcels()));
    }

    @Test
    void diamondNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void wrongDiamond() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,1,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
    }

    /**
     * <h1><u>CAS ARC</u></h1>
     */

    @Test
    void checkNoMissionArc(){
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void arcOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertTrue(missionAR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void arcOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(0,-2,2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertFalse(missionAG.checkMission(board.getPlacedParcels()));
    }

    @Test
    void arcNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(0,-2,2));
        assertFalse(missionAR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void wrongArc() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,1,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,0,-1));
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
    }

    /**
     * <h1><u>CAS BICOLORE DIAMOND</u></h1>
     */

    @Test
    void checkNoMissionDiamondBicolor(){
        assertFalse(missionDBR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void diamondBicolorOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-2,2));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(missionDBR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void diamondBicolorOnBoardBadColor() { //check Line
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-2,2));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDBG.checkMission(board.getPlacedParcels()));
    }

    @Test
    void diamondBicolorNotIrrigated() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-2,2));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
    }

    @Test
    void wrongDiamondBicolor() { //checkLine
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(0,-3,3));
        board.placeParcel(parcel5,new Coordinate(1,-1,0));
        board.placeParcel(parcel6,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(0,-3,3)).setIrrigated();
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(missionDR.checkMission(board.getPlacedParcels()));
    }
}