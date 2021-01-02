package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class MissionTest {
    Board board;
    Mission mission1;
    Mission mission2;
    Mission mission3;
    Mission mission4;
    Mission mission5;
    Mission mission6;

    @BeforeEach void setUp(){
        board = new Board();
        mission1 = new ParcelMission(board,ColorType.RED, 2, FormType.TRIANGLE);
        mission2 = new ParcelMission(board,ColorType.GREEN, 3, FormType.LINE);
        mission3 = new PandaMission(board,ColorType.RED, 0);
        mission4 = new PandaMission(board,ColorType.GREEN, 1);
        mission5 = new PeasantMission(board,ColorType.RED, 0,ImprovementType.NOTHING);
        mission6 = new PeasantMission(board,ColorType.GREEN, 1,ImprovementType.NOTHING);
    }

    @Test void newMissionParcel(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertNotEquals(mission1.getPoints(),mission2.getPoints());
        assertNotEquals(mission1.getColor(),mission2.getColor());
        assertEquals(mission1.getColor(), mission1.getColor());
        assertEquals(mission1.getPoints(), mission1.getPoints());
        assertEquals(mission1, mission1);
    }

    @Test void newMissionPanda(){
        assertNotEquals(mission3,null);
        assertNotEquals(mission3,mission4);
        assertNotEquals(mission3.getPoints(),mission4.getPoints());
        assertNotEquals(mission3.getColor(),mission4.getColor());
        assertEquals(mission3.getColor(), mission3.getColor());
        assertEquals(mission3.getPoints(), mission3.getPoints());
        assertEquals(mission3, mission3);
    }

    @Test void newMissionPeasant(){
        assertNotEquals(mission5,null);
        assertNotEquals(mission5,mission6);
        assertNotEquals(mission5.getPoints(),mission6.getPoints());
        assertNotEquals(mission5.getColor(),mission6.getColor());
        assertEquals(mission5.getColor(), mission5.getColor());
        assertEquals(mission5.getPoints(), mission5.getPoints());
        assertEquals(mission5, mission5);
    }

    @Test void newMission(){
        assertNotEquals(mission1.getMissionType(),mission3.getMissionType());
        assertNotEquals(mission1.getMissionType(),mission5.getMissionType());
        assertNotEquals(mission3.getMissionType(),mission5.getMissionType());
    }
}