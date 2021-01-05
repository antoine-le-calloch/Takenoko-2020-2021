package fr.unice.polytech.startingpoint.game.mission;

import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PeasantMissionTest {
    PeasantMission mission;
    Board boardMock;
    Parcel parcelMock;
    Parcel parcelMock2;
    Parcel parcelMock3;
    Parcel parcelMock4;

    
    @BeforeEach
    void setUp(){
        parcelMock = mock(Parcel.class);
        parcelMock2 = mock(Parcel.class);
        parcelMock3 = mock(Parcel.class);
        parcelMock4 = mock(Parcel.class);
        boardMock = mock(Board.class);
        mission = new PeasantMission(ColorType.RED, ImprovementType.NOTHING, 2);
    }

    /**
     * <h1><u>CAS UNE PARCELLE</u></h1>
     */

    @Test
    void missionComplete() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(4);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock.getImprovement()).thenReturn(ImprovementType.NOTHING);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);
        assertTrue(mission.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    @Test
    void wrongColor() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(4);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.GREEN);
        Mockito.when(parcelMock.getImprovement()).thenReturn(ImprovementType.NOTHING);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);
        assertFalse(mission.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    @Test
    void notEnoughBamboo(){
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock.getImprovement()).thenReturn(ImprovementType.NOTHING);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);
        assertFalse(mission.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    @Test
    void wrongImprovement(){
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(4);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock.getImprovement()).thenReturn(ImprovementType.WATERSHED);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);
        assertFalse(mission.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    /**
     * <h1><u>CAS PLUSIEURS PARCELLES</u></h1>
     */

    @Test
    void missionCompleteSpecialGreen() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);
        coordinateParcelMap.put(new Coordinate(1,0,-1), parcelMock2);
        coordinateParcelMap.put(new Coordinate(0,1,-1), parcelMock3);
        coordinateParcelMap.put(new Coordinate(-1,1,0), parcelMock4);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock2.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock3.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock4.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.GREEN);
        Mockito.when(parcelMock2.getColor()).thenReturn(ColorType.GREEN);
        Mockito.when(parcelMock3.getColor()).thenReturn(ColorType.GREEN);
        Mockito.when(parcelMock4.getColor()).thenReturn(ColorType.GREEN);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);

        PeasantMission specialMissionGreen = new PeasantMission(ColorType.GREEN, ImprovementType.WHATEVER, 2);
        assertTrue(specialMissionGreen.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));

    }


    @Test
    void missionCompleteSpecialYellow() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);
        coordinateParcelMap.put(new Coordinate(1,0,-1), parcelMock2);
        coordinateParcelMap.put(new Coordinate(0,1,-1), parcelMock3);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock2.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock3.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.YELLOW);
        Mockito.when(parcelMock2.getColor()).thenReturn(ColorType.YELLOW);
        Mockito.when(parcelMock3.getColor()).thenReturn(ColorType.YELLOW);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);

        PeasantMission specialMissionYellow = new PeasantMission(ColorType.YELLOW, ImprovementType.WHATEVER, 2);
        assertTrue(specialMissionYellow.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    @Test
    void missionCompleteSpecialRed() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);
        coordinateParcelMap.put(new Coordinate(1,0,-1), parcelMock2);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock2.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock2.getColor()).thenReturn(ColorType.RED);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);

        PeasantMission specialMissionRed = new PeasantMission(ColorType.RED, ImprovementType.WHATEVER, 2);
        assertTrue(specialMissionRed.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    @Test
    void missionCompleteSpecialRedTooManyParcelWithBamboo() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);
        coordinateParcelMap.put(new Coordinate(1,0,-1), parcelMock2);
        coordinateParcelMap.put(new Coordinate(0,1,-1), parcelMock2);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock2.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock3.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock2.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock3.getColor()).thenReturn(ColorType.RED);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);

        PeasantMission specialMissionRed = new PeasantMission(ColorType.RED, ImprovementType.WHATEVER, 2);
        assertTrue(specialMissionRed.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }


    @Test
    void missionIncompleteSpecialRedNotEnoughParcelWithBamboo() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);

        PeasantMission specialMissionRed = new PeasantMission(ColorType.RED, ImprovementType.WHATEVER, 2);
        assertFalse(specialMissionRed.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    @Test
    void missionIncompleteSpecialGreenTooManyBambooInTheParcels() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);
        coordinateParcelMap.put(new Coordinate(1,0,-1), parcelMock2);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(4);
        Mockito.when(parcelMock2.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock2.getColor()).thenReturn(ColorType.RED);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);

        PeasantMission specialMissionRed = new PeasantMission(ColorType.RED, ImprovementType.WHATEVER, 2);
        assertFalse(specialMissionRed.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }

    @Test
    void missionIncompleteSpecialGreenNotEnoughBamboo() {
        Map<Coordinate,Parcel> coordinateParcelMap = new HashMap<>();
        coordinateParcelMap.put(new Coordinate(1,-1,0), parcelMock);
        coordinateParcelMap.put(new Coordinate(1,0,-1), parcelMock2);

        Mockito.when(parcelMock.getNbBamboo()).thenReturn(2);
        Mockito.when(parcelMock2.getNbBamboo()).thenReturn(3);
        Mockito.when(parcelMock.getColor()).thenReturn(ColorType.RED);
        Mockito.when(parcelMock2.getColor()).thenReturn(ColorType.RED);

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(coordinateParcelMap);

        PeasantMission specialMissionRed = new PeasantMission(ColorType.RED, ImprovementType.WHATEVER, 2);
        assertFalse(specialMissionRed.checkMission(new ArrayList<>(boardMock.getPlacedParcels().values())));
    }
}