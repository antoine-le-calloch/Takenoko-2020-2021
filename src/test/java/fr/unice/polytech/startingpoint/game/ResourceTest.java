package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
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
        assertEquals(26, resource.getDeckParcel().size());
    }

    @Test public void goodInitializeCanal(){
        assertEquals(27, resource.getDeckCanal().size());
    }


    @Test public void goodInitializeMission(){
        assertEquals(45, resource.getNbMission());
    }

    @Test
    public void parcelDecrease() throws OutOfResourcesException {
        resource.selectParcel(resource.drawParcel().get(0));
        assertEquals(25,resource.getDeckParcel().size());
    }

    @Test
    public void canalDecrease() throws OutOfResourcesException {
        resource.drawCanal();
        assertEquals(26,resource.getDeckCanal().size());
    }

    @Test
    public void missionDecreasePeasant() throws OutOfResourcesException {
        resource.drawMission(MissionType.PEASANT);
        assertEquals(44,resource.getNbMission());
    }

    @Test
    public void missionDecreasePanda() throws OutOfResourcesException {
        resource.drawMission(MissionType.PANDA);
        assertEquals(44,resource.getNbMission());
    }

    @Test
    public void missionDecreaseParcel() throws OutOfResourcesException {
        resource.drawMission(MissionType.PARCEL);
        assertEquals(44,resource.getNbMission());
    }
}