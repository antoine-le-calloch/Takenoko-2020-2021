package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class ParcelMissionTest {
    Game game;
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
        game = new Game();
        board = game.getBoard();
        resource = game.getResource();
        parcel1 = new Parcel(ColorType.RED,ImprovementType.NOTHING);
        parcel2 = new Parcel(ColorType.RED,ImprovementType.NOTHING);
        parcel3 = new Parcel(ColorType.RED,ImprovementType.NOTHING);
        parcel4 = new Parcel(ColorType.RED,ImprovementType.NOTHING);
        bot = new RandomBot(game, game.getRules());
    }

    @Test
    void newMission(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertEquals(mission1, mission1);
    }


    /**
     * <h1><u>CHECK MISSIONPARCEL</u></h1>
     */


    @Test void checkMissionTriangle(){
        parcel1.setCoordinates(new Coordinate(1,-1,0)).setIrrigated();
        parcel2.setCoordinates(new Coordinate(0,-1,1)).setIrrigated();
        parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated();
        board.placeParcel(parcel1,parcel1.getCoordinates());
        board.placeParcel(parcel2,parcel2.getCoordinates());
        board.placeParcel(parcel3,parcel3.getCoordinates());
        assertEquals(2, mission1.checkMission(board,game.getPlayerData().getInventory()));
    }


    @Test void checkMissionLigneOnBoard(){
        parcel1.setCoordinates(new Coordinate(1,0,-1)).setIrrigated();
        parcel2.setCoordinates(new Coordinate(1,-1,0)).setIrrigated();
        parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated();
        parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated();
        board.placeParcel(parcel4,parcel4.getCoordinates());
        board.placeParcel(parcel1,parcel1.getCoordinates());
        board.placeParcel(parcel2,parcel2.getCoordinates());
        board.placeParcel(parcel3,parcel3.getCoordinates());
        assertEquals(3,mission2.checkMission(board,game.getPlayerData().getInventory()));
    }

    /**
     * <h1><u>CAS TRIANGLE</u></h1>
     */

    @Test void checkNoMissionTriangle(){
        assertEquals(0, mission1.checkMission(board, game.getPlayerData().getInventory()));
    }

    @Test void triangleOnBoardGoodColor(){ //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated();
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertTrue(mission1.checkFormIrrigateWithColor(board,new Coordinate(1, 0, -1),new Coordinate(1, -1, 0)));
    }

    @Test void triangleOnBoardBadColor() { //checkTriangle
        parcel1.setCoordinates(new Coordinate(1, -1, 0)).setIrrigated();
        parcel2.setCoordinates(new Coordinate(0, -1, 1)).setIrrigated();
        parcel3.setCoordinates(new Coordinate(1, -2, 1)).setIrrigated();
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.placeParcel(parcel2, new Coordinate(0, -1, 1));
        board.placeParcel(parcel3, new Coordinate(1, -2, 1));
        assertFalse(mission3.checkFormIrrigateWithColor(board, new Coordinate(1, 0, -1), new Coordinate(1, -1, 0)));
    }


    @Test void triangleNotIrrigated() { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,new Coordinate(1, 0, -1),new Coordinate(1, -1, 0)));
    }


    @Test void wrongTriangle() { //checkTriangle
        parcel1.setCoordinates(new Coordinate(1,-1,0)).setIrrigated();
        parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated();
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,new Coordinate(1, 0, -1),new Coordinate(1, -1, 0)));
    }

    /**
     * <h1><u>CAS LIGNE</u></h1>
     */

    @Test void ligneOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        parcel1.setCoordinates(new Coordinate(1,0,-1)).setIrrigated();
        parcel2.setCoordinates(new Coordinate(1,-1,0)).setIrrigated();
        parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated();
        parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated();
        assertTrue(mission1.checkFormIrrigateWithColor(board,new Coordinate(0, -1, 1),new Coordinate(0, 1, -1)));
    }

    @Test void ligneOnBoardBadColor() { //check Line
        parcel1.setCoordinates(new Coordinate(1,0,-1)).setIrrigated();
        parcel2.setCoordinates(new Coordinate(1,-1,0)).setIrrigated();
        parcel3.setCoordinates(new Coordinate(1,-2,1)).setIrrigated();
        parcel4.setCoordinates(new Coordinate(0,-1,1)).setIrrigated();
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission3.checkFormIrrigateWithColor(board,new Coordinate(0, -1, 1),new Coordinate(0, 1, -1)));
    }

    @Test void ligneNotIrrigated() { //checkLine
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,new Coordinate(0, -1, 1),new Coordinate(0, 1, -1)));
    }

    @Test void wrongLine() { //checkLine
        parcel1.setCoordinates(new Coordinate(0, -1, 1)).setIrrigated();
        parcel4.setCoordinates(new Coordinate(0, -2, 2)).setIrrigated();
        board.placeParcel(parcel1, new Coordinate(0, -1, 1));
        board.placeParcel(parcel2, new Coordinate(1, -1, 0));
        board.placeParcel(parcel3, new Coordinate(1, -2, 1));
        board.placeParcel(parcel4, new Coordinate(0, -2, 2));
        assertFalse(mission1.checkFormIrrigateWithColor(board, new Coordinate(0, -1, 1), new Coordinate(0, 1, -1)));
    }


    @Test void checkNoMissionLigne(){
        assertEquals(0,mission2.checkMission(board,game.getPlayerData().getInventory()));
    }

}