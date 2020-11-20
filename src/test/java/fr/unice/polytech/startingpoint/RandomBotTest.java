package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RandomBotTest {
    private RandomBot rdmBot1;
    private Resource resource;
    private Board board;
    Parcel parcel1;

    @BeforeEach
    public void setUp() {
        rdmBot1 = new RandomBot("random");
        resource = new Resource();
        board = new Board();
        parcel1 = new Parcel();
    }

    @Test
    public void parcelIncrease(){
        rdmBot1.placeParcel(resource, board);
        assertEquals(2,board.getParcel().size());
    }
}
