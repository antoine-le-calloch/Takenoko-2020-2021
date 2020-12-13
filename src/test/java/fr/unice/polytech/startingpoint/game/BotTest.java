package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.ParcelBot;


import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class BotTest {
    private Game game;
    private Parcel parcel1;
    private Parcel parcel2;
    private Parcel parcel3;
    private ParcelBot bot1;
    private Canal canal;
    Board board;
    Rules rules;



    @BeforeEach
    public void setUp(){
        game = new Game();
        parcel1 = new Parcel(ColorType.BLUE);
        parcel2 = new Parcel(ColorType.NO_COLOR);
        parcel3 = new Parcel(ColorType.NO_COLOR);
        bot1=new ParcelBot(game,game.getRules());
        canal = new Canal();
        board = game.getBoard();
        rules=game.getRules();
    }



    @Test
    public void initializeNextCoordinatesNextToCentral(){
        List<Coordinate> nextTocentral = bot1.possibleCoordinatesParcel();
        assertEquals(6,nextTocentral.size());
        Coordinate randomco=nextTocentral.get(0);
        assertEquals(2,    Coordinate.getNorm(new Coordinate(0,0,0),randomco));
        int[] tabco = randomco.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertEquals(0,sumco);
    }

    @Test
    public void initializeNextCoordinatesAwayFromCentral() throws BadPlaceParcelException {

        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        List<Coordinate> awayFromCentral =  bot1.allPlaces();
        Collections.shuffle(awayFromCentral);
        Coordinate randomCo=awayFromCentral.get(0);
        int [] tabco= randomCo.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)<19);
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)>=0);
        assertEquals(0,sumco);
    }


    @Test
    public void possibleCoordinatesParcelTest(){
        List<Coordinate> possibleCo = bot1.possibleCoordinatesParcel();
        Collections.shuffle(possibleCo);
        assertTrue(rules.isPlayableParcel(possibleCo.get(0)));
    }

    @Test
    public void notPossibleCoordinatesCanal() throws BadPlaceParcelException{
        List<Coordinate[]> possibleCanals = bot1.possibleCoordinatesCanal();
        assertEquals(possibleCanals.size(),0);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        board.placeParcel(parcel3,new Coordinate(2,-1,-1));

/*
        Exception exception1 = assertThrows(BadPlaceCanalException.class, () ->
        { board.placeCanal(canal,new Coordinate(0,0,0),new Coordinate(1,-1,0));});

        assertEquals("[0,0,0], [1,-1,0]",exception1.getMessage());*/

        List<Coordinate[]>possibleCanals2 = bot1.possibleCoordinatesCanal();
        assertEquals(possibleCanals2.size(),2);
    }

    @Test
    public void possibleCoordinatesCanal() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        List<Coordinate[]> possibleCanals = bot1.possibleCoordinatesCanal();
        Collections.shuffle(possibleCanals);
        Coordinate[] tabco = possibleCanals.get(0);
        assertTrue(rules.isPlayableCanal(tabco[0],tabco[1]));
    }

    @Test
    public void notExistPossibleCoordinatesBamboo(){
        assertEquals(0,bot1.possibleCoordinatesPanda().size());
    }

    @Test
    public void ExistPossibleCoordinatesBamboo() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        assertTrue(parcel1.getIrrigated());
        assertEquals(parcel1.getCoordinates(), bot1.possibleCoordinatesPanda().get(0));
    }


    @Test
    public void movePanda() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.movePanda(bot1.possibleCoordinatesPanda().get(0));
        assertEquals(1, game.getPlayerData().getInventory().getBamboo(ColorType.BLUE));
        assertEquals(0,parcel1.getNbBamboo());
    }

    @Test
    public void movePeasant() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.movePeasant(bot1.possibleCoordinatesPanda().get(0));
        assertEquals(2,parcel1.getNbBamboo());
    }

    @Test
    public void freePlaceInitialStates(){
        List<Coordinate> newPlaces = bot1.possibleCoordinatesParcel();
        assertEquals(new Coordinate(1,-1,0),newPlaces.get(0));
        assertEquals(new Coordinate(0,-1,1),newPlaces.get(1));
        assertEquals(new Coordinate(-1,1,0),newPlaces.get(2));
        assertEquals(new Coordinate(0,1,-1),newPlaces.get(3));
        assertEquals(new Coordinate(1,0,-1),newPlaces.get(4));
        assertEquals(new Coordinate(-1,0,1),newPlaces.get(5));
    }

}