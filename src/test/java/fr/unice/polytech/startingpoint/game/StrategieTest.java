package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.ParcelBot;
import fr.unice.polytech.startingpoint.bot.RandomStrat;
import fr.unice.polytech.startingpoint.bot.Strategie;
import fr.unice.polytech.startingpoint.type.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StrategieTest {
    private Game game;
    private Parcel parcel1;
    private Parcel parcel2;
    private Parcel parcel3;
    private ParcelBot bot1;
    private Board board;
    private Rules rules;
    private Strategie strategie;

    @BeforeEach
    public void setUp(){
        game = new Game();
        parcel1 = new Parcel(ColorType.BLUE);
        parcel2 = new Parcel();
        parcel3 = new Parcel();
        bot1 = new ParcelBot(game.getGameInteraction(),game.getRules());
        board = game.getBoard();
        rules = game.getRules();
        strategie = new RandomStrat(bot1, rules);
    }



    @Test
    public void initializeNextCoordinatesNextToCentral(){
        List<Coordinate> nextTocentral = strategie.possibleCoordinatesParcel();
        assertEquals(6,nextTocentral.size());
        Coordinate randomco = nextTocentral.get(0);
        assertEquals(2,    Coordinate.getNorm(new Coordinate(0,0,0),randomco));
        int[] tabco = randomco.getCoordinate();
        int sumco = tabco[0] + tabco[1] + tabco[2];
        assertEquals(0,sumco);
    }

    @Test
    public void initializeNextCoordinatesAwayFromCentral(){

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
    public void possibleCoordinatesParcelTest(){
        List<Coordinate> possibleCo = strategie.possibleCoordinatesParcel();
        Collections.shuffle(possibleCo);
        assertTrue(rules.isPlayableParcel(possibleCo.get(0)));
    }

    @Test
    public void notPossibleCoordinatesCanal(){
        List<Coordinate[]> possibleCanals = strategie.possibleCoordinatesCanal();
        assertEquals(possibleCanals.size(),0);
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        board.placeParcel(parcel3,new Coordinate(2,-1,-1));

        List<Coordinate[]>possibleCanals2 = strategie.possibleCoordinatesCanal();
        assertEquals(possibleCanals2.size(),2);
    }

    @Test
    public void possibleCoordinatesCanal() {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        List<Coordinate[]> possibleCanals = strategie.possibleCoordinatesCanal();
        Collections.shuffle(possibleCanals);
        Coordinate[] tabco = possibleCanals.get(0);
        assertTrue(rules.isPlayableCanal(tabco[0],tabco[1]));
    }

    @Test
    public void notExistPossibleCoordinatesBamboo(){
        assertEquals(0,strategie.possibleCoordinatesPanda().size());
    }

    @Test
    public void ExistPossibleCoordinatesBamboo(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        assertTrue(board.getPlacedParcels().get(new Coordinate(1,-1,0)).getIrrigated());
        assertEquals(new Coordinate(1,-1,0), strategie.possibleCoordinatesPanda().get(0));
    }

    @Test
    public void freePlaceInitialStates(){
        List<Coordinate> newPlaces = strategie.possibleCoordinatesParcel();
        assertEquals(new Coordinate(1,-1,0),newPlaces.get(0));
        assertEquals(new Coordinate(0,-1,1),newPlaces.get(1));
        assertEquals(new Coordinate(-1,1,0),newPlaces.get(2));
        assertEquals(new Coordinate(0,1,-1),newPlaces.get(3));
        assertEquals(new Coordinate(1,0,-1),newPlaces.get(4));
        assertEquals(new Coordinate(-1,0,1),newPlaces.get(5));
    }
}
