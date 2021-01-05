package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.WeatherType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();

    @Test
    void firstRoundSoNoWeather(){
        game.botPlay();
        assertNotEquals(3,game.getPlayerData().getStamina());//No weather so stamina=2
        assertFalse(game.getPlayerData().isActionCouldBeDoneTwice());//No weather so no same double action
    }

    @Test
    void atLeastTwoRoundsSoWeatherMustBePresent(){
        game.newRound();
        game.newRound();//2 round so No Weather dissapear
        game.botPlay();
        assertNotEquals(WeatherType.NO_WEATHER,game.getPlayerData().getWeatherType());
        //2e le dé météo peut être roll
    }

    @Test
    void gameMissionAndScoreTest(){
        assertEquals(0,game.getScores().get(0));
        assertEquals(0, game.getMissionsDone().get(0));
        game.getPlayerData().addScore(2);
        assertEquals(2,game.getScores().get(0));
        assertEquals(1,game.getMissionsDone().get(0));
    }
}