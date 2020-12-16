package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.CantDeleteBambooException;
import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class ParcelTest {
    Board board;

    @BeforeEach
    public void initialize(){
        board = new Board();
    }

    @Test
    public void sameParcel(){
        Parcel parcel1 = new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING);
        Parcel parcel2 = new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING);
        assertEquals(parcel1,parcel1);
        assertNotEquals(parcel1,parcel2);
        assertNotEquals(parcel1,null);
    }

    @Test
    public void notIrrigated(){
        Parcel parcel = new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING);
        assertFalse(parcel.getIrrigated());
    }

    @Test
    public void setIrrigated(){
        Parcel parcel = new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING);
        parcel.setIrrigated();
        assertTrue(parcel.getIrrigated());
    }

    @Test
    public void addBamboo(){
        Parcel parcel = new Parcel(ColorType.NO_COLOR, ImprovementType.NOTHING);
        parcel.setIrrigated();
        parcel.addBamboo();
        assertEquals(parcel.getNbBamboo(),2);
    }

    @Test
    public void delBamboo() throws CantDeleteBambooException {
        Parcel parcel = new Parcel(ColorType.NO_COLOR, ImprovementType.NOTHING);
        parcel.setIrrigated();
        parcel.delBamboo();
        assertEquals(parcel.getNbBamboo(),0);
    }


    /**
     * <h1><u>TEST IMPROVEMENT</u></h1>
     */

    @Test
    public void setIrrigatedWithImprovement(){
        Parcel parcel = new Parcel(ColorType.NO_COLOR, ImprovementType.WATERSHED);
        assertTrue(parcel.getIrrigated());
    }

    @Test
    public void addBambooWithImprovement(){
        Parcel parcel = new Parcel(ColorType.NO_COLOR, ImprovementType.FERTILIZER);
        parcel.setIrrigated();
        parcel.addBamboo();
        assertEquals(parcel.getNbBamboo(),4);
    }

    @Test
    public void maxAddBambooWithImprovement(){
        Parcel parcel = new Parcel(ColorType.NO_COLOR, ImprovementType.FERTILIZER);
        parcel.setIrrigated();
        parcel.addBamboo();
        parcel.addBamboo();
        assertEquals(parcel.getNbBamboo(),4);
    }

    @Test
    public void delBambooWithImprovement() {
        Parcel parcel = new Parcel(ColorType.NO_COLOR, ImprovementType.ENCLOSURE);
        parcel.setIrrigated();

        Exception exception1 = assertThrows(CantDeleteBambooException.class, parcel::delBamboo);
        assertEquals(exception1.getMessage(),"null");
    }


}