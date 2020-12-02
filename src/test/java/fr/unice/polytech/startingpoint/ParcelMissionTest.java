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
    Resource resource;
    ParcelMission mission1;
    ParcelMission mission2;
    ParcelMission mission3;
    ParcelMission mission4;
    RandomBot bot;


    @BeforeEach
    void setUp(){
        mission1 = new ParcelMission(2,"triangle","red");
        mission2 = new ParcelMission(3,"ligne","red");
        mission3 = new ParcelMission(2,"triangle","blue");
        mission4 = new ParcelMission(3,"ligne","blue");
        board = new Board();
        resource = new Resource();
        parcel1 = new Parcel("red");
        parcel2 = new Parcel("red");
        parcel3 = new Parcel("red");
        parcel4 = new Parcel("red");
        bot = new RandomBot(resource, board);
    }

    @Test
    void newMission(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertEquals(mission1, mission1);
    }

    @Test void checkMissionTriangle(){
        board.isPlacedParcel(parcel1,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel2,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcelsAdd(new Coordinate(1,-1,0));
        board.irrigatedParcelsAdd(new Coordinate(0,-1,1));
        board.irrigatedParcelsAdd(new Coordinate(1,-2,1));
        assertEquals(2, mission1.checkMissionParcel(board));
        assertEquals(2, mission1.checkMission(board,bot));
    }

    @Test void checkNoMissionTriangle(){
        assertEquals(0, mission1.checkMissionParcel(board));
        assertEquals(0, mission1.checkMission(board,bot));
    }


    @Test void checkMissionligneOnBoard(){
        board.isPlacedParcel(parcel4,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel1,new Coordinate(1,0,-1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcelsAdd(new Coordinate(0,-1,1));
        board.irrigatedParcelsAdd(new Coordinate(1,0,-1));
        board.irrigatedParcelsAdd(new Coordinate(1,-1,0));
        board.irrigatedParcelsAdd(new Coordinate(1,-2,1));
        assertEquals(3,mission2.checkMissionParcel(board));
        assertEquals(3,mission2.checkMission(board,bot));
    }


    @Test void checkNoMissionLigne(){
        assertEquals(0,mission2.checkMissionParcel(board));
        assertEquals(0,mission2.checkMission(board,bot));
    }


    @Test void triangleOnBoardGoodColor(){ //checkTriangle
        board.isPlacedParcel(parcel1,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel2,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcelsAdd(new Coordinate(1,-1,0));
        board.irrigatedParcelsAdd(new Coordinate(0,-1,1));
        board.irrigatedParcelsAdd(new Coordinate(1,-2,1));
        assertTrue(mission1.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneOnBoardGoodColor(){ //check Line
        board.isPlacedParcel(parcel4,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel1,new Coordinate(1,0,-1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcelsAdd(new Coordinate(0,-1,1));
        board.irrigatedParcelsAdd(new Coordinate(1,0,-1));
        board.irrigatedParcelsAdd(new Coordinate(1,-1,0));
        board.irrigatedParcelsAdd(new Coordinate(1,-2,1));
        assertTrue(mission1.checkFormIrrigateWithColor(board,2,5));
    }


    @Test void triangleOnBoardBadColor(){ //checkTriangle
        board.isPlacedParcel(parcel1,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel2,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcelsAdd(new Coordinate(1,-1,0));
        board.irrigatedParcelsAdd(new Coordinate(0,-1,1));
        board.irrigatedParcelsAdd(new Coordinate(1,-2,1));
        assertFalse(mission3.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneOnBoardBadColor(){ //check Line
        board.isPlacedParcel(parcel4,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel1,new Coordinate(1,0,-1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcelsAdd(new Coordinate(0,-1,1));
        board.irrigatedParcelsAdd(new Coordinate(1,0,-1));
        board.irrigatedParcelsAdd(new Coordinate(1,-1,0));
        board.irrigatedParcelsAdd(new Coordinate(1,-2,1));
        assertFalse(mission3.checkFormIrrigateWithColor(board,2,5));
    }

    @Test void triangleNotIrrigated(){ //checkTriangle
        board.isPlacedParcel(parcel1,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel2,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneNotIrrigated(){ //checkLine
        board.isPlacedParcel(parcel4,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel1,new Coordinate(1,0,-1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,2,5));
    }


    @Test void wrongTriangle(){ //checkTriangle
        board.isPlacedParcel(parcel1,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel4,new Coordinate(0,1,-1));
        board.irrigatedParcelsAdd(new Coordinate(1,-1,0));
        board.irrigatedParcelsAdd(new Coordinate(0,1,-1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,0,1));
    }

    @Test void wrongLine(){ //checkLine
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.isPlacedParcel(parcel4,new Coordinate(0,-2,2));
        board.irrigatedParcelsAdd(new Coordinate(0,-1,1));
        board.irrigatedParcelsAdd(new Coordinate(0,-2,2));
        assertFalse(mission1.checkFormIrrigateWithColor(board,2,5));
    }
}