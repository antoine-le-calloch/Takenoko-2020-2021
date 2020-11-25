package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RandomBotTest {
    private RandomBot rdmBot1;
    private Board board;
    Parcel parcel1;

    @BeforeEach
    public void setUp() {
        Resource resource = new Resource();
        board = new Board();
        parcel1 = new Parcel();
        rdmBot1 = new RandomBot(resource,board);
    }

    @Test
    public void parcelIncrease(){
        rdmBot1.placeParcel(rdmBot1.possibleCoordinatesParcel());
        assertEquals(2,board.getParcels().size());
    }


}
