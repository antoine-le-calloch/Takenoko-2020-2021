package fr.unice.polytech.startingpoint.bot.strategie;


import fr.unice.polytech.startingpoint.bot.PandaBot;
import fr.unice.polytech.startingpoint.bot.ParcelBot;
import fr.unice.polytech.startingpoint.bot.strategie.RandomStrat;
import fr.unice.polytech.startingpoint.bot.strategie.Strategie;
import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.game.board.BoardRules;

import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StrategieTest {
    private Game game;
    private Parcel parcel1;
    private Parcel parcel2;
    private Parcel parcel3;
    private ParcelBot bot1;
    private Board board;
    private BoardRules boardRules;
    private Strategie strategie;

    @BeforeEach
    void setUp(){
        game = new Game();
        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.RED);
        parcel3 = new Parcel(ColorType.GREEN);
        bot1 = new ParcelBot(game.getGameInteraction());
        board = game.getBoard();
        boardRules = game.getRules();
        strategie = new RandomStrat(bot1);
    }

    @Test
    void judiciousDrawMission(){
        assertTrue(strategie.isJudiciousDrawMission());
    }

    @Test
    void notJudiciousDrawMission_ActionAlreadyPlay() {
        game.getPlayerData().add(ActionType.DRAW_MISSION);
        assertFalse(strategie.isJudiciousDrawMission());
    }

    @Test
    void initializeNextCoordinatesNextToCentral(){
        List<Coordinate> nextTocentral = strategie.possibleCoordinatesParcel();
        assertEquals(6,nextTocentral.size());
        Coordinate randomco = nextTocentral.get(0);
        assertEquals(2,    Coordinate.getNorm(new Coordinate(0,0,0),randomco));
        int[] tabco = randomco.getCoordinate();
        int sumco = tabco[0] + tabco[1] + tabco[2];
        assertEquals(0,sumco);
    }

    @Test
    void initializeNextCoordinatesAwayFromCentral(){

        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        List<Coordinate> awayFromCentral =  strategie.allPlaces();
        Collections.shuffle(awayFromCentral);
        Coordinate randomCo=awayFromCentral.get(0);
        int [] tabco= randomCo.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)<19);
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)>=0);
        assertEquals(0,sumco);
    }

    @Test
    void possibleCoordinatesParcelTest(){
        List<Coordinate> possibleCo = strategie.possibleCoordinatesParcel();
        Collections.shuffle(possibleCo);
        assertTrue(boardRules.isPlayableParcel(possibleCo.get(0)));
    }

    @Test
    void notPossibleCoordinatesCanal(){
        List<Coordinate[]> possibleCanals = strategie.possibleCoordinatesCanal();
        assertEquals(possibleCanals.size(),0);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        board.placeParcel(parcel3,new Coordinate(2,-1,-1));

        List<Coordinate[]>possibleCanals2 = strategie.possibleCoordinatesCanal();
        assertEquals(possibleCanals2.size(),2);
    }

    @Test
    void possibleCoordinatesCanal() {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        List<Coordinate[]> possibleCanals = strategie.possibleCoordinatesCanal();
        Collections.shuffle(possibleCanals);
        Coordinate[] tabco = possibleCanals.get(0);
        assertTrue(boardRules.isPlayableCanal(tabco[0],tabco[1]));
    }

    @Test
    void notExistPossibleCoordinatesBamboo(){
        assertEquals(0,strategie.possibleCoordinatesPanda().size());
    }

    @Test
    void ExistPossibleCoordinatesBamboo(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        assertTrue(board.getPlacedParcels().get(new Coordinate(1,-1,0)).getIrrigated());
        assertEquals(new Coordinate(1,-1,0), strategie.possibleCoordinatesPanda().get(0));
    }

    @Test
    void freePlaceInitialStates(){
        List<Coordinate> newPlaces = strategie.possibleCoordinatesParcel();
        assertEquals(new Coordinate(1,-1,0),newPlaces.get(0));
        assertEquals(new Coordinate(0,-1,1),newPlaces.get(1));
        assertEquals(new Coordinate(-1,1,0),newPlaces.get(2));
        assertEquals(new Coordinate(0,1,-1),newPlaces.get(3));
        assertEquals(new Coordinate(1,0,-1),newPlaces.get(4));
        assertEquals(new Coordinate(-1,0,1),newPlaces.get(5));
    }

    /** <h2><b>Test  posssibleCoordinatesNextToParcelsWithAColor </b></h2>
     */

    @Test
    void noParcelsSoNoPossibleCoordinatesForAnyColorGiven(){
        List<Coordinate> allPossibleCoNextToBlue=strategie.allPosssibleCoordinatesNextToParcelsWithAColor(ColorType.GREEN);
        List<Coordinate> allPossibleCoNextToRed=strategie.allPosssibleCoordinatesNextToParcelsWithAColor(ColorType.RED);
        assertEquals(0,allPossibleCoNextToBlue.size());
        assertEquals(0,allPossibleCoNextToRed.size());
    }

    @Test
    void twoRedParcelPlaced(){
        Coordinate expectedCo1=new Coordinate(0,1,-1);//11h
        Coordinate expectedCo2=new Coordinate(0,-1,1);//5h
        Coordinate expectedCo3=new Coordinate(2,-1,-1);//2h distant 2 du centre
        board.placeParcel(parcel1,new Coordinate(1,0,-1));//3h
        board.placeParcel(parcel2,new Coordinate(1,-1,0));//2h
        List<Coordinate> allPossibleCoNextToBlue=strategie.allPosssibleCoordinatesNextToParcelsWithAColor(ColorType.GREEN);
        List<Coordinate> allPossibleCoNextToRed=strategie.allPosssibleCoordinatesNextToParcelsWithAColor(ColorType.RED);
        assertEquals(0,allPossibleCoNextToBlue.size());
        assertEquals(3,allPossibleCoNextToRed.size());
        assertTrue(allPossibleCoNextToRed.contains(expectedCo1));
        assertTrue(allPossibleCoNextToRed.contains(expectedCo2));
        assertTrue(allPossibleCoNextToRed.contains(expectedCo3));
    }


    @Test
    void simpleRainStratPreferFertilizer(){
        board.placeParcel(new Parcel(),new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(ImprovementType.FERTILIZER),new Coordinate(-1,1,0));
        assertEquals(new Coordinate(-1,1,0),strategie.stratRain());
    }
    @Test
    void simpleRainStratWith1Parcel(){
        board.placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertEquals(new Coordinate(1,-1,0),strategie.stratRain());
    }


    @Test
    void simlpeThunderstormStrat(){
        Parcel parcel=new Parcel();
        parcel.addBamboo();
        board.placeParcel(new Parcel(),new Coordinate(-1,1,0));
        board.placeParcel(parcel,new Coordinate(1,-1,0));
        assertEquals(new Coordinate(1,-1,0),strategie.stratThunderstorm());
    }

    @Test
    void stratCloudNoMoreWathershedSoFertizilerChosen(){
        Game gamePanda= new Game(new BotType[]{BotType.PANDA_BOT});
        for(int i=0;i<3;i++) {
            gamePanda.getGameInteraction().drawImprovement(ImprovementType.WATERSHED);
        }
        PandaBot pandaBot= (PandaBot) gamePanda.getPlayerData().getBot();
        pandaBot.getRushPandaStrat().stratCloud();
        assertFalse(gamePanda.getPlayerData().getInventory().getInventoryImprovement(ImprovementType.FERTILIZER).isEmpty());
        assertTrue(gamePanda.getPlayerData().getInventory().getInventoryImprovement(ImprovementType.ENCLOSURE).isEmpty());
    }

    @Test
    void stratCloudNoMoreImprovementSoWeatherChanged(){
        Game gamePanda= new Game(new BotType[]{BotType.PANDA_BOT});
        assertEquals(WeatherType.NO_WEATHER,gamePanda.getPlayerData().getTemporaryInventory().getWeatherType());
        for(int i=0;i<3;i++) {
            gamePanda.getGameInteraction().drawImprovement(ImprovementType.WATERSHED);
            gamePanda.getGameInteraction().drawImprovement(ImprovementType.FERTILIZER);
            gamePanda.getGameInteraction().drawImprovement(ImprovementType.ENCLOSURE);
        }
        PandaBot pandaBot= (PandaBot) gamePanda.getPlayerData().getBot();
        pandaBot.getRushPandaStrat().stratCloud();
        assertEquals(WeatherType.SUN,gamePanda.getPlayerData().getTemporaryInventory().getWeatherType());
    }


    @Test
    void stratQuestionMark(){
        Game gamePanda= new Game(new BotType[]{BotType.PANDA_BOT});
        assertEquals(WeatherType.NO_WEATHER,gamePanda.getPlayerData().getTemporaryInventory().getWeatherType());
        PandaBot pandaBot= (PandaBot) gamePanda.getPlayerData().getBot();
        pandaBot.getRushPandaStrat().stratQuestionMark();
        assertEquals(WeatherType.SUN,gamePanda.getPlayerData().getTemporaryInventory().getWeatherType());
    }




}

