package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.BadCoordinateException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
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

public class PeasantMissionTest {
    Game game;
    Board board;
    PeasantMission mission1;
    PeasantMission mission2;

    @BeforeEach
    void setUp(){
        game = new Game();
        board = game.getBoard();
        mission1 = new PeasantMission(board,ColorType.RED, 2);
        mission2 = new PeasantMission(board,ColorType.BLUE, 2);
    }

    @Test
    void missionComplete() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        board.placeParcel(new Parcel(ColorType.RED),new Coordinate(1,-1,0));
        game.getGameInteraction().moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0));
        assertTrue(mission1.checkMission(game.getPlayerData().getInventory()));
    }

    @Test
    void wrongColor() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        board.placeParcel(new Parcel(ColorType.RED),new Coordinate(1,-1,0));
        game.getGameInteraction().moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0));
        assertFalse(mission2.checkMission(game.getPlayerData().getInventory()));
    }

    @Test
    void notEnoughBamboo(){
        assertFalse(mission1.checkMission(game.getPlayerData().getInventory()));
    }
}