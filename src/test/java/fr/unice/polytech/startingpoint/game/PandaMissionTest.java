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
    Board board;
    Mission mission1;
    Mission mission2;
    Mission mission3;
    Inventory inventory;

    @BeforeEach
    void setUp(){
        inventory = new Inventory();

        board = new Board();
        mission1 = new PandaMission(board,ColorType.RED, 2);
        mission2 = new PandaMission(board,ColorType.RED, 3);
        mission3 = new PandaMission(board,ColorType.ALL_COLOR, 3);
    }

    @Test
    void missionCompleteOneColor(){
        inventory.addBamboo(ColorType.RED);
        inventory.addBamboo(ColorType.RED);
        assertTrue(mission1.checkMission(inventory));
    }

    @Test
    void missionCompleteAllColor(){
        inventory.addBamboo(ColorType.YELLOW);
        inventory.addBamboo(ColorType.GREEN);
        inventory.addBamboo(ColorType.RED);
        assertTrue(mission3.checkMission(inventory));
    }


    @Test
    void missionIncompleteBadColor(){
        inventory.addBamboo(ColorType.GREEN);
        inventory.addBamboo(ColorType.GREEN);
        assertFalse(mission1.checkMission(inventory));
    }

    @Test
    void missionIncompleteNotAllColor(){
        inventory.addBamboo(ColorType.YELLOW);
        inventory.addBamboo(ColorType.GREEN);
        assertFalse(mission3.checkMission(inventory));
    }

    @Test
    void missionIncompleteNoEnoughBamboo(){
        inventory.addBamboo(ColorType.RED);
        assertFalse(mission1.checkMission(inventory));
    }
}