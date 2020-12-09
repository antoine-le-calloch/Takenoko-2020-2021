package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
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

public class ResourceTest {
    Resource resource;

    @BeforeEach
    public void initialize(){
        resource = new Resource();
    }

    @Test public void goodInitializeParcel(){
        assertEquals(26, resource.getParcel().size());
    }

    @Test public void goodInitializeCanal(){
        assertEquals(27, resource.getCanal().size());
    }

    /*
    @Test public void goodInitializeMission(){
        assertEquals(45, resource.getNbMission());
    }*/

    @Test
    public void parcelDecrease(){
        resource.drawParcel();
        assertEquals(25,resource.getParcel().size());
    }

    @Test
    public void canalDecrease(){
        resource.drawCanal();
        assertEquals(26,resource.getCanal().size());
    }

    @Test
    public void missionDecrease(){
        assertEquals(45,resource.getNbMission());
        resource.drawMission(MissionType.PANDA);
        assertEquals(44,resource.getNbMission());
    }
}