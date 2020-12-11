package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Type.BotType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDataTest {
    Game game;
    Rules rules;

    @Test
    public void initialize(){
        game = new Game();
        rules = new Rules(new Resource(),new Board());
        PlayerData playerData = new PlayerData(new BotType[]{BotType.PEASANTBOT}, game);
        playerData.completedMission(0, 3);
        assertEquals(playerData.getScores().get(0), 3);
        assertEquals(playerData.getMissionsDone().get(0), 1);
    }
}


