package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.*;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
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
    public void goodParcelPlacementSoParcelIncrease(){
        board.placeParcel(resource.drawParcel(),new Coordinate(1,-1,0));
        assertEquals(2,board.getPlacedParcels().size());
    }
    @Test
    void normTesting(){
        assertEquals(2,Coordinate.getNorm(new Coordinate(1,-1,0),new Coordinate(1,0,-1)));
        assertNotEquals(17,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(3,0,-3)));
        assertEquals(0,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(0,0,0)));
    }

    @Test void getParcelbyCotesting(){
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        parcel2 = board.getPlacedParcels().get(new Coordinate(0,-1,1));
        assertEquals(parcel1,parcel2);
        assertNull(board.getPlacedParcels().get(new Coordinate(1,-1,0)));
    }

    @Test void irrigationFromCentral(){
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        assertTrue(parcel1.getIrrigated());
    }

    @Test void noIrrigationFromCentral(){
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(parcel3.getIrrigated());
    }

    @Test void irrigationBycanals(){
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        board.placeCanal(canal2,new Coordinate(0,-1,1),new Coordinate(1,-2,1));
        assertTrue(parcel3.getIrrigated());
    }

    @Test void canalAboveanAnother(){
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(board.isPlayableCanal(new Coordinate(1,-1,0),new Coordinate(0,-1,1)));
    }
    @Test void wrongPlacementCanalawayFromcentral(){
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-2,1)));
    }

    @Test void wrongPlacementCanal(){
        board.placeParcel(parcel1,new Coordinate(0,-1,1));
        board.placeParcel(parcel2,new Coordinate(1,-1,0));
        board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(0,-2,2)));
    }

    @Test void invalideCoordinatesforCanal(){
        assertFalse(board.placeCanal(canal,new Coordinate(0,-1,1),new Coordinate(0,-1,1)));
        assertFalse(board.placeCanal(canal,new Coordinate(1,-1,0),new Coordinate(-1,1,0)));
        assertFalse(board.placeCanal(canal,new Coordinate(1,-1,0),new Coordinate(2,0,-2)));
    }

    @Test void parcelInexistantsoNoCanal(){
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(board.isPlayableCanal(new Coordinate(0,0,0),new Coordinate(1,-1,0)));
    }

    @Test
    void goodMoveCharacter(){
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,0,-1));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(2,-1,-1));
        assertTrue(board.moveCharacter(board.getPanda(),new Coordinate(1,-1,0)));
        assertTrue(board.moveCharacter(board.getPanda(),new Coordinate(2,-1,-1)));
    }

    @Test
    void wrongMoveCharacter(){
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,0,-1));
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(2,-1,-1));
        assertFalse(board.moveCharacter(board.getPanda(),new Coordinate(2,-1,-1)));
    }
}
