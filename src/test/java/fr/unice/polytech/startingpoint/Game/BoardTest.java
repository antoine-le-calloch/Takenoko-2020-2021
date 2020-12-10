package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Type.*;

import fr.unice.polytech.startingpoint.exception.BadPlaceCanalException;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import fr.unice.polytech.startingpoint.exception.MoveCharacterException;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


public class BoardTest {
    Resource resource;
    Board board;
    Bot bot;
    Parcel parcel1;
    Parcel parcel2;
    Parcel parcel3;
    Parcel parcel4;
    Canal canal;
    Canal canal2;

    @BeforeEach
    public void initialize(){
        board = new Board();
        resource = new Resource();
        bot = new ParcelBot(resource, board);
        parcel1 = new Parcel(ColorType.NO_COLOR);
        parcel2 = new Parcel(ColorType.NO_COLOR);
        parcel3 = new Parcel(ColorType.NO_COLOR);
        parcel4 = new Parcel(ColorType.NO_COLOR);
        canal = new Canal();
        canal2 = new Canal();
    }

    @Test
    public void freePlaceInitialStates(){
        List<Coordinate> newPlaces = bot.possibleCoordinatesParcel();
        assertEquals(new Coordinate(1,-1,0),newPlaces.get(0));
        assertEquals(new Coordinate(0,-1,1),newPlaces.get(1));
        assertEquals(new Coordinate(-1,1,0),newPlaces.get(2));
        assertEquals(new Coordinate(0,1,-1),newPlaces.get(3));
        assertEquals(new Coordinate(1,0,-1),newPlaces.get(4));
        assertEquals(new Coordinate(-1,0,1),newPlaces.get(5));
    }


    @Test
    public void goodParcelPlacementSoParcelIncrease() throws BadPlaceParcelException {
        board.placeParcel(resource.drawParcel().get(0),new Coordinate(1,-1,0));
        assertEquals(2,board.getPlacedParcels().size());
    }
    @Test
    void normTesting(){
        assertEquals(2,Coordinate.getNorm(new Coordinate(1,-1,0),new Coordinate(1,0,-1)));
        assertNotEquals(17,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(3,0,-3)));
        assertEquals(0,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(0,0,0)));
    }

    @Test void getParcelbyCotesting() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        parcel2 = board.getPlacedParcels().get(new Coordinate(0,-1,1));
        assertEquals(parcel1,parcel2);
        assertNull(board.getPlacedParcels().get(new Coordinate(1,-1,0)));
    }

    @Test void irrigationFromCentral() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        assertTrue(parcel1.getIrrigated());
    }

    @Test void noIrrigationFromCentral() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(parcel3.getIrrigated());
    }

    @Test void irrigationBycanals() throws BadPlaceParcelException, BadPlaceCanalException {
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        board.placeCanal(canal2,new Coordinate(0,-1,1),new Coordinate(1,-2,1));
        assertTrue(parcel3.getIrrigated());
    }

    @Test void canalAboveAnother() throws BadPlaceParcelException, BadPlaceCanalException {
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));

        Exception exception1 = assertThrows(BadPlaceCanalException.class, () ->
        { board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));});

        Exception exception2 = assertThrows(BadPlaceCanalException.class, () ->
        { board.placeCanal(canal,new Coordinate(1,-1,0),new Coordinate(0,-1,1));});

        assertEquals(exception1.getMessage(),"[0,-1,1], [1,-1,0]");
        assertEquals(exception2.getMessage(),"[1,-1,0], [0,-1,1]");
    }
    @Test void wrongPlacementCanalawayFromcentral() throws BadPlaceParcelException {
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));

        Exception exception1 = assertThrows(BadPlaceCanalException.class, () ->
        { board.placeCanal(canal, new Coordinate(0,-1,1),new Coordinate(1,-2,1));});

        assertEquals(exception1.getMessage(),"[0,-1,1], [1,-2,1]");
    }

    @Test void goodPlacementCanal() throws BadPlaceParcelException, BadPlaceCanalException {
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
    }

    @Test void invalideCoordinatesforCanal(){
        Exception exception1 = assertThrows(BadPlaceCanalException.class, () ->
        { board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(0,-1,1));});
        Exception exception2 = assertThrows(BadPlaceCanalException.class, () ->
        { board.placeCanal(canal,new Coordinate(1,-1,0),new Coordinate(-1,1,0));});
        Exception exception3 = assertThrows(BadPlaceCanalException.class, () ->
        { board.placeCanal(canal,new Coordinate(1,-1,0),new Coordinate(2,0,-2));});

        assertEquals(exception1.getMessage(),"[0,-1,1], [0,-1,1]");
        assertEquals(exception2.getMessage(),"[1,-1,0], [-1,1,0]");
        assertEquals(exception3.getMessage(),"[1,-1,0], [2,0,-2]");
    }



    @Test void parcelInexistantsoNoCanal(){
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(board.isPlayableCanal(new Coordinate(0,0,0),new Coordinate(1,-1,0)));
    }

    //bonne coord pour deplacer un character
    @Test
    void goodMoveCharacter() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,0,-1));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(2,-1,-1));
        board.moveCharacter(board.getPanda(),new Coordinate(1,-1,0));
        board.moveCharacter(board.getPanda(),new Coordinate(2,-1,-1));
    }

    //mauvaise coord pour deplacer un character
    @Test
    void wrongMoveCharacter() throws BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,0,-1));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(2,-1,-1));
        Exception exception = assertThrows(MoveCharacterException.class, () ->
        {board.moveCharacter(board.getPanda(),new Coordinate(2,-1,-1));});
        assertEquals(exception.getMessage(), "[2,-1,-1]");
    }

    //le paysan fait pousser un bambou ou il est
    @Test
    void goodGrow() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.moveCharacter(board.getPeasant(),parcel1.getCoordinates());
        assertEquals(2,parcel1.getNbBamboo());
    }

    //4 bambous max
    @Test
    void maxGrow() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        for (int i = 0; i < 10; i++) {
            board.moveCharacter(board.getPeasant(),parcel1.getCoordinates());
            board.moveCharacter(board.getPeasant(),new Coordinate(0,0,0));
        }
        assertEquals(4,parcel1.getNbBamboo());
    }

    //bambous pousse autour si irrigué + même couleur
    @Test
    void actionPeasantSameColorAroundAndIrrigated() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.BLUE), new Coordinate(1, -1, 0));
        board.placeParcel(new Parcel(ColorType.BLUE), new Coordinate(1,0,-1));
        board.moveCharacter(board.getPeasant(), new Coordinate(1, -1, 0));
        assertEquals(2,board.getPlacedParcels().get(new Coordinate(1,0,-1)).getNbBamboo());
    }

    //bambous pousse pas autour si couleur diff
    @Test
    void actionPeasantDifferentColorAround() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.BLUE), new Coordinate(1, -1, 0));
        board.placeParcel(new Parcel(ColorType.RED), new Coordinate(1,0,-1));
        board.moveCharacter(board.getPeasant(), new Coordinate(1, -1, 0));
        assertEquals(1,board.getPlacedParcels().get(new Coordinate(1,0,-1)).getNbBamboo());
    }

    //bambous pousse pas autour si non irriguée
    @Test
    void actionPeasantNotIrrigatedAround() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(new Parcel(ColorType.BLUE), new Coordinate(1, -1, 0));
        board.placeParcel(new Parcel(ColorType.BLUE), new Coordinate(1, 0, -1));
        board.placeParcel(new Parcel(ColorType.RED), new Coordinate(2,-1,-1));
        board.moveCharacter(board.getPeasant(), new Coordinate(1, -1, 0));
        assertEquals(0,board.getPlacedParcels().get(new Coordinate(2,-1,-1)).getNbBamboo());
    }

    // panda mange un bambou
    @Test
    void goodEat() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.moveCharacter(board.getPanda(), parcel1.getCoordinates());
        assertEquals(0, parcel1.getNbBamboo());
    }

    //il mange pas si 0 bambous sur la parcelle
    @Test
    void minEat() throws MoveCharacterException, BadPlaceParcelException {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.placeParcel(parcel2, new Coordinate(1,0,-1));
        for (int i = 0; i < 10; i++) {
            board.moveCharacter(board.getPanda(), parcel1.getCoordinates());
            board.moveCharacter(board.getPanda(), parcel2.getCoordinates());
        }
        assertEquals(0, parcel1.getNbBamboo());
    }

}
