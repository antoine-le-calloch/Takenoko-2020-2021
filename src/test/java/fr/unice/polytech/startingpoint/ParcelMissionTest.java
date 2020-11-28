package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParcelMissionTest {
    Parcel parcel1;
    Parcel parcel2;
    Parcel parcel3;
    Parcel parcel4;
    Board board;
    ParcelMission mission1;
    ParcelMission mission2;
    ParcelMission mission3;
    ParcelMission mission4;

    @BeforeEach
    void setUp(){
        mission1 = new ParcelMission(2,"triangle","red");
        mission2 = new ParcelMission(3,"ligne","red");
        mission3 = new ParcelMission(2,"triangle","blue");
        mission4 = new ParcelMission(3,"ligne","blue");
        board = new Board();
        parcel1 = new Parcel("red");
        parcel2 = new Parcel("red");
        parcel3 = new Parcel("red");
        parcel4 = new Parcel("red");
    }

    @Test
    void newMission(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertEquals(mission1, mission1);
    }

    @Test void checkMissionTriangle(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getIrrigatedParcels().add(new Coordinate(1,-1,0));
        board.getIrrigatedParcels().add(new Coordinate(0,-1,1));
        board.getIrrigatedParcels().add(new Coordinate(1,-2,1));
        assertEquals(2, mission1.checkMissionParcel(board));
        assertEquals(2, mission1.checkMission(board));
    }

    @Test void checkNoMissionTriangle(){
        assertEquals(0, mission1.checkMissionParcel(board));
        assertEquals(0, mission1.checkMission(board));
    }


    @Test void checkMissionligneOnBoard(){
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getIrrigatedParcels().add(new Coordinate(0,-1,1));
        board.getIrrigatedParcels().add(new Coordinate(1,0,-1));
        board.getIrrigatedParcels().add(new Coordinate(1,-1,0));
        board.getIrrigatedParcels().add(new Coordinate(1,-2,1));
        assertEquals(3,mission2.checkMissionParcel(board));
        assertEquals(3,mission2.checkMission(board));
    }


    @Test void checkNoMissionLigne(){
        assertEquals(0,mission2.checkMissionParcel(board));
        assertEquals(0,mission2.checkMission(board));
    }


    @Test void triangleOnBoardGoodColor(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getIrrigatedParcels().add(new Coordinate(1,-1,0));
        board.getIrrigatedParcels().add(new Coordinate(0,-1,1));
        board.getIrrigatedParcels().add(new Coordinate(1,-2,1));
        assertTrue(mission1.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getIrrigatedParcels().add(new Coordinate(0,-1,1));
        board.getIrrigatedParcels().add(new Coordinate(1,0,-1));
        board.getIrrigatedParcels().add(new Coordinate(1,-1,0));
        board.getIrrigatedParcels().add(new Coordinate(1,-2,1));
        assertTrue(mission1.checkFormIrrigateWithColor(board,2,5));
    }


    @Test void triangleOnBoardBadColor(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getIrrigatedParcels().add(new Coordinate(1,-1,0));
        board.getIrrigatedParcels().add(new Coordinate(0,-1,1));
        board.getIrrigatedParcels().add(new Coordinate(1,-2,1));
        assertFalse(mission3.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneOnBoardBadColor(){ //check Line
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getIrrigatedParcels().add(new Coordinate(0,-1,1));
        board.getIrrigatedParcels().add(new Coordinate(1,0,-1));
        board.getIrrigatedParcels().add(new Coordinate(1,-1,0));
        board.getIrrigatedParcels().add(new Coordinate(1,-2,1));
        assertFalse(mission3.checkFormIrrigateWithColor(board,2,5));
    }

    @Test void triangleNotIrrigated(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneNotIrrigated(){ //checkLine
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,2,5));
    }


    @Test void wrongTriangle(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel4,new Coordinate(0,1,-1));
        board.getIrrigatedParcels().add(new Coordinate(1,-1,0));
        board.getIrrigatedParcels().add(new Coordinate(0,1,-1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,0,1));
    }

    @Test void wrongLine(){ //checkLine
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.getIrrigatedParcels().add(new Coordinate(0,-1,1));
        board.getIrrigatedParcels().add(new Coordinate(0,-2,2));
        assertFalse(mission1.checkFormIrrigateWithColor(board,2,5));
    }
}