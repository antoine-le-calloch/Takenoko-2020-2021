package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {
    Mission mission1;
    Mission mission2;
    Mission mission3;
    Mission mission4;
    Mission mission5;
    Mission mission6;

    @BeforeEach void setUp(){
        mission1 = new ParcelMission(2,"triangle","red");
        mission2 = new ParcelMission(3,"ligne","red");
        mission3 = new PandaMission(0, "red");
        mission4 = new PandaMission(1, "red");
        mission5 = new PeasantMission(0);
        mission6 = new PeasantMission(1);
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