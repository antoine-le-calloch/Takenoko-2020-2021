package fr.unice.polytech.startingpoint.game;


import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.WeatherType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();

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
        assertEquals(2,game.getPlayerData().getScore()[0]);
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
        assertEquals(0,game.getPlayerData().getScore()[0]);
    }

    @Test
    void gameIsFinish(){
        BotType[] botList = new BotType[]{BotType.PARCEL_BOT,BotType.PANDA_BOT,BotType.PARCEL_BOT,BotType.PANDA_BOT};
        Game game=new Game(botList);
        for (int i = 0; i < 28; i++) {
            game.getPlayerData().addMissionDone();
            System.out.println(game.getMissionsDone());
            System.out.println(game.isContinue());
            game.nextBot();
        }
        assertFalse(game.isContinue());
    }

    @Test
    void gameIsntFinishBecauseLastTurnIsntFinish(){
        BotType[] botList = new BotType[]{BotType.PARCEL_BOT,BotType.PANDA_BOT,BotType.PARCEL_BOT,BotType.PANDA_BOT};
        Game game=new Game(botList);
        for (int i = 0; i < 25; i++) {
            game.getPlayerData().addMissionDone();
            game.nextBot();
        }
        assertTrue(game.isContinue());
    }

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
        assertEquals(0,game.getScores().get(0)[0]);
        assertEquals(0, game.getMissionsDone().get(0));

        game.getPlayerData().addMissionDone(2);

        assertEquals(2,game.getScores().get(0)[0]);
        assertEquals(1,game.getMissionsDone().get(0));
    }
}