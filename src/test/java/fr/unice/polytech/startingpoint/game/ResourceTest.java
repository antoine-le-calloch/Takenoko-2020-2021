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
    void initialize(){
        resource = new Resource();
    }

    @Test
    void goodInitializeParcel(){
        assertEquals(32, resource.getDeckParcel().size());
    }

    @Test
    void goodInitializeCanal(){
        assertEquals(27, resource.getDeckCanal().size());
    }


    @Test
    void goodInitializeMission(){
        assertEquals(45, resource.getNbMission());
    }

    @Test
    void parcelDecrease() throws OutOfResourcesException {
        resource.selectParcel(resource.drawParcels().get(0));
        assertEquals(31,resource.getDeckParcel().size());
    }

    @Test
    void canalDecrease() throws OutOfResourcesException {
        resource.drawCanal();
        assertEquals(26,resource.getDeckCanal().size());
    }

    @Test
    void missionDecreasePeasant() throws OutOfResourcesException {
        resource.drawMission(MissionType.Peasant);
        assertEquals(44,resource.getNbMission());
    }

    @Test
    void missionDecreasePanda() throws OutOfResourcesException {
        resource.drawMission(MissionType.Panda);
        assertEquals(44,resource.getNbMission());
    }

    @Test
    void missionDecreaseParcel() throws OutOfResourcesException {
        resource.drawMission(MissionType.Parcel);
        assertEquals(44,resource.getNbMission());
    }

    @Test
    void notOutOfResources(){
        assertFalse(resource.isEmpty());
    }

    @Test
    void outOfCanals() throws OutOfResourcesException {
        for (int i = 0; i < 27; i++) {
            resource.drawCanal();
        }
        assertTrue(resource.isEmpty());
        assertThrows(OutOfResourcesException.class, () -> resource.drawCanal());
    }

    @Test
    void outOfCParcel() throws OutOfResourcesException {
        for (int i = 0; i < 32; i++) {
            resource.selectParcel(resource.drawParcels().get(0));
        }
        assertTrue(resource.isEmpty());
        assertThrows(OutOfResourcesException.class, () -> resource.drawParcels());
    }

    @Test
    void outOfMissions() throws OutOfResourcesException {
        for (int i = 0; i < 15; i++) {
            resource.drawMission(MissionType.Panda);
            resource.drawMission(MissionType.Parcel);
            resource.drawMission(MissionType.Peasant);
        }
        assertTrue(resource.isEmpty());
        assertThrows(OutOfResourcesException.class, () -> resource.drawMission(MissionType.Panda));
        assertThrows(OutOfResourcesException.class, () -> resource.drawMission(MissionType.Peasant));
        assertThrows(OutOfResourcesException.class, () -> resource.drawMission(MissionType.Parcel));
    }
}