
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
        assertTrue(!parcel1.equals(null));
    }

    @Test
    public void sameCoordinates(){
        parcel1.setPosition(1,0,0);
        parcel2.setPosition(1,0,0);
        assertEquals(0,parcel1.getNorm(parcel2));
    }

    @Test
    public void nextToEachOther(){
        parcel1.setPosition(1,0,0);
        parcel2.setPosition(0,0,0);
        assertEquals(1,parcel1.getNorm(parcel2));
        parcel1.setPosition(0,0,-1);
        parcel2.setPosition(0,0,0);
        assertEquals(1,parcel1.getNorm(parcel2));
    }

    @Test
    public void farAwayFromEachOther(){
        parcel1.setPosition(0,0,-2);
        parcel2.setPosition(0,0,0);
        assertTrue(parcel1.getNorm(parcel2) > 1);
    }
}
