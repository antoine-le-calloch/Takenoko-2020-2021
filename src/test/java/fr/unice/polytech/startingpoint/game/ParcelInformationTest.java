package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParcelInformationTest {
    ParcelInformation parcelInformation;

    @BeforeEach
    void setUp(){
        parcelInformation = new ParcelInformation();
    }

    @Test
    void parcelInformationEquality(){
        assertEquals(parcelInformation,parcelInformation);
        assertEquals(new ParcelInformation(),new ParcelInformation(ColorType.NO_COLOR));
        assertEquals(new ParcelInformation(),new ParcelInformation(ImprovementType.NOTHING));
        assertEquals(new ParcelInformation(),new ParcelInformation(ColorType.NO_COLOR,ImprovementType.NOTHING));

        parcelInformation.setImprovementType(ImprovementType.WATERSHED);
        assertEquals(new ParcelInformation(ImprovementType.WATERSHED),parcelInformation);
    }
}