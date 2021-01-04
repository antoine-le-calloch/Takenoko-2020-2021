package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    PandaMission mission1;
    PandaMission mission2;
    PandaMission mission3;
    Inventory inventory;

    @BeforeEach
    void setUp(){
        inventory = new Inventory();

        board = new Board();
        mission1 = new PandaMission(ColorType.RED, 2);
        mission2 = new PandaMission(ColorType.RED, 3);
        mission3 = new PandaMission(ColorType.ALL_COLOR, 3);
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