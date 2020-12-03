package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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
        mission1 = new PandaMission(2, Color.RED);
        mission2 = new PandaMission(3, Color.RED);
        board = new Board();
        resource = new Resource();
        bot = new RandomBot(resource, board);
        parcel1 = new Parcel(Color.RED);
        parcel2 = new Parcel(Color.BLUE);
    }

    @Test
    void missionCompleteGoodColor(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));  // parcel red
        IntStream.range(0, 5).forEach(i -> {
            try {
                bot.addBamboo(parcel1.getColor());
            } catch (ExceptionTakenoko exceptionTakenoko) {
                exceptionTakenoko.printStackTrace();
            }
        });
        assertEquals(2,mission1.checkMissionPanda(bot));
        assertEquals(4,bot.getInventoryBamboo()[0]);
        assertEquals(2,mission1.checkMission(board, bot));
    }

    @Test
    void missionIncompleteBadColor(){
        board.placeParcel(parcel2,new Coordinate(1,-1,0)); // parcel blue
        IntStream.range(0, 5).forEach(i -> {
            try {
                bot.addBamboo(parcel2.getColor());
            } catch (ExceptionTakenoko exceptionTakenoko) {
                exceptionTakenoko.printStackTrace();
            }
        });
        assertEquals(0,bot.getInventoryBamboo()[0]);
        assertEquals(5,bot.getInventoryBamboo()[1]);
        assertEquals(0,mission1.checkMissionPanda(bot));
        //assertEquals(4,bot.getInventoryBamboo()[1]);
        //assertEquals(0,mission1.checkMission(board, bot));
    }

    @Test
    void missionIncompleteNoBamboo(){
        assertEquals(0,mission1.checkMissionPanda(bot));
        assertEquals(0,bot.getInventoryBamboo()[0]);
    }
}