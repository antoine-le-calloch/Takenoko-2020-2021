package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.Mission;
import fr.unice.polytech.startingpoint.Game.PandaMission;
import fr.unice.polytech.startingpoint.Game.ParcelMission;
import fr.unice.polytech.startingpoint.Game.PeasantMission;
import fr.unice.polytech.startingpoint.Type.ColorType;
import fr.unice.polytech.startingpoint.Type.FormType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        mission1 = new ParcelMission(2, FormType.TRIANGLE, ColorType.RED);
        mission2 = new ParcelMission(3, FormType.LINE, ColorType.RED);
        mission3 = new PandaMission(0, ColorType.RED);
        mission4 = new PandaMission(1, ColorType.RED);
        mission5 = new PeasantMission(0, ColorType.RED);
        mission6 = new PeasantMission(1, ColorType.RED);
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