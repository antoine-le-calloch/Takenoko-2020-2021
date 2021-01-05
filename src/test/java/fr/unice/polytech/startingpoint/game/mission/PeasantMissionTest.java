package fr.unice.polytech.startingpoint.game.mission;

import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.type.CharacterType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    Game game;
    Board board;
    PeasantMission missionRed;
    PeasantMission missionBlue;
    PeasantMission missionFertiziler;

    
    @BeforeEach
    void setUp(){
        game = new Game();
        board = game.getBoard();
        missionRed = new PeasantMission(ColorType.RED, ImprovementType.NOTHING, 2);
        missionBlue = new PeasantMission(ColorType.GREEN, ImprovementType.NOTHING, 2);
        missionFertiziler = new PeasantMission(ColorType.GREEN, ImprovementType.FERTILIZER, 2);
    }

    @Test
    void missionPoints(){
        assertEquals(missionRed.getPoints(),missionFertiziler.getPoints());
        assertEquals(missionBlue.getPoints(),missionFertiziler.getPoints());
    }

    @Test
    void missionComplete() {
        Parcel parcel1 = new Parcel(ColorType.RED);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        parcel1.addBamboo();
        parcel1.addBamboo();
        parcel1.addBamboo();
        assertEquals(4, parcel1.getNbBamboo());
        assertTrue(missionRed.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void missionCompleteSpecialGreen() {
        Parcel parcel1 = new Parcel(ColorType.GREEN);
        Parcel parcel2 = new Parcel(ColorType.GREEN);
        Parcel parcel3 = new Parcel(ColorType.GREEN);
        Parcel parcel4 = new Parcel(ColorType.GREEN);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        board.placeParcel(parcel3,new Coordinate(0,1,-1));
        board.placeParcel(parcel4,new Coordinate(-1,1,0));
        for (int i = 0; i < 2; i++) {
            parcel1.addBamboo();
            parcel2.addBamboo();
            parcel3.addBamboo();
            parcel4.addBamboo();
        }
        PeasantMission specialMissionGreen = new PeasantMission(ColorType.GREEN, ImprovementType.WHATEVER, 2);
        assertTrue(specialMissionGreen.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void missionIncompleteSpecialGreenNotEnoughParcelWithBamboo() {
        Parcel parcel1 = new Parcel(ColorType.GREEN);
        Parcel parcel2 = new Parcel(ColorType.GREEN);
        Parcel parcel3 = new Parcel(ColorType.GREEN);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        board.placeParcel(parcel3,new Coordinate(0,1,-1));
        for (int i = 0; i < 2; i++) {
            parcel1.addBamboo();
            parcel2.addBamboo();
            parcel3.addBamboo();
        }
        PeasantMission specialMissionGreen = new PeasantMission(ColorType.GREEN, ImprovementType.WHATEVER, 2);
        assertFalse(specialMissionGreen.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void missionIncompleteSpecialGreenNotEnoughBamboo() {
        Parcel parcel1 = new Parcel(ColorType.GREEN);
        Parcel parcel2 = new Parcel(ColorType.GREEN);
        Parcel parcel3 = new Parcel(ColorType.GREEN);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        board.placeParcel(parcel3,new Coordinate(0,1,-1));
        parcel1.addBamboo();
        parcel2.addBamboo();
        parcel3.addBamboo();
        PeasantMission specialMissionGreen = new PeasantMission(ColorType.GREEN, ImprovementType.WHATEVER, 2);
        assertFalse(specialMissionGreen.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void missionCompleteSpecialYellow() {
        Parcel parcel1 = new Parcel(ColorType.YELLOW);
        Parcel parcel2 = new Parcel(ColorType.YELLOW);
        Parcel parcel3 = new Parcel(ColorType.YELLOW);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        board.placeParcel(parcel3,new Coordinate(0,1,-1));
        for (int i = 0; i < 2; i++) {
            parcel1.addBamboo();
            parcel2.addBamboo();
            parcel3.addBamboo();
        }
        PeasantMission specialMissionGreen = new PeasantMission(ColorType.YELLOW, ImprovementType.WHATEVER, 2);
        assertTrue(specialMissionGreen.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void missionCompleteSpecialRed() {
        Parcel parcel1 = new Parcel(ColorType.RED);
        Parcel parcel2 = new Parcel(ColorType.RED);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        for (int i = 0; i < 2; i++) {
            parcel1.addBamboo();
            parcel2.addBamboo();
        }
        PeasantMission specialMissionGreen = new PeasantMission(ColorType.RED, ImprovementType.WHATEVER, 2);
        assertTrue(specialMissionGreen.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void wrongColor() {
        board.placeParcel(new Parcel(ColorType.RED),new Coordinate(1,-1,0));
        game.getGameInteraction().moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0));
        assertFalse(missionBlue.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void notEnoughBamboo(){
        assertFalse(missionRed.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }

    @Test
    void wrongImprovement(){
        PeasantMission missionWaterShed = new PeasantMission(ColorType.GREEN, ImprovementType.WATERSHED, 2);
        PeasantMission missionEnclosure = new PeasantMission(ColorType.GREEN, ImprovementType.ENCLOSURE, 2);
        board.placeParcel(new Parcel(ColorType.GREEN,ImprovementType.FERTILIZER),new Coordinate(1,-1,0));
        assertFalse(missionEnclosure.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
        assertFalse(missionWaterShed.checkMission(new ArrayList<>(board.getPlacedParcels().values())));
    }
}