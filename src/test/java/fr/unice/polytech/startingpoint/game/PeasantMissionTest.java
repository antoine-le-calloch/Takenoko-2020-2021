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
        missionRed = new PeasantMission(board,ColorType.RED, 2,ImprovementType.NOTHING);
        missionBlue = new PeasantMission(board,ColorType.GREEN, 2,ImprovementType.NOTHING);
        missionFertiziler = new PeasantMission(board,ColorType.GREEN, 2,ImprovementType.FERTILIZER);
    }

    @Test
    void missionComplete() {
        Parcel parcel1 = new Parcel(ColorType.RED);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        parcel1.addBamboo();
        parcel1.addBamboo();
        parcel1.addBamboo();
        assertEquals(4, parcel1.getNbBamboo());
        assertTrue(missionRed.checkMission(game.getPlayerData().getInventory()));
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
        Mission speciaMissionGreen = new PeasantMission(board,ColorType.GREEN, 2,ImprovementType.WHATEVER);
        assertTrue(speciaMissionGreen.checkMission(game.getPlayerData().getInventory()));
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
        Mission speciaMissionGreen = new PeasantMission(board,ColorType.GREEN, 2,ImprovementType.WHATEVER);
        assertFalse(speciaMissionGreen.checkMission(game.getPlayerData().getInventory()));
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
        Mission speciaMissionGreen = new PeasantMission(board,ColorType.GREEN, 2,ImprovementType.WHATEVER);
        assertFalse(speciaMissionGreen.checkMission(game.getPlayerData().getInventory()));
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
        Mission speciaMissionYellow = new PeasantMission(board,ColorType.YELLOW, 2,ImprovementType.WHATEVER);
        assertTrue(speciaMissionYellow.checkMission(game.getPlayerData().getInventory()));
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
        Mission specialMissionRed = new PeasantMission(board,ColorType.RED, 2,ImprovementType.WHATEVER);
        assertTrue(specialMissionRed.checkMission(game.getPlayerData().getInventory()));
    }

    @Test
    void wrongColor() {
        board.placeParcel(new Parcel(ColorType.RED),new Coordinate(1,-1,0));
        game.getGameInteraction().moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0));
        assertFalse(missionBlue.checkMission(game.getPlayerData().getInventory()));
    }

    @Test
    void notEnoughBamboo(){
        assertFalse(missionRed.checkMission(game.getPlayerData().getInventory()));
    }

    @Test
    void wrongImprovement(){
        Mission missionWaterShed = new PeasantMission(board,ColorType.GREEN, 2,ImprovementType.WATERSHED);
        Mission missionEnclosure = new PeasantMission(board,ColorType.GREEN, 2,ImprovementType.ENCLOSURE);
        board.placeParcel(new Parcel(ColorType.GREEN,ImprovementType.FERTILIZER),new Coordinate(1,-1,0));
        assertFalse(missionEnclosure.checkMission(game.getPlayerData().getInventory()));
        assertFalse(missionWaterShed.checkMission(game.getPlayerData().getInventory()));
    }

}