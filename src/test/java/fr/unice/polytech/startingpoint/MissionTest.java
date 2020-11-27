package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {
    Parcel parcel1;
    Parcel parcel2;
    Parcel parcel3;
    Parcel parcel4;
    Board board;
    Mission mission;
    Mission mission2;
    @BeforeEach void setUp(){
        mission=new Mission(2,"triangle","rouge");
        mission2=new Mission(3,"ligne","rouge");
        board = new Board();
        parcel1 = new Parcel("noColor");
        parcel2 = new Parcel("noColor");
        parcel3 = new Parcel("noColor");
        parcel4 = new Parcel("noColor");
    }

    @Test void newMission(){
        assertNotEquals(mission,null);
        assertNotEquals(mission,mission2);
        assertEquals(mission,mission);
    }

    @Test void checkMissionTriangle(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(1,-2,1));
        assertEquals(2,mission.checkMissionParcel(board));
        assertEquals(2,mission.checkMission(board));
    }

    @Test void checkNoMissionTriangle(){
        assertEquals(0,mission.checkMissionParcel(board));
        assertEquals(0,mission.checkMission(board));
    }

    @Test void checkMissionligneOnBoard(){
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(1,0,-1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(1,-2,1));
        assertEquals(3,mission2.checkMissionParcel(board));
        assertEquals(3,mission2.checkMission(board));
    }

    @Test void checkNoMissionLigne(){
        assertEquals(0,mission2.checkMissionParcel(board));
        assertEquals(0,mission2.checkMission(board));
    }


    @Test void triangleOnBoard(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(1,-2,1));
        assertTrue(mission.checkTriangle(board));
    }

    @Test void triangleNotIrrigated(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission.checkTriangle(board));
    }

    @Test void ligneOnBoard(){ //check Line
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(1,0,-1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(1,-2,1));
        assertTrue(mission.checkLine(board));
    }


    @Test void ligneNotIrrigated(){ //checkLine
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission.checkLine(board));
    }


    @Test void wrongTriangle(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel4,new Coordinate(0,1,-1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(0,1,-1));
        assertFalse(mission.checkTriangle(board));
    }

    @Test void wrongLine(){ //checkLine
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel4,new Coordinate(0,-2,2));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(0,-2,2));
        assertFalse(mission.checkLine(board));
    }

}