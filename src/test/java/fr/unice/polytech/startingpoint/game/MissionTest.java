package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    ParcelMission mission1;
    ParcelMission mission2;
    PandaMission mission3;
    PandaMission mission4;
    PeasantMission mission5;
    PeasantMission mission6;

    @BeforeEach void setUp(){
        board = new Board();
        mission1 = new ParcelMission(ColorType.RED, FormType.TRIANGLE, 2);
        mission2 = new ParcelMission(ColorType.GREEN, FormType.LINE, 3);
        mission3 = new PandaMission(ColorType.RED, 0);
        mission4 = new PandaMission(ColorType.GREEN, 1);
        mission5 = new PeasantMission(ColorType.RED, ImprovementType.NOTHING, 0);
        mission6 = new PeasantMission(ColorType.GREEN, ImprovementType.NOTHING, 1);
    }

    @Test void newMissionParcel(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertNotEquals(mission1.getPoints(),mission2.getPoints());
        assertNotEquals(mission1.getColorType(),mission2.getColorType());
        assertEquals(mission1.getColorType(), mission1.getColorType());
        assertEquals(mission1.getPoints(), mission1.getPoints());
        assertEquals(mission1, mission1);
    }

    @Test void newMissionPanda(){
        assertNotEquals(mission3,null);
        assertNotEquals(mission3,mission4);
        assertNotEquals(mission3.getPoints(),mission4.getPoints());
        assertNotEquals(mission3.getColorType(),mission4.getColorType());
        assertEquals(mission3.getColorType(), mission3.getColorType());
        assertEquals(mission3.getPoints(), mission3.getPoints());
        assertEquals(mission3, mission3);
    }

    @Test void newMissionPeasant(){
        assertNotEquals(mission5,null);
        assertNotEquals(mission5,mission6);
        assertNotEquals(mission5.getPoints(),mission6.getPoints());
        assertNotEquals(mission5.getColorType(),mission6.getColorType());
        assertEquals(mission5.getColorType(), mission5.getColorType());
        assertEquals(mission5.getPoints(), mission5.getPoints());
        assertEquals(mission5, mission5);
    }
}