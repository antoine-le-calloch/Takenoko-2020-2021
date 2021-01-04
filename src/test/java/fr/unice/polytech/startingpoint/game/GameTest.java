package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.WeatherType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void firstRoundSoNoWeather(){
        Game game=new Game();
        game.botPlay();
        assertNotEquals(3,game.getTemporaryInventory().getStamina());//No weather so stamina=2
        assertFalse(game.getTemporaryInventory().isActionCouldBeDoneTwice());//No weather so no same double action
    }

    @Test
    void atLeastTwoRoundsSoWeatherMustBePresent(){
        Game game=new Game();
        game.newRound();
        game.newRound();//2 round so No Weather dissapear
        game.botPlay();
        assertNotEquals(WeatherType.NO_WEATHER,game.getTemporaryInventory().getWeatherType());
        //2e le dé météo peut être roll
    }





}