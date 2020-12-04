package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
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
    Board board;
    Resource resource;
    PandaMission mission1;
    PandaMission mission2;
    RandomBot bot;
    Parcel parcel1;
    Parcel parcel2;

    @BeforeEach
    void setUp(){
        mission1 = new PandaMission(ColorType.RED, 2);
        mission2 = new PandaMission(ColorType.RED, 3);
        board = new Board();
        resource = new Resource();
        bot = new RandomBot(resource, board);
        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.BLUE);
    }

    @Test
    void missionCompleteGoodColor(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));  // parcel red
        IntStream.range(0, 4).forEach(i -> {
            bot.getInventory().addBamboo(parcel1.getColor());
        });
        assertEquals(4,bot.getInventory().getBamboo(ColorType.RED));
        assertEquals(2,mission1.checkMission(board, bot.getInventory()));
    }

    @Test
    void missionIncompleteBadColor(){
        board.placeParcel(parcel2,new Coordinate(1,-1,0)); // parcel blue
        IntStream.range(0, 5).forEach(i -> {
            bot.getInventory().addBamboo(parcel2.getColor());
        });
        assertEquals(0,bot.getInventory().getBamboo(ColorType.RED));
        assertEquals(5,bot.getInventory().getBamboo(ColorType.BLUE));
        assertEquals(0,mission1.checkMission(board, bot.getInventory()));
    }

    @Test
    void missionIncompleteNoBamboo(){
        assertEquals(0,mission1.checkMission(board, bot.getInventory()));
        assertEquals(0,bot.getInventory().getBamboo(ColorType.RED));
    }
}