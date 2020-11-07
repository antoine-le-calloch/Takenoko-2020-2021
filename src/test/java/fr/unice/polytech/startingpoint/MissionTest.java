package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {

    Mission mission;
    Mission mission2;
    Mission mission3;


    @BeforeEach void setUp(){

        mission=new Mission(1,1);
        mission2=new Mission(1,2);
        mission3=new Mission(2,1);



    }

    @Test void newMission(){

        assertTrue(!mission.equals(null));
        assertTrue(!mission.equals(mission2));
        assertTrue(mission.equals(mission));
        assertTrue(!mission.equals(mission3));

    }

}