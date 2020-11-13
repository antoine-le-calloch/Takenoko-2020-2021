package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {
    Parcel parcel1;
    Parcel parcel2;
    Parcel parcel3;
    Board board;
    Mission mission;
    Mission mission2;
    Mission mission3;

    @BeforeEach void setUp(){
        mission=new Mission(1,1,"triangle");
        mission2=new Mission(1,2,"ligne");
        mission3=new Mission(2,1,"ligne");
        board = new Board();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
        parcel3 = new Parcel();
    }

    @Test void newMission(){
        assertTrue(!mission.equals(null));
        assertTrue(!mission.equals(mission2));
        assertTrue(mission.equals(mission));
        assertTrue(!mission.equals(mission3));
    }

    @Test void triangleMisssionDone(){
        System.out.println(board.putParcel(parcel1,new int[]{1,-1,0}));
        System.out.println(board.putParcel(parcel2,new int[]{0,-1,1}));
        System.out.println(board.putParcel(parcel3,new int[]{1,-2,1}));
        assertEquals(1,mission.checkMission(board));
    }
}