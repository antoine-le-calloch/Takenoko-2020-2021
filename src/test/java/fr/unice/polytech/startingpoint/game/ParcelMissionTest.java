package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;

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
        parcel1 = new Parcel(ColorType.RED);
        parcel2 = new Parcel(ColorType.RED);
        parcel3 = new Parcel(ColorType.RED);
        parcel4 = new Parcel(ColorType.RED);
        bot = new RandomBot(game.getGameInteraction(), game.getRules());
    }

    @Test
    void newMission(){
        assertNotEquals(mission1,null);
        assertNotEquals(mission1,mission2);
        assertNotEquals(mission1.getFormType(),mission2.getFormType());
        assertEquals(mission1, mission1);
        assertEquals(mission1.getFormType(),mission3.getFormType());
    }

    /**
     * <h1><u>CHECK PARCEL MISSIONS</u></h1>
     */

    @Test void checkMissionTriangle(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertEquals(2, mission1.checkMission(board,game.getPlayerData().getInventory()));
    }

    @Test void checkMissionLineOnBoard(){
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
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
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(mission1.checkFormIrrigateWithColor(board,new Coordinate(1, 0, -1),new Coordinate(1, -1, 0)));
    }

    @Test void triangleOnBoardBadColor() { //checkTriangle
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.placeParcel(parcel2, new Coordinate(0, -1, 1));
        board.placeParcel(parcel3, new Coordinate(1, -2, 1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(mission3.checkFormIrrigateWithColor(board, new Coordinate(1, 0, -1), new Coordinate(1, -1, 0)));
    }

    @Test void triangleNotIrrigated() { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(0,-1,1));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,new Coordinate(1, 0, -1),new Coordinate(1, -1, 0)));
    }

    @Test void wrongTriangle() { //checkTriangle
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,new Coordinate(1, 0, -1),new Coordinate(1, -1, 0)));
    }

    /**
     * <h1><u>CAS LINE</u></h1>
     */

    @Test void lineOnBoardGoodColor(){ //check Line
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertTrue(mission1.checkFormIrrigateWithColor(board,new Coordinate(0, -1, 1),new Coordinate(0, 1, -1)));
    }

    @Test void lineOnBoardBadColor() { //check Line
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.getPlacedParcels().get(new Coordinate(1,-2,1)).setIrrigated();
        assertFalse(mission3.checkFormIrrigateWithColor(board,new Coordinate(0, -1, 1),new Coordinate(0, 1, -1)));
    }

    @Test void lineNotIrrigated() { //checkLine
        board.placeParcel(parcel4,new Coordinate(0,-1,1));
        board.placeParcel(parcel1,new Coordinate(1,0,-1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(mission1.checkFormIrrigateWithColor(board,new Coordinate(0, -1, 1),new Coordinate(0, 1, -1)));
    }

    @Test void wrongLine() { //checkLine
        board.placeParcel(parcel1, new Coordinate(0, -1, 1));
        board.placeParcel(parcel2, new Coordinate(1, -1, 0));
        board.placeParcel(parcel3, new Coordinate(1, -2, 1));
        board.placeParcel(parcel4, new Coordinate(0, -2, 2));
        board.getPlacedParcels().get(new Coordinate(0,-2,2)).setIrrigated();
        assertFalse(mission1.checkFormIrrigateWithColor(board, new Coordinate(0, -1, 1), new Coordinate(0, 1, -1)));
    }

    @Test void checkNoMissionLine(){
        assertEquals(0,mission2.checkMission(board,game.getPlayerData().getInventory()));
    }
}