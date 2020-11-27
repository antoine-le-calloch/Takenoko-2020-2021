
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

}
