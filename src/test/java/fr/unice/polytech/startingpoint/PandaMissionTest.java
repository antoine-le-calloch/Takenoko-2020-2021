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

    @BeforeEach
    void setUp(){
        mission1 = new PandaMission(2);
        mission2 = new PandaMission(3);
        board = new Board();
        resource = new Resource();
        bot = new RandomBot(resource, board);
    }

    @Test
    void missionComplete(){
        IntStream.range(0, 5).forEach(i -> bot.addBamboo());
        assertEquals(2,mission1.checkMissionPanda(bot));
        assertEquals(4,bot.getInventoryBamboo()[0]);
        assertEquals(2,mission1.checkMission(board, bot));
    }

    @Test
    void missionIncomplete(){
        assertEquals(0,mission1.checkMissionPanda(bot));
        assertEquals(0,bot.getInventoryBamboo()[0]);
    }
}