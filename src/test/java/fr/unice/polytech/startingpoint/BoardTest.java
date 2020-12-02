package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Resource resource;
    Board board;
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
        parcel1 = new Parcel("noColor");
        parcel2 = new Parcel("noColor");
        parcel3 = new Parcel("noColor");
        parcel4 = new Parcel("noColor");
        canal = new Canal();
        canal2 = new Canal();
    }

    @Test
    public void freePlaceInitialStates(){
        List<Coordinate> newPlaces = board.getPlayablePlaces();
        assertEquals(new Coordinate(1,-1,0),newPlaces.get(0));
        assertEquals(new Coordinate(0,-1,1),newPlaces.get(1));
        assertEquals(new Coordinate(-1,1,0),newPlaces.get(2));
        assertEquals(new Coordinate(0,1,-1),newPlaces.get(3));
        assertEquals(new Coordinate(1,0,-1),newPlaces.get(4));
        assertEquals(new Coordinate(-1,0,1),newPlaces.get(5));
    }


    @Test
    public void goodParcelPlacementSoParcelIncrease(){
        board.isPlacedParcel(resource.drawParcel(),new Coordinate(1,-1,0));
        assertEquals(2,board.getPlacedParcels().size());
    }
    @Test
    void normTesting(){
        assertEquals(2,Coordinate.getNorm(new Coordinate(1,-1,0),new Coordinate(1,0,-1)));
        assertNotEquals(17,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(3,0,-3)));
        assertEquals(0,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(0,0,0)));
    }

    @Test void getParcelbyCotesting(){
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        parcel2 = board.getPlacedParcels().get(new Coordinate(0,-1,1));
        assertEquals(parcel1,parcel2);
        assertNull(board.getPlacedParcels().get(new Coordinate(1,-1,0)));
    }

    @Test void irrigationFromCentral(){
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        assertTrue(parcel1.getIrrigated());
    }

    @Test void noIrrigationFromCentral(){
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(parcel3.getIrrigated());
    }

    @Test void irrigationBycanals(){
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        board.isPlacedCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        board.isPlacedCanal(canal2,new Coordinate(0,-1,1),new Coordinate(1,-2,1));
        assertTrue(parcel3.getIrrigated());
    }

    @Test void canalAboveanAnother(){
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(board.isPlayableCanal(new Coordinate(1,-1,0),new Coordinate(0,-1,1)));
    }
    @Test void wrongPlacementCanalawayFromcentral(){
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-2,1)));
    }

    @Test void wrongPlacementCanal(){
        board.isPlacedParcel(parcel1,new Coordinate(0,-1,1));
        board.isPlacedParcel(parcel2,new Coordinate(1,-1,0));
        board.isPlacedCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(0,-2,2)));
    }

    @Test void invalideCoordinatesforCanal(){
        assertFalse(board.isPlacedCanal(canal,new Coordinate(0,-1,1),new Coordinate(0,-1,1)));
        assertFalse(board.isPlacedCanal(canal,new Coordinate(1,-1,0),new Coordinate(-1,1,0)));
        assertFalse(board.isPlacedCanal(canal,new Coordinate(1,-1,0),new Coordinate(2,0,-2)));
    }

    @Test void parcelInexistantsoNoCanal(){
        assertFalse(board.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(board.isPlayableCanal(new Coordinate(0,0,0),new Coordinate(1,-1,0)));
    }
}
