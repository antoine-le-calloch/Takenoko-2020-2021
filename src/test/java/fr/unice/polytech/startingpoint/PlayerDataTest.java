package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.PlayerData;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.BotType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerDataTest {


    @Test
    public void completedMissionTest() {
        Resource resource = new Resource();
        Board board = new Board();
        PlayerData playerData = new PlayerData(new BotType[]{BotType.PEASANTBOT}, resource, board);
        playerData.completedMission(0, 3);
        assertEquals(playerData.getScores().get(0), 3);
        assertEquals(playerData.getMissions().get(0), 1);
    }




}


