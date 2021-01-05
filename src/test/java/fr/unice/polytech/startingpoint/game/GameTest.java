package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.WeatherType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {

    @Test
    void aPlayerFinished(){
        Game game=new Game();
        for (int i = 0; i < 10; i++)
            game.getPlayerData().addMissionDone();
        assertTrue(game.isSomebodyFinished());
    }

    @Test
    void firstPlayerReceiveEmperor(){
        Game game=new Game();
        for (int i = 0; i < 10; i++)
            game.getPlayerData().addMissionDone();
        game.isSomebodyFinished();
        assertEquals(2,game.getPlayerData().getScore());
    }

    @Test
    void secondPlayerDidntReceiveEmperor(){
        BotType[] botList = new BotType[]{BotType.PARCEL_BOT,BotType.PANDA_BOT};
        Game game=new Game(botList);
        for (int i = 0; i < 9; i++) {
            game.getPlayerData().addMissionDone();
            game.nextBot();
        }
        game.isSomebodyFinished();
        game.nextBot();
        game.isSomebodyFinished();
        assertEquals(0,game.getPlayerData().getScore());
    }

    @Test
    void gameIsFinish(){
        BotType[] botList = new BotType[]{BotType.PARCEL_BOT,BotType.PANDA_BOT};
        Game game=new Game(botList);
        for (int i = 0; i < 8; i++) {
            game.getPlayerData().addMissionDone();
            game.nextBot();
        }
        assertFalse(game.isContinue());
    }

    @Test
    void gameIsntFinishBecauseLastTurnIsntFinish(){
        BotType[] botList = new BotType[]{BotType.PARCEL_BOT,BotType.PANDA_BOT,BotType.PARCEL_BOT,BotType.PANDA_BOT};
        Game game=new Game(botList);
        for (int i = 0; i < 14; i++) {
            game.getPlayerData().addMissionDone();
            game.nextBot();
        }
        assertTrue(game.isContinue());
    }

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