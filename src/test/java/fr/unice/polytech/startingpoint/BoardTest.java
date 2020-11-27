package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        parcel1 = new Parcel();
        parcel2 = new Parcel();
        parcel3 = new Parcel();
        parcel4 = new Parcel();
        canal = new Canal();
        canal2 = new Canal();
    }

    @Test
    public void freePlaceInitialStates(){
        ArrayList<Coordinate> newPlaces = board.getFreePlaces();
        assertEquals(new Coordinate(1,-1,0),newPlaces.get(0));
        assertEquals(new Coordinate(0,-1,1),newPlaces.get(1));
        assertEquals(new Coordinate(0,1,-1),newPlaces.get(2));
        assertEquals(new Coordinate(-1,1,0),newPlaces.get(3));
        assertEquals(new Coordinate(1,0,-1),newPlaces.get(4));
        assertEquals(new Coordinate(-1,0,1),newPlaces.get(5));
    }


    @Test
    public void goodParcelPlacementSoParcelIncrease(){
        board.putParcel(resource.drawParcel(),new Coordinate(1,-1,0));
        assertEquals(2,board.getPlacedParcels().size());
    }
    @Test
    void normTesting(){
        assertEquals(2,Coordinate.getNorm(new Coordinate(1,-1,0),new Coordinate(1,0,-1)));
        assertNotEquals(17,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(3,0,-3)));
        assertEquals(0,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(0,0,0)));
    }

    @Test void getParcelbyCotesting(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        parcel2=board.getParcelByCo(new Coordinate(0,-1,1));
        assertEquals(parcel1,parcel2);
        assertNull(board.getParcelByCo(new Coordinate(1,-1,0)));
    }

    @Test void irrigationFromCentral(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        assertTrue(parcel1.getIrrigated());
    }

    @Test void noIrrigationFromCentral(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(parcel3.getIrrigated());
    }

    @Test void irrigationBycanals(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        board.putCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        board.putCanal(canal2,new Coordinate(0,-1,1),new Coordinate(1,-2,1));
        assertTrue(parcel3.getIrrigated());
    }

    @Test void canalAboveanAnother(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertFalse(board.playableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(board.playableCanal(new Coordinate(1,-1,0),new Coordinate(0,-1,1)));
    }
    @Test void wrongPlacementCanalawayFromcentral(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(board.playableCanal(new Coordinate(0,-1,1),new Coordinate(1,-2,1)));
    }

    @Test void wrongPlacementCanal(){
        Parcel parcel4=new Parcel();
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        board.putParcel(parcel4,new Coordinate(0,-2,2));
        board.putCanal(canal,new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertFalse(board.playableCanal(new Coordinate(0,-1,1),new Coordinate(0,-2,2)));
    }

    @Test void invalideCoordinatesforCanal(){
        assertFalse(board.putCanal(canal,new Coordinate(0,-1,1),new Coordinate(0,-1,1)));
        assertFalse(board.putCanal(canal,new Coordinate(1,-1,0),new Coordinate(-1,1,0)));
        assertFalse(board.putCanal(canal,new Coordinate(1,-1,0),new Coordinate(2,0,-2)));
    }

    @Test void parcelInexistantsoNoCanal(){
        assertFalse(board.playableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(board.playableCanal(new Coordinate(0,0,0),new Coordinate(1,-1,0)));
    }

    @Test void triangleOnBoard(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel2,new Coordinate(0,-1,1));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(1,-2,1));
        assertTrue(board.checkTriangle());
    }

    @Test void triangleNotIrrigated(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel2,new Coordinate(0,-1,1));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(board.checkTriangle());
    }

    @Test void ligneOnBoard(){
        board.putParcel(parcel4,new Coordinate(0,-1,1));
        board.putParcel(parcel1,new Coordinate(1,0,-1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(1,0,-1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(1,-2,1));
        assertTrue(board.checkLine());
    }


    @Test void ligneNotIrrigated(){
        board.putParcel(parcel4,new Coordinate(0,-1,1));
        board.putParcel(parcel1,new Coordinate(1,0,-1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(board.checkLine());
    }


    @Test void wrongTriangle(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel4,new Coordinate(0,1,-1));
        board.irrigatedParcels.add(new Coordinate(1,-1,0));
        board.irrigatedParcels.add(new Coordinate(0,1,-1));
        assertFalse(board.checkTriangle());
    }

    @Test void wrongLine(){
        board.putParcel(parcel4,new Coordinate(0,-1,1));
        board.putParcel(parcel4,new Coordinate(0,-2,2));
        board.irrigatedParcels.add(new Coordinate(0,-1,1));
        board.irrigatedParcels.add(new Coordinate(0,-2,2));
        assertFalse(board.checkLine());
    }


}
