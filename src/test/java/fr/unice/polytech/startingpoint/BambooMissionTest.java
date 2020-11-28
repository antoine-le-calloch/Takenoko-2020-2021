package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BambooMissionTest {
    Board board;
    Resource resource;
    BambooMission mission1;
    BambooMission mission2;
    RandomBot bot;


    @BeforeEach
    void setUp(){
        mission1 = new BambooMission(2);
        mission2 = new BambooMission(3);
        board = new Board();
        resource = new Resource();
        bot = new RandomBot(resource, board);
    }

    @Test
    void missionComplete(){
        bot.inventoryBamboo[0] = 5;
        assertEquals(2,mission1.checkMission(board,bot));
        assertEquals(4,bot.inventoryBamboo[0]);
    }

    @Test
    void missionIncomplete(){
        bot.inventoryBamboo[0] = 0;
        assertEquals(0,mission1.checkMission(board,bot));
        assertEquals(0,bot.inventoryBamboo[0]);
    }
}
