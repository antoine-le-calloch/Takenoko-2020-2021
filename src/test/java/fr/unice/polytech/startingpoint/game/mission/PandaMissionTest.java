package fr.unice.polytech.startingpoint.game.mission;

import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.playerdata.Inventory;
import fr.unice.polytech.startingpoint.type.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    Inventory inventory;

    PandaMission mission1;
    PandaMission mission2;
    PandaMission mission3;

    @BeforeEach
    void setUp(){
        board = new Board();

        inventory = new Inventory();

        mission1 = new PandaMission(ColorType.RED, 2);
        mission2 = new PandaMission(ColorType.RED, 3);
        mission3 = new PandaMission(ColorType.ALL_COLOR, 3);
    }

    @Test
    void missionPoints(){
        assertEquals(mission2.getPoints(),mission3.getPoints());
        assertNotEquals(mission1.getPoints(),mission3.getPoints());
        assertNotEquals(mission1.getPoints(),mission2.getPoints());
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