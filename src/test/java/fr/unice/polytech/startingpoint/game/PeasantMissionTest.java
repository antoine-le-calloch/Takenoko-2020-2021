package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.BadMoveCharacterException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    Parcel parcel1;

    @BeforeEach
    void setUp(){
        mission1 = new PeasantMission(ColorType.Red, 2);
        mission2 = new PeasantMission(ColorType.Blue, 2);
        game = new Game();
        board = game.getBoard();
        parcel1 = new Parcel(ColorType.Red,ImprovementType.Nothing);
    }

    @Test
    void missionComplete() throws BadMoveCharacterException, OutOfResourcesException, IllegalAccessException {
        board.placeParcel(new Parcel(ColorType.Red,ImprovementType.Nothing),new Coordinate(1,-1,0));
        game.moveCharacter(CharacterType.Peasant, new Coordinate(1,-1,0));
        assertEquals(2,mission1.checkMission(board, game.getPlayerData().getInventory()));
    }

    @Test
    void wrongColor() throws BadMoveCharacterException, OutOfResourcesException, IllegalAccessException {
        board.placeParcel(new Parcel(ColorType.Red,ImprovementType.Nothing),new Coordinate(1,-1,0));
        game.moveCharacter(CharacterType.Peasant, new Coordinate(1,-1,0));
        assertEquals(0,mission2.checkMission(board, game.getPlayerData().getInventory()));
    }

    @Test
    void notEnoughBamboo(){
        assertNotEquals(2,mission1.checkMission(board, game.getPlayerData().getInventory()));
    }
}