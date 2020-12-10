package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class ParcelMissionTest {
    Parcel parcel1;
    Parcel parcel2;
    Parcel parcel3;
    Parcel parcel4;
    Board board;
    Resource resource;
    ParcelMission mission1;
    ParcelMission mission2;
    ParcelMission mission3;
    ParcelMission mission4;
    RandomBot bot;


    @BeforeEach
    void setUp(){
        mission1 = new ParcelMission(ColorType.RED, 2, FormType.TRIANGLE);
        mission2 = new ParcelMission(ColorType.RED, 3, FormType.LINE);
        mission3 = new ParcelMission(ColorType.BLUE, 2, FormType.TRIANGLE);
        mission4 = new ParcelMission(ColorType.BLUE, 3, FormType.LINE);
        board = new Board();
        resource = new Resource();
        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.RED);
        parcel3 = new Parcel(ColorType.RED);
        parcel4 = new Parcel(ColorType.RED);
        bot = new RandomBot(resource, board);
    }

    @Test
    void newMission(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertEquals(mission1, mission1);
    }

    @Test void checkMissionTriangle() throws BadPlaceParcelException {
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(1,-1,0)).setIrrigated());
        board.placeParcel(parcel2,parcel2.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        board.placeParcel(parcel3,parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated());
        assertEquals(2, mission1.checkMission(board,bot.getInventory()));
    }

    @Test void checkNoMissionTriangle(){
        assertEquals(0, mission1.checkMission(board, bot.getInventory()));
    }


    @Test void checkMissionLigneOnBoard() throws BadPlaceParcelException {
        board.placeParcel(parcel4,parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(1,0,-1)).setIrrigated());
        board.placeParcel(parcel2,parcel2.setCoordinates(new Coordinate(1,-1,0)).setIrrigated());
        board.placeParcel(parcel3,parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated());
        assertEquals(3,mission2.checkMission(board,bot.getInventory()));
    }


    @Test void checkNoMissionLigne(){
        assertEquals(0,mission2.checkMission(board,bot.getInventory()));
    }


    @Test void triangleOnBoardGoodColor() throws BadPlaceParcelException { //checkTriangle
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(1,-1,0)).setIrrigated());
        board.placeParcel(parcel2,parcel2.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        board.placeParcel(parcel3,parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated());
        assertTrue(mission1.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneOnBoardGoodColor() throws BadPlaceParcelException { //check Line
        board.placeParcel(parcel4,parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(1,0,-1)).setIrrigated());
        board.placeParcel(parcel2,parcel2.setCoordinates(new Coordinate(1,-1,0)).setIrrigated());
        board.placeParcel(parcel3,parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated());
        assertTrue(mission1.checkFormIrrigateWithColor(board,2,5));
    }


    @Test void triangleOnBoardBadColor() throws BadPlaceParcelException { //checkTriangle
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(1,-1,0)).setIrrigated());
        board.placeParcel(parcel2,parcel2.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        board.placeParcel(parcel3,parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated());
        assertFalse(mission3.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneOnBoardBadColor() throws BadPlaceParcelException { //check Line
        board.placeParcel(parcel4,parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(1,0,-1)).setIrrigated());
        board.placeParcel(parcel2,parcel2.setCoordinates(new Coordinate(1,-1,0)).setIrrigated());
        board.placeParcel(parcel3,parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated());
        assertFalse(mission3.checkFormIrrigateWithColor(board,2,5));
    }

    @Test void triangleNotIrrigated() throws BadPlaceParcelException { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,0,1));
    }


    @Test void ligneNotIrrigated() throws BadPlaceParcelException { //checkLine
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,2,5));
    }


    @Test void wrongTriangle() throws BadPlaceParcelException { //checkTriangle
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(1,-1,0)).setIrrigated());
        board.placeParcel(parcel4,parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        assertFalse(mission1.checkFormIrrigateWithColor(board,0,1));
    }

    @Test void wrongLine() throws BadPlaceParcelException { //checkLine
        board.placeParcel(parcel1,parcel1.setCoordinates(new Coordinate(0,-1,1)).setIrrigated());
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeParcel(parcel4,parcel4.setCoordinates(new Coordinate(0,-2,2)).setIrrigated());
        assertFalse(mission1.checkFormIrrigateWithColor(board,2,5));
    }
}