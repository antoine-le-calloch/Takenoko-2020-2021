package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RandomBotTest {
    private RandomBot rdmBot1;
    private Board board;
    Parcel parcel1;

    @BeforeEach
    public void setUp() {
        board = new Board();
        parcel1 = new Parcel("noColor");
        rdmBot1 = new RandomBot(new Resource(),board);
    }
/*
    @Test
    public void parcelIncrease(){
        rdmBot1.placeRandomParcel(board.getFreePlaces());
        assertEquals(2,board.getPlacedParcels().size());
    }
*/

}
