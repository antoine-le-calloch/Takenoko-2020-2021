package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.Ressource.Resource;
import fr.unice.polytech.startingpoint.Type.MissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test public void goodInitializeMission(){
        assertEquals(45, resource.getNbMission());
    }

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
        resource.drawMission(MissionType.PANDA);
        assertEquals(44,resource.getNbMission());
    }
}