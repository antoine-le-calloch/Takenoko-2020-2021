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
    Mission mission3;

    @BeforeEach void setUp(){
        mission=new Mission(1,1,"triangle");
        mission2=new Mission(2,2,"ligne");
        mission3=new Mission(3,2,"irrigated");
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
        assertNotEquals(mission,mission3);
    }

    @Test void triangleMissionDone(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel2,new Coordinate(0,-1,1));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        assertEquals(1,mission.checkMissionParcel(board));
    }


    @Test void ligneMissionDone(){
        board.putParcel(parcel4,new Coordinate(0,-1,1));
        board.putParcel(parcel1,new Coordinate(1,0,-1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        assertEquals(2,mission2.checkMissionParcel(board));
    }



    @Test void wrongTriangle(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel4,new Coordinate(0,1,-1));
        assertEquals(0,mission.checkMissionParcel(board));
    }

    @Test void toShortLine(){
        board.putParcel(parcel4,new Coordinate(0,-1,1));
        assertEquals(0,mission2.checkMissionParcel(board));
    }

    @Test void noMission(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        System.out.println(board.getParcel().size());
        assertEquals(0,mission2.checkMissionParcel(board));
        assertEquals(0,mission.checkMissionParcel(board));
    }

    @Test void missionIrrigated(){
        assertNotEquals(2,mission3.checkMissionIrrigated(board));
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        assertEquals(2,mission3.checkMissionIrrigated(board));
    }


}