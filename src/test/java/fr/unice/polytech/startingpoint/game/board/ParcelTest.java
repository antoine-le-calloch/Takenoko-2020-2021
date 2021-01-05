package fr.unice.polytech.startingpoint.game.board;

import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    Parcel parcel;

    @BeforeEach
    void initialize(){
        parcel = new Parcel();
    }

    @Test
    void sameParcel(){
        assertEquals(parcel,parcel);
        assertNotEquals(parcel,new Parcel());
        assertNotEquals(parcel,null);
    }

    @Test
    void constructorTest(){
        assertEquals(new Parcel().getParcelInformation(),new Parcel().getParcelInformation());
        assertEquals(new Parcel().getColor(),new Parcel(ColorType.NO_COLOR).getColor());
        assertEquals(new Parcel().getImprovement(),new Parcel(ImprovementType.NOTHING).getImprovement());
        assertEquals(new Parcel().getParcelInformation(),new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING).getParcelInformation());

        parcel.setImprovementType(ImprovementType.ENCLOSURE);
        assertEquals(new Parcel(ImprovementType.ENCLOSURE).getImprovement(),parcel.getImprovement());
    }

    @Test
    void notIrrigated(){
        Parcel parcel = new Parcel();
        assertFalse(parcel.getIrrigated());
    }

    @Test
    void setIrrigated(){
        Parcel parcel = new Parcel();
        parcel.setIrrigated();
        assertTrue(parcel.getIrrigated());
    }

    @Test
    void addBamboo(){
        Parcel parcel = new Parcel();
        parcel.setIrrigated();
        parcel.addBamboo();
        assertEquals(parcel.getNbBamboo(),2);
    }

    @Test
    void delBamboo(){
        Parcel parcel = new Parcel();
        parcel.setIrrigated();
        parcel.delBamboo();
        assertEquals(parcel.getNbBamboo(),0);
    }

    @Test
    void notIrrigatedThenIrrigatedWithImprovement(){
        Parcel parcel = new Parcel();
        assertFalse(parcel.getIrrigated());
        parcel.setImprovementType(ImprovementType.WATERSHED);
        assertTrue(parcel.getIrrigated());
    }

    @Test
    void setIrrigatedWithImprovement(){
        Parcel parcel = new Parcel(ImprovementType.WATERSHED);
        assertTrue(parcel.getIrrigated());
    }

    @Test
    void addBambooWithImprovement(){
        Parcel parcel = new Parcel(ImprovementType.FERTILIZER);
        parcel.setIrrigated();
        parcel.addBamboo();
        assertEquals(parcel.getNbBamboo(),4);
    }

    @Test
    void maxAddBambooWithImprovement(){
        Parcel parcel = new Parcel(ImprovementType.FERTILIZER);
        parcel.setIrrigated();
        parcel.addBamboo();
        parcel.addBamboo();
        assertEquals(parcel.getNbBamboo(),4);
    }

    @Test
    void delBambooWithImprovement() {
        Parcel parcel = new Parcel(ColorType.GREEN,ImprovementType.ENCLOSURE);
        parcel.setIrrigated();
        assertEquals(ColorType.NO_COLOR, parcel.delBamboo());
    }
}