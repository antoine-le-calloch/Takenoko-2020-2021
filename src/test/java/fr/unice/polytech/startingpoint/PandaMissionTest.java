package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.RandomBot;
import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Coordinate;
import fr.unice.polytech.startingpoint.Game.Parcel;
import fr.unice.polytech.startingpoint.Game.PandaMission;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.ColorType;
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
        mission1 = new PandaMission(2, ColorType.RED);
        mission2 = new PandaMission(3, ColorType.RED);
        board = new Board();
        resource = new Resource();
        bot = new RandomBot(resource, board);
        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.BLUE);
    }

    @Test
    void missionCompleteGoodColor(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));  // parcel red
        IntStream.range(0, 5).forEach(i -> {
            bot.getInventory().addBamboo(parcel1.getColor());
        });
        assertEquals(2,mission1.checkMissionPanda(bot));
        assertEquals(4,bot.getInventory().getBamboo(ColorType.RED));
        assertEquals(2,mission1.checkMission(board, bot));
    }

    @Test
    void missionIncompleteBadColor(){
        board.placeParcel(parcel2,new Coordinate(1,-1,0)); // parcel blue
        IntStream.range(0, 5).forEach(i -> {
            bot.getInventory().addBamboo(parcel2.getColor());
        });
        assertEquals(0,bot.getInventory().getBamboo(ColorType.RED));
        assertEquals(5,bot.getInventory().getBamboo(ColorType.BLUE));
        assertEquals(0,mission1.checkMissionPanda(bot));
    }

    @Test
    void missionIncompleteNoBamboo(){
        assertEquals(0,mission1.checkMissionPanda(bot));
        assertEquals(0,bot.getInventory().getBamboo(ColorType.RED));
    }
}