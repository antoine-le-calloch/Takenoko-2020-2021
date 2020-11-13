
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
        parcel1.setPosition(new int[]{1,-1,0});
        parcel2.setPosition(new int[]{1,-1,0});
        assertEquals(0,board.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()));
    }

    @Test
    public void nextToEachOther(){
        parcel1.setPosition(new int[]{1,-1,0});
        parcel2.setPosition(new int[]{0,0,0});
        assertEquals(1,board.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()));
        parcel1.setPosition(new int[]{-1,1,0});
        parcel2.setPosition(new int[]{0,0,0});
        assertEquals(1,board.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()));
    }

    @Test
    public void farAwayFromEachOther(){
        parcel1.setPosition(new int[]{-2,2,0});
        parcel2.setPosition(new int[]{0,0,0});
        assertTrue(board.getNorm(parcel1.getCoordinates(),parcel2.getCoordinates()) > 1);
    }
}
