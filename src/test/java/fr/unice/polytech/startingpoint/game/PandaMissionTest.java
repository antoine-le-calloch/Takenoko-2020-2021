package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PandaMissionTest {
    Game game;
    Board board;
    Resource resource;
    PandaMission mission1;
    PandaMission mission2;
    RandomBot bot;
    Parcel parcel1;
    Parcel parcel2;

    @BeforeEach
    void setUp(){
        game = new Game();
        board = game.getBoard();
        resource = game.getResource();
        bot = new RandomBot(game.getGameInteraction());
        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.GREEN);
        mission1 = new PandaMission(board,ColorType.RED, 2);
        mission2 = new PandaMission(board,ColorType.RED, 3);
    }

    @Test
    void missionCompleteGoodColor(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));  // parcel red
        IntStream.range(0, 4).forEach(i -> game.getPlayerData().getInventory().addBamboo(parcel1.getColor()));
        assertEquals(4,game.getPlayerData().getInventory().getBamboo(ColorType.RED));
        assertTrue(mission1.checkMission(game.getPlayerData().getInventory()));
    }

    @Test
    void missionIncompleteBadColor(){
        board.placeParcel(parcel2,new Coordinate(1,-1,0)); // parcel blue
        IntStream.range(0, 5).forEach(i -> game.getPlayerData().getInventory().addBamboo(parcel2.getColor()));
        assertEquals(0,game.getPlayerData().getInventory().getBamboo(ColorType.RED));
        assertEquals(5,game.getPlayerData().getInventory().getBamboo(ColorType.GREEN));
        assertFalse(mission1.checkMission(game.getPlayerData().getInventory()));
    }

    @Test
    void missionIncompleteNoBamboo(){
        assertFalse(mission1.checkMission(game.getPlayerData().getInventory()));
        assertEquals(0,game.getPlayerData().getInventory().getBamboo(ColorType.RED));
    }
}