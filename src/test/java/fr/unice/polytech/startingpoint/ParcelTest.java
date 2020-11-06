
package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {


    @Test
    public void simpleAdd(){

        Parcel parcel = new Parcel();
        Parcel parcel1=new Parcel();
        assertEquals(parcel,parcel);
        assertNotEquals(parcel,parcel1);
        assertTrue(!parcel.equals(null));

    }

}
