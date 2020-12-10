package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
import fr.unice.polytech.startingpoint.exception.MoveCharacterException;
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
    Board board;
    Resource resource;
    PeasantMission mission1;
    PeasantMission mission2;
    Parcel parcel1;
    ParcelBot bot;

    @BeforeEach
    void setUp(){
        mission1 = new PeasantMission(ColorType.RED, 2);
        mission2 = new PeasantMission(ColorType.BLUE, 2);
        board = new Board();
        resource = new Resource();
        parcel1 = new Parcel(ColorType.RED);
        bot = new ParcelBot(resource,board);
    }

    @Test
    void missionComplete() throws MoveCharacterException {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.moveCharacter(board.getPeasant(), parcel1.getCoordinates());
        assertEquals(2,mission1.checkMission(board, bot.getInventory()));
    }

    @Test
    void wrongColor() throws MoveCharacterException {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.moveCharacter(board.getPeasant(), parcel1.getCoordinates());
        assertEquals(0,mission2.checkMission(board, bot.getInventory()));
    }

    @Test
    void notEnoughBamboo(){
        assertNotEquals(2,mission1.checkMission(board, bot.getInventory()));
    }
}