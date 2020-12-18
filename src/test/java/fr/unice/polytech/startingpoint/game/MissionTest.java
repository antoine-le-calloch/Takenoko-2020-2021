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
    Mission mission1;
    Mission mission2;
    Mission mission3;
    Mission mission4;
    Mission mission5;
    Mission mission6;

    @BeforeEach void setUp(){
        mission1 = new ParcelMission(ColorType.Red, 2, FormType.Triangle);
        mission2 = new ParcelMission(ColorType.Red, 3, FormType.Line);
        mission3 = new PandaMission(ColorType.Red, 0);
        mission4 = new PandaMission(ColorType.Red, 1);
        mission5 = new PeasantMission(ColorType.Red, 0);
        mission6 = new PeasantMission(ColorType.Red, 1);
    }

    @Test void newMissionParcel(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertEquals(mission1, mission1);
    }

    @Test void newMissionPanda(){
        assertNotEquals(mission3,null);
        assertNotEquals(mission3,mission4);
        assertEquals(mission3, mission3);
    }

    @Test void newMissionPeasant(){
        assertNotEquals(mission5,null);
        assertNotEquals(mission5,mission6);
        assertEquals(mission5, mission5);
    }

    @Test void newMission(){
        assertNotEquals(mission1,mission3);
        assertNotEquals(mission1,mission5);
        assertNotEquals(mission3,mission5);
    }
}