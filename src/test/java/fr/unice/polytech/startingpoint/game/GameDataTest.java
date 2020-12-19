package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameDataTest {
    Game game;
    Rules rules;
    GameData gameData;
    Board board;

    @BeforeEach
    void initialize(){
        game = new Game(new BotType[]{BotType.RANDOM,BotType.PARCEL_BOT,BotType.PANDA_BOT,BotType.PEASANT_BOT},4);
        rules = game.getRules();
        gameData = game.getGameData();
        board = game.getBoard();
    }

    @Test
    void completedMission(){
        gameData.completedMission( 3);
        gameData.nextBot();
        gameData.completedMission( 2);
        gameData.completedMission( 2);
        gameData.nextBot();
        gameData.nextBot();
        gameData.completedMission( 2);
        gameData.completedMission( 2);
        gameData.completedMission( 2);
        assertEquals(gameData.getScores(), new ArrayList<>(Arrays.asList(3,4,0,6)));
        assertEquals(gameData.getMissionsDone(), new ArrayList<>(Arrays.asList(1,2,0,3)));
    }

    @Test
    void isContinue(){
        assertTrue(gameData.isContinue());
        gameData.completedMission( 1);
        assertTrue(gameData.isContinue());
        gameData.completedMission( 1);
        assertTrue(gameData.isContinue());
        gameData.completedMission( 1);
        assertTrue(gameData.isContinue());
        gameData.completedMission( 1);
        assertFalse(gameData.isContinue());
    }

    @Test
    void botInventoryMissionDecrease(){
        gameData.addMission(new ParcelMission(ColorType.NO_COLOR,2, FormType.TRIANGLE));
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(),new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(),new Coordinate(1,-2,1));
        board.placeCanal(new Canal(),new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        board.placeCanal(new Canal(),new Coordinate(0,-1,1),new Coordinate(1,-2,1));
        assertEquals(0, gameData.getMissionsDone().get(0));
        assertEquals(1, gameData.getInventory().getMissions().size());
        assertEquals(0, gameData.getScores().get(0));
        gameData.missionDone();
        assertEquals(1, gameData.getMissionsDone().get(0));
        assertEquals(0, gameData.getInventory().getMissions().size());
        assertEquals(2, gameData.getScores().get(0));
    }
}


