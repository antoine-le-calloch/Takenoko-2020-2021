
package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {
    Board board;
    Parcel parcel1;
    Parcel parcel2;

    @BeforeEach
    public void initialize(){
        board = new Board();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
    }

    @Test
    public void simpleAdd(){
        assertEquals(parcel1,parcel1);
        assertNotEquals(parcel1,parcel2);
        assertNotEquals(parcel1,null);
    }

    @Test
    public void sameCoordinates(){
        parcel1.setCoordinates(new Coordinate(1,-1,0));
        parcel2.setCoordinates(new Coordinate(1,-1,0));
        assertEquals(0,Coordinate.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()));
    }

    @Test
    public void nextToEachOther(){
        parcel1.setCoordinates(new Coordinate(1,-1,0));
        parcel2.setCoordinates(new Coordinate(1,0,-1));
        assertEquals(2,Coordinate.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()));
        parcel1.setCoordinates(new Coordinate(1,0,-1));
        parcel2.setCoordinates(new Coordinate(0,1,-1));
        assertEquals(2,Coordinate.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()));
    }

    @Test
    public void farAwayFromEachOther(){
        parcel1.setCoordinates(new Coordinate(2,-2,0));
        parcel2.setCoordinates(new Coordinate(0,0,0));
        assertTrue(Coordinate.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()) > 1);
    }
}
