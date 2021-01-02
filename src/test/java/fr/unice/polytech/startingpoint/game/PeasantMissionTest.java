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
    PeasantMission missionWaterShed;
    PeasantMission missionEnclosure;

    
    @BeforeEach
    void setUp(){
        game = new Game();
        board = game.getBoard();
        missionRed = new PeasantMission(board,ColorType.RED, 2,ImprovementType.NOTHING);
        missionBlue = new PeasantMission(board,ColorType.BLUE, 2,ImprovementType.NOTHING);
        missionFertiziler = new PeasantMission(board,ColorType.BLUE, 2,ImprovementType.FERTILIZER);
        missionWaterShed = new PeasantMission(board,ColorType.BLUE, 2,ImprovementType.WATERSHED);
        missionEnclosure = new PeasantMission(board,ColorType.BLUE, 2,ImprovementType.ENCLOSURE);
    }

    @Test
    void missionComplete() {
        board.placeParcel(new Parcel(ColorType.RED),new Coordinate(1,-1,0));
        game.getGameInteraction().moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0));
        assertTrue(missionRed.checkMission(game.getPlayerData().getInventory()));
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
        board.placeParcel(new Parcel(ColorType.BLUE,ImprovementType.FERTILIZER),new Coordinate(1,-1,0));
        assertFalse(missionEnclosure.checkMission(game.getPlayerData().getInventory()));
        assertFalse(missionWaterShed.checkMission(game.getPlayerData().getInventory()));
    }

}