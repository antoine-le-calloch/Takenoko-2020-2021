package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDataTest {
    Game game;
    Rules rules;
    PlayerData playerData;
    Board board;

    @BeforeEach
    void initialize(){
        game = new Game(new BotType[]{BotType.RANDOM,BotType.PARCEL_BOT,BotType.PANDA_BOT,BotType.PEASANT_BOT},4);
        rules = game.getRules();
        playerData = game.getPlayerData();
        board = game.getBoard();
    }

    @Test
    void completedMission(){
        playerData.completedMission( 3);
        playerData.nextBot();
        playerData.completedMission( 2);
        playerData.completedMission( 2);
        playerData.nextBot();
        playerData.nextBot();
        playerData.completedMission( 2);
        playerData.completedMission( 2);
        playerData.completedMission( 2);
        assertEquals(playerData.getScores(), new ArrayList<>(Arrays.asList(3,4,0,6)));
        assertEquals(playerData.getMissionsDone(), new ArrayList<>(Arrays.asList(1,2,0,3)));
    }

    @Test
    void isContinue(){
        assertTrue(playerData.isContinue());
        playerData.completedMission( 1);
        assertTrue(playerData.isContinue());
        playerData.completedMission( 1);
        assertTrue(playerData.isContinue());
        playerData.completedMission( 1);
        assertTrue(playerData.isContinue());
        playerData.completedMission( 1);
        assertFalse(playerData.isContinue());
    }

    @Test
    void botInventoryMissionDecrease(){
        playerData.addMission(new ParcelMission(ColorType.NO_COLOR,2, FormType.TRIANGLE));
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(),new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(),new Coordinate(1,-2,1));
        board.placeCanal(new Canal(),new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        board.placeCanal(new Canal(),new Coordinate(0,-1,1),new Coordinate(1,-2,1));
        assertEquals(0,playerData.getMissionsDone().get(0));
        assertEquals(1,playerData.getInventory().getMissions().size());
        assertEquals(0,playerData.getInventory().getScore());
        playerData.missionDone();
        assertEquals(1,playerData.getMissionsDone().get(0));
        assertEquals(0,playerData.getInventory().getMissions().size());
        assertEquals(2,playerData.getInventory().getScore());
    }
}


