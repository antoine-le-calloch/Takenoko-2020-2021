package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PeasantMissionTest {
    Board board;
    Resource resource;
    PeasantMission mission1;
    PeasantMission mission2;
    Parcel parcel1;
    IntelligentBot bot;

    @BeforeEach
    void setUp(){
        mission1 = new PeasantMission(2);
        mission2 = new PeasantMission(3);
        board = new Board();
        resource = new Resource();
        parcel1 = new Parcel("red");
        bot = new IntelligentBot(resource,board);
    }

    @Test
    void missionComplete(){
        board.placedParcel(parcel1,new Coordinate(1,-1,0));
        board.getPeasant().action(new Coordinate(1,-1,0), board);
        assertEquals(2,mission1.checkMissionPeasant(board));
        assertEquals(2,mission1.checkMission(board, bot));
    }

    @Test
    void missionIncomplete(){
        assertNotEquals(2,mission1.checkMissionPeasant(board));
    }
}