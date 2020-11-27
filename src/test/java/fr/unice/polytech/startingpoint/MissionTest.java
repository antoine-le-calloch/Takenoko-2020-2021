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
        mission=new Mission(2,"triangle");
        mission2=new Mission(3,"ligne");
        board = new Board();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
        parcel3 = new Parcel();
        parcel4 = new Parcel();
    }

    @Test void newMission(){
        assertNotEquals(mission,null);
        assertNotEquals(mission,mission2);
        assertEquals(mission,mission);
    }

    @Test void checkMissionTriangle(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel2,new Coordinate(0,-1,1));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
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

    @Test void ligneOnBoard(){
        board.putParcel(parcel4,new Coordinate(0,-1,1));
        board.putParcel(parcel1,new Coordinate(1,0,-1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
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

}